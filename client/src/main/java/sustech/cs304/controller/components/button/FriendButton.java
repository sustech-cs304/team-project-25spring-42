package sustech.cs304.controller.components.button;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class FriendButton extends HBox {
    @FXML private ImageView avatarImage;
    @FXML private Label nameLabel;
    @FXML private Label statusLabel;
    @FXML private Circle statusDot;
    @FXML private HBox root;

    public FriendButton() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendButton.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setName(String name) {
        nameLabel.setText(name);
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
        if ("Online".equals(status)) {
            statusDot.setFill(Color.LIMEGREEN);
        } else if ("Bot".equals(status)) {
            statusDot.setFill(Color.BLUE);
        } else {
            statusDot.setFill(Color.GRAY);
        }
    }

    public void setAvatar(Image image) {
        avatarImage.setImage(image);
    }
}
