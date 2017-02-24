package fr.android.jbrobert;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fr.android.jbrobert.model.Book;
import fr.android.jbrobert.vue.BookDetailFragment;
import fr.android.jbrobert.vue.BookListFragment;
import fr.android.jbrobert.vue.BookRecyclerAdapter;


public class LibraryActivity extends AppCompatActivity implements BookRecyclerAdapter.BookItemListener {

    private View secondFrameLayout;
    private Book selectedBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_library);
        secondFrameLayout = findViewById(R.id.container_secondary_frame_layout);
        int orientation = getResources().getConfiguration().orientation;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_primary_frame_layout, new BookListFragment(), BookListFragment.class.getSimpleName())
                    .commit();
        } else {
            // Restore selected book if it was saves
            if(savedInstanceState.getParcelable(getString(R.string.selected_book)) != null){
                selectedBook = savedInstanceState.getParcelable(getString(R.string.selected_book));
            }
        }

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            secondFrameLayout.setVisibility(View.GONE);

            // Restore selected book instead of book list
            if(selectedBook != null && getSupportFragmentManager().findFragmentById(R.id.container_secondary_frame_layout) != null) {
                replaceBookDetailLayout(selectedBook, R.id.container_primary_frame_layout);
            }
        } else {
            getSupportFragmentManager()
                    .popBackStack(BookListFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Restore selected book instead of book list
            if(selectedBook != null) {
                // If user was on the list, display only the list on landscape orientation
                if((getSupportFragmentManager().findFragmentById(R.id.container_primary_frame_layout) instanceof BookListFragment)){
                    selectedBook = null;
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_secondary_frame_layout);
                    if(fragment != null) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                } else {
                    replaceBookDetailLayout(selectedBook, R.id.container_secondary_frame_layout);
                }
            }
        }
    }

    @Override
    public void onClickBookItem(Book book) {
        selectedBook = book;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            secondFrameLayout.setVisibility(View.VISIBLE);
            replaceBookDetailLayout(book, R.id.container_secondary_frame_layout);
        } else {
            replaceBookDetailLayout(book, R.id.container_primary_frame_layout);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(selectedBook != null && outState.getParcelable(getString(R.string.selected_book)) != selectedBook) {
            // Save selected book
            outState.putParcelable(getString(R.string.selected_book), selectedBook);
        }
    }

    /**
     * This method allows you to create a BookDetail fragment ans to choose in which layout it will be placed
     * @param book The book
     * @param containViewId The container ID
     */
    public void replaceBookDetailLayout(Book book, int containViewId) {
        BookDetailFragment detailFragment = new BookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.clicked_book), book);
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(containViewId, detailFragment, BookDetailFragment.class.getSimpleName())
                .addToBackStack(BookListFragment.class.getSimpleName())
                .commit();
    }

}