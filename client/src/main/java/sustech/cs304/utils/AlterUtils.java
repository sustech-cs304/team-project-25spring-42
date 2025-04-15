package sustech.cs304.utils;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sustech.cs304.entity.Announce;
import sustech.cs304.entity.Assignment;
import sustech.cs304.entity.Resource;

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

            result[0] = new Assignment(null, nameField.getText(), deadline.toString(), null, visibleCheckBox.isSelected(), fileField.getText());
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
        Label nameLabel = new Label("Resource Name:");
        TextField nameField = new TextField();
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
        final Resource result = null;
        final boolean[] submitted = {false};
        okButton.setOnAction(e -> {
            submitted[0] = true;
            dialogStage.close();
        });
        cancelButton.setOnAction(e -> dialogStage.close());
        dialogStage.centerOnScreen();
        dialogStage.showAndWait();
        if (submitted[0]) {
            return result;
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
}
