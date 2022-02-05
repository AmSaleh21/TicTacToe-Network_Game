/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selectionmode;

import Helper_Package.Game;
import Helper_Package.InsideXOGame;
import Helper_Package.Player;
import Helper_Package.RecordedMessages;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.loginController;
import tictactoe_player.TicTacToe_Player;

public class SelectionModeController implements Initializable {

    PrintStream PSFromController;
    @FXML
    private Text logedInUserName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PSFromController = TicTacToe_Player.ps;
        logedInUserName.setText(loginController.username);

        //why Integer.toString?
        //because setText required text not integer
        // logedInUserScore.setText(Integer.toString(TicTacToe_Player.score));
    }

    //if the user press single player it will move to the select level scene
    @FXML
    private void singlePlayer(ActionEvent event) throws IOException {
        FXMLLoader levelSelectionlader = new FXMLLoader();
        levelSelectionlader.setLocation(getClass().getResource("/selectlevel/SelectLevel.fxml"));
        Parent levelSelectionRoot = levelSelectionlader.load();
        Scene levelSelectionScene = new Scene(levelSelectionRoot);
        Stage levelSelectionstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        levelSelectionstage.hide();
        levelSelectionstage.setScene(levelSelectionScene);
        levelSelectionstage.show();
    }

    //if the user press multiplayer it will send this message to server in order to return back
    //the data about the online users
    @FXML
    private void multiplayer(ActionEvent event) {
        Player player = new Player();
        player.setUserName(loginController.username);
        InsideXOGame xoMessage = new InsideXOGame(RecordedMessages.RETRIVE_PLAYERS, player);
        Gson g = new Gson();
        String s = g.toJson(xoMessage);
        PSFromController.println(s);
    }

    // ************************start  Recording Handlers ******************
    @FXML
    private Button gameRecordsButton;

    @FXML
    private void gameRecords(ActionEvent event) {
        System.out.println("inside gameRecords -> SelectionModeController");
        Game game = new Game();
//        player.setUserName(loginController.username); 
        InsideXOGame xoMessage = new InsideXOGame(RecordedMessages.Recorded_LIST, game);
        Gson g = new Gson();
        String s = g.toJson(xoMessage);
        PSFromController.println(s);
    }
    // ************************ end Recording Handlers ******************

    //if the user press logout it will send logout to server
    //and it will return back to login scene
    @FXML
    private void logout(ActionEvent event) {
        try {
//            Player player=new Player();
//            if (loginController.username == null)
//            {
//                loginController.username = "null";
//            }
//            player.setUserName(loginController.username);
//            InsideXOGame xoMessage =new InsideXOGame (RecordedMessages.LOGOUT,player);
//            Gson g = new Gson();
//            String s = g.toJson(xoMessage);
//            PSFromController.println(s);
//            FXMLLoader logInLoader=new FXMLLoader();
//            logInLoader.setLocation(getClass().getResource("/login/login.fxml"));
//            Parent  loginRoot = logInLoader.load();
//            TicTacToe_Player.LI = logInLoader.getController();
//            Scene loginScene = new Scene( loginRoot);
//            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            loginStage.hide();
//            loginStage.setScene(loginScene);
//            loginStage.show(); 
            Player player = new Player();
            player.setUserName(loginController.username);
            InsideXOGame xointerface = new InsideXOGame(RecordedMessages.LOGOUT, player);
            Gson g = new Gson();
            String s = g.toJson(xointerface);
            PSFromController.println(s);
            Platform.exit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void minimize(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    private void exit(ActionEvent event) {
        Player player = new Player();
        player.setUserName(loginController.username);
        InsideXOGame xointerface = new InsideXOGame(RecordedMessages.LOGOUT, player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        PSFromController.println(s);
        Platform.exit();
    }
}
