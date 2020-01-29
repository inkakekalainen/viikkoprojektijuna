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

        String [] splitattutimestamp = timestamp.split("T");
        String [] splitattuaika = splitattutimestamp[1].split("\\.");
        String aika = splitattuaika[0];
        String [] tunnit = aika.split(":");
        int korjatuttunnit = Integer.valueOf(tunnit[0]);

        if(korjatuttunnit == 22){
            korjatuttunnit = 00;
        }else if(korjatuttunnit == 23){
            korjatuttunnit = 01;
        }else{
            korjatuttunnit = korjatuttunnit + 2;
        }

        this.timestamp = Integer.valueOf(korjatuttunnit).toString() + ":" + tunnit[1];
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
