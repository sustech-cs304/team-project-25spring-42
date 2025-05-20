package sustech.cs304.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClassroomController {
    @FXML private Label courseTitle;
    @FXML private Label teacherName;

    private Long courseId;

    @FXML
    private void initialize() {

    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setTitle(String courseName, String teacherName) {
        this.courseTitle.setText(courseName);
        this.teacherName.setText(teacherName);

    }
}