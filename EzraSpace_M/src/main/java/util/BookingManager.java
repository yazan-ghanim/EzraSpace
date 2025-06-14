package util;

import model.Booking;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingManager class:
 * 📌 Handles CRUD operations for room bookings.
 * 📌 Bookings are saved to a CSV file (persistence).
 * 📌 Prevents overlapping bookings and multiple bookings on the same day.
 */
public class BookingManager {

    private static final String BOOKINGS_FILE = "src/main/resources/bookings.csv";

    /**
     * Adds a booking to the CSV file.
     */
    public void addBooking(Booking booking) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKINGS_FILE, true))) {
            pw.println(booking.toCSV());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all bookings from the CSV.
     */
    public List<Booking> loadAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) return bookings;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 5) {
                    String username = parts[0];
                    String room = parts[1];
                    LocalDate date = LocalDate.parse(parts[2]);
                    int startHour = Integer.parseInt(parts[3]);
                    int endHour = Integer.parseInt(parts[4]);
                    bookings.add(new Booking(username, room, date, startHour, endHour));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Checks if a user has already booked a room today.
     */
    public boolean hasBookingToday(String username, LocalDate date) {
        List<Booking> bookings = loadAllBookings();
        return bookings.stream()
                .anyMatch(b -> b.getUsername().equals(username) && b.getDate().equals(date));
    }

    /**
     * Checks if the time slot overlaps with existing bookings for a room.
     */
    public boolean isOverlapping(String room, LocalDate date, int start, int end) {
        List<Booking> bookings = loadAllBookings();
        return bookings.stream()
                .anyMatch(b -> b.getRoom().equals(room)
                        && b.getDate().equals(date)
                        && !(end <= b.getStartHour() || start >= b.getEndHour()));
    }

    /**
     * Removes a booking by its display string.
     */
    public void removeBooking(String bookingLine) {
        // Convert the display line back to Booking
        Booking toRemove = parseDisplayLine(bookingLine);
        if (toRemove == null) return;

        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) return;

        List<String> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals(toRemove.toCSV())) {  // Remove the exact CSV line
                    bookings.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String b : bookings) {
                pw.println(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper to parse the display string back to a Booking object.
     */
    private Booking parseDisplayLine(String displayLine) {
        try {
            // Expected format: "student1 booked Room1 on 2025-06-10 from 10:00 to 12:00"
            String[] parts = displayLine.split(" booked | on | from | to ");
            String username = parts[0];
            String room = parts[1];
            LocalDate date = LocalDate.parse(parts[2].trim());
            int start = Integer.parseInt(parts[3].replace(":00", "").trim());
            int end = Integer.parseInt(parts[4].replace(":00", "").trim());
            return new Booking(username, room, date, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
