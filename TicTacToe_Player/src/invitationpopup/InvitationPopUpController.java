/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invitationpopup;

import Helper_Package.InsideXOGame;
import Helper_Package.RecordedMessages;
import com.google.gson.Gson;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.loginController;
import tictactoe_player.TicTacToe_Player;


public class InvitationPopUpController implements Initializable {

    @FXML
    private Label labelName;
    PrintStream PSFromController;
    String homeplayer = loginController.username;
    String opponentPlayer;
    InsideXOGame xoMessage;
    Stage stage;
    // poptextinvitation
    public void getAwayplayerName(InsideXOGame xoMessage,Stage stage){
        this.xoMessage = xoMessage;
        this.opponentPlayer = xoMessage.getGame().getHomeplayer();
        this.stage = stage;
        labelName.setText("You're invited to play with: "+ opponentPlayer);
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PSFromController = TicTacToe_Player.ps;
    }    

    @FXML
    private void accept(ActionEvent event) {
        xoMessage.setTypeOfOperation(RecordedMessages.INVITATION_ACCEPTED);
        Gson g = new Gson();
        String s = g.toJson(xoMessage);
        PSFromController.println(s);
        stage.hide();
        
        String path = "sound.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaplayer = new MediaPlayer(media);
        mediaplayer.play();
        
        
    }

    @FXML
    private void decline(ActionEvent event) {
       xoMessage.getGame().setAwayPlayer(homeplayer);
       xoMessage.setTypeOfOperation(RecordedMessages.INVITATION_REJECTED);
       Gson g = new Gson();
       String s = g.toJson(xoMessage);
       PSFromController.println(s);
       stage.hide();
    }
    
}
