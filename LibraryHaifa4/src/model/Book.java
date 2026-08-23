package model;

import java.util.ArrayList;

public abstract class Book { // מחלקת ספר בסיס

    protected String bookCode; // קוד ספר
    protected String bookName; // שם ספר
    protected String author; // שם מחבר
    protected int publishYear; // שנת פרסום
    protected boolean availableToBorrow; // זמינות להשאלה
    protected double lendingPrice; // מחיר השאלה
    protected double finePrice; // קנס יומי

    protected Category category; // קטגוריה לספר
    protected ArrayList<Librarian> responsibleLibrarians; // ספרנים אחראים

    public Book(String bookCode, String bookName, String author, int publishYear, double lendingPrice, double finePrice) { // בנאי ספר בסיס
        this.bookCode = bookCode;
        this.bookName = bookName;
        this.author = author;
        this.publishYear = publishYear;
        this.lendingPrice = lendingPrice;
        this.finePrice = finePrice;
        this.availableToBorrow = true;
        this.responsibleLibrarians = new ArrayList<>();
    }

    public String getBookCode() { // החזרת קוד ספר
        return bookCode;
    }

    public String getBookName() { // החזרת שם ספר
        return bookName;
    }

    public String getAuthor() { // החזרת שם מחבר
        return author;
    }

    public int getPublishYear() { // החזרת שנת פרסום
        return publishYear;
    }

    public boolean isAvailableToBorrow() { // בדיקת זמינות להשאלה
        return availableToBorrow;
    }

    public void setAvailableToBorrow(boolean availableToBorrow) { // עדכון זמינות להשאלה
        this.availableToBorrow = availableToBorrow;
    }

    public double getLendingPrice() { // החזרת מחיר השאלה
        return lendingPrice;
    }

    public double getFinePrice() { // החזרת קנס יומי
        return finePrice;
    }

    public Category getCategory() { // החזרת קטגוריה
        return category;
    }

    public void setCategory(Category category) { // עדכון קטגוריה
        this.category = category;
    }

    public boolean addResponsibleLibrarian(Librarian l) { // הוספת ספרן אחראי
        if (l == null) return false; // בדיקת ערך ריק
        if (!responsibleLibrarians.contains(l)) responsibleLibrarians.add(l); // מניעת כפילות ספרן
        return true;
    }

    public ArrayList<Librarian> getResponsibleLibrarians() { // החזרת ספרנים אחראים
        return responsibleLibrarians;
    }

    public String toFileString() { // שורת שמירה לקובץ
        String type = getClass().getSimpleName(); // קביעת סוג ספר
        String cat = (category == null) ? "" : category.getCategoryCode(); // קביעת קוד קטגוריה
        return bookCode + "," + bookName + "," + author + "," + publishYear + "," + type + "," + cat + "," + availableToBorrow + "," + lendingPrice + "," + finePrice;
    }

    public ArrayList<String> responsibleLibrariansToFileLines() { // שורות ספרנים אחראים
        ArrayList<String> lines = new ArrayList<>(); // יצירת רשימת שורות
        for (Librarian l : responsibleLibrarians) { // מעבר על ספרנים אחראים
            lines.add("LIBRARIAN," + l.getId() + "," + l.getFirstName()); // שורה עבור ספרן
        }
        return lines;
    }

    @Override
    public String toString() { // הצגת ספר מלא
        return bookCode + " " + bookName + " " + author + " " + publishYear + " " + availableToBorrow;
    }
}