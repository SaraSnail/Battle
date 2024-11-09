import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.awt.*;

//aws
public class GameView extends Application {
    private GameBoard myGameBoard;
    private GameBoard enemyGameBoard;

    //aws
    @Override
    public void start(Stage primaryStage) throws Exception {
        myGameBoard = new GameBoard();
        enemyGameBoard= new GameBoard();

        AnchorPane myGame = new AnchorPane();        // Min Spelplan
        battleGroundFX(myGame, myGameBoard, false);
        myGame.setLayoutX(50);
        myGame.setLayoutY(50);

        Label myLabel = new Label("Min Spelplan");
        myLabel.setLayoutX(50);
        myLabel.setLayoutY(25);

        AnchorPane enemyGame = new AnchorPane();    // Motståndare Spelplan
        battleGroundFX(enemyGame, enemyGameBoard, true);
        enemyGame.setLayoutX(600);
        enemyGame.setLayoutY(50);

        Label enemyLabel = new Label("Motståndarens Spelplan");
        enemyLabel.setLayoutX(600);
        enemyLabel.setLayoutY(25);

        AnchorPane stack = new AnchorPane();
        stack.getChildren().addAll(myLabel,enemyLabel,myGame,enemyGame);

        Scene scene = new Scene(stack, 1600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BattleShips");

        primaryStage.show();
        myGameBoard.displayBoard();
        enemyGameBoard.displayBoard();
    }

    //aws
    public void battleGroundFX(AnchorPane boardPane, GameBoard board, boolean isEnemy) {
        char[][] gameBoardFX = board.getBoard();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                Rectangle cell = new Rectangle(50, 50);
                cell.setX(c * 50);
                cell.setY(r * 50);

                if(isEnemy){
                    cell.setFill(Color.BLUE);
                }else{
                    if(gameBoardFX[r][c] == 'S') {
                        cell.setFill(Color.DARKGRAY);
                    }else{
                        cell.setFill(Color.BLUE);
                    }
                }
                cell.setStroke(Color.BLACK);
                boardPane.getChildren().add(cell);
            }
        }
    }
}
