package model;

public class HeadLibrarian extends Librarian { // מחלקת ספרן ראשי

    private String username; // שם משתמש
    private String password; // סיסמה

    public HeadLibrarian(String id, String firstName, String lastName, String phoneNumber, String username, String password) { // בנאי ספרן ראשי
        super(id, firstName, lastName, phoneNumber);
        this.username = username;
        this.password = password;
    }

    public String getUsername() { // החזרת שם משתמש
        return username;
    }

    public String getPassword() { // החזרת סיסמה
        return password;
    }

    public boolean login(String u, String p) { // בדיקת התחברות ראשי
        return username.equals(u) && password.equals(p);
    }
}