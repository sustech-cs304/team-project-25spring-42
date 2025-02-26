package sustech.cs304.ai.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sustech.cs304.ai.ChatController;

public class Main extends Application {

    private TextArea chatArea;
    private TextField inputField;
    private Button sendButton;
    private ChatController chatController;

    @Override
    public void start(Stage primaryStage) {
        chatController = new ChatController();
        String model = "gpt-3.5-turbo";

        //<output area>
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        //</output area>

        //<input area>
        inputField = new TextField();
        inputField.setPromptText("输入你的消息...");
        inputField.setPrefWidth(300);

        sendButton = new Button("发送");
        sendButton.setOnAction(e -> sendMessage());

        HBox inputBox = new HBox(10, inputField, sendButton);
        inputBox.setPadding(new Insets(10));
        //</input area>

        BorderPane root = new BorderPane();

        root.setCenter(chatArea);

        root.setBottom(inputBox);



        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("AI 对话界面");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void sendMessage() {
        String userMessage = inputField.getText().trim();
        String model = "gpt-3.5-turbo";

        if (!userMessage.isEmpty()) {

            chatArea.appendText("你: " + userMessage + "\n");

            // only simulation
            String aiResponse = chatController.getResponse(model,userMessage);

            

            chatArea.appendText(aiResponse);

            inputField.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}