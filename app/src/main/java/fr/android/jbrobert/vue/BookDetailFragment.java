package fr.android.jbrobert.vue;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fr.android.jbrobert.R;
import fr.android.jbrobert.model.Book;

public class BookDetailFragment extends Fragment {

    private TextView titleTextView, priceTextView, isbnTextView, synopsisTextView;
    private ImageView coverImageView;
    private Book book;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_view_book, container, false);

        titleTextView = (TextView) view.findViewById(R.id.tv_detail_book_title);
        priceTextView = (TextView) view.findViewById(R.id.tv_detail_book_price);
        isbnTextView = (TextView) view.findViewById(R.id.tv_detail_book_isbn);
        synopsisTextView = (TextView) view.findViewById(R.id.tv_detail_book_synopsis);
        coverImageView = (ImageView) view.findViewById(R.id.iv_detail_book_cover);
        book = getArguments().getParcelable(getString(R.string.clicked_book));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTextView.setText(book.getTitle());
        priceTextView.setText(book.getPrice() + "€");
        isbnTextView.setText(book.getIsbn());
        synopsisTextView.setText(book.getSynopsis().get(0));
        Glide.with(this.getContext()).load(book.getCover()).into(coverImageView);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
