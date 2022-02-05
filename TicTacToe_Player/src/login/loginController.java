/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tictactoe_player.TicTacToe_Player;

/**
 *
 * @author Ahmed Ibrahim
 */
public class loginController implements Initializable {
    //why username static?
    //to be accesed by the class name to get username at any other scene
    public static String username;
    public static boolean myTurn = false;
    private String password;
    @FXML
    private Text checkusername;
    @FXML
    private Text checkpassword;
    Stage window;
    PrintStream PSFromController;
    @FXML
    private Label errorMessage;
    @FXML
    private TextField loginUserName;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;
    
     //to intialize the login controller with printstream refer to main class printStream
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PSFromController = TicTacToe_Player.ps;
        errorMessage.setVisible(false);
    }
    //login button function to validate the inputs from user
    //also to send message to server to say person want to log in check it's credintional
    @FXML
    private void login(ActionEvent event) {
        username = loginUserName.getText();
        password = loginPassword.getText();
       if(username.equals("") || password.equals(""))
       {
            if (username.equals(""))
            {
                checkusername.setVisible(true);
            }
            else
            {
                checkusername.setVisible(false);
            }
            if(password.equals(""))
            {
                checkpassword.setVisible(true);
            }
            else
            {
                checkpassword.setVisible(false);
            }
       }
       else
       {
            checkusername.setVisible(false);
            checkpassword.setVisible(false);
            Player player=new Player();
            player.setUserName(username);
            player.setPassword(password);
            InsideXOGame xoMessage =new InsideXOGame (RecordedMessages.LOGIN,player);
            Gson g = new Gson();
            String s = g.toJson(xoMessage);
            PSFromController.println(s);
       }
    }
    
    //function will be called if the server respond with login rejected
    public void displayErrorMessage(){
        errorMessage.setVisible(true);
    }
    
    //to move to sign up scene if the user press on sign up button
    @FXML
    void signup(ActionEvent event)throws IOException {
        FXMLLoader signupLoader=new FXMLLoader();
        signupLoader.setLocation(getClass().getResource("/signup/SignUp.fxml"));
        Parent  signupLoaderRoot = signupLoader.load();
        TicTacToe_Player.SU = signupLoader.getController();
        Scene signupScene = new Scene(signupLoaderRoot);
        Stage signupStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        signupStage.hide();
        signupStage.setScene(signupScene);
        signupStage.show();
    }
    

    

    
}
