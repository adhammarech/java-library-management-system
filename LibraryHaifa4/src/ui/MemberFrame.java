package ui;

import database.LibraryDataBase;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MemberFrame extends JFrame { // מסך קורא

    private LibraryDataBase db; // שמירת בסיס נתונים
    private Member member; // שמירת קורא

    private JTable borrowsTable; // טבלת השאלות
    private JTextField searchField; // שדה חיפוש
    private JTextField addressField; // שדה כתובת
    private JTextField phoneField; // שדה טלפון

    public MemberFrame(LibraryDataBase db, Member member) { // בנאי מסך קורא
        this.db = db;
        this.member = member;
        initFrame(); // אתחול חלון
        initUI(); // אתחול ממשק
        refreshBorrows(); // רענון השאלות
    }

    private void initFrame() { // הגדרות חלון
        setTitle("Member");
        setSize(760, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void initUI() { // בניית רכיבים
        borrowsTable = new JTable(); // יצירת טבלה
        add(new JScrollPane(borrowsTable), BorderLayout.CENTER); // הוספת טבלה

        JPanel bottom = new JPanel(new BorderLayout()); // פאנל תחתון
        JPanel search = new JPanel(new FlowLayout()); // פאנל חיפוש
        searchField = new JTextField(18); // שדה חיפוש
        JButton searchBtn = new JButton("Search Book"); // כפתור חיפוש
        searchBtn.addActionListener(e -> searchBook()); // פעולה לכפתור
        search.add(new JLabel("Search")); search.add(searchField); search.add(searchBtn); // הוספת רכיבי חיפוש

        JPanel update = new JPanel(new GridLayout(3, 2, 6, 6)); // פאנל עדכון
        addressField = new JTextField(); // שדה כתובת
        phoneField = new JTextField(); // שדה טלפון
        JButton updateBtn = new JButton("Update"); // כפתור עדכון
        updateBtn.addActionListener(e -> updateInfo()); // פעולה לכפתור
        JButton backBtn = new JButton("Back"); // כפתור חזרה
        backBtn.addActionListener(e -> backToLogin()); // פעולה לכפתור

        update.add(new JLabel("Address")); update.add(addressField); // שדות כתובת
        update.add(new JLabel("Phone")); update.add(phoneField); // שדות טלפון
        update.add(backBtn); update.add(updateBtn); // כפתורים

        bottom.add(search, BorderLayout.NORTH); // הוספת חיפוש
        bottom.add(update, BorderLayout.CENTER); // הוספת עדכון
        add(bottom, BorderLayout.SOUTH); // הוספת פאנל תחתון
    }

    private void refreshBorrows() { // רענון השאלות לקורא
        DefaultTableModel m = new DefaultTableModel(new String[]{"Book", "Price", "Date"}, 0); // מודל טבלה
        ArrayList<Borrow> list = db.getBorrowsByMember(member.getMemberCode()); // שליפת השאלות
        for (Borrow br : list) { // מעבר על השאלות
            Book b = db.findBookByCode(br.getBookCode()); // שליפת ספר
            String bookText = (b == null) ? br.getBookCode() : b.getBookName() + " | " + b.getAuthor() + " | " + b.getLendingPrice(); // טקסט ספר
            double price = (b == null) ? 0 : b.getLendingPrice(); // מחיר
            String date = String.format("%02d/%02d/%04d", br.getBorrowDay(), br.getBorrowMonth(), br.getBorrowYear()); // תאריך
            m.addRow(new Object[]{bookText, price, date}); // הוספת שורה
        }
        borrowsTable.setModel(m); // עדכון טבלה
    }

    private void searchBook() { // חיפוש ספר לפי טקסט
        String q = searchField.getText().trim(); // קריאת טקסט חיפוש
        if (q.isEmpty()) return; // בדיקת טקסט ריק
        for (Book b : db.getBooks()) { // מעבר על ספרים
            if (b.getBookName().equalsIgnoreCase(q) || b.getAuthor().equalsIgnoreCase(q)) { // בדיקת התאמה
                JOptionPane.showMessageDialog(this, b.getBookName() + " | " + b.getAuthor() + " | " + b.getLendingPrice()); // הצגת תוצאה
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Not found"); // הודעת אי מציאה
    }

    private void updateInfo() { // עדכון פרטים אישיים
        String addr = addressField.getText().trim(); // קריאת כתובת
        String phone = phoneField.getText().trim(); // קריאת טלפון
        boolean ok = db.updateMemberInfo(member.getMemberCode(), addr, phone); // ביצוע עדכון
        if (!ok) JOptionPane.showMessageDialog(this, "Cannot update"); // הודעת כשל
        refreshBorrows(); // רענון טבלה
    }

    private void backToLogin() { // חזרה למסך התחברות
        new LoginFrame(db).setVisible(true); // פתיחת התחברות
        dispose(); // סגירת חלון
    }
}