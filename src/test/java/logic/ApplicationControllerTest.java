package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ApplicationController.
 * Verifies business logic, input validation, and status flow enforcement.
 */
class ApplicationControllerTest {

    private ApplicationController controller;

    /**
     * Initializes the controller with an in-memory storage stub before each test.
     */
    @BeforeEach
    void setUp() {
        controller = new ApplicationController(new InMemoryStorage());
    }

    /**
     * Verifies that providing valid application details successfully creates and stores the application.
     */
    @Test
    void addApplication_validInput_returnsApplication() {
        Application app = controller.addApplication(
                "Google", "SWE Intern", 5000, "Singapore", ApplicationStatus.APPLIED);
        assertNotNull(app);
        assertEquals("Google", app.getCompanyName());
        assertEquals(ApplicationStatus.APPLIED, app.getStatus());
    }

    /**
     * Verifies that attempting to create an application with an empty company name throws an exception.
     */
    @Test
    void addApplication_emptyCompanyName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.addApplication("", "SWE Intern", 5000, "Singapore", ApplicationStatus.APPLIED);
        });
    }

    /**
     * Verifies that attempting to create an application with a null role title throws an exception.
     */
    @Test
    void addApplication_nullRoleTitle_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.addApplication("Google", null, 5000, "Singapore", ApplicationStatus.APPLIED);
        });
    }

    /**
     * Verifies that loading applications from an empty storage returns an empty list.
     */
    @Test
    void getAllApplications_emptyList_returnsEmptyList() {
        assertTrue(controller.getAllApplications().isEmpty());
    }

    /**
     * Verifies that all saved applications are successfully retrieved from storage.
     */
    @Test
    void getAllApplications_afterAdding_returnsAll() {
        controller.addApplication("Meta", "PM Intern", 4000, "Remote", ApplicationStatus.APPLIED);
        controller.addApplication("Grab", "Data Intern", 3500, "Singapore", ApplicationStatus.APPLIED);
        assertEquals(2, controller.getAllApplications().size());
    }

    /**
     * Verifies that a valid status transition (e.g., APPLIED to INTERVIEWING) updates the application correctly.
     */
    @Test
    void updateStatus_validTransition_updatesCorrectly() {
        Application app = controller.addApplication(
                "Shopee", "Backend Intern", 4500, "Singapore", ApplicationStatus.APPLIED);
        Application updated = controller.updateStatus(app.getId(), ApplicationStatus.INTERVIEWING);
        assertEquals(ApplicationStatus.INTERVIEWING, updated.getStatus());
    }

    // --- NEW EXCEPTION TESTS FOR STATUS FLOW ---

    /**
     * Verifies the business rule that a REJECTED application is in a terminal state and cannot be revived.
     */
    @Test
    void updateStatus_rejectedToOffer_throwsException() {
        Application app = controller.addApplication(
                "DeadCo", "Role", 1000, "SG", ApplicationStatus.REJECTED);

        assertThrows(IllegalStateException.class, () -> {
            controller.updateStatus(app.getId(), ApplicationStatus.OFFER);
        }, "Should throw exception when reviving a REJECTED application");
    }

    /**
     * Verifies the business rule that an application must pass through the INTERVIEWING stage before receiving an OFFER.
     */
    @Test
    void updateStatus_appliedToOffer_throwsException() {
        Application app = controller.addApplication(
                "FastCo", "Role", 2000, "SG", ApplicationStatus.APPLIED);

        assertThrows(IllegalStateException.class, () -> {
            controller.updateStatus(app.getId(), ApplicationStatus.OFFER);
        }, "Should throw exception when jumping straight to OFFER without an interview");
    }

    // -------------------------------------------

    /**
     * Verifies that attempting to update the status of a non-existent application throws an exception.
     */
    @Test
    void updateStatus_invalidId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateStatus("nonexistent-id", ApplicationStatus.OFFER);
        });
    }

    /**
     * Verifies that deleting a valid application removes it completely from storage.
     */
    @Test
    void deleteApplication_existingId_removesFromList() {
        Application app = controller.addApplication(
                "ByteDance", "iOS Intern", 5000, "Singapore", ApplicationStatus.APPLIED);
        controller.deleteApplication(app.getId());
        assertTrue(controller.getAllApplications().isEmpty());
    }

    /**
     * Verifies that attempting to delete an application with an invalid ID throws an exception.
     */
    @Test
    void deleteApplication_invalidId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.deleteApplication("fake-id");
        });
    }

    /**
     * Verifies that the compareApplications method sorts the specified applications by pay in descending order.
     */
    @Test
    void compareApplications_sortedByPayDescending() {
        Application low = controller.addApplication(
                "StartupA", "Intern", 2000, "Singapore", ApplicationStatus.APPLIED);
        Application high = controller.addApplication(
                "BigTech", "Intern", 6000, "Singapore", ApplicationStatus.OFFER);
        List<Application> result = controller.compareApplications(List.of(low.getId(), high.getId()));

        assertEquals("BigTech", result.get(0).getCompanyName());
        assertEquals("StartupA", result.get(1).getCompanyName());
    }
}