import Controller.Controller;
import Model.CachedData.SqlController;
import Model.HttpRequest.ApiRequest;
import Model.HttpRequest.Service;
import Model.HttpRequest.ServiceImpl;
import Model.JAVAObject.Holidays.Holiday;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MileStone2Tests {
    private static SqlController testController;
    ApiRequest apiRequest;
    Service service;

    private static String Holidays = """
            [
                {
                    "name": "Easter Sunday",
                    "name_local": "",
                    "language": "",
                    "description": "",
                    "country": "AU",
                    "location": "Australia - Victoria",
                    "type": "Local holiday",
                    "date": "04/17/2022",
                    "date_year": "2022",
                    "date_month": "04",
                    "date_day": "17",
                    "week_day": "Sunday"
                },
                {
                    "name": "Easter Sunday",
                    "name_local": "",
                    "language": "",
                    "description": "",
                    "country": "AU",
                    "location": "Australia - Australian Capital Territory",
                    "type": "Local holiday",
                    "date": "04/17/2022",
                    "date_year": "2022",
                    "date_month": "04",
                    "date_day": "17",
                    "week_day": "Sunday"
                },
                {
                    "name": "Easter Sunday",
                    "name_local": "",
                    "language": "",
                    "description": "",
                    "country": "AU",
                    "location": "Australia - Queensland",
                    "type": "Local holiday",
                    "date": "04/17/2022",
                    "date_year": "2022",
                    "date_month": "04",
                    "date_day": "17",
                    "week_day": "Sunday"
                },
                {
                    "name": "Easter Sunday",
                    "name_local": "",
                    "language": "",
                    "description": "",
                    "country": "AU",
                    "location": "Australia - New South Wales",
                    "type": "Local holiday",
                    "date": "04/17/2022",
                    "date_year": "2022",
                    "date_month": "04",
                    "date_day": "17",
                    "week_day": "Sunday"
                },
                {
                    "name": "Easter Sunday",
                    "name_local": "",
                    "language": "",
                    "description": "",
                    "country": "AU",
                    "location": "Australia - Australian Capital Territory, New South Wales, Northern Territory, Queensland, South Australia, Tasmania, Victoria, Western Australia, Christmas Island, Norfolk Island, Cocos and Keeling Islands, Heard and McDonald Islands",
                    "type": "Observance",
                    "date": "04/17/2022",
                    "date_year": "2022",
                    "date_month": "04",
                    "date_day": "17",
                    "week_day": "Sunday"
                }
            ]""";

    @BeforeEach
    public void setUp() throws InterruptedException {
        //set up the correct date and country
        Controller.curDate = LocalDate.of(2022,4,17);
        Controller.Country = "AU";

        //mock apiRequest to get holidays
        apiRequest = Mockito.mock(ApiRequest.class);
        service = new ServiceImpl();
        service.setApiRequest(apiRequest);
        when(apiRequest.getHoliday(any())).thenReturn(Holidays);
    }

    //set up
    @Test
    public void setTestController() {
        testController = SqlController.getSqlController();
        assertNotNull(testController);
//        testController.removeDB();
    }

    //test valid insertion and selection functions
    @Test
    public void testAllValidDBFunctions() throws InterruptedException {
        testController = SqlController.getSqlController();
        testController.removeDB();
        testController.createDateBase();
        testController.setupDB();
        assertEquals(0,testController.getCountsTable());
        List<Holiday> holidayList = service.getHoliday(Controller.curDate);
        testController.addHolidayInfo(holidayList);
        assertEquals(5,testController.getCountsTable());

        List<Holiday> sqlHolidays = testController.queryHolidays(Controller.curDate);
        assertEquals(5,sqlHolidays.size());
        assertEquals("AU",sqlHolidays.get(0).getCountry());
        assertEquals("Easter Sunday",sqlHolidays.get(3).getName());
        testController.removeDB();
    }

    //test empty holiday insertion and selection (no holiday in that date)
    @Test
    public void testNoHolidaysDBFunctions() {
        testController = SqlController.getSqlController();
        testController.removeDB();
        testController.createDateBase();
        testController.setupDB();
        assertEquals(0,testController.getCountsTable());
        List<Holiday> emptyHolidays = new ArrayList<>();
        testController.addHolidayInfo(emptyHolidays);
        assertEquals(1,testController.getCountsTable());
        List<Holiday> sqlHoliday  = testController.queryHolidays(Controller.curDate);
        assertEquals(1,sqlHoliday.size());
        assertEquals("AU",sqlHoliday.get(0).getCountry());
        assertEquals("2022",sqlHoliday.get(0).getDate_year());
        assertEquals("04",sqlHoliday.get(0).getDate_month());
        assertEquals("17",sqlHoliday.get(0).getDate_day());
        testController.removeDB();
    }

    @Test
    public void testDuplicationInsertion() throws InterruptedException {
        testController = SqlController.getSqlController();
        testController.removeDB();
        testController.createDateBase();
        testController.setupDB();
        assertEquals(0,testController.getCountsTable());

        List<Holiday> holidayList = service.getHoliday(Controller.curDate);
        testController.addHolidayInfo(holidayList);
        assertEquals(5,testController.getCountsTable());

        testController.addHolidayInfo(holidayList);
        assertEquals(5,testController.getCountsTable());
        testController.removeDB();
    }

}
