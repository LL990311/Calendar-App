package Controller;

import Model.CachedData.SqlController;
import Model.HttpRequest.ApiRequest;
import Model.HttpRequest.Mode.OfflineMode;
import Model.HttpRequest.Mode.OnlineMode;
import Model.HttpRequest.PasteBin.OfflinePasteBin;
import Model.HttpRequest.PasteBin.OnlinePasteBin;
import Model.HttpRequest.PasteBin.PasteBin;
import Model.HttpRequest.Service;
import Model.HttpRequest.ServiceImpl;
import Model.JAVAObject.CustomHoliday.CustomHoliday;
import Model.JAVAObject.Holidays.Holiday;
import View.AlertWindow;
import View.Calendar.AnchorPaneNode;
import View.CalendarView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public static boolean Mode;
    public static boolean OutputMode;
    public static String Country;
    public static Service service;
    public static PasteBin pasteBin;
    public static LocalDate curDate;
    public static LocalDate customDate;
    public static SqlController sqlController;
    public static List<LocalDate> HolidayDateList = new ArrayList<>();
    public static List<LocalDate> CustomHolidayDateList = new ArrayList<>();
    public static List<CustomHoliday> CustomHolidays = new ArrayList<>();

    public Controller(){}

    public static void setOnlineMode() {
        Controller.Mode = true;
        service = new ServiceImpl();
        ApiRequest.mode = OnlineMode.getInstance();
    }

    public static void setOfflineMode() {
        Controller.Mode = false;
        service = new ServiceImpl();
        ApiRequest.mode = OfflineMode.getInstance();
    }

    public static void setupSqlController() {
        sqlController = SqlController.getSqlController();
    }

    public static void createDB() {
        sqlController.createDateBase();
    }

    public static void setupDB() {
        sqlController.setupDB();
    }

    public static void removeDB() {
        sqlController.removeDB();
    }

    public static void addHolidayData(List<Holiday> h) {
        sqlController.addHolidayInfo(h);
    }

    public static List<Holiday> queryHolidayData(LocalDate date) {
        return sqlController.queryHolidays(date);
    }

    public static void setOnlinePaste() {
        OutputMode = true;
        pasteBin = new OnlinePasteBin();
    }

    public static void setOfflinePaste() {
        OutputMode = false;
        pasteBin = new OfflinePasteBin();
    }

    public static void setCountry(String country) {Controller.Country = country;}

    /**
     * Concurrency for getHoliday by using RunLater method
     * @param date
     */
    public static void getHoliday(LocalDate date) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    service.getHoliday(date);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        List<Holiday> holidayList = Controller.service.getCurHolidayList();
                        //if is online mode add holiday into database
                        if (Controller.Mode) {
                            Controller.addHolidayData(holidayList);
                        }
                        //if no holiday indicate, replace no holiday in checked day
                        if (holidayList.size() == 0){
                            //if that day already assigned a custom holiday, just remain the custom holiday
                            for (AnchorPaneNode anchorPaneNode : CalendarView.allCalendarNodes) {
                                if (anchorPaneNode.getDate() == Controller.curDate) {
                                    if (anchorPaneNode.getStyle().equals("-fx-background-color: lightpink")) {
                                        break;
                                    }

                                    //if not assigned a custom holiday then add no holiday on it
                                    Controller.HolidayDateList.add(Controller.curDate);
                                    Text text = new Text();
                                    text.setText("No Holiday");
                                    anchorPaneNode.setStyle("-fx-background-color: lightblue;" +
                                            "-fx-border-color: gold;");
                                    anchorPaneNode.setTopAnchor(text, 15.0 + 20.0);
                                    anchorPaneNode.setLeftAnchor(text, 0.0);
                                    anchorPaneNode.getChildren().add(text);
                                }
                            }
                        }else {
                            CalendarView.updateCell(holidayList);
                        }
                        AlertWindow.alert.close();
                    }
                });
            }
        });
        thread.start();
    }

    /**
     * Concurrency for long report
     * @param date
     */
    public static void getMonthHolidays(LocalDate date){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    service.getMonthHolidays(date);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        List<List<Holiday>> longHolidays = service.getLongHolidays();
                        List<CustomHoliday> customHolidays = Controller.CustomHolidays;
                        StringBuilder sb = new StringBuilder();
                        String longHolidayReport = Controller.service.getReport(longHolidays);
                        String customHolidayReport = Controller.service.getCustomReport(customHolidays);
                        sb.append(longHolidayReport).append("\n").append(customHolidayReport);
                        String output = Controller.sendLongReport(sb.toString());
                        if (Controller.OutputMode) {
                            AlertWindow.onlineOutputAPIWindow(output);
                        } else {
                            AlertWindow.offlineOutputAPIWindow(output);
                        }
                        AlertWindow.alert.close();
                    }
                });
            }
        });
        thread.start();
    }

    /**
     * Concurrency for short report
     */
    public static void sendReport(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<Holiday>> shortHolidays = Controller.service.getShortHolidays();
                List<CustomHoliday> customHolidays = Controller.CustomHolidays;
                StringBuilder sb = new StringBuilder();
                String holidayReport = Controller.service.getReport(shortHolidays);
                String customHolidayReport = Controller.service.getCustomReport(customHolidays);
                sb.append(holidayReport).append("\n").append(customHolidayReport);
                try {
                    pasteBin.SendReport(sb.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (shortHolidays.size() == 0){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                    "no holidays indicate");
                            alert.show();
                        }else {
                            String output = null;
                            try {
                                output = pasteBin.getCurReport();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (Controller.OutputMode) {
                                AlertWindow.onlineOutputAPIWindow(output);
                            }else {
                                AlertWindow.offlineOutputAPIWindow(output);
                            }
                        }
                        AlertWindow.alert.close();
                    }
                });
            }
        });
        thread.start();
    }

    public static String sendLongReport(String res){
        return pasteBin.SendLongReport(res);
    }

}
