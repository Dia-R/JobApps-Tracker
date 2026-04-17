# Product Requirements Document (PRD)

## Internship / Job Tracker for Students

**Project Name:** Internship / Job Tracker
**Document Type:** Product Requirements Document
**Version:** 1.6
**Date:** 16 April 2026
**Based on:** Software Design Document v1.6

---

## Table of Contents

1. [Product Overview](#1-product-overview)
2. [Stakeholders](#2-stakeholders)
3. [User Stories](#3-user-stories)
4. [Feature List](#4-feature-list)
5. [Functional Requirements](#5-functional-requirements)
6. [Non-Functional Requirements](#6-non-functional-requirements)
7. [Use Cases](#7-use-cases)
8. [Constraints](#8-constraints)
9. [Glossary](#9-glossary)

---

## 1. Product Overview

### 1.1 Purpose

The Internship / Job Tracker is a centralized application that helps students manage all their job and internship applications in one place. Students applying to multiple roles simultaneously often lose track of application statuses, interview schedules, offer deadlines, and company notes. This product addresses that problem by providing structured tracking, reminders, comparison features, and notes management.

### 1.2 Problem Statement

Students face the following pain points during their job search:

- Losing track of which stage they are at with each company
- Missing offer acceptance deadlines due to no centralized reminder system
- Struggling to compare opportunities based on pay, location, and status
- Forgetting interview notes and company-specific details after conversations

### 1.3 Target Users

Students who are actively applying for internships, part-time jobs, or full-time jobs and are managing multiple applications at any given time.

---

## 2. Stakeholders

| Stakeholder | Role | Interest |
|---|---|---|
| Students | Primary User | Track applications, prepare for interviews, compare offers |
| Course Supervisor | Internal | Ensure product meets academic and design requirements |

---

## 3. User Stories

User stories are written in the format: **As a [role], I want to [goal], so that [benefit].**

All user stories are prioritized using three levels:

- **Level 0 — Essential:** Must be delivered for the product to be usable
- **Level 1 — Typical:** Expected by users and significantly improves experience

| ID | User Story | Priority |
|---|---|---|
| US-01 | As a student, I want to be able to monitor the deadline of offers, so that I can decide which ones to accept or decline. | Level 0 |
| US-02 | As a student, I want to compare internships by pay and location, so that I can prioritize which roles to focus on. | Level 0 |
| US-03 | As a student, I want to track interview rounds so that I know which stage I am in. | Level 0 |
| US-04 | As a student, I want to store interview notes so that I can prepare better. | Level 1 |
| US-05 | As a student, I want to see upcoming interview dates in one place so that I can prepare accordingly. | Level 0 |
| US-06 | As a student, I want to edit application details (company, role, pay, location, status, deadline, notes) so that I can keep my records up to date. | Level 0 |
| US-07 | As a student, I want to keep notes on each application and what was discussed during interviews. | Level 1 |

---

## 4. Feature List

### Feature 1 — Application Tracking Dashboard

- Centralized dashboard showing all applications and their statuses
  - Track stages: Applied → Interviewing → Offer → Accepted / Rejected / Withdrawn
  - View application progress at a glance via stat cards, bar chart, and pie chart
  - Search/filter applications by company name or role title
  - Edit button per row for quick access to application editing

### Feature 2 — Application Editing

- Full edit form for any application entry
  - Edit core details: company name, role title, pay, location
  - Update status with enforced transition rules
  - Set or clear deadline dates
  - Add or update free-text notes
  - Delete an application with confirmation dialog

### Feature 3 — Deadline & Interview Reminders

- Reminders for:
  - Offer acceptance deadlines
  - Interview schedules
  - Follow-ups with HR
- Reminders can be dismissed once acknowledged

### Feature 4 — Calendar-Style Overview of Upcoming Events

- A calendar view displaying all upcoming events across all applications in one place
  - Application deadlines
  - Interview dates
  - Reminder trigger dates
- Monthly navigation with colour-coded event badges

### Feature 5 — Internship Comparison Tool

- Compare applications by selecting multiple entries from a list (Cmd/Ctrl-click)
- Side-by-side comparison table showing: company, role, pay, location, status, deadline
- Best-pay row is visually highlighted

---

## 5. Functional Requirements

Functional requirements specify **what the system must do**.

### 5.1 Application Tracking Dashboard

| ID | Requirement | Linked User Story | Priority |
|---|---|---|---|
| FR-01 | The system shall allow students to create a new application entry with fields: Company Name, Role Title, Pay, Location, and Status. | US-03 | Level 0 |
| FR-02 | The system shall allow students to update the status of any application to one of the following stages: Applied, Interviewing, Offer, Rejected, Accepted, Withdrawn. | US-03 | Level 0 |
| FR-03 | The system shall enforce valid status transitions — terminal states (Rejected, Accepted, Withdrawn) cannot be changed, and Applied cannot skip directly to Offer. | US-03 | Level 0 |
| FR-04 | The system shall display all application entries on a dashboard with their current status visible at a glance. | US-03 | Level 0 |
| FR-05 | The system shall allow students to delete an application entry with a confirmation dialog. | US-03 | Level 0 |
| FR-06 | The system shall allow students to search/filter applications by company name or role title. | US-03 | Level 1 |

### 5.2 Application Editing

| ID | Requirement | Linked User Story | Priority |
|---|---|---|---|
| FR-07 | The system shall allow students to edit the core details of an application: company name, role title, pay, and location. | US-06 | Level 0 |
| FR-08 | The system shall allow students to set or clear a deadline date on any application. | US-01 | Level 0 |
| FR-09 | The system shall allow students to write and save free-text notes per application. | US-07 | Level 1 |

### 5.3 Deadline & Interview Reminders

| ID | Requirement | Linked User Story | Priority |
|---|---|---|---|
| FR-10 | The system shall allow students to set an offer acceptance deadline date on any application. | US-01 | Level 0 |
| FR-11 | The system shall allow students to add interview dates and times to any application entry. | US-05 | Level 0 |
| FR-12 | The system shall allow students to set a follow-up reminder with a type and target date for any application. | US-05 | Level 1 |
| FR-13 | The system shall allow students to dismiss a reminder once it is acknowledged. | US-05 | Level 1 |

### 5.4 Calendar-Style Overview of Upcoming Events

| ID | Requirement | Linked User Story | Priority |
|---|---|---|---|
| FR-14 | The system shall provide a calendar view displaying all upcoming deadlines, interview dates, and reminder trigger dates across all applications. | US-05 | Level 1 |
| FR-15 | The system shall display colour-coded event badges on calendar dates to differentiate event types. | US-05 | Level 1 |

### 5.5 Internship Comparison Tool

| ID | Requirement | Linked User Story | Priority |
|---|---|---|---|
| FR-16 | The system shall allow students to select two or more applications from a list for comparison. | US-02 | Level 0 |
| FR-17 | The system shall display a comparison table showing company, role, pay, location, status, and deadline for the selected applications, sorted by pay descending. | US-02 | Level 0 |
| FR-18 | The system shall visually highlight the highest-pay application in the comparison table. | US-02 | Level 1 |

---

## 6. Non-Functional Requirements

Non-functional requirements specify **the constraints and quality standards** the system must meet.

### 6.1 Performance

| ID | Requirement |
|---|---|
| NFR-01 | The system shall load the main dashboard within 2 seconds under normal usage. |
| NFR-02 | Search and filter operations shall return results within 1 second for up to 200 application entries. |

### 6.2 Usability

| ID | Requirement |
|---|---|
| NFR-03 | The system shall provide a clear and intuitive interface that a first-time user can navigate without a manual. |
| NFR-04 | Updating an application status shall be achievable in no more than 2 user interactions (click Edit, change status, save). |

### 6.3 Security & Privacy

| ID | Requirement |
|---|---|
| NFR-05 | All data shall be stored locally on the user's device; no data shall be transmitted to any external server. |

### 6.4 Portability

| ID | Requirement |
|---|---|
| NFR-06 | The system shall operate fully without an internet connection. |
| NFR-07 | The system shall not depend on any third-party API or database. |

---

## 7. Use Cases

---

### UC-01: Track Interview Round Progress

**System:** Internship / Job Tracker

**Actor:** Student

**Precondition:** Student has at least one application entry saved in the system.

**MSS:**

1. Student opens the Application Dashboard.
2. System displays all application entries with their current statuses.
3. Student clicks the Edit button on an application entry.
4. System loads the Edit Application form with the current details.
5. Student updates the status (e.g., from Applied to Interviewing).
6. Student clicks Save.
7. System validates the status transition and saves the update.
8. System navigates back to the Dashboard with the updated status reflected.

Use case ends.

**Extensions:**

2a. No application entries exist.
- 2a1. System displays an empty dashboard with a prompt to add a new application.
- Use case ends.

7a. Status transition is invalid (e.g., Rejected to Offer).
- 7a1. System displays an error message explaining the invalid transition.
- 7a2. Student selects a valid status.
- Resume from step 6.

*a. Application data fails to load.
- *a1. System informs the student that data could not be retrieved.
- Use case ends.

---

### UC-02: Monitor Offer Deadline

**System:** Internship / Job Tracker

**Actor:** Student

**Precondition:** Student has at least one application entry in the system.

**MSS:**

1. Student opens the Application Dashboard.
2. System displays all application entries.
3. Student clicks the Edit button on an application entry.
4. System loads the Edit Application form.
5. Student sets a deadline date using the date picker.
6. Student clicks Save.
7. System saves the deadline.
8. Student navigates to the Calendar view.
9. System displays the deadline as a colour-coded badge on the calendar.

Use case ends.

**Extensions:**

5a. Student wants to clear an existing deadline.
- 5a1. Student clicks the Clear Deadline button.
- 5a2. Deadline field is cleared.
- Resume from step 6.

*a. Data fails to save.
- *a1. System informs the student that the deadline could not be saved.
- Use case ends.

---

### UC-03: Compare Internship Offers

**System:** Internship / Job Tracker

**Actor:** Student

**Precondition:** Student has at least two application entries saved in the system.

**MSS:**

1. Student navigates to the Comparison page.
2. System displays a list of all applications with a multi-select list.
3. Student selects two or more applications (using Cmd/Ctrl-click).
4. System displays a comparison table showing company, role, pay, location, status, and deadline for the selected applications, sorted by pay descending.
5. System highlights the best-pay row.

Repeat steps 3–5 until the student is satisfied.

Use case ends.

**Extensions:**

2a. No application entries exist.
- 2a1. System displays an empty list.
- Use case ends.

3a. Student selects fewer than two applications.
- 3a1. System displays a hint label prompting multi-selection.
- Resume from step 3.

*a. Data fails to load.
- *a1. System informs the student that comparison data could not be retrieved.
- Use case ends.

---

### UC-04: Edit Application Details

**System:** Internship / Job Tracker

**Actor:** Student

**Precondition:** Student has at least one application entry saved in the system.

**MSS:**

1. Student opens the Application Dashboard.
2. System displays all application entries.
3. Student clicks the Edit button on an application row.
4. System loads the Edit Application form with all current details (company, role, pay, location, status, deadline, notes).
5. Student modifies the desired fields.
6. Student clicks Save.
7. System validates all fields (company/role non-blank, pay is numeric, status transition is valid).
8. System saves all changes and displays a "Changes saved" confirmation.
9. Student clicks Back to return to the Dashboard.

Use case ends.

**Extensions:**

7a. Company name or role title is blank.
- 7a1. System displays an error message.
- 7a2. Student corrects the field.
- Resume from step 6.

7b. Pay value is not a valid number.
- 7b1. System displays "Pay must be a valid number" error.
- 7b2. Student corrects the pay field.
- Resume from step 6.

7c. Status transition is invalid.
- 7c1. System displays an error message explaining the invalid transition.
- 7c2. Student selects a valid status.
- Resume from step 6.

6a. Student clicks Delete instead of Save.
- 6a1. System shows a confirmation dialog.
- 6a2. Student confirms deletion.
- 6a3. System deletes the application and navigates back to the Dashboard.
- Use case ends.

*a. Data fails to save.
- *a1. System informs the student that changes could not be saved.
- Use case ends.

---

### UC-05: Log Interview Notes

**System:** Internship / Job Tracker

**Actor:** Student

**Precondition:** Student has at least one application entry saved in the system.

**MSS:**

1. Student opens the Application Dashboard.
2. System displays all application entries.
3. Student clicks the Edit button on an application entry.
4. System loads the Edit Application form.
5. Student writes notes in the Notes text area.
6. Student clicks Save.
7. System saves the notes.

Use case ends.

**Extensions:**

2a. No application entries exist.
- 2a1. System displays an empty dashboard with a prompt to add a new application.
- Use case ends.

*a. Notes fail to save.
- *a1. System informs the student that the notes could not be saved.
- Use case ends.

---

## 8. Constraints

| Constraint | Description |
|---|---|
| No external services | The system must not call any third-party APIs or external services. |
| No internet dependency | All features must function fully offline. |
| Internal data handling | All data (applications, interviews, reminders, notes) must be stored and managed locally on the user's device via flat-file storage. |
| 3-layer architecture | Development must follow the UI / Logic / Storage separation as defined in the SDD. |
| Team split | The codebase is divided among three workstreams: UI, Logic, and Storage. |

---

## 9. Glossary

| Term | Definition |
|---|---|
| Application Entry | A record representing a single job or internship application, containing fields: company name, role title, pay, location, status, date applied, deadline, and notes. |
| Application Status | The current stage of an application in the hiring pipeline: Applied, Interviewing, Offer, Accepted, Rejected, or Withdrawn. |
| Terminal State | A status from which no further transitions are allowed: Rejected, Accepted, or Withdrawn. |
| Offer Deadline | The date by which a student must accept or reject a job or internship offer from a company. |
| Reminder | An in-app record tied to an application, with a type (Deadline, Interview, Follow-up), trigger date, and dismissed flag. |
| Comparison Tool | A feature that allows selecting multiple applications and viewing them side-by-side, sorted by pay descending, with the highest-pay row highlighted. |
