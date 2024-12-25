package geoGame;

public class ScoreBoardRow {
    private String username;
    private String time;
    private int place;

    public ScoreBoardRow(String username, String time, int place) {
        this.username = username;
        this.time = time;
        this.place = place;
    }

    public ScoreBoardRow() {
        this.username = "";
        this.time = "";
        this.place = 0;
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

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
