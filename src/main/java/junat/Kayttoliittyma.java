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
        //getLahtoasemanJunat();

        while (true) {
            valikkoTeksti();

            Scanner scanner = new Scanner(System.in);
            String luku = scanner.nextLine();

            if (luku.equals("1")) {
                valintaAsema();
                continue;
            } else if (luku.equals("2")) {

                System.out.print("Syötä lähtöaika: ");
                String syotettyaika = scanner.nextLine();

                String[] tunnitjaminuutit = syotettyaika.split(":");
                int tunnit = Integer.valueOf(tunnitjaminuutit[0]);
                if (tunnit == 02) {
                    tunnit = 00;
                } else if (tunnit == 01) {
                    tunnit = 23;
                } else if (tunnit == 00) {
                    tunnit = 22;
                } else {
                    tunnit = tunnit - 2;
                }
                String korjatuttunnit = Integer.valueOf(tunnit).toString() + ":" + tunnitjaminuutit[1] + ":00";

                getLahtoajanPerusteella(korjatuttunnit);


                continue;
            } else if (luku.equals("3")) {
                System.out.print("Anna haettavan junan numero: ");
                String junannumero = scanner.nextLine();
                int junannumeroInteger = 0;
                if (onkoNumero(junannumero)) {
                    junannumeroInteger = Integer.valueOf(junannumero);
                    getReitti(junannumeroInteger);
                } else {
                    System.out.println("Tarkista syötetty numero!");
                }
                continue;
            } else if (luku.equals("4")) {
                System.out.println("Etsi junan sijainti junanumeron perusteella");
                String numero = scanner.nextLine();
                int numeroInteger = 0;
                if (onkoNumero(numero)) {
                    numeroInteger = Integer.valueOf(numero);
                    junadata.haeJunanPaikka(numeroInteger);
                } else {
                    System.out.println("Tarkista syötetty numero!");
                }
                continue;

            } else if (luku.equals("5")) {
                System.out.println(nopeinJuna());
                continue;
            } else if (luku.equals("6")) {
                teejuna();
                continue;
            } else if (luku.equals("0")) {
                System.out.println("Kiitos hei!");
                break;
            } else {
                System.out.println("Tarkista syöte!");
                continue;
            }

        }


    }

    public static void getReitti(int junannumero) throws Exception {

        int i = 0;
        int p = 0;

        while (i < junadata.getJunat().size()) {
            if (junadata.getJunat().get(i).getTrainNumber() == junannumero) {

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


    public static String getLahtoasemanJunat(String asema) {
        int i = 0;
        String viesti = "";
        while (i < junadata.getJunat().size()) {
            if (junadata.getJunat().get(i).getTimeTableRows().get(0).getStationShortCode().equals(asema)) {
                viesti += (junadata.getJunat().get(i).getTrainNumber() + ", ");
                viesti += "Junan tyyppi: " + junadata.getJunat().get(i).getTrainType();

            }
            i++;
        }
        return viesti;
    }

    public static String nopeinJuna() {
        JSON_pohja_junat.lueJunansijainti();
        int nopeus = 0;
        int junannumero = 0;
        for (int i = 0; i < junadata.getSijainti().size(); i++) {
            if (junadata.getSijainti().get(i).getSpeed() > nopeus) {
                nopeus = junadata.getSijainti().get(i).getSpeed();
                junannumero = junadata.getSijainti().get(i).getTrainNumber();
            }
        }
        return "Tämän hetken nopein juna on " + junannumero + " , nopeudella " + nopeus + " km/h.";
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
        String[] tunnitjaminuutit = kellonaika[0].split(":");
        int tunnit = Integer.valueOf(tunnitjaminuutit[0]);
        if (tunnit == 22) {
            tunnit = 00;
        } else if (tunnit == 23) {
            tunnit = 01;
        } else {
            tunnit = tunnit + 2;
        }
        String korjatuttunnit = Integer.valueOf(tunnit).toString();

        return korjatuttunnit + ":" + tunnitjaminuutit[1];
    }

    public static String asemanNimenKonvertointi(String asemannimi) {
        String asemanKoodi = "";
        for (int i = 0; i < asemadata.getAsemat().size(); i++) {
            if (asemadata.getAsemat().get(i).getStationName().toLowerCase().equals(asemannimi)) {
                asemanKoodi = asemadata.getAsemat().get(i).getStationShortCode();
            } else {
                String[] asemaSplitattu = asemadata.getAsemat().get(i).getStationName().split(" ");
                if (asemaSplitattu[0].toLowerCase().equals(asemannimi) &&
                        asemaSplitattu[1].toLowerCase().contains("asema") !=
                                asemaSplitattu[1].toLowerCase().contains("-")) {
                    asemanKoodi = asemadata.getAsemat().get(i).getStationShortCode();
                }
            }
        }
        return asemanKoodi;
    }

    public static String asemanNimenKonvertointiPitkaksi(String lyhenne) {
        String pitkanimi = "";
        for (int i = 0; i < asemadata.getAsemat().size(); i++) {
            if (asemadata.getAsemat().get(i).getStationShortCode().equals(lyhenne)) {
                pitkanimi = asemadata.getAsemat().get(i).getStationName();
            }
        }
        return pitkanimi;
    }

    public static boolean onkoNumero(String s) {
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
            int r = 0;
            while (r < junadata.getJunat().get(i).getTimeTableRows().size()) {

                if (junadata.getJunat().get(i).getTimeTableRows().get(r).getStationShortCode().equals(lahtoasema) &&
                        junadata.getJunat().get(i).getTimeTableRows().get(r).trainStopping == true &&
                        junadata.getJunat().get(i).getTimeTableRows().get(r).getType().equals("DEPARTURE")) {
                    int p = 0;
                    while (p < junadata.getJunat().get(i).getTimeTableRows().size()) {
                        if (junadata.getJunat().get(i).getTimeTableRows().get(p).getStationShortCode().equals(maarasema) &&
                                junadata.getJunat().get(i).getTimeTableRows().get(p).trainStopping == true &&
                                junadata.getJunat().get(i).getTimeTableRows().get(p).getType().equals("ARRIVAL")) {
                            int junanumero = junadata.getJunat().get(i).getTrainNumber();


                            String aika = splittaaAika(i, r);
                            System.out.println(junanumero + ", " + aika);
                        }
                        p++;
                    }

                }
                r++;
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
        System.out.println(" ");
        System.out.println("Valitse vaihtoehto:");
        System.out.println("1 - Hae juna lähtö- ja määräaseman perusteella");
        System.out.println("2 - Hae juna lähtöajan perusteella"); //alkaen helsingistä
        System.out.println("3 - Hae reitin asemat"); //junan numeron perusteella
        System.out.println("4 - Hae junan sijainti numeron perusteella");
        System.out.println("5 - Näytä tämän hetken nopeimman junan numero ja nopeus");
        System.out.println("6 - Tulosta juna");
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

    public static void teejuna() {
        String filepath = "toot-toot.wav";
        Musiikki musicObject = new Musiikki();
        musicObject.playMusic(filepath);
        System.out.println("                   _-====-__-======-__-========-_____-============-__");
        System.out.println("               _(                                                 _)");
        System.out.println("             OO(           _/_ _  _  _/_   _/_ _  _  _/_           )_");
        System.out.println("            0  (_          (__(_)(_) (__   (__(_)(_) (__            _)");
        System.out.println("        o         '=-___-===-_____-========-___________-===-===-='");
        System.out.println("    .o                                _________");
        System.out.println("   . ______          ______________  |         |      _____");
        System.out.println(" _()_||__|| ________ |            |  |_________|   __||___||__");
        System.out.println("(SIL2020  | |      | |            | __Y______00_| |_         _|");
        System.out.println("/-OO----OO'='OO--OO'='OO--------OO'='OO-------OO'='OO-------OO¨'=");
        System.out.println("#####################################################################");
    }


}

