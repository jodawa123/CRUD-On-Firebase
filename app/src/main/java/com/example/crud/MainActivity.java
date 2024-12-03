package com.example.crud;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    RecyclerView rv;
    private ArrayList<Verse> verseList;
    private verseAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply system insets for edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        // Initialize views
        floatingActionButton = findViewById(R.id.floatingActionButton);
        rv = findViewById(R.id.rv);
       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        // Initialize verse list and adapter
        verseList = new ArrayList<>();
        adapter = new verseAdapter(verseList, MainActivity.this); // Pass the context properly
        rv.setAdapter(adapter);

        // Fetch data from Firebase
        fetchDataFromFirebase();

        // Floating action button click listener to open AddActivity
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Add.class);
            startActivity(intent);
        });
    }

    // Fetch verses data from Firebase
    private void fetchDataFromFirebase() {
        // Database reference to fetch verses
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("verses");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                verseList.clear(); // Clear the list to prevent duplication

                // Loop through each verse and add it to the list
                for (DataSnapshot data : snapshot.getChildren()) {
                    Verse verse = data.getValue(Verse.class);
                    if (verse != null) {
                        verse.setKey(data.getKey()); // Ensure each verse has a unique key
                        verseList.add(verse);
                    }
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
                Log.d("FirebaseData", "Data fetched: " + verseList.size() + " items");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error fetching data: " + error.getMessage());
            }
        });
    }
}
