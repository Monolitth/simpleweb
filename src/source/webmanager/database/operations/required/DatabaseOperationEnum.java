package webmanager.database.operations.required;

import webmanager.database.operations.*;
import webmanager.interfaces.DatabaseOperation;
import webmanager.util.Checker;

public enum DatabaseOperationEnum {
    CHANGEUSERDATA {
        {
            operation = new ChangeUserData();
        }
    },
    CREATEROOM {
        {
            operation = new CreateRoom();
        }
    },
    CREATEUSER {
        {
            operation = new CreateUser();
        }
    },
    FINDUSER {
        {
            operation = new FindUser();
        }
    },
    FINDROOM {
        {
            operation = new FindRoom();
        }
    },
    GETROOMDATA {
        {
            operation = new GetRoomData();
        }
    },
    GETROOMLISTBYUSER {
        {
            operation = new GetRoomListByUser();
        }
    },
    GETUSERDATA {
        {
            operation = new GetUserData();
        }
    },
    ISUSEREXISTS {
        {
            operation = new IsUserExists();
        }
    },
    ROOMADDTOUSER {
        {
            operation = new RoomAddToUser();
        }
    },
    GETROOM {
        {
            operation = new GetRoom();
        }
    };

    DatabaseOperation operation;

    public static DatabaseOperationEnum getInstance(String s) {
        if (Checker.isContainsWrong(s))
            return null;
        return DatabaseOperationEnum.valueOf(s.toUpperCase());
    }

    public DatabaseOperation getOperation() {
        return operation;
    }
}
