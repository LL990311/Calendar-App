package Model.HttpRequest;

import Model.JAVAObject.CustomHoliday.CustomHoliday;
import Model.JAVAObject.Holidays.Holiday;

import java.time.LocalDate;
import java.util.List;

public interface Service {

    /*
    This method is aiming for checking a holiday in chosen date
     */
    List<Holiday> getHoliday(LocalDate date) throws InterruptedException;

    /*
        This method is aiming for checking the holidays in that month
    */
    List<List<Holiday>> getMonthHolidays(LocalDate date) throws InterruptedException;

    /*
    This method is aiming for get a checked holiday in current user
     */
    List<List<Holiday>> getShortHolidays();

    /*
    This method is aiming for get a full list of Holidays in chosen month
     */
    List<List<Holiday>> getLongHolidays();

    /*
    This method is aiming for change Holiday information to a String
     */
    String getReport(List<List<Holiday>> holidays);

    /*
    This method is aiming for change customHoliday information to String
     */
    String getCustomReport(List<CustomHoliday> customHolidays);

    void setApiRequest(ApiRequest a);

    List<Holiday> getCurHolidayList();


}
