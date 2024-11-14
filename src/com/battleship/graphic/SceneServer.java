package com.battleship.graphic;

import com.battleship.CommunicationHandler;
import com.battleship.Game;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Scanner;

//GB-15-SA
public class SceneServer {

    public static Scene scene;

    private static Text player2Label;

    private static TextField port2;
    private static Button submit2;
    private static Button back2;


    //GB-15-SA
    public static Scene getScene(Stage window) {
        LoginView login = new LoginView();

        //Skapat textField där användaren kan skriva in port
        port2 = new TextField();
        port2.setPromptText("port");
        port2.setPrefSize(100,20);

        //Knappar för att samla infon från TextFields eller om man vill gå tillbaka
        submit2 = new Button("Submit");
        submit2.getStyleClass().add("button-standard");

        back2 = new Button();
        back2.setText("Back");
        back2.getStyleClass().add("button-standard");


        //Action för submit och back knapparna
        submit2.setOnAction(e->{

            //GB-34-AA (if satsen)
            String portText = port2.getText().trim();
            if (portText.isEmpty()){
                System.out.println("Empty port box");
                return;
            }

            //SA
            System.out.println("Sumbit");
            if(login.isInt(port2, port2.getText())){

                //GB-39-SA
                //I Platform.runLater visar jag först WaitToConnect fönstret
                Platform.runLater(()->{
                    WaitToConnect.display();

                    //Skapar en "PauseTransition" med försening på 2 sekunder
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    //I pause skapas view Scenen
                    pause.setOnFinished(event -> {

                        //Scene för spelplanen som ska vara i samma Stage som LoginView
                        Scene view = GameView.gameView(window);
                        //GB-37-SA, la till Platform.runLater
                        Platform.runLater(()->{
                            //Platform.runLater i pause för att uppdatera till spelplanen efter den visat WaitToConnect


                            //Skapar först CommunicationHandler som väntar på kontakt med Client
                            //GB-18-SA
                            CommunicationHandler communicationHandler = new CommunicationHandler(login.whichPlayer(2), Integer.parseInt(port2.getText()));
                            port2.clear();
                            Game game = new Game(communicationHandler, false, login);
                            game.startGame();


                            //Byter scene till spelplanen
                            window.setScene(view);
                            //GB-39-SA
                            //Stänger WaitToConnect
                            WaitToConnect.close();

                        });

                    });
                    //Startar förseningen mellan WaitToConnect.display och CommunicationHandler
                    pause.play();
                });

                //WaitToConnect.display();

                //Pausa scene switch tills vi fått kontakt med klienten?
                //Vill ladda krigsscenen och starta allt
                //Skriv kod att ladda krigsscenen
                //Innan connection ha fördörjning





            } else if (!login.isInt(port2, port2.getText())) {
                System.out.println("Can't play at that port");
                //GB-35-AA (AlertBox - meddelandet)
                AlertBox.display("Warning", "Invalid port! \nA valid port must be 4 digits and a number between 1025-9999.\nPlease try again!");
                port2.clear();
            }
        });

        //SA
        //Går tillbaka till start
        back2.setOnAction(e->{
            goTo(login, window);
        });

        //Scene-Server
        //Skapar gridpane
        GridPane gridPane2 = new GridPane();
        gridPane2.setPadding(new Insets(10));
        gridPane2.setVgap(8);
        gridPane2.setHgap(10);

        //Skapar text för vad användaren ska skriva in
        player2Label = new Text();
        player2Label.setText("Enter port");
        player2Label.getStyleClass().add("titel-small");

        //Sätter alla nodes på gridpane
        gridPane2.getChildren().addAll(player2Label, port2, submit2, back2);

        //Sätter ut placering på allt
        GridPane.setConstraints(player2Label, login.COLUMN, 15);
        GridPane.setConstraints(port2, login.COLUMN, 17);
        GridPane.setConstraints(submit2, login.COLUMN, 19);
        GridPane.setConstraints(back2, login.COLUMN, 21);

        //Sätter bakgrund
        gridPane2.setBackground(
                new Background(
                        new BackgroundImage(
                                login.startBackground,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(login.windowSizeWidth,login.windowSizeHeight, false,false,false,false)
                        )
                )
        );
        //Skapar scenen och ger den Stylesheet
        scene = new Scene(gridPane2, login.windowSizeWidth, login.windowSizeHeight);
        scene.getStylesheets().add("com/battleship/graphic/BattleShip.css");

        return scene;
    }
    //GB-15-SA
    //Om man vill gå tillbaka
    //GB-18-SA, bytte från goBack till goTo
    private static void goTo(LoginView login, Stage window) {
        //GB-37-SA, la till Platform.runLater
        Platform.runLater(()->{
            try{
                //Tar klassen LoginView, metoden "start" och sätter igång "window" vilket är primaryStage medskickat från LoginView
                login.start(window);
                //window.setFullScreen(true);

            }catch (Exception exception){
                exception.printStackTrace();
            }
        });

    }


}
