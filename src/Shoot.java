
import java.util.Random;

public class Shoot {

    boolean randomShotTaken;

    public char[][] enemyBoard= {
            {'e','e','e','e','e','e','e','e','e','e'},
            {'e','e','e','e','e','e','e','e','e','e'}};


    Random random = new Random();

    private int[] randomShot() {

        randomShotTaken = true;

        while (true) {


            int randomX = random.nextInt(10);
            int randomY = random.nextInt(10);
            char randomCoordinate = enemyBoard[randomX][randomY];

            int[] result = new int[2];
            result[0] = randomX;
            result[1] = randomY;

            if(randomCoordinate=='e') {
                randomShotTaken = false;
                enemyBoard[randomX][randomY] = 'h';
                return result;

            }
        }
    }
}
