package controller;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Classroom;
import org.jfree.chart.ChartPanel;
import util.FileManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.List;

/**
 * Controller for the pie chart window.
 * It loads classroom data and displays a pie chart
 * showing the number of available and occupied rooms.
 */
public class PieChartController {

    @FXML private SwingNode chartSwingNode;  // A JavaFX node to embed Swing content (the chart)

    /**
     * Initialize the pie chart when the window is loaded.
     */
    @FXML
    public void initialize() {
        // Load classroom data
        List<Classroom> classrooms = FileManager.loadClassrooms();
        long available = classrooms.stream().filter(Classroom::isAvailable).count();
        long occupied = classrooms.size() - available;

        // Create dataset for the chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Available", available);
        dataset.setValue("Occupied", occupied);

        // Create pie chart using JFreeChart
        JFreeChart chart = ChartFactory.createPieChart(
                "Classrooms Status",
                dataset,
                true,
                true,
                false
        );

        // Customize chart colors
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Available", new java.awt.Color(0, 255, 0)); // Green for available
        plot.setSectionPaint("Occupied", new java.awt.Color(255, 0, 0)); // Red for occupied

        // Embed the chart in the SwingNode (thread-safe call)
        SwingUtilities.invokeLater(() -> {
            ChartPanel chartPanel = new ChartPanel(chart);
            chartSwingNode.setContent(chartPanel);
        });
    }

    /**
     * Close the pie chart window.
     */
    @FXML
    private void close() {
        Stage stage = (Stage) chartSwingNode.getScene().getWindow();
        stage.close();
    }
}
