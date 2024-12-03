package com.example.crud;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class verseAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<Verse> verses;
    private Context context;

    public verseAdapter(ArrayList<Verse> verses,Context context) {
        this.verses = verses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Verse verse = verses.get(position);
        holder.View.setText(verse.getVerse());
        holder.View2.setText(verse.getBook());

        // OnClickListener for the item
        holder.itemView.setOnClickListener(v -> {
            // Pass data to the EditVerseActivity
            Intent intent = new Intent(context, EditVerseActivity.class);
            intent.putExtra("verseKey", verse.getKey()); // Pass the key of the selected verse
            intent.putExtra("verse", verse.getVerse());
            intent.putExtra("book", verse.getBook());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return verses.size();
    }
}
