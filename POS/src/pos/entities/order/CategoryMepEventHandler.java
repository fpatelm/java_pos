/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.entities.order;

import Context.Context;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import static pos.OrderScreenController.PrintMessage;
import pos.controls.NumPad;
import pos.entities.fop.Fop;
import pos.entities.fop.FopCode;
import static pos.OrderScreenController.updateAmountLabels;

/**
 *
 * @author fpatel
 */
public class CategoryMepEventHandler implements EventHandler<ActionEvent>{

        private FopCode fop;
        private ScrollPane aItemsListView; 
        private OrderCart orderCartList;
        private ObservableList<Fop> FormOfPaymentObsList;
        private List<Fop> ListOfFops;
        private List<Label> labelList;
        private Context context;

        public CategoryMepEventHandler(Context context,FopCode fop) {
            this.context = context;
            this.fop = fop;
        }
        
        public CategoryMepEventHandler(FopCode fop, ScrollPane aItemsListView, OrderCart orderCartList, ObservableList<Fop> FormOfPaymentObsList, List<Fop> ListOfFops, List<Label> labelList) {
            this.fop = fop;
            this.aItemsListView = aItemsListView;
            this.orderCartList = orderCartList;
            this.FormOfPaymentObsList = FormOfPaymentObsList;
            this.ListOfFops = ListOfFops;
            this.labelList = labelList;
        }
       
        public ScrollPane getItemsMainScreen() {
            return context.getItemsListView();
        }

      
        @Override
        public void handle(ActionEvent event) {
            switch(fop.getType()){
                case CASH:
                    
                    if(context.getFp().getFops()!= null){
                        if(context.getFp().getFopByCode(FopCode.Type.CASH) != null)
                        {
                            PrintMessage(getItemsMainScreen(),"CASH already added!");
                            break;
                        }
                    }
                    //Show Num Pad in ItemsListView
                    VBox vbox = new VBox();
                    NumPad numpad = new NumPad();
                    vbox.getChildren().add(numpad.getInstance());
                    HBox hbox = new HBox();
                    Button okBtn = new Button("Enter");
                    okBtn.setPrefWidth(180);
                    okBtn.setPrefHeight(90);
                    okBtn.setFont(new Font(25));
                    okBtn.setAlignment(Pos.CENTER);
                    okBtn.wrapTextProperty().setValue(true);        
                    okBtn.setContentDisplay(ContentDisplay.CENTER);
                    okBtn.setTextAlignment(TextAlignment.CENTER);
                    okBtn.setOnAction(new CashEventHandler(Boolean.TRUE, numpad));
                    
              
                    Button cancelBtn = new Button("X");
                    cancelBtn.setPrefWidth(90);
                    cancelBtn.setPrefHeight(90);
                    cancelBtn.setFont(new Font(25));
                    cancelBtn.setAlignment(Pos.CENTER);
                    cancelBtn.wrapTextProperty().setValue(true);        
                    cancelBtn.setContentDisplay(ContentDisplay.CENTER);
                    cancelBtn.setTextAlignment(TextAlignment.CENTER);
                    cancelBtn.setOnAction(new CashEventHandler(Boolean.FALSE, null));
                    
                    hbox.getChildren().add(okBtn);
                    hbox.getChildren().add(cancelBtn);
                    vbox.getChildren().add(hbox);
                    getItemsMainScreen().setContent(vbox);
                  break;
                case CC:
                    //Open Connection to EMV Device
                    PrintMessage(getItemsMainScreen(),"Please insert Card!");
                    break;
                case ACCOUNT:
                    // Not defined
                    break;
                case CHECK:
                    // Show Num Pad to enter amount of the check
                    break;
                    
            }
        }
        
        private class CashEventHandler implements EventHandler<ActionEvent>{
            Boolean add;
            NumPad numPad;
            public CashEventHandler(Boolean add, NumPad numpad) {
                this.add = add;
                this.numPad = numpad;
            }
            @Override
            public void handle(ActionEvent event) {
                if(this.add)
                {
                    //add cash form of payment
                    //create an fop  of amount numPad
                    Fop fop = new Fop(FopCode.Type.CASH, numPad.getValue());
                    
                    context.getFp().getFops().add(fop);
                    //context.getListOfFops().add(fop);
                    context.getFormOfPaymentObsList().add(fop);
                    
                    getItemsMainScreen().setContent(null);
                  
                    /*
                    Double TotalDue, FOP;
                    TotalDue = context.getOrderCartList().getTotalDue();
                    FOP = context.getFp().getTotal();
                    
                   
                    if (FOP >= TotalDue){
                        //Change to be given
                        context.getOrderCartList().setChange(new Double(FOP.doubleValue()-TotalDue.doubleValue()));
                        context.getOrderCartList().setTotalDue(new Double(0));
                   }
                    else if(FOP < TotalDue){
                        // More FOPs are needed
                        //Compute new TotalDue
                        Double newTotalDue = new Double(TotalDue.doubleValue() - FOP.doubleValue());
                        context.getOrderCartList().setTotalDue(new Double(newTotalDue));
                    }*/
                    updateAmountLabels(context);
                }
                return;
            }
        }
    
    }
