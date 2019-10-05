/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderScreenUtils;

import Context.Context;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import pos.OrderScreenController;
import pos.entities.food.FoodItem;
import pos.entities.fop.Fop;
import pos.entities.order.OrderCart;
import pos.entities.order.OrderItem;

/**
 *
 * @author fpatel
 */

public class ItemsEventHandler implements EventHandler<ActionEvent>{
        FoodItem item;
        private OrderCart orderCartList;
        private ObservableList<OrderItem> OrderItemObsList;
        private List<Label> labelList;
        private List<Fop> ListOfFops;
        private Context context;

        public ItemsEventHandler(Context context,FoodItem item) {
            this.context = context;
            this.item = item;
        }
        
        public ItemsEventHandler(FoodItem item, OrderCart orderCartList, ObservableList<OrderItem> OrderItemObsList,
                List<Label> labelList, List<Fop> ListOfFops) {
            this.item = item;
            this.orderCartList = orderCartList;
            this.OrderItemObsList = OrderItemObsList;
            this.labelList = labelList;
            this.ListOfFops = ListOfFops;
        }
        
        @Override
        public void handle(ActionEvent event) {
            //Add to table order
            System.out.println("Add Item to Cart " );
            
            context.getOrderCartList().addItem(FoodItem.newInstance(item));
            
            //fill QtList from orderCartList
            //OrderItemObsList.clear();
            context.getOrderItemObsList().clear();
            for(OrderItem item: context.getOrderCartList().getOrderItemList()){
               // OrderItemObsList.add(item);
                context.getOrderItemObsList().add(item);
            }
            OrderScreenController.updateAmountLabels(context); 
        } 
    }
