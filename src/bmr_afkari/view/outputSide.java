package bmr_afkari.view;

import bmr_afkari.dp.Observable;
import bmr_afkari.dp.Observer;
import java.text.DecimalFormat;
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
public class outputSide extends GridPane implements Observer {

    private TextField bmrField;
    private TextField caloriesField;
    private dataSide observable;
    private DecimalFormat df = new DecimalFormat("0.00");

    public outputSide(Observable observable) {
        if (observable == null) {
            throw new IllegalArgumentException("Nothing to observe");
        }
        this.observable = (dataSide) observable;

        observable.registerObserver(this);
        setVgap(10);
        setHgap(10);
        Text ResultRightPaneTitle = new Text("Result");
        ResultRightPaneTitle.setUnderline(true);
        ResultRightPaneTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 16));

        Label bmrLabel = new Label("BMR");
        Label caloriesLabel = new Label("Calories");
        bmrField = new TextField();
        bmrField.setEditable(false);
        caloriesField = new TextField();
        caloriesField.setEditable(false);

        add(ResultRightPaneTitle, 0, 0);
        add(bmrLabel, 0, 1);
        add(bmrField, 1, 1);
        add(caloriesLabel, 0, 2);
        add(caloriesField, 1, 2);
    }

    void setBmrField(String text) {
        bmrField.setText(text);
    }

    void setCaloriesField(String text) {
        caloriesField.setText(text);
    }

    @Override
    public void update() {
        bmrField.setText(
                String.valueOf(df.format(observable.getBmrResult())));
        caloriesField.setText(
                String.valueOf(df.format(observable.getCaloriesResult())));
    }
}
