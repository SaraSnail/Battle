import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

    private final int windowSizeHeight = 700;
    private final int windowSizeWidth = 1000;

    public void start(Stage primaryStage) throws Exception {
        //Sätter primaryStage i window, gör att fönstrets storlek inte går att ändra och anger titel
        window = primaryStage;
        window.setResizable(false);
        window.setTitle("Login View");

        //Lägger in en bild från resources
        Image startScreen = new Image(getClass().getResourceAsStream("ship.jpg"));

        //Ändrar utseende på texten för titel och choose player
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

        //Knappar tillbaka
        back1 = new Button();
        back1.setText("Back");
        back1.setTextFill(Color.BLACK);
        back1.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        back1.setFont(Font.font("Arial", 14));

        back2 = new Button();
        back2.setText("Back");
        back2.setTextFill(Color.BLACK);
        back2.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        back2.setFont(Font.font("Arial", 14));


        //Skapat textField där användaren kan skriva in host och port
        host = new TextField();
        host.setPromptText("host");
        host.setPrefSize(100,20);

        port1 = new TextField();
        port1.setPromptText("port");
        port1.setPrefSize(100,20);

        port2 = new TextField();
        port2.setPromptText("port");
        port2.setPrefSize(100,20);

        //submit för att ta emot text från användaren
        submit1 = new Button("Submit");
        submit1.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        submit1.setFont(Font.font("Arial", 14));

        submit2 = new Button("Submit");
        submit2.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        submit2.setFont(Font.font("Arial", 14));


        //Action för knapparna player1 och player2
        clientButton.setOnAction(e->{
            System.out.println("Player 1");
            //Lägg in att användaren får skriva in localhost och port
            //Skicka vidare vilken player det är
            //New Scene
            whichPlayer(1);
            window.setScene(scene1);
        });

        serverButton.setOnAction(e->{
            System.out.println("Player 2");
            //Lägg in att användaren får skriva in port
            whichPlayer(2);
            window.setScene(scene2);
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
            isInt(port2, port2.getText());
        });

        //Tillbaka till start skärmen
        back1.setOnAction(e->{
            window.setScene(loginView);
        });
        back2.setOnAction(e->{
            window.setScene(loginView);
        });


        //Förhindrar att fönstret stängs utan möjlighet att gå tillbaka
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });


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

        GridPane.setConstraints(player1Label, 12, 9);
        GridPane.setConstraints(host, 12, 11);
        GridPane.setConstraints(port1, 12, 13);
        GridPane.setConstraints(submit1, 12, 15);
        GridPane.setConstraints(back1, 12,17);


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

        GridPane.setConstraints(player2Label, 12, 9);
        GridPane.setConstraints(port2, 12, 11);
        GridPane.setConstraints(submit2, 12, 13);
        GridPane.setConstraints(back2, 12, 15);

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


        //Koordinater för placeringar av titel, choosePlayer, client-, serverButton
        //width 1000, height 700
        //x = width, y = height
        titel.setLayoutX(windowSizeWidth/5.5);
        titel.setLayoutY(windowSizeHeight/7.0);

        choosePlayer.setLayoutX(windowSizeWidth/6.4);
        choosePlayer.setLayoutY(windowSizeHeight/5.0);

        clientButton.setLayoutX(windowSizeWidth/7.0);
        clientButton.setLayoutY(windowSizeHeight/4.0);

        serverButton.setLayoutX(windowSizeWidth/3.7);
        serverButton.setLayoutY(windowSizeHeight/4.0);

        //Sätter bakgrund på start scenen
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(choosePlayer, clientButton, serverButton, titel);

        anchorPane.setBackground(
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


        loginView = new Scene(anchorPane, windowSizeWidth,windowSizeHeight);
        window.setScene(loginView);
        window.show();


    }

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
