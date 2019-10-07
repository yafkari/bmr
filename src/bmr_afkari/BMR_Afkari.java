package bmr_afkari;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        
        HBox rootPanel = new HBox();
        GridPane leftPanel = new GridPane();
        GridPane rightPanel = new GridPane();
        
        Button submitButton = new Button("Submit");
        Label sizeLabel = new Label("Size (cm)");
        Label weightLabel = new Label("Weight (kg)");
        Label ageLabel = new Label("Age (years)");
        Label genderLabel = new Label("Life Style");
        Label lifestyleLabel = new Label("Life Style");

        leftPanel.add(sizeLabel, 0, 0);
        leftPanel.add(weightLabel, 0, 1);
        leftPanel.add(ageLabel, 0, 2);
        leftPanel.add(genderLabel, 0, 3);
        leftPanel.add(lifestyleLabel, 0, 4);
        
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
