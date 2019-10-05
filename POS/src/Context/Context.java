/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Context;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import jpos.CashDrawer;
import pos.entities.food.Food;
import pos.entities.fop.FP;
import pos.entities.fop.Fop;
import pos.entities.fop.FopTable;
import pos.entities.order.OrderCart;
import pos.entities.order.OrderItem;
import pos.entities.order.Sales;
import pos.entities.order.Transaction;

/**
 *
 * @author fpatel
 */
public class Context {
    private Food aFood;
    private ObservableList<OrderItem> OrderItemObsList;
    private ObservableList<Fop> FormOfPaymentObsList;
    private OrderCart orderCartList;
    private Transaction transaction;
    private Sales sales; // one per session
    private FopTable mep;
    private List<Fop> ListOfFops;
    //Devices
    private CashDrawer cashDrawer;
    private List<Label> labelList;
    private FP fp;
    @FXML
    private ScrollPane CategoryListView;
    @FXML
    private ScrollPane ItemsListView;

    public Context(Food aFood, ObservableList<OrderItem> OrderItemObsList, ObservableList<Fop> FormOfPaymentObsList, OrderCart orderCartList, Transaction transaction, Sales sales, FopTable mep, List<Fop> ListOfFops, CashDrawer cashDrawer, List<Label> labelList, ScrollPane CategoryListView, ScrollPane ItemsListView, FP fp) {
        this.aFood = aFood;
        this.OrderItemObsList = OrderItemObsList;
        this.FormOfPaymentObsList = FormOfPaymentObsList;
        this.orderCartList = orderCartList;
        this.transaction = transaction;
        this.sales = sales;
        this.mep = mep;
        this.ListOfFops = ListOfFops;
        this.cashDrawer = cashDrawer;
        this.labelList = labelList;
        this.CategoryListView = CategoryListView;
        this.ItemsListView = ItemsListView;
        this.fp = fp;
    }

    public Food getFood() {
        return aFood;
    }

    public void setFood(Food aFood) {
        this.aFood = aFood;
    }

    public ObservableList<OrderItem> getOrderItemObsList() {
        return OrderItemObsList;
    }

    public void setOrderItemObsList(ObservableList<OrderItem> OrderItemObsList) {
        this.OrderItemObsList = OrderItemObsList;
    }

    public ObservableList<Fop> getFormOfPaymentObsList() {
        return FormOfPaymentObsList;
    }

    public void setFormOfPaymentObsList(ObservableList<Fop> FormOfPaymentObsList) {
        this.FormOfPaymentObsList = FormOfPaymentObsList;
    }

    public OrderCart getOrderCartList() {
        return orderCartList;
    }

    public void setOrderCartList(OrderCart orderCartList) {
        this.orderCartList = orderCartList;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public FopTable getMep() {
        return mep;
    }

    public void setMep(FopTable mep) {
        this.mep = mep;
    }

    public List<Fop> getListOfFops() {
        return ListOfFops;
    }

    public void setListOfFops(List<Fop> ListOfFops) {
        this.ListOfFops = ListOfFops;
    }

    public CashDrawer getCashDrawer() {
        return cashDrawer;
    }

    public void setCashDrawer(CashDrawer cashDrawer) {
        this.cashDrawer = cashDrawer;
    }

    public List<Label> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<Label> labelList) {
        this.labelList = labelList;
    }

    public ScrollPane getCategoryListView() {
        return CategoryListView;
    }

    public void setCategoryListView(ScrollPane CategoryListView) {
        this.CategoryListView = CategoryListView;
    }

    public ScrollPane getItemsListView() {
        return ItemsListView;
    }

    public void setItemsListView(ScrollPane ItemsListView) {
        this.ItemsListView = ItemsListView;
    }

    public FP getFp() {
        return fp;
    }

    public void setFp(FP fp) {
        this.fp = fp;
    }
    
}
