import Graphic.LoginView;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {

        GameBoard gameBoard = new GameBoard();
        gameBoard.displayBoard();


        //GB-15-SA
        try{
            Application.launch(Graphic.LoginView.class, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


}