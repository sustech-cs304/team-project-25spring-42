package sustech.cs304.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sustech.cs304.App;
import sustech.cs304.controller.components.button.FriendButton;
import sustech.cs304.service.StompChatClient;
import sustech.cs304.entity.Friend;
import sustech.cs304.entity.User;
import sustech.cs304.entity.Course;
import sustech.cs304.service.FriendApi;
import sustech.cs304.service.CourseApi;
import sustech.cs304.service.FriendApiImpl;
import sustech.cs304.service.CourseApiImpl;
import sustech.cs304.utils.AlterUtils;
import sustech.cs304.service.ChatApi;
import sustech.cs304.service.ChatApiImpl;
import sustech.cs304.entity.ChatMessage;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private final Map<String, Image> avatarCache = new HashMap<>();
    private final Map<String, User> userCache = new HashMap<>();

    @FXML
    private void initialize() {

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

                    String avatarUrl = item.getAvatar();
                    if (avatarUrl != null && !avatarUrl.isEmpty()) {
                        try {
                            Image avatar = getCachedAvatar(avatarUrl);
                            button.setAvatar(avatar);
                            avatar.exceptionProperty().addListener((obs, old, newVal) -> {
                                if (newVal != null) {
                                    System.err.println("Failed to Load Avatar: " + newVal.getMessage() + " (URL: " + avatarUrl + ")");
                                }
                            });
                        } catch (Exception e) {
                            System.err.println("Failed to Load Avatar: " + e.getMessage());
                        }
                    }

                    setGraphic(button);
                }
            }
        });

        contactsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals(currentContact)) {
                ChatApi chatApi = new ChatApiImpl();
                currentContact = newVal;
                chatBox.getChildren().clear();
                chatPartnerLabel.setText(newVal.getName());
                if (newVal.getId().equals("Bot")) return;
                if (newVal.getStatus().equals("Friend")) {
                    List<ChatMessage> messages = chatApi.getChatMessages(App.user.getUserId(), newVal.getId());
                    if (messages == null) return;
                    List<Node> messageNodes = new ArrayList<>();

                    for (ChatMessage message : messages) {
                        boolean isUser = message.getSenderId().equals(App.user.getUserId());
                        String username = isUser ? App.user.getUsername() : currentContact.getName();
                        String avatar = isUser ? App.user.getAvatarPath() : currentContact.getAvatar();

                        HBox messageBox = createMessageBox(username, message.getMessage(), avatar, isUser);
                        messageNodes.add(messageBox);
                    }
                    chatBox.getChildren().addAll(messageNodes);
                } else if (newVal.getStatus().equals("Course")) {
                    List <ChatMessage> messages = chatApi.getGroupMessages(newVal.getId());
                    if (messages == null) return;
                    List<Node> messageNodes = new ArrayList<>();

                    for (ChatMessage message : messages) {
                        boolean isUser = message.getSenderId().equals(App.user.getUserId());
                        String username, avatar;
                        if (!isUser) {
                            String otherId = message.getSenderId();
                            // User sender = App.userApi.getUserById(otherId);
                            User sender = getCachedUser(otherId);
                            username = sender.getUsername();
                            avatar = sender.getAvatarPath();
                        } else {
                            username = App.user.getUsername();
                            avatar = App.user.getAvatarPath();
                        }

                        HBox messageBox = createMessageBox(username, message.getMessage(), avatar, isUser);
                        messageNodes.add(messageBox);
                    }
                    chatBox.getChildren().addAll(messageNodes);
                }
            }
        });

        messageField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                handleSendMessage();
                event.consume();
            }
        });
        Platform.runLater(() -> {
        Stage stage = App.primaryStage;
            stage.setOnCloseRequest(event -> {
                if (client != null) {
                    try {
                        client.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Platform.exit();
                System.exit(0);
            });
        });

        refreshContacts();
    }

    public void refreshContacts() {

        contactsList.getItems().clear();

        FriendApi friendApi = new FriendApiImpl();
        CourseApi courseApi = new CourseApiImpl();

        contactsList.getItems().addAll(
            new Friend("Bot", "Gemini", "Bot", getClass().getResource("/img/gemini.png").toString()),
            new Friend("Bot", "ChatGPT", "Bot", getClass().getResource("/img/chatgpt.png").toString()),
            new Friend("Bot", "DeepSeek", "Bot", getClass().getResource("/img/deepseek.png").toString())
        );
        List<Course> courses = courseApi.getCourseByUserId(App.user.getUserId());
        if (courses != null && !courses.isEmpty()) {
            for (Course course : courses) {
                String courseName = course.getCourseName();
                String courseId = String.valueOf(course.getId());
                String avatarUrl = getClass().getResource("/img/course.png").toString();
                contactsList.getItems().add(new Friend(courseId, courseName, "Course", avatarUrl));
            }
        }

        List<User> friendList = friendApi.getFriendList(App.user.getUserId());
        if (friendList != null && !friendList.isEmpty()) {
            for (User user : friendList) {
                contactsList.getItems().add(new Friend(user.getUserId(), user.getUsername(), "Friend", user.getAvatarPath()));
            }
        }

        if (client != null) {
            client.disconnect();
        }
        List<Long> courseIds = courseApi.getCourseIdByUserId(App.user.getUserId());
        try {
            client = new StompChatClient(App.user.getUserId(), courseIds);
            client.setOnReceivedMessage((senderId, message) -> {
                if (currentContact != null && currentContact.getId().equals(senderId)) {
                    Platform.runLater(() -> showReceivedMessage(message));
                }
            });
            client.setOnReceivedGroupMessage((senderId, courseId, message) -> {
                if (currentContact != null && currentContact.getId().equals(courseId) && !senderId.equals(App.user.getUserId())) {
                    Platform.runLater(() -> showGroupMessage(senderId, message));
                }
            });
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                if (currentContact.getStatus().equals("Friend")) {
                    client.sendPrivateMessage(currentContact.getId(), message);
                } else if (currentContact.getStatus().equals("Course")) {
                    client.sendGroupMessage(currentContact.getId(), message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addUserMessage(String message) {
        HBox messageBox = createMessageBox(App.user.getUsername(), message, App.user.getAvatarPath(), true);
        chatBox.getChildren().add(messageBox);
    }

    private void showReceivedMessage(String messageText) {
        String senderName = currentContact.getName();
        String avatarUrl = currentContact.getAvatar();
        HBox messageBox = createMessageBox(senderName, messageText, avatarUrl, false);
        chatBox.getChildren().add(messageBox);
    }

    private void showGroupMessage(String senderId, String messageText) {
        User sender = App.userApi.getUserById(senderId);
        String senderName = sender.getUsername();
        String avatarUrl = sender.getAvatarPath();
        HBox messageBox = createMessageBox(senderName, messageText, avatarUrl, false);
        chatBox.getChildren().add(messageBox);
    }

    private Label createBubble(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style + " -fx-padding: 10; -fx-background-radius: 10;");
        label.setWrapText(true);
        label.setMaxWidth(300);
        return label;
    }

    private HBox createMessageBox(String username, String message, String avatarUrl, boolean isUser) {
        double avatarSize = 30;
        ImageView avatarView = createCircularAvatar(avatarUrl, avatarSize);
        Label nameLabel = new Label(username);
        nameLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");

        Label messageLabel = createBubble(message, isUser ? "-fx-background-color: lightblue;" : "-fx-background-color: #e0e0e0;");

        VBox messageContent = new VBox(nameLabel, messageLabel);
        messageContent.setSpacing(2);

        HBox messageBox = new HBox();
        messageBox.setSpacing(10);
        messageBox.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        if (isUser) {
            messageBox.getChildren().addAll(messageContent, avatarView);
        } else {
            messageBox.getChildren().addAll(avatarView, messageContent);
        }

        return messageBox;
    }

    @FXML
    private void showNewRequestList() {
        FriendApi friendApi = new FriendApiImpl();
        List<User> friendRequestList = friendApi.getFriendRequestList(App.user.getUserId());
        AlterUtils.showNewRequestList((Stage) this.messageField.getScene().getWindow(), friendRequestList);
    }

    private ImageView createCircularAvatar(String imageUrl, double size) {
        Image image = getCachedAvatar(imageUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        Circle clip = new Circle(size / 2, size / 2, size / 2);
        imageView.setClip(clip);

        return imageView;
    }

    private Image getCachedAvatar(String avatarUrl) {
        if (avatarCache.containsKey(avatarUrl)) {
            return avatarCache.get(avatarUrl);
        } else {
            Image image = new Image(avatarUrl, true);
            avatarCache.put(avatarUrl, image);
            return image;
        }
    }

    private User getCachedUser(String userId) {
        if (userCache.containsKey(userId)) {
            return userCache.get(userId);
        } else {
            User user = App.userApi.getUserById(userId);
            if (user != null) {
                userCache.put(userId, user);
            }
            return user;
        }
    }
}
