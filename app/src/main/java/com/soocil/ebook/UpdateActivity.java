package com.soocil.ebook;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class UpdateActivity extends AppCompatActivity {

    EditText titleInput, authorInput, pagesInput;
    Button updateButton;

    int id;
    String title, author;
    int pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        titleInput = findViewById(R.id.title_input);
        authorInput = findViewById(R.id.author_input);
        pagesInput = findViewById(R.id.pages_input);
        updateButton = findViewById(R.id.update_button);

        if (getIntent().hasExtra("id") && getIntent().hasExtra("title")
                && getIntent().hasExtra("author") && getIntent().hasExtra("pages")) {

            id = getIntent().getIntExtra("id", -1);
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getIntExtra("pages", 0);

            titleInput.setText(title);
            authorInput.setText(author);
            pagesInput.setText(String.valueOf(pages));
        } else {
            Toast.makeText(this, "No data to update", Toast.LENGTH_SHORT).show();
            finish();
        }

        updateButton.setOnClickListener(v -> {
            String newTitle = titleInput.getText().toString().trim();
            String newAuthor = authorInput.getText().toString().trim();
            String pagesStr = pagesInput.getText().toString().trim();

            if (newTitle.isEmpty() || newAuthor.isEmpty() || pagesStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int newPages;
            try {
                newPages = Integer.parseInt(pagesStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid pages", Toast.LENGTH_SHORT).show();
                return;
            }

            MyDatabaseHelper db = new MyDatabaseHelper(UpdateActivity.this);
            boolean success = db.updateBook(id, newTitle, newAuthor, newPages);

            if (success) {
                Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
