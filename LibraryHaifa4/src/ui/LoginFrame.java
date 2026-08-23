package ui;

import database.LibraryDataBase;
import model.HeadLibrarian;
import model.Librarian;
import model.Member;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame { // מסך התחברות ראשי

    private LibraryDataBase db; // שמירת בסיס נתונים
    private JTextField userField; // שדה שם משתמש
    private JPasswordField passField; // שדה סיסמה
    private JComboBox<String> roleBox; // בחירת תפקיד

    public LoginFrame(LibraryDataBase db) { // בנאי מסך התחברות
        this.db = db;
        initFrame(); // אתחול חלון
        initComponents(); // אתחול רכיבים
    }

    private void initFrame() { // הגדרות חלון
        setTitle("Login");
        setSize(360, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void initComponents() { // בניית טופס
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8)); // פאנל טופס
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12)); // שוליים פנימיים

        form.add(new JLabel("Username:")); // תווית משתמש
        userField = new JTextField(); // יצירת שדה משתמש
        form.add(userField); // הוספת שדה משתמש

        form.add(new JLabel("Password:")); // תווית סיסמה
        passField = new JPasswordField(); // יצירת שדה סיסמה
        form.add(passField); // הוספת שדה סיסמה

        form.add(new JLabel("Role:")); // תווית תפקיד
        roleBox = new JComboBox<>(new String[]{"HeadLibrarian", "Librarian", "Member"}); // בחירת תפקיד
        form.add(roleBox); // הוספת בחירה

        JButton loginBtn = new JButton("Login"); // כפתור התחברות
        loginBtn.addActionListener(e -> doLogin()); // פעולה לכפתור

        add(form, BorderLayout.CENTER); // הוספת טופס
        add(loginBtn, BorderLayout.SOUTH); // הוספת כפתור
    }

    private void doLogin() { // ביצוע התחברות
        String username = userField.getText().trim(); // קריאת שם משתמש
        String password = new String(passField.getPassword()); // קריאת סיסמה
        String role = (String) roleBox.getSelectedItem(); // קריאת תפקיד

        if (role.equals("HeadLibrarian")) { // התחברות ספרן ראשי
            HeadLibrarian hl = db.loginHeadLibrarian(username, password); // בדיקת התחברות
            if (hl == null) { // טיפול כשל התחברות
                JOptionPane.showMessageDialog(this, "Login failed");
                return;
            }
            new HeadLibrarianFrame(db, hl).setVisible(true); // פתיחת מסך ראשי
            dispose(); // סגירת מסך נוכחי
            return;
        }

        if (role.equals("Librarian")) { // התחברות ספרן רגיל
            Librarian l = db.findLibrarianById(username); // שימוש במזהה כשם משתמש
            if (l == null || l instanceof HeadLibrarian) { // בדיקת תקינות ספרן
                JOptionPane.showMessageDialog(this, "Login failed");
                return;
            }
            new LibrarianFrame(db, l).setVisible(true); // פתיחת מסך ספרן
            dispose(); // סגירת מסך נוכחי
            return;
        }

        Member m = db.findMemberByCode(username); // התחברות קורא לפי קוד
        if (m == null) { // טיפול כשל התחברות
            JOptionPane.showMessageDialog(this, "Login failed");
            return;
        }
        new MemberFrame(db, m).setVisible(true); // פתיחת מסך קורא
        dispose(); // סגירת מסך נוכחי
    }
}