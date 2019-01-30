package src.main.java.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectJoin {
    SelectJoin() {
        Connection conn =  MyConnection.getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("select Cities.CityName, Cities.StateName, Cities.Population, States.region from States\n" +
                    "inner JOIN Cities on States.name=Cities.StateName");
            statement.execute();
            ResultSet rs = statement.getResultSet();
            System.out.format("%15s%15s%15s%15s\n", "CityName", "StateName", "Population", "Region");
            while(rs.next()) {
                System.out.println();
                System.out.format("%15s%15s%15s%15s\n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        new SelectJoin();
    }

}
