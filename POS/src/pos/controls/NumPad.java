/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.controls;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author fpatel
 */
public class NumPad extends Object{

    TextField textField = new TextField();
    VBox vbox = new VBox();
    GridPane gridPane = new GridPane();

    /**
     * NumPad Control
     */
    public NumPad() {
        
        textField.setFont(new Font(20));
        vbox.getChildren().add(textField);
        gridPane.add(new NumButton("7", textField, Boolean.FALSE), 1, 1);
        gridPane.add(new NumButton("4", textField, Boolean.FALSE), 1, 2);
        gridPane.add(new NumButton("1", textField, Boolean.FALSE), 1, 3);
        gridPane.add(new NumButton("0", textField, Boolean.FALSE), 1, 4);
        gridPane.add(new NumButton("8", textField, Boolean.FALSE), 2, 1);
        gridPane.add(new NumButton("5", textField, Boolean.FALSE), 2, 2);
        gridPane.add(new NumButton("2", textField, Boolean.FALSE), 2, 3);
        gridPane.add(new NumButton(".", textField, Boolean.FALSE), 2, 4);
        gridPane.add(new NumButton("9", textField, Boolean.FALSE), 3, 1);
        gridPane.add(new NumButton("6", textField, Boolean.FALSE), 3, 2);
        gridPane.add(new NumButton("3", textField, Boolean.FALSE), 3, 3);
        gridPane.add(new NumButton("<<", textField, Boolean.TRUE), 3, 4);
        vbox.getChildren().add(gridPane);
    }
    
    public String getDisplay(){
        return this.textField.getText();
    }

    public VBox getInstance(){
        return vbox;
    }
    
    public Double getValue(){
        return Double.parseDouble(this.textField.getText());
    }
   
    public void reset(){
        this.textField.clear();
    }
    
    
}
