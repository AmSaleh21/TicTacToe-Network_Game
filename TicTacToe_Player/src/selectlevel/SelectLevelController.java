/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selectlevel;

import Helper_Package.InsideXOGame;
import Helper_Package.Player;
import Helper_Package.RecordedMessages;
import com.google.gson.Gson;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import login.loginController;
import tictactoe_player.TicTacToe_Player;


public class SelectLevelController implements Initializable {
    
    private Label label;
    PrintStream PSFromController;
    public static int gameLevel=0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PSFromController = TicTacToe_Player.ps;
    }    

    @FXML
    private void hard(ActionEvent event) {
        Player player=new Player();
        player.setUserName(loginController.username);
        InsideXOGame xoMessage =new InsideXOGame (RecordedMessages.PLAYING_SINGLE_MODE,player);
        Gson g = new Gson();
        String s = g.toJson(xoMessage);
        PSFromController.println(s); 
        gameLevel=1;
    }

    @FXML
    private void easy(ActionEvent event) {
        Player player=new Player();
        player.setUserName(loginController.username);
        InsideXOGame xoMessage =new InsideXOGame (RecordedMessages.PLAYING_SINGLE_MODE,player);
        Gson g = new Gson();
        String s = g.toJson(xoMessage);
        PSFromController.println(s); 
        gameLevel=0;
    }

    @FXML
    private void medium(ActionEvent event) {
        Player player=new Player();
        player.setUserName(loginController.username);
        InsideXOGame xoMessage =new InsideXOGame (RecordedMessages.PLAYING_SINGLE_MODE,player);
        Gson g = new Gson();
        String s = g.toJson(xoMessage);
        PSFromController.println(s); 
        gameLevel=0;
    }
    
     @FXML
    private void minimize(ActionEvent event) {
        ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void exit(ActionEvent event) {
        Player player=new Player();
        player.setUserName(loginController.username);
        InsideXOGame xointerface =new InsideXOGame (RecordedMessages.LOGOUT,player);
        Gson g = new Gson();
        String s = g.toJson(xointerface);
        PSFromController.println(s);        
        Platform.exit();
    }
}
