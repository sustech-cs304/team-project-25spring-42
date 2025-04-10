package sustech.cs304.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
}
