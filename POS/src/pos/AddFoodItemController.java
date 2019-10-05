/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.mongodb.morphia.Datastore;
import pos.entities.food.FoodCategory;
import pos.entities.food.FoodItem;

/**
 * FXML Controller class
 *
 * @author fpatel
 */
public class AddFoodItemController implements Initializable {


    /**
     * Initializes the controller class.
     */
    
    
    
    FoodItem foodItem;
    FoodCategory foodCategoory;
    FoodCategory copyFoodCategoory;
    Datastore datastore;
    @FXML //  fx:id="ItemBox"
    private TextField ItemBox;
    @FXML //  fx:id="PriceBox"
    private TextField PriceBox; 
    @FXML //  fx:id="DiscountPercentageBox"
    private TextField DiscountPercentageBox; 
    @FXML //  fx:id="DiscountMonetaryBox"
    private TextField DiscountMonetaryBox; 
    @FXML //  fx:id="ListView"
    private ListView ListView;
    /*@FXML //  fx:id="AddButton"
    private Button  AddButton;
    @FXML //  fx:id="CancelButton"
    private Button  CancelButton;
    @FXML //  fx:id="ClearButton"
    private Button  ClearButton;
    */
    private ObservableList<FoodItem> foodItemList;
    
    Alert alertDialog;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        foodItem = new FoodItem();

    }    
  
    
    void initData(FoodCategory inputFoodCategory, Datastore idatastore, String FoodItemName ){
        foodCategoory = inputFoodCategory;
        System.out.println("Item to add: " + FoodItemName);
        ItemBox.setText(FoodItemName);
        datastore = idatastore;
        foodItemList = FXCollections.observableArrayList();
        foodItemList.addAll(foodCategoory.getFoodItemList());
        ListView.getItems().addAll(foodItemList);
        DiscountPercentageBox.setText("0");
        DiscountMonetaryBox.setText("0");
    }
    @FXML
    private void SaveBtnClicked(ActionEvent event) {
        System.out.println("You clicked me!");
        if( !ItemBox.getText().isEmpty() ){
            foodItem.setName(ItemBox.getText());
        }
        else
        {
            alertDialog = new Alert(Alert.AlertType.INFORMATION);
            alertDialog.setTitle("Information");
            alertDialog.setContentText("No entry");
            alertDialog.showAndWait(); 
        return;
        }
            
        if( !PriceBox.getText().isEmpty()){
            foodItem.setPrice(Double.valueOf(PriceBox.getText()));
        }
        if ( !DiscountPercentageBox.getText().isEmpty() ){
            foodItem.setPercentageDiscount(Double.valueOf(DiscountPercentageBox.getText()));
        }
        if ( !DiscountMonetaryBox.getText().isEmpty() ){
            foodItem.setDiscountValue(Double.valueOf(DiscountMonetaryBox.getText()));
        }
        
        if (!foodCategoory.getFoodItemList().contains(foodItem)){
            foodCategoory.setFoodItem(foodItem);
        }
        ListView.getItems().clear();
        foodItemList.clear();
        foodItemList.addAll(foodCategoory.getFoodItemList());
        ListView.getItems().addAll(foodItemList);
        
    }
    @FXML
    private void CancelBtnClicked(ActionEvent event) {
        System.out.println("You clicked me!");
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    private void NewBtnClicked(ActionEvent event) {
        //copyFoodCategoory.setFoodItem(foodItem);
        if (!foodCategoory.getFoodItemList().contains(foodItem)){
           
        }
        System.out.println("You clicked me!");
        System.out.println("You clicked me!");
        ItemBox.setText("");
        PriceBox.setText("");
        DiscountPercentageBox.setText("0");
        DiscountMonetaryBox.setText("0");
        foodItem = new FoodItem();
    }
    @FXML
    private void OkBtnClicked(ActionEvent event) {
        System.out.println("You clicked me!");
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
}
