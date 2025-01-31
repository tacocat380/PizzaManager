package com.example.project5;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project5.pizzeria.Order;
import com.example.project5.pizzeria.Pizza;

import java.util.ArrayList;

/**
 * Class for the history activity page
 * @author Andrew Ho, Amit Deshpande
 */
public class HistoryActivity extends AppCompatActivity {
    /**
     * Spinner for the spinner
     */
    private Spinner spinner;
    /**
     * Array adapter for the spinner
     */
    private ArrayAdapter<String> spinnerAdapter;
    /**
     * recycler view for the recycler
     */
    private RecyclerView recyclerView;
    /**
     * HistoryViewAdapter for the recycler
     */
    private HistoryViewAdapter recyclerAdapter;

    /**
     * view for the textBox for the order cost
     */
    private TextView orderCostview;

    /**
     * the hard set tax rate
     */
    private static final double TAX_RATE = 0.06625;

    /**
     * On create for the history activity page
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderhistory);

        recyclerView = findViewById(R.id.RecyclerOrderView);
        orderCostview = findViewById(R.id.orderCostView);

        spinner = findViewById(R.id.orderList);
        setSpinner();
        setRecyclerView(-1);

        String text = "Subtotal: $0.00" + System.lineSeparator() +
                "Tax: $0.00" + System.lineSeparator() +
                "Total: $0.00";
        orderCostview.setText(text);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = Integer.parseInt(parent.getItemAtPosition(position).toString());
                setRecyclerView(position);
                recyclerAdapter.notifyDataSetChanged();
                ArrayList<Pizza> pizzaList = Singleton.getInstance().getHistoryOfOrders().get(position).getPizzas();
                double subtotal = 0.00;
                for(Pizza pizza: pizzaList){
                    subtotal += pizza.price();
                }
                double tax = Math.round(subtotal * TAX_RATE * 100) / 100.0;
                double total = Singleton.getInstance().getHistoryOfOrders().get(position).total();
                String text = "Subtotal: $" + subtotal + System.lineSeparator() +
                        "Tax: $" + tax + System.lineSeparator() +
                        "Total: $" + total;
                orderCostview.setText(text);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }

    /**
     * on resume update for the page
     */
    @Override
    protected void onResume(){
        super.onResume();
        spinnerAdapter.notifyDataSetChanged();
    }

    /**
     * sets the view for the recycler
     * @param index, -1 for new array list and 0 onwards for the index of the order number
     */
    private void setRecyclerView(int index){
        if(index == -1){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerAdapter = new HistoryViewAdapter(new ArrayList<>());
            recyclerView.setAdapter(recyclerAdapter);
        }
        else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerAdapter = new HistoryViewAdapter(Singleton.getInstance().getHistoryOfOrders().get(index).getPizzas());
            recyclerView.setAdapter(recyclerAdapter);
        }
    }

    /**
     * sets the spinner values
     */
    private void setSpinner(){
        ArrayList<Order> historyData = Singleton.getInstance().getHistoryOfOrders();
        ArrayList<String> orderNumbers = new ArrayList<>();
        for(Order order: historyData){
            orderNumbers.add(order.orderNumToString());
        }
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,orderNumbers);
        spinner.setAdapter(spinnerAdapter);
    }

}
