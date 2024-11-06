import java.net.Socket;

public class Game {

    //GB-23-AA
    private CommunicationHandler server;
    private CommunicationHandler client;

    //GB-13-AA //GB-23-AA
    public Game(CommunicationHandler server, CommunicationHandler client) {
        this.server = server;
        this.client = client;
    }

    //GB-13-AA
    public void startGame(CommunicationHandler server, CommunicationHandler client) {

        /*GameBoard gameBoardClient = new GameBoard();        //Ny spelplan
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
        boolean isClientsTurn = true;

        while (!gameOver) {
            if (isClientsTurn) {
                System.out.println("Klientens tur"); //Tas bort senare
                // makeMove(client);                //Klient skickar sitt drag
                // receiveMove(server);             //Server tar emot drag
                isClientsTurn = false;
            } else {
                System.out.println("Servens tur: "); //Tas bort senare
                //makeMove(server);                 //Server skickar sitt drag
                //receiveMove(client);              //klient tar emot sitt drag
                isClientsTurn = true;
            }
            try {
                Thread.sleep(3000);          //Väntar 3 sekunder mellan varje drag
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //Kanske metod som kollar gameover och retunerar boolean.
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
}
