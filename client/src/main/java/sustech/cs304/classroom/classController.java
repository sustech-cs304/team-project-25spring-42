package sustech.cs304.classroom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import sustech.cs304.classroom.MenuBarController;

import java.io.IOException;
import java.util.Objects;

public class classController {


    @FXML
    public Button ThirdModeButton;
    @FXML
    public Button secondModeButton;
    @FXML
    public AnchorPane backgroundPane;
    @FXML
    public Button firstModeButton;
    public ScrollPane ClassChoiceScroll;
    public AnchorPane editorPane;
    @FXML
    private MenuBarController menuBarController;

    private String css;

    @FXML
    private void initialize() {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            backgroundPane.setPrefHeight(1048);
            backgroundPane.setLayoutY(-32);
        }
        //menuBarController.setFileTreeController(fileTreeController);
        //menuBarController.setEditorController(editorController);
       // menuBarController.setclassController(this);
        //fileTreeController.setEditorController(editorController);
        //fileTreeController.setMYpdfReaderController(MYpdfReaderController);
        //editorController.setIdeController(this);
        //editorController.setBackground("vs-dark");





        VBox contentBox = new VBox(10);
        contentBox.setPadding(new Insets(10));
        ThirdModeButton.setOnAction(event -> switchToClass(contentBox));
        firstModeButton.setOnAction(event -> switchToIDE());
    }

    private void switchToIDE() {
        try {
            // 加载新的FXML内容
            Parent newContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/IDE/IDE.fxml")));

            // 替换当前场景的内容
            Scene currentScene = ThirdModeButton.getScene();
            currentScene.setRoot(newContent);

            // 或者如果你只想替换部分内容
            // backgroundPane.getChildren().setAll(newContent);

        } catch (IOException e) {
            e.printStackTrace();
            // 错误处理...
        }
    }

    @FXML
    private void switchToClass(VBox contentBox) {



        // 添加多个课程按钮示例
        addCourseButton(contentBox, "Java编程", "张老师", true, "/img/x.png");
        //addCourseButton(contentBox, "数据结构", "李教授", false, "/img/github.png");
        //addCourseButton(contentBox, "算法分析", "王老师", true, "/img/google.png");

        ClassChoiceScroll.setFitToWidth(true);
        ClassChoiceScroll.setContent(contentBox);
    }

    private void addCourseButton(VBox container, String course, String teacher,
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

        container.getChildren().add(btn);
    }

    public void changeTheme(String theme) {
        //editorController.setBackground(theme);
        //editorController.setTheme(theme);
        Scene scene = backgroundPane.getScene();
        if (scene != null) {
            scene.getStylesheets().remove(css);
        }
        css = this.getClass().getResource("/css/style-" + theme + ".css").toExternalForm();
        scene.getStylesheets().add(css);
    }



    private void showCourseHomePage(String courseName, String teacherName) {
        try {
            // 加载FXML文件
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