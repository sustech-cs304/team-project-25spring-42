package sustech.cs304.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sustech.cs304.App;
import sustech.cs304.entity.Announce;
import sustech.cs304.entity.Assignment;
import sustech.cs304.entity.Resource;
import sustech.cs304.service.CourseApi;
import sustech.cs304.service.CourseApiImpl;
import javafx.collections.ObservableList;
import sustech.cs304.utils.AlterUtils;


import java.util.List;

import javafx.collections.FXCollections;

public class CoursePageController {
    @FXML private Label courseTitle;
    @FXML private Label teacherName;
    @FXML private TextField newAnnouncement;
    @FXML private TableView<AnnouncementItem> announcementTable;
    @FXML private TableView<ResourceItem> resourceTable;
    @FXML private TableView<HomeworkItem> homeworkTable;
    @FXML private TextField resourceSearch;
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
        initializeHomework();
    }

    private void initializeAnnouncements() {
        List<Announce> announcements = courseApi.getAnnounceByCourseId(courseId);
        ObservableList<AnnouncementItem> announcementItems = FXCollections.observableArrayList();
        for (Announce announce : announcements) {
            announcementItems.add(new AnnouncementItem(announce.getAnnounceName(), announce.getUpLoadTime(), announce));
        }
        announcementTable.setItems(announcementItems);

        TableColumn<AnnouncementItem, String> actionColumn = (TableColumn<AnnouncementItem, String>) announcementTable.getColumns().get(2);
        actionColumn.setCellFactory(col -> new TableCell<AnnouncementItem, String>() {
            private final Button viewButton = new Button("View");

            {
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
            resourceItems.add(new ResourceItem(resource.getResourceName(), resource.getType(), resource.getUploadTime(), resource.getSize()));
        }
        resourceTable.setItems(resourceItems);
    }

    private void initializeHomework() {
        List<Assignment> assignments = courseApi.getAssignmentByCourseId(courseId, App.user.getUserId());
        ObservableList<HomeworkItem> homeworkItems = FXCollections.observableArrayList();
        for (Assignment assignment : assignments) {
            String status;
            if (assignment.getWhetherSubmitted()) {
                status = "Submited";
            } else {
                status = "Not Submitted";
            }
            homeworkItems.add(new HomeworkItem(assignment.getAssignmentName(), assignment.getDeadline(), status, "Submit"));
        }
        homeworkTable.setItems(homeworkItems);
    }

    @FXML
    private void publishAnnouncement() {
        // String announcement = newAnnouncement.getText();
        // if (!announcement.isEmpty()) {
        //     announcementList.getItems().add(0, announcement);
        //     newAnnouncement.clear();
        // }
    }

    @FXML
    private void searchResources() {
        String keyword = resourceSearch.getText();
        // Implement search functionality
        System.out.println("Searching for: " + keyword);
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
    private void enterCourse() {
        // Implement enter course
        System.out.println("Enter course button clicked");
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public static class ResourceItem {
        private final String fileName;
        private final String fileType;
        private final String date;
        private final String size;
        private final String action;

        public ResourceItem(String fileName, String fileType, String date, String size) {
            this.fileName = fileName;
            this.fileType = fileType;
            this.date = date;
            this.size = size;
            this.action = "下载";
        }

        public String getFileName() { return fileName; }
        public String getFileType() { return fileType; }
        public String getDate() { return date; }
        public String getSize() { return size; }
        public String getAction() { return action; }
    }

    public static class HomeworkItem {
        private final String name;
        private final String dueDate;
        private final String status;
        private final String action;

        public HomeworkItem(String name, String dueDate, String status, String action) {
            this.name = name;
            this.dueDate = dueDate;
            this.status = status;
            this.action = action;
        }

        public String getName() { return name; }
        public String getDueDate() { return dueDate; }
        public String getStatus() { return status; }
        public String getAction() { return action; }
    }

    public static class AnnouncementItem {
        private final String name;
        private final String time;
        private final String action;
        private final Announce announce;

        public AnnouncementItem(String name, String uploadTime, Announce announce) {
            this.name = name;
            this.time = uploadTime;
            this.announce = announce;
            this.action = "view";
        }

        public String getName() { return name; }
        public String getTime() { return time; }
        public String getAction() { return action; }
        public Announce getAnnounce() { return announce; }
    }
}
