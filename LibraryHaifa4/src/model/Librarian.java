package model;

import java.util.ArrayList;

public class Librarian { // מחלקת ספרן

    private String id; // תעודת זהות
    private String firstName; // שם פרטי
    private String lastName; // שם משפחה
    private String phoneNumber; // טלפון

    private ArrayList<Book> assignedBooks; // ספרים באחריות
    private ArrayList<Borrow> myBorrows; // השאלות שביצע

    public Librarian(String id, String firstName, String lastName, String phoneNumber) { // בנאי ספרן
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.assignedBooks = new ArrayList<>();
        this.myBorrows = new ArrayList<>();
    }

    public String getId() { // החזרת מזהה ספרן
        return id;
    }

    public String getFirstName() { // החזרת שם פרטי
        return firstName;
    }

    public String getLastName() { // החזרת שם משפחה
        return lastName;
    }

    public String getPhoneNumber() { // החזרת טלפון
        return phoneNumber;
    }

    public boolean addBook(Book b) { // הוספת ספר לאחריות
        if (b == null) return false; // בדיקת ערך ריק
        if (!assignedBooks.contains(b)) assignedBooks.add(b); // מניעת כפילות ספר
        return true;
    }

    public boolean isResponsibleForBook(String bookCode) { // בדיקת אחריות ספרן
        for (Book b : assignedBooks) { // מעבר על ספרים
            if (b.getBookCode().equals(bookCode)) return true; // נמצאה התאמה
        }
        return false;
    }

    public boolean addBorrow(Borrow br) { // הוספת השאלה לספרן
        if (br == null) return false; // בדיקת ערך ריק
        myBorrows.add(br); // הוספה לרשימת השאלות
        return true;
    }

    public int getBorrowCount() { // החזרת מספר השאלות
        return myBorrows.size();
    }

    @Override
    public String toString() { // הצגת ספרן מלא
        return id + " " + firstName + " " + lastName + " " + phoneNumber;
    }
}