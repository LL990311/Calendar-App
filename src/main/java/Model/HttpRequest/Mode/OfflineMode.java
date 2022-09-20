package Model.HttpRequest.Mode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OfflineMode implements Mode{

    private static OfflineMode offlineMode;

    private OfflineMode(){};

    public static OfflineMode getInstance() {
        if (offlineMode == null) {
            offlineMode = new OfflineMode();
        }
        return offlineMode;
    }

    public String getHoliday(LocalDate date) throws InterruptedException {
        String str = "[]";
        if (date == null) {
            Thread.sleep(1000);
            return str;
        }
        if (date.getDayOfMonth() == 3) {
            str = "[\n" +
                    "    {\n" +
                    "        \"name\": \"World Press Freedom Day\",\n" +
                    "        \"name_local\": \"\",\n" +
                    "        \"language\": \"\",\n" +
                    "        \"description\": \"\",\n" +
                    "        \"country\": \"US\",\n" +
                    "        \"location\": \"United States\",\n" +
                    "        \"type\": \"UN observance\",\n" +
                    "        \"date\": \"05/03/2022\",\n" +
                    "        \"date_year\": \"2022\",\n" +
                    "        \"date_month\": \"05\",\n" +
                    "        \"date_day\": \"03\",\n" +
                    "        \"week_day\": \"Tuesday\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"name\": \"Eid al-Fitr\",\n" +
                    "        \"name_local\": \"\",\n" +
                    "        \"language\": \"\",\n" +
                    "        \"description\": \"\",\n" +
                    "        \"country\": \"US\",\n" +
                    "        \"location\": \"United States\",\n" +
                    "        \"type\": \"Muslim\",\n" +
                    "        \"date\": \"05/03/2022\",\n" +
                    "        \"date_year\": \"2022\",\n" +
                    "        \"date_month\": \"05\",\n" +
                    "        \"date_day\": \"03\",\n" +
                    "        \"week_day\": \"Tuesday\"\n" +
                    "    }\n" +
                    "]";
        }else if (date.getDayOfMonth() == 6) {
            str = "[\n" +
                    "    {\n" +
                    "        \"name\": \"Dummy Day\",\n" +
                    "        \"name_local\": \"\",\n" +
                    "        \"language\": \"\",\n" +
                    "        \"description\": \"\",\n" +
                    "        \"country\": \"DUMMY\",\n" +
                    "        \"location\": \"DDD\",\n" +
                    "        \"type\": \"National\",\n" +
                    "        \"date\": \"05/06/2022\",\n" +
                    "        \"date_year\": \"2022\",\n" +
                    "        \"date_month\": \"05\",\n" +
                    "        \"date_day\": \"06\",\n" +
                    "        \"week_day\": \"Friday\"\n" +
                    "    }\n" +
                    "]";
        }
        Thread.sleep(1500);
        return str;
    }

    @Override
    public List<String> getMonthHoliday(LocalDate date) throws InterruptedException {
        String s = "[\n" +
                "    {\n" +
                "        \"name\": \"World Press Freedom Day\",\n" +
                "        \"name_local\": \"\",\n" +
                "        \"language\": \"\",\n" +
                "        \"description\": \"\",\n" +
                "        \"country\": \"US\",\n" +
                "        \"location\": \"United States\",\n" +
                "        \"type\": \"UN observance\",\n" +
                "        \"date\": \"05/03/2022\",\n" +
                "        \"date_year\": \"2022\",\n" +
                "        \"date_month\": \"05\",\n" +
                "        \"date_day\": \"03\",\n" +
                "        \"week_day\": \"Tuesday\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Eid al-Fitr\",\n" +
                "        \"name_local\": \"\",\n" +
                "        \"language\": \"\",\n" +
                "        \"description\": \"\",\n" +
                "        \"country\": \"US\",\n" +
                "        \"location\": \"United States\",\n" +
                "        \"type\": \"Muslim\",\n" +
                "        \"date\": \"05/03/2022\",\n" +
                "        \"date_year\": \"2022\",\n" +
                "        \"date_month\": \"05\",\n" +
                "        \"date_day\": \"03\",\n" +
                "        \"week_day\": \"Tuesday\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\": \"Dummy Day\",\n" +
                "        \"name_local\": \"\",\n" +
                "        \"language\": \"\",\n" +
                "        \"description\": \"\",\n" +
                "        \"country\": \"DUMMY\",\n" +
                "        \"location\": \"DDD\",\n" +
                "        \"type\": \"National\",\n" +
                "        \"date\": \"05/06/2022\",\n" +
                "        \"date_year\": \"2022\",\n" +
                "        \"date_month\": \"05\",\n" +
                "        \"date_day\": \"06\",\n" +
                "        \"week_day\": \"Friday\"\n" +
                "    }\n" +
                "]";
        List<String> list = new ArrayList<>();
        list.add(s);
        Thread.sleep(3000);
        return list;
    }

}
