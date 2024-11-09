import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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

        Label myLabel = new Label("Min Spelplan");
        myLabel.setLayoutX(50);
        myLabel.setLayoutY(25);

        Label enemyLabel = new Label("Motståndarens Spelplan");
        enemyLabel.setLayoutX(600);
        enemyLabel.setLayoutY(25);

        Label coordinateLabel = new Label();
        coordinateLabel.setStyle("/*-fx-background-color: transparant;*/ -fx-border-color: black;");
        coordinateLabel.setVisible(false);



        battleGroundFX(myGame, myGameBoard, false, coordinateLabel);
        battleGroundFX(enemyGame, enemyGameBoard, true,coordinateLabel );

        myGame.setLayoutX(50);
        myGame.setLayoutY(50);
        enemyGame.setLayoutX(600);
        enemyGame.setLayoutY(50);

        AnchorPane stack = new AnchorPane();
        stack.getChildren().addAll(myLabel,enemyLabel,myGame,enemyGame,coordinateLabel);

        Scene scene = new Scene(stack, 1600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BattleShips");

        primaryStage.show();
        myGameBoard.displayBoard();
        enemyGameBoard.displayBoard();
    }

    //aws
    public void battleGroundFX(AnchorPane boardPane, GameBoard board, boolean isEnemy, Label cooerdinateLabel) {
        char[][] gameBoardFX = board.getBoard();

        /*Tooltip tooltip = new Tooltip();*/

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

                String coordinate = String.valueOf((char) ('A' + r)) +(c + 1);
                cell.setOnMouseEntered(e -> {
                    double centerX = cell.getX() + cell.getWidth() / 2;
                    double centerY = cell.getY() + cell.getHeight() / 2;

                    cooerdinateLabel.setText(coordinate);
                    cooerdinateLabel.setVisible(true);

                    /*double width = cooerdinateLabel.getWidth();
                    double height = cooerdinateLabel.getHeight();*/

                    cooerdinateLabel.setTranslateX(centerX - cooerdinateLabel.getWidth() / 2);
                    cooerdinateLabel.setTranslateY(centerY - cooerdinateLabel.getHeight() / 2);


                });

                cell.setOnMouseExited(e -> {cooerdinateLabel.setVisible(false);});





                boardPane.getChildren().add(cell);
            }
        }
    }
}
