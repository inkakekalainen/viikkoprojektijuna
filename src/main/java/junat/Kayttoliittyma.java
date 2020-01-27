package junat;

import java.sql.SQLOutput;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Kayttoliittyma {
    Scanner scanner = new Scanner(System.in);
    public Kayttoliittyma(){

    }

    public static void kaynnista(){
        JSON_pohja_junat.lueJunanJSONData();
        //System.out.println(JSON_pohja_junat.getJunat().size());
    }

    public static void kaynnistaValikko(){
        kaynnista();
        System.out.println("Valitse vaihtoehto:");
        System.out.println("1 - Hae juna lähtö- ja määräaseman perusteella");
        System.out.println("2 - Hae juna lähtöajan perusteella"); //alkaen helsingistä
        System.out.println("3 - Hae junan reitti"); //junan numeron perusteella
    }

}


/*
    int i = 0;
    int p = 0;
            while(i < junat.size()) {
        if (junat.get(i).getTrainNumber() == 37) {
        // System.out.println(junat.get(i).getTimeTableRows().get(5).getScheduledTime());
        while (p < junat.get(i).getTimeTableRows().size()){
        if(junat.get(i).getTimeTableRows().get(p).getStationShortCode().equals("OLK")){
        System.out.println("JEP JUNA TULEE JA LÄHTEE OGELISTA");
        break;
        }
        p++;
        }
        }
        i++;
        }*/
