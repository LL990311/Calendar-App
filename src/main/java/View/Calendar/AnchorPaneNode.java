package View.Calendar;

import Controller.Controller;
import Model.JAVAObject.CustomHoliday.CustomHoliday;
import View.CalendarView;
import View.PopWindow;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.Optional;

public class AnchorPaneNode extends AnchorPane {

    private LocalDate date;

    /*
    Set MouseEntered, MouseExisted and MouseClicked
    action to every single AnchorPaneNode.
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        this.setOnMouseEntered(event -> {
            if (this.getStyle().equals("-fx-background-color: lightblue;" +
                    "-fx-border-color: gold;")) {
                return;
            }else if (this.getStyle().equals("-fx-background-color: lightpink")) {
                return;
            }else {
                this.setStyle("-fx-background-color: lightgrey");
            }
        });
        this.setOnMouseExited(event -> {
            if (this.getStyle().equals("-fx-background-color: lightblue;" +
                    "-fx-border-color: gold;")) {
                return;
            }else if (this.getStyle().equals("-fx-background-color: lightpink")){
                return;
            }else {
                this.setStyle("-fx-background-color: none");
            }
        });
        this.setOnMouseClicked(e -> {
            if (this.getStyle().equals("-fx-background-color: lightpink")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Choice");
                alert.setContentText("Do you want to check holiday in that day \n" +
                        "Or you want to check the custom holiday description");

                ButtonType button1 = new ButtonType("Holiday");
                ButtonType button2 = new ButtonType("Description");

                alert.getButtonTypes().setAll(button1, button2);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get().equals(button1)) {
                    Controller.curDate = this.date;
                    CalendarView.checkBtnAction();
                    return;
                }else if (result.get().equals(button2)) {
                    for (CustomHoliday customHoliday : Controller.CustomHolidays) {
                        if (this.getDate().toString().equals(customHoliday.getLocalDate().toString())) {
                            new PopWindow(customHoliday);
                            return;
                        }
                    }
                }
            }
            Controller.curDate = this.date;
            CalendarView.checkBtnAction();
        });
    }

    public LocalDate getDate(){return date;}

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
