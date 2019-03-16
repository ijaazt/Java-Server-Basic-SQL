package src.main.java.sql;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.System.out;
import static java.util.Objects.requireNonNull;
import static src.main.java.sql.ColoredTerminal.*;

class State {
    String name;
    String region;
    String largestCity;
    String capital;
    int population;

    State() {

    }

    State(String name, String region, String largestCity, String capital, int population) {
        this.name = name;
        this.region = region;
        this.largestCity = largestCity;
        this.capital = capital;
        this.population = population;
    }
}

class CreateStates {
    static final String TABLE_NAME = "States";
    static final String NAME = "stateName";
    static final String REGION = "region";
    static final String LARGEST_CITY = "largestCity";
    static final String CAPITAL = "capital";
    static final String POPULATION = "population";
    static final Connection connection = MyConnection.getConnection();

    public static void main(String[] args) {
        CreateStates states = new CreateStates();
        try {
            states.createTable();
            states.addState(new State("Michigan", "midwest", "Ann Arbor", "Detroit", 2));
            states.addState(new State("Pennsylvania", "northeast", "Sufjan", "Mexico", 200000000));
            states.addState(new State("New York", "southwest", "New York City", "Malaysia", 66666));
            states.addState(new State("New Jersey", "darkwest", "Gotham", "Metropolis", 27893202));
            states.addState(new State("Nail", "murknorth", "Nail Head", "Nail Body", 1101));
            states.printTable();
            states.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        String table = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + NAME + " VARCHAR(25), " +
                REGION + " VARCHAR(25), " + LARGEST_CITY + " VARCHAR(25), " +
                CAPITAL + " VARCHAR(25), " + POPULATION + " INT);";
        try (Statement statement = requireNonNull(connection).createStatement()) {
            statement.execute(table);
        }
    }

    private void addState(State state) throws SQLException {
        try (PreparedStatement preparedStatement = requireNonNull(connection).prepareStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setObject(1, state.name);
            preparedStatement.setObject(2, state.region);
            preparedStatement.setObject(3, state.largestCity);
            preparedStatement.setObject(4, state.capital);
            preparedStatement.setObject(5, state.population);
            preparedStatement.execute();
        }
    }

    private List<State> allStates() throws SQLException {
        ArrayList<State> states = new ArrayList<>();
        PreparedStatement statement = requireNonNull(connection).prepareStatement("SELECT * FROM " + TABLE_NAME);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            State state = new State();
            state.region = set.getString(REGION);
            state.population = set.getInt(POPULATION);
            state.largestCity = set.getString(LARGEST_CITY);
            state.capital = set.getString(CAPITAL);
            state.name = set.getString(NAME);
            states.add(state);
        }
        return states;
    }

    private void printTable() throws SQLException {
        out.format(ANSI_PURPLE + "\n%45s\n\n" + ANSI_RESET, "State Table");
        out.format(ANSI_GREEN + "%15s%15s%15s%15s%15s\n\n" + ANSI_RESET, "StateName", "Region", "LargestCity", "Capital", "Population");
        List<State> states = allStates();
        for (State state : states) {
            out.format("%15s%15s%15s%15s%15s\n", state.name, state.region, state.largestCity, state.capital, state.population);
        }
    }

    private void clearTable() throws SQLException {
        requireNonNull(connection).prepareStatement("DROP TABLE " + TABLE_NAME).execute();
    }

    private void close() throws SQLException {
        requireNonNull(connection).close();
    }
}
