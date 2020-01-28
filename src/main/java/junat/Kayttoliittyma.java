package junat;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class Kayttoliittyma {
    public static Scanner scanner = new Scanner(System.in);

    public Kayttoliittyma() {

    }

    public static void kaynnista() {
        JSON_pohja_junat.lueJunanJSONData();
        JSON_pohja_junat.lueJunansijainti();
        //System.out.println(JSON_pohja_junat.getJunat().size());
    }

    public static void kaynnistaValikko() {
        kaynnista();
        System.out.println("Valitse vaihtoehto:");
        System.out.println("1 - Hae juna lähtö- ja määräaseman perusteella");
        System.out.println("2 - Hae juna lähtöajan perusteella"); //alkaen helsingistä
        System.out.println("3 - Hae junan reitti"); //junan numeron perusteella
        System.out.println("4 - Hae kulussa olevat junat");
        System.out.println("Syötä numero");
        int luku = Integer.valueOf(scanner.nextLine());
        if (luku == 1) {

        } else if (luku == 2) {

        } else if (luku == 3) {
            System.out.println("Syötä junan numero");
            int numero = Integer.valueOf(scanner.nextLine());
            getReitti(numero);
        } else if (luku == 4) {
            getJunatLiikkeessa();
        }
    }

    public static void getReitti(int junannumero) {
        int i = 0;
        int p = 0;
        while (i < JSON_pohja_junat.getJunat().size()) {
            if (JSON_pohja_junat.getJunat().get(i).getTrainNumber() == junannumero) {
                // System.out.println(junat.get(i).getTimeTableRows().get(5).getScheduledTime());
                while (p < JSON_pohja_junat.getJunat().get(i).getTimeTableRows().size()) {
                    if (JSON_pohja_junat.getJunat().get(i).getTimeTableRows().get(p).getType().equals("ARRIVAL")) {
                        System.out.print(JSON_pohja_junat.getJunat().get(i).getTimeTableRows().get(p).getStationShortCode());
                        System.out.println(" " + JSON_pohja_junat.getJunat().get(i).getTimeTableRows().get(p).getScheduledTime());
                    }
                    p++;
                }
            }
            i++;
        }
    }

    public static void getJunatLiikkeessa() {
        int j = 0;
        int k = 0;
        while (j < JSON_pohja_junat.getJunat().size()) {
            while (k < JSON_pohja_junat.getJunat().get(j).getTimeTableRows().size()) {
                if (JSON_pohja_junat.getJunat().get(j).getTimeTableRows().get(k).getStationShortCode().equals("PSL")) {
                    System.out.print(JSON_pohja_junat.getJunat().get(j).getTimeTableRows().get(k).getStationShortCode());
                    System.out.println(" " + JSON_pohja_junat.getJunat().get(j).getTimeTableRows().get(k).getScheduledTime());
                }
                k++;
            }
        }
        j++;
    }


    }






