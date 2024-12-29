package ConnecToDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameConnect {
    ConnectR connectR;
    public GameConnect(ConnectR connectR) {
        this.connectR = connectR;
    }
    /**
     * checks if a tile is supposed to collide with player
     *
     * @param tileName name of tile to check
     * @return boolean value representing if collision happens
     */
    public boolean getCollision(String tileName) {
        try {
            Statement stmt = connectR.connection.createStatement();

            String query = "SELECT collision FROM \"Collisions\" WHERE name ='" + tileName + "';";

            ResultSet rs = stmt.executeQuery(query);
            //System.out.println("negro");
            if (rs.next()) {
                return rs.getBoolean("collision");
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Did not connect to database");
        }

        return false;
    }

    public boolean getCollisionForObjects(String objName) {
        try {
            Statement stmt = connectR.connection.createStatement();

            String query = "SELECT collision FROM \"mapforobj\" WHERE name ='" + objName + "';";

            ResultSet rs = stmt.executeQuery(query);
            //System.out.println("negro");
            if (rs.next()) {
                return rs.getBoolean("collision");
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Did not connect to database");
        }

        return false;
    }
}
