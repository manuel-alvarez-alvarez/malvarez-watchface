package es.malvarez.mywatchfaces.data;

/**
 * Time data
 */

public class Time {

    private final int seconds;
    private final int minutes;
    private final int hours;
    private final int ampm;

    public Time(int seconds, int minutes, int hours, int ampm) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
        this.ampm = ampm;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getAmpm() {
        return ampm;
    }
}
