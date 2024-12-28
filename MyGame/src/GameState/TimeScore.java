package GameState;

public class TimeScore {
    public int hour, minutes, seconds, milliseconds;

    public TimeScore() {
    }

    public TimeScore(double timeInSec) {
        hour = (int) Math.floor(timeInSec / 3600);
        minutes = (int) Math.floor((timeInSec / 60) % 60);
        seconds = (int) Math.floor(timeInSec % 60);
        milliseconds = (int) Math.floor((timeInSec * 100) % 100);
    }
}
