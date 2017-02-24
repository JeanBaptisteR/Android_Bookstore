package fr.android.jbrobert.vue;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.android.jbrobert.R;
import fr.android.jbrobert.model.Book;
import fr.android.jbrobert.service.LibraryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class BookListFragment extends Fragment {

    private ArrayList<Book> bookList;
    private RecyclerView recyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_book, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.bookListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new BookRecyclerAdapter(LayoutInflater.from(view.getContext()), new ArrayList<Book>()));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null){
            bookList = savedInstanceState.getParcelableArrayList(getString(R.string.book_list));
        }

        if(bookList==null || bookList.size() == 0){
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://henri-potier.xebia.fr/").addConverterFactory(GsonConverterFactory.create()).build();
            LibraryService service = retrofit.create(LibraryService.class);

            final ProgressDialog mProgressDialog = new ProgressDialog(view.getContext());
            mProgressDialog.setMessage("Chargement des ressources ...");
            mProgressDialog.show();
            service.listBooks().enqueue(new Callback<List<Book>>() {
                @Override
                public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                    bookList = (ArrayList<Book>) response.body();
                    ((BookRecyclerAdapter) recyclerView.getAdapter()).setBookList(bookList);
                    mProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<List<Book>> call, Throwable t) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Timber.e(t.getMessage());
                    Toast.makeText(getContext(), "Erreur de chargement des ressources", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ((BookRecyclerAdapter) recyclerView.getAdapter()).setBookList(bookList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(outState.getParcelableArrayList(getString(R.string.book_list)) == null) {
            outState.putParcelableArrayList(getString(R.string.book_list), bookList);
        }
    }

}
