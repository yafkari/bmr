package bmr_afkari.view;

import bmr_afkari.model.ActivityLevel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author g52196
 */
public class inputSide extends GridPane {

    private DoubleTextField sizeInput;
    private DoubleTextField weightInput;
    private IntTextField ageInput;
    private ChoiceBox<ActivityLevel> lifestyleChoice;
    private ToggleGroup genderGroup;

    inputSide() {
        setVgap(10);
        setHgap(10);
        Text ResultLeftPaneTitle = new Text("Data");
        ResultLeftPaneTitle.setUnderline(true);
        ResultLeftPaneTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

        Label sizeLabel = new Label("Size (cm)");
        sizeInput = new DoubleTextField();
        Label weightLabel = new Label("Weight (kg)");
        weightInput = new DoubleTextField();
        Label ageLabel = new Label("Age (years)");
        ageInput = new IntTextField();
        Label genderLabel = new Label("Gender");

        genderGroup = new ToggleGroup();
        RadioButton womanButton = new RadioButton("Woman");
        womanButton.setToggleGroup(genderGroup);
        womanButton.setUserData("woman");
        RadioButton manButton = new RadioButton("Man");
        manButton.setToggleGroup(genderGroup);
        manButton.setUserData("man");
        Label lifestyleLabel = new Label("Life Style");
        lifestyleChoice = new ChoiceBox();
        lifestyleChoice.getItems().setAll(ActivityLevel.values());

        add(ResultLeftPaneTitle, 0, 0);
        add(sizeLabel, 0, 1);
        add(sizeInput, 1, 1);
        add(weightLabel, 0, 2);
        add(weightInput, 1, 2);
        add(ageLabel, 0, 3);
        add(ageInput, 1, 3);
        add(genderLabel, 0, 4);
        add(new HBox(20, womanButton, manButton), 1, 4);
        add(lifestyleLabel, 0, 5);
        add(lifestyleChoice, 1, 5);
    }
    
    StringProperty getSizeInputProperty() {
        return sizeInput.textProperty();
    }
    
    String getSizeInputText() {
        return sizeInput.getText();
    }
    
    StringProperty getWeightInputProperty() {
        return weightInput.textProperty();
    }
    
    String getWeightInputText() {
        return weightInput.getText();
    }
    
    StringProperty getAgeInputProperty() {
        return ageInput.textProperty();
    }
    
    String getAgeInputText() {
        return ageInput.getText();
    }
    
    ObjectProperty<ActivityLevel> getLifestyleChoiceProperty() {
        return lifestyleChoice.valueProperty();
    }
    
    double getLifestyleFactor() {
        return lifestyleChoice.getValue().getFactor();
    }
    
    ReadOnlyObjectProperty<Toggle> getGenderGroupProperty() {
        return genderGroup.selectedToggleProperty();
    }
    
    String getGenderSelected() {
        return genderGroup.getSelectedToggle().getUserData().toString();
    }
    
    void setSize(String text) {
        sizeInput.setText(text);
    }
    
    void setWeight(String text) {
        weightInput.setText(text);
    }
    
    void setAge(String text) {
        ageInput.setText(text);
    }
    
    void emptyLifestyleChoice() {
        lifestyleChoice.getSelectionModel().clearSelection();
    }
    
    void setGenderGroupSelected(boolean selected){
        genderGroup.getSelectedToggle().setSelected(selected);
    }
}
