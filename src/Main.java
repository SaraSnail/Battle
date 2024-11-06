import java.util.Scanner;
import javafx.application.Application;

public class Main {



public class Main{

    public static void main(String[] args) {

        GameBoard gameBoard = new GameBoard();
        gameBoard.displayBoard();


        //Sara
        try{
            Application.launch(LoginView.class, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }




}