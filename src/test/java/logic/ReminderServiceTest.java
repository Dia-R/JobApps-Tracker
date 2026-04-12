package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ReminderService.
 * Verifies the creation, filtering, sorting, and dismissing of reminders.
 */
class ReminderServiceTest {

    private ReminderService reminderService;
    private ApplicationController appController;
    private String appId;

    /**
     * Initializes the ReminderService and ApplicationController with a shared
     * in-memory storage stub before each test. Creates a real application so that
     * referential integrity checks in ReminderService pass correctly.
     * Ensures each test runs in complete isolation.
     */
    @BeforeEach
    void setUp() {
        InMemoryStorage storage = new InMemoryStorage();
        reminderService = new ReminderService(storage);
        appController = new ApplicationController(storage);
        appId = appController.addApplication(
                "TestCo", "Intern", 3000, "SG", ApplicationStatus.APPLIED).getId();
    }

    /**
     * Verifies that a valid reminder is successfully created and stored
     * with an initial non-dismissed state.
     */
    @Test
    void addReminder_validInput_storesReminder() {
        Reminder reminder = reminderService.addReminder(
                appId, ReminderType.DEADLINE, LocalDate.now().plusDays(2));
        assertNotNull(reminder);
        assertFalse(reminder.isDismissed());
    }

    /**
     * Verifies that attempting to add a reminder for a non-existent application
     * throws an IllegalArgumentException, enforcing referential integrity.
     */
    @Test
    void addReminder_invalidAppId_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                reminderService.addReminder(
                        "fake-id", ReminderType.DEADLINE, LocalDate.now().plusDays(1)));
    }

    /**
     * Verifies that the service retrieves only the reminders that fall
     * strictly within the specified look-ahead window.
     */
    @Test
    void getUpcomingReminders_withinWindow_returnsCorrectReminders() {
        reminderService.addReminder(appId, ReminderType.DEADLINE, LocalDate.now().plusDays(1));
        reminderService.addReminder(appId, ReminderType.INTERVIEW, LocalDate.now().plusDays(3));
        reminderService.addReminder(appId, ReminderType.FOLLOWUP, LocalDate.now().plusDays(10));

        List<Reminder> upcoming = reminderService.getUpcomingReminders(5);

        assertEquals(2, upcoming.size());
    }

    /**
     * Verifies that dismissed reminders are correctly filtered out
     * and excluded from the upcoming reminders list.
     */
    @Test
    void getUpcomingReminders_dismissedReminderExcluded() {
        Reminder r = reminderService.addReminder(
                appId, ReminderType.DEADLINE, LocalDate.now().plusDays(1));
        reminderService.dismissReminder(r.getId());

        List<Reminder> upcoming = reminderService.getUpcomingReminders(5);

        assertTrue(upcoming.isEmpty());
    }

    /**
     * Verifies that the retrieved list of upcoming reminders is sorted
     * chronologically in ascending order.
     */
    @Test
    void getUpcomingReminders_sortedByDateAscending() {
        reminderService.addReminder(appId, ReminderType.DEADLINE, LocalDate.now().plusDays(3));
        reminderService.addReminder(appId, ReminderType.INTERVIEW, LocalDate.now().plusDays(1));

        List<Reminder> upcoming = reminderService.getUpcomingReminders(5);

        assertTrue(upcoming.get(0).getTriggerDate().isBefore(upcoming.get(1).getTriggerDate()));
    }
}