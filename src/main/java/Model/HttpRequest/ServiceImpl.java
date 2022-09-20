package Model.HttpRequest;

import Model.JAVAObject.CustomHoliday.CustomHoliday;
import Model.JAVAObject.Holidays.Holiday;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service{

    private static final Gson gson = new Gson();
    private static ApiRequest apiRequest = new ApiRequest();
    private static List<List<Holiday>> shortHolidays = new ArrayList<>();
    private static List<List<Holiday>> longHolidays = new ArrayList<>();

    private static List<Holiday> curHolidayList = new ArrayList<>();
    private static boolean flag = false;

    @Override
    public List<Holiday> getHoliday(LocalDate date) throws InterruptedException {
        flag = false;
        String s = apiRequest.getHoliday(date);
        List<Holiday> holidayList = gson.fromJson(s,
                new TypeToken<List<Holiday>>(){}.getType());
        shortHolidays.add(holidayList);
        curHolidayList = holidayList;
        flag = true;
        return holidayList;
    }

    @Override
    public List<List<Holiday>> getMonthHolidays(LocalDate date) throws InterruptedException {
        flag = false;
        List<String> listOfHolidays = apiRequest.getMonthHolidays(date);
        List<List<Holiday>> monthHoliday = new ArrayList<>();
        for(String s: listOfHolidays) {
            List<Holiday> holidayList = gson.fromJson(s,
                    new TypeToken<List<Holiday>>(){}.getType());
            monthHoliday.add(holidayList);
        }
        longHolidays = monthHoliday;
        flag = true;
        return monthHoliday;
    }

    @Override
    public List<List<Holiday>> getShortHolidays() {
        return shortHolidays;
    }

    @Override
    public List<List<Holiday>> getLongHolidays() {
        return longHolidays;
    }

    @Override
    public String getReport(List<List<Holiday>> holidays) {
        StringBuilder sb = new StringBuilder();
        for (List<Holiday> holidays1 : holidays) {
            for (Holiday h : holidays1) {
                String name = h.getName();
                String name_local = h.getName_local();
                String language = h.getLanguage();
                String description = h.getDescription();
                String country = h.getCountry();
                String location = h.getLocation();
                String type = h.getType();
                String date = h.getDate();
                String date_year = h.getDate_year();
                String date_month = h.getDate_month();
                String date_day = h.getDate_day();
                String week_day = h.getWeek_day();

                String s = new String(
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
                sb.append(s).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String getCustomReport(List<CustomHoliday> customHolidays) {
        StringBuilder sb = new StringBuilder();
        for (CustomHoliday customHoliday : customHolidays) {
            String name = customHoliday.getCustomHolidayName();
            String date = customHoliday.getLocalDate().toString();
            String description = customHoliday.getDescription();
            String s = new String(
                    "Custom Holiday: \n"
                            + "Name: " + name + "\n"
                            + "Date: " + date + "\n"
                            + "Description: " + description + "\n"
            );
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void setApiRequest(ApiRequest a) {
        this.apiRequest = a;
    }

    @Override
    public List<Holiday> getCurHolidayList() {
        return curHolidayList;
    }

}
