package Model.HttpRequest.Mode;

import java.time.LocalDate;
import java.util.List;

public interface Mode {

    /*
    This method is aiming for checking a holiday in chosen date
     */
    String getHoliday(LocalDate date) throws InterruptedException;

    /*
    This method is aiming for checking all the holidays in chosen month
     */
    List<String> getMonthHoliday(LocalDate date) throws InterruptedException;

}
