package storage;

import model.User;
import model.Student;
import model.Manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles storing and loading users from a CSV file.
 *
 * Responsibilities:
 * ✅ Load existing users from a CSV file.
 * ✅ Save users (students & managers) to the CSV.
 * ✅ Add new users (ensuring no duplicates).
 * ✅ Validate credentials for login.
 */
public class UserStorage {

    private static final String USER_FILE = "src/main/resources/users.csv";
    private List<User> users;

    public UserStorage() {
        users = loadUsers();
    }

    /**
     * Loads users from the CSV file.
     * CSV format: username,password,1 (student) or 0 (manager)
     */
    private List<User> loadUsers() {
        List<User> loadedUsers = new ArrayList<>();
        File file = new File(USER_FILE);
        if (!file.exists()) {
            return loadedUsers;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String type = parts[2];
                    if ("1".equals(type)) {
                        loadedUsers.add(new Student(username, password));
                    } else if ("0".equals(type)) {
                        loadedUsers.add(new Manager(username, password));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedUsers;
    }

    /**
     * Saves the current list of users to the CSV file.
     */
    private void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USER_FILE))) {
            for (User u : users) {
                String type = (u instanceof Student) ? "1" : "0";
                pw.println(u.getUsername() + "," + u.getPassword() + "," + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new user if username is unique.
     */
    public boolean addUser(User user) {
        if (users.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            return false;
        }
        users.add(user);
        saveUsers();
        return true;
    }

    /**
     * Finds a user by username.
     */
    public User findUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if credentials match any stored user.
     */
    public boolean validateCredentials(String username, String password) {
        User u = findUserByUsername(username);
        if (u == null) return false;
        return u.getPassword().equals(password);
    }
}
