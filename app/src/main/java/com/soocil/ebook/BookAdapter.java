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
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private final Context context;
    private final List<BookModel> bookList;
    private final MyDatabaseHelper dbHelper;

    public BookAdapter(Context context, List<BookModel> bookList, MyDatabaseHelper dbHelper) {
        this.context = context;
        this.bookList = bookList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookModel book = bookList.get(position);

        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.pages.setText(book.getPages() + " pages");

        holder.btnDelete.setOnClickListener(v ->
                new AlertDialog.Builder(context)
                        .setTitle("Delete Book")
                        .setMessage("Are you sure you want to delete \"" + book.getTitle() + "\"?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            dbHelper.deleteBook(book.getId());
                            bookList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, bookList.size());
                            if (bookList.isEmpty() && context instanceof MainActivity) {
                                ((MainActivity) context).loadBooks();
                            }
                            Toast.makeText(context, "Book deleted", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", null)
                        .show()
        );

        holder.btnEdit.setOnClickListener(v -> {
            if (book.getId() <= 0) {
                Toast.makeText(context, "Invalid Book ID", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", book.getId());
            intent.putExtra("title", book.getTitle());
            intent.putExtra("author", book.getAuthor());
            intent.putExtra("pages", book.getPages());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, pages;
        ImageButton btnDelete, btnEdit;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            author = itemView.findViewById(R.id.authorText);
            pages = itemView.findViewById(R.id.pagesText);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}