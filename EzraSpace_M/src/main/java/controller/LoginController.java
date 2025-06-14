package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Manager;
import model.Student;
import model.User;
import util.SessionManager;
import util.UserManager;

import java.io.IOException;

/**
 * LoginController handles login and sign-up functionality,
 * as well as auto-login if a session was saved previously.
 */
public class LoginController {
    // Fields for user input
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ChoiceBox<String> userTypeChoiceBox;

    // Business logic helper
    private final UserManager userManager = new UserManager();

    /**
     * Initialize the UI components and check if a user was previously logged in.
     */
    @FXML
    public void initialize() {
        userTypeChoiceBox.getItems().addAll("Student", "Manager");
        userTypeChoiceBox.setValue("Student");

        // Auto-login last user if present (loaded after scene attaches)
        javafx.application.Platform.runLater(() -> {
            String lastUser = SessionManager.loadLastUser();
            if (lastUser != null) {
                User user = userManager.findUserByUsername(lastUser);
                if (user != null) {
                    loadDashboard(user); // direct to dashboard
                }
            }
        });
    }

    /**
     * Handle user sign-up: validate inputs and add to user manager.
     */
    @FXML
    public void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String type = userTypeChoiceBox.getValue();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Username and password cannot be empty.");
            return;
        }

        User newUser = "Student".equals(type) ? new Student(username, password)
                : new Manager(username, password);

        if (userManager.addUser(newUser)) {
            showAlert(Alert.AlertType.INFORMATION, "User registered successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Username already exists.");
        }
    }

    /**
     * Handle user sign-in: check credentials and load dashboard if correct.
     */
    @FXML
    public void handleSignIn() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (userManager.validateCredentials(username, password)) {
            User u = userManager.findUserByUsername(username);
            loadDashboard(u);
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid username or password.");
        }
    }

    /**
     * Common logic to load either Student or Manager dashboard.
     */
    private void loadDashboard(User u) {
        SessionManager.saveLastUser(u.getUsername());
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            FXMLLoader loader;
            if (u instanceof Manager) {
                loader = new FXMLLoader(getClass().getResource("/view/ManagerDashboard.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/view/StudentDashboard.fxml"));
            }
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Ezra Space - Dashboard");
            stage.show();

            // Show welcome message when loading dashboard
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Welcome back, " + u.getUsername() + "!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to show alerts.
     */
    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
