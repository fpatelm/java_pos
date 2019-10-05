/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import IO.FileManager;
import com.mongodb.MongoClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import pos.entities.food.Food;
import pos.entities.food.FoodCategory;
import pos.entities.food.FoodItem;
import pos.entities.order.OrderCart;
import pos.entities.order.OrderItem;

/**
 * FXML Controller class
 *
 * @author fpatel
 */
public class AddItemController implements Initializable {

    //FXML variables
    @FXML
    private TextField tb_newcategory;
    @FXML
    private TextField tb_item;
    @FXML
    private TextField tb_price;
    @FXML
    private TextField tb_discount;
    @FXML
    private TextField tb_discount2;
    @FXML
    private Button btn_addCat;
    @FXML
    private Button btn_removeCat; 
    @FXML
    private Button btn_save; 
    @FXML
    private Button btn_clear;
    @FXML
    private Button btn_delete;
    @FXML
    private ComboBox<FoodCategory> cb_foodCatList;
    @FXML
    private TableView<FoodItem> tv_Item;
    @FXML //  fx:id="QuantityCol"
    private TableColumn<FoodItem, String> ItemCol;
    @FXML //  fx:id="ItemNameCol"
    private TableColumn<FoodItem, Double> PriceCol;
    @FXML //  fx:id="TotalPriceCol"
    private TableColumn<FoodItem, Double> DiscCol;
    @FXML //  fx:id="UnitPriceCol"
    private TableColumn<FoodItem, Double> Disc2Col;
    //Entities
    Food  aFood;
    Query<Food> foodQuery;
    Morphia morphia;
    Datastore datastore;
    private ObservableList<FoodItem> FoodItemObsList;
    private ObservableList<FoodCategory> foodCategoryObsList;
    FoodItem oldSelectedValue;
    Alert alertDialog;
    
    //File database
    FileInputStream f_in = null;
    FileOutputStream f_out = null;
    ObjectOutputStream obj_out = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FoodItemObsList = FXCollections.observableArrayList();
        foodCategoryObsList = FXCollections.observableArrayList();
        ItemCol = new TableColumn<>("Item");
        ItemCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PriceCol = new TableColumn<>("Price");
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        DiscCol = new TableColumn<>("Discount(€)");
        DiscCol.setCellValueFactory(new PropertyValueFactory<>("DiscountValue"));
        Disc2Col = new TableColumn<>("Discount(%)");
        Disc2Col.setCellValueFactory(new PropertyValueFactory<>("PercentageDiscount"));
        tv_Item.setItems(FoodItemObsList);
        tv_Item.getColumns().addAll(ItemCol, PriceCol, DiscCol, Disc2Col);
        
        
        
        try {
            //local file serializable
            //Read file, if not available, create a new one
            aFood = FileManager.readFood();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(AddItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        if(aFood == null){
            aFood = new Food();
        }
        
        
        //Initialize DataBase
     /*   morphia = new Morphia();
        morphia.map(Food.class).map(FoodCategory.class).map(FoodItem.class);
        datastore = morphia.createDatastore(new MongoClient(), "test");
        datastore.ensureIndexes();
        
        //
        foodQuery = datastore.createQuery(Food.class);
        if (foodQuery.asList().isEmpty())
        {
            aFood = new Food();
        }else{
            aFood = foodQuery.asList().get(0);
        }*/
        
        if (! aFood.getFoodCategoryList().isEmpty())
        {
            fillCategoryObsList();
        }
        
        //Listeners
        cb_foodCatList.getSelectionModel().selectedItemProperty().addListener(ItemCategoryListener);   
        tv_Item.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends FoodItem> observable, FoodItem oldValue, FoodItem newValue) -> {
            clearInputData();
            oldSelectedValue = oldValue;
            //Select Items from the table and bind it to the input controls
            if(cb_foodCatList.getSelectionModel() == null){
                    return;
            }
            //System.out.println("Selected Item: " + newValue.getName() );
            FoodCategory aSelectedFoodCategory = cb_foodCatList.getSelectionModel().getSelectedItem();
            if(newValue != null)
            {
                FoodItem selectedItem = aSelectedFoodCategory.getFoodByName(newValue.getName()); 
                if(selectedItem!=null)
                    fillInputControls(selectedItem);
            }
        });
    }
    
    // Listeners
    private ChangeListener<FoodCategory> ItemCategoryListener= new ChangeListener<FoodCategory>(){
    @Override
    public void changed(ObservableValue<? extends FoodCategory> observable, FoodCategory oldValue, FoodCategory newValue) {
        FoodItemObsList.clear();
        if(newValue != null){
            // Fill the table with the right items
            FoodCategory aCategory =  aFood.getFoodCategoryByName(newValue.toString());
            for(FoodItem item : aCategory.getFoodItemList()){
                FoodItemObsList.add(item);
            }
            //FoodItemObsList
        }
    }};  
    
    private void fillInputControls(FoodItem input){
        tb_item.clear();
        tb_price.clear();
        tb_discount.clear();
        tb_discount2.clear();
        tb_item.setText(input.getName());
        tb_price.setText(input.getPrice().toString());
        tb_discount.setText(input.getDiscountValue().toString());
        tb_discount2.setText(input.getPercentageDiscount().toString());
    }
    
    private void fillCategoryObsList(){
        foodCategoryObsList.clear();
        cb_foodCatList.getItems().clear();
        for (FoodCategory aCat :  aFood.getFoodCategoryList()){
            foodCategoryObsList.add(aCat);
        }
        cb_foodCatList.getItems().addAll(foodCategoryObsList);
    }
   private void fillTable(FoodCategory input ){
       FoodItemObsList.clear();
       //tv_Item.getItems().clear();
       for(FoodItem item : input.getFoodItemList()){
           FoodItemObsList.add(item);
       }
       //tv_Item.setItems(FoodItemObsList);
   
   }
    @FXML
    private void saveBtn(ActionEvent event) throws IOException{
        //do validation in all TextBox and create a Item object based on the values
        
        if(cb_foodCatList.getSelectionModel().isEmpty())
        {
            //Info, entry already in database
            alertDialog = new Alert(Alert.AlertType.INFORMATION);
            alertDialog.setTitle("Information");
            alertDialog.setContentText("Select a category");
            alertDialog.showAndWait(); 
            return;
        }
        
        //FoodCategory aSelectedFoodCategory = cb_foodCatList.getSelectionModel().getSelectedItem();
        FoodCategory catg = aFood.getFoodCategoryByName(cb_foodCatList.getSelectionModel().getSelectedItem().getName());
        if (!tb_item.getText().isEmpty() && !tb_price.getText().isEmpty() && !tb_discount.getText().isEmpty() && !tb_discount2.getText().isEmpty()){
            FoodItem aNewItem = new FoodItem(tb_item.getText());
            aNewItem.setPrice(Double.parseDouble(tb_price.getText()));
            aNewItem.setDiscountValue(Double.parseDouble(tb_discount.getText()));
            aNewItem.setPercentageDiscount(Double.parseDouble(tb_discount2.getText()));
            
            
            
            //Index in database
            if(!tv_Item.getSelectionModel().getSelectedItems().isEmpty()){
                FoodItem aItem = catg.getFoodByName(tv_Item.getSelectionModel().getSelectedItems().get(0).getName());
                if(aItem!=null)
                {
                    System.out.println("replacing item");
                    aItem.setName(tb_item.getText());             
                    aItem.setPrice(Double.parseDouble(tb_price.getText()));
                    aItem.setDiscountValue(Double.parseDouble(tb_discount.getText()));
                    aItem.setPercentageDiscount(Double.parseDouble(tb_discount2.getText()));
                }
                else
                {
                System.out.println("adding new item, item" + tv_Item.getSelectionModel().getSelectedItems().get(0).getName() + "not found" );
                catg.getFoodItemList().add(aNewItem);
                }

            }
            else
            {
                System.out.println("adding new item: " + aNewItem.getName() );
                catg.getFoodItemList().add(aNewItem);
            }
           // datastore.save(aNewItem);
            clearInputData();
            //datastore.save(aFood);
            FileManager.writeFood(aFood);
            
        }
        //refresh obs list
        fillTable(catg);
    }
    
    @FXML
    private void addCategoryBtn(ActionEvent event) throws IOException{
        if( !tb_newcategory.getText().isEmpty() ){
            if( !aFood.findCategory(tb_newcategory.getText()) )
            {
                aFood.getFoodCategoryList().add(new FoodCategory(tb_newcategory.getText()));
                fillCategoryObsList();
                tb_newcategory.clear();
                //datastore.save(aFood);
                
                FileManager.writeFood(aFood);
            }
            else
            {
                //Info, entry already in database
                alertDialog = new Alert(Alert.AlertType.INFORMATION);
                //alertDialog.setTitle("Information");
                alertDialog.setContentText("Entry already exists in database");
                alertDialog.showAndWait(); 
                tb_newcategory.clear();
            }
        }
    } 
    
    @FXML
    private void newBtn(ActionEvent event){
        clearInputData();
    }
    
    @FXML
    private void deeteBtn(ActionEvent event) throws IOException{
        ObservableList<FoodItem> selectedItems = tv_Item.getSelectionModel().getSelectedItems();
        ObservableList<FoodItem> items = tv_Item.getItems();
        
        //delete from object
        //Index in database
        FoodCategory catg = aFood.getFoodCategoryByName(cb_foodCatList.getSelectionModel().getSelectedItem().getName());
        if(!tv_Item.getSelectionModel().getSelectedItems().isEmpty()){
            FoodItem aItem = catg.getFoodByName(tv_Item.getSelectionModel().getSelectedItems().get(0).getName());
            if(aItem!=null)
            {
                catg.getFoodItemList().remove(aItem);
                selectedItems.forEach(items::remove);
                //datastore.delete(aItem);
               // aItem = null;
                //datastore.save(aFood);
                //datastore.save(catg);
                FileManager.writeFood(aFood);
            }
            
        }
    }
    
    
    private void clearInputData(){         
        tb_item.clear();
        tb_price.clear();
        tb_discount.clear();
        tb_discount2.clear();
    }
}

