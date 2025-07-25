// CustomAdapter.java
package com.soocil.ebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.BookViewHolder> {

    private final Context context;
    private final ArrayList<String> book_id;
    private final ArrayList<String> book_title;
    private final ArrayList<String> book_author;
    private final ArrayList<String> book_pages;
    private final MyDatabaseHelper myDB;

    public CustomAdapter(Context context, ArrayList<String> book_id, ArrayList<String> book_title,
                         ArrayList<String> book_author, ArrayList<String> book_pages) {
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
        this.myDB = new MyDatabaseHelper(context);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        String id = book_id.get(position);
        String title = book_title.get(position);
        String author = book_author.get(position);
        String pages = book_pages.get(position);

        holder.title.setText(title);
        holder.author.setText(author);
        holder.pages.setText(pages + " pages");

        // Edit Button Click
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("author", author);
            intent.putExtra("pages", pages);
            context.startActivity(intent);
        });

        // Delete Button Click
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Book")
                    .setMessage("Do you want to delete \"" + title + "\"?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        myDB.deleteOneRow(id); // Call existing deleteOneRow method
                        book_id.remove(position);
                        book_title.remove(position);
                        book_author.remove(position);
                        book_pages.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, book_id.size());
                        Toast.makeText(context, "Deleted \"" + title + "\"", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, pages;
        ImageButton btnEdit, btnDelete;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            author = itemView.findViewById(R.id.authorText);
            pages = itemView.findViewById(R.id.pagesText);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}