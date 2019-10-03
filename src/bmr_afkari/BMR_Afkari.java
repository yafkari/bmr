package bmr_afkari;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author 52196
 */
public class BMR_Afkari extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Basal Metabolic Rate");
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Button submitButton = new Button("Submit");

        HBox rootPanel = new HBox();
        GridPane leftPanel = new GridPane();
        GridPane rightPanel = new GridPane();

        // TODO
        
        rootPanel.getChildren().addAll(leftPanel, rightPanel);
        root.getChildren().addAll(rootPanel, submitButton);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
