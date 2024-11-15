package com.battleship.graphic;

import com.battleship.CommunicationHandler;
import com.battleship.Game;
import com.battleship.GameBoard;
import com.battleship.Ship;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

//GB-14-aws
public class GameView {//GB-18-SA, tog bort "extends applications"
    //GB-18-SA, gjorde dem static
    //aws
    private static GameBoard myGameBoard;
    private static GameBoard enemyGameBoard;
    //GB-18-SA, la till scene
    public static Scene scene;

    //aws hade skrivit detta som en start metod, GB-18-SA, behövde göra om den till en scene istället

    public static Scene gameView (Stage window){
        //aws
        /*myGameBoard = new GameBoard(true);
        enemyGameBoard = new GameBoard(false);*/



        AnchorPane myGame = new AnchorPane();        // Min Spelplan
        AnchorPane enemyGame = new AnchorPane();    // Motståndare Spelplan

        Label myLabel = new Label("--------MyGameBoard-------");
        myLabel.getStyleClass().add("text-stroke");
        myLabel.setLayoutX(100);
        myLabel.setLayoutY(5);

        Label enemyLabel = new Label("------EnemyGameBoard------");
        enemyLabel.getStyleClass().add("text-stroke");
        enemyLabel.setLayoutX(850);
        enemyLabel.setLayoutY(5);

        topNumberLabel(myGame);
        topNumberLabel(enemyGame);
        sideCharLabel(myGame);
        sideCharLabel(enemyGame);

        battleGroundFX(myGame, myGameBoard, false);
        battleGroundFX(enemyGame, enemyGameBoard, true );
        gameBoardGrid(myGame, myGameBoard,false);
        gameBoardGrid(enemyGame, enemyGameBoard, true);

        myGame.setLayoutX(100);
        myGame.setLayoutY(100);
        enemyGame.setLayoutX(850);
        enemyGame.setLayoutY(100);

        Image backgroundOcean = new Image("file:recourses/images/oceanblue.png");        //PNGEGG
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
        scene = new Scene(stack, 1450, 700);

        stack.getStylesheets().add("com/battleship/graphic/BattleShip.css");

        //GB-18-SA, kommenterade ut detta
        /*
        primaryStage.setScene(scene);
        primaryStage.setTitle("BattleShips");
        primaryStage.show();*/

        myGameBoard.displayBoard();

        //GB-18-SA
        return scene;

    }

    //GB-14-aws
    public static void battleGroundFX(AnchorPane boardPane, GameBoard board, boolean isEnemy) {
        char[][] gameBoardFX = board.getBoard();
        //GB-36-AWS
        Image carrierImage = new Image("file:recourses/images/Hangarfartyg.png");       //PNGEGG
        Image battleShipImage = new Image("file:recourses/images/Slagskepp.png");       //PNGEGG
        Image cruiserImage = new Image("file:recourses/images/Kryssarenr2.png");           //PNGEGG
        Image subImage = new Image("file:recourses/images/Ubåt.png");                   //PNGEGG
        //GB-36-AWS
        Image carrierImageVertikal = new Image("file:recourses/images/Hangarfartygvertikal.png"); //PNGEGG
        Image battleShipImageVertikal = new Image("file:recourses/images/Slagskeppvertikal.png");       //PNGEGG
        Image cruiserImageVertikal = new Image("file:recourses/images/Kryssarenr2vertikal.png");           //PNGEGG
        Image subImageVertikal = new Image("file:recourses/images/Ubåtvertikal.png");                   //PNGEGG

        for (Ship ship : board.getShips()){
            List<int[]> coordinatesList = ship.getCoordinates();
            int[][] coordinates = coordinatesList.toArray(new int[0][0]);
            int r = coordinates[0][0];
            int c = coordinates[0][1];
            int shipSize = ship.getSize();
            boolean isHorizontal = coordinates.length > 1 && coordinates[0][1] != coordinates[1][1];
            //GB-36-AWS
            ImageView shipImage = getShipImage(shipSize,isHorizontal,
                                                carrierImage, carrierImageVertikal,
                                                battleShipImage, battleShipImageVertikal,
                                                cruiserImage, cruiserImageVertikal,
                                                subImage, subImageVertikal);
            if(shipImage != null){
                if(isHorizontal) {
                    shipImage.setX(c * 50);
                    shipImage.setY(r * 50 - 25);

                }else{
                    shipImage.setX(c * 50);
                    shipImage.setY(r * 50);
                }
                boardPane.getChildren().add(shipImage);
            }

        }

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                Rectangle cell = new Rectangle(50, 50);
                cell.setX(c * 50);
                cell.setY(r * 50);

                if(isEnemy){
                    if (gameBoardFX[r][c] == 'X') {      //Miss
                        cell.setFill(Color.BLUE);
                    } else if (gameBoardFX[r][c] == '0') {      //Träff
                        cell.setFill(Color.RED);
                    } else{
                        continue;
                    }

                } else{
                    if (gameBoardFX[r][c] == 'X') {      //Miss
                    cell.setFill(Color.BLUE);
                } else if (gameBoardFX[r][c] == '0') {      //Träff
                    cell.setFill(Color.RED);
                } else{
                    continue;
                }
                }
                cell.setStroke(Color.BLACK);
                cell.setStrokeWidth(5);
                boardPane.getChildren().add(cell);
            }
        }

    }
    //GB-36-AWS
    private static void gameBoardGrid(AnchorPane boardPane, GameBoard board,boolean isEnemy){
        char[][] gameBoardGrid = board.getBoard();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                Rectangle grid = new Rectangle(50, 50);
                grid.setX(c * 50);
                grid.setY(r * 50);
                grid.setFill(Color.TRANSPARENT);
                grid.setStroke(Color.BLACK);
                grid.setStrokeWidth(5);
                boardPane.getChildren().add(grid);
            }
        }
    }

    //GB-36-AWS
    private static ImageView getShipImage(int shipSize, boolean isHorizontal,
                                   Image carrierImage, Image carrierImageVertikal,
                                   Image battleShipImage, Image battleShipImageVertikal,
                                   Image cruiserImage, Image cruiserImageVertikal,
                                   Image subImage, Image subImageVertikal) {
        ImageView shipImage = null;
        if(shipSize == 5){
            shipImage = new ImageView(isHorizontal ? carrierImage : carrierImageVertikal);
        } else if (shipSize == 4) {
            shipImage = new ImageView(isHorizontal ? battleShipImage : battleShipImageVertikal);
        } else if (shipSize == 3) {
            shipImage = new ImageView(isHorizontal ? cruiserImage : cruiserImageVertikal);
        } else if (shipSize == 2) {
            shipImage = new ImageView(isHorizontal ? subImage : subImageVertikal);
        }
        if(shipImage != null){
            if(isHorizontal){
                shipImage.setFitWidth(shipSize * 50);
                shipImage.setFitHeight(75);
            } else{
                shipImage.setFitWidth(75);
                shipImage.setFitHeight(shipSize * 50);
            }
        }
        return shipImage;
    }

    //GB-36-AWS
    private static void topNumberLabel(AnchorPane boardPane){
        for (int c = 0; c < 10; c++) {
            Label numberLabel = new Label(String.valueOf(c));
            numberLabel.getStyleClass().add("text-stroke");
            numberLabel.setLayoutX(c * 50+15);
            numberLabel.setLayoutY(-50);
            boardPane.getChildren().add(numberLabel);
        }
    }
    //GB-36-AWS
    private static void sideCharLabel(AnchorPane boardPane){
        for (int r = 0; r < 10; r++) {
            Label letterLabel = new Label(String.valueOf((char)('A' + r)));
            letterLabel.getStyleClass().add("text-stroke");
            letterLabel.setLayoutX(-35);
            letterLabel.setLayoutY(r * 50);
            boardPane.getChildren().add(letterLabel);
        }
    }

}
