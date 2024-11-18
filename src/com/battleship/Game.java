package com.battleship;

import com.battleship.graphic.AlertBox;
import com.battleship.graphic.LoginView;
import javafx.application.Platform;

import static com.battleship.Coordinates.getValueAtCoordinates;

public class Game {

    //GB-23-AA //GB-25-AA //GB-25-AA
    private CommunicationHandler player;
    private boolean isClient;
    private GameBoard myGameBoard;
    private GameBoard enemyGameBoard;
    private boolean iLose = false;
    private String lastShot;
    private String lastHitShot;
    private boolean sunk = true;

    //GB-43-AA
    private boolean gameOver;
    private boolean firstMove;


    //private boolean iLose; - använt för testning

    //GB-18-SA, så man kan nå samma Stage i updateGameView
    private LoginView loginView;

    //GB-26-SA
    //private char valueAtCoordinates;
    //private int row;
    //private int col;
    private String lastMove;

    //GB-13-AA //GB-23-AA //GB-25-AA
    public Game(CommunicationHandler player, boolean isClient, LoginView loginView) {
        this.player = player;
        this.isClient = isClient;

        //GB-18-SA
        this.loginView = loginView;

        //GB-43-AA
        if (isClient) {
            firstMove = true;
        } else {
            firstMove = false;
        }
    }

    //GB-42-SA, la att boards skapas separat från startGame
    public void createBoards() {
        //GB-13-AA //GB-25-AA //GB-30-AA
        myGameBoard = new GameBoard(true);
        enemyGameBoard = new GameBoard(false);

        //GB-42-SA, testar lite
        /*
        System.out.println("My gameBoard");
        myGameBoard.displayBoard();
        System.out.println("Enemy gameBoard");
        enemyGameBoard.displayBoard();*/
    }

    private boolean waitForReady() {
        String readySignal = player.handleIncomingMessages();
        if (readySignal.equals("ready")) {
            return true;
        }
        return false;
    }

    private void sendReady() {
        player.handleSendingMessages("ready");
    }


    //GB-13-AA //GB-25-AA //GB-30-AA //GB-43-AA
    public void startGame() {
        //createBoards();

        if (!isClient) {
            waitForReady();
        } else {
            sendReady();
        }

        System.out.println("Game started!");


        int counter = 1;

        //Denna loop körs till game-over.
        while (!gameOver) {
            System.out.println("I while-game-loopen. isClientsTurn: " + isClient + ". Varv: " + counter);
            if (isClient) {
                System.out.println("klientens drag - while-loopen");
                if (firstMove) {
                    handleClientsTurn(true);
                    firstMove = false;
                } else {
                    handleClientsTurn(false);
                }
            } else {
                System.out.println("Serverns drag - while-loopen");
                handlePlayersTurn();
            }
            waitOneSec();
            counter++;
        }


        //createBoards();

       /* if (!isClientTurn){
            waitForReady();
        } else {
            sendReady();
        }

        waitOneSec();
        gameLoop();

        if (!isClientTurn) { // den här delen kanske kan tas bort sedan
            System.out.println("Waiting for client to connect and make it's fist move");
        }*/

        //new Thread(this::gameLoop).start(); //startar spel-loopen asynkront - tror detta behövs för att inte stoppa upp flödet (AA).
    }

    //GB-43-AA
    private void handleClientsTurn(boolean firstMove) {
        System.out.println("Clients turn");
        if (firstMove) {
            System.out.println("I first move");
            firstMove(player);

        } else {
            handlePlayersTurn();
        }
    }

    //GB-43-AA
    private void handlePlayersTurn() {
        System.out.println("I handlePlayersTurn");

        String enemymove = player.handleIncomingMessages();
        System.out.println("mottaget drag i handlePlayersTurn: " + enemymove);

        //kolla game over
        gameOver = checkIfGameOver(enemymove);
        System.out.println("game over: " + gameOver);

        //skicka drag
        makeMove(player, false, enemymove);

    }

    //GB-25-AA //GB-35-AA
    /*private void gameLoop(){
        boolean gameOver = false;
        boolean firstMove = false;
        String enemymove;

        if (isClient){
            firstMove = true;
        }

        int counter = 1;
        while (!gameOver) {

            System.out.println("Whale-loopen startar. isClientsTurn: " + isClient + ". varv: " + counter);
            if (isClient) {
                if (firstMove){
                    firstMove(player);
                    firstMove = false;
                } else {
                    System.out.println("I if-satsen, clientTurn");
                    enemymove = player.handleIncomingMessages();
                    gameOver = checkIfGameOver(enemymove);
                    makeMove(player, false, enemymove);
                    isClient = false;
                    if (gameOver){
                        break;
                    }
                }
            } else {
                System.out.println("I if-satsen, !clientTurn");
                enemymove = player.handleIncomingMessages();
                gameOver = checkIfGameOver(enemymove);
                makeMove(player, false, enemymove);
                isClient = true;
                if (gameOver){
                    break;
                }
            }
            System.out.println("Utanför if-satsen");
            enemymove = player.handleIncomingMessages();
            gameOver = checkIfGameOver(enemymove); //GB-19-AA ifall innevarande spelare skickar game over. Spelare vinner.
            waitOneSec();
            counter ++;
        }
        System.out.println("Game over!");
    }*/

    //GB-31-AA
    private void waitOneSec() {
        try {
            Thread.sleep(1000); //vänta 1 sek
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //GB-43-AA
    private void firstMove(CommunicationHandler player) {
        String myMove = "shot "; //sträng att bygga på till den färdiga sträng som skickas till motspelaren
        String myShotCoordinates = ""; //sträng med tex "2g" från någon av shoot-metoderna
        String enemyMove = ""; //Sträng från motspelaren tex "h shot 3c"
        String enemyHitOrMiss = ""; //sträng från getShotOutCome - "h", "m", "s" eller "game over"

        myShotCoordinates = Shoot.randomShot(enemyGameBoard);
        lastShot = myShotCoordinates;
        myMove = "i " + myMove + myShotCoordinates;
        System.out.println("Sträng till motståndaren: " + myMove);

        player.getWriter().println(myMove);
        lastShot = myShotCoordinates;
        //updateMaps(myMove,enemyGameBoard);
    }

    //GB-19-AA //GB-45-AA
    private void makeMove(CommunicationHandler player, boolean firstMove, String enemyMove) {

        String myMove = "shot "; //sträng att bygga på till den färdiga sträng som skickas till motspelaren
        String myShotCoordinates = ""; //sträng med tex "2g" från någon av shoot-metoderna
        //String enemyMove = ""; //Sträng från motspelaren tex "h shot 3c"
        String enemyHitOrMiss = ""; //sträng från getShotOutCome - "h", "m", "s" eller "game over"


        System.out.println("inkommen Sträng i makeMove: " + enemyMove);
        char myShotHitOrMiss = setShotOutcome(enemyMove);

        updateMyMap(enemyMove);

        myShotCoordinates = selectShot(myShotHitOrMiss);

        //updateMaps(enemyMove, myGameBoard);
        enemyHitOrMiss = getShotOutcome(enemyMove, myGameBoard);


        if (enemyHitOrMiss.equalsIgnoreCase("Game Over")) {
            iLose = true;    //Ändra till iLoose
            System.out.println("Sträng till motståndaren vid GAME OVER: " + myMove);
            player.getWriter().println(enemyHitOrMiss.toLowerCase());
        } else {
            lastMove = myShotHitOrMiss +" "+myMove+ lastShot;
            updateEnemyMap(lastMove);

            myMove = enemyHitOrMiss + " " + myMove + myShotCoordinates;
            System.out.println("Sträng till motståndaren i makeMove: " + myMove);
            player.getWriter().println(myMove);

        }

        lastShot = myShotCoordinates; //sparar skottet i global Sträng som kan användas av andra metoder i Game.

    }

    //Gb-45-AA
    private String selectShot(char myShotHitOrMiss) {
        String myShotCoordinates = ""; //sträng med tex "2g" från någon av shoot-metoderna


        if (myShotHitOrMiss == 'h') {
            //GB-43-AA kommenterade ut hitSot
            System.out.println("Föregående skott var h. lagrat i lastShot är: " + lastShot);
            //Shoot.setLastHit(lastShot); //sträng med tex "5b"
            lastHitShot = lastShot; //string tex 5b
            myShotCoordinates = Shoot.hitShot(enemyGameBoard, lastShot);
            System.out.println("Kordinater från hitShot: " + myShotHitOrMiss);
            sunk = false;
            myShotCoordinates = Shoot.randomShot(enemyGameBoard);

        } else if (myShotHitOrMiss == 'm' && !sunk) {
            System.out.println("Föregående skott var miss men skepp ej sänkt");
            //GB-43-AA kommenterade ut hitSot
            myShotCoordinates = Shoot.hitShot(enemyGameBoard, lastHitShot);
            System.out.println("Kordinater från hitShot: " + myShotHitOrMiss);
            //myShotCoordinates = Shoot.randomShot(enemyGameBoard);

        } else if (myShotHitOrMiss == 's') {
            System.out.println("Föregående skott sänkte skepp");
            sunk = true;
            myShotCoordinates = Shoot.randomShot(enemyGameBoard);
            System.out.println("Kordinater från randomShot: " + myShotCoordinates);
     /*  } else if (myShotHitOrMiss == 'm' && lastHitShot == null ){
           System.out.println("Föregående skott var miss och senaste träff var 'null'");
           myShotCoordinates = Shoot.randomShot(enemyGameBoard);
           System.out.println("Kordinater från randomShot: " + myShotCoordinates);*/
        } else {
            System.out.println("Föregående skott var miss");
            myShotCoordinates = Shoot.randomShot(enemyGameBoard);
            System.out.println("Kordinater från randomShot: " + myShotCoordinates);
        }

        //lastShot = myShotCoordinates; //sparar skottet i global Sträng som kan användas av andra metoder i Game.
        //System.out.println("LastShot (skottet som skickas till motståndaren): " + lastShot);

        return myShotCoordinates;
   }


    //GB-21-DE
    private char setShotOutcome(String enemyMove){ //denna metod bör kanske i BoardGame
        //return 'x'; //Tillfällig char till metoden är klar.
        char resultCode = enemyMove.charAt(0);
        if (resultCode != 'i' && resultCode != 'h' && resultCode != 'm' && resultCode != 's') {
            throw new IllegalArgumentException(" Ogiltig input" + resultCode);
        }
        System.out.println("result i setShotOutCome: " + resultCode);

        return resultCode;
    }
    // GB-lapp ej funnen-D.E

    private String getShotOutcome(String enemyMove, GameBoard myGameBoard) { //denna metod bör kanske i BoardGame
        //Tillfällig sträng tills metoden är klar.
        //Läser enemyMove för att få rad och kolumnindex på brädet
        Coordinates shotCoordinates = Coordinates.getValueAtCoordinates(enemyMove);
        int row = shotCoordinates.getRow();
        int col = shotCoordinates.getCol();
        char[][] myBoard = myGameBoard.getBoard();

        /*
        if (myBoard[row][col] == ' ') { // Om det är tomt är det första skottet. returnerar "i"
            return "i";

        }*/
        // spara värde i träffad ruta för att se om den är en del av ett skepp
        char outcome = myBoard[row][col];
        // uppdatera brädet vid träffens position ,x för träff eller 0 för miss

        //myBoard[row][col] = outcome == 'S' ? 'X' : '0';//debug-SA tog bort denna, den ändra värdet på koordinaten

        // kollar om träffen var en träff på ett skepp. s betyder träff på skepp

        if (outcome == 'S') {
            for (Ship ship : myGameBoard.getShips()) { //Loopa genom varje skepp,se om något av dom innehåller koordinaterna

                if (ship.getCoordinates().stream().anyMatch(coord -> coord[0] == row && coord[1] == col)) {
                    ship.setNumberOfHits(ship.getNumberOfHits() + 1); // Använder stream API för att kolla
                    // om skeppets koordinater innehåller det träffade området,
                    // Träff

                    // Är skeppet sänkt?
                    if (ship.getNumberOfHits() == ship.getSize()) {
                        ship.setSunk(true);
                        // Är alla skepp sänkta?
                        if (myGameBoard.getShips().stream().allMatch(Ship::isSunk)) {
                            return "game over";
                        } // Om bara detta skepp är sänkt men fler finns kvar returneras "s"
                        return "s"; // Träff med sänkt skepp
                    }
                    return "h"; // Endast träff
                }

            }
        }
        return "m"; // miss

    }
    //GB-26-SA
    private void updateMyMap(String message){

        //Får in typ "i shot 4b" i message

        //[0.0][0.1]
        //[1.0][1.1]

        // i = x-led = row = letter
        // j = y-led = column = number

        try{
            //Skickar in tex "4b" och spelplanen
            //Har en klass som i dens metod som tar koordinaterna från meddelandet och får fram till row och column i siffror
            //Skickar in gameBoard får att så storleken på spelplanen så skottet inte är utanför
            Coordinates coords = getValueAtCoordinates(message);
            //Får tillbaka ett Coordinates objekt där jag kan hämta ut dens row och col

            //Sätter in row och column från klassen i variablerna row och column
            int row = coords.getRow();
            int col = coords.getCol();

            System.out.println("Value at: "+message+" = [" + myGameBoard.getBoard()[row][col]+"]");

            //Kollar om värdet var ett S eller blankt och byter sen ut det till antigen 0 eller X
            if(myGameBoard.getBoard()[row][col] == 'S'){
                System.out.println("A ship");
                myGameBoard.getBoard()[row][col] = '0';

            } else if (myGameBoard.getBoard()[row][col] == ' ') {
                System.out.println("No ship");
                myGameBoard.getBoard()[row][col] = 'X';

            }

            myGameBoard.displayBoard();


            //GB-25-AA
            //Uppdatera GameBoard-metod(coordinates)
            //GB-18-SA
            //updateGameView(row, col, gameBoard);//GB-18-SA, behöver inte skicka med row och col

        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }



    }
    //GB-18-SA.part2
    private void updateEnemyMap(String message){
        //Får in m/h/s eller null
        System.out.println("I shot last at: " + message);
        System.out.println("It was a: " + message.charAt(0));
        if(message.contains("null")){
            return;
        }
        //Samma som i updateMyMap
        Coordinates coords = getValueAtCoordinates(message);
        int row = coords.getRow();
        int col = coords.getCol();

        //Gör inget om det är i, första skottet
        if(message.charAt(0) == 'i'){

        }else if(message.charAt(0) == 'h'){
            enemyGameBoard.getBoard()[row][col] = '0';

        }else if (message.charAt(0) == 'm'){
            enemyGameBoard.getBoard()[row][col] = 'X';

        } else if (message.charAt(0) == 's') {
            enemyGameBoard.getBoard()[row][col] = '0';
        }

        enemyGameBoard.displayBoard();


    }


    //GB-25-AA
    private void updateGameView(int row, int col, GameBoard gameBoard){ // denna metod kanske bör ligga i GameBoard
        Platform.runLater(() ->{

            //GB-18-SA
            //Medskickad loginView så man kan nå samma fönster de andra scenerna har
            //loginView.window.setScene(GameView.gameView(loginView.window, myGameBoard,enemyGameBoard));
            //Uppdatera GUI/GameView
            //GameView.updateMapFX(row, col, gameBoard);
        });
    }

    //GB-25-AA
    private boolean checkIfGameOver(String message){
        //GB-33-SA
        /*
        String[] isGameOverArray = message.split(" ");//Delar upp i array så jag kan få bort "h shot"
        //Samlar om de två sista arrays i isGameOver
        String isGameOver = isGameOverArray[isGameOverArray.length-2] + " " + isGameOverArray[isGameOverArray.length-1];
        //Kan använda String message rakt av om jag bara får tillbaka "game over"
        */

        //GB-33-SA
        //String message = """";

       /* try {
            message = String.valueOf(player.getReader().readLine());//Samlar texten från players reader
            System.out.println("mottaget i checkIfGameOver: " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/



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

                updateEnemyMap(lastMove);//Uppdaterar GUI också
                // Får game over från motståndaren och uppdaterar deras karta så sista skottet på dem syns
                //lastShot fixa

                //GB-35-AA (Alertbox och .exit())
                Platform.runLater(() -> {
                    AlertBox.display("Game Over", "GAME OVER\nYOU WIN!\n\n When you klick OK you vill exit the application ");
                    Platform.exit(); //Stänger ner hela applikationen när spelaren trycker OK!
                });

                return true;

            } else {
                System.out.println("Not game over");
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

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean client) {
        isClient = client;
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



