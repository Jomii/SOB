package oope2018ht.viestit;

import oope2018ht.omalista.OmaLista;

/**
  * Keskustelualuetta mallintava luokka.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
  * <p>
  * @author Miika Johansson (Johansson.Miika.A@student.uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  */

public class Keskustelualue {

    private OmaLista viestiketjut;
    private Viestiketju aktiivinenKetju;

    public Keskustelualue() {
        viestiketjut = new OmaLista();
        aktiivinenKetju = null;
    }

    public OmaLista getViestiketjut() {
        return viestiketjut;
    }
    
    /**
     * Lisätään viestiketju keskustelualueeseen.
     * @param uusi lisättävä viestiketju.
     * @return tosi jos lisääminen onnistui, muuten false.
     */
    public boolean lisaaKetju(Viestiketju uusi) {
        if (uusi != null && uusi instanceof Comparable) {
            viestiketjut.lisaa(uusi);
            
            /* Ensimmäinen lisätty viestiketju asetetaan aktiiviseksi. */
            if (viestiketjut.koko() == 1) {
                aktiivinenKetju = uusi;
            }
            return true;
        }
        return false;
    }
    
    /**
     * Haetaan tunniste seuraavalle viestiketjulle.
     * 
     * @return seuraava vapaa tunniste.
     */
    public int haeUusiTunniste() {
        /* Jos alueella ei vielä ole ketjuja annetaan ensimmäiselle
           ketjulle tunniste 1. */
        if (viestiketjut.onkoTyhja()) {
            return 1;
        }
        
        return viestiketjut.koko() + 1;
    }
    
    /**
     * Haetaan ketju keskustelualueelta.
     * 
     * @param tunniste haettavan ketjun tunniste.
     * @return löydetty viestiketju tai null, jos ei löytynyt
     */
    public Viestiketju haeKetju(int tunniste) {
        /* Parametrin virheentarkistukset. */
        if (tunniste < 1 || viestiketjut.onkoTyhja()) {
            return null;
        }
        
        /* Käydään viestiketjut läpi ja verrataan niiden tunnisteita
           parametrinä saatuun tunnisteeseen. */
        Viestiketju loydetty;
        for (int i = 0; i < viestiketjut.koko(); i++) {
            /* Asetetaan listan alkio viestiketjuksi. */
            loydetty = (Viestiketju) viestiketjut.alkio(i);
            /* Verrataan viestiketjun tunnistetta parametrinä saatuun, jos
               tunniste on sama, niin palautetaan löydetty viestiketju. */
            if (loydetty.getTunniste() == tunniste) {
                return loydetty;
            }
        }
        
        return null;
    }

    public Viestiketju getAktiivinenKetju() {
        return aktiivinenKetju;
    }

    // Asettaa parametrinä saadun tunnisteen omaavan viestiketjun aktiiviseksi.
    /**
     * Valitsee aktiivisen ketjun. Metodissa tallennetaan myös viestien viitteet,
     * jotta viestit saavat oikeat viitteet vaikka ketju vaihtuisi.
     * 
     * @param ketju valittavan ketjun tunnus. 
     */
    public void valitseKetju(int ketju) {
        /* Parametrin virheentarkistukset. */
        if (ketju < 1 || viestiketjut.koko() < ketju) {
            throw new IllegalArgumentException();
        } else {
            /* Siirretään seuraava viite uuteen ketjuun talteen. */
            int apu = aktiivinenKetju.haeUusiViite() - 1;
            aktiivinenKetju = (Viestiketju) viestiketjut.alkio(ketju - 1);
            aktiivinenKetju.setUusiViite(apu);
        }
    }

}
