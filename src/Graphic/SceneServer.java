package Graphic;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
            System.out.println("Sumbit");
            if(login.isInt(port2, port2.getText())){
                System.out.println("k");

                /*
                CommunicationHandler communicationHandler = new CommunicationHandler(login.whichPlayer(2), Integer.parseInt(port2.getText()));
                Game game = new Game(communicationHandler, false);
                game.startGame();*/


            } else if (!login.isInt(port2, port2.getText())) {
                System.out.println("Can't play at that port");
            }
        });

        //Går tillbaka till start
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
        scene.getStylesheets().add("Graphic/BattleShip.css");

        return scene;
    }
    //GB-15-SA
    //Om man vill gå tillbaka
    private static void goBack(LoginView login, Stage window) {

        try{
            //Tar klassen LoginView, metoden "start" och sätter igång "window" vilket är primaryStage medskickat från LoginView
            login.start(window);
            window.setFullScreen(true);

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
