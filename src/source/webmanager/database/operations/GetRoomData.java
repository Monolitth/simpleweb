package webmanager.database.operations;

import webmanager.Controller;
import webmanager.database.abstractions.Room;
import webmanager.database.operations.required.DatabaseOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetRoomData implements DatabaseOperation<Room, Room> {
    @Override
    public Room operate(Statement statement, Room room) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM room_data WHERE ROOMNAME='" + room.getName() + "'");
            return new Room(resultSet.getString(1), resultSet.getString(2));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Controller.logger.warning("SQLException:\n\t" + e.getMessage() + "\n\t" + e.getSQLState() + "\n\t" +
                    e.getCause());
            return null;
        }
    }
}
