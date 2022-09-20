package Model.CachedData;

import Controller.Controller;
import Model.JAVAObject.Holidays.Holiday;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SqlController {
    public static SqlController sqlController;
    public static String dbName = "Holiday.db";
    public static String dbURL = "jdbc:sqlite:" + dbName;

    private SqlController() {}

    public void createDateBase(){
        File dbFile = new File(dbName);
        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            // If we get here that means no exception raised from getConnection -
            //meaning it worked
            System.out.println("A new database has been created.");
        } catch (Exception e) {
            System.out.println("e.getMessage()");
            System.exit(-1);
        }
    }

    public void removeDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            boolean result = dbFile.delete();
            if (!result) {
                System.out.println("Couldn't delete existing db file");
                System.exit(-1);
            } else {
                System.out.println("Removed existing DB file.");
            }
        } else {
            System.out.println("No existing DB file.");
        }
    }

    public void setupDB() {
        String createHolidayTableSQL =
                """
                        CREATE TABLE IF NOT EXISTS Holidays (
                            h_id integer PRIMARY KEY NOT NULL,
                            h_name text,
                            h_name_local text,
                            h_language text,
                            h_country text,
                            h_location text,
                            h_type text,
                            h_date text,
                            h_date_year text,
                            h_date_month text,
                            h_date_day text,
                            h_week_day text
                        );        
                        """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
             statement.execute(createHolidayTableSQL);
             System.out.println("Created tables");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    //check duplicate rows in db
    public boolean checkDuplicate(List<Holiday> holidays) {
        //if db is new just return false to add the data
        if (getCountsTable() == 0) {
            return false;
        }
        if (holidays.size() == 0) {
            return false;
        }
        int year = Integer.parseInt(holidays.get(0).getDate_year());
        int month = Integer.parseInt(holidays.get(0).getDate_month());
        int day = Integer.parseInt(holidays.get(0).getDate_day());

        LocalDate date = LocalDate.of(year,month,day);
        List<Holiday> holidayList = queryHolidays(date);
        return holidayList.size() != 0;
    }

    //This method is aiming to figure out the db is created totally new or not
    public long getCountsTable(){
        String sql = "SELECT COUNT(*) FROM Holidays";
        long count = 0L;
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            ResultSet results = statement.executeQuery(sql);
            count = results.getInt("COUNT(*)");
            System.out.println("Current row: "+count);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return count;
    }

    //insert holidays information into db
    public void addHolidayInfo(List<Holiday> h) {

        //check whether db contains the holidays or not
        if (checkDuplicate(h)) return;

        //if not have holiday just add empty name with the date checked
        if (h.size() == 0) {
            String emptyHoliday =
                    """
                    INSERT INTO Holidays (h_name,h_country,h_date_year,h_date_month,h_date_day)
                    VALUES ("",?,?,?,?);
                    """;
            LocalDate date = Controller.curDate;
            List<String> str = correctDateFormat(date);

            try (Connection conn = DriverManager.getConnection(dbURL);
                 PreparedStatement preparedStatement =
                         conn.prepareStatement(emptyHoliday)) {
                preparedStatement.setString(1, Controller.Country);
                preparedStatement.setString(2, str.get(0));
                preparedStatement.setString(3, str.get(1));
                preparedStatement.setString(4, str.get(2));
                preparedStatement.executeUpdate();
                System.out.println("Empty insert");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }else {
            String addHolidayInfo =
                    """
                    INSERT INTO Holidays (h_name,h_name_local,h_language,h_country
                    ,h_location,h_type,h_date,h_date_year,h_date_month,h_date_day,h_week_day)
                    VALUES (?,?,?,?,?,?,?,?,?,?,?)
                    """;
            for (Holiday holiday : h) {
                try (Connection conn = DriverManager.getConnection(dbURL);

                     PreparedStatement preparedStatement =
                             conn.prepareStatement(addHolidayInfo)) {
                    preparedStatement.setString(1, holiday.getName());
                    preparedStatement.setString(2, holiday.getName_local());
                    preparedStatement.setString(3, holiday.getLanguage());
                    preparedStatement.setString(4, holiday.getCountry());
                    preparedStatement.setString(5, holiday.getLocation());
                    preparedStatement.setString(6, holiday.getType());
                    preparedStatement.setString(7, holiday.getDate());
                    preparedStatement.setString(8, holiday.getDate_year());
                    preparedStatement.setString(9, holiday.getDate_month());
                    preparedStatement.setString(10, holiday.getDate_day());
                    preparedStatement.setString(11, holiday.getWeek_day());
                    preparedStatement.executeUpdate();
                    System.out.println("Added holiday data");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    System.exit(-1);
                }
            }
        }

    }

    /*
    This function is aim to correct the format of LocalDate
    eg 9/9/2020 to 09/09/2020
     */
    private List<String> correctDateFormat(LocalDate date) {
        String year = String.valueOf(date.getYear());
        String month;
        if (date.getMonthValue() <= 9) {
            month = "0" + date.getMonthValue();
        } else {
            month = String.valueOf(date.getMonthValue());
        }
        String day;
        if (date.getDayOfMonth() <= 9) {
            day = "0" + date.getDayOfMonth();
        } else {
            day = String.valueOf(date.getDayOfMonth());
        }
        List<String> str = new ArrayList<>();
        str.add(year);
        str.add(month);
        str.add(day);
        return str;
    }

    public List<Holiday> queryHolidays(LocalDate date) {
        List<String> str = correctDateFormat(date);
        List<Holiday> holidays = new ArrayList<>();
        String queryHolidays =
                """
                SELECT *
                FROM Holidays
                WHERE h_date_year = ? AND h_date_month = ?
                AND h_date_day = ? AND h_country = ?
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(queryHolidays)) {
            preparedStatement.setString(1, str.get(0));
            preparedStatement.setString(2, str.get(1));
            preparedStatement.setString(3, str.get(2));
            preparedStatement.setString(4, Controller.Country);

            ResultSet results = preparedStatement.executeQuery();
            while(results.next()) {
                Holiday holiday = new Holiday();
                holiday.setName(results.getString("h_name"));
                holiday.setName_local(results.getString("h_name_local"));
                holiday.setLanguage(results.getString("h_language"));
                holiday.setCountry(results.getString("h_country"));
                holiday.setLocation(results.getString("h_location"));
                holiday.setType(results.getString("h_type"));
                holiday.setDate(results.getString("h_date"));
                holiday.setDate_year(results.getString("h_date_year"));
                holiday.setDate_month(results.getString("h_date_month"));
                holiday.setDate_day(results.getString("h_date_day"));
                holiday.setWeek_day(results.getString("h_week_day"));

                holidays.add(holiday);
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return holidays;
        }

        public static SqlController getSqlController() {
        if (sqlController == null) {
            sqlController = new SqlController();
        }
        return sqlController;
    }
}
