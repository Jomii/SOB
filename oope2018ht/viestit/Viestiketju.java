package oope2018ht.viestit;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;

/**
  * Kekustelualueen viestiketjuja mallintava luokka.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
  * <p>
  * @author Miika Johansson (Johansson.Miika.A@student.uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  */
public class Viestiketju implements Comparable {

    /* Sisältää ketjun oksaviestit. */
    private final OmaLista oksaviestit;
    /* Sisältää ketjun kaikki viestit. */
    private final OmaLista viitteet;
    private int tunniste;
    private String aihe;
    /* Apumuuttuja jonka avulla viestien viitteet saadaan jatkumaan
       eri ketjujen välillä. */
    private int uusiViite;

    public Viestiketju(int tunniste, String aihe) {
        oksaviestit = new OmaLista();
        viitteet = new OmaLista();
        this.tunniste = tunniste;
        this.aihe = aihe;
        uusiViite = 0;
    }

    public int getUusiViite() {
        return uusiViite;
    }

    public void setUusiViite(int uusiViite) {
        this.uusiViite = uusiViite;
    }

    /**
     * Lisää uuden viestin oksaviestien listaan.
     * 
     * @param uusi oksaviesti -listaan lisättävä viesti.
     * @return true jos lisääminen onnistui, muuten false.
     */
    public boolean lisaaOksa(Object uusi) {
        if (uusi != null && uusi instanceof Comparable) {
            oksaviestit.lisaa(uusi);
            /* Viesti lisätään myös kaikki viestit säilövään listaan. */
            this.lisaaViesti(uusi);
            return true;
        }
        return false;
    }

    /**
     * Lisätään viesti kaikkien viestien listalle.
     * 
     * @param uusi ketjuun lisättävä viesti.
     * @return true jos lisääminen onnistui, muuten false.
     */
    public boolean lisaaViesti(Object uusi) {
        if (uusi != null && uusi instanceof Comparable) {
            viitteet.lisaa(uusi);
            /* Kasvatetaan viitelukua yhdellä. */
            uusiViite++;
            return true;
        }

        return false;
    }

    /**
     * Lisää vastauksen ketjussa olevaan viestiin. Metodi käy kaikki ketjun viestit
     * läpi ja etsii viestiä, jolla on parametrinä saatu haettava viite.
     * 
     * @param viite vastattavan viestin viite.
     * @param vastaus uusi vastaus -viesti.
     * @return true, jos vastaaminen onnistui, muuten false.
     */
    public boolean vastaa(int viite, Object vastaus) {
        for (int i = 0; i < viitteet.koko(); i++) {
            Viesti haettava = (Viesti) viitteet.alkio(i);
            /* Jos parametrinä annettu viite löytyy, lisätään viitteen
               sisältämään viestiin vastaus ja palautetaan true. */
            if (haettava.getTunniste() == viite) {
                haettava.setViesti((Viesti) vastaus);
                return true;
            }
        }

        return false;
    }
    
    public int haeUusiViite() {
        return getUusiViite() + 1;
    }

    @Getteri
    public OmaLista getOksaviestit() {
        return oksaviestit;
    }

    @Getteri
    public OmaLista getViitteet() {
        return viitteet;
    }

    @Getteri
    public int getTunniste() {
        return tunniste;
    }

    @Setteri
    public void setTunniste(int tunniste) {
        this.tunniste = tunniste;
    }

    @Getteri
    public String getAihe() {
        return aihe;
    }

    @Setteri
    public void setAihe(String aihe) {
        this.aihe = aihe;
    }

    @Override
    public int compareTo(Object t) {
        Viestiketju toka = (Viestiketju) t;

        if (tunniste < toka.tunniste) {
            return -1;
        }
        if (tunniste == toka.tunniste) {
            return 0;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "#" + tunniste + " " + aihe + " (" + viitteet.koko() + " messages)";
    }

}
