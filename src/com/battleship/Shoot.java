package com.battleship;//GB-17-cf








import java.util.HashSet;
import java.util.Random;

public class Shoot {




    static int lastShotX;
    static int lastShotY;



    static char[] yAxis= {'a','b','c','d','e','f','g','h','i','j'};
    static HashSet<String> shotsFired = new HashSet<>();


    static Random random = new Random();


    public static String randomShot(GameBoard enemyGameBoard ) {

        char[][] enemyBoard = enemyGameBoard.getBoard();


        boolean randomShotTaken = false;

        while (!randomShotTaken) {



            int randomX = random.nextInt(10);
            int randomY = random.nextInt(10);
            char randomCoordinate = enemyBoard[randomX][randomY];





            if(randomCoordinate==' ') {

                String letterCoordinate = Character.toString(yAxis[randomY]);
                String numberCoordinate = Integer.toString(randomX);
                String result = numberCoordinate+letterCoordinate;

                if (!shotsFired.contains(result)) {
                    shotsFired.add(result);
                    randomShotTaken = true;
                    return result;
                }

            }
        }
        return "No valid shot";
    }
    public static  String hitShot(GameBoard enemyGameBoard, String lastHitShot){

        char[][] enemyBoard = enemyGameBoard.getBoard();


        boolean randomShotTaken= false;

        while(!randomShotTaken) {

            int randomSpace = random.nextInt(4);

            int[] fireFieldX = {-1, 1, 0, 0};
            int[] fireFieldY = {0, 0, -1, 1};

            String xString = lastHitShot.substring(0, lastHitShot.length() - 1);
            String yString = lastHitShot.substring(lastHitShot.length() - 1);

            lastShotX = Integer.parseInt(xString);
            lastShotY = yString.charAt(0)-'a';

            int randomX = lastShotX + fireFieldX[randomSpace];
            int randomY = lastShotY + fireFieldY[randomSpace];

            if (randomX < 0 || randomX >= 10 || randomY < 0 || randomY >= 10) {
                continue;
            }

            char randomSecondShotCoordinate = enemyBoard[randomX][randomY];

            if (randomSecondShotCoordinate == ' ') {

                String letterCoordinate = Character.toString(yAxis[randomY]);
                String numberCoordinate = Integer.toString(randomX);
                String result = numberCoordinate + letterCoordinate;

                if (!shotsFired.contains(result)) {
                    shotsFired.add(result);
                    randomShotTaken = true;
                    return result;
                }
            }
        }
        return "No valid shot";
    }

    public Shoot() {
    }
}
