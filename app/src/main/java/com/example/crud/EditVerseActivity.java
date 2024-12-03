package com.example.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditVerseActivity extends AppCompatActivity {

    private EditText editVerse, editBook;
    private Button btnUpdate, btnDelete;
    private String verseKey;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_verse);

        // Set up the Toolbar as the action bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        editVerse = findViewById(R.id.editVerse);
        editBook = findViewById(R.id.editBook);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // Get the passed data
        Intent intent = getIntent();
        verseKey = intent.getStringExtra("verseKey");
        String verse = intent.getStringExtra("verse");
        String book = intent.getStringExtra("book");

        // Populate EditText fields with current data
        editVerse.setText(verse);
        editBook.setText(book);

        // Update verse
        btnUpdate.setOnClickListener(view -> updateVerse());

        // Delete verse
        btnDelete.setOnClickListener(view -> deleteVerse());
    }

    private void updateVerse() {
        String updatedVerse = editVerse.getText().toString();
        String updatedBook = editBook.getText().toString();

        // Update the verse in Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("verses").child(verseKey);
        Verse updatedVerseObj = new Verse(verseKey, updatedVerse, updatedBook);
        databaseReference.setValue(updatedVerseObj)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditVerseActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        finish(); // Notify the user and go back
                    }
                });
    }

    private void deleteVerse() {
        // Delete the verse from Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("verses").child(verseKey);
        databaseReference.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditVerseActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        finish(); // Notify the user and go back
                    }
                });
    }
}
