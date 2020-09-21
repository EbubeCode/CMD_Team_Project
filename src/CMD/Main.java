package CMD;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("welcomePage.fxml"));
        //primaryStage.setTitle("CMD App");
        //primaryStage.setScene(new Scene(root, 500, 600));
        //primaryStage.show();
   Scene scene = new Scene (root);
   primaryStage.initStyle(StageStyle.UNDECORATED);
   primaryStage.setScene(scene);
   primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
