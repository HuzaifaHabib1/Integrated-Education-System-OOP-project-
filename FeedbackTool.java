import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.Stack;

public class FeedbackTool {
    VBox layout;
    TextArea feedbackArea;
    Button submitButton;
    Label responseLabel;
    Stack<String> feedbackStack;

    public FeedbackTool(VBox layout) {
        this.layout = layout;
        initializeUI();
    }

    private void initializeUI() {
        feedbackArea = createTextArea("Enter your feedback...");
        submitButton = createButton("Submit Feedback");
        responseLabel = new Label();
        feedbackStack = new Stack<>();

        submitButton.setOnAction(e -> submitFeedback(feedbackArea.getText()));
        layout.getChildren().addAll(new Label("Feedback and Survey Tool"), feedbackArea, submitButton, responseLabel);
    }

    private TextArea createTextArea(String promptText) {
        TextArea textArea = new TextArea();
        textArea.setPromptText(promptText);
        textArea.setPrefHeight(100);
        return textArea;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        return button;
    }

    private void submitFeedback(String feedback) {
        if (!feedback.isEmpty()) {
            feedbackStack.push(feedback);
            updateFeedbackDisplay();
            feedbackArea.clear();
        } else {
            responseLabel.setText("Please enter some feedback before submitting.");
        }
    }

    private void updateFeedbackDisplay() {
        StringBuilder allFeedback = new StringBuilder();
        feedbackStack.forEach(f -> allFeedback.insert(0, f + "\n"));
        responseLabel.setText(allFeedback.toString());
    }
}