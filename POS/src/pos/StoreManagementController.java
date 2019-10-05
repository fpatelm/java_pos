/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import IO.FileManager;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pos.entities.order.Sales;

/**
 * FXML Controller class
 *
 * @author fpatel
 */
public class StoreManagementController implements Initializable {
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void btn_staff_action(ActionEvent event) throws IOException{
        //Open items scene
       
        final Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStaff.fxml"));
        Scene scene = new Scene((Pane)loader.load());
        window.setResizable(false);
        window.setScene(scene);  
        window.show();
    
   
    }
    @FXML
    private void btn_items_action(ActionEvent event) throws IOException{
        final Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddItem.fxml"));
        Scene scene = new Scene((Pane)loader.load());
        window.setResizable(false);
        window.setScene(scene);  
        window.show();
     }
     
    @FXML
    private void btn_login_action(ActionEvent event)throws IOException {
          final Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene((Pane)loader.load());
        window.setResizable(false);
        window.setScene(scene);  
        window.show();
   
    }
    @FXML
    private void btn_order_action(ActionEvent event)throws IOException {
          final Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderScreen.fxml"));
        Scene scene = new Scene((Pane)loader.load());
        window.setResizable(false);
        window.setScene(scene);  
        window.show();
   
    }
    @FXML
    private void btn_report_action(ActionEvent event)throws IOException {
   
                 
    }
   
}
