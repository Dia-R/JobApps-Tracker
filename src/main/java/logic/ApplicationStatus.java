package logic;

/**
 * Represents the various stages of a job application process.
 * Used to track the current state of an application and enforce valid transitions.
 */
public enum ApplicationStatus {
    /** The application has been submitted but no response has been received. */
    APPLIED,

    /** The candidate is currently undergoing rounds of interviews. */
    INTERVIEWING,

    /** A formal job offer has been extended by the company. */
    OFFER,

    /** The application was not successful or was closed by the company. */
    REJECTED,

    /** The candidate has formally accepted the job offer. */
    ACCEPTED,

    /** The candidate has chosen to withdraw their application. */
    WITHDRAWN
}