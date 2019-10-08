package bmr_afkari;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author 52196
 */
public class BMR_Afkari extends Application {

    double bmrWomanCalculation(String size, String weight, String age) {
        return 9.6 * Double.parseDouble(weight) + 1.8 * Double.parseDouble(size)
                - 4.7 * Double.parseDouble(age) + 655;
    }

    double bmrManCalculation(String size, String weight, String age) {
        return 13.7 * Double.parseDouble(weight) + 5 * Double.parseDouble(size)
                - 6.8 * Double.parseDouble(age) + 66;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Basal Metabolic Rate");

        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(25, 25, 25, 25));

        HBox rootPanel = new HBox(20);

        GridPane leftPanel = new GridPane();
        GridPane rightPanel = new GridPane();
        leftPanel.setVgap(10);
        leftPanel.setHgap(10);
        rightPanel.setVgap(10);
        rightPanel.setHgap(10);

        Button submitButton = new Button("Submit");
        submitButton.setMinWidth(450);

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

        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton womanButton = new RadioButton("Woman");
        womanButton.setToggleGroup(genderGroup);
        womanButton.setUserData("woman");
        RadioButton manButton = new RadioButton("Man");
        manButton.setToggleGroup(genderGroup);
        manButton.setUserData("man");
        Label lifestyleLabel = new Label("Life Style");
        ChoiceBox<ActivityLevel> lifestyleChoice = new ChoiceBox();
        lifestyleChoice.getItems().setAll(ActivityLevel.values());

        leftPanel.add(leftPaneTitle, 0, 0);
        leftPanel.add(sizeLabel, 0, 1);
        leftPanel.add(sizeInput, 1, 1);
        leftPanel.add(weightLabel, 0, 2);
        leftPanel.add(weightInput, 1, 2);
        leftPanel.add(ageLabel, 0, 3);
        leftPanel.add(ageInput, 1, 3);
        leftPanel.add(genderLabel, 0, 4);
        leftPanel.add(new HBox(20, womanButton, manButton), 1, 4);
        leftPanel.add(lifestyleLabel, 0, 5);
        leftPanel.add(lifestyleChoice, 1, 5);

        Text rightPaneTitle = new Text("Result");
        rightPaneTitle.setUnderline(true);
        rightPaneTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

        Label bmrLabel = new Label("BMR");
        TextField bmrField = new TextField();
        bmrField.setEditable(false);
        Label caloriesLabel = new Label("Calories");
        TextField caloriesField = new TextField();
        caloriesField.setEditable(false);

        rightPanel.add(rightPaneTitle, 0, 0);
        rightPanel.add(bmrLabel, 0, 1);
        rightPanel.add(bmrField, 1, 1);
        rightPanel.add(caloriesLabel, 0, 2);
        rightPanel.add(caloriesField, 1, 2);

        submitButton.setOnAction((ActionEvent e) -> {
            System.out.println(genderGroup.getSelectedToggle().getUserData());
            if (genderGroup.getSelectedToggle().getUserData().toString().equals("woman")) {
                bmrField.setText(String.valueOf(bmrWomanCalculation(sizeInput.getText(),
                        weightInput.getText(),
                        ageInput.getText()) * lifestyleChoice.getValue().getFactor()));
            } else if (genderGroup.getSelectedToggle().getUserData().toString().equals("man")) {
                bmrField.setText(String.valueOf(bmrManCalculation(sizeInput.getText(),
                        weightInput.getText(),
                        ageInput.getText()) * lifestyleChoice.getValue().getFactor()));
            }
        });

        rootPanel.getChildren().addAll(leftPanel, rightPanel);
        root.getChildren().addAll(rootPanel, submitButton);
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
