package app;

import database.LibraryDataBase;
import ui.LoginFrame;

import javax.swing.SwingUtilities;

public class Main { // מחלקת הפעלה ראשית

    public static void main(String[] args) { // נקודת התחלה
        SwingUtilities.invokeLater(() -> start()); // הפעלת ממשק בטוח
    }

    private static void start() { // הפעלת מערכת גרפית
        LibraryDataBase db = new LibraryDataBase(); // יצירת בסיס נתונים
        LoginFrame f = new LoginFrame(db); // יצירת מסך התחברות
        f.setVisible(true); // הצגת חלון התחברות
    }
}