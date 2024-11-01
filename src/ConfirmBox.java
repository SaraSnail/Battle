import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
    public static boolean answer;

    public static boolean display(String titel, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titel.toUpperCase());
        window.setMinWidth(400);
        window.setMinHeight(300);
        Label label = new Label();
        label.setText(message);
        label.setTextFill(Color.BLACK);


        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e->{
            answer = true;
            window.close();
        });

        noButton.setOnAction(e->{
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
