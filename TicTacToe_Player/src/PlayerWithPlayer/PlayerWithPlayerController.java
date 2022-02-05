/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PlayerWithPlayer;

import Helper_Package.Game;
import Helper_Package.InsideXOGame;
import Helper_Package.Player;
import Helper_Package.RecordedMessages;
import com.google.gson.Gson;
import java.io.PrintStream;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.loginController;
import tictactoe_player.TicTacToe_Player;


public class PlayerWithPlayerController implements Initializable {
    
    boolean gameEnded;
    @FXML
    private Button resume;
    @FXML
    private Button pos1;
    @FXML
    private Button pos2;
    @FXML
    private Button pos3;
    @FXML
    private Button pos6;
    @FXML
    private Button pos5;
    @FXML
    private Button pos4;
    @FXML
    private Button pos7;
    @FXML
    private Button pos8;
    @FXML
    private Button pos9;
    @FXML
    private Label playerSign;
    @FXML
    private Label opponenPlayerSign;
    @FXML
    private Label homeNameLabel;
    @FXML
    private Label opponentNameLabel;
    @FXML
    private Label gameResult;
    @FXML
    private TextArea textScreenMessanger;
    @FXML
    private Button sendButton;
    @FXML
    private TextField textAreaMessanger;
    
   PrintStream PSFromController;
    boolean myturn;
    String myUserName;
    String opponentUserName;
    int gameID;
    char playerSymbol, opponentSymbol;
    Integer playerPos;
    Vector<Integer> playerMoves= new Vector<>();
    Vector<Integer> opponentMoves= new Vector<>();
    Vector<Integer> movesPool= new Vector<>();
    int numOfMoves;
    public static boolean turnOffNotification = true;
    // Winning Prositions 
    boolean isWinningPosition(Vector<Integer> moves){
        boolean winFlag = false;
        Integer []  topRow = {1, 2, 3};
        Integer []  midRow = {4, 5, 6};
        Integer []  botRow = {7, 8, 9};
        Integer []  leftCol = {1, 4, 7};
        Integer []  midCol = {2, 5, 8};
        Integer []  rightCol = {3, 6, 9};
        Integer []  mainDiag = {1, 5, 9};
        Integer []  secondaryDiag = {3, 5, 7};
        Integer [][] winningCases = {
                topRow, midRow, botRow,
                leftCol, midCol, rightCol,
                mainDiag, secondaryDiag
        };

        int i=0;
        while(!winFlag && i<winningCases.length){
            if(moves.containsAll(Arrays.asList(winningCases[i])))
                winFlag = true;
            i++;
        }
        return winFlag;
    }
    
    // initiate a new game setup 
    public void init(){
        playerMoves.clear();
        opponentMoves.clear();
        movesPool.clear();
        turnOffNotification = true;
        
        // Turns Logic
        if(loginController.myTurn){
            playerSymbol = 'X';
            opponentSymbol = 'O';
            gameResult.setText("Your Turn");
        }
        else
        {
            playerSymbol = 'O';
            opponentSymbol = 'X';
            gameResult.setText("Their Turn");
        }
        for(int i=0; i<9; i++)
        movesPool.add(i+1);
        numOfMoves = 0;
        gameEnded = false;
    }

    public void displayMove(Integer position, char symbol) 
    // will be used in playMove(ActionEvent event)
    {
        switch (position) {
            case 1:
                pos1.setText(Character.toString(symbol));
                break;
            case 2:
                pos2.setText(Character.toString(symbol));
                break;
            case 3:
                pos3.setText(Character.toString(symbol));
                break;
            case 4:
                pos4.setText(Character.toString(symbol));
                break;
            case 5:
                pos5.setText(Character.toString(symbol));
                break;
            case 6:
                pos6.setText(Character.toString(symbol));
                break;
            case 7:
                pos7.setText(Character.toString(symbol));
                break;
            case 8:
                pos8.setText(Character.toString(symbol));
                break;
            case 9:
                pos9.setText(Character.toString(symbol));
                break;
            default:
                break;
        }
    }
    
    // PlayeMove Handeling 
    @FXML
    void playMove(ActionEvent event) {
        if (myturn)
        {
            System.out.println("is myturn");
            if (!gameEnded) {
                // Player move
                System.out.println("game not ended");
                playerPos = Integer.parseInt(((Control) event.getSource()).getId());
                if (!movesPool.isEmpty() && movesPool.contains(playerPos)) {
                    System.out.println("I can play here");
                    displayMove(playerPos, playerSymbol);
                    movesPool.remove(playerPos);
                    playerMoves.add(playerPos);
                    sendMyMove();
                    numOfMoves++;
                    myturn = false;
                    gameResult.setText("Their Turn");
                    if(isWinningPosition(playerMoves)){
                        System.out.println("You win! 😃");
                        gameResult.setText("You Win! 😃");
                        gameEnded = true;
                        myturn = false;
                        reportGameEnding(true);
                    }
                }
                if (!gameEnded && numOfMoves >= 9){
                    System.out.println("It's a draw!");
                    gameResult.setText("It's a Draw! ");
                    gameEnded = true;
                    myturn = false;
                    //reportGameEnding(false);                    
                }
            }            
        }
    }

    void reportGameEnding(boolean state){
        InsideXOGame xoMessage = new InsideXOGame(RecordedMessages.GAME_GOT_FINISHED, new Player(myUserName), new Game(gameID, myUserName, opponentUserName));
        xoMessage.setOperationResult(state);
        Gson g = new Gson();
        String messageend = g.toJson(xoMessage);
        System.out.println(messageend);
        PSFromController.println(messageend);
    }
    public void recieveGameEnding(){
        gameEnded = true;
        myturn = false;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PSFromController = TicTacToe_Player.ps;
        gameResult.setText("");
        init();
        myturn = loginController.myTurn;
    }

    void clearAll ()
    {
        pos1.setText("");
        pos2.setText("");
        pos3.setText("");
        pos4.setText("");
        pos5.setText("");
        pos6.setText("");
        pos7.setText("");
        pos8.setText("");
        pos9.setText("");
        gameResult.setText("");
    }
    void sendMyMove(){
        Game game = new Game(gameID, myUserName, opponentUserName);
        InsideXOGame xoMessage = new InsideXOGame(RecordedMessages.GAME_PLAY_MOVE, game, playerPos, playerSymbol);
        Gson g = new Gson();
        System.out.println(g.toJson(xoMessage));
        PSFromController.println(g.toJson(xoMessage));
        System.out.println(g.toJson(xoMessage.getFieldPosition())); // testing xoMessage.getFieldPosition
    }
    
    public void setIDs(int gameID, String myUserName, String opponentUserName){
        this.gameID = gameID;
        this.myUserName = myUserName;
        this.opponentUserName = opponentUserName;
        if(myturn){
            Platform.runLater(() -> {
                homeNameLabel.setText(myUserName);
                opponentNameLabel.setText(opponentUserName);
                gameResult.setText("Your Turn");
            });
        }
        else{
            Platform.runLater(() -> {
                homeNameLabel.setText(opponentUserName);
                opponentNameLabel.setText(myUserName);
                gameResult.setText("Their Turn");
            });
        }


    }

    public void printOpponentMove(Integer playerPos,boolean _myturn){
        if (!movesPool.isEmpty() && movesPool.contains(playerPos)) {
            opponentMoves.add(playerPos);
            if(movesPool.contains(playerPos)){
                movesPool.remove(playerPos);                
            }
            numOfMoves++;
            if(!gameEnded){
                displayMove(playerPos, opponentSymbol);
                myturn = _myturn;
                if (myturn)
                {
                    gameResult.setText("Your Turn");
                }
                else
                {
                    gameResult.setText("Their Turn");
                }
            }
        }
        if(isWinningPosition(opponentMoves)){
            System.out.println("You Lose! 🙁");
            gameResult.setText("You Lose! 🙁");
            gameEnded = true;
            myturn = false;
        }
        if (!gameEnded && numOfMoves >= 9){
            System.out.println("It's a draw!");
            gameResult.setText("It's a Draw! ");
            gameEnded = true;
            myturn = false;
        }

    }
    @FXML
    private void back(ActionEvent event)
    {
        
        InsideXOGame xoMsg = new InsideXOGame(RecordedMessages.BACK_FROM_ONLINE,new Player(myUserName),new Game(myUserName, opponentUserName));
        xoMsg.getGame().setIsFinished(gameEnded);
        xoMsg.getPlayer().setIsMyTurn(myturn);
        Gson g = new Gson();
        PSFromController.println(g.toJson(xoMsg));
        turnOffNotification = false;
    }


    @FXML
    private void sendMessage(ActionEvent event) {
        String chatingMessage = "["+ loginController.username +"]: "+ textAreaMessanger.getText();
        textAreaMessanger.setText("");
        textScreenMessanger.appendText(chatingMessage+"\n");
        Game onlineGameChating = new Game(myUserName, opponentUserName, chatingMessage);
        InsideXOGame xointerface = new InsideXOGame(RecordedMessages.CHAT_PLAYERS_WITH_EACH_OTHERS, onlineGameChating);
        Gson g = new Gson();
        String message = g.toJson(xointerface);
        System.out.println(message);
        PSFromController.println(message); 
    }

    @FXML
    private void resume(ActionEvent event) {
        Game gamelog = new Game(gameID, myUserName, opponentUserName);
        InsideXOGame xoMsg = new InsideXOGame(RecordedMessages.RESUME, gamelog);
        Gson g = new Gson();
        PSFromController.println(g.toJson(xoMsg));
    }
    
    public void displayMovesOnBoard (char[] savedGame, String homePlayer, int gameID, boolean  myturn)
    {
        
        clearAll();
        init();
        this.myturn = myturn;
        int countHome = 0;
        int countOpponent = 0;
        this.gameID = gameID;
        if(myUserName.equals(homePlayer))
        {
            playerSymbol = 'X';
        //    playerSign.setText(Character.toString(playerSymbol));
            opponentSymbol = 'O';
        //    opponenPlayerSign.setText(Character.toString(opponentSymbol));
        }
        else
        {
            playerSymbol = 'O';
        //    playerSign.setText(Character.toString(playerSymbol) + "hiiii");            
            opponentSymbol = 'X';
        //    opponenPlayerSign.setText(Character.toString(opponentSymbol));
        }
        char s = ' ';
        for(int i=0;i<9;i++)
        {
            Integer move = i+1;
            if(savedGame[i] == '-' )
            {
                savedGame[i] = s ; 
            }
            else if(savedGame[i] == playerSymbol){
                playerMoves.add(move);
                movesPool.remove(move);
                countHome++;
                numOfMoves++;
            }
            else if(savedGame[i] == opponentSymbol){
                opponentMoves.add(move);
                movesPool.remove(move);
                countOpponent++;
                numOfMoves++;
            }
        }
        
//        if(countHome <= countOpponent)
//        {
//            myturn = true;
//            gameResult.setText("Your Turn");            
//        }
//        else
//        {
//            myturn = false;
//            gameResult.setText("Their Turn");              
//        }
            
        if(myturn){
            gameResult.setText("Your Turn"); 
        }else{
            gameResult.setText("Their Turn");
        }
        
        pos1.setText(Character.toString(savedGame[0]));
        pos2.setText(Character.toString(savedGame[1]));
        pos3.setText(Character.toString(savedGame[2]));
        pos4.setText(Character.toString(savedGame[3]));
        pos5.setText(Character.toString(savedGame[4]));
        pos6.setText(Character.toString(savedGame[5]));
        pos7.setText(Character.toString(savedGame[6]));
        pos8.setText(Character.toString(savedGame[7]));
        pos9.setText(Character.toString(savedGame[8]));  
    }
       
    public void printMessage(InsideXOGame xo)
    {
        if (!xo.getGame().getMessage().equals("IS_LEFT"))
        {
            textScreenMessanger.appendText(xo.getGame().getMessage()+"\n");
            System.out.println(textScreenMessanger.getText());
        }
        else
        {
            gameResult.setText("Opponent Left The Session");
        }
    }
    
    public void cancelOrEnableResume(boolean state)
    {
        resume.setDisable(state);
    }
    
    private void minimize(ActionEvent event) {
        ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }

    private void exit(ActionEvent event) {
        Player player=new Player();
        player.setUserName(loginController.username);
        InsideXOGame xoMessage =new InsideXOGame (RecordedMessages.LOGOUT,player);
        Gson g = new Gson();
        String s = g.toJson(xoMessage);
        PSFromController.println(s);        
        Platform.exit();
    } 
}
