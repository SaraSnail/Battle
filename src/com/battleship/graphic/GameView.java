package com.battleship.graphic;

import com.battleship.CommunicationHandler;
import com.battleship.Game;
import com.battleship.GameBoard;

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
import java.util.Scanner;

//aws
public class GameView {//GB-18-SA, tog bort "extends applications"
    //GB-18-SA, gjorde dem static
    //aws
    private static GameBoard myGameBoard;
    private static GameBoard enemyGameBoard;
    //GB-18-SA, la till scene
    public static Scene scene;

    //aws
    //@Override
    //aws hade skrivit detta som en start metod, GB-18-SA, behövde göra om den till en scene istället
    public static Scene gameView (Stage window){
        //aws
        myGameBoard = new GameBoard(true);
        enemyGameBoard = new GameBoard(false);



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

        Image backgroundOcean = new Image("file:recourses/images/ocean.jpg");        //Akira Hojo
        BackgroundImage background = new BackgroundImage(
                backgroundOcean,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));



        AnchorPane stack = new AnchorPane();
        stack.getChildren().addAll(myLabel,enemyLabel,myGame,enemyGame);
        stack.setBackground(new Background(background));

        //GB-18-SA, gjorde att den "skapas" utanför metoden
        scene = new Scene(stack, 1150, 600);


        //GB-18-SA, kommenterade ut detta
        /*
        primaryStage.setScene(scene);
        primaryStage.setTitle("BattleShips");

        primaryStage.show();*/

        myGameBoard.displayBoard();
        enemyGameBoard.displayBoard();

        //GB-18-SA
        return scene;

    }

    //aws
    public static void battleGroundFX(AnchorPane boardPane, GameBoard board, boolean isEnemy) {
        char[][] gameBoardFX = board.getBoard();

        Image carrierImage = new Image("file:recourses/images/Hangarfartyg.png");       //PNGEGG
        Image battleShipImage = new Image("file:recourses/images/Slagskepp.png");       //PNGEGG
        Image cruiserImage = new Image("file:recourses/images/Kryssare.png");           //PNGEGG
        Image subImage = new Image("file:recourses/images/Ubåt.png");                   //PNGEGG



        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                Rectangle cell = new Rectangle(50, 50);
                cell.setX(c * 50);
                cell.setY(r * 50);

                if(isEnemy){
                    cell.setFill(Color.TRANSPARENT);            // Enemy Havet
                }else{
                    if(gameBoardFX[r][c] == 'S') {              //Skepp
                        cell.setFill(Color.DARKGRAY);
                    } else if (gameBoardFX[r][c] == 'X') {      //Miss
                        cell.setFill(Color.BLUE);
                    } else if (gameBoardFX[r][c] == '0') {      //Träff
                        cell.setFill(Color.RED);
                    } else{
                        cell.setFill(Color.TRANSPARENT);        //Havet
                    }
                }
                cell.setStroke(Color.BLACK);
                cell.setStrokeWidth(5);

                boardPane.getChildren().add(cell);
            }
        }
    }

}
