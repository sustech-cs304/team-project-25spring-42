package sustech.cs304.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatController {

    @FXML
    private VBox chatBox;

    @FXML
    private TextArea messageField;

    @FXML
    private ListView<String> contactsList;

    @FXML
    private Label chatPartnerLabel;

    private String currentContact = null;

    @FXML
    private void initialize() {
        contactsList.getItems().addAll("Gemini", "Deepseek", "ChatGPT");
        // TODO: Get the friends list from the server

        contactsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals(currentContact)) {
                currentContact = newVal;
                chatBox.getChildren().clear();
                chatPartnerLabel.setText(newVal);
            }
        });
        //enter to send message
        messageField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                handleSendMessage();
            }
        });
    }

    @FXML
    private void handleSendMessage() {
        String message = messageField.getText();
        if (message != null && !message.isEmpty() && currentContact != null) {
            addUserMessage(message);
            // TODO: Send the message to the server
            messageField.clear();
            if (currentContact == "Gemini" || currentContact == "Deepseek" || currentContact == "ChatGPT") {
                // Simulate a response from the AI
                String response = getAIResponse(message, currentContact);
                showReceivedMessage(response);
            }
        }
    }

    private void addUserMessage(String message) {
        Label label = createBubble(message, "-fx-background-color: lightblue; -fx-alignment: center-right;");
        label.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 10;");
        HBox container = new HBox(label);
        container.setAlignment(Pos.CENTER_RIGHT);
        chatBox.getChildren().add(container);
    }

    private void showReceivedMessage(String messageText) {
        Label messageLabel = new Label(messageText);
        messageLabel.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 10; -fx-background-radius: 10;");
        messageLabel.setWrapText(true);

        HBox messageContainer = new HBox(messageLabel);
        messageContainer.setAlignment(Pos.CENTER_LEFT);
        messageContainer.setPadding(new Insets(5, 10, 5, 10));

        chatBox.getChildren().add(messageContainer);
    }

    private void addSystemMessage(String message) {
        Label label = new Label(message);
        label.setStyle("-fx-font-style: italic; -fx-text-fill: gray;");
        chatBox.getChildren().add(label);
    }

    private Label createBubble(String text, String style) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setMaxWidth(300);
        label.setStyle(style);
        return label;
    }

    public String getAIResponse(String message, String model) {
        // Simulate AI response
        AIChatController aiChatController = new AIChatController();
        if (model.equals("Gemini")) {
            model = "gemini-2.0-flash";
        } else if (model.equals("Deepseek")) {
            model = "deepseek-chat";
        } else if (model.equals("ChatGPT")) {
            model = "gpt-3.5-turbo";
        }
        String response = aiChatController.getResponse(model, message);
        return response;
    }

}
