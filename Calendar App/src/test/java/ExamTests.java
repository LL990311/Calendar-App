import Model.HttpRequest.Service;
import Model.HttpRequest.ServiceImpl;
import Model.JAVAObject.CustomHoliday.CustomHoliday;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * In this extension, I only modified Service interface and ServiceImpl class
 * And also add a POJO java class to fulfil the outputs function
 */
public class ExamTests {

    String holidayName = "testHolidayName";
    String description = "testDescription";
    String date = "2022-06-17";
    LocalDate localDate = LocalDate.parse(date);


    private static String customReport = """
            Custom Holiday:\s
            Name: testHolidayName
            Date: 2022-06-17
            Description: testDescription
            
            Custom Holiday:\s
            Name: testHolidayName
            Date: 2022-06-17
            Description: testDescription
            
            """;

    @Test
    public void testCustomHolidayConstructor() {
        CustomHoliday customHoliday = new CustomHoliday(holidayName,description,localDate);
        assertNotNull(customHoliday);
        assertEquals(description,customHoliday.getDescription());
        assertEquals(holidayName, customHoliday.getCustomHolidayName());
        assertEquals(localDate.toString(),customHoliday.getLocalDate().toString());
    }

    @Test
    public void testServiceGetCustomReport() {
        Service service = new ServiceImpl();
        CustomHoliday customHoliday = new CustomHoliday(holidayName,description,localDate);
        CustomHoliday customHoliday1 = new CustomHoliday(holidayName,description,localDate);
        List<CustomHoliday> customHolidayList = new ArrayList<>();
        customHolidayList.add(customHoliday1);
        customHolidayList.add(customHoliday);
        String report = service.getCustomReport(customHolidayList);
        assertEquals(customReport,report);
    }
}
