//GB-17-cf




import java.util.Random;

public class Shoot {



    int lastShotX;
    int lastShotY;


    /*char[][] enemyBoard= {
            {'e','e','e','e','e','e','e','e','e','e'},
            {'e','e','e','e','e','e','e','e','e','e'}};*/

    char[] yAxis= {'a','b','c','d','e','f','g','h','i','j'};

    Random random = new Random();


    private  String randomShot() {



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
    private  String hitShot(){

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
    private void uppdateLastShot(){
        lastShotX=;
        lastShotY=;
    }

    public Shoot(int lastShotX, int lastShotY) {
        this.lastShotX = lastShotX;
        this.lastShotY = lastShotY;
    }

    public Shoot() {
    }
}
