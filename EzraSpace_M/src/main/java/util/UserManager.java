package util;

import model.Manager;
import model.Student;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserManager:
 * Manages user data (students and managers).
 * Implements CsvManager<User> to load/save users from/to CSV.
 * Supports adding new users, finding users by username, and validating credentials.
 */
public class UserManager implements CsvManager<User> {

    /**
     * Load all users from the CSV file.
     */
    @Override
    public List<User> load() {
        List<User> users = new ArrayList<>();
        File file = new File(Config.USERS_FILE);
        if (!file.exists()) return users;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String type = parts[2];
                    if ("1".equals(type)) { // Student
                        users.add(new Student(username, password));
                    } else if ("0".equals(type)) { // Manager
                        users.add(new Manager(username, password));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Save all users to the CSV file.
     */
    @Override
    public void save(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Config.USERS_FILE))) {
            for (User u : users) {
                String type = (u instanceof Student) ? "1" : "0";
                pw.println(u.getUsername() + "," + u.getPassword() + "," + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new user if username is unique.
     */
    public boolean addUser(User newUser) {
        List<User> users = load();
        boolean exists = users.stream().anyMatch(u -> u.getUsername().equals(newUser.getUsername()));
        if (exists) return false;

        users.add(newUser);
        save(users);
        return true;
    }

    /**
     * Find a user by username.
     */
    public User findUserByUsername(String username) {
        return load().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Validate login credentials (username & password).
     */
    public boolean validateCredentials(String username, String password) {
        User u = findUserByUsername(username);
        return u != null && u.getPassword().equals(password);
    }
}
