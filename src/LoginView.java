import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginView extends Application{//Sara har skapat klassen och kodat in det i

    public Stage window;
    private Button client;
    private Button server;
    //private Label choosePlayer;
    private Scene loginView;
    private final Text choosePlayer2 = new Text("Choose player");

    //@Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setResizable(false);
        window.setTitle("Login View");

        //BorderPane borderPane = new BorderPane();
        //borderPane.setPadding(new Insets(20,20,20,20));

        //GridPane gridBottom = new GridPane();
        //gridBottom.setPadding(new Insets(20));
        //gridBottom.setHgap(50);
        //gridBottom.setVgap(50);

        //BorderPane borderPane = new BorderPane();
        //borderPane.setPadding(new Insets(20));

        //int columnIndex = 19;

        //GridPane gridPane = new GridPane();
        //choosePlayer = new Label();
        //choosePlayer.setText("Choose player");

        choosePlayer2.setFill(Color.BLACK);
        //choosePlayer2.setStyle("-fx-font: 24 Courier New");
        choosePlayer2.setFont(Font.font("Courier New", 24));
        //GridPane.setConstraints(choosePlayer2, 15, 10);


        //GridPane.setConstraints(choosePlayer, 13,15);


        client = new Button();
        client.setText("Player 1");
        client.setTextFill(Color.BLUE);
        client.setFont(Font.font("Courier New", 14));
        //GridPane.setConstraints(player1,13,11);
        server = new Button();
        server.setText("Player 2");
        server.setTextFill(Color.GREEN);
        server.setFont(Font.font("Courier New",14));
        //GridPane.setConstraints(player2, 20,11);


        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(choosePlayer2, client, server);
        anchorPane.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));

        //gridBottom.getChildren().addAll(player1, player2);
        //gridCenter.getChildren().addAll(choosePlayer);
        //borderPane.setCenter(choosePlayer2);



        //HBox HBottom = new HBox(20);
        //HBottom.getChildren().addAll(player1, player2);
        //borderPane.setBottom(HBottom);
        choosePlayer2.setLayoutX(150);
        choosePlayer2.setLayoutY(150);

        client.setLayoutX(150);
        client.setLayoutY(200);

        server.setLayoutX(250);
        server.setLayoutY(200);


        loginView = new Scene(anchorPane, 500,500);
        window.setScene(loginView);
        window.show();


    }

   
}
