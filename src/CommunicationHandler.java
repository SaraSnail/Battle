import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicationHandler {

    private String name;
    private String host;
    private int port;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private BufferedReader reader;
    private PrintWriter writer;


    //BG-10-AA
    public CommunicationHandler(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
        connectToServer();
    }

    //BG-10-AA
    public CommunicationHandler(String name, int port){
        this.name = name;
        this.port = port;
        connectToClient();
    }

    //BG-10-AA
    private void connectToServer(){
        try (Socket socket = new Socket(this.host,this.port)) {
            this.clientSocket = socket;
            System.out.println("Connected to  server");
            this.writer = new PrintWriter (new OutputStreamWriter(clientSocket.getOutputStream()),true);
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (Exception e) {
            System.out.println("Could not add socket");
            e.printStackTrace();
        }
    }

    //BG-10-AA
    private void connectToClient(){
        try (ServerSocket serverSocket1 = new ServerSocket(this.port)){
            this.serverSocket = serverSocket1;
            System.out.println("Waiting for client to connect");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            this.writer = new PrintWriter(clientSocket.getOutputStream(),true);
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (Exception e) {
            System.out.println("Could not add serverSocket");
            e.printStackTrace();
        }
    }

    //GB-10-AA
    private void handleIncomingMessages (){ // vet ännu inte om private eller public
        String incomingMessage;
        try{
            while ((incomingMessage = reader.readLine()) != null){
                //Uppdatera spel, vet ej om metoden ska ligga här eller på annan plats
            }

        } catch (IOException e){
            System.out.println("Connection closed by server");
        }
    }

    //GB-10-AA
    private void handleSendingMessages(String message){
        if (message != null && !message.trim().isEmpty()){
            writer.println(message);
            //Uppdatera egen spelplan (vet ej om metoden ska ligga här eller på annan plats
        }
    }
}
