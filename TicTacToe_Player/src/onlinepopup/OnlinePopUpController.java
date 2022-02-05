/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinepopup;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;


public class OnlinePopUpController implements Initializable {

    @FXML
//    private Text poptext;
    String uname;

    @FXML
    private Label userNameLabel ;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void getUserName(String username) {
        uname = username;
        userNameLabel.setText(uname + " is Online now");
    }

}
