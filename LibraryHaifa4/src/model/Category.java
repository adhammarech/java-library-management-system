package model;

import java.util.ArrayList;

public class Category { // מחלקת קטגוריה

    private String categoryCode; // קוד קטגוריה
    private String categoryName; // שם קטגוריה
    private ArrayList<Book> books; // ספרים בקטגוריה

    public Category(String categoryCode, String categoryName) { // בנאי קטגוריה
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.books = new ArrayList<>();
    }

    public String getCategoryCode() { // החזרת קוד קטגוריה
        return categoryCode;
    }

    public String getCategoryName() { // החזרת שם קטגוריה
        return categoryName;
    }

    public ArrayList<Book> getBooks() { // החזרת ספרים בקטגוריה
        return books;
    }

    public boolean addBook(Book b) { // הוספת ספר לקטגוריה
        if (b == null) return false; // בדיקת ערך ריק
        if (!books.contains(b)) books.add(b); // מניעת כפילות ספר
        return true;
    }

    @Override
    public String toString() { // הצגת קטגוריה
        return categoryCode + " " + categoryName;
    }
}