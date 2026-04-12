package logic;

/**
 * Defines the categories of reminders available in the system.
 */
public enum ReminderType {
    /** Reminder for an application or offer deadline.  */
    DEADLINE,

    /** Reminder for a scheduled interview date and time.  */
    INTERVIEW,

    /** Reminder to follow up with a recruiter or company.  */
    FOLLOWUP
}