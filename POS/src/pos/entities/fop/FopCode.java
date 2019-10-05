/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.fop;

import org.mongodb.morphia.annotations.Embedded;

/**
 *
 * @author fpatel
 */

@Embedded
public class FopCode implements java.io.Serializable{

 
    public enum Type{
    CASH, CC, ACCOUNT, CHECK
    }
    Type type;
    boolean Enable = false;

    public FopCode(Type type, boolean enabled) {
        this.type = type;
        this.Enable = enabled;
    }

    public boolean isEnable() {
        return Enable;
    }

    public void setEnable(boolean Enable) {
        this.Enable = Enable;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        
        String output = "";
        switch(type){
            case CASH:
                output = "Cash";
                break;
            case CC:
                output = "Carte Bleu";
                break;
            case ACCOUNT:
                output = "Account";
                break;
            case CHECK:
                output = "Check";
                break;               
                       
        }
        return output;
    }
    
}
