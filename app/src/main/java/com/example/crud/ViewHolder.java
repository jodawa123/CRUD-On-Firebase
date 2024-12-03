package com.example.crud;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView View,View2;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        View=itemView.findViewById(R.id.View);
        View2=itemView.findViewById(R.id.View2);

    }
}
