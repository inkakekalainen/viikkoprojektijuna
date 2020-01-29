package junat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JSON_pohja_asemat {

    public static List<Asemat> asemat;

    public static void main(String[] args) {
        lueAsemanJSONData();
    }

    public static void lueAsemanJSONData() { //edellisestä devistä lisätty metodi vastaanottamaan int junannumero (ei välttämätön)
        String baseurl = "https://rata.digitraffic.fi/api/v1";

        try {
            URL url = new URL(URI.create(String.format("%s/metadata/stations", baseurl)).toASCIIString());
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Asemat.class);
            asemat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi


        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static List<Asemat> getAsemat() {
        return asemat;
    }
}
