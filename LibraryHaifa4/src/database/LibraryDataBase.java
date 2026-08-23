package database;

import model.*;
import utils.FileException;
import utils.FileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibraryDataBase { // מאגר נתונים מרכזי

    private ArrayList<Member> members; // רשימת קוראים
    private ArrayList<Librarian> librarians; // רשימת ספרנים
    private ArrayList<Book> books; // רשימת ספרים
    private ArrayList<Borrow> borrows; // רשימת השאלות

    private HashMap<String, ArrayList<Borrow>> borrowsByMember; // מיפוי השאלות לקורא
    private ArrayList<Category> categories; // רשימת קטגוריות

    public LibraryDataBase() { // אתחול כל האוספים
        members = new ArrayList<>(); // יצירת רשימת קוראים
        librarians = new ArrayList<>(); // יצירת רשימת ספרנים
        books = new ArrayList<>(); // יצירת רשימת ספרים
        borrows = new ArrayList<>(); // יצירת רשימת השאלות
        borrowsByMember = new HashMap<>(); // יצירת מיפוי השאלות
        categories = new ArrayList<>(); // יצירת רשימת קטגוריות
        seedData(); // הכנסת נתוני בדיקה
    }

    private void seedData() { // נתונים התחלתיים קבועים

        librarians.add(new HeadLibrarian("212592059", "Adham", "Mreeh", "0545461016", "admin", "12345")); // הוספת ספרן ראשי קבוע

        librarians.add(new Librarian("123", "Ali", "Saleh", "0520000000")); // הוספת ספרן רגיל
        librarians.add(new Librarian("456", "Dana", "Bar", "0520000001")); // הוספת ספרן רגיל

        members.add(new Member("M1", "Ali", "Ahmad", "Street1", "Haifa", "11111", "0501111111")); // הוספת קורא ראשון
        members.add(new Member("M2", "Dana", "Cohen", "Street2", "Haifa", "22222", "0502222222")); // הוספת קורא שני

        Category history = new Category("HISTORY", "HISTORY"); // יצירת קטגוריה היסטוריה
        Category computers = new Category("COMPUTERS", "COMPUTERS"); // יצירת קטגוריה מחשבים
        Category science = new Category("SCIENCE", "SCIENCE"); // יצירת קטגוריה מדע

        categories.add(history); // הוספת קטגוריה
        categories.add(computers); // הוספת קטגוריה
        categories.add(science); // הוספת קטגוריה

        Book b1 = new PrintedBook("B1", "History", "Alex", 2010, 60.0, 10.0, 200, true, 2.0); // יצירת ספר היסטוריה
        Book b2 = new PrintedBook("B2", "Java", "Shadi", 2012, 120.0, 10.0, 300, true, 2.0); // יצירת ספר גאוה
        Book b3 = new EBook("B3", "OOP", "Hala", 2018, 80.0, 8.0, 50.0, "pdf", 14, 1.0); // יצירת ספר מונחה עצמים

        books.add(b1); // הוספת ספר
        books.add(b2); // הוספת ספר
        books.add(b3); // הוספת ספר

        assignBookToCategory("B1", "HISTORY"); // שיוך קטגוריה לספר
        assignBookToCategory("B2", "COMPUTERS"); // שיוך קטגוריה לספר
        assignBookToCategory("B3", "SCIENCE"); // שיוך קטגוריה לספר

        assignBookToLibrarian("B1", "123"); // שיוך ספר לספרן
        assignBookToLibrarian("B2", "123"); // שיוך ספר לספרן
        assignBookToLibrarian("B3", "123"); // שיוך ספר לספרן
    }

    public ArrayList<Member> getMembers() { // החזרת קוראים
        return members;
    }

    public ArrayList<Librarian> getLibrarians() { // החזרת ספרנים
        return librarians;
    }

    public ArrayList<Book> getBooks() { // החזרת ספרים
        return books;
    }

    public ArrayList<Borrow> getBorrows() { // החזרת השאלות
        return borrows;
    }

    public ArrayList<Category> getCategories() { // החזרת קטגוריות
        return categories;
    }

    public boolean addMember(Member m) { // הוספת קורא חדש
        if (m == null) return false; // בדיקת ערך ריק
        if (findMemberByCode(m.getMemberCode()) != null) return false; // בדיקת כפילות קוד
        members.add(m); // הוספת קורא
        return true;
    }

    public boolean addLibrarian(Librarian l) { // הוספת ספרן חדש
        if (l == null) return false; // בדיקת ערך ריק
        if (findLibrarianById(l.getId()) != null) return false; // בדיקת כפילות מזהה
        librarians.add(l); // הוספת ספרן
        return true;
    }

    public boolean addBook(Book b) { // הוספת ספר חדש
        if (b == null) return false; // בדיקת ערך ריק
        if (findBookByCode(b.getBookCode()) != null) return false; // בדיקת כפילות קוד
        books.add(b); // הוספת ספר
        return true;
    }

    public boolean addCategory(Category c) { // הוספת קטגוריה
        if (c == null) return false; // בדיקת ערך ריק
        if (findCategoryByCode(c.getCategoryCode()) != null) return false; // בדיקת כפילות קוד
        categories.add(c); // הוספת קטגוריה
        return true;
    }

    public boolean assignBookToCategory(String bookCode, String categoryCode) { // שיוך ספר לקטגוריה
        Book b = findBookByCode(bookCode); // איתור ספר
        Category c = findCategoryByCode(categoryCode); // איתור קטגוריה
        if (b == null || c == null) return false; // בדיקת קיום
        b.setCategory(c); // עדכון קטגוריה בספר
        c.addBook(b); // הוספת ספר לקטגוריה
        return true;
    }

    public boolean assignBookToLibrarian(String bookCode, String librarianId) { // שיוך ספר לספרן
        Book b = findBookByCode(bookCode); // איתור ספר
        Librarian l = findLibrarianById(librarianId); // איתור ספרן
        if (b == null || l == null) return false; // בדיקת קיום
        l.addBook(b); // הוספת ספר לספרן
        b.addResponsibleLibrarian(l); // הוספת ספרן לספר
        return true;
    }

    public boolean addBorrow(Borrow br) { // הוספת השאלה
        if (br == null) return false; // בדיקת ערך ריק
        if (findBorrowByCode(br.getBorrowCode()) != null) return false; // בדיקת כפילות קוד

        Member m = findMemberByCode(br.getMemberCode()); // איתור קורא
        Book b = findBookByCode(br.getBookCode()); // איתור ספר
        Librarian l = findLibrarianById(br.getLibrarianId()); // איתור ספרן

        if (m == null || b == null || l == null) return false; // בדיקת קיום
        if (!b.isAvailableToBorrow()) return false; // בדיקת זמינות ספר
        if (!l.isResponsibleForBook(b.getBookCode())) return false; // בדיקת אחריות ספרן

        b.setAvailableToBorrow(false); // עדכון זמינות ספר
        borrows.add(br); // הוספת השאלה למאגר

        ArrayList<Borrow> list = borrowsByMember.getOrDefault(m.getMemberCode(), new ArrayList<>()); // שליפת רשימת קורא
        list.add(br); // הוספת השאלה לרשימה
        borrowsByMember.put(m.getMemberCode(), list); // עדכון מיפוי קורא

        l.addBorrow(br); // עדכון השאלות לספרן
        return true;
    }

    public ArrayList<Borrow> getBorrowsByMember(String memberCode) { // החזרת השאלות לקורא
        return borrowsByMember.getOrDefault(memberCode, new ArrayList<>()); // החזרת רשימת השאלות
    }

    public boolean updateMemberInfo(String memberCode, String address, String phone) { // עדכון פרטי קורא
        Member m = findMemberByCode(memberCode); // איתור קורא
        if (m == null) return false; // בדיקת קיום
        if (address != null && !address.trim().isEmpty()) m.setAddress(address); // עדכון כתובת
        if (phone != null && !phone.trim().isEmpty()) m.setPhoneNumber(phone); // עדכון טלפון
        return true;
    }

    public HeadLibrarian loginHeadLibrarian(String username, String password) { // התחברות ספרן ראשי
        for (Librarian l : librarians) { // חיפוש באוסף פנימי
            if (l instanceof HeadLibrarian) { // בדיקת סוג ראשי
                HeadLibrarian hl = (HeadLibrarian) l; // המרה לראשי
                if (hl.getUsername().equals(username) && hl.getPassword().equals(password)) { // בדיקת התאמה
                    return hl;
                }
            }
        }
        return checkHeadLibrarianExternal(username, password); // בדיקה בקובץ חיצוני
    }

    private HeadLibrarian checkHeadLibrarianExternal(String username, String password) { // בדיקת ראשי מקובץ
        try { // ניסיון קריאה חיצונית
            List<String> lines = FileManager.readFile("HeadLibrarians.txt"); // קריאת קובץ ראשיים
            for (String line : lines) { // מעבר על שורות קובץ
                String[] p = line.split(","); // פיצול לשדות
                if (p.length < 6) continue; // דילוג שורה לא תקינה

                String id = p[0].trim(); // חילוץ מזהה
                String first = p[1].trim(); // חילוץ שם פרטי
                String last = p[2].trim(); // חילוץ שם משפחה
                String phone = p[3].trim(); // חילוץ טלפון
                String user = p[4].trim(); // חילוץ משתמש
                String pass = p[5].trim(); // חילוץ סיסמה

                if (user.equals(username) && pass.equals(password)) { // בדיקת התאמה
                    if (findLibrarianById(id) != null) return null; // בדיקת כפילות מזהה
                    HeadLibrarian hl = new HeadLibrarian(id, first, last, phone, user, pass); // יצירת ספרן ראשי
                    librarians.add(hl); // הוספה למערכת
                    return hl;
                }
            }
        } catch (FileException e) { // טיפול שגיאת קובץ
            return null;
        }
        return null;
    }

    public boolean loadLibrariansFromFile() throws FileException { // טעינת ספרנים מקובץ
        List<String> lines = FileManager.readFile("Librarians.txt"); // קריאת קובץ ספרנים
        for (String line : lines) { // מעבר על שורות
            String[] p = line.split(","); // פיצול לשדות
            if (p.length < 5) throw new FileException("קובץ ספרנים לא תקין " + line); // שורה לא תקינה

            String id = p[0].trim(); // חילוץ מזהה
            String first = p[1].trim(); // חילוץ שם פרטי
            String last = p[2].trim(); // חילוץ שם משפחה
            String phone = p[3].trim(); // חילוץ טלפון
            String type = p[4].trim(); // חילוץ סוג

            if (findLibrarianById(id) != null) throw new FileException("קוד ספרן כפול " + id); // שגיאת כפילות

            if (type.equalsIgnoreCase("Head")) { // קליטת ספרן ראשי
                if (p.length < 7) throw new FileException("קובץ ספרנים לא תקין " + line); // שורה לא תקינה
                String user = p[5].trim(); // חילוץ משתמש
                String pass = p[6].trim(); // חילוץ סיסמה
                librarians.add(new HeadLibrarian(id, first, last, phone, user, pass)); // הוספת ראשי
            } else { // קליטת ספרן רגיל
                librarians.add(new Librarian(id, first, last, phone)); // הוספת רגיל
            }
        }
        return true;
    }

    public boolean loadMembersFromFile() throws FileException { // טעינת קוראים מקובץ
        List<String> lines = FileManager.readFile("Members.txt"); // קריאת קובץ קוראים
        for (String line : lines) { // מעבר על שורות
            String[] p = line.split(","); // פיצול לשדות
            if (p.length < 7) throw new FileException("קובץ קוראים לא תקין " + line); // שורה לא תקינה

            String code = p[0].trim(); // חילוץ קוד
            String first = p[1].trim(); // חילוץ שם פרטי
            String last = p[2].trim(); // חילוץ שם משפחה
            String street = p[3].trim(); // חילוץ רחוב
            String city = p[4].trim(); // חילוץ עיר
            String zip = p[5].trim(); // חילוץ מיקוד
            String phone = p[6].trim(); // חילוץ טלפון

            if (findMemberByCode(code) != null) throw new FileException("קוד קורא כפול " + code); // שגיאת כפילות

            members.add(new Member(code, first, last, street, city, zip, phone)); // הוספת קורא
        }
        return true;
    }

    public void exportLibrariansToFile() throws FileException { // שמירת ספרנים לקובץ
        ArrayList<Librarian> regular = new ArrayList<>(); // יצירת רשימה זמנית
        for (Librarian l : librarians) { // מעבר על ספרנים
            if (!(l instanceof HeadLibrarian)) regular.add(l); // סינון ספרנים רגילים
        }
        regular.sort((a, b) -> a.getId().compareTo(b.getId())); // מיון לפי קוד

        ArrayList<String> out = new ArrayList<>(); // יצירת שורות קובץ
        for (Librarian l : regular) { // מעבר על ממוינים
            out.add(l.getId() + "," + l.getFirstName() + "," + l.getLastName() + "," + l.getPhoneNumber() + ",Regular"); // שורת ספרן
        }
        FileManager.writeFile("Librarians.txt", out); // כתיבה לקובץ
    }

    public void exportMembersToFile() throws FileException { // שמירת קוראים לקובץ
        ArrayList<Member> copy = new ArrayList<>(members); // יצירת עותק למיון
        copy.sort((a, b) -> a.getLastName().compareTo(b.getLastName())); // מיון לפי משפחה

        ArrayList<String> out = new ArrayList<>(); // יצירת שורות קובץ
        for (Member m : copy) { // מעבר על ממוינים
            out.add(m.getMemberCode() + "," + m.getFirstName() + "," + m.getLastName() + "," + m.getStreet() + "," + m.getCity() + "," + m.getZip() + "," + m.getPhoneNumber()); // שורת קורא
        }
        FileManager.writeFile("Members.txt", out); // כתיבה לקובץ
    }

    public void exportBorrowsToFile() throws FileException { // שמירת השאלות לקובץ
        ArrayList<Borrow> copy = new ArrayList<>(borrows); // יצירת עותק למיון
        copy.sort((a, b) -> a.getBorrowCode().compareTo(b.getBorrowCode())); // מיון לפי קוד

        ArrayList<String> out = new ArrayList<>(); // יצירת שורות קובץ
        for (Borrow br : copy) { // מעבר על ממוינים
            out.add(br.getBorrowCode() + "," + br.getLibrarianId() + "," + br.getMemberCode() + "," + br.getBookCode() + "," + br.getBorrowDay() + "," + br.getBorrowMonth() + "," + br.getBorrowYear()); // שורת השאלה
        }
        FileManager.writeFile("Borrows.txt", out); // כתיבה לקובץ
    }

    public void exportBooksToFile() throws FileException { // שמירת ספרים לקובץ
        ArrayList<Book> copy = new ArrayList<>(books); // יצירת עותק למיון
        copy.sort((a, b) -> a.getBookCode().compareTo(b.getBookCode())); // מיון לפי קוד

        ArrayList<String> out = new ArrayList<>(); // יצירת שורות קובץ
        for (Book b : copy) { // מעבר על ממוינים
            out.add(b.toFileString()); // שורת ספר
            out.addAll(b.responsibleLibrariansToFileLines()); // שורות ספרנים
        }
        FileManager.writeFile("Books.txt", out); // כתיבה לקובץ
    }

    public Member findMemberByCode(String code) { // חיפוש קורא לפי קוד
        for (Member m : members) { // מעבר על קוראים
            if (m.getMemberCode().equals(code)) return m; // החזרת התאמה
        }
        return null;
    }

    public Librarian findLibrarianById(String id) { // חיפוש ספרן לפי מזהה
        for (Librarian l : librarians) { // מעבר על ספרנים
            if (l.getId().equals(id)) return l; // החזרת התאמה
        }
        return null;
    }

    public Book findBookByCode(String code) { // חיפוש ספר לפי קוד
        for (Book b : books) { // מעבר על ספרים
            if (b.getBookCode().equals(code)) return b; // החזרת התאמה
        }
        return null;
    }

    public Borrow findBorrowByCode(String code) { // חיפוש השאלה לפי קוד
        for (Borrow br : borrows) { // מעבר על השאלות
            if (br.getBorrowCode().equals(code)) return br; // החזרת התאמה
        }
        return null;
    }

    public Category findCategoryByCode(String code) { // חיפוש קטגוריה לפי קוד
        for (Category c : categories) { // מעבר על קטגוריות
            if (c.getCategoryCode().equals(code)) return c; // החזרת התאמה
        }
        return null;
    }
}