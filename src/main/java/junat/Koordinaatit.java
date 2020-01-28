package junat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
@JsonIgnoreProperties(ignoreUnknown = true)
class Koordinaatit {
    private String type;
    private double[] coordinates;

    @Override
    public String toString() {
        return " Koordinaatit" + '\n' +
                "  type='" + type + '\n' +
                "  coordinates=" + Arrays.toString(coordinates);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
