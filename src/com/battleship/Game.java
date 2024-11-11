package com.battleship;

import javafx.application.Platform;

import java.io.IOException;

public class Game {

    /*
    // GB-25-AA
    På inloggningssidan när spelaren loggar in som server eller klient. Lägg då till en boolean med true/false beroende
    på om det är en klient eller en server som skapats. Skicka med boolean tillsammans med CommunicationHandler-objektet
    när game-objekt skapas.
    */


    //GB-23-AA //GB-25-AA //GB-25-AA
    private CommunicationHandler player;
    private boolean isClientTurn;
    private GameBoard myGameBoard;
    private GameBoard enemyGameBoard;

    //GB-13-AA //GB-23-AA //GB-25-AA
    public Game(CommunicationHandler player, boolean isClient) {
        this.player = player;
        this.isClientTurn = isClient;
    }

    //GB-13-AA //GB-25-AA //GB-30-AA
    public void startGame() {

        myGameBoard = new GameBoard(true);
        enemyGameBoard = new GameBoard(false);

        waitForStart();

        new Thread(this::gameLoop).start(); //startar spel-loopen asynkront - tror detta behövs för att inte stoppa upp flödet.

    }
    //GB-25-AA
    private void gameLoop(){
        boolean gameOver = false;
        boolean firstMove = true;
        while (!gameOver) {
            if (isClientTurn) {
                if (firstMove){
                    makeMove(player, true);
                    firstMove = false;
                } else {
                    makeMove(player, false);
                    isClientTurn = false;
                }
            } else {
                makeMove(player, false);
                isClientTurn = true;
            }
            try {
                Thread.sleep(3000);          //Väntar 3 sekunder mellan varje drag
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gameOver = checkIfGameOver();
        }
        System.out.println("Game over!");
    }

    private void waitForStart(){
        try {
            Thread.sleep(5000);  //Väntar 5 sek innan spelet drar igång
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (!isClientTurn) { // den här delen kanske kan tas bort sedan
            System.out.println("Waiting for client to connect and make it's fist move");
        }
    }

    //GB-19-AA
   private void makeMove(CommunicationHandler player, boolean firstMove){
        String myMove = "shot ";
        String myShotCoordinates = "";
        String enemyMove = "";
        if (firstMove){
            myShotCoordinates = //randomShot - statisk metod? //metod som genererarSkot och retunerar kordinater.
            myMove = "i " + myMove + myShotCoordinates;
            System.out.println("Sträng till motståndaren: " + myMove);
            player.getWriter().println(myMove);
        } else {
            try {
                enemyMove = player.getReader().readLine();
            } catch (IOException e) {
                System.out.println("Could not receive move from other player");
                throw new RuntimeException(e);
            }
            //enemyGameBoard.setShotOutcome(enemyMove.charAt(0)); //ShotOutcome finns ej ännu
            //update map
            String enemyShotCoordinates = enemyMove.substring(enemyMove.length() -2);
            //char hitOrMiss = ;//Gameboard.evaluateShotFromEnemy() - eller vad metoden nu kan tänkas få för namn
            myShotCoordinates = ""; //metod som genererarSkot och retunerar kordinater från coordinates.
            // myMove = hitOrMiss + " " + myMove + myShotCoordinates;
            //updateMaps(enemyShotCoordinates, hitOrMiss, myShotCoordinates); //Updatera GUI alt båda kartorna? Hur tänker vi?
            System.out.println("Sträng till motståndaren: " + myMove);
            player.getWriter().println(myMove);
        }
    }


    //Recive move ska inte användas då endast en sträng ska skickas. Eventuellt kan man använda den inom MakeMove();
/*    private void receiveMove(CommunicationHandler player) {
        // Metod för att ta emot skott/koordinater
        // Metod för att titta på egen kartan om träff/träff & sänkt skepp/träff & game over/miss
        setShotOutcome();
        //updateMaps(coordinates);
    }*/

    private void setShotOutcome(){ //denna metod bör kanske i BoardGame

    }

    private void getShotOutcome(){ //denna metod bör kanske i BoardGame

    }

/*    private void updateMaps(String coordinates){
        //Uppdatera GabeBoard-metod(coordinates)
        updateGameView(coordinates);
    }*/

/*    //GB-25-AA
    private void updateGameView(String coordinates){ // denna metod kanske bör ligga i GameBoard
        Platform.runLater(() ->{
            //Uppdatera GUI/GameView
        });
    }*/

    //GB-25-AA
    private boolean checkIfGameOver(){
        boolean gameOver;
        try {
            if (player.getReader().readLine().equals("game over")) {
                gameOver = true;
            } else {
                gameOver = false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //"protokoll" för att se om spelet är slut / uppdatera GUI/ GameView med "Game Over" - Vinnare är:
        return gameOver;
    }

    public CommunicationHandler getPlayer() {
        return player;
    }

    public void setPlayer(CommunicationHandler player) {
        this.player = player;
    }

    public boolean isClientTurn() {
        return isClientTurn;
    }

    public void setClientTurn(boolean clientTurn) {
        isClientTurn = clientTurn;
    }

    public GameBoard getMyGameBoard() {
        return myGameBoard;
    }

    public void setMyGameBoard(GameBoard myGameBoard) {
        this.myGameBoard = myGameBoard;
    }

    public GameBoard getEnemyGameBoard() {
        return enemyGameBoard;
    }

    public void setEnemyGameBoard(GameBoard enemyGameBoard) {
        this.enemyGameBoard = enemyGameBoard;
    }
}
