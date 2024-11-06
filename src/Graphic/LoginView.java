package Graphic;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//GB-15-SA
public class LoginView extends Application{

    public Stage window;
    public Scene loginView;
    private Button clientButton;
    private Button serverButton;

    public Image startBackground = new Image("file:recourses/images/ship.jpg");
    //Credit "Dorian Mongel" på unsplash

    private final Text choosePlayer = new Text("Choose player");
    private final Text titel = new Text("Battleship");

    public final int windowSizeHeight = 1080;
    public final int windowSizeWidth = 1920;

    public final int COLUMN = 25;

    @Override//GB-15-SA
    public void start(Stage primaryStage) throws Exception {
        //Sätter primaryStage i window, gör att fönstrets storlek inte går att ändra och anger titel
        window = primaryStage;
        window.setResizable(false);
        window.setTitle("Login View");

        //Klasser för sceneClient och sceneServer
        Scene sceneClient = SceneClient.getScene(window);
        Scene sceneServer = SceneServer.getScene(window);


//----------------------------------------------------------------------------------------------------------------------
        //Ändrar utseende text, buttons
        //Start
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
            //Lägg in att användaren får skriva in localhost och port
            //Skicka vidare vilken player det är
            //New Scene
            whichPlayer(1);
            window.setScene(sceneClient);
            window.setFullScreen(true);
        });

        serverButton.setOnAction(e->{
            System.out.println("Player 2");
            //Lägg in att användaren får skriva in port
            whichPlayer(2);
            window.setScene(sceneServer);
            window.setFullScreen(true);
        });


        //Förhindrar att fönstret stängs utan möjlighet att gå tillbaka
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
        GridPane start = new GridPane();
        start.setPadding(new Insets(10));
        start.setVgap(8);
        start.setHgap(10);

        //Lägger till all Nodes på gridpane "start"
        start.getChildren().addAll(titel, choosePlayer, clientButton, serverButton, close);

        //Sätter platser för alla Nodes
        GridPane.setConstraints(titel, COLUMN,13);
        GridPane.setConstraints(choosePlayer, COLUMN,15);

        GridPane.setConstraints(clientButton, COLUMN, 17);
        GridPane.setConstraints(serverButton, COLUMN, 19);
        GridPane.setConstraints(close, COLUMN, 25);

        //Backgrund för gridpaneen
        start.setBackground(
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

        //Skapar scenen med gridpane och fönsterstorlek
        loginView = new Scene(start, windowSizeWidth,windowSizeHeight);
        //Stil på scenen
        loginView.getStylesheets().add("Graphic/BattleShip.css");

//----------------------------------------------------------------------------------------------------------------------
        //Ställer in så att fönstret är helskärm och inte går att ändra på
        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);


        window.setScene(loginView);
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
    //GB-15-SA
    //Kollar om porten användaren skrivir in är en Int och om den är över 1024 vilket är portar som redan används
    public boolean isInt(TextField input, String message){
        try{
            int port = Integer.parseInt(input.getText());
            if(port<1024){
                System.out.println("Port not available");
                return false;
            } else {
                System.out.println("Port is: " + message);
                return true;
            }

        }catch (NumberFormatException e){
            System.out.println(message + " is not a number");
            return false;

        }
    }
    //GB-15-SA
    //Skickar med vilken spelare det är
    public String whichPlayer(int player){
        if(player == 1){
            return "Player 1";
        } else if (player == 2) {
            return "Player 2";
        }
        return null;
    }

   
}
