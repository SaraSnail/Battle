import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// GB-8-AWS
public class GameBoard {
    private char[][] board;
   private List<Ship> ships;
    // GB-8-AWS
    public GameBoard(){
        board = new char[10][10];
        ships = new ArrayList<>();
        initializeBoard();
        initializeFleet();
        placeAllShips();

    }
           // GB-8-AWS
    private void initializeBoard(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = ' ';
            }
        }
    }
        // GB-8-AWS-P2
    private void initializeFleet(){
        ships.add( new Ship("Hangarfartyg", 5, false, 0));
        ships.add( new Ship("Slagskepp", 4, false, 0));
        ships.add( new Ship("Slagskepp", 4, false, 0));
        ships.add( new Ship("Kryssare", 3, false, 0));
        ships.add( new Ship("Kryssare", 3, false, 0));
        ships.add( new Ship("Kryssare", 3, false, 0));
        ships.add( new Ship("Ubåt", 2, false, 0));
        ships.add( new Ship("Ubåt", 2, false, 0));
        ships.add( new Ship("Ubåt", 2, false, 0));
        ships.add( new Ship("Ubåt", 2, false, 0));
    }

            //GB-8-AWS
    private void placeShip(Ship ship, int row, int col, boolean horizontal){
        int size = ship.getSize();

        if(horizontal){
            for (int i = 0; i < size; i++) {
                board[row][col + i] = 'S';
            }
        }else{
            for (int i = 0; i < size; i++) {
                board[row + i][col] = 'S';
            }
        }
    }
   // GB-8-AWS
    private void placeAllShips(){
        Random random = new Random();

        ships.sort((s1, s2) -> Integer.compare(s2.getSize(), s1.getSize()));

        for (Ship ship: ships){
            boolean placed = false;
            int attempts = 0;

            while(!placed && attempts < 100){
                attempts ++;

                int row = random.nextInt(10);
                int col = random.nextInt(10);
                boolean horizontal = random.nextBoolean();


                if(canPlaceShip(ship,row,col,horizontal)) {
                    placeShip(ship, row, col, horizontal);
                    System.out.println("Placed " + ship.getKind() + " at (" + row + ", " + col + ") " + (horizontal ? "horizontally" : "vertically"));
                    placed = true;
                }
            }
        }
    }
        //GB-8-AWS
    private boolean canPlaceShip(Ship ship, int row, int col, boolean horizontal){
        int size = ship.getSize();

        if(horizontal){
            if(col + size > 10) return false;
            for (int i = 0; i < size; i++) {
                if(board[row][col + i] != ' ')return false;
            }
        }else{
            if(row + size > 10) return false;
            for (int i = 0; i < size; i++) {
                if(board[row + i][col] != ' ')return false;

            }
        }
        return isAreaAvailable(row,col,size,horizontal);
    }
    //GB-8-AWS
    private boolean isAreaAvailable(int row, int col, int size, boolean horizontal) {
        for (int i = -1; i <= size; i++) {
            for (int j = -1; j <= 1; j++) {
                int checkRow = horizontal ? row : row + i;
                int checkCol = horizontal ? col + i : col;

                checkCol += j;

                if(checkRow >= 0 && checkRow < 10 && checkCol >= 0 && checkCol < 10){
                    if (board[checkRow][checkCol] != ' '){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // GB-8-AWS metod för att testa i intellij hur det ser ut kommer att tas bort senare när grafisk funktion är inlagd.
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
