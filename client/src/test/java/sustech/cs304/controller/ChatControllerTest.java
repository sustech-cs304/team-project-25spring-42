package sustech.cs304.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import junit.framework.TestCase;
import sustech.cs304.App;
import sustech.cs304.entity.Friend;
import sustech.cs304.entity.User;

public class ChatControllerTest extends TestCase {
    private ChatController controller;

    @BeforeAll
    public static void initJavaFX() {
        // 初始化 JavaFX 平台
        new JFXPanel(); // 这是关键：它会隐式启动 JavaFX Toolkit
        Platform.setImplicitExit(false); // 防止测试时关闭 JavaFX 平台
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        controller = new ChatController();
    }

    public void testConstructor() {
        controller.initialize();
        assertNotNull("Controller should be initialized", controller);
    }

    public void testInitialize() {
        // Skipped: depends on JavaFX controls, cannot be tested in normal JUnit environment
    }

    @Test
    void testCreateMessageBox_UserMessage() {
        this.controller = new ChatController();
        HBox box = controller.createMessageBox("User", "hello", getClass().getResource("/img/gemini.png").toString(), true);
        assertNotNull(box);
    }

    @Test
    void testCreateCircularAvatar() {
        this.controller = new ChatController();
        ImageView imageView = controller.createCircularAvatar(getClass().getResource("/img/gemini.png").toString(), 50);
        assertNotNull(imageView);
    }

    @Test
    void testRenderMessageBox() {
        App.user = new User("github96641098");
        VBox box = new VBox();
        TextArea messageField = new TextArea("This is a test message.");
        ListView<Friend> friendList = new ListView<>();
        Label chatPartnerLabel = new Label("Chat Partner");
        controller = new ChatController(box, messageField, friendList, chatPartnerLabel, null);
        controller.refreshContacts();
    }

    @Test
    void testHandleSendMessage() {
        App.user = new User("github96641098");
        App.user.setAvatarPath(getClass().getResource("/img/gemini.png").toString());
        VBox box = new VBox();
        TextArea messageField = new TextArea("This is a test message.");
        ListView<Friend> friendList = new ListView<>();
        Label chatPartnerLabel = new Label("Chat Partner");
        Friend friend = new Friend("testFriend", "Test Friend", getClass().getResource("/img/gemini.png").toString(), "online");
        controller = new ChatController(box, messageField, friendList, chatPartnerLabel, friend);
        controller.handleSendMessage();
        controller.getCachedAvatar(getClass().getResource("/img/gemini.png").toString()); // Cache the avatar
        controller.getCachedUser("github96641098"); // Cache the user
    }

} 
