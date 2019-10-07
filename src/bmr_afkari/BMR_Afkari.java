package bmr_afkari;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(25, 25, 25, 25));

        HBox rootPanel = new HBox();

        GridPane leftPanel = new GridPane();
        GridPane rightPanel = new GridPane();
        leftPanel.setVgap(20);
        rightPanel.setVgap(20);

        Button submitButton = new Button("Submit");
        Text leftPaneTitle = new Text("Data");
        leftPaneTitle.setUnderline(true);
        leftPaneTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

        Label sizeLabel = new Label("Size (cm)");
        TextField sizeInput = new TextField();
        Label weightLabel = new Label("Weight (kg)");
        TextField weightInput = new TextField();
        Label ageLabel = new Label("Age (years)");
        TextField ageInput = new TextField();
        Label genderLabel = new Label("Gender");
        Label lifestyleLabel = new Label("Life Style");
        

        leftPanel.add(leftPaneTitle, 0, 0);
        leftPanel.add(sizeLabel, 0, 1);
        leftPanel.add(sizeInput, 1, 1);
        leftPanel.add(weightLabel, 0, 2);
        leftPanel.add(ageLabel, 0, 3);
        leftPanel.add(genderLabel, 0, 4);
        leftPanel.add(lifestyleLabel, 0, 5);

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
