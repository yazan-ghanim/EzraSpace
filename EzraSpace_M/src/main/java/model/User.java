package model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base abstract class for users in the system.
 * - Fields: username, password
 * - Extended by: Student, Manager
 * - Includes Jackson annotations for polymorphic JSON handling.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,       // Use class name as type identifier
        property = "type"                 // Field in JSON to determine class type
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Student.class, name = "student"),
        @JsonSubTypes.Type(value = Manager.class, name = "manager")
})
public abstract class User {
    protected String username;
    protected String password;  // Note: not secure - should be hashed in real apps!

    public User() {}  // Required for JSON deserialization

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Basic getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
