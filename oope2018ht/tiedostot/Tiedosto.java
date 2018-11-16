package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
  * Kuva ja video luokkien abstrakti yliluokka.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
  * <p>
  * @author Miika Johansson (Johansson.Miika.A@student.uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  */

public abstract class Tiedosto {
    private String nimi;
    private int koko;
    
    /**
     * Asetetaan tiedostolle nimi ja koko mikäli parametrit ovat kelvolliset.
     * @param nimi
     * @param koko 
     */
    public Tiedosto(String nimi, int koko) {
        if (nimi == null || koko < 1) {
            throw new IllegalArgumentException();
        }
        this.nimi = nimi;
        this.koko = koko;
    }

    @Getteri
    public String getNimi() {
        return nimi;
    }
    
    @Setteri
    public void setNimi(String nimi) {
        if (nimi == null || nimi.equals("")) {
            throw new IllegalArgumentException();
        }
        this.nimi = nimi;
    }

    @Getteri
    public int getKoko() {
        return koko;
    }

    @Setteri
    public void setKoko(int koko) {
        if (koko < 0) {
            throw new IllegalArgumentException();
        }
        this.koko = koko;
    }

    @Override
    public String toString() {
        return nimi + " " + koko + " B";
    }

}
