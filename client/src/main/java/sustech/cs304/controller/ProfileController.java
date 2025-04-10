package sustech.cs304.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;

public class ProfileController {
    @FXML
    private MenuBarController menuBarController;

    @FXML
    private ImageView userPicture;

    public void changeUserName() {

    }

    public void clickPicture(MouseEvent event) {
        // TODO: open folder to add/change user's picture
    }

    public void changePicture(String path) {
        Image picture = new Image(path);
        userPicture.setImage(picture);
    }

}
