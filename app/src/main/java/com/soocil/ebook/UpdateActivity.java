// UpdateActivity.java (unchanged for reference)
package com.soocil.ebook;

import android.content.Intent;
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

        Intent intent = getIntent();
        if (intent.hasExtra("id") && intent.hasExtra("title") && intent.hasExtra("author") && intent.hasExtra("pages")) {
            try {
                id = Integer.parseInt(intent.getStringExtra("id"));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid Book ID", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            title = intent.getStringExtra("title");
            author = intent.getStringExtra("author");
            try {
                pages = Integer.parseInt(intent.getStringExtra("pages"));
            } catch (NumberFormatException e) {
                pages = 0;
            }

            if (id <= 0) {
                Toast.makeText(this, "Invalid Book ID", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            titleInput.setText(title);
            authorInput.setText(author);
            pagesInput.setText(String.valueOf(pages));
        } else {
            Toast.makeText(this, "No data to update", Toast.LENGTH_SHORT).show();
            finish();
            return;
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
                if (newPages <= 0) {
                    Toast.makeText(this, "Pages must be greater than 0", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid pages", Toast.LENGTH_SHORT).show();
                return;
            }

            MyDatabaseHelper db = new MyDatabaseHelper(UpdateActivity.this);
            if (!db.doesBookExist(id)) {
                Toast.makeText(this, "Book does not exist", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            boolean success = db.updateBook(id, newTitle, newAuthor, newPages);
            if (success) {
                Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("id", String.valueOf(id));
                resultIntent.putExtra("title", newTitle);
                resultIntent.putExtra("author", newAuthor);
                resultIntent.putExtra("pages", String.valueOf(newPages));
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show();
            }
        });
    }
}