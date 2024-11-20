package com.battleship.graphic;

import com.battleship.CommunicationHandler;
import com.battleship.Game;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//GB-15-SA
public class SceneServer {
    //GB-49-SA, ändra från public till default
    static Scene scene;

    private static Text player2Label;
    private static TextField port2;
    private static Button submit2;
    private static Button back2;

    private static Game game;

    //GB-47-AA
    private static ComboBox<Double> delay;
    private static double delaySec;

    //GB-49-SA, ändra från public till default
    //GB-15-SA
    static Scene getScene(Stage window) {
        LoginView login = new LoginView();//Nå metoder i loginView

        //Skapat textField där användaren kan skriva in port
        port2 = new TextField();
        port2.setPromptText("port");
        port2.setPrefSize(100,20);

        //GB-47-AA
        delay = new ComboBox<>();
        delay.setPromptText("Set delay between shots in sec");
        delay.getItems().addAll(0.0,0.5, 1.0,1.5,2.0,2.5,3.0,3.5,4.0,4.5,5.0);
        delay.setOnAction(event -> {
            delaySec = delay.getSelectionModel().getSelectedItem();
        });

        //Knappar för att samla info från TextFields eller om man vill gå tillbaka
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
                //GB-46-SA
                back2.setOnAction(Event::consume);
                //GB-39-SA, fick hjälp av Micke
                Platform.runLater(WaitToConnect::display);
                window.setTitle(login.whichPlayer(2));
                startThread(window, login);

            } else if (!login.isInt(port2, port2.getText())) {
                System.out.println("Can't play at that port");
                //GB-35-AA (AlertBox - meddelandet)
                AlertBox.display( "Warning", "Invalid port! \nA valid port must be 4 digits and a number between 1025-9999.\nPlease try again!");
                port2.clear();
            }
        });

        //SA. Går tillbaka till start
        back2.setOnAction(e->{
            goBack(login, window);
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
        gridPane2.getChildren().addAll(player2Label, port2,delay, submit2, back2);

        //Sätter ut placering på allt
        GridPane.setConstraints(player2Label, login.COLUMN, 15);
        GridPane.setConstraints(port2, login.COLUMN, 17);
        GridPane.setConstraints(delay,login.COLUMN,19);
        GridPane.setConstraints(submit2, login.COLUMN, 21);
        GridPane.setConstraints(back2, login.COLUMN, 23);

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

    //SA, fick hjälp av Micke. Starta Thread:en i en metod
    private static void startThread(Stage window, LoginView login) {
        //SA, gav Thread:en ett namn
        //GB-Debug-AA-2.0 implementering av thread för bakgrundskommunikation..
        Thread threadServer = new Thread(() -> {
            //AA
            try {
                //SA
                CommunicationHandler communicationHandler = new CommunicationHandler(login.whichPlayer(2), Integer.parseInt(port2.getText()));
                port2.clear();
                //GB-49-SA, tog bort LoginView som inparameter
                Game game = new Game(communicationHandler, false);
                game.setDelay(delaySec); //GB-47-AA
                game.createBoards();

                //GB-18-SA
                try {
                    Scene view = GameView.gameView(window, game.getMyGameBoard(), game.getEnemyGameBoard());

                    //GB-37-SA, la till Platform.runLater
                    //SA, hjälp av Micke. Två Platform.runLater istället för en
                    Platform.runLater(()-> window.setScene(view));
                    Platform.runLater(WaitToConnect::close);

                    //AA
                    game.startGame();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        threadServer.setDaemon(true);//SA, satte Daemon = true. Så det är bakgrunds thread som inte hindrar JVM att avsluta
        threadServer.start();//AA
    }

    //GB-15-SA. Om man vill gå tillbaka
    private static void goBack(LoginView login, Stage window) {
        //GB-37-SA, la till Platform.runLater
        Platform.runLater(()->{
            try{
                //Tar klassen LoginView, metoden "start" och sätter igång "window" vilket är primaryStage medskickat från LoginView
                login.start(window);

            }catch (Exception exception){
                exception.printStackTrace();
            }
        });

    }


}
