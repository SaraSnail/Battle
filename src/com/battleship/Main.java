package com.battleship;

import com.battleship.graphic.LoginView;
import javafx.application.Application;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {



        //GB-26-SA
        //Skrev in detta för att testa updateMap
        //Skapa en tom constructor för CommunicationHandler för detta, ta bort senare

        CommunicationHandler communicationHandler = new CommunicationHandler();
        Game game = new Game(communicationHandler, true);

        GameBoard gameBoard = new GameBoard(true);
        gameBoard.displayBoard();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Battle Ship Game");
        System.out.print("Enter coordinates: ");
        String coordinates = scanner.nextLine();
        game.updateMaps(coordinates, gameBoard);



        //GB-15-SA
        try{
            Application.launch(LoginView.class, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }


}