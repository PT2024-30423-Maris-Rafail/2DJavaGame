package ConnecToDB;

import java.sql.*;

/**
 * Allows working with the database. It also doesn't sleep in summer
 */
public class ConnectR {
    Connection connection;
    public AccountConnect accountConnect;
    public GeoConnect geoConnect;
    public GameConnect gameConnect;
    public UIConnect uiConnect;
    public ConnectR() {
        try {
            connection = DriverManager.getConnection(DBInfo.getUrl(), DBInfo.getUser(), DBInfo.getPassword());
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
        }
        accountConnect = new AccountConnect(this);
        geoConnect = new GeoConnect(this);
        gameConnect = new GameConnect(this);
        uiConnect = new UIConnect(this);
    }
}
