package src.main.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

class MyConnection {
    private static final HashMap<String, String> args =  new HashMap<String, String>() {{
        put("database", "mitello");
        put("username", "root");
        put("password", "En7j6pur8v");
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