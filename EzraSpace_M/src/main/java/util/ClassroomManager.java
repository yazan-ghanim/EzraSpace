package util;

import model.Classroom;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassroomManager class:
 * 📌 Manages CRUD operations for classroom data (persistence in CSV).
 * 📌 Adds, removes, loads, and checks for classrooms.
 */
public class ClassroomManager {

    private static final String CLASSROOMS_FILE = "src/main/resources/classrooms.csv";

    /**
     * Loads all classrooms from the CSV file.
     */
    public List<Classroom> load() {
        List<Classroom> classrooms = new ArrayList<>();
        File file = new File(CLASSROOMS_FILE);
        if (!file.exists()) return classrooms;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
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
     * Checks if a room with the given name exists.
     */
    public boolean roomExists(String name) {
        return load().stream().anyMatch(c -> c.getName().equalsIgnoreCase(name));
    }

    /**
     * Adds a new room with the default "available" status (true).
     */
    public void addRoom(String name) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CLASSROOMS_FILE, true))) {
            pw.println(name + ",true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a room by name (exact match).
     */
    public void removeRoom(String roomName) {
        File file = new File(CLASSROOMS_FILE);
        if (!file.exists()) return;

        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Only add back lines that do not match the room name
                if (!line.startsWith(roomName + ",")) {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String l : updatedLines) {
                pw.println(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
