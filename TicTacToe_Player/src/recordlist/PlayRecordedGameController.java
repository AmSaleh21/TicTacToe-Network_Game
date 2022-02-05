/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recordlist;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 */
public class PlayRecordedGameController implements Initializable {

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

    static protected String game;
    static protected String homePlayer;
    static protected String awayPlayer;

    @FXML
    private Button Play;

    char[] gameChar = game.toCharArray();
    int len = gameChar.length;
    int[] position = new int[len];
    char symbol = 'X';

/*    public void setPos() {
        int i = 0;
        for (char c : gameChar) {
            position[i] = Character.getNumericValue(gameChar[i]);
            i++;
        }
    }

    public void changeSymbol() {
        if (symbol == 'X') {
            symbol = 'O';
        } else {
            symbol = 'X';
        }
    }
*/
    public void run() {
          Thread th = new Thread(() -> {
              char[] gameRecChar = game.toCharArray();
              int [] gameRecInt = new int[gameRecChar.length];
              for (int i = 0; i < gameRecChar.length; i++) {
                  gameRecInt[i] = Character.getNumericValue(gameRecChar[i]);
                  int counter = i;
                  Platform.runLater(() -> {
                      String nextPlay = counter%2 == 0 ? "X" : "O";
                      switch (gameRecInt[counter]) {
                          case 1:
                              pos1.setText(nextPlay);
                              break;
                          case 2:
                              pos2.setText(nextPlay);
                              break;
                          case 3:
                              pos3.setText(nextPlay);
                              break;
                          case 4:
                              pos4.setText(nextPlay);
                              break;
                          case 5:
                              pos5.setText(nextPlay);
                              break;
                          case 6:
                              pos6.setText(nextPlay);
                              break;
                          case 7:
                              pos7.setText(nextPlay);
                              break;
                          case 8:
                              pos8.setText(nextPlay);
                              break;
                          case 9:
                              pos9.setText(nextPlay);
                              break;
                      }
                  });
                  LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(1000));
              }
          });

        th.start();
    }    
    
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
            
        }
    }

    @FXML
    private void play(ActionEvent event) {
        run();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeNameLabel.setText(homePlayer);
        opponentNameLabel.setText(awayPlayer);
    }

    @FXML
    private void nothing(MouseEvent event) {
    }

}
