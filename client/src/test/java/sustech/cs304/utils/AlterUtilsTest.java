package sustech.cs304.utils;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import junit.framework.TestCase;
import sustech.cs304.entity.Course;
import sustech.cs304.entity.User;

public class AlterUtilsTest extends TestCase{
    private Stage stage;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Initialize any necessary resources or mock data here
    }

    @BeforeAll
    public static void initJavaFX() {
        // 初始化 JavaFX 平台
        new JFXPanel(); // 这是关键：它会隐式启动 JavaFX Toolkit
        Platform.setImplicitExit(false); // 防止测试时关闭 JavaFX 平台
    }

    @Test
    void testShowComfirmationAlert() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                String title = "Test Confirmation";
                String headerText = "Are you sure?";
                String contentText = "This is a test confirmation alert.";
                AlterUtils.showConfirmationAlert(stage, title, headerText, contentText);
                latch.countDown(); // 释放计数器，表示测试完成
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showConfirmationAlert: " + e.getMessage());
            } finally {
            }
        });

    }

    @Test
    void testShowInfoAlert() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                String title = "Test Information";
                String headerText = "Information Alert";
                String contentText = "This is a test information alert.";
                AlterUtils.showInfoAlert(stage, title, headerText, contentText);
                latch.countDown(); // 释放计数器，表示测试完成
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showInformationAlert: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testShowAnnounceInputForm() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                String courseId = "1";
                AlterUtils.showAnnounceInputForm(stage, courseId);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showAnnounceInputForm: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testShowAssignmentInputForm() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                AlterUtils.showAssignmentInputForm(stage);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showAssignmentInputForm: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testShowResourceInputForm() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                AlterUtils.showResourceInputForm(stage);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showResourceInputForm: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testShowInputForm() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                String title = "Test Input Form";
                List<String> labels = List.of("Label1", "Label2");
                AlterUtils.showInputForm(stage, title, labels);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showInputForm: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testCourseInputForm() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                AlterUtils.courseInputForm(stage);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showCourseInputForm: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testShowMemberList() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                List<User> members = List.of(
                    new User("1"),
                    new User("2"),
                    new User("3")
                );
                AlterUtils.showMemberList(stage, members);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showMemberList: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testShowNewRequestList() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                List<User> requests = List.of(new User("request1"), new User("request2"));
                AlterUtils.showNewRequestList(stage, requests);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showNewRequestList: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testShowNewRequestListWithEmptyList() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                List<User> requests = List.of(); // Empty list
                AlterUtils.showNewRequestList(stage, requests);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showNewRequestList with empty list: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testShowInvitatonInputForm() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                AlterUtils.showInvitationInputForm(stage);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showInvitationInputForm: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testShowInvitationList() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                List<Course> invitations = List.of(new Course(1L, "course1", "test", "test", "test", true));
                AlterUtils.showInvitationList(stage, invitations);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showInvitationList: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

    @Test
    void testShowSubmisson() {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                stage = new Stage();
                Long assignmentId = 1L;
                AlterUtils.showSubmission(stage, assignmentId);
                latch.countDown();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception thrown during showSubmission: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
    }

}
