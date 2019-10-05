/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.staff;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author fpatel
 */
@Embedded
public class Employee {
    
    public enum Rights{
    ADMIN, CASHIER
    }
    @Id
    private ObjectId id;
    private Rights rights;
    private String name;
    private Integer identification;
    private String password;
}
