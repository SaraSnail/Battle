package com.battleship;

import com.battleship.graphic.AlertBox;
import com.battleship.graphic.GameView;
import com.battleship.graphic.LoginView;
import javafx.application.Platform;
import java.io.IOException;

import static com.battleship.Coordinates.getValueAtCoordinates;

public class Game {

    //GB-23-AA //GB-25-AA //GB-25-AA
    private CommunicationHandler player;
    private boolean isClientTurn;
    private GameBoard myGameBoard;
    private GameBoard enemyGameBoard;
    private boolean iLose = false;
    private String lastShot;
    private String lastHitShot;
    private boolean sunk;

    //private boolean iLose; - använt för testning

    //GB-18-SA, så man kan nå samma Stage i updateGameView
    private LoginView loginView;

    //GB-26-SA
    private char valueAtCoordinates;
    private int row;
    private int col;

    //GB-13-AA //GB-23-AA //GB-25-AA
    public Game(CommunicationHandler player, boolean isClient, LoginView loginView) {
        this.player = player;
        this.isClientTurn = isClient;

        //GB-18-SA
        this.loginView = loginView;
    }

    //GB-13-AA //GB-25-AA //GB-30-AA
    public void startGame() {

        myGameBoard = new GameBoard(true);
        enemyGameBoard = new GameBoard(false);

        waitOneSec();

        if (!isClientTurn) { // den här delen kanske kan tas bort sedan
            System.out.println("Waiting for client to connect and make it's fist move");
        }

        new Thread(this::gameLoop).start(); //startar spel-loopen asynkront - tror detta behövs för att inte stoppa upp flödet.
    }

    //GB-25-AA //GB-35-AA
    private void gameLoop(){
        boolean gameOver = false;
        boolean firstMove = true;

        while (!gameOver) {
            if (isClientTurn) {
                if (firstMove){
                    makeMove(player,true);
                    firstMove = false;
                } else {
                    gameOver = checkIfGameOver();
                    makeMove(player, false);
                    isClientTurn = false;
                    if (gameOver){
                        break;
                    }
                }
            } else {
                gameOver = checkIfGameOver();
                makeMove(player, false);
                isClientTurn = true;
                if (gameOver){
                    break;
                }
            }
            gameOver = checkIfGameOver(); //GB-19-AA ifall innevarande spelare skickar game over. Spelare vinner.
            waitOneSec();
        }
        System.out.println("Game over!");
    }

    //GB-31-AA
    private void waitOneSec(){
        try {
            Thread.sleep(1000); //vänta 1 sek
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //GB-19-AA
   private void makeMove(CommunicationHandler player, boolean firstMove){
        String myMove = "shot "; //sträng att bygga på till den färdiga sträng som skickas till motspelaren
        String myShotCoordinates = ""; //sträng med tex "2g" från någon av shoot-metoderna
        String enemyMove = ""; //Sträng från motspelaren tex "h shot 3c"
        String enemyHitOrMiss = ""; //sträng från getShotOutCome - "h", "m", "s" eller "game over"

        if (firstMove){
            myShotCoordinates = Shoot.randomShot(enemyGameBoard);
            myMove = "i " + myMove + myShotCoordinates;
            System.out.println("Sträng till motståndaren: " + myMove);
            updateMaps(myShotCoordinates,enemyGameBoard);
            player.getWriter().println(myMove);
        } else {
            try {
                enemyMove = player.getReader().readLine();  //Tar emot sträng från mottagaren
            } catch (IOException e) {
                System.out.println("Could not receive move from other player");
                throw new RuntimeException(e);
            }
            updateMaps(enemyMove, myGameBoard);
            char myShotHitOrMiss = setShotOutcome(enemyMove);

            if (myShotHitOrMiss == 'h') {
                myShotCoordinates = Shoot.hitShot(enemyGameBoard);
                Shoot.setLastHit(lastShot); //sträng med tex "5b"
                lastHitShot = myShotCoordinates;
                sunk = false;

            } else if (myShotHitOrMiss == 'm' && !sunk){
                   myShotCoordinates = Shoot.hitShot(enemyGameBoard);

            } else if (myShotHitOrMiss == 's'){
                sunk = true;
                myShotCoordinates = Shoot.randomShot(enemyGameBoard);
            } else {
                myShotCoordinates = Shoot.randomShot(enemyGameBoard);
            }

            lastShot = myShotCoordinates; //sparar skottet i global Sträng som kan användas av andra metoder i Game.

            enemyHitOrMiss = getShotOutcome(enemyMove, myGameBoard);

            if (enemyHitOrMiss.equalsIgnoreCase("Game Over")){
                iLose = true;    //Ändra till iLoose
                System.out.println("Sträng till motståndaren: " + myMove);
                player.getWriter().println(enemyHitOrMiss.toLowerCase());
            } else {
                myMove = enemyHitOrMiss + " " + myMove + myShotCoordinates;
                System.out.println("Sträng till motståndaren: " + myMove);
                player.getWriter().println(myMove);
                updateMaps(myShotCoordinates, enemyGameBoard);
            }
        }
    }


    private char setShotOutcome(String enemyMove){ //denna metod bör kanske i BoardGame
        return 'x'; //Tillfällig char till metoden är klar.
    }

    private String getShotOutcome(String enemyMove, GameBoard myGameBoard){ //denna metod bör kanske i BoardGame
        return "Tillfällig sträng"; //Tillfällig sträng tills metoden är klar.
    }

    //GB-26-SA, ändrar för test till public
    public void updateMaps(String message, GameBoard gameBoard){

        //Får in typ "i shot 4b" i message

        //[0.0][0.1]
        //[1.0][1.1]

        // i = x-led = row = letter
        // j = y-led = column = number

        try{
            //Skickar in tex "4b" och spelplanen
            //Har en klass som i dens metod som tar koordinaterna från meddelandet och får fram till row och column som går att få ut vart man är eller värdet på koordinaten
            Coordinates coords = getValueAtCoordinates(message, gameBoard.getBoard());
            //Coordinates constructor ska innehålla row och col, delar message och får tillbaka row och column

            //Sätter in row och column från klassen i variablerna row och column
            row = coords.getRow();
            col = coords.getCol();

            //Samlar värdet på koordinaten på spelplanen
            valueAtCoordinates = gameBoard.getBoard()[row][col];
            //System.out.println("Value at ["+coordinates+"]: ["+valueAtCoordinates+"]");

        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }

        //Kollar om värdet var ett S eller blankt och byter sen ut det till antigen 0 eller X
        //Kan ta bort sout senare, finns där för att se att allting fungerar
        if(valueAtCoordinates == 'S'){
            System.out.println("A ship");
            gameBoard.getBoard()[col][row] = '0';

        } else if (valueAtCoordinates == ' ') {
            System.out.println("No ship");
            gameBoard.getBoard()[col][row] = 'X';

        }
        gameBoard.displayBoard();


        //GB-25-AA
        //Uppdatera GameBoard-metod(coordinates)
        //GB-18-SA
        updateGameView();//GB-18-SA, behöver inte skicka med row och col

    }


    //GB-25-AA
    private void updateGameView(){ // denna metod kanske bör ligga i GameBoard
        Platform.runLater(() ->{

            //GB-18-SA
            //Medskickad loginView så man kan nå samma fönster de andra scenerna har
            //loginView.window.setScene(GameView.gameView(loginView.window));
            //Uppdatera GUI/GameView
        });
    }

    //GB-25-AA
    private boolean checkIfGameOver(){
        //GB-33-SA
        /*
        String[] isGameOverArray = message.split(" ");//Delar upp i array så jag kan få bort "h shot"
        //Samlar om de två sista arrays i isGameOver
        String isGameOver = isGameOverArray[isGameOverArray.length-2] + " " + isGameOverArray[isGameOverArray.length-1];
        //Kan använda String message rakt av om jag bara får tillbaka "game over"
        */

        //GB-33-SA
        String message = " ";
        try {
            message = String.valueOf(player.getReader().readLine());//Samlar texten från players reader
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //GB-35-AA (Alertbox och .exit)
        if (iLose){ // iLose kommer fungera när makeMove är mergeat!
            Platform.runLater(() ->{
                AlertBox.display("Game Over", "GAME OVER\nYOU LOSE!\n\n When you klick OK you will exit the application ");
                Platform.exit(); //Stänger ner hela applikationen när spelaren trycker OK!
            });
            return true;
        } else {
            //GB-33-SA
            if (message.equalsIgnoreCase("game over")) {

                updateMaps(lastShot, enemyGameBoard);//Uppdaterar GUI också
                // Får game over från motståndaren och uppdaterar deras karta så sista skottet på dem syns
                //lastShot fixa

                //GB-35-AA (Alertbox och .exit())
                Platform.runLater(() -> {
                    AlertBox.display("Game Over", "GAME OVER\nYOU WIN!\n\n When you klick OK you vill exit the application ");
                    Platform.exit(); //Stänger ner hela applikationen när spelaren trycker OK!
                });

                return true;

            } else {

                return false;
            }
        }
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
