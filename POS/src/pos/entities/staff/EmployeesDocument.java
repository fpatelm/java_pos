/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.staff;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author fpatel
 */
@Entity
public class EmployeesDocument {
    @Id
    private ObjectId id;
    private List<Employee> EmployeesList = new ArrayList<>();

    public List<Employee> getEmployeesList() {
        return EmployeesList;
    }

    public void setEmployeesList(List<Employee> EmployeesList) {
        this.EmployeesList = EmployeesList;
    }
}
