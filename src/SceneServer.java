import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//Sara
public class SceneServer {

    public static Scene scene;

    private static Text player2Label;

    private static TextField port2;
    private static Button submit2;
    private static Button back2;

    private static final int windowSizeHeight = 1080;
    private static final int windowSizeWidth = 1920;

    private static final int COLUMN = 25;

    //Sara
    public static Scene getScene(Stage window) {
        LoginView login = new LoginView();

        port2 = new TextField();
        port2.setPromptText("port");
        port2.setPrefSize(100,20);

        submit2 = new Button("Submit");
        submit2.getStyleClass().add("button-standard");

        back2 = new Button();
        back2.setText("Back");
        back2.getStyleClass().add("button-standard");


        //Submit knapprar som kollar av i metoden isInt om port är ett nummer
        submit2.setOnAction(e->{
            System.out.println("Sumbit");
            if(login.isInt(port2, port2.getText())){
                //launch(game);
            } else if (!login.isInt(port2, port2.getText())) {
                System.out.println("Can't play at that port");
            }
        });

        //Tillbaka till start skärmen

        back2.setOnAction(e->{
            goBack(login, window);
            //window.setScene(startScene);
        });

        //Scene 2
        GridPane gridPane2 = new GridPane();
        gridPane2.setPadding(new Insets(10));
        gridPane2.setVgap(8);
        gridPane2.setHgap(10);

        player2Label = new Text();
        player2Label.setText("Enter port");
        player2Label.getStyleClass().add("titel-small");

        gridPane2.getChildren().addAll(player2Label, port2, submit2, back2);

        GridPane.setConstraints(player2Label, COLUMN, 15);
        GridPane.setConstraints(port2, COLUMN, 17);
        GridPane.setConstraints(submit2, COLUMN, 19);
        GridPane.setConstraints(back2, COLUMN, 21);

        gridPane2.setBackground(
                new Background(
                        new BackgroundImage(
                                login.startBackground,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(windowSizeWidth,windowSizeHeight, false,false,false,false)
                        )
                )
        );
        scene = new Scene(gridPane2, windowSizeWidth, windowSizeHeight);
        scene.getStylesheets().add("BattleShip.css");

        return scene;
    }
    //Sara
    private static void goBack(LoginView login, Stage window) {

        try{
            login.start(window);
            window.setFullScreen(true);

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
