package bmr_afkari.view;

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
    private dataSide observable;
    private LineChart<Number, Number> chart;
    private XYChart.Series menData;
    private XYChart.Series womenData;
    private String title;

    public Chart(Observable observable, String title) {
        super(0);
        if (observable == null) {
            throw new IllegalArgumentException("Nothing to observe");
        }
        this.observable = (dataSide) observable;
        this.title = title;

        observable.registerObserver(this);
        xAxis.setLabel(title.split(" vs ")[0]);
        yAxis.setLabel(title.split(" vs ")[1]);
        chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle(title);
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
        XYChart.Data data;
        if (title.equals("Weight (kg) vs BMR")) {
            data = new XYChart.Data(observable.getWeight(),
                    observable.getBmrResult());
        } else if (title.equals("Weight (kg) vs Calories")) {
            data = new XYChart.Data(observable.getWeight(),
                    observable.getCaloriesResult());
        } else if (title.equals("Size (cm) vs BMR")) {
            data = new XYChart.Data(observable.getSize(),
                    observable.getBmrResult());
        } else {
            throw new IllegalArgumentException("Unknown chart title: " + title);
        }

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

    public String getTitle() {
        return title;
    }
}
