package ui;

import database.LibraryDataBase;
import model.Book;
import model.Borrow;
import model.Category;
import model.EBook;
import model.HeadLibrarian;
import model.Librarian;
import model.Member;
import model.PrintedBook;
import utils.FileException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

public class HeadLibrarianFrame extends JFrame { // מסך ספרן ראשי

    private LibraryDataBase db; // שמירת בסיס נתונים
    private HeadLibrarian head; // שמירת ספרן ראשי

    private JTable membersTable; // טבלת קוראים
    private JTable librariansTable; // טבלת ספרנים
    private JTable booksTable; // טבלת ספרים

    private JTextField libIdField; // שדה מזהה ספרן
    private JTextField libPhoneField; // שדה טלפון ספרן
    private JTextField libFirstField; // שדה שם פרטי ספרן
    private JTextField libLastField; // שדה שם משפחה ספרן
    private JComboBox<String> libTypeBox; // בחירת סוג ספרן
    private JTextField libUserField; // שדה משתמש ספרן
    private JTextField libPassField; // שדה סיסמה ספרן

    private JTextField bookCodeField; // שדה קוד ספר
    private JTextField bookNameField; // שדה שם ספר
    private JTextField bookAuthorField; // שדה מחבר
    private JTextField bookYearField; // שדה שנת פרסום
    private JTextField bookPriceField; // שדה מחיר השאלה
    private JTextField bookFineField; // שדה קנס יומי
    private JComboBox<String> bookTypeBox; // בחירת סוג ספר
    private JTextField bookExtra1Field; // שדה נתון נוסף
    private JTextField bookExtra2Field; // שדה נתון נוסף
    private JTextField bookExtra3Field; // שדה נתון נוסף
    private JTextField bookCategoryField; // שדה קוד קטגוריה

    private JTextField assignLibIdField; // שדה קוד ספרן
    private JTextField assignBookCodeField; // שדה קוד ספר

    public HeadLibrarianFrame(LibraryDataBase db, HeadLibrarian head) { // בנאי מסך ראשי
        this.db = db; // שמירת בסיס נתונים
        this.head = head; // שמירת ספרן ראשי
        initFrame(); // אתחול חלון
        initUI(); // בניית ממשק
        refreshAll(); // רענון טבלאות
    }

    private void initFrame() { // הגדרות חלון
        setTitle("Head Librarian"); // כותרת חלון
        setSize(820, 520); // גודל חלון
        setLocationRelativeTo(null); // מיקום מרכזי
        setDefaultCloseOperation(EXIT_ON_CLOSE); // סגירת חלון
        setLayout(new BorderLayout()); // פריסת חלון
    }

    private void initUI() { // בניית כרטיסיות
        JTabbedPane tabs = new JTabbedPane(); // יצירת כרטיסיות
        tabs.add("Members", buildMembersPanel()); // כרטיסיית קוראים
        tabs.add("Librarians", buildLibrariansPanel()); // כרטיסיית ספרנים
        tabs.add("Books", buildBooksPanel()); // כרטיסיית ספרים
        tabs.add("Assign", buildAssignPanel()); // כרטיסיית שיוך
        tabs.add("Files", buildFilesPanel()); // כרטיסיית קבצים
        tabs.add("Tool", buildToolPanel()); // כרטיסיית כלי עזר
        add(tabs, BorderLayout.CENTER); // הוספת כרטיסיות
    }

    private JPanel buildMembersPanel() { // בניית כרטיסיית קוראים
        JPanel p = new JPanel(new BorderLayout()); // יצירת פאנל
        membersTable = new JTable(); // יצירת טבלה
        p.add(new JScrollPane(membersTable), BorderLayout.CENTER); // הוספת טבלה
        return p; // החזרת פאנל
    }

    private JPanel buildLibrariansPanel() { // בניית כרטיסיית ספרנים
        JPanel p = new JPanel(new BorderLayout()); // יצירת פאנל
        librariansTable = new JTable(); // יצירת טבלה
        p.add(new JScrollPane(librariansTable), BorderLayout.CENTER); // הוספת טבלה

        JPanel form = new JPanel(new GridLayout(3, 6, 6, 6)); // יצירת טופס
        form.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // שוליים פנימיים

        libIdField = new JTextField(); // יצירת שדה מזהה
        libPhoneField = new JTextField(); // יצירת שדה טלפון
        libFirstField = new JTextField(); // יצירת שדה שם פרטי
        libLastField = new JTextField(); // יצירת שדה שם משפחה
        libTypeBox = new JComboBox<>(new String[]{"Regular", "Head"}); // יצירת בחירת סוג
        libUserField = new JTextField(); // יצירת שדה משתמש
        libPassField = new JTextField(); // יצירת שדה סיסמה

        form.add(new JLabel("ID")); form.add(libIdField); // שדות מזהה
        form.add(new JLabel("Phone")); form.add(libPhoneField); // שדות טלפון
        form.add(new JLabel("First")); form.add(libFirstField); // שדות שם פרטי

        form.add(new JLabel("Last")); form.add(libLastField); // שדות שם משפחה
        form.add(new JLabel("Type")); form.add(libTypeBox); // שדות סוג

        JButton addBtn = new JButton("Add Librarian"); // יצירת כפתור הוספה
        addBtn.addActionListener(e -> addLibrarian()); // פעולה לכפתור
        form.add(addBtn); // הוספת כפתור

        form.add(new JLabel("User")); form.add(libUserField); // שדות משתמש
        form.add(new JLabel("Pass")); form.add(libPassField); // שדות סיסמה
        form.add(new JLabel("")); form.add(new JLabel("")); // ריווח טופס

        p.add(form, BorderLayout.SOUTH); // הוספת טופס
        return p; // החזרת פאנל
    }

    private JPanel buildBooksPanel() { // בניית כרטיסיית ספרים
        JPanel p = new JPanel(new BorderLayout()); // יצירת פאנל
        booksTable = new JTable(); // יצירת טבלה
        p.add(new JScrollPane(booksTable), BorderLayout.CENTER); // הוספת טבלה

        JPanel form = new JPanel(new GridLayout(3, 6, 6, 6)); // יצירת טופס
        form.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // שוליים פנימיים

        bookCodeField = new JTextField(); // יצירת שדה קוד
        bookNameField = new JTextField(); // יצירת שדה שם
        bookAuthorField = new JTextField(); // יצירת שדה מחבר
        bookYearField = new JTextField(); // יצירת שדה שנה
        bookPriceField = new JTextField(); // יצירת שדה מחיר
        bookFineField = new JTextField(); // יצירת שדה קנס
        bookTypeBox = new JComboBox<>(new String[]{"PrintedBook", "EBook"}); // יצירת בחירת סוג
        bookExtra1Field = new JTextField(); // יצירת שדה נוסף
        bookExtra2Field = new JTextField(); // יצירת שדה נוסף
        bookExtra3Field = new JTextField(); // יצירת שדה נוסף
        bookCategoryField = new JTextField(); // יצירת שדה קטגוריה

        form.add(new JLabel("Code")); form.add(bookCodeField); // שדות קוד
        form.add(new JLabel("Name")); form.add(bookNameField); // שדות שם
        form.add(new JLabel("Author")); form.add(bookAuthorField); // שדות מחבר

        form.add(new JLabel("Year")); form.add(bookYearField); // שדות שנה
        form.add(new JLabel("Price")); form.add(bookPriceField); // שדות מחיר
        form.add(new JLabel("Fine")); form.add(bookFineField); // שדות קנס

        form.add(new JLabel("Type")); form.add(bookTypeBox); // שדות סוג
        form.add(new JLabel("Extra1")); form.add(bookExtra1Field); // שדות נוסף
        form.add(new JLabel("Extra2")); form.add(bookExtra2Field); // שדות נוסף

        form.add(new JLabel("Extra3")); form.add(bookExtra3Field); // שדות נוסף
        form.add(new JLabel("Category")); form.add(bookCategoryField); // שדות קטגוריה

        JButton addBtn = new JButton("Add Book"); // יצירת כפתור הוספה
        addBtn.addActionListener(e -> addBook()); // פעולה לכפתור
        form.add(addBtn); // הוספת כפתור

        p.add(form, BorderLayout.SOUTH); // הוספת טופס
        return p; // החזרת פאנל
    }

    private JPanel buildAssignPanel() { // בניית כרטיסיית שיוך
        JPanel p = new JPanel(new BorderLayout()); // יצירת פאנל
        JPanel form = new JPanel(new GridLayout(2, 3, 8, 8)); // יצירת טופס
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // שוליים פנימיים

        assignLibIdField = new JTextField(); // יצירת שדה ספרן
        assignBookCodeField = new JTextField(); // יצירת שדה ספר

        form.add(new JLabel("Librarian ID")); form.add(assignLibIdField); // שדות ספרן
        JButton assignBtn = new JButton("Assign"); // יצירת כפתור שיוך
        assignBtn.addActionListener(e -> assignBook()); // פעולה לכפתור
        form.add(assignBtn); // הוספת כפתור

        form.add(new JLabel("Book Code")); form.add(assignBookCodeField); // שדות ספר
        form.add(new JLabel("")); // ריווח טופס

        p.add(form, BorderLayout.NORTH); // הוספת טופס
        return p; // החזרת פאנל
    }

    private JPanel buildFilesPanel() { // בניית כרטיסיית קבצים
        JPanel p = new JPanel(new GridLayout(3, 2, 10, 10)); // יצירת פאנל
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // שוליים פנימיים

        JButton loadLib = new JButton("Load Librarians"); // יצירת כפתור טעינה
        loadLib.addActionListener(e -> loadLibrarians()); // פעולה לכפתור

        JButton loadMem = new JButton("Load Members"); // יצירת כפתור טעינה
        loadMem.addActionListener(e -> loadMembers()); // פעולה לכפתור

        JButton saveLib = new JButton("Save Librarians"); // יצירת כפתור שמירה
        saveLib.addActionListener(e -> saveLibrarians()); // פעולה לכפתור

        JButton saveMem = new JButton("Save Members"); // יצירת כפתור שמירה
        saveMem.addActionListener(e -> saveMembers()); // פעולה לכפתור

        JButton saveBooks = new JButton("Save Books"); // יצירת כפתור שמירה
        saveBooks.addActionListener(e -> saveBooks()); // פעולה לכפתור

        JButton saveBorrows = new JButton("Save Borrows"); // יצירת כפתור שמירה
        saveBorrows.addActionListener(e -> saveBorrows()); // פעולה לכפתור

        p.add(loadLib); p.add(loadMem); // הוספת כפתורים
        p.add(saveLib); p.add(saveMem); // הוספת כפתורים
        p.add(saveBooks); p.add(saveBorrows); // הוספת כפתורים

        return p; // החזרת פאנל
    }

    private JPanel buildToolPanel() { // בניית כרטיסיית כלי עזר
        JPanel p = new JPanel(new FlowLayout()); // יצירת פאנל
        JButton calc = new JButton("Calculator"); // יצירת כפתור מחשבון
        calc.addActionListener(e -> new CalculatorFrame().setVisible(true)); // פתיחת מחשבון
        p.add(calc); // הוספת כפתור
        return p; // החזרת פאנל
    }

    private void refreshAll() { // רענון כל הטבלאות
        refreshMembersTable(); // רענון קוראים
        refreshLibrariansTable(); // רענון ספרנים
        refreshBooksTable(); // רענון ספרים
    }

    private void refreshMembersTable() { // רענון טבלת קוראים
        DefaultTableModel m = new DefaultTableModel(new String[]{"Code", "First", "Last", "Zip"}, 0); // יצירת מודל
        for (Member x : db.getMembers()) { // מעבר על קוראים
            m.addRow(new Object[]{x.getMemberCode(), x.getFirstName(), x.getLastName(), x.getZip()}); // הוספת שורה
        }
        membersTable.setModel(m); // עדכון טבלה
    }

    private void refreshLibrariansTable() { // רענון טבלת ספרנים
        DefaultTableModel m = new DefaultTableModel(new String[]{"ID", "Phone", "First", "Type"}, 0); // יצירת מודל
        ArrayList<Librarian> list = db.getLibrarians(); // שליפת ספרנים
        list.sort((a, b) -> a.getFirstName().compareTo(b.getFirstName())); // מיון לפי שם פרטי
        for (Librarian x : list) { // מעבר על ספרנים
            String type = (x instanceof HeadLibrarian) ? "Head" : "Regular"; // קביעת סוג
            m.addRow(new Object[]{x.getId(), x.getPhoneNumber(), x.getFirstName(), type}); // הוספת שורה
        }
        librariansTable.setModel(m); // עדכון טבלה
    }

    private void refreshBooksTable() { // רענון טבלת ספרים
        DefaultTableModel m = new DefaultTableModel(new String[]{"Code", "Name", "Type", "Category", "Available"}, 0); // יצירת מודל
        ArrayList<Book> list = db.getBooks(); // שליפת ספרים
        list.sort((a, b) -> a.getBookCode().compareTo(b.getBookCode())); // מיון לפי קוד
        for (Book x : list) { // מעבר על ספרים
            String type = x.getClass().getSimpleName(); // קביעת סוג
            String cat = (x.getCategory() == null) ? "" : x.getCategory().getCategoryCode(); // קביעת קטגוריה
            m.addRow(new Object[]{x.getBookCode(), x.getBookName(), type, cat, x.isAvailableToBorrow()}); // הוספת שורה
        }
        booksTable.setModel(m); // עדכון טבלה
    }

    private void addLibrarian() { // הוספת ספרן חדש
        String id = libIdField.getText().trim(); // קריאת מזהה
        String phone = libPhoneField.getText().trim(); // קריאת טלפון
        String first = libFirstField.getText().trim(); // קריאת שם
        String last = libLastField.getText().trim(); // קריאת משפחה
        String type = (String) libTypeBox.getSelectedItem(); // קריאת סוג

        if (id.isEmpty() || first.isEmpty()) { // בדיקת שדות חובה
            JOptionPane.showMessageDialog(this, "Missing data"); // הודעת שגיאה
            return;
        }

        boolean ok; // משתנה תוצאה
        if (type.equals("Head")) { // הוספת ספרן ראשי
            String user = libUserField.getText().trim(); // קריאת משתמש
            String pass = libPassField.getText().trim(); // קריאת סיסמה
            if (user.isEmpty() || pass.isEmpty()) { // בדיקת שדות חובה
                JOptionPane.showMessageDialog(this, "Missing data"); // הודעת שגיאה
                return;
            }
            ok = db.addLibrarian(new HeadLibrarian(id, first, last, phone, user, pass)); // הוספת ראשי
        } else { // הוספת ספרן רגיל
            ok = db.addLibrarian(new Librarian(id, first, last, phone)); // הוספת רגיל
        }

        if (!ok) { // טיפול כשל הוספה
            JOptionPane.showMessageDialog(this, "Cannot add"); // הודעת שגיאה
            return;
        }

        refreshLibrariansTable(); // רענון טבלה
    }

    private void addBook() { // הוספת ספר חדש
        try { // ניסיון קליטה
            String code = bookCodeField.getText().trim(); // קריאת קוד
            String name = bookNameField.getText().trim(); // קריאת שם
            String author = bookAuthorField.getText().trim(); // קריאת מחבר
            int year = Integer.parseInt(bookYearField.getText().trim()); // קריאת שנה
            double price = Double.parseDouble(bookPriceField.getText().trim()); // קריאת מחיר
            double fine = Double.parseDouble(bookFineField.getText().trim()); // קריאת קנס
            String type = (String) bookTypeBox.getSelectedItem(); // קריאת סוג
            String catCode = bookCategoryField.getText().trim(); // קריאת קטגוריה

            Book b; // משתנה ספר
            if (type.equals("PrintedBook")) { // יצירת ספר מודפס
                int pages = Integer.parseInt(bookExtra1Field.getText().trim()); // קריאת עמודים
                boolean hard = bookExtra2Field.getText().trim().equalsIgnoreCase("true"); // קריאת כריכה
                double addFine = Double.parseDouble(bookExtra3Field.getText().trim()); // קריאת תוספת
                b = new PrintedBook(code, name, author, year, price, fine, pages, hard, addFine); // בניית מודפס
            } else { // יצירת ספר דיגיטלי
                double mb = Double.parseDouble(bookExtra1Field.getText().trim()); // קריאת גודל
                String format = bookExtra2Field.getText().trim(); // קריאת פורמט
                int days = Integer.parseInt(bookExtra3Field.getText().trim()); // קריאת ימים
                b = new EBook(code, name, author, year, price, fine, mb, format, days, 0); // בניית דיגיטלי
            }

            boolean ok = db.addBook(b); // הוספת ספר
            if (!catCode.isEmpty()) { // שיוך קטגוריה
                Category c = db.findCategoryByCode(catCode); // חיפוש קטגוריה
                if (c == null) db.addCategory(new Category(catCode, catCode)); // יצירת קטגוריה
                db.assignBookToCategory(code, catCode); // שיוך ספר
            }

            if (!ok) { // טיפול כשל הוספה
                JOptionPane.showMessageDialog(this, "Cannot add"); // הודעת שגיאה
                return;
            }

            refreshBooksTable(); // רענון טבלה

        } catch (Exception ex) { // טיפול שגיאה כללית
            JOptionPane.showMessageDialog(this, "Invalid data"); // הודעת שגיאה
        }
    }

    private void assignBook() { // שיוך ספר לספרן
        String libId = assignLibIdField.getText().trim(); // קריאת ספרן
        String bookCode = assignBookCodeField.getText().trim(); // קריאת ספר
        boolean ok = db.assignBookToLibrarian(bookCode, libId); // ביצוע שיוך
        if (!ok) JOptionPane.showMessageDialog(this, "Cannot assign"); // הודעת שגיאה
        refreshBooksTable(); // רענון טבלה
    }

    private void loadLibrarians() { // טעינת ספרנים מקובץ
        try { // ניסיון טעינה
            db.loadLibrariansFromFile(); // קריאת ספרנים
            refreshLibrariansTable(); // רענון טבלה
            JOptionPane.showMessageDialog(this, "Loaded successfully"); // הודעת הצלחה
        } catch (FileException e) { // טיפול שגיאת קובץ
            JOptionPane.showMessageDialog(this, e.getMessage()); // הצגת שגיאה מדויקת
        }
    }

    private void loadMembers() { // טעינת קוראים מקובץ
        try { // ניסיון טעינה
            db.loadMembersFromFile(); // קריאת קוראים
            refreshMembersTable(); // רענון טבלה
            JOptionPane.showMessageDialog(this, "Loaded successfully"); // הודעת הצלחה
        } catch (FileException e) { // טיפול שגיאת קובץ
            JOptionPane.showMessageDialog(this, e.getMessage()); // הצגת שגיאה מדויקת
        }
    }

    private void saveLibrarians() { // שמירת ספרנים לקובץ
        try { // ניסיון שמירה
            db.exportLibrariansToFile(); // כתיבת ספרנים
            JOptionPane.showMessageDialog(this, "Saved successfully"); // הודעת הצלחה
        } catch (FileException e) { // טיפול שגיאת קובץ
            JOptionPane.showMessageDialog(this, e.getMessage()); // הצגת שגיאה מדויקת
        }
    }

    private void saveMembers() { // שמירת קוראים לקובץ
        try { // ניסיון שמירה
            db.exportMembersToFile(); // כתיבת קוראים
            JOptionPane.showMessageDialog(this, "Saved successfully"); // הודעת הצלחה
        } catch (FileException e) { // טיפול שגיאת קובץ
            JOptionPane.showMessageDialog(this, e.getMessage()); // הצגת שגיאה מדויקת
        }
    }

    private void saveBooks() { // שמירת ספרים לקובץ
        try { // ניסיון שמירה
            db.exportBooksToFile(); // כתיבת ספרים
            JOptionPane.showMessageDialog(this, "Saved successfully"); // הודעת הצלחה
        } catch (FileException e) { // טיפול שגיאת קובץ
            JOptionPane.showMessageDialog(this, e.getMessage()); // הצגת שגיאה מדויקת
        }
    }

    private void saveBorrows() { // שמירת השאלות לקובץ
        try { // ניסיון שמירה
            db.exportBorrowsToFile(); // כתיבת השאלות
            JOptionPane.showMessageDialog(this, "Saved successfully"); // הודעת הצלחה
        } catch (FileException e) { // טיפול שגיאת קובץ
            JOptionPane.showMessageDialog(this, e.getMessage()); // הצגת שגיאה מדויקת
        }
    }
}