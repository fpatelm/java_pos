/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.order;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.print.attribute.standard.DateTimeAtCreation;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import pos.entities.staff.Employee;

/**
 *
 * @author fpatel
 */

//Contain the list of transactions

@Entity
public class Sales implements java.io.Serializable{
    
    @Id
    private ObjectId id;
    @Embedded
    private List<Transaction> TransactionList;
    private Double TotalSales;
    private LocalDateTime Datetime;

    public List<Transaction> getTransactionList() {
        if(TransactionList == null){
            TransactionList = new ArrayList<>();
        }
        return TransactionList;
    }

    public void setTransactionList(List<Transaction> TransactionList) {
        this.TransactionList = TransactionList;
    }

    public Double getTotalSales() {
        for(Transaction transaction: getTransactionList())
        {
            
        }
        return TotalSales;
    }

    public void setTotalSales(Double TotalSales) {
        this.TotalSales = TotalSales;
    }

    public LocalDateTime getDatetime() {
        return Datetime;
    }

    public void setDatetime(LocalDateTime Datetime) {
        this.Datetime = Datetime;
    }
}
