package sustech.cs304.classroom;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CourseHomeController {
    @FXML private Label courseTitle;
    @FXML private Label teacherName;
    @FXML private ListView<String> announcementList;
    @FXML private TextField newAnnouncement;
    @FXML private TableView<ResourceItem> resourceTable;
    @FXML private TextField resourceSearch;
    @FXML private Button enterCourseBtn;

    // 初始化方法
    @FXML
    private void initialize() {
        // 设置进入课程按钮的点击事件
        enterCourseBtn.setOnAction(event -> {
            System.out.println("进入课程按钮被点击");
            // 这里添加进入课程的具体逻辑
        });
    }

    // 资源项数据模型（内部类）
    public static class ResourceItem {
        private final String fileName;
        private final String fileType;
        private final String date;
        private final String size;

        public ResourceItem(String fileName, String fileType, String date, String size) {
            this.fileName = fileName;
            this.fileType = fileType;
            this.date = date;
            this.size = size;
        }

        // Getter方法
        public String getFileName() { return fileName; }
        public String getFileType() { return fileType; }
        public String getDate() { return date; }
        public String getSize() { return size; }
    }
}