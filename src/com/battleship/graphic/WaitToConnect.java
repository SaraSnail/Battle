package com.battleship.graphic;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

//GB-39-SA
public class WaitToConnect {
    private static Stage window;

    //GB-39-SA
    public static void display() {
        window = new Stage();

        window.setTitle("Waiting on connection");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);
        window.setMinHeight(300);

        Text waitingText = new Text("Waiting for a connection");
        waitingText.getStyleClass().add("titel-small");

        VBox vbox = new VBox();
        vbox.getChildren().add(waitingText);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("background-blue");

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("com/battleship/graphic/BattleShip.css");


        window.setOnCloseRequest(e->{
            e.consume();
        });


        window.setScene(scene);
        window.show();
        //window.showAndWait();//Måste stänga fönstret innan den går vidare
        //Platform.runLater(() -> {window.show();});
    }
    //GB-39-SA
    public static void close() {
        if(window != null) {
            window.close();
        }
    }
}
