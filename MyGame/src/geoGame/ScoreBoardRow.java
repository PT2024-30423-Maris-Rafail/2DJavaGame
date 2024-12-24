package geoGame;

public class ScoreBoardRow {
    private String username;
    private String time;
    public ScoreBoardRow(String username, String time) {
        this.username = username;
        this.time = time;
    }
    public ScoreBoardRow() {
        this.username = "";
        this.time = "";
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
