package com.codepath.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//responsible for displaying data from the model into a row of recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    public interface OnLongClickListener {
        void OnItemLongClicked(int position);
    }

    //for edit activity
    public interface OnClickListner {
        void OnItemClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListner clickListner;
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListner clickListner) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListner = clickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        Use layout inflator to inflate a view
        View todoView = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);

//        wrap it inside a view holder and return it

        return new ViewHolder(todoView);
    }

//    Binding data to a particular ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        Grab item at the particular position
        String item = items.get(i);

//        Bind the item to the specified view holder
        viewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Container to provide easy access to view that represents each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

//      update the view inside the viewHolder with this data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListner.OnItemClicked(getAdapterPosition());
                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //remove item from recycler view
                    longClickListener.OnItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}

