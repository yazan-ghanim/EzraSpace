package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Booking;
import model.Classroom;
import util.BookingManager;
import util.ClassroomManager;
import util.LogManager;
import util.SessionManager;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the manager's dashboard.
 * Manages classroom and booking data, updates UI lists, and handles user actions.
 */
public class ManagerDashboardController {

    // UI elements
    @FXML private ListView<String> classroomsListView;
    @FXML private ListView<String> bookingsListView;
    @FXML private TextField newRoomField;
    @FXML private Button addRoomButton, removeRoomButton, removeBookingButton, logoutButton, openChartButton;

    // Managers to handle data logic
    private final ClassroomManager classroomManager = new ClassroomManager();
    private final BookingManager bookingManager = new BookingManager();
    private List<Classroom> classrooms; // Current loaded classrooms

    /**
     * Initialize the dashboard by loading classroom and booking data and setting button actions.
     */
    @FXML
    public void initialize() {
        classrooms = classroomManager.load();
        updateClassroomsList();
        updateBookingsList();

        addRoomButton.setOnAction(e -> addRoom());
        removeRoomButton.setOnAction(e -> removeRoom());
        removeBookingButton.setOnAction(e -> removeBooking());
        logoutButton.setOnAction(e -> handleLogout());
        openChartButton.setOnAction(e -> openPieChart());
    }

    /**
     * Update the classrooms list view with current data (with status icons).
     */
    private void updateClassroomsList() {
        ObservableList<String> displayList = FXCollections.observableArrayList();
        for (var c : classrooms) {
            String status = c.isAvailable() ? "Available" : "Occupied";
            String colorStatus = c.isAvailable() ? "✅ " : "❌ ";
            displayList.add(colorStatus + c.getName() + " (" + status + ")");
        }
        classroomsListView.setItems(displayList);
    }

    /**
     * Update the bookings list view to show all current bookings.
     */
    private void updateBookingsList() {
        List<Booking> bookings = bookingManager.loadAllBookings();
        ObservableList<String> displayList = FXCollections.observableArrayList();
        for (var b : bookings) {
            displayList.add(b.toString());
        }
        bookingsListView.setItems(displayList);
    }

    /**
     * Add a new room to the system (with checks for empty name and duplicates).
     */
    private void addRoom() {
        String name = newRoomField.getText().trim();
        if (name.isEmpty()) {
            showAlert("Room name cannot be empty.");
            return;
        }
        if (classroomManager.roomExists(name)) {
            showAlert("Room already exists.");
            return;
        }

        classroomManager.addRoom(name);
        LogManager.log("Added Room", name, "");
        classrooms = classroomManager.load();  // Refresh classroom data
        updateClassroomsList();
        newRoomField.clear();
    }

    /**
     * Remove a selected room from the system.
     */
    private void removeRoom() {
        int idx = classroomsListView.getSelectionModel().getSelectedIndex();
        if (idx == -1) {
            showAlert("Select a room to remove.");
            return;
        }

        String name = classrooms.get(idx).getName();
        classroomManager.removeRoom(name);
        LogManager.log("Removed Room", name, "");
        classrooms = classroomManager.load();
        updateClassroomsList();
    }

    /**
     * Remove a selected booking from the system.
     */
    private void removeBooking() {
        int idx = bookingsListView.getSelectionModel().getSelectedIndex();
        if (idx == -1) {
            showAlert("Select a booking to remove.");
            return;
        }

        String bookingLine = bookingsListView.getItems().get(idx);
        bookingManager.removeBooking(bookingLine);
        LogManager.log("Removed Booking", bookingLine, "");
        updateBookingsList();
    }

    /**
     * Open a pie chart window to visualize room availability.
     */
    private void openPieChart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PieChartView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Classrooms Pie Chart");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle logout: clear session data and return to login view.
     */
    private void handleLogout() {
        SessionManager.clearSession();
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            var loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Ezra Space - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show an informational alert.
     */
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
