package com.example.aryabhatt_thebookbazzar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView showname, showdesp, showprice, showdate, showtime;
    public ImageView showimage;
    public OnItemClickListener listener;



    public void setItemClickListener(OnItemClickListener Listener){

        this.listener = listener;

    }

    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        showname = itemView.findViewById(R.id.show_pro_name);
        showdesp = itemView.findViewById(R.id.show_pro_desp);
        showprice = itemView.findViewById(R.id.show_pro_price);
        showdate = itemView.findViewById(R.id.show_pro_date);
        showtime = itemView.findViewById(R.id.show_pro_time);
        showimage = itemView.findViewById(R.id.pro_image);

    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
