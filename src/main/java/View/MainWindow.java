package View;

import Controller.Controller;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.controlsfx.control.WorldMapView;

import java.time.YearMonth;

public class MainWindow {

    private WorldMapView worldMapView = new WorldMapView();

    private static MainWindow mainWindow;

    private Stage stage;
    private final Scene scene;
    private final AnchorPane pane;

    public MainWindow(){
        stage = new Stage();
        pane = new AnchorPane();
        scene = new Scene(pane,1008,665);
        stage.setScene(scene);
        stage.setTitle("Holiday GUI");
        stage.show();
        initWorldMap();

        pane.getChildren().add(worldMapView);
    }

    private void initWorldMap() {
        worldMapView.setCountryViewFactory(country -> {
            worldMapView.setOnMouseClicked(event -> {
                selectCountryAction();
            });
            return new WorldMapView.CountryView(country);
        });
    }

    //Sava and check the selected country
    private void selectCountryAction() {
        String s = worldMapView.getSelectedCountries().toString();
        char[] ch = s.toCharArray();
        if (ch.length > 2){
            {
                String country = s.substring(1,3);
                Controller.setCountry(country);
                new CalendarView(YearMonth.now());
                stage.close();
            }

        }
    }

    public static MainWindow getInstance() {
        if (mainWindow == null) {
            mainWindow = new MainWindow();
        }
        return mainWindow;
    }

    public Scene getScene() {
        return this.scene;
    }

    public Stage getStage() {
        return this.stage;
    }
}
