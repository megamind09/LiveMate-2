package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AdminDashBoard  {

    Stage primaryStage;
    Scene adminScene,adminOwnerScene,adminBatchScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setAdmiScene(Scene admiScene) {
        this.adminScene = admiScene;
    }

    public Parent createAdminPage(Runnable adminThread) {


         // Back Button with Icon
        ImageView backIcon = new ImageView(new Image("assets/images/back.png")); // Replace with actual path
        backIcon.setFitWidth(24);
        backIcon.setFitHeight(24);
        Button backBtn = new Button("", backIcon);
        backBtn.setStyle("-fx-background-color: transparent;");
        backBtn.setOnAction(e -> {
            System.out.println("Back button pressed!");
            adminThread.run();
        });

        // ADMIN Header Label
        Label adminLabel = new Label("ADMIN");
        adminLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        adminLabel.setStyle("-fx-text-fill: #333;");

        // Header HBox
        HBox headBox = new HBox(20, backBtn, adminLabel);
        headBox.setAlignment(Pos.CENTER_LEFT);
        headBox.setPadding(new Insets(20));
        headBox.setStyle("-fx-background-color: linear-gradient(to right, #494CA2, #494CA2);");

        // Owner's Data Card
        VBox ownerCard = new VBox(10);
        ownerCard.setPadding(new Insets(20));
        ownerCard.setAlignment(Pos.CENTER);
        ownerCard.setStyle("""
            -fx-background-color: #ffffff;
            -fx-border-radius: 12;
            -fx-background-radius: 12;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);
            -fx-border-color: #e0e0e0;
        """);

        Text ownerHeader = new Text("Owner’s Data");
        ownerHeader.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        ownerCard.getChildren().add(ownerHeader);
        ownerCard.setOnMouseClicked(e ->{
            intializeOwnerData();
            primaryStage.setScene(adminOwnerScene);

        });

        // Bachelor's Data Card
        VBox bachelorCard = new VBox(10);
        bachelorCard.setPadding(new Insets(20));
        bachelorCard.setAlignment(Pos.CENTER);
        bachelorCard.setStyle("""
            -fx-background-color: #ffffff;
            -fx-border-radius: 12;
            -fx-background-radius: 12;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);
            -fx-border-color: #e0e0e0;
        """);

        Text bachelorHeader = new Text("Bachelor’s Data");
        bachelorHeader.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        bachelorCard.getChildren().add(bachelorHeader);
        bachelorCard.setOnMouseClicked(e ->{
            intializeBatchData();
            primaryStage.setScene(adminBatchScene);
        });

        // Center HBox containing cards
        HBox centerHbox = new HBox(40, ownerCard, bachelorCard);
        centerHbox.setAlignment(Pos.CENTER);
        centerHbox.setPadding(new Insets(40));
        centerHbox.setStyle("-fx-background-color: #f8f9fa;");

        // Root VBox
        VBox adminvb = new VBox(30, headBox, centerHbox);
        adminvb.setAlignment(Pos.TOP_CENTER);
        adminvb.setPadding(new Insets(20));
        adminvb.setStyle("-fx-background-color: linear-gradient(to bottom, #a8edea, #fed6e3);");
        return adminvb;
      
    }

    private void intializeBatchData() {
       AdminBachelorData adminBachelorData = new AdminBachelorData();
       adminBachelorData.setPrimaryStage(primaryStage);
       Rectangle2D  screen= Screen.getPrimary().getVisualBounds();
       adminBatchScene = new Scene(adminBachelorData.createAdminBatchData(()->handleBack()), screen.getWidth(),screen.getHeight());
       adminBachelorData.setAdminBatchScene(adminBatchScene);

    }

    private void intializeOwnerData() {
      AdminOwnerData adminOwnerData = new AdminOwnerData();
      adminOwnerData.setPrimaryStage(primaryStage);
      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
      adminOwnerScene = new Scene(adminOwnerData.createAdminOwnerDataPage(()->handleBack()),screenBounds.getWidth(),screenBounds.getHeight());
    }

    public void handleBack() {
        primaryStage.setScene(adminScene);
        return ;
    }

    
}
