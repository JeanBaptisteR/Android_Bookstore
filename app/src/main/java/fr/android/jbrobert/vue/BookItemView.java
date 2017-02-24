package fr.android.jbrobert.vue;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fr.android.jbrobert.R;
import fr.android.jbrobert.model.Book;


public class BookItemView extends LinearLayout {

    private TextView titleTextView, priceTextView;
    private ImageView coverImageView;

    public BookItemView(Context context) {
        this(context, null);
    }

    public BookItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        titleTextView = (TextView) this.findViewById(R.id.title_text_view);
        priceTextView = (TextView) this.findViewById(R.id.price_text_view);
        coverImageView = (ImageView) this.findViewById(R.id.book_image_view);
    }

    public void bindView(Book book) {
        titleTextView.setText(book.getTitle());
        priceTextView.setText(book.getPrice() + "€");
        Glide.with(this.getContext()).load(book.getCover()).into(coverImageView);
    }
}