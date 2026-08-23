package model;

public class Borrow { // מחלקת השאלה

    private String borrowCode; // קוד השאלה
    private String librarianId; // מזהה ספרן
    private String memberCode; // קוד קורא
    private String bookCode; // קוד ספר

    private int borrowDay; // יום השאלה
    private int borrowMonth; // חודש השאלה
    private int borrowYear; // שנה השאלה

    public Borrow(String borrowCode, String librarianId, String memberCode, String bookCode, int borrowDay, int borrowMonth, int borrowYear) { // בנאי השאלה
        this.borrowCode = borrowCode;
        this.librarianId = librarianId;
        this.memberCode = memberCode;
        this.bookCode = bookCode;
        this.borrowDay = borrowDay;
        this.borrowMonth = borrowMonth;
        this.borrowYear = borrowYear;
    }

    public String getBorrowCode() { // החזרת קוד השאלה
        return borrowCode;
    }

    public String getLibrarianId() { // החזרת מזהה ספרן
        return librarianId;
    }

    public String getMemberCode() { // החזרת קוד קורא
        return memberCode;
    }

    public String getBookCode() { // החזרת קוד ספר
        return bookCode;
    }

    public int getBorrowDay() { // החזרת יום השאלה
        return borrowDay;
    }

    public int getBorrowMonth() { // החזרת חודש השאלה
        return borrowMonth;
    }

    public int getBorrowYear() { // החזרת שנה השאלה
        return borrowYear;
    }

    @Override
    public String toString() { // הצגת השאלה מלאה
        return borrowCode + " " + librarianId + " " + memberCode + " " + bookCode + " " + borrowDay + " " + borrowMonth + " " + borrowYear;
    }
}