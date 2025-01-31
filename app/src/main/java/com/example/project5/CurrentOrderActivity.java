package com.example.project5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project5.pizzeria.Pizza;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the current order page
 * @author Andrew Ho, Amit Deshpande
 */
public class CurrentOrderActivity extends AppCompatActivity{
    /**
     * private array adapter for the list of pizzas
     */
    private CurrentOrderAdapter listA;
    /**
     * the hard set tax rate
     */
    private static final double TAX_RATE = 0.06625;

    /**
     * Creates the activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentorder);

        listA = new CurrentOrderAdapter(this,Singleton.getInstance().getCurrentOrder().getPizzas());
        ListView currentOrderView = findViewById(R.id.CurrentOrderView);
        currentOrderView.setAdapter(listA);

        Button cancelButton = findViewById(R.id.cancelOrder);
        cancelButton.setOnClickListener(v -> {
            Singleton.getInstance().cancelOrder();
            Toast.makeText(this, "Order Canceled", Toast.LENGTH_SHORT).show();
            finish();
        });

        Button placeOrder = findViewById(R.id.placeOrderButton);
        placeOrder.setOnClickListener(v -> {
            if(Singleton.getInstance().getCurrentOrder().total() == 0){
                Toast.makeText(this, "Add a pizza to the order", Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();
                Singleton.getInstance().placeOrder();
                finish();
            }
        });

        TextView costTextView = findViewById(R.id.orderCostView);
        ArrayList<Pizza> pizzaList = Singleton.getInstance().getCurrentOrder().getPizzas();
        double subtotal = 0.00;
        for(Pizza pizza: pizzaList){
            subtotal += pizza.price();
        }
        double tax = Math.round(subtotal * TAX_RATE * 100) / 100.0;
        double total = Singleton.getInstance().getCurrentOrder().total();
        String text = "Subtotal: $" + subtotal + System.lineSeparator() +
                "Tax: $" + tax + System.lineSeparator() +
                "Total: $" + total;
        costTextView.setText(text);
    }

    /**
     * When the activity is resumed refresh
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (listA != null) {
            listA.notifyDataSetChanged();
        }
    }

    public void updateCostTextView() {
        TextView costTextView = findViewById(R.id.orderCostView);
        ArrayList<Pizza> pizzaList = Singleton.getInstance().getCurrentOrder().getPizzas();
        double subtotal = 0.00;
        for (Pizza pizza : pizzaList) {
            subtotal += pizza.price();
        }
        double tax = Math.round(subtotal * TAX_RATE * 100) / 100.0;
        double total = Singleton.getInstance().getCurrentOrder().total();
        String text = "Subtotal: $" + subtotal + System.lineSeparator() +
                "Tax: $" + tax + System.lineSeparator() +
                "Total: $" + total;
        costTextView.setText(text);
    }
}
