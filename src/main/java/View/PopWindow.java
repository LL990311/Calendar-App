package View;

import Model.JAVAObject.CustomHoliday.CustomHoliday;
import Model.JAVAObject.Holidays.Holiday;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Description pop window
 */
public class PopWindow {

    private Stage stage;
    private Scene scene;
    private AnchorPane pane;

    private TextFlow descriptionText = new TextFlow();

    private Holiday holiday;

    public PopWindow(Holiday holiday) {
        stage = new Stage();
        pane = new AnchorPane();
        scene = new Scene(pane,300,400);
        stage.setTitle("Description");
        stage.setScene(scene);
        stage.show();

        this.holiday = holiday;

        setTextFiled();
    }

    public PopWindow(CustomHoliday customHoliday) {
        stage = new Stage();
        pane = new AnchorPane();
        scene = new Scene(pane,200,300);
        stage.setTitle("Description");
        stage.setScene(scene);
        stage.show();

        Text text = new Text();
        text.setText(
                "Description: " + "\n"
                + customHoliday.getDescription() + "\n"
        );
        text.setLayoutY(20);
        text.setLayoutX(10);
        pane.getChildren().add(text);
    }

    /*
    Translate it to String and displaying it on the window
     */
    public void setTextFiled() {

                String name = holiday.getName();
                String name_local = holiday.getName_local();
                String language = holiday.getLanguage();
                String description = holiday.getDescription();
                String country = holiday.getCountry();
                String location = holiday.getLocation();
                String type = holiday.getType();
                String date = holiday.getDate();
                String date_year = holiday.getDate_year();
                String date_month = holiday.getDate_month();
                String date_day = holiday.getDate_day();
                String week_day = holiday.getWeek_day();

                Text text = new Text(10,20,"");
                text.setText(
                        "Name: " + name + "\n"
                                + "name_local: " + name_local + "\n"
                                + "language: " + language + "\n"
                                + "description: " + description + "\n"
                                + "country: " + country + "\n"
                                + "location: " + location + "\n"
                                + "type: " + type + "\n"
                                + "date: " + date + "\n"
                                + "date_year: " + date_year + "\n"
                                + "date_month: " + date_month + "\n"
                                + "date_day: " + date_day + "\n"
                                + "week_day: " + week_day + "\n"
                );

                text.setFont(Font.font(16));

                descriptionText.getChildren().add(text);

        descriptionText.setLineSpacing(1.2);
        pane.getChildren().add(descriptionText);
    }


}
