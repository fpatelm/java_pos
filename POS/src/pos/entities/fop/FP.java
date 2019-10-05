/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.fop;

import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;

/**
 *
 * @author fpatel
 */
@Embedded
public class FP implements java.io.Serializable{
    private List<Fop> fops;
    
    public Double getTotal(){
        double total = 0;
        if(fops != null){
            for (Fop fop : fops){
                total = total + fop.getValue();
            }
        }
        return total;
    }
    
    public Fop getFopByCode(FopCode.Type code){
        for(Fop fop : fops){
            if(fop.getFopcode() == code){
                return fop;
            }
        }
        return null;
    }

    public List<Fop> getFops() {
        if(fops == null)
        {
            fops = new ArrayList<>();
        }
        return fops;
    } 
}
