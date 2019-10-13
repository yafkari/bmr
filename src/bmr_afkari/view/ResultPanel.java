package bmr_afkari.view;

import bmr_afkari.dp.Observable;
import bmr_afkari.dp.Observer;
import bmr_afkari.model.ActivityLevel;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * @author 52196
 *
 *
 */
public class ResultPanel extends VBox implements Observable {

    private double size;
    private double weight;
    private int age;
    private double factor;
    private String gender;
    private List<Observer> observers;

    public ResultPanel(double spacing) {
        super(spacing);
        observers = new ArrayList<>();

        HBox dataPanel = new HBox(20);
        GridPane leftPanel = new GridPane();
        RightPane rightPanel = new RightPane(this);
        leftPanel.setVgap(10);
        leftPanel.setHgap(10);

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
            try {
                size = Double.parseDouble(sizeInput.getText());
                weight = Double.parseDouble(weightInput.getText());
                age = Integer.parseInt(ageInput.getText());
            } catch (NumberFormatException ex) {
                rightPanel.getBmrField().setText("CHECK THE VALUES !");
                rightPanel.getCaloriesField().setText("");
                return;
            }

            if (size == 0 || weight == 0 || age == 0) {
                new Alert(Alert.AlertType.ERROR,
                        "Please enter non-zero value").show();
            }

            factor = lifestyleChoice.getValue().getFactor();
            gender = genderGroup.getSelectedToggle().getUserData().toString();
            this.notifyObservers();
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

        clearButton.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sizeInput.setText("");
                weightInput.setText("");
                ageInput.setText("");
                lifestyleChoice.getSelectionModel().clearSelection();
                genderGroup.getSelectedToggle().setSelected(false);
                rightPanel.getBmrField().setText("");
                rightPanel.getCaloriesField().setText("");
            }
        });

        dataPanel.getChildren().addAll(leftPanel, rightPanel);
        getChildren().addAll(dataPanel, submitButton, clearButton);
    }

    public double getBmrResult() {
        if (gender.equals("woman")) {
            return 9.6 * weight + 1.8 * size - 4.7 * age + 655;
        } else {
            return 13.7 * weight + 5 * size - 6.8 * age + 66;
        }
    }

    public double getCaloriesResult() {
        return getBmrResult() * factor;
    }

    public String getGender() {
        return gender;
    }

    public double getWeight() {
        return weight;
    }

    public double getSize() {
        return size;
    }

    @Override
    public void registerObserver(Observer obs) {
        if (!observers.contains(obs)) {
            observers.add(obs);
        }
    }

    @Override
    public void removeObserver(Observer obs) {
        if (observers.contains(obs)) {
            observers.remove(obs);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update();
        }
    }
}
