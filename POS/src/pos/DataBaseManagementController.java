/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.BSON;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import pos.entities.food.Food;
import pos.entities.food.FoodCategory;
import pos.entities.food.FoodItem;

/**
 * FXML Controller class
 *
 * @author fpatel
 */
public class DataBaseManagementController implements Initializable {

     @FXML //  fx:id="DatasetDropDownList"
     private ComboBox DatasetDropDownList; 
     
     @FXML //  fx:id="SubDataDropDownList"
     private ComboBox SubDataDropDownList;
     
     @FXML //  fx:id="newItemLabel"
     private Label newItemLabel;
     
     @FXML //  fx:id="AddSubDataBtn"
     private Button  AddSubDataBtn;
     
     @FXML //  fx:id="AddItemBtn"
     private Button  AddItemBtn;
     
     @FXML //  fx:id="NewItemTextBox"
     private TextField NewItemTextBox;
     
     @FXML //  fx:id="ErrorLabel"
     private Label ErrorLabel;
     @FXML //  fx:id="ItemViewList"
     private ListView ItemViewList;
     
     private ObservableList<FoodCategory> foodCategory;
     TextInputDialog addDialog;
     Alert alertDialog;
     
     //Entities
     Food  aFood;
     Query<Food> foodQuery;
     Morphia morphia;
     Datastore datastore;
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try{
            MongoClient mongo = new MongoClient();
              
        }catch(Exception ex){
            ErrorLabel.setVisible(true);
            ErrorLabel.setText("Unable to connect to database");
            return;
        }
        
        morphia = new Morphia();
        morphia.map(Food.class).map(FoodCategory.class);
        
        datastore = morphia.createDatastore(new MongoClient(), "test");
        datastore.ensureIndexes();
        
        foodQuery = datastore.createQuery(Food.class);
        if (foodQuery.asList().isEmpty())
        {
            aFood = new Food();
        }else{
            aFood = foodQuery.asList().get(0);
        }
   
        // TODO
        DatasetDropDownList.getItems().add("Staff");
        DatasetDropDownList.getItems().add("Food");
        
           //setItems(foodCategory);
           
           
        DatasetDropDownList.getSelectionModel().selectedItemProperty().addListener(new
                ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if (newValue != null){
                                     
                    if (oldValue != newValue)
                    {
                        if(oldValue == "Staff"){
                            SubDataDropDownList.getSelectionModel().selectedItemProperty().removeListener(StaffListener);
                        }
                        if(oldValue == "Food"){
                            SubDataDropDownList.getSelectionModel().selectedItemProperty().removeListener(FoodListener);
                        }
                        if (newValue == "Staff"){
                            //Fill SubDataDropDownList with Staff data
                            SubDataDropDownList.getSelectionModel().clearSelection();
                            SubDataDropDownList.getItems().clear();
                            SubDataDropDownList.getItems().add("Admin");
                            SubDataDropDownList.getItems().add("Cashier");
                            SubDataDropDownList.getSelectionModel().selectedItemProperty().addListener(StaffListener);
                        }
                        if (newValue == "Food"){
                            SubDataDropDownList.getSelectionModel().clearSelection();
                            SubDataDropDownList.getItems().clear();
                            foodCategory = FXCollections.observableArrayList();
                            if (! aFood.getFoodCategoryList().isEmpty())
                            {
                                for (FoodCategory aCat :  aFood.getFoodCategoryList()){
                                    foodCategory.add(aCat);
                                }
                                /* foodCategory =
                                FXCollections.observableArrayList(
                                new FoodCategory("Drinks"),
                                new FoodCategory("Menu Enfant")); */
                                SubDataDropDownList.getItems().addAll(foodCategory);
                                 
                            }
                            SubDataDropDownList.getSelectionModel().selectedItemProperty().addListener(FoodListener);
                            AddSubDataBtn.setDisable(false);
                        }
                        }else{
                        
                        }
                    
                    if(!SubDataDropDownList.getItems().isEmpty())
                    {
                        SubDataDropDownList.setDisable(false);
                        AddSubDataBtn.setDisable(false);
                    }else{
                        SubDataDropDownList.setDisable(true);
                    }
                }
              }
            });
           
                  
    }
    
    private ChangeListener<String> StaffListener= new ChangeListener<String>(){
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                 if(newValue != null){
                                    System.out.println("Selected Country: " + newValue);
                                    AddItemBtn.setDisable(false);
                                } 
                            }};
    private ChangeListener<FoodCategory> FoodListener= new ChangeListener<FoodCategory>(){
                        @Override
                        public void changed(ObservableValue<? extends FoodCategory> observable, FoodCategory oldValue, FoodCategory newValue) {
                             if(newValue != null){
                                NewItemTextBox.setDisable(false);
                                System.out.println("Selected Country: " + newValue.getName());
                                newItemLabel.setText("New " + newValue.getName());
                                AddItemBtn.setDisable(false);
                                fillListViewByFoodCateegory(newValue);
                            }
                        }};
    
    void fillListViewByFoodCateegory(FoodCategory input){
        //fill the liist panel
        ItemViewList.getItems().clear();
        // loog in FoodCategory and get all the Items
        for( FoodItem item: input.getFoodItemList() ){
            ItemViewList.getItems().add(item);
        }
    }
    
    @FXML
    private void addBtnClicked(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        String ItemName;
        if (NewItemTextBox.getText().isEmpty())
        {
            alertDialog = new Alert(Alert.AlertType.INFORMATION);
            alertDialog.setTitle("Information");
            alertDialog.setContentText("No entry");
            alertDialog.showAndWait(); 
        }else // open a new window
        {
            ItemName  = NewItemTextBox.getText();
            //Read the category from the ComboBox
            
            //Listen to DatasetDropDownList, to determine in Adding Food or Staff informarion
            
            //Add FoodItem
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFoodItem.fxml"));
            FoodCategory foodCat  = (FoodCategory) SubDataDropDownList.getSelectionModel().getSelectedItem();
            String category = foodCat.toString();
            dialog.setTitle("Item Management in ["+category+"]");
            Scene scene = new Scene((Pane)loader.load());
            dialog.setResizable(false);
            dialog.setScene(scene);          
            AddFoodItemController controller = loader.<AddFoodItemController>getController();
            FoodCategory inputFoodCategory = aFood.getFoodCategoryByName(category);
            controller.initData(inputFoodCategory,datastore, ItemName);
            dialog.showAndWait();
            datastore.save(aFood);
            fillListViewByFoodCateegory(foodCat);
            NewItemTextBox.setText("");
            //End Add FoodItem
            
            //Add Staff
            
            //End Add Staff
            
            
            
        }
    }
    @FXML
    private void editBtnClicked(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    @FXML
    private void deleteBtnClicked(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    @FXML
    private void exitBtnClicked(ActionEvent event) {
        System.out.println("You clicked me!");
        datastore.save(aFood);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }//exitBtnClicked
    
    
    @FXML
    private void addSubDataClicked(ActionEvent event){
        addDialog = new TextInputDialog();
        if(DatasetDropDownList.getSelectionModel().isEmpty())
        {
            alertDialog = new Alert(Alert.AlertType.INFORMATION);
            alertDialog.setTitle("Information");
            alertDialog.setContentText("You must select a Data Category");
            alertDialog.showAndWait();
            return;
        }
        String selectedDataSet = DatasetDropDownList.getSelectionModel().getSelectedItem().toString();
        addDialog.setTitle("Add " + selectedDataSet);
        addDialog.setHeaderText("Insert a new Category for "+ selectedDataSet);
        Optional<String> result = addDialog.showAndWait();
        String entered = "";
        if (result.isPresent()){
            entered = result.get();
            if(entered.isEmpty() || result.get() == "")
            {
                alertDialog = new Alert(Alert.AlertType.INFORMATION);
                alertDialog.setTitle("Information");
                alertDialog.setContentText("No entry");
                alertDialog.showAndWait(); 
                return;
            }
        }
        switch (selectedDataSet){
            case "Staff":
                if(entered!="") //TODO: moce this method under switch
                    addStaffCategory(entered);
                break;
            case "Food":
                if(entered!="")
                    addFoodCategory(entered);
                break;
            default:                
        }
        //Read from Database and fetch the Dropdown list
            
          
        }//addSubDataClicked
       
    
    void addStaffCategory(String input)
    {
        
    }
    
    void addFoodCategory(String input)
    {
        if (!aFood.findCategory(input))
        {
            aFood.getFoodCategoryList().add(new FoodCategory(input));
            SubDataDropDownList.getItems().add(new FoodCategory(input));
            System.out.println("Entered " + input);
            if (SubDataDropDownList.isDisable())
                SubDataDropDownList.setDisable(false);
        }else{
            showAlertCategoryExists(input);
        }
    }//addFoodCategory
    
    void showAlertCategoryExists(String input){
        alertDialog = new Alert(Alert.AlertType.INFORMATION);
        alertDialog.setTitle("Information");
        alertDialog.setContentText("The Cathegory "+input+" exists in Databse");
        alertDialog.showAndWait();
    }//showAlertCategoryExists
    
    
}//DataBaseManagementController
