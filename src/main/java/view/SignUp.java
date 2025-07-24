package view;

import java.util.Map;

import com.clutchcoders.Contrller.LoginController;
import com.clutchcoders.Model.SignUpModel;
import com.clutchcoders.dao.AddSignUpData;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SignUp {

    LoginController loginController = new LoginController();
    Stage primaryStage;
    Scene signUpScene, homepagescene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setSignUpScene(Scene signUpScene) {
        this.signUpScene = signUpScene;
    }

    public Parent createSignUpPage(Runnable signUpThread) {
        Button backButton = new Button("←");
        backButton.setStyle("-fx-background-color: transparent; -fx-font-size: 20px;");
        backButton.setOnAction(e -> signUpThread.run());

        ImageView logo = new ImageView("assets/images/logo.png");
        logo.setFitWidth(250);
        logo.setPreserveRatio(true);


        Text nameLabel = new Text("Full Name");
        nameLabel.setFont(Font.font(14));

        Text emailLabel = new Text("Email");
        emailLabel.setFont(Font.font(14));

        Text passwordLabel = new Text("Password");
        passwordLabel.setFont(Font.font(14));

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your full name");
        nameField.setFocusTraversable(false);
        nameField.setPrefWidth(280);

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

        Button signUpButton = new Button("Sign Up");
        signUpButton.setPrefWidth(150);
        signUpButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
        Label status = new Label();

        signUpButton.setOnAction(e -> {
            System.out.println("Sign Up button clicked");

            String email = emailField.getText();
            String password = passField.getText();

            if (password.length() < 8) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Password");
                alert.setHeaderText("Password Too Short");
                alert.setContentText("Password must be at least 8 characters long.");
                alert.show();
                return;  // FIX: prevent continuing if password is short
            }

            boolean success = loginController.signUp(email, password);

            if (success) {
                intializeHomePage2();
                primaryStage.setScene(homepagescene);
                status.setText("Registered Successfully");
            } else {
                status.setText("There's Some Problem. Please Try Again");
            }
        });

              if((nameField.getText().isEmpty() == false) && (passField.getText().isEmpty() == false ) && (emailField.getText().isEmpty() == false)){
           
                SignUpModel signUpModel = new SignUpModel(nameField.getText().trim(), emailField.getText().trim(), passField.getText().trim());
                Map<String,String> data = signUpModel.getMap();
                AddSignUpData.putData(data,emailField.getText().trim());
                
                intializeHomePage2();
                  primaryStage.setScene(homepagescene);
            
        }
          


        HBox loginBox = new HBox();
        Text haveAccount = new Text("Already have an account?");
        Hyperlink loginLink = new Hyperlink("Sign In");
        loginLink.setStyle("-fx-text-fill: #0000EE; -fx-font-weight: bold; -fx-underline: true;");
        loginBox.getChildren().addAll(haveAccount, loginLink);
        loginBox.setAlignment(Pos.CENTER);
        loginLink.setOnAction(e -> signUpThread.run());

        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(320);
        card.setMaxWidth(320);
        card.setMaxHeight(500);
        card.setStyle(
                 "-fx-background-color: rgba(139, 138, 138, 0.63);" +
                        "-fx-background-radius: 20;" 
                        // "-fx-effect: dropshadow(gaussian, rgba(127, 113, 113, 0.54), 10, 0, 0, 6");
        );

        card.getChildren().addAll(
                logo,
                nameLabel, nameField,
                emailLabel, emailField,
                passwordLabel, passField,
                signUpButton,
                invalid,
                loginBox,
                status
        );

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));
        layout.setTop(backButton);
        BorderPane.setAlignment(backButton, Pos.TOP_LEFT);

        StackPane background = new StackPane(layout);
        layout.setCenter(card);
        background.setStyle(
                "-fx-background-image: url('assets/images/room3.jpg');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center center;" +
                        "-fx-background-repeat: no-repeat;"
        );

        
        return background;
    }

  public void intializeHomePage2() {
    HomePage homePage = new HomePage();
    homePage.setPrimaryStage(primaryStage);
    homePage.setExploreUser(false);  // SignUp = Full access
    Rectangle2D screen = Screen.getPrimary().getVisualBounds();
    homepagescene = new Scene(homePage.createHomePage(() -> handleBack()),screen.getWidth(),screen.getHeight());
    homePage.setHomePagScene(homepagescene);
}


    public Object handleBack() {
        primaryStage.setScene(signUpScene);
        return homepagescene;
    }
}
