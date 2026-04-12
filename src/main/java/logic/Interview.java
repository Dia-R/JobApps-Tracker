package logic;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a scheduled interview for a specific job application.
 * Tracks the interview round, scheduled date, and any notes from the session.
 */
public class Interview {
    private final String id;
    private final String applicationId;
    private final int round;
    private LocalDateTime date;
    private String notes;

    /**
     * Standard constructor for creating a new interview record.
     * Automatically generates a unique identifier for the interview.
     *
     * @param applicationId The unique ID of the parent job application.
     * @param round         The interview round number (e.g., 1 for first round).
     * @param date          The scheduled date and time for the interview.
     */
    public Interview(String applicationId, int round, LocalDateTime date) {
        this.id = UUID.randomUUID().toString();
        this.applicationId = applicationId;
        this.round = round;
        this.date = date;
        this.notes = "";
    }

    /**
     * Full constructor used primarily by the storage layer when loading existing records from disk.
     *
     * @param id            The unique identifier of the interview.
     * @param applicationId The unique ID of the parent job application.
     * @param round         The interview round number.
     * @param date          The scheduled date and time for the interview.
     * @param notes         Any notes or feedback saved from the interview.
     */
    public Interview(String id, String applicationId, int round, LocalDateTime date, String notes) {
        this.id = id;
        this.applicationId = applicationId;
        this.round = round;
        this.date = date;
        this.notes = notes;
    }

    /**
     * @return The unique identifier of the interview.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The unique identifier of the associated job application.
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * @return The sequential round number of this interview.
     */
    public int getRound() {
        return round;
    }

    /**
     * @return The scheduled date and time.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @return Any custom notes or feedback associated with the interview.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Updates the custom notes for this interview.
     *
     * @param notes The new notes text to save.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Updates the scheduled date and time of the interview.
     *
     * @param date The new date and time.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}