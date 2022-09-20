package Model.JAVAObject.CustomHoliday;

import java.time.LocalDate;

public class CustomHoliday {

    public String customHolidayName;
    public String description;
    public LocalDate localDate;

    public CustomHoliday(String customHolidayName, String description, LocalDate localDate){
        this.customHolidayName = customHolidayName;
        this.description = description;
        this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getCustomHolidayName() {
        return customHolidayName;
    }

    public String getDescription() {
        return description;
    }

}
