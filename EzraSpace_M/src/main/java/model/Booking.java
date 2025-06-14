package model;

import java.time.LocalDate;

/**
 * Represents a single booking made by a user for a classroom.
 * Includes username, room, date, and booking times.
 */
public class Booking {
    private String username;
    private String room;
    private LocalDate date;
    private int startHour;
    private int endHour;

    public Booking(String username, String room, LocalDate date, int startHour, int endHour) {
        this.username = username;
        this.room = room;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getUsername() { return username; }
    public String getRoom() { return room; }
    public LocalDate getDate() { return date; }
    public int getStartHour() { return startHour; }
    public int getEndHour() { return endHour; }

    /**
     * Converts booking to a CSV line for file saving.
     */
    public String toCSV() {
        return username + "," + room + "," + date + "," + startHour + "," + endHour;
    }

    /**
     * Parses a booking from a CSV line.
     */
    public static Booking fromCSV(String line) {
        String[] parts = line.split(",", -1);
        return new Booking(parts[0], parts[1], LocalDate.parse(parts[2]),
                Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
    }

    /**
     * Compact string for manager’s booking list.
     */
    public String toDisplayString() {
        return String.format("[%s] %s booked %s on %s from %02d:00 to %02d:00",
                room, username, room, date, startHour, endHour);
    }

    /**
     * Default string for display (used by default in ListView).
     */
    @Override
    public String toString() {
        return username + " booked " + room + " on " + date + " from " + startHour + ":00 to " + endHour + ":00";
    }
}
