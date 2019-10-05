/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderScreenUtils;

import Context.Context;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pos.controls.Variables;
import pos.entities.food.FoodCategory;
import pos.entities.food.FoodItem;
import pos.entities.fop.Fop;
import pos.entities.order.OrderCart;
import pos.entities.order.OrderItem;

/**
 *
 * @author fpatel
 */

public class CategoryItemsEventHandler implements EventHandler<ActionEvent>{

        private FoodCategory foodcategory;
        private ScrollPane ItemsListView;
        private OrderCart orderCartList;
        private ObservableList<OrderItem> OrderItemObsList;
        private List<Label> labelList;
        private List<Fop> ListOfFops;
        private Context context;

        public CategoryItemsEventHandler(FoodCategory foodcategory, Context context) {
            this.context = context;
            this.foodcategory = foodcategory;
        }
        
        
        
        public CategoryItemsEventHandler(FoodCategory foodcategory, ScrollPane ItemsListView, OrderCart orderCartList, ObservableList<OrderItem> OrderItemObsList,
                    List<Label> labelList) {
            this.foodcategory = foodcategory;
            this.ItemsListView = ItemsListView;
            this.orderCartList = orderCartList;
            this.OrderItemObsList = OrderItemObsList;
            this.labelList = labelList;
            
        }

        @Override
        public void handle(ActionEvent event) {
             System.out.println("Button " + event.toString());
             VBox vbox = new VBox();
             vbox.setSpacing(10);
             int pos = 0;
             HBox hbox = null;
             List<HBox> hboxList = new ArrayList<>();
             int itemsAdded = 0;
              
             for (FoodItem item: foodcategory.getFoodItemList()){
                System.out.println("Looping on: " + item.getName() );
                if (pos <3 ){
                   if(pos ==0){
                     hbox = new HBox(); 
                     hboxList.add(hbox);
                   }
                    hbox.setSpacing(10);
                    Button abutton = new Button(item.getName());
                    abutton.setPrefWidth(Variables.BUTTON_WIDTH);
                    abutton.setPrefHeight(Variables.BUTTON_HEIGHT);
                    abutton.setFont(new Font(25));
                    abutton.setAlignment(Pos.CENTER);
                    abutton.wrapTextProperty().setValue(true);    
                    abutton.setContentDisplay(ContentDisplay.CENTER);            
                    //abutton.setOnAction(new ItemsEventHandler(item,orderCartList, OrderItemObsList, labelList,ListOfFops ));
                    abutton.setOnAction(new ItemsEventHandler(context,item));
                    abutton.setTextAlignment(TextAlignment.CENTER);
                    hbox.getChildren().add(abutton);
                    System.out.println("Adding button " + abutton.getText() );
                    itemsAdded++;
                }
                pos++;
                if(pos == 3)
                    pos = 0;
             }
             for(HBox box: hboxList){
                    vbox.getChildren().add(box);
             }
             //ItemsListView.setContent(vbox);
             context.getItemsListView().setContent(vbox);
             System.out.println("ItemListView: " +  context.getItemsListView().getContent().toString() );
        }
    }
