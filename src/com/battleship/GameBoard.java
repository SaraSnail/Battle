package com.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// GB-8-AWS
public class GameBoard {
    private char[][] board;
   private List<Ship> ships;
    // GB-8-AWS
    public GameBoard(boolean myBoard){
        board = new char[10][10];
        ships = new ArrayList<>();
        initializeBoard();
        if (myBoard) {
            initializeFleet();
            placeAllShips();
        }
    }
           // GB-8-AWS
    private void initializeBoard(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = ' ';
            }
        }
    }
        // GB-8-AWS-
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
                ship.addCoordinates(row, col + i);
            }
        }else{
            for (int i = 0; i < size; i++) {
                board[row + i][col] = 'S';
                ship.addCoordinates(row + i, col);
            }
        }
    }

                // logik för att lösa om inte alla skepp är inlagda.

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

                if(isAreaAvailable(row,col,ship.getSize(),horizontal)){
                    placeShip(ship, row, col, horizontal);
                    System.out.println("Placed " + ship.getKind() + " at (" + row + ", " + col + ") " + (horizontal ? "horizontally" : "vertically"));      // använder för kontroll atm kommer försvinna
                    placed = true;
                }
            }
            // om alla inte placeras så kör vi programmet igen.
            if(!placed){
                System.out.println("-----------------------------------------------------------");
                System.out.println("Misslyckades med att placera alla skeppen, försöker igen.");
                System.out.println("-----------------------------------------------------------");
                initializeBoard();
                placeAllShips();
                return;
            }
        }
    }

    //GB-8-AWS
    private boolean isAreaAvailable(int row, int col, int size, boolean horizontal) {
        if((horizontal && col + size > 10) || (!horizontal && row + size > 10)){
            return false;
        }
        for (int i = 0; i < size; i++) {
            int shipRow = horizontal ? row : row + i;
            int shipCol = horizontal ? col + i : col;

            for (int r = shipRow - 1; r <= shipRow + 1; r++) {
                for (int c = shipCol -1; c <= shipCol + 1; c++) {
                    if (r >= 0 && c < 10 && c >= 0 && r < 10) {
                        if (board[r][c] != ' ') {
                            return false;
                        }
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

   /* public boolean getPosition(int row, int col){
        for (Ship ship : ships){
            for(int[] coordinate : ship.getCoordinates()){
                if(coordinate[0] == row && coordinate[1] == col){
                    return true;
                }
            }
        }
        return false;
    }*/

    //GB-26-SA
    public char[][] getBoard() {
        return board;
    }

    //GB-36-AWS
    public List<Ship> getShips() {
        return ships;
    }
}
