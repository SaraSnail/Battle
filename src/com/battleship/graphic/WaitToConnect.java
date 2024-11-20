package com.battleship.graphic;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

//GB-39-SA
public class WaitToConnect {
    private static Stage window;

    //GB-49-SA, ändra från public till default
    //GB-39-SA
    static void display() {
        //Ny Stage
        window = new Stage();

        //Titel, resizable, modality, storlek på Stage
        window.setTitle("Waiting on connection");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);//Användaren kan inte interagera med spelet tills denna Stages har stängt
        window.setMinWidth(400);
        window.setMinHeight(300);

        //Text som syns
        Text waitingText = new Text("Waiting for a connection");
        waitingText.getStyleClass().add("titel-small");

        //VBox
        VBox vbox = new VBox();
        vbox.getChildren().add(waitingText);
        vbox.setAlignment(Pos.CENTER);//Centrera på Stage:en
        vbox.getStyleClass().add("background-blue");

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("com/battleship/graphic/BattleShip.css");

        window.setOnCloseRequest(e->{
            e.consume();//Användaren ska inte kunna stänga denna Stage
        });

        window.setScene(scene);
        window.show();
    }
    //GB-49-SA, ändra från public till default
    //GB-39-SA
    static void close() {
        if(window != null) {
            window.close();//Stage stängs när denna metod kallas
        }
    }
}
