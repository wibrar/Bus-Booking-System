import java.sql.*;

public class Reservations extends Customer {
    private int passengers;
    private int route;
    private double totalAmount;

    public Reservations(int passengers, int route, String cnic, String name, String phoneNo, String email, double totalAmount) {
        super(cnic, name, phoneNo, email);
        this.passengers = passengers;
        this.route = route;
        this.totalAmount = totalAmount;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bus_routes", "root", "fortis fortuna adiuvat");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM route");
            int j = 0;
            int seats = 0;

            while (resultSet.next()) {
                if (resultSet.getInt("id") == route) {
                    seats = resultSet.getInt("seats");
                }
            }

            int random = seats;
            seats -= passengers;

            try {
                if (seats < 0) {
                    j = 1;
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("\n<---Only " + random + " Seats Are Available--->\n");
            }

            if (j == 0) {
                double amount = this.totalAmount / passengers;

                if (amount == 1500) {
                    statement.executeUpdate("UPDATE route SET seats = '" + seats + "' WHERE id='" + route + "'");
                    statement.executeUpdate("INSERT INTO reservations(cnic, name, phone_number, email, routeNo, passengers, seatStatus, amount)"
                            + "VALUES('" + super.getCnic() + "','" + super.getName() + "','" + super.getPhoneNo() + "','" + super.getEmail() + "'," + this.route + "," + this.passengers + ",'" + "VIP" + "'," + this.totalAmount + ")");
                    System.out.println("Successfully Seat Booked!");
                    System.out.println("Total Bill = " + this.totalAmount);
                } else if (amount == 1000) {
                    statement.executeUpdate("UPDATE route SET seats = '" + seats + "' WHERE id='" + route + "'");
                    statement.executeUpdate("INSERT INTO reservations(cnic, name, phone_number, email, routeNo, passengers, seatStatus, amount)"
                            + "VALUES('" + super.getCnic() + "','" + super.getName() + "','" + super.getPhoneNo() + "','" + super.getEmail() + "'," + this.route + "," + this.passengers + ",'" + "ECONOMY" + "'," + this.totalAmount + ")");
                    System.out.println("Successfully Seat Booked!");
                    System.out.println("Total Bill = " + this.totalAmount);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
