import java.util.ArrayList;
import java.util.List;

// GB-8-AWS
public class GameBoard {
    private char[][] board;
   /* private List<Ship> ships;*/

    public GameBoard(){
        board = new char[10][10];
    /*    ships = new ArrayList<>();*/
        initializeBoard();

    }
        // metod för att sätta upp spelplanen
    private void initializeBoard(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = ' ';
            }
        }
    }
        // metod för att testa i intellij hur det ser ut
    public void displayBoard(){
        System.out.println("------------GameBoard------------");
        System.out.println("   0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < 10; i++) {
            char rowLabel = (char) ('A' + i);
            System.out.print(rowLabel + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
        }

    }


}
