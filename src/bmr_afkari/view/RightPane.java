package bmr_afkari.view;

import bmr_afkari.BMR_Afkari;
import bmr_afkari.model.Observable;
import bmr_afkari.model.Observer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Represents the right pane where the results are.
 *
 * @author g52196
 */
public class RightPane extends GridPane implements Observer {

    private TextField bmrField;
    private TextField caloriesField;
    private BMR_Afkari observable;

    public RightPane() {
        observable.registerObserver(this);
        setVgap(10);
        setHgap(10);
        Text rightPaneTitle = new Text("Result");
        rightPaneTitle.setUnderline(true);
        rightPaneTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

        Label bmrLabel = new Label("BMR");
        Label caloriesLabel = new Label("Calories");
        bmrField = new TextField();
        bmrField.setEditable(false);
        caloriesField = new TextField();
        caloriesField.setEditable(false);

        add(rightPaneTitle, 0, 0);
        add(bmrLabel, 0, 1);
        add(bmrField, 1, 1);
        add(caloriesLabel, 0, 2);
        add(caloriesField, 1, 2);
    }

    public TextField getBmrField() {
        return bmrField;
    }
    
    public TextField getCaloriesField() {
        return bmrField;
    }
    
    @Override
    public void update() {
        bmrField.setText(String.valueOf(observable.getBmrResult()));
        caloriesField.setText(String.valueOf(observable.getBmrResult()));
    }
}
