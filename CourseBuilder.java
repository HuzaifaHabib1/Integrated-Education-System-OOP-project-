import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CourseBuilder {
    VBox layout;
    ComboBox<String> courseSelector;
    TextArea contentArea;
    TextField newCourseField;
    Button addCourseButton;
    Button saveButton;
    ContentModel contentModel;

    public CourseBuilder(VBox layout, ContentModel contentModel) {
        this.layout = layout;
        this.contentModel = contentModel;
        initializeUI();
    }

    private void initializeUI() {
        courseSelector = new ComboBox<>();
        courseSelector.getItems().addAll("Linear Algebra", "Probability & Statistics", "Discrete Structures", "Add New Course");
        courseSelector.setValue("Linear Algebra");
        contentModel.setSelectedCourse("Linear Algebra");

        courseSelector.setOnAction(e -> {
            if (courseSelector.getValue().equals("Add New Course")) {
                newCourseField.setVisible(true);
                addCourseButton.setVisible(true);
            } else {
                newCourseField.setVisible(false);
                addCourseButton.setVisible(false);
                contentModel.setSelectedCourse(courseSelector.getValue());
            }
        });

        newCourseField = new TextField();
        newCourseField.setPromptText("Enter new course name");
        newCourseField.setVisible(false);

        addCourseButton = new Button("Add Course");
        addCourseButton.setVisible(false);
        addCourseButton.setOnAction(e -> addNewCourse());

        contentArea = new TextArea();
        contentArea.setPromptText("Enter course content...");

        saveButton = new Button("Save Content");
        saveButton.setOnAction(e -> saveContent());

        layout.getChildren().addAll(new Label("Course Builder"), courseSelector, newCourseField, addCourseButton, contentArea, saveButton);
    }

    private void addNewCourse() {
        String newCourse = newCourseField.getText().trim();
        if (!newCourse.isEmpty() && !courseSelector.getItems().contains(newCourse)) {
            courseSelector.getItems().remove("Add New Course");
            courseSelector.getItems().add(newCourse);
            courseSelector.getItems().add("Add New Course");
            contentModel.addCourse(newCourse);
            courseSelector.setValue(newCourse);
            contentModel.setSelectedCourse(newCourse);
            newCourseField.clear();
            newCourseField.setVisible(false);
            addCourseButton.setVisible(false);
        }
    }

    private void saveContent() {
        String selectedCourse = courseSelector.getValue();
        contentModel.setContent(selectedCourse, contentArea.getText());
    }
}