import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class LoginView extends Application{//Sara har skapat klassen och kodat in det i

    public Stage window;
    private Button clientButton;
    private Button serverButton;
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

        //GridPane gridPane = new GridPane();
        //choosePlayer = new Label();
        //choosePlayer.setText("Choose player");

        choosePlayer2.setFill(Color.WHITE);
        choosePlayer2.setFont(Font.font("Courier New", 30));
        //GridPane.setConstraints(choosePlayer2, 15, 10);


        //GridPane.setConstraints(choosePlayer, 13,15);


        clientButton = new Button();
        clientButton.setText("Player 1");
        clientButton.setTextFill(Color.BLUE);
        clientButton.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        clientButton.setFont(Font.font("Courier New", 14));
        //GridPane.setConstraints(player1,13,11);
        serverButton = new Button();
        serverButton.setText("Player 2");
        serverButton.setTextFill(Color.GREEN);
        serverButton.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
        serverButton.setFont(Font.font("Courier New",14));
        //GridPane.setConstraints(player2, 20,11);

/*
        clientButton.setOnAction(e->{
            System.out.println("Creating Socket");
            try{
                Socket socketClient = new Socket("localhost", 8886);

                //Communication clientCom = new Communication(socketClient);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        serverButton.setOnAction(e->{
            System.out.println("Creating serverSocket");
            try(ServerSocket serverSocket = new ServerSocket(8886)){
                Socket socketServer = serverSocket.accept();
                //Communication clientCom = new Communication(socketServer);

            }catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
*/

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(choosePlayer2, clientButton, serverButton);
        //anchorPane.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        Image startScreen = new Image(getClass().getResourceAsStream("sprite_0.png"));


        anchorPane.setBackground(
                new Background(
                        new BackgroundImage(
                                startScreen,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(500,500, false,false,false,false)
                        )
                )
        );

        //gridBottom.getChildren().addAll(player1, player2);
        //gridCenter.getChildren().addAll(choosePlayer);
        //borderPane.setCenter(choosePlayer2);



        //HBox HBottom = new HBox(20);
        //HBottom.getChildren().addAll(player1, player2);
        //borderPane.setBottom(HBottom);
        choosePlayer2.setLayoutX(135);
        choosePlayer2.setLayoutY(125);

        clientButton.setLayoutX(150);
        clientButton.setLayoutY(200);

        serverButton.setLayoutX(250);
        serverButton.setLayoutY(200);


        loginView = new Scene(anchorPane, 500,500);
        window.setScene(loginView);
        window.show();


    }

   
}
