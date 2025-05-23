package sustech.cs304.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sustech.cs304.App;
import sustech.cs304.entity.Announce;
import sustech.cs304.entity.Assignment;
import sustech.cs304.entity.Resource;
import sustech.cs304.entity.User;
import sustech.cs304.service.CourseApi;
import sustech.cs304.service.CourseApiImpl;
import javafx.collections.ObservableList;
import sustech.cs304.utils.AlterUtils;

import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;

public class TeacherCourseController {
    @FXML private Label courseTitle;
    @FXML private Label teacherName;
    @FXML private TableView<AnnouncementItem> announcementTable;
    @FXML private TableView<ResourceItem> resourceTable;
    @FXML private TableView<AssignmentItem> assignmentTable;
    @FXML private Button deleteCourseButton;
    @FXML private Label courseIdLabel;

    private CourseApi courseApi;
    private Long courseId;

    @FXML
    private void initialize() {
        courseApi = new CourseApiImpl();
    }

    public void loadData() {
        initializeAnnouncements();
        initializeResources();
        initializeAssignment();
    }

    public void setTitle(String courseName, String teacherName) {
        this.courseTitle.setText(courseName);
        this.teacherName.setText(teacherName);
        this.courseIdLabel.setText("Course ID: " + String.valueOf(courseId));
    }

    private void initializeAnnouncements() {
        List<Announce> announcements = courseApi.getAnnounceByCourseId(courseId);
        if (announcements == null || announcements.isEmpty()) {
            return;
        }
        ObservableList<AnnouncementItem> announcementItems = FXCollections.observableArrayList();
        for (Announce announce : announcements) {
            announcementItems.add(new AnnouncementItem(announce));
        }
        announcementTable.setItems(announcementItems);

        TableColumn<AnnouncementItem, String> actionColumn = (TableColumn<AnnouncementItem, String>) announcementTable.getColumns().get(2);
        actionColumn.setCellFactory(col -> new TableCell<AnnouncementItem, String>() {
            private final Button viewButton = new Button("View");
            {
                viewButton.getStyleClass().add("operation-button");
                viewButton.setOnAction(event -> {
                    AnnouncementItem item = getTableView().getItems().get(getIndex());
                    AlterUtils.showInfoAlert(
                        (Stage) viewButton.getScene().getWindow(),
                        "Announcement", item.getAnnounce().getAnnounceName(), item.getAnnounce().getAnnounceContent());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(viewButton);
                }
            }
        });
    }

    private void initializeResources() {
        List<Resource> resources = courseApi.getResourceByCourseId(courseId);
        if (resources == null || resources.isEmpty()) {
            return;
        }
        ObservableList<ResourceItem> resourceItems = FXCollections.observableArrayList();
        for (Resource resource : resources) {
            resourceItems.add(new ResourceItem(resource));
        }
        resourceTable.setItems(resourceItems);

        TableColumn<ResourceItem, String> actionColumn = (TableColumn<ResourceItem, String>) resourceTable.getColumns().get(4);
        actionColumn.setCellFactory(col -> new TableCell<ResourceItem, String>() {
            private final Button downloadButton = new Button("Download");
            {
                downloadButton.getStyleClass().add("operation-button");
                downloadButton.setOnAction(event -> {
                    ResourceItem item = getTableView().getItems().get(getIndex());
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    directoryChooser.setTitle("Select Download Directory");
                    Stage stage = (Stage) downloadButton.getScene().getWindow();

                    File selectedDirectory = directoryChooser.showDialog(stage);
                    if (selectedDirectory == null) {
                        return; // User canceled the dialog
                    }
                    String downloadPath = selectedDirectory.getAbsolutePath();
                    courseApi.downloadResource(item.getResource().getAddress(), downloadPath);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(downloadButton);
                }
            }
        });
    }

    private void initializeAssignment() {
        List<Assignment> assignments = courseApi.getAssignmentByCourseId(courseId, App.user.getUserId());
        if (assignments == null || assignments.isEmpty()) {
            return;
        }
        ObservableList<AssignmentItem> assignmentItems = FXCollections.observableArrayList();
        for (Assignment assignment : assignments) {
            assignmentItems.add(new AssignmentItem(assignment));
        }
        assignmentTable.setItems(assignmentItems);
        TableColumn<AssignmentItem, String> actionColumn = (TableColumn<AssignmentItem, String>) assignmentTable.getColumns().get(3);
        actionColumn.setCellFactory(col -> new TableCell<AssignmentItem, String>() {
            private final Button submitButton = new Button("View Submission");
            {
                submitButton.getStyleClass().add("operation-button");
                submitButton.setOnAction(event -> {
                    AssignmentItem item = getTableView().getItems().get(getIndex());
                    AlterUtils.showSubmission(
                        (Stage) submitButton.getScene().getWindow(),
                        item.getAssignment().getId());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(submitButton);
                }
            }
        });
        TableColumn<AssignmentItem, String> attachmentColumn = (TableColumn<AssignmentItem, String>) assignmentTable.getColumns().get(1);
        attachmentColumn.setCellFactory(col -> new TableCell<AssignmentItem, String>() {
            private final Button attachmentButton = new Button("View Attachment");
            {
                attachmentButton.getStyleClass().add("operation-button");
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    AssignmentItem currentItem = getTableView().getItems().get(getIndex());
                    if (currentItem.getAssignment().getAttachmentName() != null) {
                        attachmentButton.setText(currentItem.getAssignment().getAttachmentName());
                    } else {
                        attachmentButton.setText("No Attachment");
                        attachmentButton.setDisable(true);
                    }
                    attachmentButton.setOnAction(event -> {
                        // Download attachment
                        DirectoryChooser directoryChooser = new DirectoryChooser();
                        directoryChooser.setTitle("Select Download Directory");
                        Stage stage = (Stage) attachmentButton.getScene().getWindow();

                        File selectedDirectory = directoryChooser.showDialog(stage);
                        if (selectedDirectory == null) {
                            return; // User canceled the dialog
                        }
                        String downloadPath = selectedDirectory.getAbsolutePath();
                        courseApi.downloadResource(currentItem.getAssignment().getAttachmentaddress(), downloadPath);
                    });
                    setGraphic(attachmentButton);
                }
            }
        });
    }

    @FXML
    private void publishAnnouncement() {
        Announce announce = AlterUtils.showAnnounceInputForm(App.primaryStage, String.valueOf(courseId));
        if (announce != null) {
            courseApi.createAnnouncment(courseId, announce.getAnnounceName(), announce.getAnnounceContent(), App.user.getUserId());
            loadData();
        }
    }

    @FXML
    private void publishResource() {
        Resource resource = AlterUtils.showResourceInputForm(App.primaryStage);
        if (resource != null) {
            courseApi.uploadResource(courseId, resource.getAddress(), App.user.getUserId());
            loadData();
        }
    }

    @FXML
    private void publishAssignment() {
        Assignment assignment = AlterUtils.showAssignmentInputForm(App.primaryStage);
        if (assignment != null) {
            courseApi.createAssignment(courseId, assignment.getAssignmentName(), assignment.getDeadline(), App.user.getUserId(), assignment.getAddress());
            loadData();
        }
    }

    @FXML
    private void deleteCourse() {
        boolean confirm = AlterUtils.showConfirmationAlert(
            (Stage) this.courseIdLabel.getScene().getWindow(), 
            "Delete Course", "Are you sure you want to delete this course?",
            "Please confirm."
        );

        if (confirm) {
            courseApi.deleteCourse(courseId, App.user.getUserId());
        }
    }

    @FXML
    private void showMemberList() {
        List<User> members = courseApi.getUserByCourseId(courseId);
        AlterUtils.showMemberList((Stage) this.courseIdLabel.getScene().getWindow(), members);
    }

    @FXML
    private void inviteNewMember() {
        List<String> invitationList = AlterUtils.showInvitationInputForm((Stage) this.courseIdLabel.getScene().getWindow());
        if (invitationList != null) {
            courseApi.createCourseInvitation(courseId, invitationList);
        } else {
        }
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public static class ResourceItem {
        private final Resource resource;
        private final String action;

        public ResourceItem(Resource resource) {
            this.resource = resource; 
            this.action = "download";
        }

        public Resource getResource() { return resource; }
        public String getAction() { return action; }
        public String getFileName() { return resource.getResourceName(); }
        public String getFileType() { return resource.getType(); }
        public String getDate() { return resource.getUploadTime(); }
        public String getSize() { return resource.getSize(); }
    }

    public static class AssignmentItem {
        private final Assignment assignment;
        private final String action;
        private final String attachment;

        public AssignmentItem(Assignment assignment) {
            this.assignment = assignment;
            this.action = "submit";
            this.attachment = "download";
        }

        public Assignment getAssignment() { return assignment; }
        public String getAction() { return action; }
        public String getAttachment() { return attachment; }
        public String getName() { return assignment.getAssignmentName(); }
        public String getDeadline() { return assignment.getDeadline(); }
        public String getStatus() { return assignment.getWhetherSubmitted() ? "Submitted" : "Not Submitted"; }
    }

    public static class AnnouncementItem {
        private final Announce announce;
        private final String action;

        public AnnouncementItem(Announce announce) {
            this.announce = announce;
            this.action = "view";
        }

        public Announce getAnnounce() { return announce; }
        public String getAction() { return action; }
        public String getName() { return announce.getAnnounceName(); }
        public String getTime() { return announce.getUpLoadTime(); }
    }
}
