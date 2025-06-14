package View;

import controller.ClassroomController;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Classroom;
import util.PieChartGenerator;

/**
 * MainView:
 *  Main classroom management interface for managers.
 *  Displays a table of classrooms (with name & status columns).
 *  Includes search functionality to filter classrooms.
 *  Buttons to toggle classroom status, refresh, and show pie chart.
 *  Uses a border pane with search at the top and table at the center.
 */
public class MainView {
    private final ClassroomController controller;

    public MainView(Stage primaryStage) {
        controller = new ClassroomController();

        // TableView for classrooms
        TableView<Classroom> tableView = new TableView<>();
        TableColumn<Classroom, String> nameCol = new TableColumn<>("כיתה");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Classroom, String> statusCol = new TableColumn<>("סטטוס");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.getColumns().addAll(nameCol, statusCol);

        // Search bar with filter
        TextField searchField = new TextField();
        searchField.setPromptText("חפש כיתה...");
        FilteredList<Classroom> filteredList = new FilteredList<>(controller.getClassrooms(), p -> true);
        searchField.textProperty().addListener((obs, oldText, newText) -> {
            filteredList.setPredicate(classroom -> {
                if (newText == null || newText.isEmpty()) {
                    return true;
                }
                return classroom.getName().toLowerCase().contains(newText.toLowerCase());
            });
        });
        tableView.setItems(filteredList);

        // Buttons for actions
        Button toggleBtn = new Button("שנה סטטוס");
        toggleBtn.setOnAction(e -> {
            Classroom selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                controller.toggleClassroomStatus(selected);
                tableView.refresh();
            }
        });

        Button refreshBtn = new Button("רענן");
        refreshBtn.setOnAction(e -> {
            controller.refresh();
            tableView.refresh();
        });

        Button chartBtn = new Button("הצג גרף עוגה");
        chartBtn.setOnAction(e -> PieChartGenerator.showChart(controller.getClassrooms()));

        // Layout setup
        HBox buttonBox = new HBox(10, toggleBtn, refreshBtn, chartBtn);
        VBox topBox = new VBox(10, searchField, buttonBox);

        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(tableView);

        // Scene setup
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("Ezra Space - ניהול כיתות");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
