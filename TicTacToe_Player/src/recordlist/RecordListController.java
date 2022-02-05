/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recordlist;

import Helper_Package.Game;
import Helper_Package.InsideXOGame;
import Helper_Package.Player;
import Helper_Package.RecordedMessages;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import login.loginController;
import tictactoe_player.TicTacToe_Player;


public class RecordListController implements Initializable {

    PrintStream PSFromController;
    Game game;
    @FXML
    private Label timeStampLabel;
    @FXML
    private Label homePlayerLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PSFromController = TicTacToe_Player.ps;
    }

    @FXML
    private TableView<Game> recordListTable;
    private TableColumn<Game, Timestamp> timeStampColumn;

    @FXML
    private TableColumn<Game, Integer> gameIDColumn;

    private TableColumn<Game, String> homePlayerCoulumn;

    @FXML
    private TableColumn<Game, String> awayPlayerColumn;

    // holdRecords vector
    Vector<Game> holdRecords;

    @FXML
    private Button replayButton;

    String recordString = new String();
    String homePlayer = new String();
    String awayPlayer = new String();

    boolean flagSelect = false;

    public RecordListController() {
    }

    @FXML
    private void replay(ActionEvent event) {
        if (flagSelect) {
            try {
//                PlayRecordedGameController pc = new PlayRecordedGameController(recordString, homePlayer, awayPlayer);
                FXMLLoader selectionmodeloader = new FXMLLoader();
                selectionmodeloader.setLocation(getClass().getResource("/recordlist/PlayRecordedGame.fxml"));
                Parent selectionmodeRoot = selectionmodeloader.load();
                Scene selectionmodeScene = new Scene(selectionmodeRoot);
                Stage selectionmodeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                selectionmodeStage.hide();
                selectionmodeStage.setScene(selectionmodeScene);
                selectionmodeStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // ************  start record feature ************* 
    public void setAllRecords(InsideXOGame xoMessage) {
       // timeStampColumn.setCellValueFactory(new PropertyValueFactory<>("timestampGame"));
        gameIDColumn.setCellValueFactory(new PropertyValueFactory<>("gameId"));
      //  homePlayerCoulumn.setCellValueFactory(new PropertyValueFactory<>("homePlayer"));
        awayPlayerColumn.setCellValueFactory(new PropertyValueFactory<>("awayPlayer"));
       // timeStampColumn.setStyle("-fx-alignment: CENTER;");
        gameIDColumn.setStyle("-fx-alignment: CENTER;");
       // homePlayerCoulumn.setStyle("-fx-alignment: CENTER;");
        awayPlayerColumn.setStyle("-fx-alignment: CENTER;");
        holdRecords = xoMessage.records;
        System.out.println("envoke setAllRecords");


        //to put vector in observerlist in order to put it in table
        ObservableList<Game> _allRecords = FXCollections.observableList(holdRecords);
        recordListTable.setItems(_allRecords);
        recordListTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    // ************  end record feature ************* 

    @FXML
    private Button backButton;

    @FXML
    private void back(ActionEvent event) {
        try {
            FXMLLoader selectionmodeloader = new FXMLLoader();
            selectionmodeloader.setLocation(getClass().getResource("/selectionmode/SelectionMode.fxml"));
            Parent selectionmodeRoot = selectionmodeloader.load();
            Scene selectionmodeScene = new Scene(selectionmodeRoot);
            Stage selectionmodeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            selectionmodeStage.hide();
            selectionmodeStage.setScene(selectionmodeScene);
            selectionmodeStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void minimize(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    private void exit(ActionEvent event) {
        Player player = new Player();
        player.setUserName(loginController.username);
        InsideXOGame xoMessage = new InsideXOGame(RecordedMessages.LOGOUT, player);
        Gson g = new Gson();
        String s = g.toJson(xoMessage);
        PSFromController.println(s);
        Platform.exit();
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
        recordString = recordListTable.getSelectionModel().getSelectedItem().getRecordedString();
        PlayRecordedGameController.game = recordString;
        homePlayer = recordListTable.getSelectionModel().getSelectedItem().getHomeplayer();
        // System.out.println(homePlayer);
        PlayRecordedGameController.homePlayer = homePlayer;
        awayPlayer = recordListTable.getSelectionModel().getSelectedItem().getAwayPlayer();
        PlayRecordedGameController.awayPlayer = awayPlayer;
        flagSelect = true;
        System.err.println("in mouse clicked " + recordString + flagSelect);
        Timestamp t = recordListTable.getSelectionModel().getSelectedItem().gettimestampGame();
        timeStampLabel.setText(t.toString());
        homePlayerLabel.setText(homePlayer);
        
       
    }
}
