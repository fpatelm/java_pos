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
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author fpatel
 */
@Entity
public class Food implements java.io.Serializable{
    
    @Embedded
    private List<FoodCategory> foodCategoryList;
    @Id
    private ObjectId id;
    public Food() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    public FoodCategory getFoodCategoryByName(String Name){
       for (FoodCategory aFoodCategory: foodCategoryList){
           if(aFoodCategory.getName() == Name)
           {
               return aFoodCategory;
           }
       }
       return null;
    }
    public List<FoodCategory> getFoodCategoryList() {
        if (foodCategoryList == null)
        {
            foodCategoryList = new ArrayList<>();
        }
        return foodCategoryList;
    }
       
    public Boolean findCategory(String Name){
        for (FoodCategory cat : getFoodCategoryList()){
            if  (cat.getName() == null ? Name == null : cat.getName().equals(Name))
            {
                return true;
            }
        }
        return false;
    }
    public void setFoodCategoryList(List<FoodCategory> foodCategory) {
        this.foodCategoryList = foodCategory;
    }
    
}
