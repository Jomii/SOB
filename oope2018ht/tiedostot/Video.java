package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
  * Video-luokka, jolla mallinnetaan viesteissä käytettäviä videotiedostoja.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
  * <p>
  * @author Miika Johansson (Johansson.Miika.A@student.uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  */

public class Video extends Tiedosto {

    private double pituus;

    /** {@inheritDoc}
     * 
     * Annetaan videolle pituus ja kutsutaan yliluokan rakentajaa nimelle ja koolle.
     * Heittää IllegalArgumentExceptionin jos parametrina annettu pituus on kelvoton.
     * 
     * @param nimi {@inheritDoc}
     * @param koko {@inheritDoc}
     * @param pituus videon pituus.
     */
    public Video(String nimi, int koko, double pituus) {
        super(nimi, koko);
        if (pituus < 1) {
            throw new IllegalArgumentException();
        }
        this.pituus = pituus;
    }

    @Getteri
    public double getPituus() {
        return pituus;
    }

    @Setteri
    public void setPituus(double pituus) {
        if (pituus < 0) {
            throw new IllegalArgumentException();
        }
        this.pituus = pituus;
    }

    @Override
    public String toString() {
        return super.toString() + " " + pituus + " s";
    }

}
