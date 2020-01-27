package junat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JSON_pohja_junat {
    public static void main(String[] args) {
        lueJunanJSONData(265);
    }


    public static void lueJunanJSONData(int junannumero) { //edellisestä devistä lisätty metodi vastaanottamaan int junannumero (ei välttämätön)
        String baseurl = "https://rata.digitraffic.fi/api/v1";

        try {
            URL url = new URL(URI.create(String.format("%s/live-trains/station/HKI/ROI", baseurl)).toASCIIString());
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            List<Juna> junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            int i = 0;
            while(i < junat.size()) {                                           //hakujen testailua proto
                if (junat.get(i).getTrainNumber() == junannumero) {
                    System.out.println(junat.get(i).getTimeTableRows().get(0).getScheduledTime());
                }
                i++;
            }

            System.out.println(junat.get(0).getTimeTableRows().get(0).getScheduledTime());
            System.out.println("\n\n");
            System.out.println(junat.get(0));


        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
