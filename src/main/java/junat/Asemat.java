package junat;

public class Asemat {
    private boolean passengerTraffic;
    private String type;
    private String stationName;
    private String stationShortCode;
    private int stationUICCode;
    private String countryCode;
    private double longitude;
    private double latitude;

    public String getStationName() {
        return stationName;
    }

    public String getStationShortCode() {
        return stationShortCode;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setStationShortCode(String stationShortCode) {
        this.stationShortCode = stationShortCode;
    }

    public boolean isPassengerTraffic() {
        return passengerTraffic;
    }

    public String getType() {
        return type;
    }

    public int getStationUICCode() {
        return stationUICCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setPassengerTraffic(boolean passengerTraffic) {
        this.passengerTraffic = passengerTraffic;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStationUICCode(int stationUICCode) {
        this.stationUICCode = stationUICCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
