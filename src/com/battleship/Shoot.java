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




        boolean randomShotTaken = false;

        while (!randomShotTaken) {



            int randomX = random.nextInt(10);
            int randomY = random.nextInt(10);







                String letterCoordinate = Character.toString(yAxis[randomY]);
                String numberCoordinate = Integer.toString(randomX);
                String result = numberCoordinate+letterCoordinate;

                if (!shotsFired.contains(result)) {
                    shotsFired.add(result);
                    randomShotTaken = true;
                    return result;
                }


        }
        return "No valid shot";
    }
    public static String hitShot(GameBoard enemyGameBoard, String lastHitShot) {


        boolean randomShotTaken = false;

        int attempts = 0;
        int maxAttempts = 20;

        while (!randomShotTaken && attempts < maxAttempts) {


            int[] fireFieldX = {-1, 1, 0, 0};
            int[] fireFieldY = {0, 0, -1, 1};


            String xString = lastHitShot.substring(0, lastHitShot.length() - 1);
            String yString = lastHitShot.substring(lastHitShot.length() - 1);

            lastShotX = Integer.parseInt(xString) - 1;
            lastShotY = yString.charAt(0) - 'a';


            int randomSpace = random.nextInt(4);

            int randomX = lastShotX + fireFieldX[randomSpace]+1;
            int randomY = lastShotY + fireFieldY[randomSpace];


            if (randomX < 0 || randomX >= 10 || randomY < 0 || randomY >= 10) {
                attempts++;
                continue;
            }

                String letterCoordinate = Character.toString(yAxis[randomY]);
                String numberCoordinate = Integer.toString(randomX );
                String result = numberCoordinate + letterCoordinate;

                if (!shotsFired.contains(result)) {
                    shotsFired.add(result);
                    randomShotTaken = true;
                    return result;
                }

            attempts++;
        }


        if (!randomShotTaken) {
            return randomShot(enemyGameBoard);
        }

        return "No valid shot";
    }


}
