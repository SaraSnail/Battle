package com.battleship;

import com.battleship.graphic.AlertBox;
import com.battleship.graphic.GameView;
import javafx.application.Platform;

import static com.battleship.Coordinates.getValueAtCoordinates;
import static com.battleship.graphic.GameView.enemyGame;
import static com.battleship.graphic.GameView.myGame;

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

    //GB-47-AA
    private double delay;

    //SA
    private String lastMove;

    //GB-13-AA //GB-23-AA //GB-25-AA
    public Game(CommunicationHandler player, boolean isClient) {
        this.player = player;
        this.isClient = isClient;

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
    }


    //GB-13-AA //GB-25-AA //GB-30-AA //GB-43-AA
    public void startGame() {

        System.out.println("Game started!");
        int counter = 1;

        //Denna loop körs till game-over.
        while (!gameOver) {
            System.out.println("Varv: " + counter);
            if (isClient) {
                if (firstMove) {
                    handleClientsTurn(true);
                    firstMove = false;
                } else {
                    handleClientsTurn(false);
                }
            } else {
                handlePlayersTurn();
            }
            counter++;
        }
        //GB-45-AA
        System.out.println("Game Over! (game-loopen) avslutad");
        try {
            player.close();
            System.out.println("Socket closed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //GB-43-AA
    private void handleClientsTurn(boolean firstMove) {
        if (firstMove) {
            System.out.println("I first move");
            firstMove(player);

        } else {
            handlePlayersTurn();
        }
    }

    //GB-43-AA
    private void handlePlayersTurn() {
        String enemymove = player.handleIncomingMessages();
        //kolla game over
        gameOver = checkIfGameOver(enemymove);//GB-19-AA ifall innevarande spelare skickar game over. Spelare vinner.
        if (gameOver) {
            return;
        } else {
            //skicka drag
            makeMove(player, false, enemymove);
        }
    }


    //GB-31-AA //GB-47-AA (inport från view)
    private void delayInSec() {
        double millisecond = delay * 1000;
        long milisec = (long) millisecond;
        try {
            Thread.sleep(milisec);
            //GB-31-AA
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //GB-43-AA
    private void firstMove(CommunicationHandler player) {
        String myMove = "shot "; //sträng att bygga på till den färdiga sträng som skickas till motspelaren
        String myShotCoordinates = ""; //sträng med tex "2g" från någon av shoot-metoderna

        myShotCoordinates = Shoot.randomShot(enemyGameBoard);
        myMove = "i " + myMove + myShotCoordinates;

        player.handleSendingMessages(myMove);
        lastShot = myShotCoordinates;
    }

    //GB-19-AA //GB-45-AA
    private void makeMove(CommunicationHandler player, boolean firstMove, String enemyMove) {

        String myMove = "shot "; //sträng att bygga på till den färdiga sträng som skickas till motspelaren
        String myShotCoordinates = ""; //sträng med tex "2g" från någon av shoot-metoderna
        String enemyHitOrMiss = ""; //sträng från getShotOutCome - "h", "m", "s" eller "game over"


        char myShotHitOrMiss = setShotOutcome(enemyMove);
        updateMyMap(enemyMove);
        delayInSec();
        Platform.runLater(() -> {
            GameView.updateMyGameView(myGameBoard, myGame);
        });

        myShotCoordinates = selectShot(myShotHitOrMiss);

        enemyHitOrMiss = getShotOutcome(enemyMove, myGameBoard);


        if (enemyHitOrMiss.equalsIgnoreCase("Game Over")) {
            iLose = true;    //Ändra till iLoose
            System.out.println("Sträng till motståndaren vid GAME OVER if - i makeMove): " + enemyHitOrMiss);
            player.handleSendingMessages(enemyHitOrMiss);
            gameOver = checkIfGameOver(enemyHitOrMiss);
        } else {
            //SA
            lastMove = myShotHitOrMiss + " " + myMove + lastShot;
            updateEnemyMap(lastMove);
            //AA
            delayInSec();
            Platform.runLater(() -> {
                GameView.updateEnemyGameView(enemyGameBoard, enemyGame);
            });

            //AA
            myMove = enemyHitOrMiss + " " + myMove + myShotCoordinates;
            player.handleSendingMessages(myMove);

        }

        lastShot = myShotCoordinates; //sparar skottet i global Sträng som kan användas av andra metoder i Game.
    }

        //Gb-45-AA
        private String selectShot (char myShotHitOrMiss){
            String myShotCoordinates = ""; //sträng med tex "2g" från någon av shoot-metoderna


            if (myShotHitOrMiss == 'h') {
                //GB-43-AA kommenterade ut hitSot
                lastHitShot = lastShot; //string tex 5b
                myShotCoordinates = Shoot.hitShot(enemyGameBoard, lastShot);
                sunk = false;

            } else if (myShotHitOrMiss == 'm' && !sunk) {
                //GB-43-AA kommenterade ut hitSot
                myShotCoordinates = Shoot.hitShot(enemyGameBoard, lastHitShot);

            } else if (myShotHitOrMiss == 's') {
                sunk = true;
                myShotCoordinates = Shoot.randomShot(enemyGameBoard);
            } else {
                myShotCoordinates = Shoot.randomShot(enemyGameBoard);
            }

            return myShotCoordinates;
        }


        //GB-21-DE
        private char setShotOutcome (String enemyMove){ //denna metod bör kanske i BoardGame
            char resultCode = enemyMove.charAt(0);
            System.out.println("Debug: Extraherad resultCode = " + resultCode);

            if (resultCode != 'i' && resultCode != 'h' && resultCode != 'm' && resultCode != 's') {
                throw new IllegalArgumentException("Ogiltig input: " + resultCode);
            }

            return resultCode;
        }


        private String getShotOutcome (String enemyMove, GameBoard myGameBoard){ //Denna metod bör kanske i BoardGame
            // Beräkna koordinater från enemyMove
            Coordinates shotCoordinates = Coordinates.getValueAtCoordinates(enemyMove);
            int row = shotCoordinates.getRow();
            int col = shotCoordinates.getCol();


            // Hämta värdet på den träffade rutan
            char outcome = myGameBoard.getBoard()[row][col];


            // Kolla om rutan innehåller ett skepp
            if (outcome == '0') {
                for (Ship ship : myGameBoard.getShips()) {

                    // Kontrollera om träffen är en del av detta skepp
                    if (ship.getCoordinates().stream().anyMatch(coord -> coord[0] == row && coord[1] == col)) {

                        ship.setNumberOfHits(ship.getNumberOfHits() + 1);

                        // Kolla om skeppet är sänkt
                        if (ship.getNumberOfHits() == ship.getSize()) {
                            ship.setSunk(true);

                            // Kontrollera om alla skepp är sänkta (spel över)
                            if (myGameBoard.getShips().stream().allMatch(Ship::isSunk)) {
                                return "game over";
                            }
                            return "s"; // Endast detta skepp är sänkt

                        }
                        return "h"; // Träff på skeppet men inte sänkt
                    }
                }
            }

            // Om inget skepp träffades, returnera "miss"
            return "m";
        }

        //GB-26-SA
        private void updateMyMap (String message){
            try {
                //Skickar in tex "4b" och spelplanen
                //Har en klass som i dens metod som tar koordinaterna från meddelandet och får tillbaka ett Coordinates objekt där jag kan hämta ut dens row och col
                Coordinates coords = getValueAtCoordinates(message);

                //Sätter in row och column från klassen i variablerna row och column
                int row = coords.getRow();
                int col = coords.getCol();

                System.out.println("Value at: " + message + " = [" + myGameBoard.getBoard()[row][col] + "]");

                //Kollar om värdet var ett S eller blankt och byter sen ut det till antigen 0 eller X
                if (myGameBoard.getBoard()[row][col] == 'S') {
                    System.out.println("A ship");
                    myGameBoard.getBoard()[row][col] = '0';

                } else if (myGameBoard.getBoard()[row][col] == ' ') {
                    System.out.println("No ship");
                    myGameBoard.getBoard()[row][col] = 'X';

                }

                myGameBoard.displayBoard();

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        //GB-18-SA.part2
        private void updateEnemyMap (String message){
            //Får in m/h/s eller null
            System.out.println("I last shot at: " + message);
            System.out.println("It was a: " + message.charAt(0));
            if (message.contains("null")) {
                return;
            }
            //Samma som i updateMyMap
            Coordinates coords = getValueAtCoordinates(message);
            int row = coords.getRow();
            int col = coords.getCol();

            //Gör inget om det är i, första skottet
            if (message.charAt(0) == 'i') {

            } else if (message.charAt(0) == 'h') {
                enemyGameBoard.getBoard()[row][col] = '0';

            } else if (message.charAt(0) == 'm') {
                enemyGameBoard.getBoard()[row][col] = 'X';

            } else if (message.charAt(0) == 's') {
                enemyGameBoard.getBoard()[row][col] = '0';
            }

            enemyGameBoard.displayBoard();

        }

        //GB-25-AA
        private boolean checkIfGameOver (String message){
            //GB-35-AA (Alertbox och .exit)
            if (iLose) {
                //GB-50-AA
                Platform.runLater(() -> {
                    GameView.showGameOverOverlay("GAME OVER\n\nYOU LOSE!\n\n" );
                });
                return true;
            } else {
                //GB-33-SA
                if (message.equalsIgnoreCase("game over")) {
                    updateEnemyMap(lastMove);
                    // Får game over från motståndaren och uppdaterar deras karta så sista skottet på dem syns


                    //GB-50-AA
                    Platform.runLater(() -> {
                        GameView.showGameOverOverlay("GAME OVER\n\nYOU WIN!\n\n" );
                    });

                    return true;

                } else {
                    System.out.println("Not game over");
                    return false;
                }
            }
        }

        public CommunicationHandler getPlayer () {
            return player;
        }

        public void setPlayer (CommunicationHandler player){
            this.player = player;
        }

        public boolean isClient () {
            return isClient;
        }

        public void setClient ( boolean client){
            isClient = client;
        }

        public GameBoard getMyGameBoard () {
            return myGameBoard;
        }

        public void setMyGameBoard (GameBoard myGameBoard){
            this.myGameBoard = myGameBoard;
        }

        public GameBoard getEnemyGameBoard () {
            return enemyGameBoard;
        }

        public void setEnemyGameBoard (GameBoard enemyGameBoard){
            this.enemyGameBoard = enemyGameBoard;
        }

        public double getDelay() {
        return delay;
        }

        public void setDelay(double delay) {
        this.delay = delay;
        }
}