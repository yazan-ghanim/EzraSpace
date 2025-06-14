package model;

/**
 * Represents a student in the system.
 * Inherits from User and is used to differentiate student-specific actions.
 */
public class Student extends User {
    public Student() { super(); }
    public Student(String username, String password) { super(username, password); }
}
