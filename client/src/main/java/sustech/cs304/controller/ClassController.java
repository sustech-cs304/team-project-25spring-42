package sustech.cs304.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sustech.cs304.App;
import sustech.cs304.controller.components.button.ClassButton;
import sustech.cs304.entity.Course;
import sustech.cs304.service.CourseApi;
import sustech.cs304.service.CourseApiImpl;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

public class ClassController {


    @FXML
    public AnchorPane backgroundPane;
    @FXML
    public ScrollPane classChoiceScroll;
    @FXML
    public AnchorPane editorPane, fileTreePane;
    private VBox contentBox;

    private String css;

    private CourseApi courseApi;
    private List<Course> courseList;

    @FXML
    private void initialize() {
        courseApi = new CourseApiImpl();
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            backgroundPane.setPrefHeight(1048);
            backgroundPane.setLayoutY(-32);
        }

        this.contentBox = new VBox(10);
        this.contentBox.getStyleClass().add("vbox");
        this.contentBox.setPadding(new Insets(10));
        classChoiceScroll.setFitToWidth(true);
        classChoiceScroll.setContent(contentBox);

        initializeClassChoiceScroll();
    }

    private void initializeClassChoiceScroll() {
        List<Long> courseIds = courseApi.getCourseIdByUserId(App.user.getUserId());
        for (Long courseId : courseIds) {
            Course course = courseApi.getCourseById(courseId);
            String teacherName = App.userApi.getUsernameById(course.getAdminId());
            if (course != null) {
                addCourseButton(course.getCourseName(), teacherName,
                        course.isOpening(), "/img/x.png", courseId);
            }
        }
    }

    private void addCourseButton(String course, String teacher,
                                 boolean active, String imagePath, Long courseId) {
        ClassButton btn = new ClassButton();
        btn.setCourseInfo(course, teacher, courseId);
        btn.setStatus(active);

        try {
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            btn.setCourseImage(img);
        } catch (Exception e) {
            System.err.println("无法加载图片: " + imagePath);

        }

        // 设置点击事件
        btn.setOnAction(e -> {
            btn.setStatus(!btn.isActive());
            showCourseHomePage("课程主页", "请从左侧选择课程", courseId);
        });

        this.contentBox.getChildren().add(btn);
    }

    public void changeTheme(String theme) {
        Scene scene = backgroundPane.getScene();
        if (scene != null) {
            scene.getStylesheets().remove(css);
        }
        css = this.getClass().getResource("/css/style-" + theme + ".css").toExternalForm();
        scene.getStylesheets().add(css);
    }



    private void showCourseHomePage(String courseName, String teacherName, Long courseId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CourseHomePage.fxml"));
            Parent courseHomePage = loader.load();

            // 获取控制器实例
            CoursePageController controller = loader.getController();
            controller.setCourseId(courseId);
            controller.loadData();

            // 设置课程名称和教师姓名
            //controller.setCourseInfo(courseName, teacherName);

            // 清空原有内容
            editorPane.getChildren().clear();

            // 将加载的FXML内容添加到编辑器面板
            editorPane.getChildren().add(courseHomePage);
            AnchorPane.setTopAnchor(courseHomePage, 0.0);
            AnchorPane.setLeftAnchor(courseHomePage, 0.0);
            AnchorPane.setRightAnchor(courseHomePage, 0.0);
            AnchorPane.setBottomAnchor(courseHomePage, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
