/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playwithcomputer;

import Helper_Package.InsideXOGame;
import Helper_Package.Player;
import Helper_Package.RecordedMessages;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import login.loginController;
import selectlevel.SelectLevelController;
import tictactoe_player.TicTacToe_Player;


public class PlayWithComputerController implements Initializable {
    
    private Label label;
    @FXML
    private Label playerSign;
    @FXML
    private Label computerSign;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label gameResult;
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
    
    PrintStream PSFromcontroller;
    String myUserName = loginController.username;
    char playerSymbol, AISymbol;
    Vector<Integer> playerMoves= new Vector<>();
    Vector<Integer> AIMoves= new Vector<>();
    Vector<Integer> movesPool= new Vector<>();
    int numOfMoves;
    char [][] board =   {{ '_', '_', '_' }, 
			 { '_', '_', '_' }, 
		         { '_', '_', '_' }};
    int getAIMove(){
        int intMove=0;
        GFG.Move gridMove=GFG.findBestMove(board);
        intMove=gridToInt(gridMove);
        return intMove;
    }
    //from x,y to int,int
    int gridToInt(GFG.Move gridMove){
        int outInt=0;
        if(gridMove.row==0 && gridMove.col==0)
            outInt=1;
        else if(gridMove.row==0 && gridMove.col==1)
            outInt=2;
        else if(gridMove.row==0 && gridMove.col==2)
            outInt=3;  
        else if(gridMove.row==1 && gridMove.col==0)
            outInt=4;
        else if(gridMove.row==1 && gridMove.col==1)
            outInt=5; 
        else if(gridMove.row==1 && gridMove.col==2)
            outInt=6;  
        else if(gridMove.row==2 && gridMove.col==0)
            outInt=7; 
        else if(gridMove.row==2 && gridMove.col==1)
            outInt=8;
        else if(gridMove.row==2 && gridMove.col==2)
            outInt=9;        
        return outInt;
    }
    //set symbole in position
    void regMove(int position, char symbol){
        switch (position){
            case 1:
                board[0][0]=symbol;
                break;
            case 2:
                board[0][1]=symbol;
                break;
            case 3:
                board[0][2]=symbol;
                break;
            case 4:
                board[1][0]=symbol;
                break;
            case 5:
                board[1][1]=symbol;
                break;
            case 6:
                board[1][2]=symbol;
                break;
            case 7:
                board[2][0]=symbol;
                break;
            case 8:
                board[2][1]=symbol;
                break;
            case 9:
                board[2][2]=symbol;
                break;
            default:
                break;
        }
        movesPool.remove((Integer) position);
    } 
     
    //to set symbole for ai and player
     char getRndSymbol(){
        Random r = new Random();
        String symbols = "XO";
        return symbols.charAt(r.nextInt(symbols.length()));
    }
    boolean gameEnded;
    //from the video
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
    public void init(){
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                board[i][j]='_';
        playerMoves.clear();
        AIMoves.clear();
        movesPool.clear();
        AISymbol = 'O';
        playerSymbol = 'X';
/*
        playerSign.setText(Character.toString(playerSymbol));
        computerSign.setText(Character.toString(AISymbol));
*/
        for(int i=0; i<9; i++)
            movesPool.add(i+1);
        numOfMoves = 0;
        gameEnded = false;
    }
    
    //function to set random move
    Integer getRndMove() {
        int number = (int) (Math.random() * movesPool.size());
        return movesPool.get(number);
    }
    
    void displayMove(Integer position, char symbol){
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
    
    @FXML
    private void playMove(ActionEvent event) {
        if (!gameEnded) {
            // Player move
            Integer playerPos = Integer.parseInt(((Control) event.getSource()).getId()); //return id of the played position
            if (!movesPool.isEmpty() && movesPool.contains(playerPos)) {    //moves pool is empty ya3ni lesa feh mkan , by7tawi 3la el player position deh
                displayMove(playerPos, playerSymbol);
                movesPool.remove(playerPos);  // remove it from pool
                regMove(playerPos, playerSymbol);                
                playerMoves.add(playerPos);
                numOfMoves++;
                if(isWinningPosition(playerMoves)){
                    System.out.println("You win! :D");
                    gameResult.setText("You Win! :D");
                    gameEnded = true;
                    reportGameEnding();
                }
                   // AI move
                if (!movesPool.isEmpty() && !gameEnded) {
                    Integer AIPos=null; //if comp loss dont perform ant other moves
                    if(SelectLevelController.gameLevel==0){
                        AIPos = getRndMove();
                    }
                    else if(SelectLevelController.gameLevel==1){
                        AIPos = getAIMove();
                        regMove(AIPos, AISymbol);                    
                    }

                    displayMove(AIPos, AISymbol);
                    movesPool.remove(AIPos);
                    AIMoves.add(AIPos);
                    numOfMoves++;
                    if(isWinningPosition(AIMoves)){
                        System.out.println("You Lose! :(");
                        gameResult.setText("You Lose! :(");
                        gameEnded = true;
                    }
                }
                if (numOfMoves >= 9 && !gameEnded){
                    System.out.println("It's a draw!"); //t3adel
                    gameResult.setText("It's a Draw! ");
                    gameEnded = true;
                }
            }

        }
    }
    
    void reportGameEnding(){
        Player player = new Player(myUserName);
        InsideXOGame xoMsg = new InsideXOGame(RecordedMessages.SINGLE_MODE_GAME_FINISHED, player);
        PSFromcontroller.println(new Gson().toJson(xoMsg));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PSFromcontroller = TicTacToe_Player.ps;
        init();
        userNameLabel.setText(myUserName);
    }    

    @FXML
    private void back(ActionEvent event) {
        try
        {
            FXMLLoader selectionModeLoader=new FXMLLoader();
            selectionModeLoader.setLocation(getClass().getResource("/selectionmode/SelectionMode.fxml"));
            Parent  selectionModeRoot = selectionModeLoader.load();
            Scene selectionModeScene = new Scene( selectionModeRoot);
            Stage selectionModeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            selectionModeStage.hide();
            selectionModeStage.setScene(selectionModeScene);
            selectionModeStage.show();            
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        } 
    }

    @FXML
    private void restart(ActionEvent event) {
        init();
        clearAll();
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
    
    private void minimize(ActionEvent event) {
        ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }

    private void exit(ActionEvent event) {
        Player player=new Player();
        player.setUserName(loginController.username);
        InsideXOGame xoMessage =new InsideXOGame (RecordedMessages.LOGOUT,player);
        Gson g = new Gson();
        String s = g.toJson(xoMessage);
        PSFromcontroller.println(s);        
        Platform.exit();
    }
}
