package util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LogManager:
 *  Handles logging of actions to a CSV log file.
 *  Appends logs with a timestamp, action, room name, and details.
 */
public class LogManager {

    /**
     * Logs an action to the log file.
     * @param action Action performed (e.g. "Add Room", "Remove Booking").
     * @param roomName Name of the room associated with the action.
     * @param details Additional details for the log entry.
     */
    public static void log(String action, String roomName, String details) {
        File file = new File(Config.LOG_FILE);
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            pw.println(time + "," + action + "," + roomName + "," + details);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
