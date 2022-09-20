import Model.HttpRequest.ApiRequest;
import Model.HttpRequest.Service;
import Model.HttpRequest.ServiceImpl;
import Model.JAVAObject.Holidays.Holiday;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class MileStone1Tests {

    ApiRequest apiRequest;
    Service service;

    //this is a json example from abstract api and copying it for testing
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

    //this is a json example from abstract api, and I copy it through terminal so the format is not good but is worked for testing
    //and copying it for testing.
    private static String longHolidays = """
            []#
            [{"name":"May Day","name_local":"","language":"","description":"","country":"AU","location":"Australia - Northern Territory, Queensland","type":"Local holiday","date":"05/02/2022","date_year":"2022","date_month":"05","date_day":"02","week_day":"Monday"}]#
            [{"name":"Hari Raya Puasa","name_local":"","language":"","description":"","country":"AU","location":"Australia - Cocos and Keeling Islands, Christmas Island","type":"Local holiday","date":"05/03/2022","date_year":"2022","date_month":"05","date_day":"03","week_day":"Tuesday"},{"name":"Eid ul Fitr","name_local":"","language":"","description":"","country":"AU","location":"Australia","type":"Muslim","date":"05/03/2022","date_year":"2022","date_month":"05","date_day":"03","week_day":"Tuesday"}]#
            []#
            [{"name":"Yom HaAtzmaut","name_local":"","language":"","description":"","country":"AU","location":"Australia","type":"Hebrew","date":"05/05/2022","date_year":"2022","date_month":"05","date_day":"05","week_day":"Thursday"}]#
            []#
            []#
            [{"name":"Mother's Day","name_local":"","language":"","description":"","country":"AU","location":"Australia","type":"Observance","date":"05/08/2022","date_year":"2022","date_month":"05","date_day":"08","week_day":"Sunday"}]#
            []#
            []#
            []#
            []#
            []#
            []#
            []#
            []#
            []#
            []#
            [{"name":"Lag B'Omer","name_local":"","language":"","description":"","country":"AU","location":"Australia","type":"Hebrew","date":"05/19/2022","date_year":"2022","date_month":"05","date_day":"19","week_day":"Thursday"}]#
            []#
            []#
            []#
            []#
            []#
            []#
            [{"name":"Ascension Day","name_local":"","language":"","description":"","country":"AU","location":"Australia","type":"Christian","date":"05/26/2022","date_year":"2022","date_month":"05","date_day":"26","week_day":"Thursday"},{"name":"National Sorry Day","name_local":"","language":"","description":"","country":"AU","location":"Australia","type":"Observance","date":"05/26/2022","date_year":"2022","date_month":"05","date_day":"26","week_day":"Thursday"}]#
            []#
            []#
            []#
            [{"name":"Reconciliation Day","name_local":"","language":"","description":"","country":"AU","location":"Australia - Australian Capital Territory","type":"Local holiday","date":"05/30/2022","date_year":"2022","date_month":"05","date_day":"30","week_day":"Monday"}]#
            []
            """;

    //This is a report example and copying it from PasteBin for testing
    private static String longReport = """
            Name: May Day
            name_local:\s
            language:\s
            description:\s
            country: AU
            location: Australia - Northern Territory, Queensland
            type: Local holiday
            date: 05/02/2022
            date_year: 2022
            date_month: 05
            date_day: 02
            week_day: Monday
            
            Name: Hari Raya Puasa
            name_local:\s
            language:\s
            description:\s
            country: AU
            location: Australia - Cocos and Keeling Islands, Christmas Island
            type: Local holiday
            date: 05/03/2022
            date_year: 2022
            date_month: 05
            date_day: 03
            week_day: Tuesday
            
            Name: Eid ul Fitr
            name_local:\s
            language:\s
            description:\s
            country: AU
            location: Australia
            type: Muslim
            date: 05/03/2022
            date_year: 2022
            date_month: 05
            date_day: 03
            week_day: Tuesday
            
            Name: Yom HaAtzmaut
            name_local:\s
            language:\s
            description:\s
            country: AU
            location: Australia
            type: Hebrew
            date: 05/05/2022
            date_year: 2022
            date_month: 05
            date_day: 05
            week_day: Thursday
            
            Name: Mother's Day
            name_local:\s
            language:\s
            description:\s
            country: AU
            location: Australia
            type: Observance
            date: 05/08/2022
            date_year: 2022
            date_month: 05
            date_day: 08
            week_day: Sunday
            
            Name: Lag B'Omer
            name_local:\s
            language:\s
            description:\s
            country: AU
            location: Australia
            type: Hebrew
            date: 05/19/2022
            date_year: 2022
            date_month: 05
            date_day: 19
            week_day: Thursday
            
            Name: Ascension Day
            name_local:\s
            language:\s
            description:\s
            country: AU
            location: Australia
            type: Christian
            date: 05/26/2022
            date_year: 2022
            date_month: 05
            date_day: 26
            week_day: Thursday
            
            Name: National Sorry Day
            name_local:\s
            language:\s
            description:\s
            country: AU
            location: Australia
            type: Observance
            date: 05/26/2022
            date_year: 2022
            date_month: 05
            date_day: 26
            week_day: Thursday
            
            Name: Reconciliation Day
            name_local:\s
            language:\s
            description:\s
            country: AU
            location: Australia - Australian Capital Territory
            type: Local holiday
            date: 05/30/2022
            date_year: 2022
            date_month: 05
            date_day: 30
            week_day: Monday
            
            """;

    //Set up
    @BeforeEach
    public void setup() throws InterruptedException {
        apiRequest = Mockito.mock(ApiRequest.class);
        service = new ServiceImpl();
        service.setApiRequest(apiRequest);
        when(apiRequest.getHoliday(any())).thenReturn(Holidays);
        when(apiRequest.getMonthHolidays(any())).thenReturn(Arrays.asList(longHolidays.split("#")));
        assertNotNull(apiRequest);
    }

    //Testing GetHoliday method
    @Test
    public void testGetHoliday() throws InterruptedException {
        List<Holiday> holidays = service.getHoliday(LocalDate.now());
        assertEquals(5,holidays.size());
        assertEquals("Easter Sunday",holidays.get(0).getName());
        assertEquals("AU",holidays.get(0).getCountry());
        assertEquals("Australia - Victoria",holidays.get(0).getLocation());
    }

    //Testing checked holidays
    @Test
    public void testGetShortHoliday() throws InterruptedException {
        service.getHoliday(LocalDate.now());
        List<List<Holiday>> checkedHolidays = service.getShortHolidays();
        assertEquals(1,checkedHolidays.size());
        assertEquals(5,checkedHolidays.get(0).size());
        assertEquals("Easter Sunday",checkedHolidays.get(0).get(0).getName());
        assertEquals("AU",checkedHolidays.get(0).get(0).getCountry());
        assertEquals("Australia - Victoria",checkedHolidays.get(0).get(0).getLocation());

        //check another holiday
        service.getHoliday(LocalDate.now());
        assertEquals(2,checkedHolidays.size());
        assertEquals(5,checkedHolidays.get(1).size());
        assertEquals("Easter Sunday",checkedHolidays.get(1).get(0).getName());
        assertEquals("AU",checkedHolidays.get(1).get(0).getCountry());
        assertEquals("Australia - Victoria",checkedHolidays.get(1).get(0).getLocation());
    }

    //Testing get all holidays in a month
    @Test
    public void testGetMonthHolidays() throws InterruptedException {
        List<List<Holiday>> monthHolidays = service.getMonthHolidays(LocalDate.now());
        assertEquals(31,monthHolidays.size());
        assertEquals("May Day",monthHolidays.get(1).get(0).getName());
        assertEquals("Hari Raya Puasa",monthHolidays.get(2).get(0).getName());
        assertEquals("Eid ul Fitr",monthHolidays.get(2).get(1).getName());
    }

    //Testing the list of all holidays in chosen month
    @Test
    public void testGetLongHolidays() throws InterruptedException {
        service.getMonthHolidays(LocalDate.now());
        List<List<Holiday>> longHolidays = service.getLongHolidays();
        assertEquals(31,longHolidays.size());
        assertEquals("Reconciliation Day",longHolidays.get(29).get(0).getName());
    }

    //Testing get report
    @Test
    public void getReport() throws InterruptedException {
        service.getMonthHolidays(LocalDate.now());
        String report = service.getReport(service.getLongHolidays());
        assertEquals(longReport,report);
    }

}
