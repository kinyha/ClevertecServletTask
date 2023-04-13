package by.bratchykau.clevertecservlettask.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void init(Connection connection) throws SQLException, IOException {
        InputStream inputStream = DatabaseInitializer.class.getResourceAsStream("/init.sql");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String sql = sb.toString();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }
}
