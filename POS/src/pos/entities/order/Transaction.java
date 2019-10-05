/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.print.attribute.standard.DateTimeAtCreation;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import pos.entities.fop.FP;
import pos.entities.fop.Fop;
import pos.entities.fop.FopCode;

/**
 *
 * @author fpatel
 */
//One transaction per ordercart

@Embedded
public class Transaction implements java.io.Serializable{
    private ObjectId id;
    @Embedded
    private OrderCart orderCart;
    private String Employee;
    private LocalDateTime datetime;
    private Double TotalTransaction;
    private Double Change;
    
    private String FopCode;

    private FP fp;
    
    public OrderCart getOrderCart() {
        return orderCart;
    }

    public void setOrderCart(OrderCart orderCart) {
        this.orderCart = orderCart;
    }

    public String getEmployee() {
        return Employee;
    }

    public void setEmployee(String Employee) {
        this.Employee = Employee;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Double getTotalTransaction() {
        TotalTransaction = getOrderCart().getTotal();
        return TotalTransaction;
    }    

    public FP getFp() {
        return fp;
    }

    public void setFp(FP fp) {
        this.fp = fp;
    }
    
    
    
    public String getFopCode(){
        
        for(Fop fop :  fp.getFops()){
            
        }
        return "";
    }
    
}
