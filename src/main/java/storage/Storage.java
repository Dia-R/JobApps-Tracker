package storage;

import logic.Application;
import logic.Interview;
import logic.Reminder;

import java.util.List;

/**
 * Defines the contract for data persistence.
 * The logic layer relies on this interface to read and write entities,
 * ensuring it remains decoupled from the specific file or database implementation.
 */
public interface Storage {

    /** Saves a new application to the storage medium. */
    void saveApplication(Application app);

    /** Retrieves all stored applications. */
    List<Application> loadAllApplications();

    /** Updates an existing application in storage. */
    void updateApplication(Application app);

    /** Deletes an application by its unique ID. */
    void deleteApplication(String id);

    /** Saves a new interview record to storage. */
    void saveInterview(Interview interview);

    /** Retrieves all stored interview records. */
    List<Interview> loadAllInterviews();

    /** Updates an existing interview record in storage. */
    void updateInterview(Interview interview);

    /** Saves a new reminder to storage. */
    void saveReminder(Reminder reminder);

    /** Retrieves all stored reminders. */
    List<Reminder> loadAllReminders();

    /** Updates an existing reminder in storage. */
    void updateReminder(Reminder reminder);
}