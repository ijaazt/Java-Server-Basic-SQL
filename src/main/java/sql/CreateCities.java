package src.main.java.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static java.lang.System.out;
import static src.main.java.sql.CityTable.*;
import static src.main.java.sql.ColoredTerminal.*;

class City {
    String cityName;
    String stateName;
    int population;
    int id;
    City() { }
    City(String cityName, String stateName, int population) {
        this.population = population;
        this.cityName = cityName;
        this.stateName = stateName;
    }
}

enum CityTable {
    CITY_TABLE("City"),
    CITY("Name"),
    STATE("State"),
    POPULATION("Population"),
    ID("ID");
    private String name;
    String getName() {
        return name;
    }
    CityTable(String name) {
        this.name = name;
    }
}

class CreateCities {
    private final Connection conn = MyConnection.getConnection();

    private static void accept(City city) {
        out.format("%15s%15s%15s\n", city.cityName, city.stateName, city.population);
    }

    private void insertCity(City city) throws SQLException {
        String insert = "INSERT INTO " + CITY_TABLE.getName() + "(" + CITY.getName() + ", " +
                STATE.getName() + ", " + POPULATION.getName() +") " + "VALUES (?, ?, ?)";
        try(PreparedStatement statement = Objects.requireNonNull(conn).prepareStatement(insert)) {
            statement.setObject(1, city.cityName);
            statement.setObject(2, city.stateName);
            statement.setObject(3, city.population);
            statement.execute();
        }

    }

    private void createTable() throws SQLException {
        try(Statement statement = Objects.requireNonNull(conn).createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS " +
                    CITY_TABLE.getName() + " ( " + ID.getName() + " INT KEY AUTO_INCREMENT, " +
                    CITY.getName() + " VARCHAR(25)," + STATE.getName() + " VARCHAR(25), " +
                    POPULATION.getName() + " INT);";
            statement.execute(createTable);
        }
    }

    private Iterator<City> tableContents() throws SQLException {
        List<City> cityList = new ArrayList<>();
        try(Statement statement = Objects.requireNonNull(conn).createStatement()) {
            statement.execute("SELECT * FROM " + CITY_TABLE);
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                City city = new City();
                city.cityName = resultSet.getString(CITY.getName());
                city.stateName = resultSet.getString(STATE.getName());
                city.population = resultSet.getInt(POPULATION.getName());
                city.id = resultSet.getInt(ID.getName());
                cityList.add(city);
            }
        }
        return cityList.iterator();
    }

    private void closeConnection() throws SQLException {
        Objects.requireNonNull(conn).close();
    }

    public static void main(String[] args) throws SQLException {
        CreateCities createCities = new CreateCities();
        createCities.createTable();


        City annArbor = new City("Ann Arbor", "Michigan", 2000);
        City detroit = new City("Detroit", "Michigan", 20000);
        City newYork = new City("New York City", "New York", 210000);
        City gotham = new City("Gotham", "New Jersey", 290000);
        createCities.insertCity(annArbor);
        createCities.insertCity(detroit);
        createCities.insertCity(newYork);
        createCities.insertCity(gotham);

        out.format(ANSI_PURPLE + "\n%30s\n\n" + ANSI_RESET, "City Table" );
        out.format(ANSI_GREEN + "%15s%15s%15s\n\n" + ANSI_RESET, "City Name", "State Name", "Population");
        createCities.tableContents().forEachRemaining(CreateCities::accept);
        createCities.closeConnection();
    }

    private void deleteTable() throws SQLException {
        try(Statement statement = conn.createStatement()) {
            statement.execute("DROP TABLE " + CITY_TABLE);
        }
    }
}
