package src.main.java.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

class DeleteUpdate {
    private DeleteUpdate() throws SQLException {
        new SelectJoin();
        Connection connection = MyConnection.getConnection();
        Statement statement = Objects.requireNonNull(connection).createStatement();
        statement.execute("DELETE FROM " + CityTable.CITY_TABLE + " WHERE " + CityTable.CITY + "='Ann Arbor'");
        statement.execute("UPDATE " + CityTable.CITY_TABLE + " SET " + CityTable.POPULATION + "=1 WHERE " + CityTable.CITY + "='Detroit'");
        connection.close();
        System.out.print("New Join");
        new SelectJoin();
    }
    public static void main(String[] arg)  {
        try {
            new DeleteUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
