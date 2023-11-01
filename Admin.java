import java.sql.*;

public class Admin {
    public void addRoute(int id, String from, String to, String time, int seats) {
        Connection connection = null;
        Statement statement = null;
        int num;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bus_routes", "root", "fortis fortuna adiuvat");
            statement = connection.createStatement();
            num = statement.executeUpdate("insert into route(id, from, to, time, seats) values(" + id + ",'" +
                    from + "','" + to + "','" + time + "'," + seats + ")");
            System.out.println(num + " Route Updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteRoute(int del) {
        Connection connection = null;
        Statement statement = null;
        int num = 0;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bus_routes", "root", "fortis fortuna adiuvat");
            statement = connection.createStatement();
            num = statement.executeUpdate("delete from route where id=" + del + "");
            System.out.println(num + " Route Updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
