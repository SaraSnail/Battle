package com.battleship.graphic;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

//GB-34-AA
public class AlertBox {

    //GB-34-AA
    public static void display(String title, String messange){
        Stage window = new Stage(); //nytt fönster
        window.initModality(Modality.APPLICATION_MODAL); //Gör att Fönstret måste klickas ner innan anv. kan göra något annat på skrämen
        window.setTitle(title); //titel på fönstret

        //samma som ConfirmBox
        window.setWidth(370);
        window.setHeight(200);

        Label label = new Label(messange); //textfält med meddelandet som skickades in.
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER); //Centrerar texten i varje rad.
        label.getStyleClass().add("titel-small");

        //Knapp för att stänga fönstret
        Button closeButton = new Button("OK!");
        closeButton.getStyleClass().add("button-standard");
        closeButton.setOnAction(event -> window.close());

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("background-blue");


        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // för att koden efter alertboxen ska pausas tills den klickas ner innan den kan fortsätta


    }
}
