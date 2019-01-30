package src.main.java.sql;

import models.City;

import java.sql.*;
import java.util.ArrayList;

public class CreateCities {
    private Connection conn = MyConnection.getConnection();
    public static String TABLE_NAME = "Cities";
    public static String COLUMN_CITY_NAME = "CityName"; // varchar
    public static String COLUMN_STATE_NAME = "StateName"; // varchar
    public static String COLUMN_POPULATION = "Population"; // int

    CreateCities createTable() throws SQLException {
        String createTable = String.format("create table if not exists %s( %s varchar(25), %s varchar(25), %s int);", TABLE_NAME, COLUMN_CITY_NAME, COLUMN_STATE_NAME, COLUMN_POPULATION);
        String insert = "insert into " + TABLE_NAME + " values (?, ?, ?)";
        conn.createStatement().execute(createTable);
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("Ann Arbor", "Michigan", 2000));
        cities.add(new City("Detroit", "Michigan", 20000));
        cities.add(new City("New York City", "New York", 210000));
        cities.add(new City("Gotham", "New Jersey", 290000));
        for (City city : cities) {
            PreparedStatement statement = conn.prepareStatement(insert);
            statement.setObject(1, city.getCityName());
            statement.setObject(2, city.getStateName());
            statement.setObject(3, city.getPopulation());
            statement.execute();
        }
        System.out.format("%30s", "City Table");
        System.out.println();
        System.out.format("%15s%15s%15s", "CityName", "StateName", "Population");
        System.out.println();
        Statement selectStatement = conn.createStatement();
        selectStatement.execute("select * from " + TABLE_NAME);
        ResultSet resultSet = selectStatement.getResultSet();
        while(resultSet.next()) {
            System.out.println(String.format("%15s%15s%15s", resultSet.getString(COLUMN_CITY_NAME),
                    resultSet.getString(COLUMN_STATE_NAME), resultSet.getString(COLUMN_POPULATION)));
        }
        return this;
    }
    void closeConnection() throws SQLException {
        conn.close();
    }

    public static void main(String[] args) {
        try {
            new CreateCities().createTable().closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
