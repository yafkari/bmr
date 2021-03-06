package bmr_afkari;

import bmr_afkari.model.ActivityLevel;
import bmr_afkari.view.Chart;
import bmr_afkari.view.dataSide;
import java.util.Arrays;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author 52196
 */
public class BMR_Afkari extends Application {

    @Override
    public void start(Stage primaryStage) {
        // for td 6 about lambdas
        Arrays.asList(ActivityLevel.values())
                .stream()
                .forEach(activity -> System.out.println(activity));
        primaryStage.setTitle("Basal Metabolic Rate");

        VBox rootStage = new VBox();
        HBox root = new HBox();

        dataSide data = new dataSide(25);

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItemExit = new MenuItem("Exit");
        menuItemExit.setOnAction((ActionEvent e) -> {
            System.exit(0);
        });
        menuFile.getItems().add(menuItemExit);
        menuBar.getMenus().add(menuFile);

        TabPane chartPanes = new TabPane();

        Chart weightVsBmrChart = new Chart(data, "Weight (kg) vs BMR");
        Chart weightVsCaloriesChart = new Chart(data, "Weight (kg) vs Calories");
        Chart sizeVsBmrChart = new Chart(data, "Size (cm) vs BMR");

        Tab weightVsBmrTab = new Tab(weightVsBmrChart.getTitle());
        Tab weightVsCaloriesTab = new Tab(weightVsCaloriesChart.getTitle());
        Tab sizeVsBmrTab = new Tab(sizeVsBmrChart.getTitle());

        chartPanes.getTabs().add(weightVsBmrTab);
        chartPanes.getTabs().add(weightVsCaloriesTab);
        chartPanes.getTabs().add(sizeVsBmrTab);

        weightVsBmrTab.setContent(weightVsBmrChart);
        weightVsCaloriesTab.setContent(weightVsCaloriesChart);
        sizeVsBmrTab.setContent(sizeVsBmrChart);

        HBox.setHgrow(data, Priority.ALWAYS);
        HBox.setHgrow(chartPanes, Priority.ALWAYS);

        root.getChildren().addAll(data, chartPanes);
        rootStage.getChildren().addAll(menuBar, root);
        Scene scene = new Scene(rootStage, 1000, 350);
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
