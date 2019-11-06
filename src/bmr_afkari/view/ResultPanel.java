package bmr_afkari.view;

import bmr_afkari.dp.Observable;
import bmr_afkari.dp.Observer;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
        ResultLeftPane leftPanel = new ResultLeftPane();
        ResultRightPane rightPanel = new ResultRightPane(this);
        
        Button submitButton = new Button("Submit");
        submitButton.setMinWidth(450);
        submitButton.disableProperty().bind(
                Bindings.isEmpty(leftPanel.getSizeInputProperty())
                        .or(Bindings.isEmpty(leftPanel.getWeightInputProperty())
                                .or(Bindings.isEmpty(leftPanel.getAgeInputProperty())))
                        .or(Bindings.isNull(leftPanel.getLifestyleChoiceProperty()))
                        .or(Bindings.isNull(leftPanel.getGenderGroupProperty()))
        );

        submitButton.setOnAction((ActionEvent e) -> {
            try {
                size = Double.parseDouble(leftPanel.getSizeInputText());
                weight = Double.parseDouble(leftPanel.getWeightInputText());
                age = Integer.parseInt(leftPanel.getAgeInputText());
            } catch (NumberFormatException ex) {
                rightPanel.getBmrField().setText("CHECK THE VALUES !");
                rightPanel.getCaloriesField().setText("");
                return;
            }

            if (size == 0 || weight == 0 || age == 0) {
                new Alert(Alert.AlertType.ERROR,
                        "Please enter non-zero value").show();
            }

            factor = leftPanel.getLifestyleFactor();
            gender = leftPanel.getGenderSelected();
            this.notifyObservers();
        });

        Button clearButton = new Button("Clear");
        clearButton.setMinWidth(450);
        clearButton.disableProperty().bind(
                Bindings.isEmpty(leftPanel.getSizeInputProperty())
                        .and(Bindings.isEmpty(leftPanel.getWeightInputProperty())
                                .and(Bindings.isEmpty(leftPanel.getAgeInputProperty())))
                        .and(Bindings.isNull(leftPanel.getLifestyleChoiceProperty()))
                        .and(Bindings.isNull(leftPanel.getGenderGroupProperty()))
        );

        clearButton.addEventHandler(ActionEvent.ACTION,
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                leftPanel.setSize("");
                leftPanel.setWeight("");
                leftPanel.setAge("");
                leftPanel.emptyLifestyleChoice();
                leftPanel.setGenderGroupSelected(false);
                rightPanel.getBmrField().setText("");
                rightPanel.getCaloriesField().setText("");
            }
        });

        dataPanel.getChildren().addAll(leftPanel, rightPanel);
        getChildren().addAll(dataPanel, submitButton, clearButton);
    
        
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(20, 0, 0, 20));
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
