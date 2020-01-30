package junat;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Kayttoliittyma {

    static JSON_pohja_junat junadata;  //nimetään JSON_pohja_junat-luokka nimellä junadata käytön helpottamiseksi
    static JSON_pohja_asemat asemadata; //         "         asemat-luokka nimellä asemadata       "


    public Kayttoliittyma() {
    }

    public static void kaynnista() {
        junadata.lueJunanJSONData();  //hakee dataa ohjelman käynnistyessä ja luo Juna-oliot
        junadata.lueJunansijainti();    //   "    sijaintidataa ja luo sijainti-oliot
        asemadata.lueAsemanJSONData();  //   "    asemadataa ja luo asema-oliot
    }

    public static void kaynnistaValikko() throws Exception {
        kaynnista();                // hakee uusimman datan ylläoleviin luokkiin
        otsikkoTeksti();            // tulostaa otsikkotekstit

        while (true) {
            valikkoTeksti();        // valikkotekstin tulostus

            Scanner scanner = new Scanner(System.in); //skannerin luonti
            String luku = scanner.nextLine();           // lukee käyttäjän valitseman luvun

            if (luku.equals("1")) {
                valintaAsema();         //Jos valinta 1: käynnistä asemanvalinta-metodin, joka pyytää käyttäjältä lähtö- ja määränpääasemat, jotka syötetään getAsemanHaku-metodille
                continue;               // eli siis näyttää kahden aseman välillä kulkevat junat
            } else if (luku.equals("2")) {
                System.out.print("Syötä lähtöasema: ");
                String asema = scanner.nextLine();
                System.out.print("Syötä lähtöaika: ");         //pyytää käyttäjältä lähtöaseman ja kellonajan, jonka perusteella tulostaa kyseiseen aikaan asemalta lähtevät junat
                String syotettyaika = scanner.nextLine();      //toiminnallisuus getLahtoajanPerusteella-metodissa
                asema = asema.toLowerCase();
                getLahtoajanPerusteella(vahennaTunteja(syotettyaika), asemanNimenKonvertointi(asema)); //vahennaTunteja -metodi vähentää käyttäjän syöttämästä
                continue;                                                                              //kellonajasta 2h, jotta se täsmää VR:n datan kanssa

            } else if (luku.equals("3")) { //toiminta: näyttää junan pysäkit ja kellonajat pysäkeillä
                System.out.print("Anna haettavan junan numero: ");
                String junannumero = scanner.nextLine(); //pyytää junan numeron (String muodossa, helpottaa syötteen validointia myöhemmin)
                int junannumeroInteger = 0;             // esitellään int, johon asetetaan myöhemmin junan numero int muodossa
                if (onkoNumero(junannumero)) {           // metodi tarkistaa onko syötetty String numeroita
                    junannumeroInteger = Integer.valueOf(junannumero);  //jos on, niin edellä mainittuun int:iin syötetään junan numero
                    getReitti(junannumeroInteger);      // ja haetaan junan reitti getReitti-metodilla
                } else {
                    System.out.println("Tarkista syötetty numero!");    //jos syöte ei ole numero, tulostetaan virheilmoitus
                }
                continue;
            } else if (luku.equals("4")) {
                System.out.println("Etsi junan sijainti junanumeron perusteella");
                String numero = scanner.nextLine(); //sama tarkastustoiminnallisuus kuin edellä
                int numeroInteger = 0;
                if (onkoNumero(numero)) {
                    numeroInteger = Integer.valueOf(numero);
                    junadata.haeJunanPaikka(numeroInteger); //haetaan junan sijainti junan numeron perusteella haeJunanPaikka-metodilla
                } else {
                    System.out.println("Tarkista syötetty numero!"); //jos syöte ei numeroita niin virheilmoitus
                }
                continue;

            } else if (luku.equals("5")) {
                System.out.println(nopeinJuna()); // valinnalla 5 tulostetaan sen hetken nopein juna koko junadatasta (suomessa)
                continue;
            } else if (luku.equals("6")) {
                teejuna();                      // valinnalla 6 interaktiivisuutta
                continue;
            } else if (luku.equals("0")) {
                System.out.println("Kiitos hei!"); //lopetusteksti
                break;
            } else {
                System.out.println("Tarkista syöte!"); //jos valikossa ei syötetä valideja numeroita, niin tulostetaan virheilmoitus
                continue;
            }

        }


    }


    public static void getReitti(int junannumero) throws Exception {
        String ekaAsema = "";                                   //esitellään String muuttuja myöhempää käyttöä varten
        List<String>tulostettavatAsemat = new ArrayList<>();    //luodaan läpikäytäville asemille oma lista
        for(int i = 0; i < junadata.getJunat().size(); i++) {   //aloitetaan läpikäynti juna-olioista, verrataan juna-olio listan kokoa
            if (junadata.getJunat().get(i).getTrainNumber() == junannumero) { //jos junan numero täsmää syötettyyn numeroon:
                ekaAsema = asemanNimenKonvertointiPitkaksi(junadata.getJunat().get(i).getTimeTableRows().get(0).getStationShortCode()) + " " + splittaaAika(i, 0); //haetaan ekaAsema muuttujaan ensimmäisen aseman nimi (getTimeTableRows, sijainnissa 0)

                for(int p = 0; p < junadata.getJunat().get(i).getTimeTableRows().size(); p++) { //aletaan käymään läpi muita junan asemia
                    if (junadata.getJunat().get(i).getTimeTableRows().get(p).getType().equals("ARRIVAL") && //filtteröidään vain ne asemat, joihin juna saapuu
                            junadata.getJunat().get(i).getTimeTableRows().get(p).trainStopping) { //ja pysähtyy
                        tulostettavatAsemat.add(asemanNimenKonvertointiPitkaksi(junadata.getJunat().get(i).getTimeTableRows().get(p).getStationShortCode()) + " " + splittaaAika(i, p)); //otetaan edellämainittujen asemien tiedot ja kellonajat ylös listaan
                    }
                }
            }
        }
        System.out.println(ekaAsema); //tulostus käyttäjälle, ekana lähtöasema
        for(int r = 0; r < tulostettavatAsemat.size(); r++){
            System.out.println(tulostettavatAsemat.get(r)); // ja perään pysähdysasemat
        }
    }


/*    public static String getLahtoasemanJunat(String asema) {  //Varalla tulevaisuuden läpimurtoa varten
        int i = 0;                                              // Listaa valitun lähtöaseman junat + junatyypin
        String viesti = "";
        while (i < junadata.getJunat().size()) {
            if (junadata.getJunat().get(i).getTimeTableRows().get(0).getStationShortCode().equals(asema)) {
                viesti += (junadata.getJunat().get(i).getTrainNumber() + ", ");
                viesti += "Junan tyyppi: " + junadata.getJunat().get(i).getTrainType();
            }
            i++;
        }
        return viesti;
    }*/

    public static String nopeinJuna() {
        JSON_pohja_junat.lueJunansijainti(); //haetaan viimeisin tieto joka kerta metodia kutsuttaessa, muutoin tulisi vain nopeimman junan tiedot koko ohjelman käynnistyessä
        int nopeus = 0;
        int junannumero = 0;
        for (int i = 0; i < junadata.getSijainti().size(); i++) { //käydään läpi junien sijaintilistaa
            if (junadata.getSijainti().get(i).getSpeed() > nopeus) { //jos läpikäydyn junan nopeus on suurempi kuin esitellyn integerin (tällä hetkellä 0),
                nopeus = junadata.getSijainti().get(i).getSpeed(); // lisätään arvo muuttujaan
                junannumero = junadata.getSijainti().get(i).getTrainNumber(); //otetaan myös nopeimman junan numero ylös
            }
        }
        return "Nopeimmin liikkuva juna Suomessa tällä hetkellä: " + junannumero + ", nopeus " + nopeus + " km/h."; //tulostus käyttäjälle
    }


    public static void getLahtoajanPerusteella(String syotettyaika, String asema) throws Exception {
        for (int i = 0; i < junadata.getJunat().size(); i++) {                                                            //junadata-listan läpikäynti
            String[] pilkottuaika = junadata.getJunat().get(i).getTimeTableRows().get(0).scheduledTime.split("T");      //tallennetaan ja pilkotaan raakadatan aikatieto kohdasta T (on muotoa [päivämäärä]T[kellonaika].00
            String[] pilkottukellonaika = pilkottuaika[1].split("\\.");                            //pilkotaan vielä pilkottu kellonaika kohdasta ., joten meillä on data muotoa hh:mm:ss
            for (int p = 0; p < junadata.getJunat().get(i).getTimeTableRows().size(); p++){                //käydään junien aikatauludataa läpi
                if (junadata.getJunat().get(i).getTimeTableRows().get(p).getStationShortCode().equals(asema) &&         //jos match käyttäjän syöttämän aseman kanssa
                        junadata.getJunat().get(i).getTimeTableRows().get(p).getType().equals("DEPARTURE") &&       //ja ehto on, että juna lähtee
                        pilkottukellonaika[0].equals(syotettyaika)) {                    //ja match käyttäjän syöttämän kellonajan kanssa
                    System.out.println("Tähän aikaan lähtevät junat: ");
                    System.out.println(junadata.getJunat().get(i).getTrainNumber());        //tulostetaan datat
                }
            }
        }
    }

    public static String splittaaAika(int i, int p) throws Exception {
        String[] palat = junadata.getJunat().get(i).getTimeTableRows().get(p).getScheduledTime().split("T");
        String[] kellonaika = palat[1].split("\\.");
        String[] tunnitjaminuutit = kellonaika[0].split(":");
        int tunnit = Integer.valueOf(tunnitjaminuutit[0]); //metodi ajan pilkkomiseen. sama toiminnallisuus kuin yllä, eli erotellaan pvm kellonajasta
        if (tunnit == 22) { //ja koska vr:n kellonaikadata heittää 2 tunnilla, niin muutetaan aikaa 2 tunnilla oikeaan suuntaan
            tunnit = 00;
        } else if (tunnit == 23) {
            tunnit = 01;
        } else {
            tunnit = tunnit + 2;
        }
        String korjatuttunnit = Integer.valueOf(tunnit).toString(); //korjattujen tuntien palautus
        return korjatuttunnit + ":" + tunnitjaminuutit[1];
    }

    public static String asemanNimenKonvertointi(String asemannimi) { //muutetaan käyttäjän syöttämä kaupungin nimi vastaamaan
        String asemanKoodi = "";                                    // VR:n asemakoodeja (esim Helsinki = HKI)
        for (int i = 0; i < asemadata.getAsemat().size(); i++) {    // ei ole väliä syöttääkö käyttäjä kaupungin nimen isoilla vai pienillä kirjaimilla
            if (asemadata.getAsemat().get(i).getStationName().toLowerCase().equals(asemannimi)) {
                asemanKoodi = asemadata.getAsemat().get(i).getStationShortCode(); //jos syötetty nimi matchaa aseman "selkonimen" kanssa niin otetaan ylös aseman koodi
            } else {
                String[] asemaSplitattu = asemadata.getAsemat().get(i).getStationName().split(" ");
                if (asemaSplitattu[0].toLowerCase().equals(asemannimi) &&       //on myös tapauksia, joissa aseman nimen perässä lukee asema
                        asemaSplitattu[1].toLowerCase().contains("asema") !=    // ei oteta huomioon näitä "erikoisasemia"
                                asemaSplitattu[1].toLowerCase().contains("-")) { //tai esimerkiksi autojuna-asema (viiva)
                    asemanKoodi = asemadata.getAsemat().get(i).getStationShortCode();
                }
            }
        }
        return asemanKoodi;
    }

    public static String asemanNimenKonvertointiPitkaksi(String lyhenne) { //käytännössä sama kuin asemanNimenKonvertointi
        String pitkanimi = "";                                              //Mutta muutetaan koodi normi kaupungin nimeksi
        for (int i = 0; i < asemadata.getAsemat().size(); i++) {
            if (asemadata.getAsemat().get(i).getStationShortCode().equals(lyhenne)) {
                pitkanimi = asemadata.getAsemat().get(i).getStationName();
            }
        }
        return pitkanimi;
    }

    public static boolean onkoNumero(String s) {
        for (int i = 0; i < s.length(); i++)
            if (Character.isDigit(s.charAt(i)) //käytetään valikossa tarkistamaan onko string syötteessä numeroita (tsekkaa onko joka kohdassa rimpsua numero)
                    == false)
                return false;
        return true;
    }


    public static void getAsemanHaku(String lahtoasema, String maarasema) throws Exception { //Hakee kahden aseman välillä kulkevat junat ja niiden lähtöajat
        Scanner lukija = new Scanner(System.in);
        System.out.println("Asemalta " + lahtoasema + " asemalle " + maarasema + " menevät junat:");
        for (int i = 0; i < junadata.getJunat().size(); i++) {
            for (int r = 0; r < junadata.getJunat().get(i).getTimeTableRows().size(); r++) { //käydään läpi junalista ja junien timetablerowsit
                if (junadata.getJunat().get(i).getTimeTableRows().get(r).getStationShortCode().equals(lahtoasema) && //jos match nimen kanssa
                        junadata.getJunat().get(i).getTimeTableRows().get(r).trainStopping == true &&               //ja juna todella pysähtyy
                        junadata.getJunat().get(i).getTimeTableRows().get(r).getType().equals("DEPARTURE")) {       // tarkastellaan lähtöä
                             for(int p = 0; p < junadata.getJunat().get(i).getTimeTableRows().size(); p++) {
                                 if (junadata.getJunat().get(i).getTimeTableRows().get(p).getStationShortCode().equals(maarasema) && //käytännössä sama kuin yllä mutta etsitään saapumisasemaa
                                         junadata.getJunat().get(i).getTimeTableRows().get(p).trainStopping == true &&
                                         junadata.getJunat().get(i).getTimeTableRows().get(p).getType().equals("ARRIVAL")) {       //saapumisaseman ehtona saapuminen ARRIVAL
                                             int junanumero = junadata.getJunat().get(i).getTrainNumber();
                                             String aika = splittaaAika(i, r);
                                             System.out.println(junanumero + ", " + aika); //tulostetaan tiedot
                                         }

                              }

                        }

            }

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
            TimeUnit.SECONDS.sleep(2);
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
        System.out.println("5 - Näytä nopeimmin liikkuva juna Suomessa tällä hetkellä");
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

    public static void soitaMusiikkia(){
        String filepath = "toot-toot.wav";
        Musiikki musicObject = new Musiikki();
        musicObject.playMusic(filepath);
    }

    public static void teejuna() {
        soitaMusiikkia();
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

    public static String vahennaTunteja(String syotettyaika){
        String[] tunnitjaminuutit = syotettyaika.split(":");
        int tunnit = Integer.valueOf(tunnitjaminuutit[0]);
        if (tunnit == 02) {
            tunnit = 00;
        } else if (tunnit == 01) { //sama kuin splittaa aika-metodin tuntien lisäys, mutta tässä casessa vähennys
            tunnit = 23;
        } else if (tunnit == 00) {
            tunnit = 22;
        } else {
            tunnit = tunnit - 2;
        }
        return Integer.valueOf(tunnit).toString() + ":" + tunnitjaminuutit[1] + ":00";
    }


}

