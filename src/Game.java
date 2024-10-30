import java.net.Socket;

public class Game {

    private Socket server; //Eller objekt av communication-klass/socket-klass
    private Socket client; //Eller objekt av communication-klass/socket-klass

    //GB-13-AA
    public Game(Socket server, Socket client) {
        this.server = server;
        this.client = client;
    }

    //GB-13-AA
    public void startGame(Socket this.client, Socket this.server){

        /*
        GameBoard gameBoardClient = new GameBoard();        //Ny spelplan
        clinet.setGameBoard(gameBoardClient);               //Lägg till spelplan till klient
        client.getGameBoard.placeShipsOnMapWithRandom();    //Metod för att placera ut skepp på spelplanen

        GameBoaard gameBoardServer = new GameBoard();       //Ny spelplan
        server.setGameBoard(gameBoardServer);               //Lägg till spelplan på server
        server.getGameBoard.placeShipsOnMapWithRandom();    //placera ut skepp
        */

        try {
            Thread.sleep(5000);  //Väntar 5 sek innan spelet drar igång
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        boolean gameOver = false;
        while (!gameOver) {
            if (client) {
                // Metod för att slumpa fram skott - retunerar kordinater
                // Metod för att uppdatera egen karta med skott
                // Metod för att skicka skottet till server
                // Metod för att ta emot svar från server
                // Metod för att uppdatera karta med skott

            } else{
                // Metod för att ta emot skott/kordenater från client
                // Metod för att titta på kartan om träff/träff & sänkt skepp/träff & game over/miss
                // Metod för att uppdatera karta med skott
                // Metod för att skicka tillbaka om träff/träff & sänkt skepp/träff & game over/miss
                // Vänta några sekunder
                // Metod för att slumpa fram skott - retunerar kordinater
                // Metod för att skicka skottet till client

            }







        }

    }
}
