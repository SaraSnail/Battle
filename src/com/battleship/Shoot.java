package com.battleship;//GB-17-cf








import java.util.Random;

public class Shoot {


    static int lastShotX;
    static int lastShotY;


    /*char[][] enemyBoard= {
            {'e','e','e','e','e','e','e','e','e','e'},
            {'e','e','e','e','e','e','e','e','e','e'}};*/

    static char[] yAxis= {'a','b','c','d','e','f','g','h','i','j'};

    static Random random = new Random();


    public static String randomShot(char[][] enemyGameBoard) {



        boolean randomShotTaken = false;

        while (!randomShotTaken) {



            int randomX = random.nextInt(10);
            int randomY = random.nextInt(10);
            char randomCoordinate = getEnemyGameBoard[randomX][randomY];

            lastShotX = randomX;
            lastShotY = randomY;




            if(randomCoordinate=='e') {

                String letterCoordinate = Character.toString(yAxis[randomY]);
                String numberCoordinate = Integer.toString(randomX);
                String result = numberCoordinate+letterCoordinate;

                randomShotTaken = true;
                setEnemyGameBoard[randomX][randomY] = 'x';

                return result;

            }
        }
        return "No valid shot";
    }
    public static  String hitShot(char[][] enemyGmeBoard){

        boolean randomShotTaken= false;

        while(!randomShotTaken) {

            int randomSpace = random.nextInt(4);

            int[] fireFieldX = {-1, 1, 0, 0};
            int[] fireFieldY = {0, 0, -1, 1};

            int randomX = lastShotX + fireFieldX[randomSpace];
            int randomY = lastShotY + fireFieldY[randomSpace];

            if (randomX < 0 || randomX >= 10 || randomY < 0 || randomY >= 10) {
                continue;
            }

            char randomSecondShotCoordinate = getEnemyGameBoard[randomX][randomY];

            if (randomSecondShotCoordinate == 'e') {

                String letterCoordinate = Character.toString(yAxis[randomY]);
                String numberCoordinate = Integer.toString(randomX);
                String result = numberCoordinate + letterCoordinate;

                randomShotTaken = true;
                setEnemyGameBoard[randomX][randomY] = 'x';

                return result;

            }
        }
        return "No valid shot";
    }
    public static String uppdateLastShot(){
        lastShotX=0;
        lastShotY=0;
    }

    public Shoot(int lastShotX, int lastShotY) {
        this.lastShotX = lastShotX;
        this.lastShotY = lastShotY;
    }

    public Shoot() {
    }
}
