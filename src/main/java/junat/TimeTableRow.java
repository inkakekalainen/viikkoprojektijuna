package junat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class TimeTableRow {
    String stationShortCode;
    int stationUICCode;
    String countryCode;
    String type;
    boolean trainStopping;
    String commercialTrack;
    boolean cancelled;
    String scheduledTime;



    public String getStationShortCode() {
        return stationShortCode;
    }

    public int getStationUICCode() {
        return stationUICCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getType() {
        return type;
    }

    public boolean isTrainStopping() {
        return trainStopping;
    }

    public String getCommercialTrack() {
        return commercialTrack;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setStationShortCode(String stationShortCode) {
        this.stationShortCode = stationShortCode;
    }

    public void setStationUICCode(int stationUICCode) {
        this.stationUICCode = stationUICCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTrainStopping(boolean trainStopping) {
        this.trainStopping = trainStopping;
    }

    public void setCommercialTrack(String commercialTrack) {
        this.commercialTrack = commercialTrack;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public String toString() {
        return "TimeTableRow{" +
                "stationShortCode='" + stationShortCode + '\'' +
                ", stationUICCode=" + stationUICCode +
                ", countryCode='" + countryCode + '\'' +
                ", type='" + type + '\'' +
                ", trainStopping=" + trainStopping +
                ", commercialTrack=" + commercialTrack +
                ", cancelled=" + cancelled +
                ", scheduledTime=" + scheduledTime +
                '}' + "\n";
    }
}
