package src.main.java.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteUpdate {
    DeleteUpdate() throws SQLException {
        new SelectJoin();
        Connection connection = MyConnection.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("delete from " + CreateCities.TABLE_NAME + " where " + CreateCities.COLUMN_CITY_NAME + "='Ann Arbor'");
        statement.execute("update " + CreateCities.TABLE_NAME + " set " + CreateCities.COLUMN_POPULATION + "=1 where " + CreateCities.COLUMN_CITY_NAME + "='Detroit'");
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
