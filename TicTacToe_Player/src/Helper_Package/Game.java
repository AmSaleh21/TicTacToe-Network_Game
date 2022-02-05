/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper_Package;

import java.security.Timestamp;


public class Game {
    //Game Variables
    private int gameId;
    private String homePlayer;
    private String awayPlayer;
    private String message;
    private Timestamp startGameDate;
    private Timestamp endGameDate;
    private java.sql.Timestamp timestampGame;
    private boolean isFinished;
    private String recordedString;
    private char[] savedGame = new char[9];
    
    //Game Class Constructors
    public Game(){};
    
    public Game(int _gameId){
        gameId=_gameId;
    }
    
    public Game(String _homePlayer, String _awayPlayer){
        homePlayer=_homePlayer;
        awayPlayer=_awayPlayer;
    }
    
    public Game(String _homePlayer, String _awayPlayer, String _message){
        homePlayer=_homePlayer;
        awayPlayer=_awayPlayer;
        message   =_message;
    }
    
    public Game(int _gameId, String _homePlayer, String _awayPlayer){
        gameId    =_gameId;
        homePlayer=_homePlayer;
        awayPlayer=_awayPlayer;
    }
    // ***************** start record feature constructor *********
    public Game(java.sql.Timestamp _time_stamp, int _recored_game_id, String _homePlayer, String _awayPlayer, String _recordedString) {
        gameId    =_recored_game_id;
        homePlayer=_homePlayer;
        awayPlayer=_awayPlayer;
        timestampGame  =_time_stamp;
        recordedString = _recordedString;
    }
    // ***************** end record feature constructor *********

    
        public Game(String _homePlayer, String _awayPlayer, Timestamp _startGameDate, Timestamp _endGameDate){
        homePlayer   =_homePlayer;
        awayPlayer   =_awayPlayer;
        startGameDate=_startGameDate;
        endGameDate  =_endGameDate;
    }
    
    
    public Game(int _gameId, String _homePlayer, String _awayPlayer, Timestamp _startGameDate,Timestamp _endGameDate,boolean _isFinished,char[] _savedGame){
        gameId       =_gameId;
        homePlayer   =_homePlayer;
        awayPlayer   =_awayPlayer;
        startGameDate=_startGameDate;
        endGameDate  =_endGameDate;
        isFinished   =_isFinished;
        savedGame    =_savedGame;
    }
    
    //setters
    public void setGameId(int _gameId){
        gameId=_gameId;
    }
    
    //*********** setter recording feature ********
    public void setRecordedString(String _recordedString){
        recordedString = _recordedString;
    }
    
    public void settimestampGame(java.sql.Timestamp _timestampGame){
        timestampGame=_timestampGame;
    }
  //*********** setter recording feature ********
    
    public void setHomePlayer(String _homePlayer){
        homePlayer=_homePlayer;
    }
    
    public void setAwayPlayer(String _awayPlayer){
        awayPlayer=_awayPlayer;
    }
    
        public void setMessage(String _message){
        message = _message;
    }
    
    public void setStartGameDate(Timestamp _startGameDate){
        startGameDate=_startGameDate;
    }
    
    public void setEndGameDate(Timestamp _endGameDate){
        endGameDate=_endGameDate;
    }
    
    public void setIsFinished(boolean _isFinished){
        isFinished=_isFinished;
    }
    
    public void setSavedGame(char[] _savedGame){
        savedGame = _savedGame;
    }
    
    //getters
    
    //*********** setter recording feature ********
    public String getRecordedString(){
        return recordedString;
    }
    
    public java.sql.Timestamp gettimestampGame(){
       return timestampGame;
    }
  //*********** setter recording feature ********
    
    public int getGameId(){
        return gameId;
    }
    
    public String getHomeplayer(){
        return homePlayer;
    }
    
    public String getAwayPlayer(){
        return awayPlayer;
    }
    
    public String getMessage(){
        return message;
    }
    
    public Timestamp getStartGameDate(){
        return startGameDate;
    }
    
    public Timestamp getEndGameDate(){
        return endGameDate;
    }
    
    public boolean getIsFinished(){
        return isFinished;
    }
    
    public char[] getSavedGame(){
        return savedGame;
    } 
    
}
