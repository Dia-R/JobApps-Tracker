## JobApps Tracker

**Version:** V1.6 — Public Release
A desktop application for NUS/NTU students to track internship applications, interviews, deadlines, and offers — all in one place.

---

## Features

- **Application Dashboard** — view all applications with status, pay, location, and deadlines at a glance, with bar and pie charts
- **Status Tracking** — track every stage: Applied → Interviewing → Offer → Accepted/Rejected/Withdrawn, with enforced transition rules
- **Interview Management** — log interview rounds, dates, and notes per application, with referential integrity checks
- **Deadline Reminders** — never miss an offer acceptance deadline, with calendar-based reminder view
- **Comparison Tool** — compare multiple offers side by side by salary, location, and job scope; highest pay highlighted automatically
- **Search** — search applications by company or role name from the dashboard
- **Persistent Storage** — all data saved locally to plain text files, no database needed

---

## Team

| Name | Role |
|---|---|
| Nadia | UI Lead, Code Quality Lead |
| Yugam | Logic Lead, Testing Lead |
| Ashley | Storage Lead |

---

## Tech Stack

- **Language:** Java 17
- **UI Framework:** JavaFX 21
- **Build Tool:** Gradle 9.3
- **Testing:** JUnit 5

---

## Project Structure

```
JobApps-Tracker/
├── build.gradle
├── settings.gradle
├── docs/
│   ├── DeveloperGuide.md
│   ├── UserGuide.md
│   └── SSD.md
└── src/
    ├── main/java/
    │   ├── gui/                        ← JavaFX controllers (Nadia)
    │   │   ├── Launcher.java
    │   │   ├── Main.java
    │   │   ├── MainController.java
    │   │   ├── DashboardController.java
    │   │   ├── CalendarController.java
    │   │   ├── CompareController.java
    │   │   ├── NewApplicationController.java
    │   │   └── EditApplicationController.java
    │   ├── logic/                      ← Business logic (Yugam)
    │   │   ├── Application.java
    │   │   ├── ApplicationController.java
    │   │   ├── ApplicationStatus.java
    │   │   ├── Interview.java
    │   │   ├── InterviewController.java
    │   │   ├── Reminder.java
    │   │   ├── ReminderService.java
    │   │   └── ReminderType.java
    │   └── storage/                    ← File persistence (Ashley)
    │       ├── Storage.java
    │       ├── FileStorage.java
    │       └── StorageException.java
    ├── main/resources/view/            ← FXML views + CSS (Nadia)
    │   ├── MainWindow.fxml
    │   ├── DashboardView.fxml
    │   ├── CalendarView.fxml
    │   ├── CompareView.fxml
    │   ├── NewApplicationView.fxml
    │   ├── EditApplicationView.fxml
    │   └── styles.css
    └── test/java/
        ├── logic/                      ← Logic tests (Yugam)
        │   ├── ApplicationControllerTest.java
        │   ├── InterviewControllerTest.java
        │   ├── ReminderServiceTest.java
        │   └── InMemoryStorage.java
        └── storage/                    ← Storage tests (Ashley)
            └── FileStorageTest.java
```

---

## Running the App

### Prerequisites
- Java 17+

### Build and Run
```bash
./gradlew run
```

### Run Tests
```bash
./gradlew test
```

### Build JAR
```bash
./gradlew jar
```

The JAR will be output to `build/libs/`.

---

## Test Coverage

| Test Class | Tests | Status |
|---|---|---|
| `CalendarControllerTest` | 6 | ✅ Passing |
| `CompareControllerTest` | 4 | ✅ Passing |
| `DashboardControllerTest` | 8 | ✅ Passing |
| `NewApplicationControllerTest` | 8 | ✅ Passing |
| `ApplicationControllerTest` | 22 | ✅ Passing |
| `InterviewControllerTest` | 5 | ✅ Passing |
| `ReminderServiceTest` | 5 | ✅ Passing |
| `FileStorageTest` | 40 | ✅ Passing |
| **Total** | **98** | **100% passing** |
---

## Data Storage

All data is stored locally in a `data/` folder created automatically on first run:

```
data/
├── applications.dat
├── interviews.dat
└── reminders.dat
```

Each file stores one record per line with fields separated by `|`. Pipe characters in user input are escaped as `&#124;` to prevent data corruption. Corrupted lines are silently skipped and logged without crashing the app. See the [Developer Guide](docs/DeveloperGuide.md) for full format details.

---

## Documentation

Comprehensive project documentation is available in the `docs/` directory:
* [**User Guide (UG)**](docs/UserGuide.md) - Instructions for end-users on navigating the dashboard, calendar, and comparison tools.
* [**Developer Guide (DG)**](docs/DeveloperGuide.md) - Full API documentation, system architecture, UML class diagrams, and sequence diagrams for future contributors.