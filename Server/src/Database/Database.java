/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.security.Timestamp;
import Helper_Package.Game;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Helper_Package.Player;
import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import java.security.Timestamp;
import java.util.Date;

public class Database {

     static Connection con =null;
    static String db_name="xo_netwok_game";
    static String url="jdbc:mysql://localhost:3306/"+db_name;
    static String username="root";//////your name
    static String password="";//////your password

    //this function created to connect to the database
    public static void dbConnect() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection(url, username, password);
    }

    //this function created to disconnect to the database
    public static void dbDisconnect() throws SQLException {
        con.close();
    }

    // Login Methode
    public static int login(String username, String password) throws SQLException {

        PreparedStatement statement;
        statement = con.prepareStatement("Select id from player where username = ? and password = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet rs = statement.executeQuery();
        int id = -1;
        if (rs.next()) {
            id = rs.getInt("id");
            updatePlayerStatus(id, 1);
        }

        return id;
    }

    // Register Methode
    //this function take array of strings that represent player data and save this data into the database
    public static int register(String[] arr) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        int success_register = 1;
        int email_exist = valiateEmail(arr[1]);
        int userName_exist = valiateUsername(arr[0]);
        if (email_exist == 0 && userName_exist == 0) {
            PreparedStatement statement;
            statement = con.prepareStatement("insert into player(`username`,`email`,`password`)values(?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.setString(1, arr[0]);
            statement.setString(2, arr[1]);
            statement.setString(3, arr[2]);
            statement.executeUpdate();
        } else {
            success_register = 0;
        }
        return success_register;
    }

    // this function takes the game id , player id and the states as an array[9] of strings and save the game in the database
    public static void saveGame(int gameId, int player1_id, int player2_id, char[] maze) throws SQLException, IndexOutOfBoundsException, IllegalAccessException {
        PreparedStatement statement;
        statement = con.prepareStatement("insert into game_info (`game_id`,`value1`,`value2`,`value3`,"
                + "`value4`,`value5`,`value6`,`value7`,`value8`,`value9`,`player1_id`,`player2_id`)"
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)");
        statement.setInt(1, gameId);
        for (int i = 2; i <= 10; i++) {
            statement.setString(i, String.valueOf(maze[i - 2]));
        }
        statement.setInt(11, player1_id);
        statement.setInt(12, player2_id);
        statement.executeUpdate();
    }

    // ************************  start Recording Game   ***************************
    // recordGame to send the string and game id to the database
    public static void recordGameToDataBase(int gameId, String recordString, String player1_id, String player2_id) throws SQLException, IndexOutOfBoundsException, IllegalAccessException {
        PreparedStatement statement;
        statement = con.prepareStatement("insert into game_recording (`recored_game_id`,`string_record`, `player1_id` , `player2_id`) values(?,?,?,?)");

        statement.setInt(1, gameId);
        statement.setString(2, recordString);
        statement.setString(3, player1_id);
        statement.setString(4, player2_id);

        statement.executeUpdate();
    }
    // ************************  end Recording Game   ***************************

    //this function take a paused game id and return the game states in as an array[9] of strings 
//    public static char[] loadGame(int player1_id , int player2_id) throws SQLException, IndexOutOfBoundsException{
//        char[] maze = new char[9];
//        PreparedStatement statement;
//        statement = con.prepareStatement("select * from game_info where  player1_id= ? and player2_id = ? limit 1");
//        statement.setInt(1, player1_id);
//        statement.setInt(2, player2_id);
//        ResultSet rs = statement.executeQuery();
//        if(rs.next()){
//            for(int i=0;i<9;i++){
//                String v = rs.getString("value"+(i+1));
//                if(isNullOrEmpty(v)){
//                    maze[i]=' ';
//                }
//                else{
//                    maze[i]=rs.getString("value"+(i+1)).charAt(0);
//                }
//            }
//            
//            
//        }
//        return maze;
//    }
    //this function take player id and return the status of this player
    public static int getPlayerStatus(int playerId) throws SQLException, IndexOutOfBoundsException {
        PreparedStatement statement;
        statement = con.prepareStatement("Select player_status from player where id = ?");
        statement.setInt(1, playerId);
        ResultSet rs = statement.executeQuery();
        int status = 0;
        if (rs.next()) {
            status = rs.getInt("player_status");
        }

        return status;
    }

    //this function take player id and score value and update in the database the score of this player by increase it with the new value
    public static void updatePlayerScore(int playerId, int value) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("update player "
                + "set player_points = (player_points + ? )"
                + "where id = ?");
        statement.setInt(1, value);
        statement.setInt(2, playerId);
        statement.executeUpdate();
    }

    //this function return all players in the database in observable list to put it in table view in GUI
    public static ObservableList<Player> getPlayers() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ObservableList<Player> players = FXCollections.observableArrayList();
        Statement stmt = con.createStatement();
        String queryString = new String("select * from player order by  player_points desc");
        ResultSet rs = stmt.executeQuery(queryString);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String email = rs.getString(3);
            String password = rs.getString(4);
            int points = rs.getInt(5);
            int status = rs.getInt(6);

            Player p = new Player(name, password, email);
            p.setStringStatus(status);
            p.setScore(points);
            players.add(p);
        }
        return players;
    }

    // ***************************** start Records Feature **********************
    public static ObservableList<Game> getRecords() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ObservableList<Game> records = FXCollections.observableArrayList();
        Statement stmt = con.createStatement();
        String queryString = "select * from game_recording order by time_stamp desc";
        ResultSet rs = stmt.executeQuery(queryString);
        while (rs.next()) {
            java.sql.Timestamp time_stamp = rs.getTimestamp(1) ;
            int recored_game_id = rs.getInt(2);
            String recordString = rs.getString(3);
            String player1_id = rs.getString(4);
            String player2_id = rs.getString(5);

            Game game = new Game(time_stamp, recored_game_id, player1_id, player2_id, recordString);
            records.add(game);

        }
        return records;
    }

    // ***************************** end Records Feature **********************
    //this function created to add game between two players in data base
    public static int addPlayersGame(int player1Id, int player2Id) throws SQLException {
        int id = 0;
        PreparedStatement statement;
        statement = con.prepareStatement("insert into game(`player1_id`,`player2_id`)values(?,?)", Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, player1Id);
        statement.setInt(2, player2Id);
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    // this function is created to change the status of player
    public static void updatePlayerStatus(int playerId, int statusValue) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("update player set player_status= ? where id = ? ");

        statement.setInt(1, statusValue);
        statement.setInt(2, playerId);
        statement.executeUpdate();
    }

    // this function is created to validate email email must be unique for each user
    public static int valiateEmail(String email) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("select * from player where BINARY email = ? ", ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, email);
        ResultSet rs = statement.executeQuery();
        int size = 0;
        if (rs != null) {
            rs.last();
            size = rs.getRow();

        }
        return size;
    }

    // this function is created to validate username username must be unique for each user
    public static int valiateUsername(String userName) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("select * from player where BINARY username = ? ");
        statement.setString(1, userName);
        ResultSet rs = statement.executeQuery();
        int size = 0;
        if (rs != null) {
            rs.next();
            size = rs.getRow();

        }
        return size;
    }

    //this function take player userName and return the status of this player
    public static int getPlayerStatus(String playerUsername) throws SQLException, IndexOutOfBoundsException {
        PreparedStatement statement;
        statement = con.prepareStatement("Select player_status from player where username = ?");
        statement.setString(1, playerUsername);
        ResultSet rs = statement.executeQuery();
        int status = 0;
        if (rs.next()) {
            status = rs.getInt("player_status");
        }

        return status;
    }

    // this function is created to change the status of player here tale playerUser name as a parameter
    public static void updatePlayerStatus(String playerUsername, int statusValue) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("update player set player_status= ? where username = ? ");
        statement.setInt(1, statusValue);
        statement.setString(2, playerUsername);
        statement.executeUpdate();
    }

    //this function take player username and score value and update in the database the score of this player by increase it with the new value
    public static void updatePlayerScore(String playerUsername, int value) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("update player "
                + "set player_points = (player_points + ? )"
                + "where username = ?");
        statement.setInt(1, value);
        statement.setString(2, playerUsername);
        statement.executeUpdate();
    }

    public static int getPoints(int player_id) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("Select player_points from player where id = ?");
        statement.setInt(1, player_id);
        ResultSet rs = statement.executeQuery();
        int points = 0;
        if (rs.next()) {
            points = rs.getInt("player_points");
        }

        return points;

    }

    public static void setWinner(int game_id, int player1_id, int player2_id, int winner_id) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("update game set winner_id= ? where id = ? and player1_id = ? and player2_id = ? ");
        statement.setInt(1, winner_id);
        statement.setInt(2, game_id);
        statement.setInt(3, player1_id);
        statement.setInt(4, player2_id);
        statement.executeUpdate();
    }

    public static void logout(int player_id) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("update player set player_status= ? where id = ?");
        statement.setInt(1, 0);
        statement.setInt(2, player_id);
        statement.executeUpdate();
    }

    public static void changeAllStatus() throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("update player set player_status= ?");
        statement.setInt(1, 0);
        statement.executeUpdate();
    }

    public static void removeGame(int gameId) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("delete from game where id = ?");
        statement.setInt(1, gameId);
        statement.executeUpdate();
    }

    public static void removeSavedGame(int gameId) throws SQLException {
        PreparedStatement statement;
        statement = con.prepareStatement("delete from game_info where id = ?");
        statement.setInt(1, gameId);
        statement.executeUpdate();
    }

    public static int getSavedGameId(int player1_id, int player2_id) throws SQLException, IndexOutOfBoundsException {
        int id = 0;
        PreparedStatement statement;
        statement = con.prepareStatement("select game_id from game_info where player1_id= ? and player2_id = ? limit 1");
        statement.setInt(1, player1_id);
        statement.setInt(2, player2_id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            id = rs.getInt("game_id");
        }
        return id;
    }

    public static char[] loadGame(int player1_id, int player2_id) throws SQLException, IndexOutOfBoundsException {
        char[] maze = new char[9];
        PreparedStatement statement;
        statement = con.prepareStatement("select * from game_info where player1_id= ? and player2_id = ? limit 1");
        statement.setInt(1, player1_id);
        statement.setInt(2, player2_id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            for (int i = 0; i < 9; i++) {
                String v = rs.getString("value" + (i + 1));
                if (isNullOrEmpty(v)) {
                    maze[i] = ' ';
                } else {
                    maze[i] = rs.getString("value" + (i + 1)).charAt(0);
                }
            }
        } else {
            maze = null;
        }
        return maze;
    }

}
