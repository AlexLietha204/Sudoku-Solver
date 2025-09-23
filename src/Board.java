import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Board {
    // Arrays to represent rows, columns, and boxes (sub-grids of the board)
    private Row[] rows;
    private Column[] columns;
    private Box[] boxes;
    private int boardSize;       // Size of the board (e.g., 9 for a 9x9 Sudoku)
    private boolean validBoard;  // Tracks if the board is valid

    // Constructor: initializes the board from a file
    public Board(String fileName){
        validBoard = true;
        setBoard(fileName);
    }

    // Returns the size of the board
    public int getBoardSize(){
        return boardSize;
    }

    // Retrieves a specific row by index (1-based indexing)
    public Row getRow(int rowIndex){
        return rows[rowIndex-1];
    }

    // Retrieves a specific column by index (1-based indexing)
    public Column getColumn(int columnIndex){
        return columns[columnIndex-1];
    }

    // Retrieves a specific box (sub-grid) based on row and column
    public Box getBox(int row, int column){
        int boxSize = (int) Math.sqrt(getBoardSize());
        int boxRow = (row - 1) / boxSize;    
        int boxCol = (column - 1) / boxSize; 
        int location = boxCol + (boxRow * boxSize);
        return boxes[location];
    }

    // Sets a value at a specific row and column, and updates row, column, and box structures
    public void setValue(int row, int column, int value){
         getRow(row).setValue(column, value);
        getColumn(column).setValue(row, value);

        int boxSize = (int) Math.sqrt(getBoardSize());
        // compute which box (0-based box coords)
        int boxRow = (row - 1) / boxSize;
        int boxCol = (column - 1) / boxSize;
        int boxIndex = boxCol + (boxRow * boxSize);

        // index inside that box (0-based)
        int inBoxRow = (row - 1) % boxSize;
        int inBoxCol = (column - 1) % boxSize;
        int location = inBoxCol + (inBoxRow * boxSize); // 0-based

        // Box API accepts 1-based index
        boxes[boxIndex].setValue(location + 1, value);
    }

    // Prints the current state of the board to the console
    public void printBoard(){
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                System.out.print(getRow(i+1).getValue(j+1) + " ");
            }
            System.out.println();
        }
    }

    // Reads the board configuration from a file
public void setBoard(String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null && validBoard) {
            addToBoard(line.trim());
        }
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
    }

    // Print the board only if it is valid
    if (validBoard) {
        printBoard();
    }
}

// Parses a line of input and adds it to the board
public void addToBoard(String line) {
    if (line.isEmpty()) return; // Ignore empty lines

    // Handle board size input (e.g., "9x9")
    if (line.contains("x")) {
        String[] parts = line.split("x");
        if (parts.length == 2 && parts[0].equals(parts[1])) {
            boardSize = Integer.parseInt(parts[0]);
            createRows(boardSize);
            System.out.println("Board size set to: " + boardSize);
        } else {
            System.out.println("Invalid board size format: " + line);
            validBoard = false;
        }
        return;
    }

    // Handle cell input (format: row,column,value)
    String[] parts = line.split(",");
    if (parts.length == 3) {
        try {
            int row = Integer.parseInt(parts[0]);
            int column = Integer.parseInt(parts[1]);
            int value = Integer.parseInt(parts[2]);

            System.out.printf("Trying to put onto board: Row %d, Column %d, Value %d%n", row , column, value);

            boolean validPlacement = isValidPlacement(row, column, value);
            if (getRow(row).getValue(column ) != 0 || !validPlacement) {
                validBoard = false;
                System.out.println("Invalid placement — board rejected.");
            } else {
                setValue(row, column, value);
                getRow(row).setOriginalValue(column, true);

            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in line: " + line);
            validBoard = false;
        }
    } else {
        System.out.println("Invalid line format: " + line);
        validBoard = false;
    }
}

    // Creates empty rows, columns, and boxes based on board size
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

    // Checks if placing a value at (row, column) is valid according to Sudoku rules
    public boolean isValidPlacement(int row, int column, int value){

        // Check row, column, and box for conflicts
        for (int i = 0; i < getBoardSize(); i++) {
            //System.out.println("Is " + getRow(row).getValue(i) + " Equal to " + value);
            if(getRow(row).getValue(i+1) == value){
                //System.out.println("Yes");
                return false;
            }
            //System.out.println("Is " + getColumn(column).getValue(i) + " Equal to " + value);
            if(getColumn(column).getValue(i+1) == value){
                //System.out.println("Yes");
                return false;
            }
            //System.out.println("Is " + getBox(row-1, column-1).getValue(i) + " Equal to " + value);
            if(getBox(row, column).getValue(i+1) == value){
                //System.out.println("Yes");
                return false;
            }
        }
        return true;
    }
}
