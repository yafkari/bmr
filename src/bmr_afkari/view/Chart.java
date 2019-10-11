package bmr_afkari.view;

import bmr_afkari.BMR_Afkari;
import bmr_afkari.dp.Observable;
import bmr_afkari.dp.Observer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

/**
 * Represents a chart displaying BMR Index vs Weight of given data.
 *
 * @author g52196
 */
public class Chart extends VBox implements Observer {

    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private BMR_Afkari observable;
    private LineChart<Number, Number> chart;
    private XYChart.Series menData;
    private XYChart.Series womenData;

    public Chart(Observable observable) {
        if (observable == null) {
            throw new IllegalArgumentException("Nothing to observe");
        }
        this.observable = (BMR_Afkari) observable;

        observable.registerObserver(this);
        xAxis.setLabel("Weight (kg)");
        yAxis.setLabel("BMR");
        chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("BMR Index vs Weight");
        chart.setAnimated(false);

        menData = new XYChart.Series();
        menData.setName("Men Data");

        womenData = new XYChart.Series();
        womenData.setName("Women Data");

        chart.getData().add(womenData);
        chart.getData().add(menData);

        getChildren().add(chart);
    }

    @Override
    public void update() {
        XYChart.Data data = new XYChart.Data(observable.getWeight(),
                observable.getBmrResult());

        if (observable.getGender().equalsIgnoreCase("woman")) {
            if (!womenData.getData().contains(data)) {
                womenData.getData().add(data);
            }
        } else {
            if (!menData.getData().contains(data)) {
                menData.getData().add(data);
            }
        }
    }
}
