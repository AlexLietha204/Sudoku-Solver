import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Board {
    private Row[] rows;
    private Column[] columns;
    private Box[] boxes;
    private int boardSize;
    private boolean validBoard;

    public Board(String fileName){
        validBoard = true;
        setBoard(fileName);
        
    }

    public int getBoardSize(){
        return boardSize;
    }

    public Row getRow(int rowIndex){
        return rows[rowIndex-1];
    }

    public Column getColumn(int columnIndex){
        return columns[columnIndex-1];
    }

    public Box getBox(int row, int column){
        int yCord = row/(int) Math.sqrt(getBoardSize());
        int xCord = column/ (int) Math.sqrt(getBoardSize());
        int location = xCord + (yCord * (int) Math.sqrt(getBoardSize()));
        return boxes[location];
    }

    public void setValue(int row, int column, int value){
        getRow(row).setValue(column-1, value);
        getColumn(column).setValue(row-1, value);

    
        row = (row-1)%(int) Math.sqrt(getBoardSize());
        column = (column-1)%(int) Math.sqrt(getBoardSize());
        int location = column + (row * (int) Math.sqrt(getBoardSize()));
        getBox(row, column).setValue(location, value);
    }

    public void printBoard(){
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                System.out.print(getRow(i+1).getValue(j) + " ");
                
            }
            System.out.println();
        }
    }


    public void setBoard(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null && validBoard) {
                AddToBoard(line);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        if(validBoard){
            printBoard();
        }
    }

    public void AddToBoard(String line){
        String[] numbersChar = {"0", "0", "0"};
        char[] chars = new char[2];
        int numberIndex = 0;
        int charIndex = 0;
        for (int i = 0; i < line.length(); i++) {
            
            if (line.charAt(i) == ' ') {
            }
            else if (Character.isDigit(line.charAt(i))){
                numbersChar[numberIndex] += Character.toString(line.charAt(i));
            }
            else if (line.charAt(i) == ',') {
                chars[charIndex] = line.charAt(i);
                numberIndex++;
                charIndex++;
            }
            else if (line.charAt(i) == 'x') {
                chars[charIndex] = line.charAt(i);
                numberIndex = 2;
            }
            else{
                System.out.println("Error?");
            }
            
        }
        int[] numbers = new int[3];
        for (int i = 0; i < numbersChar.length; i++) {
            numbers[i] = Integer.parseInt(numbersChar[i]);
        }
        if (numbers[0] == numbers[2] && chars[0] == 'x') {
            boardSize = numbers[0];
            createRows(boardSize);
            System.out.println(boardSize);
        }

        if(chars[0] == ',' && chars[1] == ','){
            System.out.println("Trying to put onto board: ");
            System.out.println("Row: " + (numbers[0]-1) + "\nColumn: " + (numbers[1]-1));
            System.out.println("Value: " + getRow(numbers[0]).getValue(numbers[1]-1));
            if(getRow(numbers[0]).getValue(numbers[1]-1) != 0){
                validBoard = false;
                System.out.println("Invalid Board");
            }
            else{
                setValue(numbers[0], numbers[1], numbers[2]);
            }
        }

    }

    private void createRows(int boardSize){
        rows = new Row[boardSize];
        columns = new Column[boardSize];
        boxes = new Box[boardSize];
        
        for (int i = 0; i < boardSize; i++) {
            rows[i] = new Row(boardSize);
            columns[i] = new Column(boardSize);
            boxes[i] = new Box(boardSize);
        }
    }
}
