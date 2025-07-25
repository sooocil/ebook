package com.soocil.ebook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
import android.widget.Toast;
=======
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
>>>>>>> 6a0a8e8 (Feat : Add Books, Update, Delete and Login Logic)

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

<<<<<<< HEAD
import java.util.ArrayList;
=======
import java.util.List;
>>>>>>> 6a0a8e8 (Feat : Add Books, Update, Delete and Login Logic)

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
<<<<<<< HEAD
    CustomAdapter customAdapter;
    MyDatabaseHelper mydb;

    ArrayList<String> book_id, book_title, book_author, book_pages;
=======
    TextView emptyText;
    ImageView emptyImage;
    BookAdapter adapter;
    List<BookModel> bookList;
    MyDatabaseHelper dbHelper;
>>>>>>> 6a0a8e8 (Feat : Add Books, Update, Delete and Login Logic)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

<<<<<<< HEAD
        try {
            setContentView(R.layout.activity_main);  // XML must have recyclerView and add_button

            recyclerView = findViewById(R.id.recyclerView);
            add_button = findViewById(R.id.add_button);

            if (recyclerView == null || add_button == null) {
                Toast.makeText(this, "⚠️ View not found in layout!", Toast.LENGTH_LONG).show();
                Log.e("MainActivity", "Missing view ID(s) in activity_main.xml");
                return;
            }

            add_button.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            });

            // Init DB and arrays
            mydb = new MyDatabaseHelper(MainActivity.this);
            book_id = new ArrayList<>();
            book_title = new ArrayList<>();
            book_author = new ArrayList<>();
            book_pages = new ArrayList<>();

            // Fetch data safely
            storeDataInArrays();

            // Setup adapter
            customAdapter = new CustomAdapter(MainActivity.this, book_id, book_title, book_author, book_pages);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        } catch (Exception e) {
            Toast.makeText(this, "❌ Crash in MainActivity: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Exception in onCreate", e);
        }
    }

    void storeDataInArrays() {
        try {
            Cursor cursor = mydb.readAllData();

            if (cursor == null) {
                Toast.makeText(this, "Database returned null cursor.", Toast.LENGTH_LONG).show();
                return;
            }

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "📭 No data found.", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    book_id.add(cursor.getString(0));
                    book_title.add(cursor.getString(1));
                    book_author.add(cursor.getString(2));
                    book_pages.add(cursor.getString(3));
                }
            }
            cursor.close(); // Always close cursor
        } catch (Exception e) {
            Toast.makeText(this, "Failed to read from DB: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("DB_ERROR", "storeDataInArrays failed", e);
=======
        // Initialize UI
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        emptyText = findViewById(R.id.emptyText);
        emptyImage = findViewById(R.id.emptyImage); // Link image view

        // DB helper
        dbHelper = new MyDatabaseHelper(this);

        // Recycler setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add button action
        add_button.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks(); // Reload on every resume
    }

    void loadBooks() {
        bookList = dbHelper.getAllBooks();

        if (bookList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            emptyImage.setVisibility(View.GONE);

            adapter = new BookAdapter(this, bookList, dbHelper);
            recyclerView.setAdapter(adapter);
>>>>>>> 6a0a8e8 (Feat : Add Books, Update, Delete and Login Logic)
        }
    }
}
