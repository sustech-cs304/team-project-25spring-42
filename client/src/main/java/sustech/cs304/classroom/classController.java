package sustech.cs304.classroom;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sustech.cs304.classroom.MenuBarController;

public class classController {


    @FXML
    public Button ThirdModeButton;
    @FXML
    public Button secondModeButton;
    @FXML
    public AnchorPane backgroundPane;
    public Button firstModeButton;
    public ScrollPane ClassChoiceScroll;
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



}