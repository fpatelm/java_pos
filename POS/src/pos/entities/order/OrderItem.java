/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.order;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;
import pos.entities.food.FoodItem;
import pos.entities.fop.Fop;

/**
 *
 * @author fpatel
 */
@Embedded
public class OrderItem implements java.io.Serializable{
    private ObjectId id;
    
    @Embedded
    FoodItem item;
    Integer quanity;
    Double totalPrice;
    Double unitPrice;
    Double totalTax;
    String ItemName;
    
    public OrderItem(){
        quanity = 0;
    }
    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public FoodItem getItem() {
        return item;
    }

    public void setItem(FoodItem item) {
        this.item = item;
    }

    public Integer getQuanity() {
        return quanity;
    }

    public void setQuanity(Integer quanity) {
        this.quanity = quanity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }
    
    public void setItemName(String itemName){
        getItem().setName(itemName);
        ItemName = getItem().getName();
    }
    public String getItemName(){
        return ItemName;
    }

    public Double getUnitPrice() {
        if(item!=null)
        {
            unitPrice = item.getPrice();
            return unitPrice;
        }else
            return null;
    }
    
    public void increaseQuantity()
    {
        quanity = quanity + 1;
        setTotalPrice(getQuanity().doubleValue()*getItem().getPrice());
    }
    
    
    public void decreaseQuantity()
    {
        if(quanity  > 1)
        {
            quanity = quanity - 1;
            setTotalPrice(getQuanity().doubleValue()*getItem().getPrice());
        }
    }
    
}
