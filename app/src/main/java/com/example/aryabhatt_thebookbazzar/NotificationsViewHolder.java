package com.example.aryabhatt_thebookbazzar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationsViewHolder extends RecyclerView.ViewHolder {

    public TextView titles, contents;

    public NotificationsViewHolder(@NonNull View itemView) {
        super(itemView);

        titles = itemView.findViewById(R.id.titles);
        contents = itemView.findViewById(R.id.contents);

    }
}
