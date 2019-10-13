package bmr_afkari;

import bmr_afkari.view.Chart;
import bmr_afkari.dp.Observable;
import bmr_afkari.dp.Observer;
import bmr_afkari.model.ActivityLevel;
import bmr_afkari.view.DoubleTextField;
import bmr_afkari.view.IntTextField;
import bmr_afkari.view.RightPane;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author 52196
 */
public class BMR_Afkari extends Application implements Observable {

    private double size;
    private double weight;
    private int age;
    private double factor;
    private String gender;
    private List<Observer> observers;

    public BMR_Afkari() {
        observers = new ArrayList<>();
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
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Basal Metabolic Rate");

        VBox rootStage = new VBox();
        HBox root = new HBox();

        VBox data = new VBox(25);
        data.setAlignment(Pos.TOP_CENTER);
        data.setPadding(new Insets(20, 0, 0, 20));

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItemExit = new MenuItem("Exit");
        menuItemExit.setOnAction((ActionEvent e) -> {
            System.exit(0);
        });
        menuFile.getItems().add(menuItemExit);
        menuBar.getMenus().add(menuFile);

        TabPane chartPanes = new TabPane();

        Chart weightVsBmrChart = new Chart(this, "Weight (kg) vs BMR");
        Chart weightVsCaloriesChart = new Chart(this, "Weight (kg) vs Calories");
        Chart sizeVsBmrChart = new Chart(this, "Size (cm) vs BMR");

        Tab weightVsBmrTab = new Tab(weightVsBmrChart.getTitle());
        Tab weightVsCaloriesTab = new Tab(weightVsCaloriesChart.getTitle());
        Tab sizeVsBmrTab = new Tab(sizeVsBmrChart.getTitle());

        chartPanes.getTabs().add(weightVsBmrTab);
        chartPanes.getTabs().add(weightVsCaloriesTab);
        chartPanes.getTabs().add(sizeVsBmrTab);

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
                new Alert(AlertType.ERROR,
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
        data.getChildren().addAll(dataPanel, submitButton, clearButton);

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
