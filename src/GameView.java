import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        AnchorPane enemyGame = new AnchorPane();    // Motståndare Spelplan

        Label myLabel = new Label("My gameboard");
        myLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 32px; -fx-text-fill: BLACK;");
        myLabel.setLayoutX(50);
        myLabel.setLayoutY(5);


        Label enemyLabel = new Label("Enemy gameboard");
        enemyLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 32px; -fx-text-fill: BLACK;");
        enemyLabel.setLayoutX(600);
        enemyLabel.setLayoutY(5);

        battleGroundFX(myGame, myGameBoard, false);
        battleGroundFX(enemyGame, enemyGameBoard, true );

        myGame.setLayoutX(50);
        myGame.setLayoutY(50);
        enemyGame.setLayoutX(600);
        enemyGame.setLayoutY(50);

        Image backgroundOcean = new Image("file:src/ocean.jpg");        //Akira Hojo
        BackgroundImage background = new BackgroundImage(
                backgroundOcean,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));



        AnchorPane stack = new AnchorPane();
        stack.getChildren().addAll(myLabel,enemyLabel,myGame,enemyGame);
        stack.setBackground(new Background(background));

        Scene scene = new Scene(stack, 1150, 600);
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
                    cell.setFill(Color.TRANSPARENT);
                }else{
                    if(gameBoardFX[r][c] == 'S') {
                        cell.setFill(Color.DARKGRAY);
                    }else{
                        cell.setFill(Color.TRANSPARENT);
                    }
                }
                cell.setStroke(Color.BLACK);
                cell.setStrokeWidth(5);

                boardPane.getChildren().add(cell);
            }
        }
    }
}
