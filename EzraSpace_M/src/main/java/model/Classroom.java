package model;

/**
 * Represents a single classroom.
 * Includes room name, availability status, and optional note.
 */
public class Classroom {
    private String name;
    private boolean isAvailable;
    private String note;

    public Classroom(String name, boolean isAvailable) {
        this.name = name;
        this.isAvailable = isAvailable;
        this.note = "";
    }

    public String getName() { return name; }
    public boolean isAvailable() { return isAvailable; }
    public String getNote() { return note; }

    public void setAvailable(boolean available) { isAvailable = available; }
    public void setNote(String note) { this.note = note; }

    /**
     * Returns the status in Hebrew ("פנוי" = Available, "תפוס" = Occupied).
     */
    public String getStatus() {
        return isAvailable ? "פנוי" : "תפוס";
    }

    /**
     * CSV line for file saving.
     */
    @Override
    public String toString() {
        return name + "," + isAvailable + "," + note;
    }

    /**
     * Parses a Classroom from a CSV line.
     */
    public static Classroom fromCSV(String line) {
        String[] parts = line.split(",", -1);
        return new Classroom(parts[0], Boolean.parseBoolean(parts[1]));
    }
}
