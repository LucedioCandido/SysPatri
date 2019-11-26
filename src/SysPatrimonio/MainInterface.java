package SysPatrimonio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainInterface extends Application {
    public MainInterface() {
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("PrimaryOverview.fxml"));
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("image/iconBot.jpeg")));
        primaryStage.setTitle("SysPatrimonio");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}