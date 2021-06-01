/**
 * Klasse zum Verwalten des RecyclerViews bei den Prioritäten.
 * Übernommen und angepasst
 * Quelle: Übungsbeispiel
 *
 * @author: Philipp Obst (18307)
 */
package com.example.aufgabe2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterPriority extends RecyclerView.Adapter<MyAdapterPriority.ViewHolder>{

    private List<Priority> values;
    private MyAdapterPriority.OnPriorityItemClickListener priorityListener;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.tv_zeile);

            v.setOnClickListener(v1 -> {
                int pos = getAdapterPosition();
                priorityListener.onPriorityClick(values.get(pos));
            });

        }
    }

    public void add(int position, Priority item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterPriority(List<Priority> myDataset) {
        values = myDataset;
    }




    @NonNull
    @Override
    public MyAdapterPriority.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.zeile, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyAdapterPriority.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position).getPriorityTitel();
        holder.txtHeader.setText(name);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }


    public interface OnPriorityItemClickListener {
        public void onPriorityClick(Priority priority);

    }

    public void setOnItemClickListener(MyAdapterPriority.OnPriorityItemClickListener onPriorityItemClickListener){
        this.priorityListener = onPriorityItemClickListener;
    }

}
