package ConnecToDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class UIConnect {
    ConnectR connectR;
    public UIConnect(ConnectR connectR) {
        this.connectR = connectR;
    }
    /**
     * Counts entries in a table
     *
     * @param tableName table to check
     * @return number of entries
     */
    public int getSizeOfTable(String tableName) {
        try {
            Statement stmt = connectR.connection.createStatement();

            String query = "SELECT COUNT(*) FROM " + tableName;

            ResultSet rs = stmt.executeQuery(query);
            //System.out.println("negro");
            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("Did not connect to database");
        }
        return 0;
    }

    /**
     * Gets a message from the MESSAGES database. Is set to be funny
     *
     * @return String representing a funny message for the GUI
     */
    public String getMessage() {
        int size = getSizeOfTable("messages");
        Random random = new Random();

        try {
            Statement stmt = connectR.connection.createStatement();

            String query = "SELECT messages.message from messages where id = " + (random.nextInt(size) + 1) + ";";
            //System.out.println(random.nextInt(tableSize));
            ResultSet rs = stmt.executeQuery(query);
            //System.out.println("negro");
            if (rs.next()) {
                return rs.getString("message");
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Did not connect to database");
        }
        return null;

    }

    public int currentTip = 1;

    /**
     * Helps users with a small tip
     *
     * @return String representing the tip
     */
    public String getTip() {

        try {
            Statement stmt = connectR.connection.createStatement();

            String query = "SELECT tips.tips from tips where id = " + currentTip + ";";
            //System.out.println(random.nextInt(tableSize));
            ResultSet rs = stmt.executeQuery(query);
            //System.out.println("negro");
            if (rs.next()) {
                return rs.getString("tips");
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Did not connect to database");
        }
        return null;

    }

    /**
     * @return time to display the certain tip for
     */
    public int getTipTime() {

        try {
            Statement stmt = connectR.connection.createStatement();

            String query = "SELECT tips.active_seconds from tips where id = " + currentTip + ";";
            //System.out.println(random.nextInt(tableSize));
            ResultSet rs = stmt.executeQuery(query);
            //System.out.println("negro");
            if (rs.next()) {
                return rs.getInt("active_seconds");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("Did not connect to database");
        }
        return 0;

    }
}
