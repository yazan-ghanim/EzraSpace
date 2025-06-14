package util;

import model.Classroom;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager:
 * 📌 Implements CsvManager interface for Classroom data.
 * 📌 Handles loading and saving classrooms data from/to CSV file.
 */
public class FileManager implements CsvManager<Classroom> {

    private static final String FILE_PATH = "src/main/resources/classrooms.csv";

    @Override
    public List<Classroom> load() {
        return loadClassrooms();
    }

    @Override
    public void save(List<Classroom> data) {
        saveClassrooms(data);
    }

    /**
     * Load classrooms from CSV.
     * @return List of Classroom objects.
     */
    public static List<Classroom> loadClassrooms() {
        List<Classroom> classrooms = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return classrooms;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 2) {
                    String name = parts[0];
                    boolean available = Boolean.parseBoolean(parts[1]);
                    classrooms.add(new Classroom(name, available));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classrooms;
    }

    /**
     * Save classrooms to CSV.
     * @param classrooms List of classrooms to save.
     */
    public static void saveClassrooms(List<Classroom> classrooms) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Classroom c : classrooms) {
                writer.println(c.getName() + "," + c.isAvailable());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
