import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.awt.*;

//aws
public class GameView extends Application {
    private GameBoard gameBoard;

    //aws
    @Override
    public void start(Stage primaryStage) throws Exception {
        gameBoard = new GameBoard();

        AnchorPane board = new AnchorPane();        // Spelplanen





        battleGroundFX(board);
        board.setLayoutX(50);
        board.setLayoutY(50);

        Rectangle test = new Rectangle(50,50);
        test.setFill(Color.HOTPINK);
        test.setStroke(Color.BLACK);
        test.setLayoutX(-50);
        test.setLayoutY(-50);
        board.getChildren().add(test);

        Scene scene = new Scene(board, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BattleShips");

        primaryStage.show();
        gameBoard.displayBoard();
    }

    //aws
    public void battleGroundFX(AnchorPane board) {
        char[][] gameBoardFX = gameBoard.getBoard();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                Rectangle cell = new Rectangle(50, 50);
                cell.setX(c * 50);
                cell.setY(r * 50);

                if(gameBoardFX[r][c] == 'S') {
                    cell.setFill(Color.DARKGRAY);
                }else{
                    cell.setFill(Color.BLUE);
                }
                cell.setStroke(Color.BLACK);
                board.getChildren().add(cell);
            }

        }
    }


}
