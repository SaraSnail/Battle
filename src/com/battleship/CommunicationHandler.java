package com.battleship;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.ConnectException;

public class CommunicationHandler implements AutoCloseable{
    //GB-34-AA  implements Auto.... För att kunna använda tryCatch i SceneClient om det inte finns någon server

    private String name; // kan skrivas ut på skärmen över kartan
    private String host;
    private int port;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    //GB-26-SA
    //Tom constructor för testning
    public CommunicationHandler(){

    }


    //BG-10-AA (konstruktorn) //GB-34-AA (throws...)
    public CommunicationHandler(String name, String host, int port) throws ConnectException {
        this.name = name;
        this.host = host;
        this.port = port;
        connectToServer();
        setReader(reader);
        setWriter(writer);
    }

    //BG-10-AA
    public CommunicationHandler(String name, int port){
        this.name = name;
        this.port = port;
        connectToClient();
        setReader(reader);
        setWriter(writer);
    }

    //BG-10-AA
    private void connectToServer() throws ConnectException {
        //Try-with-resources om vi lägger in communication här i blocket. Socket stängs då i slutet av blocket.
        // Annars använd close()- metod.
        try {
            clientSocket = new Socket(this.host, this.port);
            System.out.println("Connected to server");
            this.writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //GB-34-AA (connectionExpectation)
        } catch (java.net.ConnectException e) {
            throw new ConnectException("Connection refused: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Could not connect to server at " + this.host + ":" + this.port);
            e.printStackTrace();
        }
    }

    //BG-10-AA
    private void connectToClient(){
        //Try-with-resources om vi lägger in communication här i blocket. Socket stängs då i slutet av blocket.
        // Annars använd closeSocket()- metod.
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("Waiting for client to connect");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            this.writer = new PrintWriter(clientSocket.getOutputStream(),true);
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (Exception e) {
            System.out.println("Could not start server on port " + this.port );
            e.printStackTrace();
        }
    }

    //GB-10-AA
    public String handleIncomingMessages (){
        String incomingMessage = "";
        try {
            System.out.println("waiting for move...");
            while (true) {
                    if (reader.ready()) { // Kontrollera om det finns data att läsa
                        incomingMessage = reader.readLine();
                        if (incomingMessage == null) { // Kontroll om strömmen är stängd
                            System.out.println("Connection closed by other side - i whileloopen");
                            break;
                        }
                        System.out.println("Mottaget: " + incomingMessage); // Hantera meddelandet
                        // Uppdatera spel, hantera logik här
                    }
            }
        } catch (IOException e) {
            System.out.println("Connection closed by other side");
            throw new RuntimeException(e);
        }
        return incomingMessage;
        /*try{
            System.out.println("Waiting for move...");
            while ((incomingMessage = reader.readLine()) != null){
                System.out.println("Mottaget: " + incomingMessage); // ska nog göras om och flyttas till grafisk vy
                //Uppdatera spel, vet ej om metoden ska ligga här eller på annan plats
            }

        } catch (IOException e){
            System.out.println("Connection closed by other side");
        }
        return incomingMessage;*/


        /*try {
            String incomingMessage = reader.readLine();
            System.out.println("Mottaget: " + incomingMessage);
            return incomingMessage;

        } catch (IOException e) {
            System.out.println("Connection closed by other side");
            throw new RuntimeException(e);
        }*/
    }

    //GB-10-AA
    public void handleSendingMessages(String message){
        if (message != null && !message.trim().isEmpty()){
            writer.println(message);
            writer.flush();
            System.out.println("Skickat: " + message);
            //Uppdatera egen spelplan (vet ej om metoden ska ligga här eller på annan plats)
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "CommunicationHandler{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", clientSocket=" + clientSocket +
                ", serverSocket=" + serverSocket +
                ", reader=" + reader +
                ", writer=" + writer +
                '}';
    }

    @Override
    //GB-10-AA
    public void close() throws Exception {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null){
                writer.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()){
                clientSocket.close();
            }
            if (serverSocket != null && !clientSocket.isClosed()){
                serverSocket.close();
            }
        } catch (IOException e){
            System.out.println("Error closing resources");
            e.printStackTrace();
        }
    }
}
