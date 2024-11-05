import javafx.application.Platform;

import java.io.IOException;

public class Game {

    /*
    // GB-25-AA
    På inloggningssidan när spelaren loggar in som server eller klient. Lägg då till en boolean med true/false beroende
    på om det är en klient eller en server som skapats. Skicka med boolean tillsammans med CommunicationHandler-objektet
    när game-objekt skapas.
    */


    //GB-23-AA //GB-25-AA
    private CommunicationHandler player;
    private boolean isClient;
    private GameBoard gameBoard;

    //GB-13-AA //GB-23-AA //GB-25-AA
    public Game(CommunicationHandler player, boolean isClient) {
        this.player = player;
        this.isClient = isClient;
    }

    //GB-13-AA //GB-25-AA
    public void startGame() {

        this.gameBoard = new GameBoard();

        try {
            Thread.sleep(5000);  //Väntar 5 sek innan spelet drar igång
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (!isClient) {
            System.out.println("Waiting for client to connect and make it's fist move");
        }

        new Thread(this::gameLoop).start(); //startar spel-loopen asynkront - tror detta behövs för att inte stoppa upp flödet.

    }

    private void gameLoop(){
        boolean gameOver = false;
        boolean fistMove = true;
        while (!gameOver) {
            if (isClient) {
                if (fistMove){
                    System.out.println("Klientens första drag"); // tas bort senare

                    fistMove = false;
                } else {
                    System.out.println("Klientens tur"); //Tas bort senare
                    // makeMove(client);                //Klient skickar sitt drag
                    // receiveMove(server);             //Server tar emot drag
                    isClient = false;
                }
            } else {
                System.out.println("Servens tur: "); //Tas bort senare
                //makeMove(server);                 //Server skickar sitt drag
                //receiveMove(client);              //klient tar emot sitt drag
                isClient = true;
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



       /* private void makeMove(CommunicationHandler player){
            // Metod för att slumpa fram skott - retunera kordinater
            // Metod för att uppdatera egen karta med skott
            // Metod för att skicka skottet till motståndare
        }*/

/*        private String reciveMove(CommunicationHandler player) {
            // Metod för att ta emot skott/kordenater
            // Metod för att titta på egen kartan om träff/träff & sänkt skepp/träff & game over/miss
            // Metod för att uppdatera karta med skott
            // String coordinates = Metod för att skicka tillbaka om träff/träff & sänkt skepp/träff & game over/miss
        return; //coordinates;
        }*/

    //GB-25-AA
    private void updateGameView(String move){
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
        //"protokoll" för att se om spelet är slut
        return gameOver;
    }
}
