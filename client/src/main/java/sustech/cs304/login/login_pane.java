package sustech.cs304.login;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

public class login_pane {

    @FXML
    private PasswordField passwordField_account;

    @FXML
    private PasswordField passwordField_secret;

    @FXML
    private Button button_signup;

    @FXML
    private Button button_login;

    @FXML
    private Button button_glogin;

    @FXML
    private void okey_sign_up() {
        System.out.println("Sign Up button clicked");
    }

    @FXML
    private void okey_login() {
        System.out.println("Login button clicked");
    }

    @FXML
    private void okey_g_login() {
        System.out.println("GitHub Login button clicked");
    }
}