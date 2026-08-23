package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager { // ניהול קבצים

    public static List<String> readFile(String fileName) throws FileException { // קריאת קובץ לשורות
        List<String> lines = new ArrayList<>(); // יצירת רשימת שורות
        try (BufferedReader br = new BufferedReader(new FileReader("src/" + fileName))) { // קריאה מתוך מקור
            String line; // משתנה שורה
            while ((line = br.readLine()) != null) { // מעבר על שורות
                line = removeBom(line).trim(); // ניקוי תווים נסתרים
                if (!line.isEmpty()) { // דילוג שורה ריקה
                    lines.add(line); // הוספת שורה
                }
            }
        } catch (IOException e) { // טיפול שגיאת קריאה
            throw new FileException("שגיאה בקריאת קובץ " + fileName); // הודעת שגיאה
        }
        return lines; // החזרת שורות
    }

    public static void writeFile(String fileName, List<String> lines) throws FileException { // כתיבה לקובץ
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/" + fileName))) { // כתיבה למקור
            for (String line : lines) { // מעבר על שורות
                bw.write(line); // כתיבת שורה
                bw.newLine(); // ירידת שורה
            }
        } catch (IOException e) { // טיפול שגיאת כתיבה
            throw new FileException("שגיאה בכתיבת קובץ " + fileName); // הודעת שגיאה
        }
    }

    private static String removeBom(String s) { // הסרת תו נסתר
        if (s != null && s.length() > 0 && s.charAt(0) == '\uFEFF') { // בדיקת תו בום
            return s.substring(1); // הסרת תו בום
        }
        return s; // החזרת מחרוזת
    }
}