package server;

import Helper_Package.InsideXOGame;
import Helper_Package.Player;
import Helper_Package.RecordedMessages;
import Database.Database;
import static Database.Database.recordGameToDataBase;
import Helper_Package.Game;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;

class ServerThread extends Thread {

    private final Socket socket;
    private DataInputStream dis;
    private PrintStream ps;
    private Player newPlayer;
    private Game game = new Game();

    static Vector<ServerThread> playersVector = new Vector<>();
    static HashMap<Integer, ServerThread> onlinePlayers = new HashMap<>();
    static HashMap<String, Integer> usernameToId = new HashMap<>();
    Gson g = new Gson();

    public ServerThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream(), true);
            newPlayer = new Player();
            String message;
            while (true) {
                message = dis.readLine();
                System.out.println("message:" + message);

                if (!message.isEmpty()) {
                    try {
                        jsonMessageHandler(message);
                    } catch (ParseException ex) {
                        ex.getStackTrace();
                        System.out.println("error while call json message hundler ");
                    }
                }
            }
        } catch (IOException ex) {
            ex.getStackTrace();
            System.out.println("server can not connect with client");
            try {
                socket.close();
                dis.close();
                ps.close();

                newPlayer.setStatus(false);
                onlinePlayers.remove(newPlayer.getPlayerId());
                usernameToId.remove(newPlayer.getUserName());
                try {
                    Database.updatePlayerStatus(newPlayer.getUserName(), 0); //update status of player to be offline
                } catch (SQLException ex1) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex1);
                }
                System.out.println("player has leaved and become offline");
            } catch (IOException e) {
                System.out.println("Error while closing socket connection from server");
                e.getStackTrace();
            }
        }
    }

    public Player getNewPlayer() {
        return newPlayer;
    }

    private void jsonMessageHandler(String data) throws ParseException {

        Gson gson = new Gson();
        // deserializing from JSON to Java object
        // The second parameter in .fromJson method ..
        // is the appropiate class for it 
        InsideXOGame msgObject = gson.fromJson(data, InsideXOGame.class);

        String s;
        switch (msgObject.getTypeOfOperation()) {
            case RecordedMessages.LOGIN: {
                try {
                    handelLogInRequest(msgObject);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case RecordedMessages.SIGNUP: {
                try {
                    handelSinUpRequest(msgObject);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case RecordedMessages.PLAYING_SINGLE_MODE: {
                try {
                    handelPlayingSingleModeRequest(msgObject);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case RecordedMessages.SINGLE_MODE_GAME_FINISHED: {
                try {
                    handelSingleGameFinishedRequest(msgObject);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case RecordedMessages.RETRIVE_PLAYERS:
                handelRetrivePlayersRequest(msgObject);
                break;
            // ************* start go to record list request 
            case RecordedMessages.Recorded_LIST:
                System.out.println("inside server comming from selectModeController");
                handelGoToRecordsList(msgObject);
                break;
            //handelGoToRecordsList
            // ************* end go to record list request 

            case RecordedMessages.INVITE:
                handelInviteRequest(msgObject);
                break;
            case RecordedMessages.INVITATION_ACCEPTED: {
                try {
                    handelInvitationAcceptedRequest(msgObject);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case RecordedMessages.INVITATION_REJECTED:
                handelInvitationRejectedRequest(msgObject);
                break;
            case RecordedMessages.GAME_PLAY_MOVE: {
                try {
                    handelGamePlayMoveRequest(msgObject);
                    recordHandler(msgObject);
                } catch (Exception ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case RecordedMessages.GAME_GOT_FINISHED: {
                try {
                    // printing string record after game got finished 
                    stringRecordSender(msgObject);
                    handelGameGotFinishedRequest(msgObject);

                } catch (SQLException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IndexOutOfBoundsException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case RecordedMessages.RESUME: {
                try {
                    handelResumeRequest(msgObject);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case RecordedMessages.CHAT_PLAYERS_WITH_EACH_OTHERS:
                handelChatRequest(msgObject);
                break;
            case RecordedMessages.BACK:
                handelBackRequest(msgObject);
                break;
            case RecordedMessages.BACK_FROM_ONLINE: {
                try {
                    handelBackFromOnlineRequest(msgObject);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IndexOutOfBoundsException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case RecordedMessages.LOGOUT: {
                handelLogoutRequest(msgObject);
                System.out.println("logout request");
            }
            break;

            case RecordedMessages.NEW_PLAYER_LOGGEDIN_POPUP:
                handelPopUpMessage(msgObject);
                break;

        }
    }

    private void handelLogInRequest(InsideXOGame objMsg) throws SQLException {

        Gson g = new Gson();
        Player player;
        String userName, password;
        int playerId = 0;
        player = objMsg.getPlayer();
        userName = player.getUserName();
        password = player.getPassword();
        playerId = Database.login(userName, password); //this function will return -1 if login faild
        if (playerId != -1) {
            refreshList();
            Database.updatePlayerStatus(playerId, 1);
            newPlayer.setPlayerId(playerId);
            newPlayer.setStatus(true);
            newPlayer.setUserName(userName);
            newPlayer.setPassword(password);
            newPlayer.setIsPlaying(false);
            newPlayer.setScore(Database.getPoints(playerId));
            System.out.println(newPlayer.getScore());
            ServerThread.onlinePlayers.put(playerId, this);
            ServerThread.usernameToId.put(userName, playerId);
            objMsg.getPlayer().setScore(newPlayer.getScore());
            objMsg.getPlayer().setStatus(true);

            objMsg.setOperationResult(true);
            objMsg.setTypeOfOperation(RecordedMessages.LOG_IN_ACCEPTED);
            ps.println(g.toJson(objMsg));
            handelPopUpMessage(objMsg);//to notify all online users with new player loged in 

        } else {
            objMsg.setTypeOfOperation(RecordedMessages.LOGIN_REJECTED);
            objMsg.setOperationResult(false);
            ps.println(g.toJson(objMsg));
        }

    }

    private void handelSinUpRequest(InsideXOGame objMsg) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Gson g = new Gson();
        Player player;
        String userName, email, password;
        int successRegister = 0;
        player = objMsg.getPlayer();
        userName = player.getUserName();
        email = player.getEmail();
        password = player.getPassword();
        String[] inputData = {userName, email, password};
        successRegister = Database.register(inputData);
        if (successRegister == 1) {
            objMsg.setOperationResult(true);
            objMsg.setTypeOfOperation(RecordedMessages.SIGN_UP_ACCEPTED);
        } else {
            objMsg.setOperationResult(false);
            objMsg.setTypeOfOperation(RecordedMessages.SIGN_UP_REJECTED);
        }
        ps.println(g.toJson(objMsg));
    }

    private void handelPlayingSingleModeRequest(InsideXOGame objMsg) throws SQLException {

        Gson g = new Gson();
        Player player;
        String userName;
        player = objMsg.getPlayer();
        userName = player.getUserName();
        Database.updatePlayerStatus(userName, 2);
        objMsg.setOperationResult(true);
        objMsg.getPlayer().setIsPlaying(true);
        objMsg.getPlayer().setStatus(true);///////////////       
        objMsg.setTypeOfOperation(RecordedMessages.PLAYING_SINGLE_MODE);
        ps.println(g.toJson(objMsg));

    }

    private void handelSingleGameFinishedRequest(InsideXOGame msgObject) throws SQLException {
        Gson g = new Gson();
        Player player;
        String userName;
        player = msgObject.getPlayer();
        userName = player.getUserName();

        Database.updatePlayerScore(userName, 5);
        newPlayer.setScore(newPlayer.getScore() + 5);
        msgObject.setOperationResult(true);
        msgObject.getPlayer().setScore(newPlayer.getScore());
        msgObject.setTypeOfOperation(RecordedMessages.SINGLE_MODE_PLAYER_SCORE_UPDATED);
        ps.println(g.toJson(msgObject));
        System.out.print(msgObject.getPlayer().getScore());
    }

    private void handelRetrivePlayersRequest(InsideXOGame msgObject) {
        //System.out.println("ok");
        Vector<Player> players = new Vector<>();
        for (Map.Entry<Integer, ServerThread> handler : onlinePlayers.entrySet()) {
            Player player = handler.getValue().getNewPlayer();
            if (player.getStatus() && !player.getIsPlaying()) {// add he is not busy
                players.add(player);
            }
        }
        msgObject.setOperationResult(true);
        msgObject.setTypeOfOperation(RecordedMessages.RETREVING_PLAYERS_LIST);
        msgObject.players = players;
        ps.println(g.toJson(msgObject));
        //System.out.println(g.toJson(msgObject));
    }

    //************** start Handle Records List SCENE *********************
    private void handelGoToRecordsList(InsideXOGame msgObject) {

        try {
            //
            ObservableList<Game> records = Database.getRecords();
            Vector<Game> recordsVector = new Vector<>(records);
            System.err.println("in habda trying to get the recStr " + recordsVector.get(0).getRecordedString());
            msgObject.setRecords(recordsVector);
            
            msgObject.setTypeOfOperation(RecordedMessages.GOTO_RECORD_LIST);
            ps.println(g.toJson(msgObject));
            System.out.println("handelGoToRecordsList from serverThread ");
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //************** end Handle Records List SCENE*********************

    private void handelInviteRequest(InsideXOGame msgObject) {
        int opponentUserId = usernameToId.get(msgObject.getGame().getAwayPlayer());
        if (onlinePlayers.containsKey(opponentUserId)) {//add he is not busy
            Player opponentPlayer = onlinePlayers.get(opponentUserId).getNewPlayer();
            if (opponentPlayer.getStatus() && !opponentPlayer.getIsPlaying()) {
                msgObject.setOperationResult(true);
                msgObject.setTypeOfOperation(RecordedMessages.RECEIVING_INVITATION);
                onlinePlayers.get(opponentUserId).getPs().println(g.toJson(msgObject));//print in json format
                return;
            }
        }
        msgObject.setOperationResult(false);
        msgObject.setTypeOfOperation(RecordedMessages.INVITATION_REJECTED);
        ps.println(g.toJson(msgObject));
    }

    private void handelInvitationAcceptedRequest(InsideXOGame msgObject) throws SQLException {
        int opponentUserId = usernameToId.get(msgObject.getGame().getHomeplayer());
        if (onlinePlayers.containsKey(opponentUserId)) {
            Player opponentPlayer = onlinePlayers.get(opponentUserId).getNewPlayer();
            if (opponentPlayer.getStatus() && !opponentPlayer.getIsPlaying()) {
                System.out.println(newPlayer.getPlayerId());
                System.out.println(opponentUserId);

                int gameId = Database.addPlayersGame(newPlayer.getPlayerId(), opponentUserId);
                game.setGameId(gameId);
                onlinePlayers.get(opponentUserId).getGame().setGameId(gameId);
                msgObject.setOperationResult(true);
                msgObject.setTypeOfOperation(RecordedMessages.INVITATION_ACCEPTED_FROM_SERVER);
                msgObject.getGame().setGameId(gameId);
                newPlayer.setOpponentId(opponentUserId);
                newPlayer.setIsPlaying(true);
                onlinePlayers.get(opponentUserId).getNewPlayer().setIsPlaying(true);
                onlinePlayers.get(opponentUserId).getNewPlayer().setOpponentId(newPlayer.getPlayerId());
                onlinePlayers.get(opponentUserId).getPs().println(g.toJson(msgObject));// json
                String home = msgObject.getGame().getHomeplayer();
                msgObject.getGame().setHomePlayer(msgObject.getGame().getAwayPlayer());
                msgObject.getGame().setAwayPlayer(home);
                ps.println(g.toJson(msgObject));
                System.out.println(g.toJson(msgObject));
                return;
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void handelInvitationRejectedRequest(InsideXOGame msgObject) {
        int opponentUserId = usernameToId.get(msgObject.getGame().getHomeplayer());
        if (onlinePlayers.containsKey(opponentUserId) && onlinePlayers.get(opponentUserId).getNewPlayer().getStatus()) {
            msgObject.setOperationResult(true);
            msgObject.setTypeOfOperation(RecordedMessages.INVITATION_REJECTED_FROM_SERVER);
            onlinePlayers.get(opponentUserId).getPs().println(g.toJson(msgObject));// json
        } else {//what if the inviter went off

        }
    }

    // ************************** recordHandler **************************************************** 
    static ArrayList<String> recorderdStringList = new ArrayList<String>();

    // Method to add players moves to the recorderdStringList
    private void recordHandler(InsideXOGame msgObject) throws SQLException, IndexOutOfBoundsException, IllegalAccessException {
        String intPosition = Integer.toString(msgObject.getFieldPosition());
        recorderdStringList.add(intPosition);

//        System.out.println(msgObject.getFieldPosition()); // printing the pos that a player play each time
        // printing the arraylist
        for (int i = 0; i < recorderdStringList.size(); i++) {
            String curr = recorderdStringList.get(i);
            System.out.print(curr);
        }
        System.out.println("");

    }

    // Method to send recorderdStringList to database 
    private void stringRecordSender(InsideXOGame msgObject) throws SQLException, IndexOutOfBoundsException, IllegalAccessException {
//        System.out.println("");
//        for (int i = 0; i < recorderdStringList.size(); i++) {
//            String curr = recorderdStringList.get(i);
//            System.out.print(curr);
//        }
        System.out.println("");
        System.out.println(msgObject.getGame().getGameId());
        String recorderdString = String.join("", recorderdStringList);
        System.out.println(recorderdString);
        System.out.println("");

        int recorderGameID = msgObject.getGame().getGameId();
        String player1_id = msgObject.getGame().getHomeplayer();
        String player2_id = msgObject.getGame().getAwayPlayer();
//        System.out.println(player1_id);
//        System.out.println(player2_id);

        // sending to DB " this method from Database class 
        recordGameToDataBase(recorderGameID, recorderdString, player1_id, player2_id);

        // clear the list 
        recorderdStringList.clear();

//        String recorderdString = String.join(", ", recorderdStringList);
    }

    // ************************* End recordHandler ***************************************
    private void handelGamePlayMoveRequest(InsideXOGame msgObject) throws SQLException, IndexOutOfBoundsException, IllegalAccessException {
        if (newPlayer.getOpponentId() == 0) {
            return;
        }
        char[] maz = game.getSavedGame();
        maz[msgObject.getFieldPosition() - 1] = msgObject.getSignPlayed();

//        System.out.println(msgObject.getFieldPosition()); // printing the pos that a player play each time
        game.setSavedGame(maz);
        if (onlinePlayers.containsKey(newPlayer.getOpponentId())) {
            Player opponentPlayer = onlinePlayers.get(newPlayer.getOpponentId()).getNewPlayer();
            if (opponentPlayer.getStatus() && opponentPlayer.getIsPlaying()) {
                onlinePlayers.get(newPlayer.getOpponentId()).getGame().setSavedGame(maz);
                msgObject.setOperationResult(true);
                msgObject.setTypeOfOperation(RecordedMessages.INCOMING_MOVE);
                onlinePlayers.get(newPlayer.getOpponentId()).getPs().println(g.toJson(msgObject));// json
            }
        } else {// other player went off line
            if (newPlayer.getUserName().equals(msgObject.getGame().getHomeplayer())) {
                Database.saveGame(game.getGameId(), newPlayer.getPlayerId(), newPlayer.getOpponentId(), game.getSavedGame());
            } else {
                Database.saveGame(game.getGameId(), newPlayer.getOpponentId(), newPlayer.getPlayerId(), game.getSavedGame());
            }
            msgObject.getGame().setMessage("sorry the opponent left your game is saved!");
            msgObject.getGame().setAwayPlayer("Server");
            msgObject.getGame().setHomePlayer("Server");
            msgObject.setTypeOfOperation(RecordedMessages.CHAT_PLAYERS_WITH_EACH_OTHERS_FROM_SERVER);
            ps.println(g.toJson(msgObject));
            newPlayer.setOpponentId(0);
        }
        System.out.println(game.getSavedGame());
    }

    private void handelGameGotFinishedRequest(InsideXOGame msgObject) throws SQLException {
        msgObject.setOperationResult(true);
        msgObject.setTypeOfOperation(RecordedMessages.GAME_GOT_FINISHED_SECCUSSFULLY);
        Database.setWinner(game.getGameId(), newPlayer.getPlayerId(), newPlayer.getOpponentId(), newPlayer.getPlayerId());
        Database.setWinner(game.getGameId(), newPlayer.getOpponentId(), newPlayer.getPlayerId(), newPlayer.getPlayerId());
        Database.removeSavedGame(game.getGameId());
        newPlayer.setIsPlaying(false);
        onlinePlayers.get(newPlayer.getOpponentId()).getNewPlayer().setIsPlaying(false);
        onlinePlayers.get(newPlayer.getOpponentId()).getNewPlayer().setOpponentId(0);
        newPlayer.setOpponentId(0);
        Database.updatePlayerScore(newPlayer.getPlayerId(), 10);
        newPlayer.setScore(Database.getPoints(newPlayer.getPlayerId()));
        msgObject.getPlayer().setScore(Database.getPoints(newPlayer.getPlayerId()));
        msgObject.setPlayer(newPlayer);
        ps.println(g.toJson(msgObject));
        System.out.print(msgObject.getPlayer().getScore());
    }

    private void handelResumeRequest(InsideXOGame msgObject) throws SQLException {
        boolean flage = false, get = true, homeTurn = false;
        msgObject.setTypeOfOperation(RecordedMessages.RETRIVEMOVES);
        char[] maz = Database.loadGame(newPlayer.getPlayerId(), newPlayer.getOpponentId());
        int id = Database.getSavedGameId(newPlayer.getPlayerId(), newPlayer.getOpponentId());
        if (maz == null) {
            flage = true;
            maz = Database.loadGame(newPlayer.getOpponentId(), newPlayer.getPlayerId());
            id = Database.getSavedGameId(newPlayer.getOpponentId(), newPlayer.getPlayerId());
        }
        if (maz == null) {
            get = false;
        } else {
            int homeMoves, awayMoves;
            homeMoves = awayMoves = 0;
            for (int i = 0; i < 9; i++) {
                if (maz[i] == 'X') {
                    homeMoves++;
                } else if (maz[i] == 'O') {
                    awayMoves++;
                }
            }
            if (homeMoves == 0 || homeMoves == awayMoves) {//handle to meet o
                homeTurn = true;
            } else {
                homeTurn = false;
            }
        }
        if (get) {
            System.out.println("ok");
            Database.removeGame(game.getGameId());
            game.setGameId(id);
            game.setSavedGame(maz);
            onlinePlayers.get(newPlayer.getOpponentId()).getGame().setGameId(id);
            onlinePlayers.get(newPlayer.getOpponentId()).getGame().setSavedGame(maz);
            //msgObject.getGame().setHomePlayer();
            msgObject.getGame().setGameId(id);
            msgObject.getGame().setSavedGame(maz);

            if (flage) {
                msgObject.getGame().setHomePlayer(onlinePlayers.get(newPlayer.getOpponentId()).getNewPlayer().getUserName());
                msgObject.getGame().setAwayPlayer(newPlayer.getUserName());
                msgObject.setPlayer(onlinePlayers.get(newPlayer.getOpponentId()).getNewPlayer());
                msgObject.getPlayer().setIsMyTurn(homeTurn);
                onlinePlayers.get(newPlayer.getOpponentId()).getPs().println(g.toJson(msgObject));
                System.out.println(g.toJson(msgObject));
                msgObject.setPlayer(newPlayer);
                msgObject.getPlayer().setIsMyTurn(!homeTurn);
                ps.println(g.toJson(msgObject));
                System.out.println(g.toJson(msgObject));
            } else {
                msgObject.getGame().setHomePlayer(newPlayer.getUserName());
                msgObject.getGame().setAwayPlayer(onlinePlayers.get(newPlayer.getOpponentId()).getNewPlayer().getUserName());
                msgObject.setPlayer(newPlayer);
                msgObject.getPlayer().setIsMyTurn(homeTurn);
                ps.println(g.toJson(msgObject));
                System.out.println(g.toJson(msgObject));
                msgObject.setPlayer(onlinePlayers.get(newPlayer.getOpponentId()).getNewPlayer());
                msgObject.getPlayer().setIsMyTurn(!homeTurn);
                onlinePlayers.get(newPlayer.getOpponentId()).getPs().println(g.toJson(msgObject));
                System.out.println(g.toJson(msgObject));
            }
        }

    }

    private void handelChatRequest(InsideXOGame msgObject) {
        if (onlinePlayers.containsKey(newPlayer.getOpponentId()) && onlinePlayers.get(newPlayer.getOpponentId()).getNewPlayer().getStatus()) {
            msgObject.setOperationResult(true);
            msgObject.setTypeOfOperation(RecordedMessages.CHAT_PLAYERS_WITH_EACH_OTHERS_FROM_SERVER);
            onlinePlayers.get(newPlayer.getOpponentId()).getPs().println(g.toJson(msgObject));//json
        }
    }

    private void handelBackRequest(InsideXOGame msgObject) {
        newPlayer.setIsPlaying(false);
        msgObject.setPlayer(newPlayer);
        msgObject.setOperationResult(true);
        msgObject.setTypeOfOperation(RecordedMessages.BACK_FROM_SERVER);
        ps.println(g.toJson(msgObject));//json
    }

    public PrintStream getPs() {
        return ps;
    }

    public void setPs(PrintStream ps) {
        this.ps = ps;
    }

    private void handelBackFromOnlineRequest(InsideXOGame msgObject) throws SQLException, IndexOutOfBoundsException, IllegalAccessException {
        newPlayer.setIsPlaying(false);
        System.out.println(g.toJson(msgObject));
        if (!msgObject.getGame().getIsFinished() && newPlayer.getOpponentId() != 0) {
            System.out.println(game.getSavedGame().toString());
            if (newPlayer.getUserName().equals(msgObject.getGame().getHomeplayer())) {
                Database.saveGame(game.getGameId(), newPlayer.getPlayerId(), newPlayer.getOpponentId(), game.getSavedGame());
            } else {
                Database.saveGame(game.getGameId(), newPlayer.getOpponentId(), newPlayer.getPlayerId(), game.getSavedGame());
            }
            msgObject.getGame().setMessage("sorry the opponent left your game is saved!");
            msgObject.getGame().setAwayPlayer("Server");
            msgObject.getGame().setHomePlayer("Server");
            handelChatRequest(msgObject);
        }
        msgObject.setTypeOfOperation(RecordedMessages.BACK_FROM_SERVER);
        if (newPlayer.getOpponentId() != 0) {
            onlinePlayers.get(newPlayer.getOpponentId()).getNewPlayer().setOpponentId(0);
            newPlayer.setOpponentId(0);
        }
        ps.println(g.toJson(msgObject));
    }

    private void handelLogoutRequest(InsideXOGame msgObject) {

        try {

            dis.close();
            ps.close();
            socket.close();
            System.out.println("Try logout");

        } catch (SocketException se) {
            System.out.println("SE Exception");
         
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        newPlayer.setStatus(false);
        onlinePlayers.remove(newPlayer.getPlayerId());
        usernameToId.remove(newPlayer.getUserName());
        try {

            Database.logout(newPlayer.getPlayerId());
            //Database.updatePlayerStatus(newPlayer.getUserName(),0); //update status of player to be offline
            refreshList();
        } catch (SQLException ex1) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex1);
        }

        System.out.println("player has leaved and become offline");
    }

    public void refreshList() {
        System.out.println("hello world");
        Platform.runLater(() -> {
            ServerGui.test.listPlayers();
        });

    }

    private void handelPopUpMessage(InsideXOGame msgObject) {

        for (Map.Entry<Integer, ServerThread> onlinePlayer : onlinePlayers.entrySet()) {
            if (onlinePlayer.getKey() != msgObject.getPlayer().getPlayerId()) {
                msgObject.setTypeOfOperation(RecordedMessages.NEW_PLAYER_LOGGEDIN_POPUP);

                onlinePlayer.getValue().getPs().println(g.toJson(msgObject));

            }
        }
    }

}
