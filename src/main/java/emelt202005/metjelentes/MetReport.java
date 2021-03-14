package emelt202005.metjelentes;

import java.time.LocalTime;

public class MetReport {
    private final String city;
    private final LocalTime time;
    private final String wind;
    private final int temperature;

    public MetReport(String city, LocalTime time, String wind, int temperature) {
        this.city = city;
        this.time = time;
        this.wind = wind;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getWind() {
        return wind;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getWindForce(){
       return Integer.parseInt(wind.substring(3));
    }
}
