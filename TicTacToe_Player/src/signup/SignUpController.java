/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signup;

import Helper_Package.InsideXOGame;
import Helper_Package.Player;
import Helper_Package.RecordedMessages;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tictactoe_player.TicTacToe_Player;


public class SignUpController implements Initializable {
    PrintStream PSFromController;

    @FXML
    private TextField userName;
    @FXML
    private TextField Email;
    @FXML
    private PasswordField Password;
    @FXML
    private PasswordField confirmationPassword;
    @FXML
    private Label errorMessage;
    @FXML
    private Text checkPassword;
    @FXML
    private Text checkcConfirmationPassword;
    @FXML
    private Text checkUserName;
    @FXML
    private Text checkEmail;

    //function to check the sign up Credentials
    public boolean checkCredentials()
    {
        boolean flag = true;
        checkUserName.setVisible(false);
        checkPassword.setVisible(false);
        checkEmail.setVisible(false);
        checkcConfirmationPassword.setVisible(false);
        if(userName.getText().equals(""))
        {
            checkUserName.setVisible(true);
            flag = false;
        }
        if(Password.getText().equals(""))
        {
            checkPassword.setVisible(true); 
            flag = false;
        }
        if(Email.getText().equals(""))
        {
            checkEmail.setVisible(true); 
            flag = false;
        }
        if(((confirmationPassword.getText()).equals(Password.getText())) == false)
        {
            checkcConfirmationPassword.setVisible(true); 
            flag = false;
        }
        return flag;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PSFromController = TicTacToe_Player.ps;
        errorMessage.setVisible(false);
    }
    
    //function to handle an action with server while pressing on signUp button
    @FXML
    private void register(ActionEvent event)throws IOException {
        //output in variable b because the previous function return boolean flag
            if(checkCredentials()){
            Player player=new Player(userName.getText(),Password.getText(),Email.getText());
            InsideXOGame xointerface =new InsideXOGame (RecordedMessages.SIGNUP,player);
            Gson g = new Gson();
            String s = g.toJson(xointerface);
            PSFromController.println(s);
        }
    }
    
    //function will be called if the server respond with signup rejected
    public void displayErrorMessage(){
        errorMessage.setVisible(true);
    }

    //to return back to login scene while pressing register button
    @FXML
    private void back(ActionEvent event)throws IOException {
        FXMLLoader logInLoader = new FXMLLoader();
           logInLoader.setLocation(getClass().getResource("/login/login.fxml"));
           Parent logInRoot = logInLoader.load();
           TicTacToe_Player.LI = logInLoader.getController();
           Scene logInScene = new Scene(logInRoot);
           Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           loginStage.hide();
           loginStage.setScene(logInScene);
           loginStage.show();
    }
    
    
}

 
