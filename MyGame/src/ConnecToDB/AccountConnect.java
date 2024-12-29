package ConnecToDB;

import GameState.TimeScore;
import RegisterForm.PasswordUtils;
import RegisterForm.User;
import geoGame.ScoreBoard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountConnect {
    ConnectR connectR;
    public AccountConnect(ConnectR connectR) {
        this.connectR = connectR;
    }
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
            Statement stmt = connectR.connection.createStatement();

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
            Statement statement = connectR.connection.createStatement();
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
            Statement statement = connectR.connection.createStatement();
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

    public void registerUser(String username, String password, String email) {
        try {
            Statement stmt = connectR.connection.createStatement();

            String query = "INSERT INTO users (username, password, email) VALUES ('" + username + "','" + PasswordUtils.hashPassword(password) + "','" + email + "');";

            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ScoreBoard getScoreBoard() {
        ScoreBoard scoreBoard = new ScoreBoard();
        try {
            Statement stmt = connectR.connection.createStatement();
            String query = "SELECT username, CONCAT(hours,'h ',minutes,'m ',seconds,'s ',milliseconds,'ms') AS \"time\" FROM scores JOIN users ON scores.user_id = users.id WHERE username not like 'admin' ORDER BY hours,minutes,seconds,milliseconds limit 10;";
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
    private int getBestTimeId(int userId){
        try {
            Statement stmt = connectR.connection.createStatement();
            String query = "SELECT bestrunid FROM users WHERE id = " + userId + ";";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("bestrunid");
            }


        } catch (SQLException e) {
            System.out.println("Error at trying to get user TIME");
            e.printStackTrace();
        }
        return 0;
    }
    public TimeScore getBestTime(int userId) {
        TimeScore timeScore;
        int bestTimeId = getBestTimeId(userId);
        if(bestTimeId == 0){
            return null;
        }
        try {
            Statement stmt = connectR.connection.createStatement();
            String query = "SELECT hours, minutes, seconds, milliseconds FROM scores JOIN users ON scores.user_id = users.id WHERE bestrunid = score_id AND users.id = " + userId + ";";
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
            Statement stmt = connectR.connection.createStatement();
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
            Statement statement = connectR.connection.createStatement();
            String query = "UPDATE users SET bestrunid = " + scoreId + " WHERE id = " + userId + ";";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error at trying to insert user TIME");
            e.printStackTrace();
        }
    }
    public void cleanDB(){
        try {
            Statement statement = connectR.connection.createStatement();
            String query = "DELETE FROM scores WHERE score_id NOT IN (\n" +
                    "    SELECT users.bestrunid from users\n" +
                    "    )\n" +
                    "AND score_id NOT IN (\n" +
                    "    SELECT score_id FROM scores WHERE user_id != (SELECT id FROM users WHERE username = 'admin') ORDER BY hours,minutes,seconds,milliseconds limit 10\n" +
                    "        );";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error at trying to clean the database");
            e.printStackTrace();
        }
    }
}
