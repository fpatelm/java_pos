/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pos.OrderScreenController;

/**
 *
 * @author fpatel
 */
public class NumButton extends Button{
    private String display;
    private TextField textfield;
    private Boolean isBackspace;
    final private int buttonHeigh = 90;
    final private int buttonWidth = 90; 
    
    public NumButton(String display, TextField inputTextField, Boolean isBackspace) {
        this.display = display;
        this.textfield =inputTextField;
        this.isBackspace = isBackspace;
        this.setText(display);
        this.setPrefWidth(buttonWidth);
        this.setPrefHeight(buttonHeigh);
        this.setFont(new Font(25));
        this.setAlignment(Pos.CENTER);
        this.wrapTextProperty().setValue(true);        
        this.setContentDisplay(ContentDisplay.CENTER);
        this.setTextAlignment(TextAlignment.CENTER);
        this.setOnAction(new NumKeyEventHandler(this.display,this.textfield, this.isBackspace));
        if(this.display =="")
              this.setDisable(true);
    }
    
    
    private class NumKeyEventHandler implements EventHandler<ActionEvent>{

        String input;
        TextField textfield;
        private Boolean isBackspace;
        public NumKeyEventHandler(String input, TextField inputTextField, Boolean isBackspace) {
            this.input = input;
            this.textfield = inputTextField;
            this.isBackspace = isBackspace;
        }

        @Override
        public void handle(ActionEvent event) {
            if(this.isBackspace)
            {
                textfield.setText(removeLastChar(this.textfield.getText()));
                return;
            }
            this.textfield.appendText(input);
            System.out.println("Clicked: " + input );
        }
        
        private String removeLastChar(String str) {
            if(str.length() != 0 && str != null)
            {
                return str.substring(0,str.length()-1);
            }
            return str;
        }
    }
    
    
}
