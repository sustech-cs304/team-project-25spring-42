package sustech.cs304.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sustech.cs304.App;
import sustech.cs304.utils.AlterUtils;
import sustech.cs304.utils.StringUtils;

import java.io.File;

public class UserPageController {

    public Button returnbutton;

    @FXML private TextField usernameField, mailField, phoneField;

    @FXML private TextArea bioTextArea;
    @FXML private ImageView avatarImageView;
    @FXML private Label userIdLabel, registerDateLabel, lastLoginLabel;

    public Parent getIDEpane() {
        return IDEpane;
    }

    public void setIDEpane(Parent IDEpane) {
        this.IDEpane = IDEpane;
    }

    private Parent IDEpane;

    @FXML
    public void initialize() {
        bindUserDataToUI();
    }

    @FXML
    private void updateUsername() {
        String newUsername = usernameField.getText();
        if (newUsername != null && !newUsername.trim().isEmpty()) {
            boolean ifChange = AlterUtils.showConfirmationAlert(
                    (Stage) usernameField.getScene().getWindow(),
                    "Confirmation",
                    "Are you sure to change your username?",
                    "New Username: " + newUsername
            );
            if (ifChange) {
                App.user.setUsername(newUsername);
                App.userApi.updateUsernameById(App.user.getUserId(), newUsername);
            } else {
                usernameField.setText(App.user.getUsername());
            }
        } else {
            usernameField.setText(App.user.getUsername());
            AlterUtils.showInfoAlert(
                    (Stage) usernameField.getScene().getWindow(),
                    "Error",
                    "Invalid Username",
                    "Please enter a valid username"
            );
        }
    }

    @FXML
    private void updateEmail() {
        String newEmail = mailField.getText();
        if (newEmail != null && !newEmail.trim().isEmpty() && StringUtils.checkIfValidEmail(newEmail)) {
            boolean ifChange = AlterUtils.showConfirmationAlert(
                    (Stage) mailField.getScene().getWindow(),
                    "Comfirmation",
                    "Are you sure to change your email?",
                    "New Email: " + newEmail
            );
            if (ifChange) {
                App.user.setEmail(newEmail);
                App.userApi.updateMailById(App.user.getUserId(), newEmail);
            } else {
                mailField.setText(App.user.getEmail());
            }
        } else {
            mailField.setText(App.user.getEmail());
            AlterUtils.showInfoAlert(
                    (Stage) mailField.getScene().getWindow(),
                    "Error",
                    "Invalid Email",
                    "Please enter a valid email"
            );
        }
    }

    @FXML
    private void updatePhone() {
        String newPhone = phoneField.getText();
        if (newPhone != null && !newPhone.trim().isEmpty()) {
            boolean ifChange = AlterUtils.showConfirmationAlert(
                    (Stage) phoneField.getScene().getWindow(),
                    "Comfirmation",
                    "Are you sure to change your phone number?",
                    "New Phone Number: " + newPhone
            );
            if (ifChange) {
                App.user.setPhoneNumber(newPhone);
                App.userApi.updatePhoneById(App.user.getUserId(), newPhone);
            } else {
                phoneField.setText(App.user.getPhoneNumber());
            }
        } else {
            phoneField.setText(App.user.getPhoneNumber());
            AlterUtils.showInfoAlert(
                    (Stage) phoneField.getScene().getWindow(),
                    "Error",
                    "Invalid Phone Number",
                    "Please enter a valid phone number"
            );
        }
    }

    private void bindUserDataToUI() {
        userIdLabel.setText(App.user.getUserId());
        usernameField.setText(App.user.getUsername());
        mailField.setText(App.user.getEmail());
        phoneField.setText(App.user.getPhoneNumber());
        bioTextArea.setText(App.user.getBio());
        registerDateLabel.setText(App.user.getRegisterDate());
        lastLoginLabel.setText(App.user.getLastLogin());

        String avatarUrl = App.user.getAvatarPath();
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            try {
                Image avatar = new Image(avatarUrl, true);
                avatarImageView.setImage(avatar);
                avatar.exceptionProperty().addListener((obs, old, newVal) -> {
                    if (newVal != null) {
                        System.err.println("Failed to Load Avatar: " + newVal.getMessage() + " (URL: " + avatarUrl + ")");
                    }
                });
            } catch (Exception e) {
                System.err.println("Failed to Load Avatar: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleChangeAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Avatar Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(avatarImageView.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image newAvatar = new Image(selectedFile.toURI().toString());
                avatarImageView.setImage(newAvatar);
                App.user.setAvatarPath(selectedFile.getAbsolutePath());
                AlterUtils.showInfoAlert(
                        (Stage) avatarImageView.getScene().getWindow(),
                        "Success",
                        "Avatar Changed",
                        "Your avatar has been changed successfully."
                );
            } catch (Exception e) {
                AlterUtils.showInfoAlert(
                        (Stage) avatarImageView.getScene().getWindow(),
                        "Error",
                        "Failed to Change Avatar",
                        "An error occurred while changing your avatar: " + e.getMessage()
                );
            }
        }
    }

    @FXML
    private void handleLogout() {
    }
}
