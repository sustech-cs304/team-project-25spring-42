package sustech.cs304.classroom;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class CourseHomeController {
    @FXML private Label courseTitle;
    @FXML private Label teacherName;
    @FXML private TextField newAnnouncement;
    @FXML private TableView<announcementItem> announcementTable;
    @FXML private TableView<ResourceItem> resourceTable;
    @FXML private TableView<HomeworkItem> homeworkTable;
    @FXML private TextField resourceSearch;
    @FXML private Button enterCourseBtn;

    @FXML
    private void initialize() {
        // Initialize sample data
        initializeAnnouncements();
        initializeResources();
        initializeHomework();
    }

    private void initializeAnnouncements() {
        ObservableList<String> announcements = FXCollections.observableArrayList(
                "5月10日：下周将进行期中考试",
                "5月5日：作业提交截止日期延长至5月15日",
                "4月28日：课程资料已更新"
        );
        // announcementList.setItems(announcements);
    }

    private void initializeResources() {
        ObservableList<ResourceItem> resources = FXCollections.observableArrayList(
                new ResourceItem("Lecture1.pdf", "PDF", "2023-05-01", "2.4MB"),
                new ResourceItem("Assignment1.docx", "Word", "2023-05-05", "1.2MB")
        );
        resourceTable.setItems(resources);
    }

    private void initializeHomework() {
        ObservableList<HomeworkItem> homework = FXCollections.observableArrayList(
                new HomeworkItem("作业1", "2023-05-20", "未提交", "提交"),
                new HomeworkItem("作业2", "2023-06-10", "已提交", "查看"),
                new HomeworkItem("实验报告", "2023-06-15", "未开始", "开始")
        );
        homeworkTable.setItems(homework);
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

    public static class announcementItem {
        private final String content;

        public announcementItem(String content) {
            this.content = content;
        }

        public String getContent() { return content; }
    }
}
