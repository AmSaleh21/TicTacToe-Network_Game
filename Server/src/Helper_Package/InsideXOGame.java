/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper_Package;

import java.util.Vector;


public class InsideXOGame {
    private String typeOfOperation = null; //operations like signin signup
    private Boolean operationResult;       // resulted operation from server
    public Vector<Player> players=null;    //vector to hold all players
    private Player player = null;          //object from player class
    public Vector<Game> records= new Vector<>();       //vector to hold all records
    private Game game = null;              //object from game class
    private int fieldPosition;             //number of XO Quarter
    private char signPlayed;               //X or O Sign
    
    //constructors
    public InsideXOGame(){
        players = new Vector<>();
    }
    // ********** record constructor ***************
    public InsideXOGame(int _dummy){
        records = new Vector<>();
    }
    
    public void setRecords(Vector<Game> _records){
        records = _records;
    }
   // ********** record constructor ***************
    
    public InsideXOGame(String _typeOfOperation){
        typeOfOperation = _typeOfOperation;
    }
    
    public InsideXOGame(String _typeOfOperation, Player _player){
        typeOfOperation = _typeOfOperation;
        player          = _player;
    }
    
    public InsideXOGame(String _typeOfOperation, Game _game){
        typeOfOperation = _typeOfOperation;
        game            = _game;
    }
    
    public InsideXOGame(String _typeOfOperation, Player _player, Game _game){
        typeOfOperation = _typeOfOperation;
        player          = _player;
        game            = _game;
    }

    
    
    public InsideXOGame(String _typeOfOperation, Game _game, int _fieldPosition, char _signPlayed){
        typeOfOperation = _typeOfOperation;
        game            = _game;
        fieldPosition   = _fieldPosition;
        signPlayed      = _signPlayed;
    }
    
    //setters
    public void setTypeOfOperation(String _typeOfOperation){
        typeOfOperation = _typeOfOperation;
    }
    
    public void setOperationResult(boolean _operationResult){
        operationResult = _operationResult;
    }
    
    public void setFieldNumber(int _fieldNumber){
        fieldPosition   = _fieldNumber;   
    }
    
    public void setSignPlayed(char _signPlayed){
        signPlayed      =_signPlayed;
    }
    
    //getters
    public String getTypeOfOperation(){
        return typeOfOperation;
    }
    
    public Boolean getOperationResults(){
        return operationResult;
    }
    
    public Game getGame(){
        return game;
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public int getFieldPosition(){
        return fieldPosition;
    }
    
    public char getSignPlayed(){
        return signPlayed;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    
}


