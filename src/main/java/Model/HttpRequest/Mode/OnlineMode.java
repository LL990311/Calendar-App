package Model.HttpRequest.Mode;

import Controller.Controller;
import kong.unirest.Unirest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OnlineMode implements Mode{
    private static OnlineMode onlineMode;
    private final String INPUT_API_KEY = "a01ec75bab9a4c54b022ed9575b03797";

    @Override
    public String getHoliday(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        String response = Unirest.get("https://holidays.abstractapi.com/v1/"
        + "?api_key=" + INPUT_API_KEY
        + "&country=" + Controller.Country
        + "&year=" + year
        + "&month=" + month
        + "&day=" + day).asJson().getBody().getArray().toString();

        return response;
    }

    @Override
    public List<String> getMonthHoliday(LocalDate date){
        int year = date.getYear();
        int month = date.getMonthValue();

        List<String> list = new ArrayList<>();

        int monthOfDays = date.getMonth().length(date.isLeapYear());

        for (int i = 1; i <= monthOfDays; i++) {
            String response = Unirest.get("https://holidays.abstractapi.com/v1/"
                    + "?api_key=" + INPUT_API_KEY
                    + "&country=" + Controller.Country
                    + "&year=" + year
                    + "&month=" + month
                    + "&day=" + i).asJson().getBody().getArray().toString();

            list.add(response);
            System.out.println(response);

            boolean flag = true;
            long start = System.currentTimeMillis()/1000;
            while(flag){
                long cur = System.currentTimeMillis()/1000;
                if (cur - start >= 2) {
                    flag = false;
                }
            }
        }

        return list;
    }


    private OnlineMode(){};

    public static OnlineMode getInstance() {
        if (onlineMode == null) {
            onlineMode = new OnlineMode();
        }
        return onlineMode;
    }
}
