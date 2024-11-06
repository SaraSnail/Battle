import javafx.application.Application;


public class Main{

    public static void main(String[] args) {

        //Sara
        try{
            Application.launch(LoginView.class, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}