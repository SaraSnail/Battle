package com.battleship.graphic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//GB-15-SA
public class LoginView extends Application{

    //Skapar nodes och variabler här uppe
    //GB-49-SA, ändra från public till default
    Stage window;
    Scene startScene;

    private Button clientButton;
    private Button serverButton;

    Image startBackground = new Image("file:recourses/images/ship.jpg");
    //Credit "Dorian Mongel" på unsplash
    // https://unsplash.com/photos/white-and-black-ship-5Rgr_zI7pBw

    private final Text choosePlayer = new Text("Choose player");
    private final Text titel = new Text("Battleship");

    //GB-49-SA, ändra från public till default
    final int windowSizeHeight = 700; // 1080, debug-SA, ändra fönster storleken
    final int windowSizeWidth = 1450; // 1920

    final int COLUMN = 25;

    @Override//GB-15-SA
    public void start(Stage primaryStage) throws Exception {
        //Sätter primaryStage i window
        window = primaryStage;
        window.setResizable(false);
        window.setTitle("Login View");

        //Klasser för sceneClient och sceneServer
        Scene sceneClient = SceneClient.getScene(window);
        Scene sceneServer = SceneServer.getScene(window);


//----------------------------------------------------------------------------------------------------------------------
        //Startsida
        //Ändrar utseende text, buttons
        titel.getStyleClass().add("titel-big");
        choosePlayer.getStyleClass().add("titel-small");

        //Initierar knapp för client/player1 och utseende
        clientButton = new Button();
        clientButton.setText("Player 1");
        clientButton.getStyleClass().add("button-blue");

        //Initierar knapp för server/player2 och utseende
        serverButton = new Button();
        serverButton.setText("Player 2");
        serverButton.getStyleClass().add("button-green");

        Button close = new Button("Close");
        close.getStyleClass().add("button-standard");


//----------------------------------------------------------------------------------------------------------------------
        //Action för knapparna
        clientButton.setOnAction(e->{
            System.out.println("Player 1");
            //GB-37-SA, la till Platform.runLater
            Platform.runLater(()->{
                window.setScene(sceneClient);//Ny scene
            });
        });

        serverButton.setOnAction(e->{
            System.out.println("Player 2");
            //GB-37-SA, la till Platform.runLater
            Platform.runLater(()->{
                window.setScene(sceneServer);//Ny scene
            });
        });

        //Metod closeProgram för close knapp och om användaren klickar på krysset
        close.setOnAction(e->{
            e.consume();
            closeProgram();
        });

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });


        //Start-scene
        //Skapar gridpane
        GridPane startGridPane = new GridPane();
        startGridPane.setPadding(new Insets(10));
        startGridPane.setVgap(8);
        startGridPane.setHgap(10);

        //Lägger till all Nodes på gridpane "startGridPane"
        startGridPane.getChildren().addAll(titel, choosePlayer, clientButton, serverButton, close);

        //Sätter platser för alla Nodes
        //Text
        GridPane.setConstraints(titel, COLUMN,13);
        GridPane.setConstraints(choosePlayer, COLUMN,15);
        //Knappar
        GridPane.setConstraints(clientButton, COLUMN, 17);
        GridPane.setConstraints(serverButton, COLUMN, 19);
        GridPane.setConstraints(close, COLUMN, 25);

        //Background för "startGridPane"
        startGridPane.setBackground(
                new Background(
                        new BackgroundImage(
                                startBackground,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(windowSizeWidth,windowSizeHeight, false,false,false,false)
                        )
                )
        );

        //Skapar scenen med gridpane och fönsterstorlekarna
        startScene = new Scene(startGridPane, windowSizeWidth,windowSizeHeight);
        //Stil på scenen
        startScene.getStylesheets().add("com/battleship/graphic/BattleShip.css");

        //Sätter scenen för Stagen
        window.setScene(startScene);
        window.show();

    }

//----------------------------------------------------------------------------------------------------------------------

//Metoder
    //GB-15-SA
    //Den skickar tillbaka om användaren verkligen vill stäga programmet eller inte, anger svar i ConfirmBox klassen
    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Exit","Sure you want to exit?");

        if (answer) {
            window.close();
        }
    }

    //GB-49-SA, ändra från public till default
    //GB-15-SA hade skrivit tidigare version. Metoden, inparametrarna, return true/false, parseInt, port < 1024.
    //GB-34-AA
    //Uppdaterar metoden så att den kontrollerar antal tecken och att det är siffror mellan 1025-9999.
    boolean isInt(TextField input, String message) {
        String textInput = input.getText().trim();
        if (textInput.length() == 4 && textInput.matches("\\d+") ) { //kontrollerar om strängen består av 4 siffror
            int port = Integer.parseInt(input.getText());

                if (port >= 1025 && port <= 9999) {
                    System.out.println("Port is: " + message);
                    return true;
                } else {
                    return false;
                }

        } else {
            return false;
        }
    }

    //GB-49-SA, ändra från public till default
    //GB-15-SA
    //Skickar med vilken spelare det är
    String whichPlayer(int player){
        if(player == 1){
            return "Player 1";
        } else if (player == 2) {
            return "Player 2";
        }
        return null;
    }

   
}
