
package server;

import Database.Database;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class ServerGui extends Application {


     public static FXMLDocumentController test;
    @Override
    public void start(Stage stage) throws Exception {
       FXMLLoader ServerPage=new FXMLLoader();
        ServerPage.setLocation(getClass().getResource("FXMLDocument.fxml"));
        Parent  ServerPageroot = ServerPage.load();
        test=ServerPage.getController();
        Scene scene = new Scene(ServerPageroot);
        stage.setScene(scene);
        stage.setTitle("Server Page");
        stage.setResizable(false);
//        stage.getIcons().add(new Image("logo.png"));
        stage.show();
    }
  
    public static void main(String[] args) {

        launch(args);
    }

}
