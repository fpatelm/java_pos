/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderScreenUtils;

import javafx.scene.control.ScrollPane;
import jpos.CashDrawerConst;
import jpos.events.StatusUpdateEvent;
import jpos.events.StatusUpdateListener;
import pos.OrderScreenController;

/**
 *
 * @author fpatel
 */
public class CashDrawerStatus implements StatusUpdateListener{

        private ScrollPane aItemsListView;

        public CashDrawerStatus(ScrollPane aItemsListView) {
            this.aItemsListView = aItemsListView;
        }

        public ScrollPane getaItemsListView() {
            return aItemsListView;
        }
        
        @Override
        public void statusUpdateOccurred(StatusUpdateEvent sue) {
            
            switch(sue.getStatus()){
                case CashDrawerConst.CASH_SUE_DRAWEROPEN:
                    OrderScreenController.PrintMessage(getaItemsListView(),"Cash Drawer Open");
                    break;
                case CashDrawerConst.CASH_SUE_DRAWERCLOSED:
                    OrderScreenController.PrintMessage(getaItemsListView(),"Cash Drawer Closed");
                    break;
                default:
                    OrderScreenController.PrintMessage(getaItemsListView(),"Unknown Status: " + Integer.toString(sue.getStatus()));
                    break;                  
            }
        }
    }
