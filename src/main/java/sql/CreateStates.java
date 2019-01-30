package src.main.java.sql;

import models.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateStates {

    Connection connection = MyConnection.getConnection();

    void createTable() throws SQLException, ClassNotFoundException {
        connection.createStatement().execute("create table if not exists States (name varchar(25), region varchar(25), largestCity varchar (25), capital varchar (25), population int );");
    }

    void addState(State state) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into States values (?, ?, ?, ?, ?)");
        preparedStatement.setObject(1, state.getName());
        preparedStatement.setObject(2, state.getRegion());
        preparedStatement.setObject(3, state.getLargestCity());
        preparedStatement.setObject(4, state.getCapital());
        preparedStatement.setObject(5, state.getPopulation());
        preparedStatement.execute();
    }
    private void setParams(String[] params, PreparedStatement statement) throws SQLException {
        if(params != null && params.length != 0) {
            for (int i = 1; i < params.length + 1; i++) {
                statement.setString(i, params[i-1]);
            }
        }
    }
    void updateState(String whereQuery, String[] params, State modifiedState) throws SQLException {
        if(whereQuery == null)
            whereQuery = "";
        PreparedStatement preparedStatement = connection.prepareStatement("update States set name='" + modifiedState.getName() + "', capital='" + modifiedState.getCapital() + "', largestCity='" + modifiedState.getLargestCity()+ "', population=" + modifiedState.getPopulation() + ", region='" +modifiedState.getRegion() + "' " + whereQuery);
        setParams(params,preparedStatement);
        preparedStatement.execute();
    }

    private State toState(ResultSet set) throws SQLException {
        return new State(set.getString(1), set.getString(2),
                set.getString(3), set.getString(4),
                set.getInt(5));
    }
    List<State> getStates(String whereQuery, String[] params) throws SQLException {
        ArrayList<State> states = new ArrayList<>();
        if(whereQuery == null) whereQuery = "";
        PreparedStatement statement = connection.prepareStatement("select * from States "+ whereQuery);
        setParams(params, statement);
        ResultSet set = statement.executeQuery();
        while(set.next()) {
            states.add(toState(set));
        }
        return states;
    }

    void printTable() throws SQLException {
        System.out.format("%45s\n", "State Table");
        System.out.format("%15s%15s%15s%15s%15s\n", "StateName", "Region", "LargestCity", "Capital", "Population");
        List<State> states = getStates(null, null);
        for(State state: states) {
            System.out.format("%15s%15s%15s%15s%15s\n", state.getName(), state.getRegion(), state.getLargestCity(), state.getCapital(), state.getPopulation());
        }
    }
    void clearTable() throws SQLException {
        connection.prepareStatement("drop table States").execute();
    }
    void close() throws SQLException {
        connection.close();
    }
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
