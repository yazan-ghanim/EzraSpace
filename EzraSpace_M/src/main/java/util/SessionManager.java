package util;

import java.io.*;

/**
 * SessionManager:
 *  Manages the session state of the application.
 *  Stores the username of the last logged-in user in a file.
 *  Provides methods to save, load, and clear the session data.
 */
public class SessionManager {

    /**
     * Saves the username of the last user to the session file.
     * @param username the username to save
     */
    public static void saveLastUser(String username) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Config.SESSION_FILE))) {
            pw.println(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the username of the last user from the session file.
     * @return the username, or null if no session file exists
     */
    public static String loadLastUser() {
        File file = new File(Config.SESSION_FILE);
        if (!file.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Clears the session file (used when the user logs out).
     */
    public static void clearSession() {
        File file = new File(Config.SESSION_FILE);
        if (file.exists()) file.delete();
    }
}
