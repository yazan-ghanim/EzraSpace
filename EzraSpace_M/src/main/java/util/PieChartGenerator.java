package util;

import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Classroom;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.List;

/**
 * PieChartGenerator:
 *  Creates and shows a pie chart displaying the status of classrooms (available vs occupied).
 *  Uses JFreeChart to generate the pie chart and integrates it into JavaFX using a SwingNode.
 */
public class PieChartGenerator {

    /**
     * Displays a pie chart in a new window, summarizing available and occupied classrooms.
     * @param classrooms List of classrooms to analyze.
     */
    public static void showChart(List<Classroom> classrooms) {
        long occupied = classrooms.stream().filter(c -> !c.isAvailable()).count();
        long available = classrooms.size() - occupied;

        // Dataset for the pie chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Available", available);
        dataset.setValue("Occupied", occupied);

        // Create the pie chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Classroom Status", dataset, true, true, false);

        // Put chart in a Swing panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 400));

        // Embed the Swing chart in JavaFX using SwingNode
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(chartPanel);

        // Create a JavaFX scene and show the chart in a new window
        StackPane root = new StackPane(swingNode);
        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Classroom Status Pie Chart");
        stage.setScene(scene);
        stage.show();
    }
}
