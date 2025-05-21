package sustech.cs304.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sustech.cs304.App;
import sustech.cs304.controller.components.button.FriendButton;
import sustech.cs304.service.StompChatClient;
import sustech.cs304.service.clients.GeminiClient;
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
                System.out.println(senderId);
                System.out.println(currentContact.getName());
                if (currentContact != null && currentContact.getName().equals(senderId)) {
                    Platform.runLater(() -> showReceivedMessage(message));
                }
            });
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        contactsList.setCellFactory(list -> new ListCell<Friend>() {
            @Override
            protected void updateItem(Friend item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    FriendButton button = new FriendButton();
                    button.setName(item.getName()); 
                    button.setStatus(item.getStatus());
                    button.setAvatar(item.getAvatar() != null ? new Image(item.getAvatar()) : null);
                    setGraphic(button);
                }
            }
        });
        contactsList.getItems().addAll(
            new Friend("Bot", "Gemini", "Bot", getClass().getResource("/img/gemini.png").toString()),
            new Friend("Bot", "ChatGPT", "Bot", getClass().getResource("/img/chatgpt.png").toString()),
            new Friend("Bot", "DeepSeek", "Bot", getClass().getResource("/img/deepseek.png").toString())
        );

        FriendApi friendApi = new FriendApiImpl();
        List<User> friendList = friendApi.getFriendList(App.user.getUserId());
        for (User user : friendList) {
            contactsList.getItems().add(new Friend(user.getUserId(), user.getUsername(), "Friend", getClass().getResource("/img/user-black.png").toString()));
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

            String contactName = currentContact.getName();
            
            if (contactName.equals("Gemini")) {
                final String model = "gemini-2.0-flash";
                new Thread(() -> {
                    AIChatController aiChatController = new AIChatController();
                    String response = aiChatController.getResponse(model, message);
                    Platform.runLater(() -> showReceivedMessage(response));
                }).start();
                return;
            } else if (contactName.equals("ChatGPT")) {
                final String model = "gpt-3.5-turbo";
                new Thread(() -> {
                    AIChatController aiChatController = new AIChatController();
                    String response = aiChatController.getResponse(model, message);
                    Platform.runLater(() -> showReceivedMessage(response));
                }).start();
                return;
            } else if (contactName.equals("DeepSeek")) {
                final String model = "deepseek-chat";
                new Thread(() -> {
                    AIChatController aiChatController = new AIChatController();
                    String response = aiChatController.getResponse(model, message);
                    Platform.runLater(() -> showReceivedMessage(response));
                }).start();
                return;
            }
            
            try {
                client.sendPrivateMessage(currentContact.getId(), message);
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
