package com.example.project5;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Class for the main activity page
 * @author Andrew Ho, Amit Deshpande
 */
public class MainActivity extends AppCompatActivity {

    /**
     * On create method for the main page
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // order pizza activity
        Button order_button = findViewById(R.id.OrderPageButton);
        order_button.setOnClickListener(v -> {
            Intent order_intent = new Intent(MainActivity.this, OrderPageActivity.class);
            startActivity(order_intent);
        });

        // order history activity
        Button history_button = findViewById(R.id.OrderHistoryButton);
        history_button.setOnClickListener(v -> {
            Intent history_intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(history_intent);
        });
    }
}