package com.freecodecamp.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.gsm.GsmCellLocation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS = "already_read_books";
    private static final String WANT_TO_READ_BOOKS = "want_to_read_books";
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";
    private static final String FAVORITE_BOOKS = "favorite_books";


    private static Utils instance;
    private SharedPreferences sharedPreferences;

//    Removed after using shared preferences
//    private static ArrayList<Book> allBooks;
//    private static ArrayList<Book> alreadyReadBooks;
//    private static ArrayList<Book> wantToReadBooks;
//    private static ArrayList<Book> currentlyReadingBooks;
//    private static ArrayList<Book> favoriteBooks;

    public Utils(Context context){

        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if(null == getAllBooks()){
//            No longer necessary w/ gson
//            allBooks = new ArrayList<>();
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBooks()){
//            alreadyReadBooks = new ArrayList<>();
            editor.putString(ALREADY_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getWantToReadBooks()){
            editor.putString(WANT_TO_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getCurrentlyReadingBooks()){
            editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getFavoriteBooks()){
            editor.putString(FAVORITE_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }

    private void initData() {
        //TODO: add initial data

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "1Q84", "Haruki Murakami", 1186, "https://images-na.ssl-images-amazon.com/images/I/41FdmYnaNuL._SX322_BO1,204,203,200_.jpg",
                "A work of brilliance", "Murakami is like a magician who explains what he’s doing as he performs the trick and still makes you believe he has supernatural powers . . . But while anyone can tell a story that resembles a dream, it's the rare artist, like this one, who can make us feel that we are dreaming it ourselves. —The New York Times Book Review"));
        books.add(new Book(2, "The Stinky Cheese Man and Other Fairly Stupid Fairy Tales", "Jon Scieszca & Lane Smith", 56, "https://images-na.ssl-images-amazon.com/images/I/91vrbFo7bxL.jpg",
                "Cute", "The entire book, with its unconventional page arrangement and eclectic, frenetic mix of text and pictures, is a spoof on the art of book design and the art of the fairy tale. The individual tales, such as The Really Ugly Duckling and Little Red Running Shorts, can be extracted for telling aloud, with great success. Another masterpiece from the team that created The True Story of the Three Little Pigs!\n" +
                "-Horn Book"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit(); //using apply would use a thread for continuous app usage. commit is being used bc the data is so small
    }

    //Makes this class a singleton
    public static Utils getInstance(Context context) {
        if (null != instance){
            return instance;
        } else {
            instance = new Utils(context);
            return instance;
        }
    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getFavoriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS, null), type);
        return books;
    }

    public Book getBookById(int id){
        ArrayList<Book> books = getAllBooks();
        if(null != books) {
            for (Book b : books) {
                if (b.getId() == id) {
                    return b;
                }
            }
        }

        return null;
    }

    public boolean addToAlreadyRead(Book book){
//        return alreadyReadBooks.add(book);
        ArrayList<Book> books = getAlreadyReadBooks();
        if(null != books) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS);
                editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }

        return false;
    }

    public boolean addToWantToRead(Book book){
//        return wantToReadBooks.add(book);
        ArrayList<Book> books = getWantToReadBooks();
        if(null != books) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS);
                editor.putString(WANT_TO_READ_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }

        return false;
    }

    public boolean addToCurrentlyReading(Book book){
//        return currentlyReadingBooks.add(book);
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if(null != books) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS);
                editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }

        return false;
    }

    public boolean addToFavorite(Book book){
//        return favoriteBooks.add(book);
        ArrayList<Book> books = getFavoriteBooks();
        if(null != books) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_BOOKS);
                editor.putString(FAVORITE_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }

        return false;
    }

    public boolean removeFromAlreadyRead(Book book){
//        return alreadyReadBooks.remove(book);
        ArrayList<Book> books = getAlreadyReadBooks();
        if(null != books){
            for(Book b: books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS);
                        editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromWantToRead(Book book){
//        return wantToReadBooks.remove(book);
        ArrayList<Book> books = getWantToReadBooks();
        if(null != books){
            for(Book b: books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOKS);
                        editor.putString(WANT_TO_READ_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromCurrentlyReading(Book book){
//        return currentlyReadingBooks.remove(book);
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if(null != books){
            for(Book b: books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS);
                        editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFavorite(Book book){
//        return favoriteBooks.remove(book);
        ArrayList<Book> books = getFavoriteBooks();
        if(null != books){
            for(Book b: books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITE_BOOKS);
                        editor.putString(FAVORITE_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
