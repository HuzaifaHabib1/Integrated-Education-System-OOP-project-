import javafx.scene.control.TextField;

import java.util.Map;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PerformanceAnalytics {
    VBox layout;
    TextField studentIdInput, scoreInput;
    Button submitButton;
    Label feedbackLabel;
    ContentModel contentModel;

    public PerformanceAnalytics(VBox layout, ContentModel contentModel) {
        this.layout = layout;
        this.contentModel = contentModel;
        initializeUI();
    }

    private void initializeUI() {
        studentIdInput = new TextField();
        studentIdInput.setPromptText("Enter Student ID...");

        scoreInput = new TextField();
        scoreInput.setPromptText("Enter Score...");

        submitButton = new Button("Submit Score");
        submitButton.setOnAction(e -> recordPerformance(contentModel.getSelectedCourse(), studentIdInput.getText(), scoreInput.getText()));

        feedbackLabel = new Label();

        layout.getChildren().addAll(new Label("Student Performance Analytics for Course: " + contentModel.getSelectedCourse()), studentIdInput, scoreInput, submitButton, feedbackLabel);
        updatePerformanceDisplay();
    }

    private void recordPerformance(String course, String studentId, String scoreText) {
        try {
            double score = Double.parseDouble(scoreText);
            contentModel.addPerformance(course, studentId, score);
            updatePerformanceDisplay();
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid score. Please enter a numeric value.");
        }
    }

    private void updatePerformanceDisplay() {
        StringBuilder allRecords = new StringBuilder();
        for (Map.Entry<String, Map<String, Double>> courseEntry : contentModel.getAllPerformance().entrySet()) {
            String course = courseEntry.getKey();
            for (Map.Entry<String, Double> entry : courseEntry.getValue().entrySet()) {
                String studentId = entry.getKey();
                double score = entry.getValue();
                allRecords.append(String.format("Course: %s, Student ID: %s, Score: %.2f, Recommendation: %s\n",
                        course, studentId, score, provideRecommendations(score)));
            }
        }
        feedbackLabel.setText(allRecords.toString());
    }

    private String provideRecommendations(double score) {
        if (score >= 90) return "Outstanding performance!";
        if (score >= 84) return "Excellent work!";
        if (score >= 74) return "Very good; can improve slightly.";
        if (score >= 68) return "Good effort; needs more work.";
        if (score >= 60) return "Satisfactory; considerable improvement needed.";
        return "Needs significant improvement.";
    }
}