import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends Application {

    private Map<String, String> userAccounts = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize user accounts
        userAccounts.put("admin@example.com", "admin");

        // Set up the user interface
        GridPane pane = setupUserInterface();

        // Action handlers for the buttons
        TextField txtEmail = (TextField) pane.lookup("#txtEmail");
        PasswordField txtPassword = (PasswordField) pane.lookup("#txtPassword");
        CheckBox chkRememberMe = (CheckBox) pane.lookup("#chkRememberMe");

        Button btnLogin = (Button) pane.lookup("#btnLogin");
        btnLogin.setOnAction(e -> attemptLogin(primaryStage, txtEmail.getText(), txtPassword.getText(), chkRememberMe.isSelected()));

        Hyperlink linkForgotPassword = (Hyperlink) pane.lookup("#linkForgotPassword");
        linkForgotPassword.setOnAction(e -> showForgotPasswordDialog());

        Button btnSignUp = (Button) pane.lookup("#btnSignUp");
        btnSignUp.setOnAction(e -> openSignUpWindow());

        // Display the primary stage
        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane setupUserInterface() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(15));
        pane.setHgap(10);
        pane.setVgap(10);

        Label welcomeLabel = new Label("Welcome to IES");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        pane.add(welcomeLabel, 0, 0, 2, 1);

        Label taglineLabel = new Label("Your Smart Integrated Education System");
        taglineLabel.setStyle("-fx-font-size: 12px; -fx-font-style: italic;");
        pane.add(taglineLabel, 0, 1, 2, 1);

        pane.add(new Label("Email:"), 0, 2);
        TextField txtEmail = new TextField();
        txtEmail.setId("txtEmail");
        pane.add(txtEmail, 1, 2);

        pane.add(new Label("Password:"), 0, 3);
        PasswordField txtPassword = new PasswordField();
        txtPassword.setId("txtPassword");
        pane.add(txtPassword, 1, 3);

        CheckBox chkRememberMe = new CheckBox("Remember me");
        chkRememberMe.setId("chkRememberMe");
        pane.add(chkRememberMe, 1, 4);

        Button btnLogin = new Button("Login");
        btnLogin.setId("btnLogin");
        pane.add(btnLogin, 1, 5);

        Hyperlink linkForgotPassword = new Hyperlink("Forgot Password?");
        linkForgotPassword.setId("linkForgotPassword");
        pane.add(linkForgotPassword, 1, 6);

        Button btnSignUp = new Button("Sign Up");
        btnSignUp.setId("btnSignUp");
        pane.add(btnSignUp, 1, 7);

        return pane;
    }

    private void attemptLogin(Stage primaryStage, String email, String password, boolean rememberMe) {
        if (userAccounts.getOrDefault(email, "").equals(password)) {
            showMainApplication(primaryStage, rememberMe, email);
        } else {
            showLoginFailedAlert();
        }
    }

    private void showMainApplication(Stage primaryStage, boolean rememberMe, String email) {
        if (rememberMe) {
            System.out.println("Remembering user: " + email);
        }
        new EducationPlatformApp().start(new Stage()); // Start main application
        primaryStage.hide();
    }

    private void showLoginFailedAlert() {
        showAlert("Login Failed", "Invalid email or password.", Alert.AlertType.ERROR);
    }

    private void showForgotPasswordDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Password Reset");
        dialog.setHeaderText("Enter your email to reset your password:");
        dialog.showAndWait().ifPresent(email ->
            showAlert("Password Reset", "A password reset link has been sent to: " + email, Alert.AlertType.INFORMATION));
    }

    private void openSignUpWindow() {
        Stage signUpStage = new Stage();
        signUpStage.setTitle("Sign Up");

        GridPane signUpPane = new GridPane();
        signUpPane.setAlignment(Pos.CENTER);
        signUpPane.setPadding(new Insets(15));
        signUpPane.setHgap(10);
        signUpPane.setVgap(10);

        signUpPane.add(new Label("First Name:"), 0, 0);
        TextField txtFirstName = new TextField();
        signUpPane.add(txtFirstName, 1, 0);

        signUpPane.add(new Label("Last Name:"), 0, 1);
        TextField txtLastName = new TextField();
        signUpPane.add(txtLastName, 1, 1);

        signUpPane.add(new Label("Email:"), 0, 2);
        TextField txtEmail = new TextField();
        signUpPane.add(txtEmail, 1, 2);

        signUpPane.add(new Label("Create Password:"), 0, 3);
        PasswordField txtCreatePassword = new PasswordField();
        signUpPane.add(txtCreatePassword, 1, 3);

        signUpPane.add(new Label("Re-enter Password:"), 0, 4);
        PasswordField txtReenterPassword = new PasswordField();
        signUpPane.add(txtReenterPassword, 1, 4);

        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnAction(e -> {
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String email = txtEmail.getText();
            String createPassword = txtCreatePassword.getText();
            String reenterPassword = txtReenterPassword.getText();

            if (createPassword.equals(reenterPassword)) {
                userAccounts.put(email, createPassword);
                showAlert("Account Created", "Account created successfully!", Alert.AlertType.INFORMATION);
                signUpStage.close();
            } else {
                showAlert("Password Mismatch", "Passwords do not match. Please try again.", Alert.AlertType.ERROR);
            }
        });

        signUpPane.add(btnSubmit, 1, 5);

        Scene signUpScene = new Scene(signUpPane, 400, 300);
        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
