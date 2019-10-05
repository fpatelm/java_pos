/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos;

import com.mongodb.MongoClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import pos.entities.food.Food;
import pos.entities.food.FoodCategory;
import pos.entities.staff.Employee;

/**
 *
 * @author fpatel
 */
public class FXMLDocumentController implements Initializable {
    
    Morphia morphia;
    Datastore datastore;
    Query<Employee> employeesQuery;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //morphia.map(Employees.class);
        //datastore = morphia.createDatastore(new MongoClient(), "test");
        //datastore.ensureIndexes();
        //employeesQuery = datastore.createQuery(Employees.class);
/*        if( !employeesQuery.asList().isEmpty() )
        {
            
        }*/
    }    
    
}
