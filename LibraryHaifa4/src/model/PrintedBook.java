package model;

public class PrintedBook extends Book { // מחלקת ספר מודפס

    private int pagesNumber; // מספר עמודים
    private boolean hardCover; // כריכה קשה
    private double addFine; // תוספת קנס

    public PrintedBook(String bookCode, String bookName, String author, int publishYear, double lendingPrice, double finePrice,
                       int pagesNumber, boolean hardCover, double addFine) { // בנאי ספר מודפס
        super(bookCode, bookName, author, publishYear, lendingPrice, finePrice);
        this.pagesNumber = pagesNumber;
        this.hardCover = hardCover;
        this.addFine = addFine;
    }

    public int getPagesNumber() { // החזרת מספר עמודים
        return pagesNumber;
    }

    public boolean isHardCover() { // החזרת כריכה קשה
        return hardCover;
    }

    public double getAddFine() { // החזרת תוספת קנס
        return addFine;
    }
}