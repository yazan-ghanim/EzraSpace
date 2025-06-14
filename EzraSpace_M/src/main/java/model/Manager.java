package model;

/**
 * Represents a manager in the system.
 * Inherits from User and is used to differentiate manager permissions.
 */
public class Manager extends User {
    public Manager() { super(); }
    public Manager(String username, String password) { super(username, password); }
}
