package junat;

import java.text.SimpleDateFormat;


import java.time.format.DateTimeFormatter;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Kayttoliittyma {

    Scanner scanner = new Scanner(System.in);
    static DateTimeFormatter date;
    static JSON_pohja_junat junadata;
    static JSON_pohja_asemat asemadata;


    public Kayttoliittyma() {

    }

    public static void kaynnista() {
        junadata.lueJunanJSONData();
        junadata.lueJunansijainti();
        asemadata.lueAsemanJSONData();
        ;


    }

    public static void kaynnistaValikko() throws Exception {
        kaynnista();
        otsikkoTeksti();

        while (true) {
            valikkoTeksti();

            Scanner scanner = new Scanner(System.in);
            String luku = scanner.nextLine();

            if (luku.equals("1")) {
                valintaAsema();
                continue;
            }else if(luku.equals("2")){

                System.out.print("Syötä lähtöaika: ");
                String syotettyaika = scanner.nextLine();
                getLahtoajanPerusteella(syotettyaika);
                continue;
            }

            else if(luku.equals("3")) {
                System.out.print("Anna haettavan junan numero: ");
                String junannumero = scanner.nextLine();
                int junannumeroInteger = 0;
                if (onkoNumero(junannumero)){
                    junannumeroInteger = Integer.valueOf(junannumero);
                    getReitti(junannumeroInteger);
                }else{
                    System.out.println("Tarkista syötetty numero!");
                }
                continue;
            }

            else if (luku.equals("4")) {
                System.out.println("Etsi junan sijainti junanumeron perusteella");
                String numero = scanner.nextLine();
                int numeroInteger = 0;
                if(onkoNumero(numero)){
                    numeroInteger = Integer.valueOf(numero);
                    junadata.haeJunanPaikka(numeroInteger);
                }else{
                    System.out.println("Tarkista syötetty numero!");
                }
                continue;

            }


            else if (luku.equals("0")) {
                System.out.println("Kiitos hei!");
                break;
            }

            else{
                System.out.println("Tarkista syöte!");
                continue;
            }

        }
        //getLahtoasemanJunat("HKI");

        //getLahtoajanPerusteella();


    }

    public static void getReitti(int junannumero) throws Exception {

        int i = 0;
        int p = 0;
        while (i < junadata.getJunat().size()) {
            if (junadata.getJunat().get(i).getTrainNumber() == junannumero) {
                // System.out.println(junat.get(i).getTimeTableRows().get(5).getScheduledTime());
                while (p < junadata.getJunat().get(i).getTimeTableRows().size()) {
                    if (junadata.getJunat().get(i).getTimeTableRows().get(p).getType().equals("ARRIVAL") &&
                            junadata.getJunat().get(i).getTimeTableRows().get(p).trainStopping == true) {
                        System.out.print(asemanNimenKonvertointiPitkaksi(junadata.getJunat().get(i).getTimeTableRows().get(p).getStationShortCode()));

                        System.out.println(" " + splittaaAika(i, p));
                    }
                    p++;
                }
            }
            i++;
        }
    }


    public static void getLahtoasemanJunat(String koodi) {
        int i = 0;
        while (i < junadata.getJunat().size()) {
            if (junadata.getJunat().get(i).getTimeTableRows().get(0).getStationShortCode().equals(koodi) &&
                    junadata.getJunat().get(i).trainCategory.equals("Long-distance")) {
                System.out.print(junadata.getJunat().get(i).getTrainNumber() + ", ");
                System.out.println("Junan tyyppi: " + junadata.getJunat().get(i).getTrainType());

            }
            i++;
        }
    }


    public static void getLahtoajanPerusteella(String syotettyaika) throws Exception {  //ei toimi
        int i = 0;
        while (i < junadata.getJunat().size()) {
            String[] pilkottuaika = junadata.getJunat().get(i).getTimeTableRows().get(0).scheduledTime.split("T");
            String[] pilkottukellonaika = pilkottuaika[1].split("\\.");
            if (junadata.getJunat().get(i).getTimeTableRows().get(0).getStationShortCode().equals("HKI") &&
                    junadata.getJunat().get(i).trainCategory.equals("Long-distance") &&
                    pilkottukellonaika[0].equals(syotettyaika)) {
                System.out.println("Tähän aikaan lähtevät junat: ");
                System.out.println(junadata.getJunat().get(i).getTrainNumber());
            }
            i++;
        }
    }

    public static String splittaaAika(int i, int p) throws Exception {
        String[] palat = junadata.getJunat().get(i).getTimeTableRows().get(p).getScheduledTime().split("T");
        String paivays = palat[0];
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(paivays);
        String[] kellonaika = palat[1].split("\\.");
        String kello = kellonaika[0];
        // System.out.println(paivays + " splitattu päivä");
        //System.out.println(kello  + " splitattu kellonaika");
        return kello;
    }

    public static String asemanNimenKonvertointi(String asemannimi) {
        String asemanKoodi = "";
        for (int i = 0; i < asemadata.getAsemat().size(); i++) {
            if(asemadata.getAsemat().get(i).getStationName().toLowerCase().equals(asemannimi)){
                asemanKoodi = asemadata.getAsemat().get(i).getStationShortCode();
            }else {
                String[] asemaSplitattu = asemadata.getAsemat().get(i).getStationName().split(" ");
                if (asemaSplitattu[0].toLowerCase().contains(asemannimi) &&
                        asemaSplitattu[1].toLowerCase().contains("asema")) {
                    asemanKoodi = asemadata.getAsemat().get(i).getStationShortCode();
                }
            }
        }
        return asemanKoodi;
    }

    public static String asemanNimenKonvertointiPitkaksi(String lyhenne){
        String pitkanimi = "";
        for(int i = 0; i < asemadata.getAsemat().size(); i++){
            if(asemadata.getAsemat().get(i).getStationShortCode().equals(lyhenne)){
                pitkanimi = asemadata.getAsemat().get(i).getStationName();
            }
        }
        return pitkanimi;
    }

    public static boolean onkoNumero(String s)
    {
        for (int i = 0; i < s.length(); i++)
            if (Character.isDigit(s.charAt(i))
                    == false)
                return false;

        return true;
    }


    public static void getAsemanHaku(String lahtoasema, String maarasema) throws Exception {
        Scanner lukija = new Scanner(System.in);
        int i = 0;
        System.out.println("Asemalta " + lahtoasema + " asemalle " + maarasema + " menevät junat:");
        while (i < junadata.getJunat().size()) {
            if (junadata.getJunat().get(i).getTimeTableRows().get(0).getStationShortCode().equals(lahtoasema)) {
                int p = 0;
                while (p < junadata.getJunat().get(i).getTimeTableRows().size()) {
                    if (junadata.getJunat().get(i).getTimeTableRows().get(p).getStationShortCode().equals(maarasema) &&
                            junadata.getJunat().get(i).getTimeTableRows().get(p).trainStopping == true &&
                            junadata.getJunat().get(i).getTimeTableRows().get(p).getType().equals("ARRIVAL")) {
                        int junanumero = junadata.getJunat().get(i).getTrainNumber();
                        //String aika = junadata.getJunat().get(i).getTimeTableRows().get(p).scheduledTime;
                        String aika = splittaaAika(i, p);
                        System.out.println(junanumero + ", " + aika);
                    }
                    p++;
                }
            }
            i++;
        }
    }

    /////////////////////VALIKON METODIT/////////////////////////


    public static void otsikkoTeksti() {
        System.out.println("**********************************************************************************************");
        System.out.println("██╗   ██╗██████╗     ██████╗ ███████╗██╗████████╗████████╗██╗ ██████╗ ██████╗  █████╗ ███████╗");
        System.out.println("██║   ██║██╔══██╗    ██╔══██╗██╔════╝██║╚══██╔══╝╚══██╔══╝██║██╔═══██╗██╔══██╗██╔══██╗██╔════╝");
        System.out.println("██║   ██║██████╔╝    ██████╔╝█████╗  ██║   ██║      ██║   ██║██║   ██║██████╔╝███████║███████╗");
        System.out.println("╚██╗ ██╔╝██╔══██╗    ██╔══██╗██╔══╝  ██║   ██║      ██║   ██║██║   ██║██╔═══╝ ██╔══██║╚════██║");
        System.out.println(" ╚████╔╝ ██║  ██║    ██║  ██║███████╗██║   ██║      ██║   ██║╚██████╔╝██║     ██║  ██║███████║");
        System.out.println("  ╚═══╝  ╚═╝  ╚═╝    ╚═╝  ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   ╚═╝ ╚═════╝ ╚═╝     ╚═╝  ╚═╝╚══════╝");
        System.out.println("**********************************************************************************************");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            System.out.println("Viive ei onnistunut.");
        }
    }

    public static void valikkoTeksti() {
        System.out.println("Valitse vaihtoehto:");
        System.out.println("1 - Hae juna lähtö- ja määräaseman perusteella");
        System.out.println("2 - Hae juna lähtöajan perusteella"); //alkaen helsingistä
        System.out.println("3 - Hae reitin asemat"); //junan numeron perusteella
        System.out.println("4 - Hae junan sijainti numeron perusteella");
        System.out.println("0 - Lopeta");
        System.out.println("Anna valinta: ");
    }


    public static void valintaAsema() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Syötä junan lähtöasema");
        String lahtoasema = scanner.nextLine().toLowerCase();

        System.out.println("Syötä junan pääteasema");
        String paateasema = scanner.nextLine().toLowerCase();

        getAsemanHaku(asemanNimenKonvertointi(lahtoasema), asemanNimenKonvertointi(paateasema));
    }

}

