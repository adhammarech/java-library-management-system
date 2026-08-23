package ui;

import javax.swing.*;
import java.awt.*;

public class CalculatorFrame extends JFrame { // מסך מחשבון פשוט

    private JTextField display; // שדה תצוגה
    private String current; // שמירת ערך נוכחי
    private String op; // שמירת פעולה
    private double first; // שמירת מספר ראשון

    public CalculatorFrame() { // בנאי מחשבון
        current = ""; // אתחול ערך
        op = ""; // אתחול פעולה
        first = 0; // אתחול מספר
        initFrame(); // אתחול חלון
        initUI(); // אתחול ממשק
    }

    private void initFrame() { // הגדרות חלון
        setTitle("Calculator");
        setSize(260, 320);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initUI() { // בניית רכיבים
        display = new JTextField(); // יצירת שדה תצוגה
        display.setEditable(false); // חסימת עריכה
        add(display, BorderLayout.NORTH); // הוספת תצוגה

        JPanel grid = new JPanel(new GridLayout(4, 3, 6, 6)); // לוח כפתורים
        String[] keys = {"1","2","3","4","5","6","7","8","9","0","C","="}; // טקסט כפתורים
        for (String k : keys) { // יצירת כפתורים
            JButton b = new JButton(k); // יצירת כפתור
            b.addActionListener(e -> press(k)); // פעולה לכפתור
            grid.add(b); // הוספת כפתור
        }

        JPanel ops = new JPanel(new GridLayout(1, 3, 6, 6)); // כפתורי פעולה
        JButton plus = new JButton("+"); // כפתור חיבור
        plus.addActionListener(e -> setOp("+")); // פעולה לכפתור
        JButton blue = new JButton("Blue"); // כפתור צבע כחול
        blue.addActionListener(e -> display.setBackground(Color.CYAN)); // שינוי צבע
        JButton red = new JButton("Red"); // כפתור צבע אדום
        red.addActionListener(e -> display.setBackground(Color.PINK)); // שינוי צבע

        ops.add(plus); ops.add(blue); ops.add(red); // הוספת כפתורים

        add(grid, BorderLayout.CENTER); // הוספת לוח
        add(ops, BorderLayout.SOUTH); // הוספת פעולות
    }

    private void press(String k) { // טיפול לחיצה מספר
        if (k.equals("C")) { // ניקוי מחשבון
            current = "";
            op = "";
            first = 0;
            display.setText("");
            return;
        }
        if (k.equals("=")) { // חישוב תוצאה
            if (!op.equals("+")) return;
            double second = current.isEmpty() ? 0 : Double.parseDouble(current); // קריאת מספר שני
            double res = first + second; // ביצוע חיבור
            display.setText(String.valueOf(res)); // הצגת תוצאה
            current = ""; // איפוס ערך
            op = ""; // איפוס פעולה
            first = 0; // איפוס מספר
            return;
        }
        current += k; // צבירת ספרות
        display.setText(current); // הצגת ערך
    }

    private void setOp(String operation) { // קביעת פעולה
        if (current.isEmpty()) return; // בדיקת ערך ריק
        first = Double.parseDouble(current); // שמירת מספר ראשון
        current = ""; // איפוס ערך
        op = operation; // שמירת פעולה
        display.setText(""); // ניקוי תצוגה
    }
}