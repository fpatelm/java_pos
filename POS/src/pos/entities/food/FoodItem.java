/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.food;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import pos.entities.fop.Fop;

/**
 *
 * @author fpatel
 */
@Embedded
public class FoodItem implements java.io.Serializable{

    private ObjectId id;
    private String Name;
    private Double Price;
    private Double PercentageDiscount;
    private Double DiscountValue;
    

    public FoodItem() {
    }
     
     private FoodItem(String Name, Double Price, Double PercentageDiscount, Double DiscountValue) {
        this.Name = Name;
        this.Price = Price;
        this.PercentageDiscount = PercentageDiscount;
        this.DiscountValue = DiscountValue;
    }
    
    public static FoodItem newInstance(FoodItem input){
        return (new FoodItem(input.getName(), input.getPrice(), input.getPercentageDiscount(), input.getDiscountValue()));
    } 

    
    public FoodItem(String Name) {
        this.Name = Name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double Price) {
        this.Price = Price;
    }

    public Double getPercentageDiscount() {
        return PercentageDiscount;
    }

    public void setPercentageDiscount(Double PercentageDiscount) {
        this.PercentageDiscount = PercentageDiscount;
    }

    public Double getDiscountValue() {
        return DiscountValue;
    }

    public void setDiscountValue(Double DiscountValue) {
        this.DiscountValue = DiscountValue;
    }
    
    @Override
    public String toString() {
        return getName();
    }

    public Object clone() throws CloneNotSupportedException {
        FoodItem cloned = newInstance(this);
        
        return cloned; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
