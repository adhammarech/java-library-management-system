package utils;

public class FileException extends Exception { // חריגה עבור קבצים

    public FileException(String message) { // בנאי הודעת שגיאה
        super(message);
    }

    public FileException(String message, Throwable cause) { // בנאי הודעה וגורם
        super(message, cause);
    }
}