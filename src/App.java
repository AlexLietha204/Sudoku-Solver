public class App {
public static int solveCounter = 0;

    public static void main(String[] args) throws Exception {
        Board board = new Board("src\\InputFile.txt");

       SolveBoard(board);
    }


    public static void SolveBoard(Board board){
      if(Solve(board, 1,1)){
      board.printBoard();
      System.out.println("Board Works");
      }
      else{
        System.out.println("Board Sucks");
      }
      System.out.println("Solve calls: " + solveCounter);
    }
    

    public static boolean Solve(Board board, int row, int col){
        solveCounter++;
        int n = board.getBoardSize();

        if (row > n) {
            return true;
        }

        int nextRow = (n == col) ? row + 1 : row;
        int nextCol = (n == col) ? 1 : col + 1;

        if (board.getRow(row).isOriginalValue(col)) {
            return Solve(board, nextRow, nextCol);
        }

        for (int i = 1; i <= n; i++) {
            if (board.isValidPlacement(row, col, i)) {
                board.setValue(row, col, i);
                if (Solve(board, nextRow, nextCol)) {
                    return true;
                }
                board.setValue(row, col, 0);
            }
        }




        return false;
    }
}


