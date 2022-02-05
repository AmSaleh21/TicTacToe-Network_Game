
package server;


import Database.Database;
import Helper_Package.Player;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Database.Database;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;


public class FXMLDocumentController 
implements Initializable {

@FXML
private Button turnONBtn;
@FXML
private Button turnOFFBtn;

Server myServer;
    @FXML
    private TableColumn<Player, String>userName;
    @FXML
    private TableColumn<Player, Integer>score;
    @FXML
    private TableColumn<Player, String>statusString;
    @FXML
    private TableView<Player> dataTable;
   
@Override
public void initialize(URL url, ResourceBundle rb) {
     

        }
    
@FXML

private void startServerConnection(ActionEvent event) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Database.dbConnect();
        Database.changeAllStatus();
        listPlayers();
        myServer=new Server(3003);
}

@FXML
private void stopServerConnection(ActionEvent event) throws SQLException {
      
    resetTable();
    Database.changeAllStatus();
    myServer.closeServer();
    Database.dbDisconnect();

    }
    public void listPlayers(){
        try {
            ObservableList players=Database.getPlayers();
            dataTable.setItems(players);
            userName.setCellValueFactory(new PropertyValueFactory<Player, String>("userName"));
            score.setCellValueFactory(new PropertyValueFactory<Player, Integer>("score"));
            statusString.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStringStatus()));
            userName.setStyle("-fx-alignment: CENTER;");
            score.setStyle("-fx-alignment: CENTER;");
            statusString.setStyle("-fx-alignment: CENTER;");

            dataTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
    } catch (SQLException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
    }


    }


    public void resetTable(){
        for( int i = 0; i<dataTable.getItems().size(); i++) {
            dataTable.getItems().clear();
        }   
    }
    
}
