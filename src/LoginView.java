import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginView extends Application{//Sara har skapat klassen och kodat in det i

    private Stage window;
    private Button clientButton;
    private Button serverButton;
    private Scene loginView;

    private Scene scene1;
    private Scene scene2;

    private Button back1;
    private Button back2;

    private TextField host;
    private TextField port1;
    private TextField port2;
    private Button submit1;
    private Button submit2;

    private final Text choosePlayer = new Text("Choose player");
    private final Text titel = new Text("Battleship");

    private final int windowSizeHeight = 1080;
    private final int windowSizeWidth = 1920;

    private final int column = 25;


    public void start(Stage primaryStage) throws Exception {
        //Sätter primaryStage i window, gör att fönstrets storlek inte går att ändra och anger titel
        window = primaryStage;

        window.setResizable(false);
        window.setTitle("Login View");

//----------------------------------------------------------------------------------------------------------------------

        //Lägger in en bild från resources
        Image startScreen = new Image(getClass().getResourceAsStream("ship.jpg"));
        //Credit "Dorian Mongel" på unsplash

//----------------------------------------------------------------------------------------------------------------------
        //Ändrar utseende labels, buttons och textfeilds
        //Start
        titel.setFill(Color.WHITE);
        titel.setFont(Font.font("Arial",30));
        choosePlayer.setFill(Color.WHITE);
        choosePlayer.setFont(Font.font("Arial", 30));

        //Initierar knapp för client/player1 och utseende
        clientButton = new Button();
        clientButton.setText("Player 1");
        clientButton.setTextFill(Color.BLUE);
        clientButton.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        clientButton.setFont(Font.font("Courier New", 14));

        //Initierar knapp för server/player2 och utseende
        serverButton = new Button();
        serverButton.setText("Player 2");
        serverButton.setTextFill(Color.GREEN);
        serverButton.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        serverButton.setFont(Font.font("Courier New",14));

//----------------------------------------------------------------------------------------------------------------------

        //Skapat textField där användaren kan skriva in host och port
        //Client sida
        host = new TextField();
        host.setPromptText("host");
        host.setPrefSize(100,20);

        port1 = new TextField();
        port1.setPromptText("port");
        port1.setPrefSize(100,20);

        submit1 = new Button("Submit");
        submit1.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        submit1.setFont(Font.font("Arial", 14));

        back1 = new Button();
        back1.setText("Back");
        back1.setTextFill(Color.BLACK);
        back1.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        back1.setFont(Font.font("Arial", 14));

//----------------------------------------------------------------------------------------------------------------------

        //Server sida
        port2 = new TextField();
        port2.setPromptText("port");
        port2.setPrefSize(100,20);

        submit2 = new Button("Submit");
        submit2.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        submit2.setFont(Font.font("Arial", 14));

        back2 = new Button();
        back2.setText("Back");
        back2.setTextFill(Color.BLACK);
        back2.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        back2.setFont(Font.font("Arial", 14));


//----------------------------------------------------------------------------------------------------------------------
        //Action för knapparna
        clientButton.setOnAction(e->{
            System.out.println("Player 1");
            //Lägg in att användaren får skriva in localhost och port
            //Skicka vidare vilken player det är
            //New Scene
            whichPlayer(1);
            window.setScene(scene1);
            window.setFullScreen(true);
        });

        serverButton.setOnAction(e->{
            System.out.println("Player 2");
            //Lägg in att användaren får skriva in port
            whichPlayer(2);
            window.setScene(scene2);
            window.setFullScreen(true);
        });

        //Submit knapprar som kollar av i metoden isInt om port är ett nummer
        submit1.setOnAction(e->{
            //Sätta detta i en loop?
            System.out.println("Submit");
            host.getText();
            if(isInt(port1, port1.getText())){
                //launch(game);

            }else if (!isInt(port1, port1.getText())){
                System.out.println("Can't play at that port");
            }

        });

        submit2.setOnAction(e->{
            System.out.println("Sumbit");
            if(isInt(port2, port2.getText())){
                //launch(game);
            } else if (!isInt(port2, port2.getText())) {
                System.out.println("Can't play at that port");
            }
        });

        //Tillbaka till start skärmen
        back1.setOnAction(e->{
            window.setScene(loginView);
            window.setFullScreen(true);
        });
        back2.setOnAction(e->{
            window.setScene(loginView);
            window.setFullScreen(true);
        });


        //Förhindrar att fönstret stängs utan möjlighet att gå tillbaka
        Button close = new Button("Close");
        close.setOnAction(e->{
            e.consume();
            closeProgram();
        });

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

//----------------------------------------------------------------------------------------------------------------------
//Scener
        //Scene 1
        GridPane gridPane1 = new GridPane();
        gridPane1.setPadding(new Insets(10));
        gridPane1.setVgap(8);
        gridPane1.setHgap(10);

        Label player1Label = new Label();
        player1Label.setText("Enter host and port");
        player1Label.setTextFill(Color.WHITE);
        player1Label.setFont(Font.font("Arial", 20));

        gridPane1.getChildren().addAll(player1Label,host, port1, submit1, back1);

        GridPane.setConstraints(player1Label, column, 15);
        GridPane.setConstraints(host, column, 17);
        GridPane.setConstraints(port1, column, 19);
        GridPane.setConstraints(submit1, column, 21);
        GridPane.setConstraints(back1, column,23);


        gridPane1.setBackground(
                new Background(
                        new BackgroundImage(
                                startScreen,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(windowSizeWidth,windowSizeHeight, false,false,false,false)
                        )
                )
        );
        scene1 = new Scene(gridPane1, windowSizeWidth, windowSizeHeight);


        //Scene 2
        GridPane gridPane2 = new GridPane();
        gridPane2.setPadding(new Insets(10));
        gridPane2.setVgap(8);
        gridPane2.setHgap(10);

        Label player2Label = new Label();
        player2Label.setText("Enter port");
        player2Label.setTextFill(Color.WHITE);
        player2Label.setFont(Font.font("Arial", 20));

        gridPane2.getChildren().addAll(player2Label, port2, submit2, back2);

        GridPane.setConstraints(player2Label, column, 15);
        GridPane.setConstraints(port2, column, 17);
        GridPane.setConstraints(submit2, column, 19);
        GridPane.setConstraints(back2, column, 21);

        gridPane2.setBackground(
                new Background(
                        new BackgroundImage(
                                startScreen,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(windowSizeWidth,windowSizeHeight, false,false,false,false)
                        )
                )
        );
        scene2 = new Scene(gridPane2, windowSizeWidth, windowSizeHeight);


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
        GridPane.setConstraints(close, column, 21);

        start.setBackground(
                new Background(
                        new BackgroundImage(
                                startScreen,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(windowSizeWidth,windowSizeHeight, false,false,false,false)
                        )
                )
        );
        loginView = new Scene(start, windowSizeWidth,windowSizeHeight);

//----------------------------------------------------------------------------------------------------------------------


        window.setFullScreen(true);
        window.setFullScreenExitHint("Exit code is Ctrl+B");
        window.setFullScreenExitKeyCombination(KeyCombination.valueOf("Ctrl+B"));

        window.setScene(loginView);
        window.show();


    }

//----------------------------------------------------------------------------------------------------------------------
//Metoder
    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Exit","Sure you want to exit?");
        if (answer) {
            window.close();
        }
    }

    private boolean isInt(TextField input, String message){
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

    public void whichPlayer(int player){
        if(player == 1){
            String player1 = "Player 1";
        } else if (player == 2) {
            String player2 = "Player 2";
        }
    }

   
}
