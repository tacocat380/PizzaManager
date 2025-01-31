package com.example.project5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.project5.pizzeria.Pizza;
import java.util.ArrayList;
import java.util.List;

public class CurrentOrderAdapter extends BaseAdapter {

    private static final double TAX_RATE = 0.06625;
    private final Context context;
    private final List<Pizza> itemList;

    public CurrentOrderAdapter(Context context, List<Pizza> items) {
        super();
        this.context = context;
        this.itemList = items;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_order, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.pizza_order_view);
        Button button = convertView.findViewById(R.id.cancel_pizza);

        Pizza pizzas = itemList.get(position);
        textView.setText(pizzas.toString());

        button.setOnClickListener(v -> {
            Singleton.getInstance().remove_Pizza(position);
            if (context instanceof CurrentOrderActivity) {
                ((CurrentOrderActivity) context).runOnUiThread(() -> ((CurrentOrderActivity) context).updateCostTextView());
            }
            notifyDataSetChanged();
        });
        return convertView;
    }
}