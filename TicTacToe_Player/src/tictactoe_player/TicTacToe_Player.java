/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_player;

import Helper_Package.InsideXOGame;
import Helper_Package.RecordedMessages;
import PlayerWithPlayer.PlayerWithPlayerController;
import com.google.gson.Gson;
import invitationpopup.InvitationPopUpController;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import login.loginController;
import onlinepopup.OnlinePopUpController;
import playerlist.PlayerListController;
import recordlist.RecordListController;
import signup.SignUpController;


public class TicTacToe_Player extends Application {
    PlayerWithPlayerController pwp;
    public static loginController LI;
    public static SignUpController SU;
    public static int score = 0;
    DataInputStream dis;
    public static PrintStream ps;
    Socket mySocket;
    
    @Override
    public void start(Stage stage) throws Exception {
        try{
            mySocket = new Socket("127.0.0.1", 3003);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            new Thread(()->{
                while (true){
                    try {
                        String recivedMsg = dis.readLine();
                        System.out.println(recivedMsg);
                        Gson g = new Gson();
                        InsideXOGame xoMessage;
                        xoMessage = g.fromJson(recivedMsg, InsideXOGame.class); // resciving msg
                        
                        //to switch to selection mode scene
                        if(xoMessage.getTypeOfOperation().equals(RecordedMessages.LOG_IN_ACCEPTED))
                        {
                            Platform.runLater(()->{
                                try {
                                    moveToSelectionScene(stage,xoMessage);
                                } catch (IOException ex) {
                                    System.err.println("No switching");
                                    ex.printStackTrace();
                                }
                            });
                        }
                        
                        if(xoMessage.getTypeOfOperation().equals(RecordedMessages.LOGIN_REJECTED))
                        {
                            LI.displayErrorMessage(); 
                        }
                        
                        //to switch to login scene
                        else if (xoMessage.getTypeOfOperation().equals(RecordedMessages.SIGN_UP_ACCEPTED))
                        {
                            System.err.println("Register here");
                            Platform.runLater(()->{
                            moveToLogInScene(stage);
                            });                           
                        }
                        
                         else if (xoMessage.getTypeOfOperation().equals(RecordedMessages.SIGN_UP_REJECTED))
                        {
                            SU.displayErrorMessage();                          
                        }
                        
                        //to switch to player with computer scene
                        else if(xoMessage.getTypeOfOperation().equals(RecordedMessages.PLAYING_SINGLE_MODE))
                        {
                            Platform.runLater(()->{
                                try 
                                {
                                    moveToPlayWithComputerScene(stage);
                                } 
                                catch (IOException ex) {
                                    System.err.println("couldn't switch");
                                    ex.printStackTrace();
                                }
                            });
                        }
                        
                        //to switch to players list scene
                        else if(xoMessage.getTypeOfOperation().equals(RecordedMessages.RETREVING_PLAYERS_LIST))
                        {
                                Platform.runLater(()->{
                                try {
                                    moveToPlayersListScene(stage, xoMessage);
                                } catch (IOException ex) {
                                    System.err.println("couldn't switch");
                                    ex.printStackTrace();
                                }
                            });              
                        }
                        
                   // **************** start Recording Feature ******************************
                      //to switch to Recorded list scene
                        else if(xoMessage.getTypeOfOperation().equals(RecordedMessages.GOTO_RECORD_LIST))
                        {
                                System.out.println("before runlater in TicTacToe_Player ");
                                Platform.runLater(()->{
                                try {
                                    System.out.println("switch to recorded list ");
                                    moveToGameRecordsList(stage, xoMessage);
                                    // we can envoke recorded games retrive from here
                                } catch (IOException ex) {
                                    System.err.println("couldn't switch");
                                    ex.printStackTrace();
                                }
                            });              
                        }  
                        
                   // **************** end Recording Feature ******************************

                        
                        //to switch to invitation to play a game
                        else if(xoMessage.getTypeOfOperation().equals(RecordedMessages.RECEIVING_INVITATION))
                        {
                           moveToInvitationPopUp(xoMessage);
                        }
                        
                        //to switch to player with player scene
                        else if (xoMessage.getTypeOfOperation().equals(RecordedMessages.INVITATION_ACCEPTED_FROM_SERVER))
                        {
                            Platform.runLater(() -> {
                                moveToPlayerToPlayerScene(stage, xoMessage);
                            });
                        }
                        
                        else if (xoMessage.getTypeOfOperation().equals(RecordedMessages.INVITATION_REJECTED_FROM_SERVER))
                        {
                            
                            loginController.myTurn = false;
                        }
                        
                        //to switch to online pop up scene
                        else if (xoMessage.getTypeOfOperation().equals(RecordedMessages.NEW_PLAYER_LOGGEDIN_POPUP))
                        {
                            moveToOnlinePopUpScene(xoMessage);
                        }
                        
                        //to print the away players XO moves
                        else if(xoMessage.getTypeOfOperation().equals(RecordedMessages.INCOMING_MOVE))
                        {
                            Platform.runLater(() -> {
                                try
                                {
                                    printGameMove(xoMessage);
                                }
                                catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }
                            });
                        }
                        
                        //to print the chat messages in the chat box
                        else if(xoMessage.getTypeOfOperation().equals(RecordedMessages.CHAT_PLAYERS_WITH_EACH_OTHERS_FROM_SERVER))
                        {                    
                            Platform.runLater(() -> {                              
                                PrintMessageOfChatRoom(xoMessage);                                    
                            });
                        }                        
                        
                        //to display XO moves
                        else if(xoMessage.getTypeOfOperation().equals(RecordedMessages.RETRIVEMOVES ))
                        {
                            Platform.runLater(() -> {
                                try
                                {
                                    if (xoMessage.getGame().getGameId() != 0)
                                        {
                                            DisplayMoves(xoMessage);
                                            //cancelResume(false);
                                        }
                                        else
                                        {
                                            //cancelResume(true);
                                        }
                                }
                                catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }
                            });
                        }
                        
                        //to indicate that game finished and show the results
                        else if(xoMessage.getTypeOfOperation().equals(RecordedMessages.GAME_GOT_FINISHED_SECCUSSFULLY))
                        {
                            pwp.recieveGameEnding();
                        }
                        
                        //to back to the selection scene if the user press back button
                        else if(xoMessage.getTypeOfOperation().equals(RecordedMessages.BACK_FROM_SERVER))
                        {
                         
                            Platform.runLater(() -> {
                                try {                                    
                                    moveToSelectionScene(stage,xoMessage);
                                } catch (IOException ex) {
                                    Logger.getLogger(TicTacToe_Player.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });  
                        }
                        else if(xoMessage.getTypeOfOperation().equals("gameIsNotSetted")){
                            System.err.println("gameIsNotSetted");
                        }
                    }
                    catch ( NullPointerException  |  IOException ex ) {
                         try
                         {
                            this.dis.close();
                            ps.close();
                            this.mySocket.close();
                            break;                             
                         }
                         catch (IOException exception )
                         {
                             exception.printStackTrace();
                         }
                    }
                }
            }).start();
        } catch (IOException ex){
            System.err.println("Server Is Off");
            ex.printStackTrace();
        }

        //loader to start login page
        FXMLLoader loginInLoader=new FXMLLoader();
        loginInLoader.setLocation(getClass().getResource("/login/login.fxml"));
        Parent root = loginInLoader.load();
        LI = loginInLoader.getController();
        
        //main root scene which will start firstly 
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tic Tac Toe Game");
        stage.setResizable(false);
//        stage.getIcons().add(new Image("logo.png"));
        stage.show();
    }
      
    //**functions to move from GUI to another GUI**//
    //1st function to move to selection mode scene
    void moveToSelectionScene(Stage stage, InsideXOGame xoMessage) throws IOException{
        score = xoMessage.getPlayer().getScore();
        System.out.println(xoMessage.getPlayer().getScore());
        FXMLLoader selectionModeLoader=new FXMLLoader();
        selectionModeLoader.setLocation(getClass().getResource("/selectionmode/SelectionMode.fxml"));
        Parent selectionModeRoot = selectionModeLoader.load();
        Scene selectionModeScene = new Scene(selectionModeRoot);
        stage.hide();
        stage.setScene(selectionModeScene);
        stage.show();
    }
    
    //2nd function to move to log in scene
    void moveToLogInScene(Stage stage){
        try
        {
           FXMLLoader logInLoader = new FXMLLoader();
           logInLoader.setLocation(getClass().getResource("/login/login.fxml"));
           Parent logInRoot = logInLoader.load();
           LI = logInLoader.getController();
           Scene logInScene = new Scene(logInRoot);
           stage.hide();
           stage.setScene(logInScene);
           stage.show();                     
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }   
    }
    
    //3rd function to move to play with computer scene
    void moveToPlayWithComputerScene(Stage stage)throws IOException{
        FXMLLoader playWithComputerLoader = new FXMLLoader();
        playWithComputerLoader.setLocation(getClass().getResource("/playwithcomputer/PlayWithComputer.fxml"));
        Parent playWithComputerRoot = playWithComputerLoader.load();
        Scene playWithComputerScene = new Scene(playWithComputerRoot);
        stage.hide();
        stage.setScene(playWithComputerScene);
        stage.show();
    }
    
    //4th function to move to online players list scene
    void moveToPlayersListScene(Stage stage, InsideXOGame xoMessage) throws IOException{
        FXMLLoader playersListLoader = new FXMLLoader();
        playersListLoader.setLocation(getClass().getResource("/playerlist/PlayerList.fxml"));
        Parent playerListRoot = playersListLoader.load();
        
        //this is object from PlayersListControler used to call setAllPlayers function
        //to show all players on the table while loading this scene
        PlayerListController plc = playersListLoader.getController();
        plc.setAllPlayers(xoMessage);
        Scene playerListScene = new Scene(playerListRoot);
        stage.hide();
        stage.setScene(playerListScene);
        stage.show();
    }
    
    // ******************* start Move To Recorded List ********************* 
    void moveToGameRecordsList(Stage stage, InsideXOGame xoMessage) throws IOException{
        FXMLLoader RecordsListLoader = new FXMLLoader();
        RecordsListLoader.setLocation(getClass().getResource("/recordlist/RecordList.fxml"));
        Parent recordsListRoot = RecordsListLoader.load();
        
        //this is object from RecordsListControler used to call *setAllPlayers* setAllReacords function
        //to show all *players* Records on the table while loading this scene
        RecordListController rlc = RecordsListLoader.getController();
        rlc.setAllRecords(xoMessage);
        System.out.println("ttt records status" + xoMessage.records.isEmpty());

        Scene RecordsListScene = new Scene(recordsListRoot);
        stage.hide();
        stage.setScene(RecordsListScene);
        stage.show();
    }
    // ******************* start Move To Recorded List ********************* 

    
    
    //5th function to move to invitation pop up
    void moveToInvitationPopUp(InsideXOGame xoMessage){
     Platform.runLater(()->{
        try{
           FXMLLoader invitationPopUpLoader = new FXMLLoader();
           invitationPopUpLoader.setLocation(getClass().getResource("/invitationpopup/InvitationPopUp.fxml"));
           Parent invitationPopUpRoot = invitationPopUpLoader.load();
           InvitationPopUpController popUpInvitation = invitationPopUpLoader.getController();
           Scene invitationPopUpScene = new Scene(invitationPopUpRoot);
           Stage invitationPopUpStage = new Stage();
           
            //this is object from invitation pop up Controller used to call getAwayPlayerName function
            //to get away player information
           popUpInvitation.getAwayplayerName(xoMessage,invitationPopUpStage);                  
           invitationPopUpStage.hide();
           invitationPopUpStage.initStyle(StageStyle.UNDECORATED);
           invitationPopUpStage.setScene(invitationPopUpScene); 
           invitationPopUpStage.show(); 
        }
        catch (IOException ex) 
            {
              Logger.getLogger(TicTacToe_Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
    }
    
    //6th function to move to multiplayers scene
    void moveToPlayerToPlayerScene(Stage stage, InsideXOGame xoMessage){
        try
        {
            FXMLLoader playerWithPlayerLoader=new FXMLLoader();
            playerWithPlayerLoader.setLocation(getClass().getResource("/PlayerWithPlayer/PlayerWithPlayer.fxml"));
            Parent playerWithPlayerRoot = playerWithPlayerLoader.load();
            
            //this is object from player with player Controller used to call setIDs function
            //to set game IDs corresponding to their players
            pwp=playerWithPlayerLoader.getController();
            pwp.setIDs(xoMessage.getGame().getGameId(), loginController.username, xoMessage.getGame().getAwayPlayer());
            Scene playerWithPlayerScene = new Scene(playerWithPlayerRoot);
            stage.hide();
            stage.setScene(playerWithPlayerScene);
            stage.show();                     
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }  
    }

    //7th function to move to online pop up person scene
    void moveToOnlinePopUpScene(InsideXOGame xoMessage){
        if(!xoMessage.getPlayer().getUserName().equals(loginController.username))
        {
            Platform.runLater(()->{
            try {
                FXMLLoader onlinePopUpLoader = new FXMLLoader();
                onlinePopUpLoader.setLocation(getClass().getResource("/onlinepopup/OnlinePopUp.fxml"));
                Parent onlinePopUpRoot = onlinePopUpLoader.load();
                
                //this is object from OnlinePopUpController used to call getUserName function
                //to show the player that become online
                OnlinePopUpController popUp = onlinePopUpLoader.getController();
                popUp.getUserName(xoMessage.getPlayer().getUserName());
                
                Scene onlinePopUpScene = new Scene(onlinePopUpRoot);
                Stage onlinePopUpStage = new Stage();
                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                onlinePopUpStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 500);
                onlinePopUpStage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 150);
                onlinePopUpStage.initStyle(StageStyle.UNDECORATED);
                onlinePopUpStage.hide(); 
                onlinePopUpStage.setScene(onlinePopUpScene);
                onlinePopUpStage.show(); 
                PauseTransition delay = new PauseTransition(Duration.seconds(4));
                delay.setOnFinished( event ->  onlinePopUpStage.close() );
                delay.play();
              }catch (IOException ex) {
                    Logger.getLogger(TicTacToe_Player.class.getName()).log(Level.SEVERE, null, ex);
              }
          }); 

        }       
    }

    //function to print the away player move
    void printGameMove(InsideXOGame xoMessage){
        pwp.printOpponentMove(xoMessage.getFieldPosition(),true);
    }
    
    //function to print the message inside the chatbox
    void PrintMessageOfChatRoom(InsideXOGame xoMessage)
    {
        pwp.printMessage(xoMessage);
        //I will handle it later -for fun-
        
        String path = "message.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaplayer = new MediaPlayer(media);
        mediaplayer.play();
        
    }
    
    //function to display XO signs moves in the screen
    void  DisplayMoves(InsideXOGame xoMessage)
    {
        System.out.println(xoMessage.getGame().getHomeplayer());
        System.out.println(xoMessage.getGame().getAwayPlayer());
        pwp.displayMovesOnBoard(xoMessage.getGame().getSavedGame(),
                                xoMessage.getGame().getHomeplayer(),
                                xoMessage.getGame().getGameId(),
                                xoMessage.getPlayer().isIsMyTurn());
    }
    
    void cancelResume(boolean state)
    {
        pwp.cancelOrEnableResume(state);
    }
    @Override
    public void stop(){
        System.out.println("Stage is closing");
        
       
        Platform.exit();

    }
    public static void main(String[] args) {
         Application.launch(args);
    } 
}
