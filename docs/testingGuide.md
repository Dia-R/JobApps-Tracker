# Testing Guide

This page supplements the [Developer Guide](developerGuide.md) and provides more information about the methodology for testing JobApps Tracker.

---

## How to Run Tests

There are two main ways to run tests.

1. Right-click the `test` folder in your IDE and click **Run Tests in ...**
2. Run `gradle test` at the root folder

**NOTE:** If you encounter an error where tests do not start, try cleaning the build first by running `gradle clean`.

---

## Types of Testing Done

### Unit Testing

Unit testing was done for the lowest-level modules in isolation. These tests ensure that individual components behave correctly independently of one another.

- Examples: `ApplicationControllerTest`, `InterviewControllerTest`, `ReminderServiceTest`

### Integration Testing

Integration testing was done at a higher level, verifying that multiple components interact correctly with each other. These tests exercise the full logic-to-storage path using `InMemoryStorage` as a test stub to avoid file I/O.

- Examples: `FileStorageTest`, `ApplicationControllerTest` (storage-integrated cases)

### GUI Testing

GUI testing was done for the package-private logic methods exposed by each controller, which can be invoked without a running JavaFX runtime. Full rendering and event-driven behaviour requires a JavaFX runtime and is not covered by automated tests.

- Examples: `DashboardControllerTest`, `CompareControllerTest`, `CalendarControllerTest`, `NewApplicationControllerTest`

---

You have reached the end of the testing guide!

To head back, click [here](./developerGuide.md)
