package sustech.cs304.utils;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sustech.cs304.App;
import sustech.cs304.entity.Announce;
import sustech.cs304.entity.Assignment;
import sustech.cs304.entity.Course;
import sustech.cs304.entity.Resource;
import sustech.cs304.entity.Submission;
import sustech.cs304.entity.User;
import sustech.cs304.service.CourseApi;
import sustech.cs304.service.CourseApiImpl;
import sustech.cs304.service.FriendApi;
import sustech.cs304.service.FriendApiImpl;

/**
 * AI-generated-content
 * tool: copilot
 * version: latest
 * usage: copilot is used to generate the AlterUtils class, which provides utility methods.
 */
public class AlterUtils {
    public static boolean showConfirmationAlert(Stage owner, String title, String header, String content) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle(title);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Label headerLabel = new Label(header);
        headerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        Label contentLabel = new Label(content);

        Button okButton = new Button("Yes");
        Button cancelButton = new Button("No");
        HBox buttonBox = new HBox(10, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(headerLabel, contentLabel, buttonBox);

        Scene dialogScene = new Scene(vbox, 400, 300);
        dialogStage.setScene(dialogScene);

        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }

        final boolean[] result = {false};

        okButton.setOnAction(e -> {
            result[0] = true;
            dialogStage.close();
        });
        cancelButton.setOnAction(e -> {
            result[0] = false;
            dialogStage.close();
        });

        dialogStage.centerOnScreen();
        dialogStage.showAndWait();

        return result[0];
    }

    public static void showInfoAlert(Stage owner, String title, String header, String content) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle(title);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Label headerLabel = new Label(header);
        headerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
        Label contentLabel = new Label(content);

        Button okButton = new Button("OK");
        HBox buttonBox = new HBox(10, okButton);
        buttonBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(headerLabel, contentLabel, buttonBox);

        Scene dialogScene = new Scene(vbox, 400, 300);
        dialogStage.setScene(dialogScene);

        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }

        okButton.setOnAction(e -> dialogStage.close());

        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
    }

    public static Announce showAnnounceInputForm(Stage owner, String courseId) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle("New Announcement");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER_LEFT);
        Label nameLabel = new Label("Announcement Name:");
        TextField nameField = new TextField();
        Label contentLabel = new Label("Announcement Content:");
        TextArea contentField = new TextArea();
        CheckBox visibleCheckBox = new CheckBox("Visible");
        visibleCheckBox.setSelected(true);
        vbox.getChildren().addAll(nameLabel, nameField, contentLabel, contentField, visibleCheckBox);
        Button okButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(buttonBox);
        Scene dialogScene = new Scene(vbox, 400, 300);
        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }
        dialogStage.setScene(dialogScene);
        final Announce[] result = {null};
        final boolean[] submitted = {false};

        okButton.setOnAction(e -> {
            result[0] = new Announce(null, courseId, nameField.getText(), LocalDate.now().toString(), contentField.getText(), visibleCheckBox.isSelected());
            submitted[0] = true;
            dialogStage.close();
        });

        cancelButton.setOnAction(e -> dialogStage.close());
        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
        if (submitted[0]) {
            return result[0];
        } else {
            return null;
        }
    }

    public static Assignment showAssignmentInputForm(Stage owner) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle("New Assignment");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER_LEFT);
        Label nameLabel = new Label("Assignment Name:");
        TextField nameField = new TextField();
        Label deadlineLabel = new Label("Deadline:");
        DatePicker deadlinePicker = new DatePicker();
        deadlinePicker.setValue(LocalDate.now());
        Label hourLabel = new Label("Hour:");
        Spinner<Integer> hourSpinner = new Spinner<>(0, 23, 0);
        hourSpinner.setEditable(true);
        Label minuteLabel = new Label("Minute:");
        Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
        minuteSpinner.setEditable(true);
        Label fileLabel = new Label("File:");
        TextField fileField = new TextField();
        fileField.setEditable(false);
        fileField.setPromptText("No file selected");
        CheckBox visibleCheckBox = new CheckBox("Visible");
        visibleCheckBox.setSelected(true);
        fileField.setPrefWidth(200);
        Button fileButton = new Button("Select File");
        fileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Assignment File");
            File file = fileChooser.showOpenDialog(dialogStage);
            if (file != null) {
                fileField.setText(file.getAbsolutePath());
            }
        });
        vbox.getChildren().addAll(nameLabel, nameField, deadlineLabel, deadlinePicker, hourLabel, hourSpinner, minuteLabel, minuteSpinner, fileLabel, fileField, fileButton, visibleCheckBox);
        Button okButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(buttonBox);
        Scene dialogScene = new Scene(vbox, 400, 500);
        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }
        dialogStage.setScene(dialogScene);
        final Assignment[] result = {null};
        final boolean[] submitted = {false};
        okButton.setOnAction(e -> {
            int hour = hourSpinner.getValue();
            int minute = minuteSpinner.getValue();
            LocalDateTime deadline = LocalDateTime.of(deadlinePicker.getValue(), LocalTime.of(hour, minute, 0));

            result[0] = new Assignment(null, nameField.getText(), deadline.toString(), null, visibleCheckBox.isSelected(), fileField.getText(), null, null);
            submitted[0] = true;
            dialogStage.close();
        });
        cancelButton.setOnAction(e -> dialogStage.close());
        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
        if (submitted[0]) {
            return result[0];
        } else {
            return null;
        }
    }

    public static Resource showResourceInputForm(Stage owner) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle("New Resource");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER_LEFT);
        Label fileLabel = new Label("File:");
        TextField fileField = new TextField();
        fileField.setEditable(false);
        fileField.setPromptText("No file selected");
        fileField.setPrefWidth(200);
        Button fileButton = new Button("Select File");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Resource File");
        fileButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(dialogStage);
            if (file != null) {
                fileField.setText(file.getAbsolutePath());
            }
        });
        vbox.getChildren().addAll(fileLabel, fileField, fileButton);

        Button okButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(buttonBox);
        Scene dialogScene = new Scene(vbox, 400, 300);
        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }
        dialogStage.setScene(dialogScene);
        final Resource[] result = {null};
        final boolean[] submitted = {false};
        okButton.setOnAction(e -> {
            result[0] = new Resource(null, fileField.getText(), null, null, null, null, null, true);
            submitted[0] = true;
            dialogStage.close();
        });
        cancelButton.setOnAction(e -> dialogStage.close());
        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
        if (submitted[0]) {
            return result[0];
        } else {
            return null;
        }
    }

    public static Map<String, String> showInputForm(Stage owner, String title, List<String> fields) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle(title);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER_LEFT);

        Map<String, TextField> inputFields = new LinkedHashMap<>();
        for (String field : fields) {
            Label label = new Label(field + ":");
            TextField textField = new TextField();
            vbox.getChildren().addAll(label, textField);
            inputFields.put(field, textField);
        }

        Button okButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(buttonBox);

        Scene dialogScene = new Scene(vbox, 400, 300);
        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }
        dialogStage.setScene(dialogScene);

        final Map<String, String> result = new HashMap<>();
        final boolean[] submitted = {false};

        okButton.setOnAction(e -> {
            for (Map.Entry<String, TextField> entry : inputFields.entrySet()) {
                result.put(entry.getKey(), entry.getValue().getText());
            }
            submitted[0] = true;
            dialogStage.close();
        });

        cancelButton.setOnAction(e -> dialogStage.close());

        dialogStage.centerOnScreen();
        dialogStage.showAndWait();

        return submitted[0] ? result : null;
    }

    public static Map<String, String> courseInputForm(Stage owner) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle("New Course");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label("Course Name:");
        TextField nameField = new TextField();
        Label descriptionLabel = new Label("Course Description:");
        TextArea descriptionField = new TextArea();
        descriptionField.setWrapText(true);

        vbox.getChildren().addAll(nameLabel, nameField, descriptionLabel, descriptionField);

        Button okButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(buttonBox);

        Scene dialogScene = new Scene(vbox, 400, 300);
        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }
        dialogStage.setScene(dialogScene);

        final Map<String, String> result = new HashMap<>();
        final boolean[] submitted = {false};

        okButton.setOnAction(e -> {
            result.put("courseName", nameField.getText());
            result.put("courseDescription", descriptionField.getText());
            submitted[0] = true;
            dialogStage.close();
        });

        cancelButton.setOnAction(e -> dialogStage.close());

        dialogStage.centerOnScreen();
        dialogStage.showAndWait();

        return submitted[0] ? result : null;
    }

    public static void showMemberList(Stage owner, List<User> members) {

        FriendApi friendApi = new FriendApiImpl();
        List<User> friends = friendApi.getFriendList(App.user.getUserId());
        List<User> friendRequests = friendApi.getFriendRequestList(App.user.getUserId());

        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle("Member List");

        TableView<User> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));

        TableColumn<User, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getUserId())));

        TableColumn<User, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button addButton = new Button();

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                    return;
                }

                User user = getTableView().getItems().get(getIndex());
                addButton.setDisable(false); // reset

                if (user.getUserId().equals(App.user.getUserId())) {
                    addButton.setText("You");
                    addButton.setDisable(true);
                    addButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
                } else if (friends.stream().anyMatch(friend -> friend.getUserId().equals(user.getUserId()))) {
                    addButton.setText("Friend");
                    addButton.setDisable(true);
                    addButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
                } else if (friendRequests.stream().anyMatch(request -> request.getUserId().equals(user.getUserId()))) {
                    addButton.setText("Request Sent");
                    addButton.setDisable(true);
                    addButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
                } else {
                    addButton.setText("Add Friend");
                    addButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
                    addButton.setOnAction(e -> {
                        friendApi.applyFriendship(App.user.getUserId(), user.getUserId());
                        addButton.setText("Request Sent");
                        addButton.setDisable(true);
                        addButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
                    });
                }

                setGraphic(addButton);
            }
        });

        tableView.getColumns().addAll(usernameCol, userIdCol, actionCol);
        tableView.getItems().addAll(members);

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> dialogStage.close());
        HBox buttonBox = new HBox(10, okButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        VBox vbox = new VBox(10, tableView, buttonBox);
        vbox.setPadding(new Insets(20));

        Scene dialogScene = new Scene(vbox, 500, 350);
        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }

        dialogStage.setScene(dialogScene);
        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
    }

    public static void showNewRequestList(Stage owner, List<User> requests) {
        FriendApi friendApi = new FriendApiImpl();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle("New Friend Requests");

        TableView<User> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));

        TableColumn<User, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getUserId())));

        TableColumn<User, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button acceptButton = new Button("Accept");
            private final Button rejectButton = new Button("Reject");
            {
                acceptButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
                rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                acceptButton.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    friendApi.acceptFriendship(user.getUserId(), App.user.getUserId());
                    acceptButton.setText("Accepted");
                    acceptButton.setDisable(true);
                    acceptButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
                    rejectButton.setDisable(true);
                    rejectButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");

                });
                rejectButton.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    friendApi.rejectFriendship(user.getUserId(), App.user.getUserId());
                    acceptButton.setDisable(true);
                    acceptButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
                    rejectButton.setText("Rejected");
                    rejectButton.setDisable(true);
                    rejectButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, acceptButton, rejectButton);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });

        tableView.getColumns().addAll(usernameCol, userIdCol, actionCol);
        tableView.getItems().addAll(requests);

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> dialogStage.close());
        HBox buttonBox = new HBox(10, okButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        VBox vbox = new VBox(10, tableView, buttonBox);
        vbox.setPadding(new Insets(20));

        Scene dialogScene = new Scene(vbox, 500, 350);
        dialogStage.setScene(dialogScene);
        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
    }

    public static List<String> showInvitationInputForm(Stage owner) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle("New Invitation");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER_LEFT);
        Label nameLabel = new Label("Invitation Id list:");
        TextArea idField = new TextArea();
        idField.setWrapText(true);
        idField.setPromptText("Enter user IDs line by line");
        idField.setPrefHeight(100);
        vbox.getChildren().addAll(nameLabel, idField);
        Button okButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(buttonBox);
        Scene dialogScene = new Scene(vbox, 400, 300);
        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }
        dialogStage.setScene(dialogScene);
        final List<String>[] result = new List[1];
        final boolean[] submitted = {false};
        okButton.setOnAction(e -> {
            String text = idField.getText();
            String[] ids = text.split("\\s+");
            result[0] = List.of(ids);
            submitted[0] = true;
            dialogStage.close();
        });
        cancelButton.setOnAction(e -> dialogStage.close());
        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
        if (submitted[0]) {
            return result[0];
        } else {
            return null;
        }
    }

    public static void showInvitationList(Stage owner, List<Course> courses){
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle("Invitation List");
        TableView<Course> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Course, String> courseNameCol = new TableColumn<>("Course Name");
        courseNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseName()));
        TableColumn<Course, String> courseIdCol = new TableColumn<>("Course ID");
        courseIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        TableColumn<Course, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button acceptButton = new Button("Accept");
            private final Button rejectButton = new Button("Reject");
            {
                acceptButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
                rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                acceptButton.setOnAction(e -> {
                    Course course = getTableView().getItems().get(getIndex());
                    CourseApi courseApi = new CourseApiImpl();
                    String userId = App.user.getUserId();
                    courseApi.acceptCourseInvitation(course.getId(), userId);
                });
                rejectButton.setOnAction(e -> {
                    Course course = getTableView().getItems().get(getIndex());
                    CourseApi courseApi = new CourseApiImpl();
                    String userId = App.user.getUserId();
                    courseApi.rejectCourseInvitation(course.getId(), userId);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, acceptButton, rejectButton);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }

        });
        tableView.getColumns().addAll(courseNameCol, courseIdCol, actionCol);
        tableView.getItems().addAll(courses);
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> dialogStage.close());
        HBox buttonBox = new HBox(10, okButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
        VBox vbox = new VBox(10, tableView, buttonBox);
        vbox.setPadding(new Insets(20));
        Scene dialogScene = new Scene(vbox, 500, 350);
        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }
        dialogStage.setScene(dialogScene);
        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
    }

    public static void showSubmission(Stage owner, Long assignmentId) {
        CourseApi courseApi = new CourseApiImpl();
        List<Submission> submissions = courseApi.getSubmissionByAssignmentId(assignmentId);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(owner);
        dialogStage.setResizable(false);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle("Submission List");
        TableView<Submission> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Submission, String> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getUserId())));
        TableColumn<Submission, String> operationCol = new TableColumn<>("Download");
        operationCol.setCellFactory(col -> new TableCell<>() {
            private final Button downloadButton = new Button("Download");
            {
                downloadButton.getStyleClass().add("operation-button");
                downloadButton.setOnAction(e -> {
                    Submission submission = getTableView().getItems().get(getIndex());
                    String address = submission.getAddress();
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save File");
                    File file = fileChooser.showSaveDialog(dialogStage);
                    if (file != null) {
                        courseApi.downloadResource(address, file.getAbsolutePath());
                    }
                });
            }

            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(downloadButton);
                }
            }
        });
        tableView.getColumns().addAll(userIdCol, operationCol);
        tableView.getItems().addAll(submissions);
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> dialogStage.close());
        HBox buttonBox = new HBox(10, okButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
        VBox vbox = new VBox(10, tableView, buttonBox);
        vbox.setPadding(new Insets(20));
        Scene dialogScene = new Scene(vbox, 500, 350);
        Scene ownerScene = owner.getScene();
        if (ownerScene != null && !ownerScene.getStylesheets().isEmpty()) {
            dialogScene.getStylesheets().addAll(ownerScene.getStylesheets());
        }
        dialogStage.setScene(dialogScene);
        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
    }

}
