package ui;

import database.LibraryDataBase;
import model.Book;
import model.Borrow;
import model.Librarian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class LibrarianFrame extends JFrame { // מסך ספרן רגיל

    private LibraryDataBase db; // שמירת בסיס נתונים
    private Librarian librarian; // שמירת ספרן פעיל

    private JTable booksTable; // טבלת ספרים
    private JTextField memberField; // שדה קוד קורא
    private JTextField bookField; // שדה קוד ספר
    private JTextField dateField; // שדה תאריך

    public LibrarianFrame(LibraryDataBase db, Librarian librarian) { // בנאי מסך ספרן
        this.db = db;
        this.librarian = librarian;
        initFrame(); // אתחול חלון
        initUI(); // אתחול רכיבים
        refreshBooks(); // רענון טבלה
    }

    private void initFrame() { // הגדרות חלון
        setTitle("Librarian");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void initUI() { // בניית ממשק
        booksTable = new JTable(); // יצירת טבלה
        add(new JScrollPane(booksTable), BorderLayout.CENTER); // הוספת טבלה

        JPanel form = new JPanel(new GridLayout(1, 7, 8, 8)); // טופס השאלה
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // שוליים פנימיים

        memberField = new JTextField(); // יצירת שדה קורא
        bookField = new JTextField(); // יצירת שדה ספר
        dateField = new JTextField("01/01/2026"); // יצירת שדה תאריך

        JButton borrowBtn = new JButton("Borrow"); // כפתור השאלה
        borrowBtn.addActionListener(e -> doBorrow()); // פעולה לכפתור

        JButton backBtn = new JButton("Back"); // כפתור חזרה
        backBtn.addActionListener(e -> backToLogin()); // פעולה לכפתור

        form.add(new JLabel("Member ID")); form.add(memberField); // שדות קורא
        form.add(new JLabel("Book code")); form.add(bookField); // שדות ספר
        form.add(new JLabel("Date")); form.add(dateField); // שדות תאריך
        form.add(borrowBtn); // הוספת כפתור

        add(form, BorderLayout.SOUTH); // הוספת טופס
        add(backBtn, BorderLayout.NORTH); // הוספת חזרה
    }

    private void refreshBooks() { // רענון ספרים באחריות
        DefaultTableModel m = new DefaultTableModel(new String[]{"Index", "Title", "Available"}, 0); // מודל טבלה
        ArrayList<Book> list = db.getBooks(); // שליפת ספרים
        int idx = 0; // מונה אינדקס
        for (Book b : list) { // מעבר על ספרים
            if (librarian.isResponsibleForBook(b.getBookCode())) { // בדיקת אחריות ספרן
                m.addRow(new Object[]{idx, b.getBookName(), b.isAvailableToBorrow()}); // הוספת שורה
            }
            idx++; // עדכון אינדקס
        }
        booksTable.setModel(m); // עדכון טבלה
    }

    private void doBorrow() { // ביצוע השאלה
        try { // ניסיון קליטה
            String memberCode = memberField.getText().trim(); // קריאת קורא
            String bookCode = bookField.getText().trim(); // קריאת ספר
            String[] d = dateField.getText().trim().split("/"); // פיצול תאריך
            int day = Integer.parseInt(d[0]); // קליטת יום
            int month = Integer.parseInt(d[1]); // קליטת חודש
            int year = Integer.parseInt(d[2]); // קליטת שנה

            String code = "BC" + (db.getBorrows().size() + 1); // יצירת קוד השאלה
            Borrow br = new Borrow(code, librarian.getId(), memberCode, bookCode, day, month, year); // יצירת השאלה

            boolean ok = db.addBorrow(br); // הוספת השאלה
            if (!ok) { // טיפול כשל השאלה
                JOptionPane.showMessageDialog(this, "Cannot borrow");
                return;
            }

            JOptionPane.showMessageDialog(this, "Borrowed"); // הודעת הצלחה
            refreshBooks(); // רענון טבלה

        } catch (Exception ex) { // טיפול נתונים שגויים
            JOptionPane.showMessageDialog(this, "Invalid data");
        }
    }

    private void backToLogin() { // חזרה למסך התחברות
        new LoginFrame(db).setVisible(true); // פתיחת התחברות
        dispose(); // סגירת חלון
    }
}