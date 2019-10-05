/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;
import Context.Context;
import IO.FileManager;
import com.mongodb.MongoClient;
import com.sun.javafx.scene.control.skin.ComboBoxPopupControl;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javax.print.attribute.standard.DateTimeAtCreation;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import pos.controls.NumButton;
import pos.controls.NumPad;
import pos.entities.food.Food;
import pos.entities.food.FoodCategory;
import pos.entities.food.FoodItem;
import pos.entities.fop.Fop;
import pos.entities.fop.FopCode;
import pos.entities.fop.FopTable;
import pos.entities.order.OrderCart;
import pos.entities.order.OrderItem;
import pos.entities.order.Sales;
import pos.entities.order.Transaction;
import jpos.*;
import jpos.events.*;
import OrderScreenUtils.CashDrawerStatus;
import OrderScreenUtils.CategoryItemsEventHandler;
import pos.entities.fop.FP;
import pos.entities.order.CategoryMepEventHandler;

/**
 * FXML Controller class
 *
 * @author fpatel
 */

public class OrderScreenController implements Initializable {

    @FXML //  fx:id="CategoryListView"
    private ScrollPane CategoryListView;
    
    @FXML //  fx:id="ItemsListView"
    private ScrollPane ItemsListView;
    
    @FXML //  fx:id="OrderTable"
    private TableView<OrderItem> OrderTable;
    
    @FXML //  fx:id="QuantityCol"
    private TableColumn<OrderItem, Integer> QuantityCol;
    @FXML //  fx:id="ItemNameCol"
    private TableColumn<OrderItem, String> ItemNameCol;
    @FXML //  fx:id="TotalPriceCol"
    private TableColumn<OrderItem, Double> TotalPriceCol;
    @FXML //  fx:id="UnitPriceCol"
    private TableColumn<OrderItem, Double> UnitPriceCol;
    
    @FXML //  fx:id="FopCol"
    private TableColumn<OrderItem, Integer> FopCol;
    @FXML //  fx:id="ValueCol"
    private TableColumn<OrderItem, String> ValueCol;
    
    @FXML //  fx:id="FopTable"
    private TableView<Fop> FopTable;
    
    @FXML //fx:id="decreaseBtn"
    private Button decreaseBtn;
    @FXML //fx:id="increaseBtn"
    private Button increaseBtn; 
    @FXML
    private Button MainActionBtn;
    
    @FXML //fx:id="deleteCartItem"
    private Button deleteCartItem;
    
    @FXML //fx:id="LabelTotalDue"
    private Label LabelTotalDue;
    
    @FXML
    private Label LabelChange;
    
    @FXML 
    private Label LabelTotal;
    
    @FXML
    private Label LabelTextTotaDue;
    
    @FXML
    private Label LabelChangeText;
    
    @FXML
    private Label LabelCheckNumber;
    
    final private int buttonHeigh = 130;
    final private int buttonWidth = 150; 
    //Entities
     Food  aFood;
     Query<Food> foodQuery;
     Morphia morphia;
     Datastore datastore;
     private ObservableList<OrderItem> OrderItemObsList;
     private ObservableList<Fop> FormOfPaymentObsList;
     private OrderCart orderCartList;
     private Transaction transaction;
     private Sales sales; // one per session
     private FopTable mep;
     private List<Fop> ListOfFops;
     //Devices
     private CashDrawer cashDrawer;
     
     private List<Label> labelList;
     
     private FP fp;
     Context context;
     
     /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTransaction();
        
    }
    
    private void initTransaction(){
        // TODO
        //Entities
        labelList = new ArrayList<>();
        labelList.add(LabelTotalDue);//0
        labelList.add(LabelChange);//1
        labelList.add(LabelTextTotaDue);//2
        labelList.add(LabelTotal);//3
        labelList.add(LabelChangeText);//4
        
        setOrderItemObsList(FXCollections.observableArrayList());
        setFormOfPaymentObsList(FXCollections.observableArrayList());
        
       // morphia = new Morphia();
        //morphia.map(Food.class).map(FoodCategory.class).map(Transaction.class).map(Sales.class).map(OrderCart.class).map(FoodItem.class).map(OrderItem.class);
        
        setOrderCartList(new OrderCart());
        setTransaction(new Transaction());
        setSales(new Sales());
       // datastore = morphia.createDatastore(new MongoClient(), "test");
        //datastore.ensureIndexes();
        mep = new FopTable();
        fp = new FP();
        setListOfFops(new ArrayList<>());
        setCashDrawer(new CashDrawer());
        /* foodQuery = datastore.createQuery(Food.class);
        if (foodQuery.asList().isEmpty())
        {
            aFood = new Food();
        }else{
            aFood = foodQuery.asList().get(0);
        }
        */
        
        try {
            aFood = FileManager.readFood();
            sales = FileManager.readSales();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(aFood == null)
        {
            aFood = new Food();
        }
        if(sales == null){
            sales = new Sales();
        }
        
        LabelCheckNumber.setText("Check #" + Integer.toString(sales.getTransactionList().size()));
      
        //QtCol = new TableColumn<>("Qt");
        QuantityCol.setCellValueFactory(new PropertyValueFactory<>("quanity"));
        //QtItem = new TableColumn<>("Item");
        ItemNameCol.setCellValueFactory(new PropertyValueFactory<>("item"));
        //QtPrice = new TableColumn<>("Price");
        TotalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        UnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        //OrderTable = new TableView<>();
        OrderTable.setItems(OrderItemObsList);
        //OrderTable.getColumns().addAll(QuantityCol,ItemNameCol,UnitPriceCol,TotalPriceCol);
        FopCol.setCellValueFactory(new PropertyValueFactory<>("fopcode"));
        ValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        FopTable.setItems(FormOfPaymentObsList);
        cashDrawer.addStatusUpdateListener(new CashDrawerStatus(getItemsMainScreen()));
        
        context = new Context(aFood, OrderItemObsList, FormOfPaymentObsList, orderCartList, transaction, sales, mep, ListOfFops, cashDrawer, labelList, CategoryListView, ItemsListView,fp);
        fillItemMainMenu(context, CategoryListView);
        MainActionBtn.setText("PAY");
        getAmountLabels().get(0).setText("");
        getAmountLabels().get(1).setText("");
        getAmountLabels().get(3).setText("");
    }

    public ScrollPane getItemsMainScreen() {
        return ItemsListView;
    }
    public void setItemMainScreen(ScrollPane ItemsListView){
        this.ItemsListView = ItemsListView;
    }
    public void setLabelTotalDue(Label LabelTotalDue) {
        this.LabelTotalDue = LabelTotalDue;
    }
    public List<Label> getAmountLabels() {
        return labelList;
    }
    public ObservableList<OrderItem> getOrderItemObsList() {
        return OrderItemObsList;
    }
    public void setOrderItemObsList(ObservableList<OrderItem> OrderItemObsList) {
        this.OrderItemObsList = OrderItemObsList;
    }
    public ObservableList<Fop> getFormOfPaymentObsList() {
        return FormOfPaymentObsList;
    }
    public void setFormOfPaymentObsList(ObservableList<Fop> FormOfPaymentObsList) {
        this.FormOfPaymentObsList = FormOfPaymentObsList;
    }
    public OrderCart getOrderCartList() {
        return orderCartList;
    }
    public void setOrderCartList(OrderCart orderCartList) {
        this.orderCartList = orderCartList;
    }
    public Transaction getTransaction() {
        return transaction;
    }
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    public Sales getSales() {
        return sales;
    }
    public void setSales(Sales sales) {
        this.sales = sales;
    }
    public List<Fop> getListOfFops() {
        return ListOfFops;
    }
    public void setListOfFops(List<Fop> ListOfFops) {
        this.ListOfFops = ListOfFops;
    }
    public CashDrawer getCashDrawer() {
        return cashDrawer;
    }
    public void setCashDrawer(CashDrawer cashDrawer) {
        this.cashDrawer = cashDrawer;
    }
 
    private void fillItemMainMenu(Context context, ScrollPane pane){
         // Initialize the Items Category Menu
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        for(FoodCategory cat: context.getFood().getFoodCategoryList()){
            Button abutton = new Button(cat.getName());
            abutton.setPrefWidth(buttonWidth);
            abutton.setPrefHeight(buttonHeigh);
            abutton.setFont(new Font(25));
            abutton.setAlignment(Pos.CENTER);
            abutton.wrapTextProperty().setValue(true);        
            abutton.setContentDisplay(ContentDisplay.CENTER);
            //abutton.setOnAction(new CategoryItemsEventHandler(cat, getItemsMainScreen(),getOrderCartList(),OrderItemObsList,getAmountLabels()));
            abutton.setOnAction(new CategoryItemsEventHandler(cat,context));
            vbox.getChildren().add(abutton); 
            System.out.println("Setting cat list view"+cat.getName());
        }
        CategoryListView.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        CategoryListView.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        pane.setContent(vbox);

        System.out.println("Setting cat list view");
    
    }
    
    public static void updateAmountLabels(Context context){
        System.out.println("Size of cart obj: " + String.valueOf(context.getOrderCartList().getOrderItemList().size()));
       
        //labelList.add(LabelTotalDue);//0
        //labelList.add(LabelChange);//1
        //labelList.add(LabelTextTotaDue);//2
        //labelList.add(LabelTotal);//3
        //labelList.add(LabelChangeText);//4 
        
       if(context.getOrderCartList().getOrderItemList().isEmpty())
       {
           System.out.println("Cart is empty");
           context.getLabelList().get(0).setText("");
           context.getLabelList().get(1).setText("");
           context.getLabelList().get(3).setText("");
           context.getLabelList().get(2).setVisible(false);
           context.getLabelList().get(4).setVisible(false);
           
           return;
       }
       
        if(context.getFp() != null){
            context.getOrderCartList().setTotalDue(new Double(0));
            Double TotalDue, FOP, Total;
            TotalDue = context.getOrderCartList().getTotalDue();
            Total = context.getOrderCartList().getTotal();
            FOP = context.getFp().getTotal();

            if (FOP >= Total){
                //Change to be given
                Double change = new Double(FOP-Total);
                context.getOrderCartList().setChange(change);
                context.getOrderCartList().setTotalDue(new Double(0));
            }
            else if(FOP < Total){
                // More FOPs are needed
                //Compute new TotalDue
                Double newTotalDue = Total - FOP;
                context.getOrderCartList().setTotalDue(newTotalDue);
                context.getOrderCartList().setChange(new Double(0));
            }
       }
       if(context.getOrderCartList().getTotalDue() != null)
       {
           context.getLabelList().get(0).setText(context.getOrderCartList().getTotalDue().toString());
           context.getLabelList().get(2).setVisible(true);
           if(context.getOrderCartList().getTotalDue() == 0){
               context.getLabelList().get(2).setVisible(false);
               context.getLabelList().get(0).setText("");
           }
       }
       if(context.getOrderCartList().getChange() != null)
       {   
           context.getLabelList().get(1).setText(context.getOrderCartList().getChange().toString());
           context.getLabelList().get(4).setVisible(true);
           if(context.getOrderCartList().getChange().doubleValue() == 0){
               context.getLabelList().get(1).setText("");
               context.getLabelList().get(4).setVisible(false);
           }
       }
       
       if(context.getOrderCartList().getTotal() != null){
           context.getLabelList().get(3).setText(context.getOrderCartList().getTotal().toString());
       }
    }
    
    @FXML
    private void IncreaseButton(ActionEvent event){
        ObservableList<OrderItem> selectedItems;
        selectedItems = OrderTable.getSelectionModel().getSelectedItems();
        //selectedItems.get(0).increaseQuantity();
        System.out.println("Size of cart tabl: " + String.valueOf(OrderTable.getItems().size()));
        if( !selectedItems.isEmpty())
        {
            System.out.println("Increment Item: " + selectedItems.get(0).getItem().getName() );
            OrderItem item = getOrderCartList().getItemByName( selectedItems.get(0).getItem().getName());
            if (item != null)
            {
                item.increaseQuantity();
                OrderTable.refresh();
                updateAmountLabels(context);
            }
        }
    }
    @FXML
    private void DecreaseButton(ActionEvent event){
        ObservableList<OrderItem> selectedItems;
        selectedItems = OrderTable.getSelectionModel().getSelectedItems();
        //selectedItems.get(0).decreaseQuantity();
        System.out.println("Size of cart tabl: " + String.valueOf(OrderTable.getItems().size()));
        if( !selectedItems.isEmpty())
        {
            System.out.println("Decrement Item: " + selectedItems.get(0).getItem().getName() );
            OrderItem item = getOrderCartList().getItemByName( selectedItems.get(0).getItem().getName());
            if (item != null)
            {
                item.decreaseQuantity();
                OrderTable.refresh();
                updateAmountLabels(context);
            }
        }
    }
    @FXML
    private void DeleteCartItem(ActionEvent event){
        ObservableList<OrderItem> selectedItems, allItems;
        selectedItems = OrderTable.getSelectionModel().getSelectedItems();
        System.out.println("Size of cart tabl: " + String.valueOf(OrderTable.getItems().size()));
        if( !selectedItems.isEmpty())
        {
            System.out.println("Delete Item: " + selectedItems.get(0).getItem().getName() );
            OrderItem item = context.getOrderCartList().getItemByName( selectedItems.get(0).getItem().getName());
            if (item != null)
            {
                context.getOrderCartList().getOrderItemList().remove(item);
                allItems = OrderTable.getItems();
                selectedItems.forEach(allItems::remove);
                OrderTable.refresh();
                updateAmountLabels(context);
                
            }
        }
    }
    @FXML
    private void UpButtonCategory(ActionEvent event) {
          CategoryListView.vvalueProperty().set(CategoryListView.getVvalue()-0.5);
   
        }
    @FXML
    private void DownButtonCategory(ActionEvent event) {
          CategoryListView.vvalueProperty().set(CategoryListView.getVvalue()+0.5); 
        }
    
    @FXML
     private void UpButtonItem(ActionEvent event) {
          getItemsMainScreen().vvalueProperty().set(ItemsListView.getVvalue()-0.5);
   
        }
    @FXML
    private void DownButtonItem(ActionEvent event) {
          getItemsMainScreen().vvalueProperty().set(ItemsListView.getVvalue()+0.5); 
        }
    
    @FXML
    private void DeletePayItem(ActionEvent event){
    ObservableList<Fop> selectedItems, allItems;
        selectedItems = FopTable.getSelectionModel().getSelectedItems();
        System.out.println("Size of cart tabl: " + String.valueOf(OrderTable.getItems().size()));
        if( !selectedItems.isEmpty())
        {
            System.out.println("Delete Item: " + selectedItems.get(0).getFopcode().toString() );
           
            Fop fop = context.getFp().getFopByCode(selectedItems.get(0).getFopcode());
            
            if (fop != null)
            {
                context.getFp().getFops().remove(fop);
                allItems = FopTable.getItems();
                selectedItems.forEach(allItems::remove);
                FopTable.refresh();
                updateAmountLabels(context);
                
            }
        }
          
    }
    @FXML
    private void endTransaction(ActionEvent event) throws IOException{
        //Query payment methods
        // validate the transaction
        if(isOrderListEmpty())
        {
            System.out.println("Cart is empty");
            getAmountLabels().get(0).setText("");
            getAmountLabels().get(1).setText("");
            getAmountLabels().get(3).setText("");
            PrintMessage(context.getItemsListView(), "Cart is empty");
            return;
        }
        if(context.getOrderCartList().getTotalDue() > 0){
            PrintMessage(context.getItemsListView(), "Payment not complete");
            return;
        }
        setTransaction(new Transaction());
        //process payment
        context.getTransaction().setOrderCart(context.getOrderCartList());
        context.getTransaction().setEmployee("Faizal");
        LocalDateTime datetime  = LocalDateTime.now();
        context.getTransaction().setDatetime(datetime);
        context.getSales().getTransactionList().add(context.getTransaction());   
        //datastore.save(sales);
        context.getOrderItemObsList().clear();
        context.getFormOfPaymentObsList().clear();
        FopTable.refresh();
        OrderTable.refresh();
        FileManager.writeSales(context.getSales());
        
        context.setOrderCartList(new OrderCart());
        context.setTransaction(new Transaction());
        context.setFp(new FP());
        fillItemMainMenu(context, CategoryListView);
       
        initTransaction();
        System.out.println("Sales size: " + context.getSales().getTransactionList().size());
    }
    @FXML
    private void CancelAction(ActionEvent event){
        initTransaction();
    }
    @FXML
    private void VoidAction(ActionEvent event){
    
    }
    
    @FXML
    private void mainAction(ActionEvent event){
        //Show FOPs on CategoryListView
        switch (MainActionBtn.getText()){
            case "PAY":
                //check if there is order in the list
                if(isOrderListEmpty())
                {
                    PrintMessage(getItemsMainScreen(),"Order is empty!");
                }
                else{
                    fillFopMainMenu(CategoryListView);
                    MainActionBtn.setText("Menu");
                }
                break;
            case "Menu":
                fillItemMainMenu(context, CategoryListView);
                MainActionBtn.setText("PAY");
                break;
        }
        
    }
    
    private void fillFopMainMenu(ScrollPane pane){
        
        //Loop on FOP and assign the FOP object to the action event handler 
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        for(FopCode fop: mep.getListFop()){
            if(fop.isEnable()){
                Button abutton = new Button(fop.toString());
                abutton.setPrefWidth(buttonWidth);
                abutton.setPrefHeight(buttonHeigh);
                abutton.setFont(new Font(25));
                abutton.setAlignment(Pos.CENTER);
                abutton.wrapTextProperty().setValue(true);        
                abutton.setContentDisplay(ContentDisplay.CENTER);
                abutton.setTextAlignment(TextAlignment.CENTER);
                /*abutton.setOnAction(new CategoryMepEventHandler(fop,getItemsMainScreen(),
                                        getOrderCartList(),getFormOfPaymentObsList(),
                                        getListOfFops(),getAmountLabels()));*/
                abutton.setOnAction(new CategoryMepEventHandler(context,fop));
                vbox.getChildren().add(abutton); 
            }
        }
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        pane.setContent(vbox);
        
     }
     
    private boolean isOrderListEmpty()
     {
        return getOrderCartList().getOrderItemList().isEmpty();
     } 
     
    public static void PrintMessage(ScrollPane aItemsListView, String OutputMessage){
        Label label = new Label(OutputMessage);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font(33));
        aItemsListView.setContent(label);
     }
     
}
