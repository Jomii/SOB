package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
  * Kuva-luokka, jolla mallinnetaan viesteissä käytettäviä kuvatiedostoja.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
  * <p>
  * @author Miika Johansson (Johansson.Miika.A@student.uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  */

public class Kuva extends Tiedosto {

    private int leveys;
    private int korkeus;

    /** {@inheritDoc}
     * 
     * Annetaan kuvalle tiedot rakentajassa, jos ne ovat kelvollisia.
     * 
     * @param nimi {@inheritDoc}
     * @param koko {@inheritDoc}
     * @param leveys kuvan leveys.
     * @param korkeus kuvan korkeus.
     */
    public Kuva(String nimi, int koko, int leveys, int korkeus) {
        super(nimi, koko);
        if (leveys == 0 || korkeus == 0) {
            throw new IllegalArgumentException();
        }

        this.leveys = leveys;
        this.korkeus = korkeus;
    }

    @Getteri
    public int getLeveys() {
        return leveys;
    }

    @Setteri
    public void setLeveys(int leveys) {
        if (leveys < 1) {
            throw new IllegalArgumentException();
        }
        this.leveys = leveys;
    }

    @Getteri
    public int getKorkeus() {
        return korkeus;
    }

    @Setteri
    public void setKorkeus(int korkeus) {
        if (korkeus < 1) {
            throw new IllegalArgumentException();
        }
        this.korkeus = korkeus;
    }

    @Override
    public String toString() {
        return super.toString() + " " + leveys + "x" + korkeus;
    }
    
}
