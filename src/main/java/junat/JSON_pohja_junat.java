package junat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JSON_pohja_junat {

    public static List<Juna> junat;
    public static List<Lokaatio> sijainti;

    public static void main(String[] args) {
        lueJunanJSONData();
        lueJunansijainti();
    }


    public static void lueJunanJSONData() { //edellisestä devistä lisätty metodi vastaanottamaan int junannumero (ei välttämätön)
        String baseurl = "https://rata.digitraffic.fi/api/v1";

        try {
            URL url = new URL(URI.create(String.format("%s/live-trains/", baseurl)).toASCIIString());
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);
            junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi


           /* System.out.println(junat.get(1).getTimeTableRows().get(1).getScheduledTime());
            System.out.println("\n\n");
            System.out.println(junat.get(1));*/


        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void lueJunansijainti() { //edellisestä devistä lisätty metodi vastaanottamaan int junannumero (ei välttämätön)
        String baseurl = "https://rata.digitraffic.fi/api/v1";

        try {
            URL url2 = new URL(URI.create(String.format("%s/train-locations/latest", baseurl)).toASCIIString());
            ObjectMapper mapper = new ObjectMapper();
            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Lokaatio.class);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            sijainti = mapper.readValue(url2, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

            /*System.out.println(sijainti.get(0).getLocation().get(0));
            //System.out.println("\n\n");
            System.out.println(sijainti.get(0));*/


        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public static void haeJunanPaikka(int numero) {
            int j = 0;
        while (j < sijainti.size()) {
            if (numero== sijainti.get(j).getTrainNumber()) {
                System.out.println("löytyi");
                System.out.println(sijainti.get(j));
            }
            j++;
        }
    }

    public static List<Juna> getJunat() {
        return junat;
    }

    public static List<Lokaatio> getSijainti() {
        return sijainti;
    }
}
