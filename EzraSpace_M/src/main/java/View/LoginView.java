package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * LoginView:
 *  Graphical view for the login page.
 *  Displays a background image, title, and "Sign In" / "Sign Up" buttons.
 *  "Sign In" goes to the main classroom management view (MainView).
 *  "Sign Up" shows a placeholder popup.
 */
public class LoginView {
    private final Stage stage;

    public LoginView(Stage stage) {
        this.stage = stage;

        // Background image configuration
        Image image = new Image("file:resources/images/background.jpeg");
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(bgImage);

        // Title label
        Label title = new Label("Ezra Space");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Arial", 36));

        // Sign In and Sign Up buttons
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");

        signIn.setOnAction(e -> new MainView(stage)); // Navigates to classroom management page
        signUp.setOnAction(e -> showSignUpPopup());   // Shows signup placeholder

        VBox content = new VBox(20, title, signIn, signUp);
        content.setAlignment(Pos.CENTER);

        // Layout with background
        StackPane root = new StackPane(content);
        root.setBackground(background);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Welcome - Ezra Space");
        stage.setScene(scene);
        stage.show();
    }

    // Displays a popup for signup feature (currently not implemented)
    private void showSignUpPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up");
        alert.setHeaderText(null);
        alert.setContentText("Signup feature coming soon!");
        alert.showAndWait();
    }
}
