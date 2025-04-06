package sustech.cs304.classroom;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // 创建课程按钮1
        ClassButton javaButton = new ClassButton();
        javaButton.setCourseInfo("Java编程基础", "张教授");
        javaButton.setStatus(true); // 正在上课
        javaButton.setCourseImage(new Image(getClass().getResourceAsStream("/img/github.png")));

        // 设置点击事件
        javaButton.setOnAction(e -> {
            System.out.println("Java课程被点击");
            javaButton.setStatus(!javaButton.isActive()); // 使用isActive()方法
        });

        // 创建课程按钮2
        ClassButton mathButton = new ClassButton();
        mathButton.setCourseInfo("高等数学", "李教授");
        mathButton.setStatus(false); // 未上课
        mathButton.setCourseImage(new Image(getClass().getResourceAsStream("/img/x.png")));

        mathButton.setOnAction(e -> {
            System.out.println("数学课程被点击");
            mathButton.setStatus(!mathButton.isActive()); // 使用isActive()方法
        });

        // 创建主布局
        VBox root = new VBox(15, javaButton, mathButton);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #f5f5f5;");

        // 创建场景
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/css/classbutton.css").toExternalForm());

        // 设置舞台
        primaryStage.setTitle("课程按钮示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
