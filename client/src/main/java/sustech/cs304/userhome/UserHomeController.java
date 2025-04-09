package sustech.cs304.userhome;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class UserHomeController {

    public Button returnbutton;
    // 用户信息字段
    @FXML private Label userIdLabel;
    @FXML private TextField usernameField;
    @FXML private Label accountLabel;
    @FXML private PasswordField passwordField;
    @FXML private TextArea bioTextArea;
    @FXML private ImageView avatarImageView;
    @FXML private Label registerDateLabel;
    @FXML private Label lastLoginLabel;

    private User user;

    public Parent getIDEpane() {
        return IDEpane;
    }

    public void setIDEpane(Parent IDEpane) {
        this.IDEpane = IDEpane;
    }

    private Parent IDEpane;

    @FXML
    public void initialize() {
        user = User.getInstance();

        // 绑定UI控件
        bindUserDataToUI();

        returnbutton.setOnAction(event -> switchToIDE());
    }

    private void bindUserDataToUI() {
        userIdLabel.setText(user.getUserId());
        usernameField.setText(user.getUsername());
        accountLabel.setText(user.getAccount());
        passwordField.setText("********"); // 不显示真实密码
        bioTextArea.setText(user.getBio());
        registerDateLabel.setText(user.getRegisterDate());
        lastLoginLabel.setText(user.getLastLogin());

        String avatarUrl = user.getAvatarPath();
        // 加载头像
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            try {
                Image avatar = new Image(avatarUrl, true); // true 表示后台加载
                avatarImageView.setImage(avatar);
                // 可选：添加加载错误处理
                avatar.exceptionProperty().addListener((obs, old, newVal) -> {
                    if (newVal != null) {
                        System.err.println("加载头像失败: " + newVal.getMessage());
                    }
                });
            } catch (Exception e) {
                System.err.println("加载头像失败: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleChangeAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择头像图片");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("图片文件", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(avatarImageView.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image newAvatar = new Image(selectedFile.toURI().toString());
                avatarImageView.setImage(newAvatar);
                user.setAvatarPath(selectedFile.getAbsolutePath());

                // 在实际应用中，这里应该上传头像到服务器
                showAlert("头像已更新", "头像已成功更改", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("错误", "无法加载所选图片: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleChangePassword() {
        // 创建一个修改密码的对话框
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("修改密码");
        dialog.setHeaderText("请输入新密码");

        // 设置按钮
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // 创建密码输入字段
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("新密码");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("确认新密码");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("新密码:"), 0, 0);
        grid.add(newPasswordField, 1, 0);
        grid.add(new Label("确认密码:"), 0, 1);
        grid.add(confirmPasswordField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // 验证密码
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                if (!newPasswordField.getText().equals(confirmPasswordField.getText())) {
                    showAlert("错误", "两次输入的密码不一致", Alert.AlertType.ERROR);
                    return null;
                }
                if (newPasswordField.getText().length() < 6) {
                    showAlert("错误", "密码长度不能少于6位", Alert.AlertType.ERROR);
                    return null;
                }
                return newPasswordField.getText();
            }
            return null;
        });

        // 处理结果
        dialog.showAndWait().ifPresent(newPassword -> {
            // 在实际应用中，这里应该加密并保存新密码
            user.setPassword(newPassword);
            showAlert("成功", "密码已更新", Alert.AlertType.INFORMATION);
        });
    }

    @FXML
    private void handleSave() {
        // 获取UI上的修改
        user.setUsername(usernameField.getText());
        user.setBio(bioTextArea.getText());

        // 验证用户名
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            showAlert("错误", "用户名不能为空", Alert.AlertType.ERROR);
            return;
        }

        // 在实际应用中，这里应该将数据保存到数据库
        showAlert("保存成功", "用户信息已保存", Alert.AlertType.INFORMATION);

        // 更新最后登录时间
        user.setLastLogin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        lastLoginLabel.setText(user.getLastLogin());
    }

    @FXML
    private void handleLogout() {
        // 确认退出
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认退出");
        alert.setHeaderText("您确定要退出登录吗?");
        alert.setContentText("退出后将返回登录页面");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // 在实际应用中，这里应该关闭当前窗口并返回登录界面
                System.out.println("用户已退出登录");
            }
        });
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void switchToIDE() {
        // 加载新的FXML内容
        //Parent newContent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/IDE/IDE.fxml")));


        // 替换当前场景的内容
        Scene currentScene = returnbutton.getScene();
        currentScene.setRoot(IDEpane);

        // 或者如果你只想替换部分内容
        // backgroundPane.getChildren().setAll(newContent);

    }

}
