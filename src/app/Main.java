package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/autor_formulario.fxml"));
        primaryStage.setTitle("Livraria Chupinguaia");
        primaryStage.setScene(new Scene(root, 605, 228));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}