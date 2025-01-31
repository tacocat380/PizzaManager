package com.example.project5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.app.AlertDialog;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project5.pizzeria.NYPizza;
import com.example.project5.pizzeria.ChicagoPizza;
import com.example.project5.pizzeria.Pizza;
import com.example.project5.pizzeria.PizzaFactory;
import com.example.project5.pizzeria.Size;
import com.example.project5.pizzeria.Topping;
import java.util.ArrayList;

/**
 * Class for the order page
 * @author Andrew Ho, Amit Deshpande
 */
public class OrderPageActivity extends AppCompatActivity{

    private static final int MAX_TOPPING = 7;
    /**
     * On create method for the page
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpage);

        CheckBox sausage = findViewById(R.id.sausage);
        CheckBox pepperoni = findViewById(R.id.pepperoni);
        CheckBox ham = findViewById(R.id.ham);
        CheckBox bbq_chicken = findViewById(R.id.bbq_chicken);
        CheckBox buffalo_chicken = findViewById(R.id.buffalo_chicken);
        CheckBox green_pepper = findViewById(R.id.green_pepper);
        CheckBox onion = findViewById(R.id.onion);
        CheckBox pineapple = findViewById(R.id.pineapple);
        CheckBox black_olive = findViewById(R.id.black_olive);
        CheckBox mushroom = findViewById(R.id.mushroom);
        CheckBox jalapeno = findViewById(R.id.jalapeno);
        CheckBox basil = findViewById(R.id.basil);

        CheckBox[] toppingList = {sausage,pepperoni,ham,bbq_chicken,buffalo_chicken,
            green_pepper,onion,pineapple,black_olive,mushroom,jalapeno,basil};

        RadioGroup pizza_style = findViewById(R.id.pizza_style);
        RadioGroup pizza_size = findViewById(R.id.pizza_size);
        RadioGroup pizza_type = findViewById(R.id.pizza_type);

        Button addPizzaButton = findViewById(R.id.place_pizza_button);
        Button view_current_order = findViewById(R.id.viewOrderButton);

        pizza_type.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.BYO_pizza) {
                // show toppings
                for (CheckBox button: toppingList){
                    button.setVisibility(View.VISIBLE);
                }
            } else {
                // hide toppings
                for (CheckBox button : toppingList) {
                    button.setVisibility(View.GONE);
                }
            }
            updatePizzaCost(toppingList,pizza_style,pizza_type,pizza_size);
        });

        pizza_size.setOnCheckedChangeListener((group, checkedId) -> {
                    updatePizzaCost(toppingList,pizza_style,pizza_type,pizza_size);
        });

        // add pizza to cart - toast
        addPizzaButton.setOnClickListener(v -> {
            Pizza pizza = getPizza(toppingList,pizza_style,pizza_type,pizza_size);

            Singleton.getInstance().getCurrentOrder().addPizza(pizza);
            Toast toast = Toast.makeText(OrderPageActivity.this,"Adding Pizza", Toast.LENGTH_SHORT);
            toast.show();
            updatePizzaCost(toppingList,pizza_style,pizza_type,pizza_size);
        });


        // view order - list view
        view_current_order.setOnClickListener(v -> {
            Intent history_intent = new Intent(OrderPageActivity.this, CurrentOrderActivity.class);
            startActivity(history_intent);
        });

        // check max 7 toppings - alert dialog
       sausage.setOnClickListener(v -> maxToppingChecker(toppingList,sausage,pizza_style,pizza_type,pizza_size));
       pepperoni.setOnClickListener(v -> maxToppingChecker(toppingList,pepperoni,pizza_style,pizza_type,pizza_size));
       ham.setOnClickListener(v -> maxToppingChecker(toppingList,ham,pizza_style,pizza_type,pizza_size));
       bbq_chicken.setOnClickListener(v -> maxToppingChecker(toppingList,bbq_chicken,pizza_style,pizza_type,pizza_size));
       buffalo_chicken.setOnClickListener(v -> maxToppingChecker(toppingList,buffalo_chicken,pizza_style,pizza_type,pizza_size));
       green_pepper.setOnClickListener(v -> maxToppingChecker(toppingList,green_pepper,pizza_style,pizza_type,pizza_size));
       onion.setOnClickListener(v -> maxToppingChecker(toppingList,onion,pizza_style,pizza_type,pizza_size));
       pineapple.setOnClickListener(v -> maxToppingChecker(toppingList,pineapple,pizza_style,pizza_type,pizza_size));
       black_olive.setOnClickListener(v -> maxToppingChecker(toppingList,black_olive,pizza_style,pizza_type,pizza_size));
       mushroom.setOnClickListener(v -> maxToppingChecker(toppingList,mushroom,pizza_style,pizza_type,pizza_size));
       jalapeno.setOnClickListener(v -> maxToppingChecker(toppingList,jalapeno,pizza_style,pizza_type,pizza_size));
       basil.setOnClickListener(v -> maxToppingChecker(toppingList,basil,pizza_style,pizza_type,pizza_size));
    }

    /**
     * Updates the view cost of the pizza being created
     * @param toppingList - list of topping Checkboxes to be checked
     * @param pizza_style - pizza style radio group
     * @param pizza_type - pizza type radio group
     * @param pizza_size - pizza size radio group
     */
    private void updatePizzaCost(CheckBox[] toppingList,RadioGroup pizza_style,RadioGroup pizza_type, RadioGroup pizza_size){
        TextView price = findViewById(R.id.price_box);
        Pizza pizza = getPizza(toppingList,pizza_style,pizza_type,pizza_size);
        price.setText("Subtotal:\n$" + pizza.price());
    }

    /**
     * Gets the pizza found on the display
     * @param toppingList - list of Checkboxes to be checked to toppings
     * @param pizza_style - Radio group for the style
     * @param pizza_type - Radio group for the type
     * @param pizza_size - Radio group for the size
     * @return instance of the pizza created
     */
    private Pizza getPizza(CheckBox[] toppingList,RadioGroup pizza_style,RadioGroup pizza_type, RadioGroup pizza_size){
        String string_style = ((RadioButton)findViewById(pizza_style.getCheckedRadioButtonId())).getText().toString();
        String string_type = ((RadioButton)findViewById(pizza_type.getCheckedRadioButtonId())).getText().toString();
        String string_size = ((RadioButton)findViewById(pizza_size.getCheckedRadioButtonId())).getText().toString();
        PizzaFactory ChicagoPizzaFactory = new ChicagoPizza();
        PizzaFactory NewYorkPizzaFactory = new NYPizza();
        Pizza pizza = ChicagoPizzaFactory.createDeluxe();

        switch(string_style) {
            case ("Chicago"): // Chicago
                switch (string_type) {
                    case ("Deluxe Pizza"):
                        pizza = ChicagoPizzaFactory.createDeluxe();
                        break;
                    case ("Meatzza Pizza"):
                        pizza = ChicagoPizzaFactory.createMeatzza();
                        break;
                    case ("BBQ Chicken Pizza"):
                        pizza = ChicagoPizzaFactory.createBBQChicken();
                        break;
                    case("Build Your Own"):
                        pizza = ChicagoPizzaFactory.createBuildYourOwn();
                        pizza.setToppings(getToppings(toppingList));
                        break;
                }
                break;
            case ("New York"): // NY
                switch (string_type) {
                    case ("Deluxe Pizza"):
                        pizza = NewYorkPizzaFactory.createDeluxe();
                        break;
                    case ("Meatzza Pizza"):
                        pizza = NewYorkPizzaFactory.createMeatzza();
                        break;
                    case ("BBQ Chicken Pizza"):
                        pizza = NewYorkPizzaFactory.createBBQChicken();
                        break;
                    case("Build Your Own"):
                        pizza = NewYorkPizzaFactory.createBuildYourOwn();
                        pizza.setToppings(getToppings(toppingList));
                        break;
                }
                break;
        }

        switch(string_size){
            case("Small"):
                pizza.setSize(Size.SMALL);
                break;
            case("Medium"):
                pizza.setSize(Size.MEDIUM);
                break;
            case("Large"):
                pizza.setSize(Size.LARGE);
                break;
        }
        return pizza;
    }

    /**
     * Checks the list of Checkboxes for the toppings choosen
     * @param toppingList - list of Checkboxes to be checked
     * @return ArrayList of toppings as their ENUM
     */
    private ArrayList<Topping> getToppings(CheckBox[] toppingList){
        ArrayList<Topping> toppings = new ArrayList<>();
        for(CheckBox box:toppingList){
            if(box.isChecked()){
                switch(getResources().getResourceEntryName(box.getId())){
                    case("sausage"):
                        toppings.add(Topping.SAUSAGE);
                        break;
                    case("pepperoni"):
                        toppings.add(Topping.PEPPERONI);
                        break;
                    case("ham"):
                        toppings.add(Topping.HAM);
                        break;
                    case("bbq_chicken"):
                        toppings.add(Topping.BBQ_CHICKEN);
                        break;
                    case("buffalo_chicken"):
                        toppings.add(Topping.BUFFALO_CHICKEN);
                        break;
                    case("green_pepper"):
                        toppings.add(Topping.GREEN_PEPPER);
                        break;
                    case("onion"):
                        toppings.add(Topping.ONION);
                        break;
                    case("pineapple"):
                        toppings.add(Topping.PINEAPPLE);
                        break;
                    case("black_olive"):
                        toppings.add(Topping.BLACK_OLIVE);
                        break;
                    case("mushroom"):
                        toppings.add(Topping.MUSHROOM);
                        break;
                    case("jalapeno"):
                        toppings.add(Topping.JALAPENO);
                        break;
                    case("basil"):
                        toppings.add(Topping.BASIL);
                        break;
                }
            }
        }

        return toppings;
    }

    /**
     * Checks the warns the user if they choose too many toppings
     * @param toppingList - list of topping Checkboxes to check
     * @param current - current topping called upon, checked back to false if the number of checked is greater than 7
     * @param pizza_style - Radio group of the style
     * @param pizza_type - Radio group of the type
     * @param pizza_size - radio group of the size
     */
    private void maxToppingChecker(CheckBox[] toppingList, CheckBox current,RadioGroup pizza_style,RadioGroup pizza_type, RadioGroup pizza_size){
        ArrayList<Topping> topping = getToppings(toppingList);
        if(topping.size() >= (MAX_TOPPING+1)) {
            current.setChecked(false);
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("You can only have up to 7 toppings")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        updatePizzaCost(toppingList,pizza_style,pizza_type,pizza_size);
    }
}


