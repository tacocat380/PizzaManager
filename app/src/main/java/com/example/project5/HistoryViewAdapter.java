package com.example.project5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project5.pizzeria.Pizza;
import java.util.ArrayList;

/**
 * Class for history view adapter
 * @author Andrew Ho, Amit Deshpande
 */
public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryViewAdapter.ViewHolder> {

    /**
     * private array list of pizzas for the order looked at currently
     */
    private ArrayList<Pizza> current;

    /**
     * View holder class to create the view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * texte view of the text box
         */
        private final TextView textView;

        /**
         * the view holder method for construction
         * @param view - view to be used
         */

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.orderWindow);
        }

        /**
         * gets the text view being used
         * @return textView used
         */
        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * adaptor constructor
     * @param current - current list of pizzas to be used
     */
    public HistoryViewAdapter(ArrayList<Pizza> current){
        this.current = current;
    }

    /**
     * Creator for the view
     * @param viewGroup The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return returns a new viewHolder with the view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_order,viewGroup,false);
        return new ViewHolder(view);
    }

    /**
     * Binder method to put the String text into the text box
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String message = current.get(position).toString();
        viewHolder.getTextView().setText(message);

    }

    /**
     * Getter for the number of items in the list of pizzas
     * @return int, number of pizzas to be displayed
     */
    @Override
    public int getItemCount() {
        return current.size();
    }
}
