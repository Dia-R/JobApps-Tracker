package logic;

import storage.Storage;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory Storage stub used exclusively for unit testing.
 * Implements the Storage interface without relying on file I/O operations,
 * ensuring tests run quickly and in complete isolation.
 */
public class InMemoryStorage implements Storage {
    private final List<Application> applications = new ArrayList<>();
    private final List<Interview> interviews = new ArrayList<>();
    private final List<Reminder> reminders = new ArrayList<>();

    /**
     * Saves a new application to the in-memory list.
     *
     * @param app The application to save.
     */
    @Override
    public void saveApplication(Application app) {
        this.applications.add(app);
    }

    /**
     * Retrieves a copy of all stored applications.
     * Returns a new list instance to prevent external modification of internal state.
     *
     * @return A list of all applications in memory.
     */
    @Override
    public List<Application> loadAllApplications() {
        return new ArrayList<>(this.applications);
    }

    /**
     * Updates an existing application in memory by replacing the old instance.
     *
     * @param app The application containing the updated data.
     */
    @Override
    public void updateApplication(Application app) {
        this.applications.removeIf(a -> {
            return a.getId().equals(app.getId());
        });
        this.applications.add(app);
    }

    /**
     * Deletes an application from memory by its unique ID.
     *
     * @param id The ID of the application to delete.
     */
    @Override
    public void deleteApplication(String id) {
        this.applications.removeIf(a -> {
            return a.getId().equals(id);
        });
    }

    /**
     * Saves a new interview record to the in-memory list.
     *
     * @param interview The interview to save.
     */
    @Override
    public void saveInterview(Interview interview) {
        this.interviews.add(interview);
    }

    /**
     * Retrieves a copy of all stored interview records.
     *
     * @return A list of all interviews in memory.
     */
    @Override
    public List<Interview> loadAllInterviews() {
        return new ArrayList<>(this.interviews);
    }

    /**
     * Updates an existing interview record in memory.
     *
     * @param interview The interview containing the updated data.
     */
    @Override
    public void updateInterview(Interview interview) {
        this.interviews.removeIf(i -> {
            return i.getId().equals(interview.getId());
        });
        this.interviews.add(interview);
    }

    /**
     * Saves a new reminder to the in-memory list.
     *
     * @param reminder The reminder to save.
     */
    @Override
    public void saveReminder(Reminder reminder) {
        this.reminders.add(reminder);
    }

    /**
     * Retrieves a copy of all stored reminders.
     *
     * @return A list of all reminders in memory.
     */
    @Override
    public List<Reminder> loadAllReminders() {
        return new ArrayList<>(this.reminders);
    }

    /**
     * Updates an existing reminder in memory.
     *
     * @param reminder The reminder containing the updated data.
     */
    @Override
    public void updateReminder(Reminder reminder) {
        this.reminders.removeIf(r -> {
            return r.getId().equals(reminder.getId());
        });
        this.reminders.add(reminder);
    }
}