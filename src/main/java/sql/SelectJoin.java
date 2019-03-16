package src.main.java.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class SelectJoin {
    SelectJoin() {
        Connection conn =  MyConnection.getConnection();
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append(CityTable.CITY_TABLE.getName() + "." + CityTable.CITY);
        builder.append(", ");
        builder.append(CityTable.CITY_TABLE.getName() + "." + CityTable.STATE);
        builder.append(", ");
        builder.append(CityTable.CITY_TABLE.getName() + "." + CityTable.POPULATION);
        builder.append(", ");
        builder.append(CreateStates.TABLE_NAME + "." + CreateStates.REGION);
        builder.append(" FROM " + CreateStates.TABLE_NAME);
        builder.append(" INNER JOIN ");
        builder.append(CityTable.CITY_TABLE.getName() + " ON ");
        builder.append(CreateStates.TABLE_NAME + "." + CreateStates.NAME);
        builder.append("=" + CityTable.CITY_TABLE.getName() + "." + CityTable.STATE);
        try {
            PreparedStatement statement = conn.prepareStatement(builder.toString());
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
    public static void main(String[] args) {
        new SelectJoin();
    }

}
