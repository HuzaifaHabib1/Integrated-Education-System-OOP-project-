import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class ContentScheduler {
    VBox layout;
    DatePicker datePicker;
    Button scheduleButton;
    Label statusLabel;
    ContentModel contentModel;

    public ContentScheduler(VBox layout, ContentModel contentModel) {
        this.layout = layout;
        this.contentModel = contentModel;
        initializeUI();
    }

    private void initializeUI() {
        datePicker = new DatePicker();
        datePicker.setPromptText("Choose your date");

        scheduleButton = new Button("Schedule Release");
        scheduleButton.setGraphic(new ImageView(new Image("file:calendar_icon.png")));
        scheduleButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size: 14px;");
        scheduleButton.setOnAction(e -> scheduleRelease(datePicker.getValue(), contentModel.getSelectedCourse()));

        statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: darkgreen; -fx-font-size: 16px;");

        layout.getChildren().addAll(new Label("Schedule Content Release"), datePicker, scheduleButton, statusLabel);
    }

    private void scheduleRelease(LocalDate releaseDate, String course) {
        if (releaseDate != null) {
            if (releaseDate.isBefore(LocalDate.now())) {
                statusLabel.setText("Release date must be in the future.");
            } else {
                String content = contentModel.getContent(course);
                if (!content.isEmpty()) {
                    statusLabel.setText("Content for " + course + " scheduled for release on: " + releaseDate + "\nContent: " + content);
                } else {
                    statusLabel.setText("No content available for " + course);
                }
            }
        } else {
            statusLabel.setText("Please select a valid date.");
        }
    }
}