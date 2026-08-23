package model;

public class Member { // מחלקת קורא

    private String memberCode; // קוד קורא
    private String firstName; // שם פרטי
    private String lastName; // שם משפחה
    private String street; // רחוב
    private String city; // עיר
    private String zip; // מיקוד
    private String phoneNumber; // טלפון

    public Member(String memberCode, String firstName, String lastName, String street, String city, String zip, String phoneNumber) { // בנאי קורא
        this.memberCode = memberCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }

    public String getMemberCode() { // החזרת קוד קורא
        return memberCode;
    }

    public String getFirstName() { // החזרת שם פרטי
        return firstName;
    }

    public String getLastName() { // החזרת שם משפחה
        return lastName;
    }

    public String getStreet() { // החזרת רחוב
        return street;
    }

    public String getCity() { // החזרת עיר
        return city;
    }

    public String getZip() { // החזרת מיקוד
        return zip;
    }

    public String getPhoneNumber() { // החזרת טלפון
        return phoneNumber;
    }

    public void setAddress(String address) { // עדכון כתובת מלאה
        if (address == null) return; // בדיקת ערך ריק
        String[] p = address.split(","); // פיצול כתובת לשדות
        if (p.length >= 1) street = p[0].trim(); // עדכון רחוב
        if (p.length >= 2) city = p[1].trim(); // עדכון עיר
        if (p.length >= 3) zip = p[2].trim(); // עדכון מיקוד
    }

    public void setPhoneNumber(String phoneNumber) { // עדכון טלפון
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() { // הצגת קורא מלא
        return memberCode + " " + firstName + " " + lastName + " " + street + " " + city + " " + zip + " " + phoneNumber;
    }
}