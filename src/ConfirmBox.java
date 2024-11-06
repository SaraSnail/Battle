import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Sara
public class ConfirmBox {
    public static boolean answer;

    //Sara
    public static boolean display(String titel, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titel.toUpperCase());
        window.setMinWidth(400);
        window.setMinHeight(300);

        Text enter = new Text();
        enter.setText(message);
        enter.getStyleClass().add("titel-small");


        Button yesButton = new Button("Yes");
        yesButton.getStyleClass().add("button-brown");
        Button noButton = new Button("No");
        noButton.getStyleClass().add("button-brown");

        yesButton.setOnAction(e->{
            answer = true;
            window.close();
        });

        noButton.setOnAction(e->{
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(enter,yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        layout.getStyleClass().add("background-blue");

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("BattleShip.css");
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
