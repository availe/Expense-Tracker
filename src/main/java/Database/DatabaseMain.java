package Database;

import java.sql.Connection;

public class DatabaseMain {
    Connection connection;
    DatabaseMain() {
        connection = DatabaseUtil.getInstance().connectToDatabase();
    }
}
