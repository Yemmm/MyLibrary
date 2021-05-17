package com.freecodecamp.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.util.Util;

import java.util.ArrayList;

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView bookRecView;
    private BooksRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

/*        Was Used for testing out animations but only on the book activity. Global animations are being used under themes.xml
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new BooksRecViewAdapter(this, "allBooks");
        bookRecView = findViewById(R.id.booksRecView);

        bookRecView.setAdapter(adapter);
        bookRecView.setLayoutManager(new LinearLayoutManager(this ));

        adapter.setBooks(Utils.getInstance(this).getAllBooks());
    }

/*    Was Used for testing out animations but only on the book activity. Global animations are being used under themes.xml
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
    }*/

//    For Back button on the top to go back to the previous actvity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}