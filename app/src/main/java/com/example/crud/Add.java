package com.example.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Add extends AppCompatActivity {
    EditText Text, Text3;
    Button button;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Text = findViewById(R.id.Text);
        Text3 = findViewById(R.id.Text3);
        button = findViewById(R.id.button);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verse = Text.getText().toString();
                String book = Text3.getText().toString();

                if (verse.isEmpty()) {
                    Text.setError("Cannot be empty");
                    return;
                }
                if (book.isEmpty()) {
                    Text3.setError("Cannot be empty");
                    return;
                }
                addVerseToDB(verse, book);
            }

            private void addVerseToDB(String verse, String book) {
                // Create hash map data structure -key and value pair
                // Create a HashMap to store key-value pairs
                HashMap<String, Object> verseHashmap = new HashMap<>();
                // Add the verse text to the map with the key "verse"
                verseHashmap.put("verse", verse);
                // Add the book name to the map with the key "book"
                verseHashmap.put("book", book);


                // Database connection
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference versesRef = database.getReference("verses");

                String key = versesRef.push().getKey();
                verseHashmap.put("key", key);

                versesRef.child(key).setValue(verseHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Add.this, "Added", Toast.LENGTH_SHORT).show();
                            Text.getText().clear();
                            Text3.getText().clear();
                            navigateToMainActivity(); // Navigate back to MainActivity
                        } else {
                            Toast.makeText(Add.this, "Failed to add verse", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    // Method to navigate back to MainActivity
    private void navigateToMainActivity() {
        Intent intent = new Intent(Add.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the stack and avoid multiple instances
        startActivity(intent);
        finish(); // Close the current activity (Add)
    }
}
