import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EducationPlatformApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

        ContentModel contentModel = new ContentModel();

        Tab courseBuilderTab = createTab("Course Builder", "file:E:course_builder_icon.png", new VBox(10));
        new CourseBuilder((VBox) courseBuilderTab.getContent(), contentModel);

        Tab performanceAnalyticsTab = createTab("Performance Analytics", "file:E:performance_analytics_icon.png", new VBox(10));
        new PerformanceAnalytics((VBox) performanceAnalyticsTab.getContent(), contentModel);

        Tab contentSchedulerTab = createTab("Content Scheduler", "file:E:content_scheduler_icon.png", new VBox(10));
        new ContentScheduler((VBox) contentSchedulerTab.getContent(), contentModel);

        Tab feedbackToolTab = createTab("Feedback Tool", "file:E:feedback_icon.png", new VBox(10));
        new FeedbackTool((VBox) feedbackToolTab.getContent());

        tabPane.getTabs().addAll(courseBuilderTab, performanceAnalyticsTab, contentSchedulerTab, feedbackToolTab);

        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setTitle("Integrated Smart Education Platform");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Tab createTab(String title, String iconPath, VBox layout) {
        Tab tab = new Tab();
        tab.setText(title);
        tab.setGraphic(new ImageView(new Image(iconPath)));
        tab.setContent(layout);
        return tab;
    }

    public static void main(String[] args) {
        launch(args);
    }
}