package model;

public class EBook extends Book { // מחלקת ספר דיגיטלי

    private double mb; // גודל קובץ
    private String format; // פורמט קובץ
    private int maxDaysBorrow; // ימי השאלה מקסימום
    private double addFine; // תוספת קנס

    public EBook(String bookCode, String bookName, String author, int publishYear, double lendingPrice, double finePrice,
                 double mb, String format, int maxDaysBorrow, double addFine) { // בנאי ספר דיגיטלי
        super(bookCode, bookName, author, publishYear, lendingPrice, finePrice);
        this.mb = mb;
        this.format = format;
        this.maxDaysBorrow = maxDaysBorrow;
        this.addFine = addFine;
    }

    public double getMb() { // החזרת גודל קובץ
        return mb;
    }

    public String getFormat() { // החזרת פורמט קובץ
        return format;
    }

    public int getMaxDaysBorrow() { // החזרת ימי השאלה
        return maxDaysBorrow;
    }

    public double getAddFine() { // החזרת תוספת קנס
        return addFine;
    }
}