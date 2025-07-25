// MainActivity.java
package com.soocil.ebook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView emptyImage;
    private TextView emptyText;
    private FloatingActionButton addButton;
    private CustomAdapter adapter;
    private ArrayList<String> book_id, book_title, book_author, book_pages;
    private MyDatabaseHelper db;
    private ActivityResultLauncher<Intent> updateLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        emptyImage = findViewById(R.id.emptyImage);
        emptyText = findViewById(R.id.emptyText);
        addButton = findViewById(R.id.add_button);

        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();
        db = new MyDatabaseHelper(this);

        adapter = new CustomAdapter(this, book_id, book_title, book_author, book_pages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                loadBooks(); // Refresh on activity result
            }
        });

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            updateLauncher.launch(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks(); // Refresh RecyclerView every time MainActivity is resumed
    }

    void loadBooks() {
        book_id.clear();
        book_title.clear();
        book_author.clear();
        book_pages.clear();

        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {
            emptyImage.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyImage.setVisibility(View.GONE);
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}