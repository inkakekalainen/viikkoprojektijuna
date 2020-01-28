package junat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Lokaatio {

    private int trainNumber;
    private String departureDate;
    private String timestamp;
    private List<Koordinaatit> location;
    private int speed;

    public int getTrainNumber() {
        return trainNumber;
    }

    @Override
    public String toString() {
        return "Lokaatio:" + '\n'+
                "trainNumber=" + trainNumber + '\n' +
                "departureDate=" + departureDate + '\n' +
                "timestamp=" + timestamp + '\n' +
                "location=" + location + '\n' +
                "speed=" + speed + " km/h" + '\n';
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<Koordinaatit> getLocation() {
        return location;
    }

    public void setLocation(List<Koordinaatit> location) {
        this.location = location;
    }



    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int spdeed) {
        this.speed = spdeed;
    }
}
