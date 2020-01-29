package junat;

import org.junit.Test;
import static org.junit.Assert.*;

public class KayttoliittymaTest {

   private Kayttoliittyma kayttoliittyma = new Kayttoliittyma();

    @Test
    public void tuleeKertoaOnkoSyoteNumeroita(){

        assertEquals("Tulisi palauttaa true jos syöte on numeroita", true, kayttoliittyma.onkoNumero("699"));
        assertEquals("Tulisi palauttaa false jos syöte ei ole numeroita", false, kayttoliittyma.onkoNumero("Pekka"));

    }

    @Test
    public void tulisiPalauttaaOikeanAsemanLahtevatJunat(){
        assertEquals("Tulisi palauttaa true jos syöte on numeroita", "23, Junan tyyppi: IC", kayttoliittyma.getLahtoasemanJunat("Rovaniemi"));
    }

}