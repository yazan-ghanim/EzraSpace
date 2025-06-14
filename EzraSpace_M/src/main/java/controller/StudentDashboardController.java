package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Booking;
import model.Classroom;
import util.BookingManager;
import util.ClassroomManager;
import util.SessionManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Student Dashboard.
 * Allows students to view available rooms, see available timeslots,
 * and book a classroom for a specific hour.
 */
public class StudentDashboardController {

    @FXML private ListView<Object> roomsListView;               // Shows the list of classrooms
    @FXML private ListView<String> availableTimesListView;     // Shows available time slots for the selected room
    @FXML private DatePicker datePicker;                       // To choose a booking date
    @FXML private Button bookButton, logoutButton;             // Buttons for booking and logout
    @FXML private Label statusLabel;                           // Status message display

    private final ClassroomManager classroomManager = new ClassroomManager();
    private final BookingManager bookingManager = new BookingManager();
    private List<Classroom> classrooms;
    private String currentStudentUsername;

    /**
     * Initializes the dashboard:
     * - Loads current user
     * - Loads classroom data
     * - Sets up event handlers for room and date selection
     */
    @FXML
    public void initialize() {
        currentStudentUsername = SessionManager.loadLastUser();
        classrooms = classroomManager.load();
        updateRoomsList();

        // Limit date selection to the next 14 days
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                LocalDate maxDate = today.plusDays(14);
                setDisable(empty || date.isBefore(today) || date.isAfter(maxDate));
            }
        });
        datePicker.setValue(LocalDate.now());

        // Show available times when a room is selected
        roomsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateAvailableTimes();
            }
        });

        bookButton.setOnAction(e -> doBooking());
        logoutButton.setOnAction(e -> handleLogout());
    }

    /**
     * Updates the rooms list, showing their status (available/occupied).
     */
    private void updateRoomsList() {
        var displayList = FXCollections.observableArrayList();
        for (var c : classrooms) {
            String status = c.isAvailable() ? "Available" : "Occupied";
            String colorStatus = c.isAvailable() ? "✅ " : "❌ ";
            displayList.add(colorStatus + c.getName() + " (" + status + ")");
        }
        roomsListView.setItems(displayList);
    }

    /**
     * Updates the available time slots list for the selected room and date.
     */
    private void updateAvailableTimes() {
        int idx = roomsListView.getSelectionModel().getSelectedIndex();
        if (idx < 0) return;

        String roomName = classrooms.get(idx).getName();
        LocalDate date = datePicker.getValue();
        List<String> availableTimes = new ArrayList<>();

        // Generate possible 1-hour slots from 8:00 to 18:00
        for (int h = 8; h < 18; h++) {
            int start = h;
            int end = h + 1;
            if (!bookingManager.isOverlapping(roomName, date, start, end)) {
                availableTimes.add(start + ":00 - " + end + ":00");
            }
        }

        availableTimesListView.setItems(FXCollections.observableArrayList(availableTimes));
    }

    /**
     * Handles the booking action.
     * Ensures no overlapping bookings and limits booking to one room/day.
     */
    private void doBooking() {
        int roomIdx = roomsListView.getSelectionModel().getSelectedIndex();
        String timeSlot = availableTimesListView.getSelectionModel().getSelectedItem();
        LocalDate date = datePicker.getValue();

        if (roomIdx < 0 || timeSlot == null || date == null) {
            statusLabel.setText("⚠️ Select room, time slot and date!");
            return;
        }

        String room = classrooms.get(roomIdx).getName();
        String[] parts = timeSlot.split(" - ");
        int startHour = Integer.parseInt(parts[0].replace(":00", "").trim());
        int endHour = Integer.parseInt(parts[1].replace(":00", "").trim());

        // Check if the student already has a booking today
        if (bookingManager.hasBookingToday(currentStudentUsername, date)) {
            statusLabel.setText("⚠️ You already booked today!");
            return;
        }

        // Add new booking
        Booking booking = new Booking(currentStudentUsername, room, date, startHour, endHour);
        bookingManager.addBooking(booking);
        statusLabel.setText("✅ Booked " + room + " from " + startHour + ":00 to " + endHour + ":00");

        // Refresh available times
        updateAvailableTimes();
    }

    /**
     * Handles the logout process.
     */
    private void handleLogout() {
        SessionManager.clearSession();
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            var loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            stage.setScene(new javafx.scene.Scene(loader.load()));
            stage.setTitle("Ezra Space - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
