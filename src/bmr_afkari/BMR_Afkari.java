package bmr_afkari;

import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
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

    double bmrWomanCalculation(double size, double weight, int age) {
        return 9.6 * weight + 1.8 * size - 4.7 * age + 655;
    }

    double bmrManCalculation(double size, double weight, int age) {
        return 13.7 * weight + 5 * size - 6.8 * age + 66;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Basal Metabolic Rate");

        VBox root = new VBox(25);
        root.setAlignment(Pos.TOP_CENTER);

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItemExit = new MenuItem("Exit");
        menuItemExit.setOnAction((ActionEvent e) -> {
            System.exit(0);
        });
        menuFile.getItems().add(menuItemExit);
        menuBar.getMenus().add(menuFile);

        HBox rootPanel = new HBox(20);
        rootPanel.setPadding(new Insets(0, 20, 0, 20));
        GridPane leftPanel = new GridPane();
        GridPane rightPanel = new GridPane();
        leftPanel.setVgap(10);
        leftPanel.setHgap(10);
        rightPanel.setVgap(10);
        rightPanel.setHgap(10);

        Text leftPaneTitle = new Text("Data");
        leftPaneTitle.setUnderline(true);
        leftPaneTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

        Label sizeLabel = new Label("Size (cm)");
        DoubleTextField sizeInput = new DoubleTextField();
        Label weightLabel = new Label("Weight (kg)");
        DoubleTextField weightInput = new DoubleTextField();
        Label ageLabel = new Label("Age (years)");
        IntTextField ageInput = new IntTextField();
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

        Button submitButton = new Button("Submit");
        submitButton.setMinWidth(450);
        submitButton.disableProperty().bind(
                Bindings.isEmpty(sizeInput.textProperty())
                        .or(Bindings.isEmpty(weightInput.textProperty())
                                .or(Bindings.isEmpty(ageInput.textProperty())))
                        .or(Bindings.isNull(lifestyleChoice.valueProperty()))
                        .or(Bindings.isNull(genderGroup.selectedToggleProperty()))
        );

        submitButton.setOnAction((ActionEvent e) -> {
            DecimalFormat df = new DecimalFormat("0.00");
            double size;
            double weight;
            int age;
            try {
                size = Double.parseDouble(sizeInput.getText());
                weight = Double.parseDouble(weightInput.getText());
                age = Integer.parseInt(ageInput.getText());
            } catch (NumberFormatException ex) {
                bmrField.setText("CHECK THE VALUES !");
                caloriesField.setText("");
                return;
            }
            
            if (size == 0 || weight == 0 || age == 0) {
                new Alert(AlertType.ERROR, "Please enter non-zero value").show();
            }

            double factor = lifestyleChoice.getValue().getFactor();

            if (genderGroup.getSelectedToggle().getUserData().equals("woman")) {
                Double result = bmrWomanCalculation(size, weight, age);
                bmrField.setText(String.valueOf(df.format(result)));
                caloriesField.setText(String.valueOf(df.format(result * factor)));
            } else {
                Double result = bmrManCalculation(size, weight, age);
                bmrField.setText(String.valueOf(df.format(result)));
                caloriesField.setText(String.valueOf(df.format(result * factor)));
            }
        });

        Button clearButton = new Button("Clear");
        clearButton.setMinWidth(450);
        clearButton.disableProperty().bind(
                Bindings.isEmpty(sizeInput.textProperty())
                        .and(Bindings.isEmpty(weightInput.textProperty())
                                .and(Bindings.isEmpty(ageInput.textProperty())))
                        .and(Bindings.isNull(lifestyleChoice.valueProperty()))
                        .and(Bindings.isNull(genderGroup.selectedToggleProperty()))
        );

        clearButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sizeInput.setText("");
                weightInput.setText("");
                ageInput.setText("");
                lifestyleChoice.getSelectionModel().clearSelection();
                genderGroup.getSelectedToggle().setSelected(false);
                bmrField.setText("");
                caloriesField.setText("");
            }

        });

        rootPanel.getChildren().addAll(leftPanel, rightPanel);
        root.getChildren().addAll(menuBar, rootPanel, submitButton, clearButton);
        Scene scene = new Scene(root, 500, 350);
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
