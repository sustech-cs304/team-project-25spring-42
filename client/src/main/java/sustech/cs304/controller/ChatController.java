package sustech.cs304.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sustech.cs304.App;
import sustech.cs304.service.StompChatClient;
import sustech.cs304.entity.Friend;
import sustech.cs304.entity.User;
import sustech.cs304.service.FriendApi;
import sustech.cs304.service.FriendApiImpl;
import sustech.cs304.utils.AlterUtils;

import java.util.List;

public class ChatController {

    @FXML
    private VBox chatBox;

    @FXML
    private TextArea messageField;

    @FXML
    private ListView<Friend> contactsList;

    @FXML
    private Label chatPartnerLabel;

    private Friend currentContact = null;
    private StompChatClient client;

    @FXML
    private void initialize() {
        try {
            client = new StompChatClient(App.user.getUserId());
            client.setOnReceivedMessage((senderId, message) -> {
                if (currentContact != null && currentContact.getName().equals(senderId)) {
                    Platform.runLater(() -> showReceivedMessage(message));
                }
            });
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 联系人列表配置
        contactsList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Friend item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Label label = new Label(item.getName());
                    label.setGraphic(new ImageView(new Image(item.getAvatar())));
                    setGraphic(label);
                }
            }
        });

        contactsList.getItems().addAll(
                new Friend("Gemini", "Bot", getClass().getResource("/img/gemini.png").toString())
                // 可添加更多
        );

        FriendApi friendApi = new FriendApiImpl();
        List<User> friendList = friendApi.getFriendList(App.user.getUserId());
        for (User user : friendList) {
            contactsList.getItems().add(new Friend(user.getUsername(), "Friend", getClass().getResource("/img/user-black.png").toString()));
        }

        contactsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals(currentContact)) {
                currentContact = newVal;
                chatBox.getChildren().clear();
                chatPartnerLabel.setText(newVal.getName());
            }
        });

        messageField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                handleSendMessage();
                event.consume();
            }
        });
    }

    @FXML
    private void handleSendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty() && currentContact != null) {
            addUserMessage(message);
            messageField.clear();

            try {
                client.sendPrivateMessage(currentContact.getName(), message); // 注意这里用的是名字
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addUserMessage(String message) {
        Label label = createBubble(message, "-fx-background-color: lightblue;");
        HBox container = new HBox(label);
        container.setAlignment(Pos.CENTER_RIGHT);
        chatBox.getChildren().add(container);
    }

    private void showReceivedMessage(String messageText) {
        Label label = createBubble(messageText, "-fx-background-color: #e0e0e0;");
        HBox container = new HBox(label);
        container.setAlignment(Pos.CENTER_LEFT);
        chatBox.getChildren().add(container);
    }

    private Label createBubble(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style + " -fx-padding: 10; -fx-background-radius: 10;");
        label.setWrapText(true);
        label.setMaxWidth(300);
        return label;
    }

    @FXML
    private void showNewRequestList() {
        FriendApi friendApi = new FriendApiImpl();
        List<User> friendRequestList = friendApi.getFriendRequestList(App.user.getUserId());
        AlterUtils.showNewRequestList((Stage) this.messageField.getScene().getWindow(), friendRequestList);
    }
}
