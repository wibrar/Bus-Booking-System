import java.util.*;
import java.sql.*;

public class BusBookingSystem_Test {
    public static void main(String[] args) {
        Scanner text = new Scanner(System.in);
        Scanner num = new Scanner(System.in);
        
        for (int i = 0; i < i + 1; i++) {
            int selection = 0;
            for (int j = 0; j < j + 1; j++) {
                try {
                    System.out.print("1. Enter 1 to Display All Routes\n"
                            + "2. Enter 2 to Book a Ticket\n"
                            + "3. Enter 3 to Display Reservations\n"
                            + "4. Enter 4 to Update Routes as Admin\n"
                            + "5. Enter 0 to Exit.\n--->> ");
                    selection = num.nextInt();
                    if (selection < 0 || selection > 4) {
                        throw new IllegalMenuException();
                    }
                    break;
                } catch (IllegalMenuException exp) {
                    System.out.println(exp.getMessage());
                }
            }
            
            if (selection == 1) {
                displayRoutesMenu();
            }
            
            if (selection == 2) {
                displayRoutesMenu();
                System.out.print("Enter name : ");
                String name = text.nextLine();
                System.out.print("Enter CNIC : ");
                String cnic = text.nextLine();
                System.out.print("Enter Phone Number : ");
                String phoneNumber = text.nextLine();
                System.out.print("Enter Email : ");
                String email = text.nextLine();
                int route = 0;
                for (int j = 0; j < j + 1; j++) {
                    try {
                        System.out.print("Enter route number: ");
                        route = num.nextInt();
                        int k = routeCheck(route);
                        if (k == 0) {
                            throw new IllegalRouteException();
                        }
                        break;
                    } catch (IllegalRouteException exp) {
                        System.out.println(exp.getMessage());
                    }
                }
                System.out.print("Enter number of passengers: ");
                int passengers = num.nextInt();
                System.out.println("Enter 1 for VIP Booking (1,500 Rupees/Seat)\n"
                        + "Enter 2 for ECONOMY Booking (1,000 Rupees/Seat)");
                double totalAmount = 0;
                System.out.print("Enter Choice : ");
                int choice = num.nextInt();
                if (choice == 1) {
                    Amount amount1 = new VIP();
                    totalAmount = amount1.calculateAmount(passengers);
                } else if (choice == 2) {
                    Amount amount1 = new Economy();
                    totalAmount = amount1.calculateAmount(passengers);
                }
                Customer customer = new Reservations(passengers, route, cnic, name, phoneNumber, email, totalAmount);
            }
            
            if (selection == 3) {
                displayReservationMenu();
                System.out.print("Enter 1 to delete a Reservation\n"
                        + "Enter 0 to go to Main Menu\n --> ");
                int s = num.nextInt();
                if (s == 1) {
                    System.out.print("Enter Route to delete that data: ");
                    int routeNo = num.nextInt();
                    Connection connection = null;
                    Statement statement = null;
                    int num1 = 0;
                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost/bus_routes", "root", "fortis fortuna adiuvat");
                        statement = connection.createStatement();
                        num1 = statement.executeUpdate("delete from reservations where routeNo=" + routeNo + "");
                        System.out.println(num1 + " Routes Updated");
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
            
            if (selection == 4) {
                System.out.print("Enter your password (default password is admin) : ");
                String password = text.nextLine();
                for (int j = 0; j < j + 1; j++) {
                    Admin admin = new Admin();
                    if (password.equals("admin")) {
                        System.out.print("1. Enter 1 to Display All Routes\n"
                                + "2. Enter 2 to Display All Reservations\n"
                                + "3. Enter 3 to Add a Route\n"
                                + "5. Enter 4 to Delete a Route\n"
                                + "6. Enter 0 to Exit.\n--->> ");
                        int s = num.nextInt();
                        if (s == 1) {
                            displayRoutesMenu();
                        }
                        if (s == 2) {
                            displayReservationMenu();
                        }
                        if (s == 3) {
                            System.out.print("Enter Route ID: ");
                            int id = num.nextInt();
                            System.out.print("Enter leaving from: ");
                            String from = text.nextLine();
                            System.out.print("Enter going to: ");
                            String to = text.nextLine();
                            System.out.print("Enter timings (HH:MM:SS): ");
                            String time = text.nextLine();
                            System.out.print("Enter number of available seats: ");
                            int seats = num.nextInt();
                            admin.addRoute(id, from, to, time, seats);
                        }
                        if (s == 4) {
                            displayRoutesMenu();
                            System.out.print("Enter route to delete: ");
                            int del = num.nextInt();
                            admin.deleteRoute(del);
                        }
                        if (s == 0) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            
            if (selection == 0) {
                break;
            }
        }
    }
    
    public static void displayRoutesMenu() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bus_routes", "root", "fortis fortuna adiuvat");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM route");
            System.out.println();
            System.out.println("Route ID Departure From Arrival At Departure Time Available Seats");
            while (resultSet.next()) {
                System.out.printf("%6d%17s%15s%15s%14d",
                        resultSet.getInt("id"),
                        resultSet.getString("from"),
                        resultSet.getString("to"),
                        resultSet.getString("time"),
                        resultSet.getInt("seats"));
                System.out.println();
            }
            System.out.println();
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
    
    public static void displayReservationMenu() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bus_routes", "root", "fortis fortuna adiuvat");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM reservations");
            System.out.println();
            System.out.printf("%7s%11s%15s%14s%13s%12s%9s%10s\n", "CNIC", "Name", "Phone No.",
                    "Email", "Route", "Passengers", "Class", "Amount");
            while (resultSet.next()) {
                System.out.printf("%9s", resultSet.getString("cnic"));
                System.out.printf("%10s", resultSet.getString("name"));
                System.out.printf("%15s", resultSet.getString("phone_Number"));
                System.out.printf("%18s", resultSet.getString("email"));
                System.out.printf("%6d", resultSet.getInt("routeNo"));
                System.out.printf("%9d", resultSet.getInt("passengers"));
                System.out.printf("%14s", resultSet.getString("seatStatus"));
                System.out.printf("%10s", resultSet.getDouble("amount"));
                System.out.println();
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
    
    public static int routeCheck(int route) {
        ArrayList<Integer> routeChecker = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bus_routes", "root", "fortis fortuna adiuvat");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM route");
            while (resultSet.next()) {
                routeChecker.add(resultSet.getInt("id"));
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
        int j = 1;
        for (int i = 0; i < routeChecker.size(); i++) {
            if (routeChecker.get(i) == route) {
                return 1;
            }
        }
        return 0;
    }
}
