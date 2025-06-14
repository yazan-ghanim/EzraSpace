package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Classroom;
import util.FileManager;
import util.LogManager;

/**
 * Controller class to manage classrooms.
 * It provides functionality to:
 *  - Load the classrooms list
 *  - Toggle classroom status (available/occupied)
 *  - Save updates to file
 *  - Log actions
 *  - Refresh the list
 */
public class ClassroomController {

    @FXML
    // Observable list to keep track of classrooms in real time.
    private final ObservableList<Classroom> classrooms;

    // Constructor: load classrooms from file into the observable list.
    public ClassroomController() {
        classrooms = FXCollections.observableArrayList(FileManager.loadClassrooms());
    }

    // Getter to retrieve the list of classrooms.
    public ObservableList<Classroom> getClassrooms() {
        return classrooms;
    }

    // Toggle the status of a given classroom (available <-> occupied).
    public void toggleClassroomStatus(Classroom classroom) {
        classroom.setAvailable(!classroom.isAvailable());
        FileManager.saveClassrooms(classrooms); // Save changes to file.

        // Log the action of toggling status.
        String newStatus = classroom.isAvailable() ? "Available" : "Occupied";
        LogManager.log("Toggle Status", classroom.getName(), newStatus);
    }

    // Reloads the classroom list from the external file.
    public void refresh() {
        classrooms.setAll(FileManager.loadClassrooms());
    }
}
