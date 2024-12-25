package ConnecToDB;

import GameState.TimeScore;
import RegisterForm.User;
import geoGame.Location;
import geoGame.ScoreBoard;

import java.sql.*;
import java.util.Random;

public class ConnectR {
    Connection connection;

    public ConnectR() {
        try {
            connection = DriverManager.getConnection(DBInfo.getUrl(), DBInfo.getUser(), DBInfo.getPassword());
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
        }

    }

    public boolean getCollision(String imageName) {
        try {
            Statement stmt = connection.createStatement();

            String query = "SELECT collision FROM \"Collisions\" WHERE name ='" + imageName + "';";

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
            Statement stmt = connection.createStatement();

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

    public int getSizeOfTable(String tableName) {
        try {
            Statement stmt = connection.createStatement();

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

    public String getMessage() {
        int size = getSizeOfTable("messages");
        Random random = new Random();

        try {
            Statement stmt = connection.createStatement();

            String query = "SELECT messages.message from messages where id = " + (random.nextInt(size) + 1) + ";";
            //System.out.println(random.nextInt(size));
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

    public String getTip() {

        try {
            Statement stmt = connection.createStatement();

            String query = "SELECT tips.tips from tips where id = " + currentTip + ";";
            //System.out.println(random.nextInt(size));
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

    public int getTipTime() {

        try {
            Statement stmt = connection.createStatement();

            String query = "SELECT tips.active_seconds from tips where id = " + currentTip + ";";
            //System.out.println(random.nextInt(size));
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

    public Location getCoordinates(int id) {
        Location location;
        try {
            Statement stmt = connection.createStatement();

            String query = "SELECT locations.coordinates,country.country_name from locations JOIN public.country ON locations.country_id = country.id WHERE locations.id = " + id + ";";
            //System.out.println(random.nextInt(size));
            ResultSet rs = stmt.executeQuery(query);
            //System.out.println("negro");
            if (rs.next()) {
                String coordinates = rs.getString("coordinates");
                //System.out.println(coordinates);
                String countryName = rs.getString("country_name");
                System.out.println(countryName);
                location = new Location();
                location.setCoordinates(coordinates);
                location.setCountry(countryName);
                return location;
            }
            System.out.println("cioara");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void markVisited(int id) {
        try {
            Statement st = connection.createStatement();
            String query = "UPDATE locations SET checked = true WHERE id = " + id + ";";
            st.executeUpdate(query);
            //ResultSet rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Marked id " + id + " visited");
    }

    public void markUNVisited(int id) {
        try {
            Statement st = connection.createStatement();
            String query = "UPDATE locations SET checked = false WHERE id = " + id + ";";
            st.executeUpdate(query);
            //ResultSet rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkVisited(int id) {
        try {
            Statement st = connection.createStatement();
            String query = "SELECT locations.checked FROM locations WHERE id = " + id + ";";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return rs.getBoolean("checked");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getValidIndex(int size) {
        Random random = new Random();
        int niggus = random.nextInt(size) + 1;

        System.out.println("NIGGUS ESTE " + niggus);
        //niggus = 5;
        int copy = niggus + 1;
        while (niggus > 0 && checkVisited(niggus)) {
            System.out.println("FOR NIGGUS = " + niggus + " IS " + checkVisited(niggus));
            niggus--;
        }
        if (niggus == 0) {
            while (checkVisited(copy)) {
                copy++;
            }
            niggus = copy;
        }

        markVisited(niggus);
        return niggus;
    }

    public void resetTable(int size) {
        for (int i = 1; i <= size; i++) {
            if (checkVisited(i)) {
                markUNVisited(i);
            }

        }
    }

    /// LOG IN
    public User getAccountBasedOnUsername(String username) {

        String query = "SELECT * FROM users WHERE username = '" + username + "' ;";

        return getAccount(query);
    }

    public User getAccountBasedOnEmail(String email) {

        String query = "SELECT * FROM users WHERE email = '" + email + "' ;";

        return getAccount(query);
    }

    public User getAccount(String query) {
        User user;
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                user = new User();
                user.setEmail(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                return user;
            }

            return null;
        } catch (Exception e) {
            System.out.println("problema la get user EMAIL");
            e.printStackTrace();
        }
        return null;
    }

    public int getUserIDEmail(String email) {
        int id;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT id FROM users WHERE email = '" + email + "';";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt("id");
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Error at trying to get user ID");
            e.printStackTrace();
        }
        return 0;
    }

    public int getUserIDUsername(String username) {
        int id;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT id FROM users WHERE USERNAME = '" + username + "';";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                id = rs.getInt("id");
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Error at trying to get user ID");
            e.printStackTrace();
        }
        return 0;
    }

    public void registerUser(String username, String password, String email) throws SQLException {

        Statement stmt = connection.createStatement();

        String query = "INSERT INTO users (username, password, email) VALUES ('" + username + "','" + password + "','" + email + "');";

        stmt.executeUpdate(query);
    }

    public ScoreBoard getScoreBoard() {
        ScoreBoard scoreBoard = new ScoreBoard();
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT username, CONCAT(hours,'h ',minutes,'m ',seconds,'s ',milliseconds,'ms') AS \"time\" FROM scores JOIN users ON scores.user_id = users.id ORDER BY hours,minutes,seconds,milliseconds limit 10;";
            ResultSet rs = stmt.executeQuery(query);
            int i = 0;
            while (rs.next() && i < 10) {
                //System.out.println("am ajuns la negrii");
                scoreBoard.scoreBoard[i].setPlace(i + 1);
                scoreBoard.scoreBoard[i].setUsername(rs.getString("username"));
                scoreBoard.scoreBoard[i].setTime(rs.getString("time"));
                i++;
            }
            return scoreBoard;
        } catch (SQLException e) {
            System.out.println("Problema la getScoreBoard");
            e.printStackTrace();
        }
        return null;
    }

    public TimeScore getBestTime(int user_id) {
        TimeScore timeScore;
        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT hours, minutes, seconds, milliseconds FROM scores JOIN \"bestScores\" ON scores.score_id = \"bestScores\".score_id WHERE \"bestScores\".user_id = " + user_id + ";";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                timeScore = new TimeScore();
                timeScore.hour = rs.getInt("hours");
                timeScore.minutes = rs.getInt("minutes");
                timeScore.seconds = rs.getInt("seconds");
                timeScore.milliseconds = rs.getInt("milliseconds");
                return timeScore;
            }


        } catch (SQLException e) {
            System.out.println("Error at trying to get user TIME");
            e.printStackTrace();
        }
        return null;
    }

    public int addRun(TimeScore timeScore, int userId) {
        try {
            Statement stmt = connection.createStatement();
            ///Nice one, internet!
            String query = "INSERT INTO scores(hours, minutes, seconds, milliseconds, user_id)  VALUES (" + timeScore.hour + "," + timeScore.minutes + "," + timeScore.seconds + "," + timeScore.milliseconds + "," + userId + ") RETURNING score_id;";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("score_id");
            }
        } catch (SQLException e) {
            System.out.println("Error at trying to insert user TIME");
            e.printStackTrace();
        }
        return 0;
    }

    public void updateBestRun(int scoreId, int userId) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE \"bestScores\" SET score_id = " + scoreId + " WHERE user_id = " + userId + ";";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error at trying to insert user TIME");
            e.printStackTrace();
        }
    }
}
















