package view;

import com.clutchcoders.Contrller.LoginController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginPage extends Application {

    LoginController loginController = new LoginController();

    Stage loginStage;
    Scene loginScene, homePagScene, signupScene, adminScene;

    @Override
    public void start(Stage stage) {
        this.loginStage = stage;

        // --- Heading and Labels ---
       ImageView logo = new ImageView("assets/images/logo.png");
        logo.setFitWidth(250);
        logo.setPreserveRatio(true);




        Text emailLabel = new Text("Email");
        emailLabel.setFont(Font.font(14));

        Text passwordLabel = new Text("Password");
        passwordLabel.setFont(Font.font(14));

        // --- TextFields ---
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setFocusTraversable(false);
        emailField.setPrefWidth(280);

        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter your password");
        passField.setFocusTraversable(false);
        passField.setPrefWidth(280);

        Text invalid = new Text();
        invalid.setFill(Color.RED);

        // --- Login Button ---
        Button loginButton = new Button("Sign In");
        loginButton.setPrefWidth(150);
        loginButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
        Label resultLabel = new Label();

        loginButton.setOnAction(e -> {
            System.out.println("Login btn clicked");

            String email = emailField.getText();
            String password = passField.getText();
            boolean success = loginController.signInWithEmailAndPassword(email, password);

            if (success) {
                initializeHomepage(false);
                loginStage.setScene(homePagScene);
                resultLabel.setText("Login Successful");
            } else {
                resultLabel.setText("Login Failed. Please Check Your Email and Password.");
            }
        });

        // --- SignUp Prompt ---
        HBox signUpBox = new HBox(5);
        Text noAccount = new Text("Don't have an account?");
        Hyperlink signUpLink = new Hyperlink("Sign Up");
        signUpLink.setStyle("-fx-text-fill: #0000EE; -fx-font-weight: bold; -fx-underline: true;");
        signUpBox.getChildren().addAll(noAccount, signUpLink);
        signUpBox.setAlignment(Pos.CENTER);
        signUpLink.setOnAction(e -> {
            initializeSignUpPage();
            loginStage.setScene(signupScene);
        });

        // --- Guest Explore Prompt ---
        HBox exploreBox = new HBox(5);
        Text guestUser = new Text("Guest User?");
        Hyperlink exploreLink = new Hyperlink("Explore!");
        exploreLink.setStyle("-fx-text-fill: #0000EE; -fx-font-weight: bold; -fx-underline: true;");
        exploreBox.getChildren().addAll(guestUser, exploreLink);
        exploreBox.setAlignment(Pos.CENTER);
        exploreLink.setOnAction(e -> {
            initializeHomepage(true);
            loginStage.setScene(homePagScene);
        });

        // --- Admin Login Prompt ---
        HBox adminLoginBox = new HBox(5);
        Text logAdmin = new Text("Log In as an Admin");
        Hyperlink logAdminLink = new Hyperlink("Log In");
        logAdminLink.setStyle("-fx-text-fill: #0000EE; -fx-font-weight: bold; -fx-underline: true;");
        adminLoginBox.getChildren().addAll(logAdmin, logAdminLink);
        adminLoginBox.setAlignment(Pos.CENTER);
        logAdminLink.setOnAction(e -> {
            if (emailField.getText().equals("xyz@gmail.com") && passField.getText().equals("xyz@123")) {
                intializeAdminPage();
                loginStage.setScene(adminScene);
            } else {
                invalid.setText("Invalid Credentials. Try again.");
            }
        });

        // --- Login Card ---
        VBox card = new VBox(20);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(320);
        card.setMaxWidth(320);
        card.setMaxHeight(450);
        card.setStyle(
                "-fx-background-color: rgba(152, 149, 149, 0.4);" +
                        "-fx-background-radius: 20;"
        );

        card.getChildren().addAll(
                logo,
                emailLabel, emailField,
                passwordLabel, passField,
                loginButton,
                invalid,
                signUpBox,
                exploreBox,
                adminLoginBox,
                resultLabel
        );

        // --- Background ---
        StackPane root = new StackPane(card);
        root.setAlignment(Pos.CENTER);
        root.setStyle(
                "-fx-background-image: url('assets/images/room3.jpg');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center center;" +
                        "-fx-background-repeat: no-repeat;"
        );

      

        // --- Full screen setup ---
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        loginScene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

        stage.setTitle("Login Page");
        stage.getIcons().add(new javafx.scene.image.Image("assets/images/logo.png"));
        stage.setScene(loginScene);
        stage.setMaximized(true);
        stage.show();
    }

    private void intializeAdminPage() {
        AdminDashBoard adminBoard = new AdminDashBoard();
        adminBoard.setPrimaryStage(loginStage);
       Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        adminScene = new Scene(adminBoard.createAdminPage(() -> handleBack()),screen.getWidth(),screen.getHeight());
        adminBoard.setAdmiScene(adminScene);
    }

   private void initializeHomepage(boolean isExplore) {
    HomePage homePage = new HomePage();
    homePage.setPrimaryStage(loginStage);
    homePage.setExploreUser(isExplore);  // Pass explore mode
    Rectangle2D screen = Screen.getPrimary().getVisualBounds();
    homePagScene = new Scene(homePage.createHomePage(() -> handleBack()),screen.getWidth(),screen.getHeight());
    homePage.setHomePagScene(homePagScene);
}


    private void handleBack() {
        System.out.println("Back pressed — returning to login");
        loginStage.setScene(loginScene);
    }

    public void initializeSignUpPage() {
        SignUp signUp = new SignUp();
        signUp.setPrimaryStage(loginStage);
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        signupScene = new Scene(signUp.createSignUpPage(() -> handleBack()),screen.getWidth(),screen.getHeight());
        signUp.setSignUpScene(signupScene);
    }
}
