package WebManager.send;


import WebManager.send.dbabstractions.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;


public class DataBaseFactory {
    private String url = "jdbc:mysql://localhost/simpleweb?useUnicode=true&serverTimezone=UTC";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DataBaseFactory() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        connection = DriverManager.getConnection(url, "root", "x4vQTu9X");
        statement = connection.createStatement();
    }

    public void addUser(String username, String email, String password)
            throws SQLException {
        statement.executeUpdate("INSERT INTO simpleweb.user_data(USERNAME, PASS, EMAIL) VALUES (\'" + username +
                "\', \'" + password + "\', \'" + email + "\');");
    }

    public void deleteUser(String username, String password, String email)
            throws SQLException {
        statement.executeUpdate("DELETE FROM user_data WHERE USERNAME=\'"
                + username + "\' AND PASS=\'" + password + "\' AND EMAIL=\'" + email + "\'");
    }

    public ArrayList<ArrayList<String>> getUserList() throws SQLException {
        ArrayList<ArrayList<String>> userList = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM user_data");
        for (int i = 0; resultSet.next(); i++) {
            userList.add(new ArrayList<>());
            userList.get(i).add(resultSet.getString(1));
            userList.get(i).add(resultSet.getString(3));
            userList.get(i).add(resultSet.getString(2));
        }
        return userList;
    }

    public boolean findUser(String username, String password) throws SQLException {
        resultSet = statement.executeQuery("SELECT EXISTS(SELECT * FROM user_data WHERE USERNAME=\'" + username +
                "\' AND PASS=\'" + password + "\')");
        resultSet.next();
        return resultSet.getBoolean(1);
    }

    public boolean findAdmin(String username, String password) throws SQLException {
        resultSet = statement.executeQuery("SELECT EXISTS(SELECT * FROM user_data WHERE USERNAME=\'" + username +
                "\' AND PASS=\'" + password + "\' AND ISADMIN=\'yes\')");
        resultSet.next();
        return resultSet.getBoolean(1);
    }

    public ArrayList<Room> getRoomList(String username) throws SQLException {
        resultSet = statement.executeQuery("SELECT rooms FROM user_data WHERE USERNAME=\'" + username + "\'");
        resultSet.next();
        String roomString = resultSet.getString(1);

        ArrayList<String> names = new ArrayList<>(Arrays.asList(roomString.split(";")));
        ArrayList<String> descriptions = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            resultSet = statement.executeQuery("SELECT description FROM room_data WHERE name=\'"
                    + names.get(i) + "\'");
            resultSet.next();
            descriptions.add(resultSet.getString(1));
        }

        ArrayList<Room> roomList = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            roomList.add(new Room(names.get(i), descriptions.get(i)));
        }

        return roomList;
    }

    public Room getRoom(String name) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM room_data WHERE name=\'" + name + "\'");
        return new Room(resultSet.getString(1), resultSet.getString(2));
    }

    public void createRoom(String name, String description) throws SQLException {
        statement.executeUpdate("INSERT INTO room_data(name, description, password) VALUES (\'" +
                name + "\', \'" + description + "\', NULL");
    }

    public void createRoom(String name, String description, String isPrivate, String password)
            throws SQLException {
        statement.executeUpdate("INSERT INTO room_data(name, description, password, isprivate) VALUES (\'" +
                name + "\', \'" + description + "\', \'" + password + "\', \'" + isPrivate + "\'");
    }
}
