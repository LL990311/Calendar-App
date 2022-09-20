package View;

import Controller.Controller;
import Model.JAVAObject.CustomHoliday.CustomHoliday;
import Model.JAVAObject.Holidays.Holiday;
import View.Calendar.AnchorPaneNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.plaf.synth.ColorType;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class CalendarView {
    public static ArrayList<AnchorPaneNode> allCalendarNodes = new ArrayList<>(35);
    private VBox view;
    private HBox titleBar;
    private Text calendarTitle;
    private YearMonth currentYearMonth;

    private Stage stage;
    private Scene scene;

    private Button shortReportBtn;
    private Button longReportBtn;
    private Button clearDbBtn;
    private Button musicControl;
    private Button customHolidayBtn;

    private ComboBox<Integer> yearSelectBox;

    public CalendarView(YearMonth yearMonth) {
        currentYearMonth = yearMonth;
        GridPane calendar = new GridPane();
        calendar.setPrefSize(600,600);
        calendar.setGridLinesVisible(true);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode node = new AnchorPaneNode();
                node.setPrefSize(200,200);
                node.setMinWidth(86);
                calendar.add(node,j,i);
                allCalendarNodes.add(node);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{ new Text("Sunday"), new Text("Monday"), new Text("Tuesday"),
                new Text("Wednesday"), new Text("Thursday"), new Text("Friday"),
                new Text("Saturday") };
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(200, 10);
            ap.setBottomAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        calendarTitle.setUnderline(true);
        calendarTitle.setFont(new Font(15));
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> previousMonth());
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> nextMonth());

        titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setSpacing(20);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);

        stage = new Stage();
        scene = new Scene(view,1008,600);
        stage.setScene(scene);
        stage.show();

        setButton();
        setComboBox();
        setMusicControl();
    }

    //Set year select combo box
    private void setComboBox() {
        yearSelectBox = new ComboBox<>();
        for (int i = 1970; i < 2038; i++) {
            yearSelectBox.getItems().add(i);
        }
        yearSelectBox.setPromptText(String.valueOf(currentYearMonth.getYear()));
        yearSelectBox.setPrefSize(100,20);
        yearSelectBox.setOnAction(event -> {
            Integer year = yearSelectBox.getValue();
            currentYearMonth = currentYearMonth.withYear(year);
            populateCalendar(currentYearMonth);
        });
        titleBar.getChildren().add(yearSelectBox);
    }

    //set music control button
    private void setMusicControl() {
        musicControl = new Button();
        musicControl.setStyle("-fx-background-color: darkred");
        musicControl.setText("Pause");
        musicControl.setPrefSize(100,20);
        musicControl.setOnAction(event -> {
            if(Objects.equals(musicControl.getText(), "Pause")) {
                musicControl.setStyle("-fx-background-color: darkgreen");
                musicControl.setText("Resume");
                SplashWindow.mediaPlayer.pause();
            }else {
                musicControl.setStyle("-fx-background-color: darkred");
                musicControl.setText("Pause");
                SplashWindow.mediaPlayer.play();
            }
        });
        titleBar.getChildren().add(musicControl);
    }

    private void setButton() {

        shortReportBtn = new Button();
        shortReportBtn.setText("Short Report");
        shortReportBtn.setPrefWidth(150);
        shortReportBtn.setPrefHeight(30);
        shortReportBtn.setLayoutX(50);
        shortReportBtn.setLayoutY(430);
        shortReportBtn.setOnAction(event -> shortReportBtnAction());

        longReportBtn = new Button();
        longReportBtn.setText("Long Report");
        longReportBtn.setPrefSize(150,30);
        longReportBtn.setLayoutX(420);
        longReportBtn.setLayoutY(430);
        longReportBtn.setOnAction(event -> {
            try {
                longReportBtnAction();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        clearDbBtn = new Button();
        clearDbBtn.setText("Clear DB");
        clearDbBtn.setPrefSize(150,30);
        clearDbBtn.setOnAction(event -> clearDbBtnAction());

        customHolidayBtn = new Button();
        customHolidayBtn.setText("Add Holiday");
        customHolidayBtn.setPrefSize(150,30);
        customHolidayBtn.setOnAction(event -> {
            customHolidayAction();
        });

        HBox buttons = new HBox(shortReportBtn,longReportBtn,customHolidayBtn,clearDbBtn);
        buttons.setSpacing(100);
        buttons.setPadding(new Insets(10,0,10,60));

        view.getChildren().addAll(buttons);
    }

    private void clearDbBtnAction() {
        if (Controller.Mode) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure to clear the Database?");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get().equals(buttonTypeOne)){
                Controller.removeDB();
            } else if (result.get().equals(buttonTypeTwo)) {
                alert.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Online mode only, please using it with online mode.");
            alert.showAndWait();
        }
    }

    private void longReportBtnAction() throws ExecutionException, InterruptedException {
        Controller.getMonthHolidays(currentYearMonth.atEndOfMonth());
        AlertWindow.loadingWindow();
    }

    private void shortReportBtnAction() {
        Controller.sendReport();
        AlertWindow.loadingWindow();
    }

    public static void checkBtnAction() {
        try {
            boolean isChecked = isChecked(Controller.curDate);
            boolean isAssigned = isAssigned(Controller.curDate);
            if (isChecked) return;
            if (Controller.Mode) {
                Controller.setupDB();
                List<Holiday> cacheHolidayList = Controller.queryHolidayData(Controller.curDate);
                if (cacheHolidayList.size() == 0) {
                    onlineCheck();
                }else {
                    Controller.service.getShortHolidays().add(cacheHolidayList);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("cache hit for this data – use cache, " +
                            "or request fresh data from the API?");
                    alert.setContentText("Choose your option.");

                    ButtonType buttonTypeOne = new ButtonType("Cache");
                    ButtonType buttonTypeTwo = new ButtonType("API");

                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne){
                        if (cacheHolidayList.get(0).getName().isEmpty()) {
                            if (!isAssigned) {
                                Controller.HolidayDateList.add(Controller.curDate);
                                for (AnchorPaneNode anchorPaneNode : allCalendarNodes) {
                                    if (anchorPaneNode.getDate() == Controller.curDate) {
                                        Text text = new Text();
                                        text.setText("No Holiday");
                                        anchorPaneNode.setStyle("-fx-background-color: lightblue;" +
                                                "-fx-border-color: gold;");
                                        anchorPaneNode.setTopAnchor(text, 15.0 + 20.0);
                                        anchorPaneNode.setLeftAnchor(text, 0.0);
                                        anchorPaneNode.getChildren().add(text);
                                    }
                                }
                            }
                        }else {
                            updateCell(cacheHolidayList);
                        }
                    } else if (result.get() == buttonTypeTwo) {
                        onlineCheck();
                    } else{
                        alert.close();
                    }
                }
            }else {
                onlineCheck();
            }
        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please select a day to check!");
            alert.show();
        }
    }

    /**
     * Add custom holiday action
     */
    private void customHolidayAction() {
        //initialize three combo boxes
        ComboBox<Integer> year = new ComboBox<>();
        ComboBox<String> month = new ComboBox<>();
        ComboBox<String> day = new ComboBox<>();

        for (int i = 1970; i < 2038; i++) {
            year.getItems().add(i);
        }
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                month.getItems().add("0" + i);
                continue;
            }
            month.getItems().add(i + "");
        }
        for (int i = 1; i < 32; i++) {
            if (i < 10) {
                day.getItems().add("0" + i);
                continue;
            }
            day.getItems().add(i + "");
        }

        year.setPromptText("year");
        month.setPromptText("month");
        day.setPromptText("day");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Custom Holiday");
        alert.setHeaderText("Add custom holiday");

        HBox hBox = new HBox(year,month,day);
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        alert.getDialogPane().setContent(hBox);

        ButtonType buttonConfirm = new ButtonType("Confirm");
        ButtonType buttonCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

        //handle the choice
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(buttonConfirm)){
            try {
                // transform selected date to localDate format
                String date = year.getValue() + "-" + month.getValue() + "-" + day.getValue();
                LocalDate localDate = LocalDate.parse(date);
                Controller.customDate = localDate;

                //check that day is checked or not
                boolean isAssigned = isAssigned(Controller.customDate);
                boolean isChecked = isChecked(Controller.customDate);

                if (isAssigned || isChecked) {
                    Alert alertChecked = new Alert(Alert.AlertType.WARNING,
                            "You only can add custom holiday on non-checked day or " +
                                    "non-assigned day.");
                    alertChecked.showAndWait();
                }else {
                    Controller.CustomHolidayDateList.add(localDate);
                    new CustomWindow();
                }
            }catch (Exception e) {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING,
                        "Please select a valid date to add!");
                alertWarning.showAndWait();
            }
        } else if (result.get().equals(buttonCancel)) {
            alert.close();
        }
    }

    private static boolean isAssigned(LocalDate localDate) {
        for (AnchorPaneNode a : allCalendarNodes) {
            if (a.getDate().toString().equals(localDate.toString())) {
                if(a.getStyle().equals("-fx-background-color: lightpink")) {
                    return true;
                }
            }
        }
        return false;
    }

    //check that day is checked or not
    private static boolean isChecked(LocalDate localDate) {
        for(AnchorPaneNode a : allCalendarNodes) {
            if(a.getDate().toString().equals(localDate.toString())) {
                if (a.getStyle().equals("-fx-background-color: lightblue;" +
                        "-fx-border-color: gold;")){
                    return true;
                }
            }
        }
        return false;
    }

    private static void onlineCheck(){
        Controller.getHoliday(Controller.curDate);
        AlertWindow.loadingWindow();
    }

    //update single cell
    public static void updateCell(List<Holiday> cacheHolidayList) {
        Controller.HolidayDateList.add(Controller.curDate);
        int numOfHolidays = cacheHolidayList.size();
        for (AnchorPaneNode a : allCalendarNodes) {
            if (a.getDate() == Controller.curDate) {

                if (a.getStyle().equals("-fx-background-color: lightpink")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "You added a holiday to a day that already has one!");
                    alert.showAndWait();
                    a.getChildren().remove(1);

                    for (LocalDate date : Controller.CustomHolidayDateList) {
                        if (date.toString().equals(a.getDate().toString())) {
                            Controller.CustomHolidayDateList.remove(date);
                            break;
                        }
                    }
                }

                for(int i = 0; i < numOfHolidays; i++) {
                    Text text = new Text();
                    a.setStyle("-fx-background-color: lightblue;" +
                            "-fx-border-color: gold;");
                    text.setText(cacheHolidayList.get(i).getName());
                    a.setTopAnchor(text, i*15.0 + 20.0);
                    a.setLeftAnchor(text, 0.0);
                    text.setOnMouseClicked(e -> {
                        for (Holiday holiday : cacheHolidayList) {
                            if (Objects.equals(text.getText(), holiday.getName())) {
                                new PopWindow(holiday);
                            }
                        }
                    });
                    a.getChildren().add(text);
                }
            }
        }
    }

    public void populateCalendar(YearMonth yearMonth) {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY") ) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarNodes) {
            ap.setDate(calendarDate);
            ap.setStyle("-fx-background-color: none");
            ap.getChildren().clear();
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);

            UpdateCalendarCells(ap);
            updateCustomHoliday(ap);

            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
    }

    //update custom holiday cells
    private void updateCustomHoliday(AnchorPaneNode ap) {
        for (LocalDate date : Controller.CustomHolidayDateList) {
            if (ap.getDate().toString().equals(date.toString())) {
                ap.setStyle("-fx-background-color: lightpink");
                for(CustomHoliday customHoliday : Controller.CustomHolidays) {
                    if (customHoliday.getLocalDate().toString().equals(ap.getDate().toString())) {
                        Text text = new Text();
                        text.setText(customHoliday.getCustomHolidayName());
                        ap.setTopAnchor(text, 15.0 + 20.0);
                        ap.setLeftAnchor(text, 0.0);
                        ap.getChildren().add(text);
                    }
                }
            }
        }
    }

    //update Calendar Cells
    private void UpdateCalendarCells(AnchorPaneNode ap) {
        //add css and texts to a Cell that contains holidays
        for (LocalDate holidayDate : Controller.HolidayDateList) {
            if (ap.getDate().getDayOfMonth() == holidayDate.getDayOfMonth()
                    && ap.getDate().getMonth() == holidayDate.getMonth()
                    && ap.getDate().getYear() == holidayDate.getYear()) {
                ap.setStyle("-fx-background-color: lightblue;" +
                        "-fx-border-color: gold;");
                Text text = new Text();
                text.setText("No holiday");
                ap.setTopAnchor(text, 15.0 + 20.0);
                ap.setLeftAnchor(text, 0.0);
                for (List<Holiday> holidays : Controller.service.getShortHolidays()) {
                    for (int i = 0 ; i < holidays.size(); i++) {
                        String apDateDay = "";
                        String apDateMon = "";
                        if (ap.getDate().getDayOfMonth() < 10) {
                            apDateDay = "0"+ ap.getDate().getDayOfMonth();
                        } else {
                            apDateDay = String.valueOf(ap.getDate().getDayOfMonth());
                        }
                        if (ap.getDate().getMonthValue() < 10) {
                            apDateMon = "0" + ap.getDate().getMonthValue();
                        }else {
                            apDateMon = String.valueOf(ap.getDate().getMonthValue());
                        }

                        if (apDateDay.equals(holidays.get(i).getDate_day())
                        && apDateMon.equals(holidays.get(i).getDate_month())) {
                            text.setText(" ");
                            if (holidays.get(i).getName().isEmpty()) {
                                text.setText("No holiday");
                            }
                            Text curText = new Text();
                            curText.setText(holidays.get(i).getName());
                            ap.setTopAnchor(curText, i*15.0 + 20.0);
                            ap.setLeftAnchor(curText, 0.0);
                            curText.setOnMouseClicked(e -> {
                                for (Holiday holiday : holidays) {
                                    if (Objects.equals(curText.getText(), holiday.getName())) {
                                        new PopWindow(holiday);
                                    }
                                }
                            });
                            ap.getChildren().add(curText);
                        }
                    }
                }
                ap.getChildren().add(text);
            }
        }
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
    }
}
