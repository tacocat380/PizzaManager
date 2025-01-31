package com.example.project5.pizzeria;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for Deluxe pizza that extends Pizza
 * @author Andrew Ho, Amit Deshpande
 */
public class Deluxe extends Pizza{

    /**
     * Constructor for Deluxe
     * @param crust the crust of the pizza
     */
    public Deluxe(Crust crust){
        super(crust);
        this.setToppings(new ArrayList<>(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM)));
    }

    /**
     * Getter for the price of the pizza based on size
     * @return double, price of the pizza
     */
    @Override
    public double price(){
        return switch (this.getSize()) {
            case SMALL -> 16.99;
            case MEDIUM -> 18.99;
            case LARGE -> 20.99;
        };
    }

    /**
     * Getter for the string of the specialty
     * @return String "Deluxe"
     */
    @Override
    public String getSpecialty(){ return "Deluxe"; }

}
