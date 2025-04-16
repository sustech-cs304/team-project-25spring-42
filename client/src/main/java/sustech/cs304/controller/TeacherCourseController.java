package sustech.cs304.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sustech.cs304.App;
import sustech.cs304.entity.Announce;
import sustech.cs304.entity.Assignment;
import sustech.cs304.entity.Resource;
import sustech.cs304.service.CourseApi;
import sustech.cs304.service.CourseApiImpl;
import javafx.collections.ObservableList;
import sustech.cs304.utils.AlterUtils;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;

public class TeacherCourseController {
    @FXML private Label courseTitle;
    @FXML private Label teacherName;
    @FXML private TableView<AnnouncementItem> announcementTable;
    @FXML private TableView<ResourceItem> resourceTable;
    @FXML private TableView<AssignmentItem> assignmentTable;
    @FXML private Button enterCourseBtn;

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
    }

    private void initializeAnnouncements() {
        List<Announce> announcements = courseApi.getAnnounceByCourseId(courseId);
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
        ObservableList<AssignmentItem> assignmentItems = FXCollections.observableArrayList();
        for (Assignment assignment : assignments) {
            assignmentItems.add(new AssignmentItem(assignment));
        }
        assignmentTable.setItems(assignmentItems);
        TableColumn<AssignmentItem, String> actionColumn = (TableColumn<AssignmentItem, String>) assignmentTable.getColumns().get(4);
        actionColumn.setCellFactory(col -> new TableCell<AssignmentItem, String>() {
            private final Button submitButton = new Button("Submit");
            {
                submitButton.getStyleClass().add("operation-button");
                submitButton.setOnAction(event -> {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Select File to Submit");
                    Stage stage = (Stage) submitButton.getScene().getWindow();

                    File selectedFile = fileChooser.showOpenDialog(stage);
                    if (selectedFile != null) {
                        String filePath = selectedFile.getAbsolutePath();
                        AssignmentItem item = getTableView().getItems().get(getIndex());
                        courseApi.submitAssignment(item.getAssignment().getId(), App.user.getUserId(), filePath);
                        loadData();
                    }
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
    private void searchResources() {
    }

    @FXML
    private void uploadResource() {
        // Implement resource upload
        System.out.println("Upload resource button clicked");
    }

    @FXML
    private void viewAllHomework() {
        // Implement view all homework
        System.out.println("View all homework button clicked");
    }

    @FXML
    private void enterClassroom() {
        // Implement enter classroom
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/classroom.fxml"));
            Parent classroomPage = loader.load();

            ClassroomController controller = loader.getController();
            controller.setCourseId(courseId);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Enter course button clicked");
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
