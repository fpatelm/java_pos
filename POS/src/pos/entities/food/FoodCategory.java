/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.food;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author fpatel
 */
@Embedded
public class FoodCategory implements java.io.Serializable{

    private String Name;
    
    @Embedded
    private List<FoodItem> foodItemList;
    
    private ObjectId id;
    
    public FoodCategory() {
    }

    public FoodCategory(String Name) {
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

    public List<FoodItem> getFoodItemList() {
        if(foodItemList == null)
        {
            foodItemList = new ArrayList<>();
        }
        return foodItemList;
    }

    public void setFoodItemList(List<FoodItem> foodItem) {
        this.foodItemList = foodItem;
    }
    
    public void setFoodItem(FoodItem item){

        getFoodItemList().add(item);
    
    }
    
    public FoodItem getFoodItemById(ObjectId id){
        for (FoodItem Item: getFoodItemList()){
            if( Item.getId() == id ){
            return Item;
            }
        }
        return null;
    }
    
    public FoodItem getFoodByName(String Name){
        for (FoodItem Item: getFoodItemList()){
            if(Item.getName() == Name){
                System.out.println("item found");
                return Item;
            }
        }
         System.out.println("item not found, returning null");
        return null;
    }

    @Override
    public String toString() {
        return getName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
}
