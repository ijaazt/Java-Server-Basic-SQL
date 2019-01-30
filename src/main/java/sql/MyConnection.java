package src.main.java.sql;

import com.mysql.cj.jdbc.MysqlDataSourceFactory;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyConnection {
    static HashMap<String, String> args =  new HashMap<String, String>() {{
        put("database", "mitello");
        put("username", "root");
        put("password", "xxxxxx");
    }};
    public static Connection getConnection() {
//        System.out.println("\n_____________args = " + args + "_____________\n");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + args.get("database"), args.get("username"), args.get("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}