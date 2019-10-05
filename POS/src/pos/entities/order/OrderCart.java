/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.order;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import pos.entities.food.FoodCategory;
import pos.entities.food.FoodItem;

/**
 *
 * @author fpatel
 */
@Embedded
public class OrderCart implements java.io.Serializable{
    private ObjectId id;
    @Embedded
    private List<OrderItem> orderItemList;
    Double totalDue;
    Double Total;
    Double tax;
    String MethodPayment;
    Double Change;

    public OrderCart() {
        totalDue = null;
        Change = null;
        tax = new Double(0);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItemList() {
        if ( orderItemList == null ){
           orderItemList = new ArrayList<>();
        }
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Double getTotal() {
        double total = 0;
        for(OrderItem item: getOrderItemList()){
            total = total+item.getTotalPrice().doubleValue();
        }
        this.Total = new Double(total);
        return this.Total;
    }

    public Double getTotalDue() {
        return totalDue;
    }
    
    public void setTotalDue(Double totalDue){
        this.totalDue = totalDue;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getChange() {
        return Change;
    }

    public void setChange(Double Change) {
        this.Change = Change;
    }
    public void addItem(FoodItem inputFoodItem){
        OrderItem inputItem = new OrderItem();
        inputItem.setItem(inputFoodItem);
        
        for(OrderItem item:getOrderItemList()){
            if(inputItem.getItem().getName() == item.getItem().getName())
            {
                //If found already Item in the Basket incresment it and update total price, then exit
                item.increaseQuantity();
                //Update total price
                //item.setTotalPrice(item.getQuanity().doubleValue()*item.getItem().getPrice());
                return;
            }
        }
        //New Item in the baskek
        inputItem.increaseQuantity();
        //inputItem.setTotalPrice(inputItem.getItem().getPrice());
        inputItem.setTotalTax(0.0);
        getOrderItemList().add(inputItem);
        
        return;
    }
    
    public OrderItem getItemByName(String name){
        for (OrderItem item: getOrderItemList()){
            if(name == item.getItem().getName())
            {
                return item;
            }
        }
        System.out.println("Return Null Order Item");
        return null;
    }
    
}
