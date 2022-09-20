package View;

import Controller.Controller;
import Model.JAVAObject.CustomHoliday.CustomHoliday;
import View.Calendar.AnchorPaneNode;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CustomWindow {
    private Stage stage;
    private Scene scene;
    private AnchorPane pane;

    private Label holidayName;
    private Label description;

    private TextField holidayNameFiled;
    private TextArea descriptionFiled;

    private Button addBtn;

    public CustomWindow() {
        stage = new Stage();
        pane = new AnchorPane();
        scene = new Scene(pane,400,400);
        stage.setScene(scene);
        stage.setTitle("Custom Holiday");
        stage.show();

        initialise();
    }

    private void initialise() {
        holidayName = new Label();
        holidayName.setLayoutX(10);
        holidayName.setLayoutY(10);
        holidayName.setText("Holiday Name");

        holidayNameFiled = new TextField();
        holidayNameFiled.setLayoutX(10);
        holidayNameFiled.setLayoutY(30);
        holidayNameFiled.setPrefWidth(380);

        description = new Label();
        description.setLayoutX(10);
        description.setLayoutY(60);
        description.setText("Description");

        descriptionFiled = new TextArea();
        descriptionFiled.setLayoutX(10);
        descriptionFiled.setLayoutY(80);
        descriptionFiled.setPrefWidth(380);
        descriptionFiled.setPrefHeight(220);

        addBtn = new Button();
        addBtn.setText("Add");
        addBtn.setPrefSize(130,30);
        addBtn.setLayoutY(330);
        addBtn.setLayoutX(135);
        addBtn.setOnAction(event -> {
            String name = holidayNameFiled.getText();
            String description = descriptionFiled.getText();
            CustomHoliday customHoliday = new CustomHoliday(name, description, Controller.customDate);
            Controller.CustomHolidays.add(customHoliday);

            for (AnchorPaneNode a : CalendarView.allCalendarNodes) {
                if (a.getDate().toString().equals(Controller.customDate.toString())) {
                    Text text = new Text();
                    text.setText(name);
                    a.setStyle("-fx-background-color: lightpink");
                    a.setTopAnchor(text, 15.0 + 20.0);
                    a.setLeftAnchor(text, 0.0);
                    a.getChildren().add(text);
                }
            }
            stage.close();
        });


        pane.getChildren().addAll(holidayName, holidayNameFiled, description, descriptionFiled,addBtn);
    }
}
