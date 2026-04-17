# User Guide (UG) — JobApps Tracker

Welcome to the JobApps Tracker! This application is designed specifically for students managing multiple internship and job applications. It helps you track your statuses, compare offers, and never miss a deadline.

---

## 1. Quick Start
1. Ensure you have Java 17 or higher installed on your machine.
2. Download the latest `JobApps-Tracker.jar` release.
3. Double-click the `.jar` file to launch the application.
4. Your data is automatically saved locally in a `data/` folder in the same directory as the app. No internet connection is required!

---

## 2. Dashboard
The Dashboard is your main command center.
* **Stat Cards & Charts:** At the top, you will see a visual breakdown of your current application pipeline (how many applications are in the Interviewing stage, how many Offers you have, etc.).
* **Search:** Use the search bar to instantly filter your applications by Company Name or Role Title.
* **Table View:** Click on any column header (like "Pay" or "Deadline") to sort your applications.

---

## 3. Managing Applications
### Adding a New Application
1. Click the blue **+ New Application** button on the Dashboard.
2. Fill in the required fields (Company and Role) and any optional details (Pay, Location, Deadline).
3. Click **Save Application**.

### Editing & Updating Status
As you progress through the interview process, you'll need to update your status:
1. On the Dashboard, double-click a row (or click the **Edit** button on the right side of the row).
2. Update your Status from the dropdown menu (e.g., from `APPLIED` to `INTERVIEWING`).
3. *Note: `ACCEPTED`, `REJECTED`, and `WITHDRAWN` are terminal states. Once an application is moved to one of these states, its status cannot be changed again.*
4. You can also use this page to write down interview notes or update the expected pay.

---

## 4. Calendar View
Click **Calendar** in the left navigation sidebar to see a monthly view of your schedule.
* **Deadlines:** Highlighted in red, showing when you must accept or decline an offer.
* **Interviews:** Highlighted in blue.
* Use the arrow buttons at the top to navigate between months.

---

## 5. Comparison Tool
Deciding between multiple offers?
1. Click **Compare** in the left navigation sidebar.
2. Select the applications you want to compare from the list on the left.
    * *Tip: Hold `Cmd` (Mac) or `Ctrl` (Windows) while clicking to select multiple applications.*
3. The selected applications will appear side-by-side in the table.
4. The system will automatically highlight the row with the highest pay in green to help you make your decision.