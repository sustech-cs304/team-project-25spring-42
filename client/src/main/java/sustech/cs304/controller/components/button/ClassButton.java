package sustech.cs304.controller.components.button;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class ClassButton extends Button {
    @FXML private Label courseNameLabel;
    @FXML private Label teacherLabel;
    @FXML private Circle statusCircle;
    @FXML private ImageView courseIcon;
    @FXML private HBox rootContainer;

    private Long courseId;
    private boolean isActive;

    public ClassButton() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/classbutton.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            this.setText("");
            this.getStyleClass().add("course-button");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML", e);
        }
    }

    public void setCourseInfo(String name, String teacher, Long courseId) {
        courseNameLabel.setText(name);
        teacherLabel.setText(teacher);
        this.courseId = courseId;
    }

    public void setStatus(boolean isActive) {
        this.isActive = isActive;
        statusCircle.setFill(isActive ? Color.GREEN : Color.GRAY);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setCourseImage(Image image) {
        courseIcon.setImage(image);
    }
}
