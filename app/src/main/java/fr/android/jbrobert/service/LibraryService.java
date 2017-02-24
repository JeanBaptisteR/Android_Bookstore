package fr.android.jbrobert.service;

import java.util.List;

import fr.android.jbrobert.model.Book;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LibraryService {

    @GET("books")
    Call<List<Book>> listBooks();

}
