package com.example.project5;

import com.example.project5.pizzeria.Order;
import java.util.ArrayList;

/**
 * Class for Singleton
 * @author Andrew Ho, Amit Deshpande
 */
public class Singleton {

    /**
     * the only instance of a singleton
     */
    private static final Singleton overheadData = new Singleton();

    /**
     * current order being used by the user
     */
    private Order currentOrder;

    /**
     * history of previous orders
     */
    private final ArrayList<Order> historyOfOrders;

    /**
     * getter for the Singleton
     * @return the single instance of the Singleton
     */
    public static Singleton getInstance(){return overheadData;}

    /**
     * the constructor for the singleton
     */
    private Singleton(){
        currentOrder = new Order();
        historyOfOrders = new ArrayList<>();
    }

    /**
     * place the order from the current order into the history
     */
    public void placeOrder(){
        historyOfOrders.add(currentOrder);
        currentOrder = new Order();
    }

    /**
     * getter for the current order at work
     * @return instance of order being used
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * cancels the order being worked on
     */
    public void cancelOrder() {
        currentOrder = new Order();
    }

    /**
     * getter for the history of orders
     * @return Get the array list of orders
     */
    public ArrayList<Order> getHistoryOfOrders() {
        return historyOfOrders;
    }

    /**
     * Removes the pizza at the passed index from the current order
     * @param index - index of the pizza to be removed
     */
    public void remove_Pizza(int index){currentOrder.getPizzas().remove(index);}
}
