package fr.android.jbrobert.vue;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.android.jbrobert.R;
import fr.android.jbrobert.model.Book;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<Book> books;

    public BookRecyclerAdapter (LayoutInflater inflater, List<Book> books) {
        this.inflater = inflater;
        this.books = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookItemView bookItemView = (BookItemView)holder.itemView;
        bookItemView.bindView(books.get(position));
        holder.setBook(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public interface BookItemListener {
        void onClickBookItem(Book book);
    }

    public void setBookList (List<Book> listBook) {
        books = listBook;
        this.notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private BookItemListener listener;
        private Book book;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            listener = (BookItemListener) itemView.getContext();
        }

        @Override
        public void onClick(View v) {
            listener.onClickBookItem(book);
        }

        public void setBook(Book book) {
            this.book = book;
        }
    }

}