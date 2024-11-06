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

//Sara
public class LoginView extends Application{//Sara har skapat klassen och kodat in det i

    public Stage window;
    private Button clientButton;
    private Button serverButton;
    public Scene loginView;

    public Image startBackground = new Image(getClass().getResourceAsStream("ship.jpg"));
    //Credit "Dorian Mongel" på unsplash

    private final Text choosePlayer = new Text("Choose player");
    private final Text titel = new Text("Battleship");

    private final int windowSizeHeight = 1080;
    private final int windowSizeWidth = 1920;

    private final int column = 25;

    @Override//Sara
    public void start(Stage primaryStage) throws Exception {
        //Sätter primaryStage i window, gör att fönstrets storlek inte går att ändra och anger titel
        window = primaryStage;

        window.setResizable(false);
        window.setTitle("Login View");

        //Klasser för sceneClient och sceneServer
        Scene sceneClient = SceneClient.getScene(window);
        Scene sceneServer = SceneServer.getScene(window);


//----------------------------------------------------------------------------------------------------------------------
        //Ändrar utseende labels, buttons och textfeilds
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
        close.getStyleClass().add("button-red");


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


        //Start scene
        GridPane start = new GridPane();
        start.setPadding(new Insets(10));
        start.setVgap(8);
        start.setHgap(10);

        start.getChildren().addAll(titel, choosePlayer, clientButton, serverButton, close);

        GridPane.setConstraints(titel, column,13);
        GridPane.setConstraints(choosePlayer, column,15);

        GridPane.setConstraints(clientButton, column, 17);
        GridPane.setConstraints(serverButton, column, 19);
        GridPane.setConstraints(close, column, 25);

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
        loginView = new Scene(start, windowSizeWidth,windowSizeHeight);

//----------------------------------------------------------------------------------------------------------------------

        loginView.getStylesheets().add("BattleShip.css");

        window.setFullScreen(true);
        window.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);



        window.setScene(loginView);
        window.show();


    }

//----------------------------------------------------------------------------------------------------------------------
//Metoder
    //Sara
    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Exit","Sure you want to exit?");
        if (answer) {
            window.close();
        }
    }
    //Sara
    public boolean isInt(TextField input, String message){
        try{
            int port = Integer.parseInt(input.getText());
            if(port<1024){
                System.out.println("Port not avalible");
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
    //Sara
    public void whichPlayer(int player){
        if(player == 1){
            String player1 = "Player 1";
        } else if (player == 2) {
            String player2 = "Player 2";
        }
    }

   
}
