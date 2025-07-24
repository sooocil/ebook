package com.soocil.ebook;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";

    EditText title_input, author_input, pages_input;
    Button add_button;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        myDB = new MyDatabaseHelper(AddActivity.this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(view -> {
            String title = title_input.getText().toString().trim();
            String author = author_input.getText().toString().trim();
            String pagesStr = pages_input.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || pagesStr.isEmpty()) {
                Toast.makeText(AddActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int pages = Integer.parseInt(pagesStr);

                boolean success = myDB.addBook(title, author, pages);
 
                if (success) {
                    Toast.makeText(AddActivity.this, "Book added successfully!", Toast.LENGTH_SHORT).show();
                    title_input.setText("");
                    author_input.setText("");
                    pages_input.setText("");
                } else {
                    Toast.makeText(AddActivity.this, "Failed to add book. Please try again.", Toast.LENGTH_SHORT).show();
                }

            } catch (NumberFormatException e) {
                Toast.makeText(AddActivity.this, "Please enter a valid number for pages", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Invalid number format for pages: " + pagesStr, e);
            } catch (Exception e) {
                Toast.makeText(AddActivity.this, "An unexpected error occurred", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Unexpected error during book addition", e);
            }
        });
    }
}
