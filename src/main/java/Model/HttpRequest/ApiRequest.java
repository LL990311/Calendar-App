package Model.HttpRequest;

import Model.HttpRequest.Mode.Mode;

import java.time.LocalDate;
import java.util.List;

public class ApiRequest {

    public static Mode mode;

    public String getHoliday(LocalDate date) throws InterruptedException {
        return mode.getHoliday(date);
    }

    public List<String> getMonthHolidays(LocalDate date) throws InterruptedException {return mode.getMonthHoliday(date);}

}
