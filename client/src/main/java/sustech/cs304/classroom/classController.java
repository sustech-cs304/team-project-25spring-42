package sustech.cs304.classroom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

import java.io.IOException;

public class ClassController {


    @FXML
    public AnchorPane backgroundPane;
    @FXML
    public ScrollPane classChoiceScroll;
    @FXML
    public AnchorPane editorPane, fileTreePane;

    private VBox contentBox;

    private String css;

    @FXML
    private void initialize() {
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
        addCourseButton("计算机科学导论", "张老师", true, "/img/x.png");
        addCourseButton("数据结构与算法", "李老师", false, "/img/x.png");
    }

    private void addCourseButton(String course, String teacher,
                                 boolean active, String imagePath) {
        ClassButton btn = new ClassButton();
        btn.setCourseInfo(course, teacher);
        btn.setStatus(active);

        try {
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            btn.setCourseImage(img);
        } catch (Exception e) {
            System.err.println("无法加载图片: " + imagePath);

        }

        // 设置点击事件
        btn.setOnAction(e -> {
            System.out.println("已选择课程: " + course);
            btn.setStatus(!btn.isActive());
            showCourseHomePage("课程主页", "请从左侧选择课程");
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



    private void showCourseHomePage(String courseName, String teacherName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CourseHomePage.fxml"));
            Parent courseHomePage = loader.load();

            // 获取控制器实例
            CourseHomeController controller = loader.getController();

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
