package bmr_afkari;

import bmr_afkari.model.Observer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

/**
 *
 * @author g52196
 */
public class Chart extends VBox implements Observer {

    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart<Number, Number> chart;
    private XYChart.Series menData;
    private XYChart.Series womenData;

    public Chart() {
        xAxis.setLabel("Weight (kg)");
        yAxis.setLabel("BMR");
        chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("BMR Index vs Weight");

        menData = new XYChart.Series();
        menData.setName("MenData");

        womenData = new XYChart.Series();
        womenData.setName("WomenData");

        getChildren().add(chart);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
