package Graphic;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

//GB-15-SA
public class ConfirmBox {
    //Kommar samla svaret användaren väljer här
    public static boolean answer;

    //GB-15-SA
    public static boolean display(String titel, String message){
        //Skapar ett nytt fönster
        Stage window = new Stage();
        //Man ska inte kunna interagera med spelet medan detta fönstret är öppet
        window.initModality(Modality.APPLICATION_MODAL);

        //Titel och storlek på fönstret
        window.setTitle(titel.toUpperCase());
        window.setMinWidth(400);
        window.setMinHeight(300);

        //Får med text från metoden om vad det ska stå att användaren kan välja
        Text enter = new Text();
        enter.setText(message);
        enter.getStyleClass().add("titel-small");

        //Knappar för yes/no
        Button yesButton = new Button("Yes");
        yesButton.getStyleClass().add("button-standard");

        Button noButton = new Button("No");
        noButton.getStyleClass().add("button-standard");

        //Action för knapparna
        yesButton.setOnAction(e->{
            answer = true;
            window.close();
        });

        noButton.setOnAction(e->{
            answer = false;
            window.close();
        });

        //Skapar VBox där allt är centrerat
        VBox layout = new VBox(10);
        layout.getChildren().addAll(enter,yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        //Style på VBox bakgrunden
        layout.getStyleClass().add("background-blue");

        //Skapar scenen
        Scene scene = new Scene(layout);
        //Style på scenen
        scene.getStylesheets().add("Graphic/BattleShip.css");

        //Sätter denna scenen på Stage window
        window.setScene(scene);
        //Ska vänta
        window.showAndWait();
        //Ger tillbaka svar från knapparna till metoden i LoginView
        return answer;
    }
}
