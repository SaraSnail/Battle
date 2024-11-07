package com.battleship;

import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                    makeMove(player);
                    firstMove = false;
                } else {
                    makeMove(player);
                    getShotOutcome();
                    updateMaps("4b", enemyGameBoard);   //GB-26-SA. Skriver in test koordinater och vilken bord man skjuter på
                    isClientTurn = false;
                }
            } else {
                //receiveMove(player);
                isClientTurn = true;
                updateMaps("5c", myGameBoard);       //GB-26-SA.Skriver in test koordinater
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

   private void makeMove(CommunicationHandler player){
        // Metod för att slumpa fram skott - retunera kordinater
        // Metod för att skicka skottet(kordinaterna) till motståndare
    }


    private void receiveMove(CommunicationHandler player) {
        // Metod för att ta emot skott/koordinater
        // Metod för att titta på egen kartan om träff/träff & sänkt skepp/träff & game over/miss
        setShotOutcome();
        //updateMaps(coordinates);
    }

    private void setShotOutcome(){ //denna metod bör kanske i BoardGame

    }

    private void getShotOutcome(){ //denna metod bör kanske i BoardGame

    }

    //GB-26-SA
    private String updateMaps(String coordinates, GameBoard gameBoard){
        //Får string på typ 4b
        List<Character>letters = new ArrayList<Character>();
        letters.add('A');//0
        letters.add('B');//1
        letters.add('C');//2
        letters.add('D');//3
        letters.add('E');//4
        letters.add('F');//5
        letters.add('G');//6
        letters.add('H');//7
        letters.add('I');//8
        letters.add('J');//9

        int boardSize = 10;

        char number = coordinates.charAt(0);
        char[] numberArray = new char[]{number};

        //Bokstav till char Array
        char letter = coordinates.toUpperCase().charAt(1);
        //Gör om bokstaven till en siffra mellan 0-9, bokstav A till J
        int letterToInt = 0;
        for (int i = 0; i < letters.size(); i++) {
            if(letter == letters.get(i)){
                letterToInt = i;
            }
        }
        char[] letterToChar = new char [] {(char)letterToInt};


        //Vet inte om detta fungerar
        //Lägger in talen i en 2d array
        char shot [][] = {numberArray, letterToChar};

        //[0.0][0.1]
        //[1.0][1.1]

        // i = x-led
        // j = y-led

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameBoard.getBoard()[i] == shot[i]) {
                    if (gameBoard.getBoard()[j] == shot[j]) {

                        if (gameBoard.getBoard()[i][j] == 'S') {
                            System.out.println("hit");
                            return "h";
                        } else if (gameBoard.getBoard()[i][j] == ' ') {
                            System.out.println("miss");
                            return "m";
                        }
                    }
                }
                System.out.println("out of bounds");
            }
        }




        //GB-25-AA
        //Uppdatera GabeBoard-metod(coordinates)
        updateGameView(coordinates);

        //GB-26-SA
        return null;
    }

    //GB-25-AA
    private void updateGameView(String coordinates){ // denna metod kanske bör ligga i GameBoard
        Platform.runLater(() ->{
            //Uppdatera GUI/GameView
        });
    }

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
