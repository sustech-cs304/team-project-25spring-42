package sustech.cs304.classroom;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp2 extends Application {

    private int buttonCount = 1; // 用于给按钮编号
    private VBox contentBox;     // 用于存放所有特殊按钮的容器

    @Override
    public void start(Stage primaryStage) {
        // 1. 创建主布局
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // 2. 创建顶部区域 - 添加按钮
        Button addButton = new Button("添加特殊按钮");
        addButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 15;");

        // 3. 创建中央区域 - ScrollPane
        contentBox = new VBox(10); // 按钮之间的垂直间距
        contentBox.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true); // 让内容宽度适应ScrollPane
        scrollPane.setStyle("-fx-background: white; -fx-border-color: lightgray;");

        // 4. 布局组装
        root.setTop(addButton);
        BorderPane.setAlignment(addButton, Pos.CENTER);
        BorderPane.setMargin(addButton, new Insets(0, 0, 15, 0));

        root.setCenter(scrollPane);

        // 5. 设置按钮点击事件
        addButton.setOnAction(e -> {
            // 创建特殊样式的按钮
            Button specialButton = createSpecialButton();
            contentBox.getChildren().add(specialButton);
        });

        // 6. 创建场景并显示
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("按钮添加示例");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 创建特殊样式的按钮
    private Button createSpecialButton() {
        Button button = new Button("特殊按钮 #" + buttonCount++);

        // 设置样式
        button.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-color: linear-gradient(to bottom, #4a90e2, #357bd8); " +
                        "-fx-background-radius: 5px; " +
                        "-fx-padding: 8 15; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 1);"
        );

        // 鼠标悬停效果
        button.setOnMouseEntered(e -> {
            button.setStyle(button.getStyle() +
                    "-fx-background-color: linear-gradient(to bottom, #5a9cec, #458be8);");
        });

        button.setOnMouseExited(e -> {
            button.setStyle(button.getStyle() +
                    "-fx-background-color: linear-gradient(to bottom, #4a90e2, #357bd8);");
        });

        // 点击事件
        button.setOnAction(e -> {
            System.out.println("特殊按钮被点击: " + button.getText());
        });

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}