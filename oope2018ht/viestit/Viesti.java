package oope2018ht.viestit;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Komennettava;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**
  * Kekustelualueen viestejä mallintava luokka.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
  * <p>
  * @author Miika Johansson (Johansson.Miika.A@student.uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  */
public class Viesti implements Comparable<Viesti>, Komennettava<Viesti> {

    private int tunniste;
    private String sisalto;
    private Viesti viesti;
    private Tiedosto tiedosto;
    private OmaLista viestiLista;

    public Viesti(int tunniste, String sisalto) {
        this(tunniste, sisalto, null, null);
    }

    public Viesti(int tunniste, String sisalto, Viesti viesti) {
        this(tunniste, sisalto, viesti, null);
    }

    public Viesti(int tunniste, String sisalto, Viesti viesti, Tiedosto tiedosto) {
        if (tunniste > 0 && sisalto != null && sisalto.length() > 0) {
            this.tunniste = tunniste;
            this.sisalto = sisalto;
            this.viesti = viesti;
            this.tiedosto = tiedosto;
            this.viestiLista = new OmaLista();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Getteri
    public int getTunniste() {
        return tunniste;
    }

    @Setteri
    public void setTunniste(int tunniste) {
        if (tunniste >= 0) {
            this.tunniste = tunniste;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Getteri
    public String getSisalto() {
        return sisalto;
    }

    @Setteri
    public void setSisalto(String sisalto) {
        if (sisalto.length() > 0) {
            this.sisalto = sisalto;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Getteri
    public Viesti getViesti() {
        return viesti;
    }

    @Setteri
    public void setViesti(Viesti viesti) {
        this.viesti = viesti;
    }

    @Getteri
    public Tiedosto getTiedosto() {
        return tiedosto;
    }

    @Setteri
    public void setTiedosto(Tiedosto tiedosto) {
        this.tiedosto = tiedosto;
    }

    @Getteri
    public OmaLista getViestiLista() {
        return viestiLista;
    }

    @Setteri
    public void setViestiLista(OmaLista viestiLista) {
        if (viestiLista == null) {
            throw new IllegalArgumentException();
        }
        this.viestiLista = viestiLista;
    }

    /* Palauttaa viestin oikeassa String muodossa, palautus on erilainen,
       jos viestillä ei ole tiedostoa. */
    @Override
    public String toString() {
        if (tiedosto == null) {
            return "#" + tunniste + " " + sisalto;
        }
        return "#" + tunniste + " " + sisalto + " (" + tiedosto.toString() + ")";
    }

    /* Vertaillaan viestejä tunnisteiden perusteella. Metodi palauttaa -1
       jos vertailtavan tunniste on suurempi, 0 jos tunnisteet ovat yhtäsuuret
       ja 1 jos vertailtavan tunniste on pienempi. */
    @Override
    public int compareTo(Viesti t){
        Viesti toka = (Viesti) t;
        if (tunniste < toka.tunniste) {
            return -1;
        }
        if (tunniste == toka.tunniste) {
            return 0;
        }
        return 1;
    }

    /* Vertaillaan viestejä toisiinsa. Jos tunnisteet ovat samat,
       niin viestit lasketaan samoiksi. 
       Palauttaa true, jos viestit ovat samat, muuten false. */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Viesti) {
            Viesti vertailtava = (Viesti) obj;
            return vertailtava.getTunniste() == tunniste;
        }
        return false;
    }

    @Override
    public Viesti hae(Viesti haettava) throws IllegalArgumentException {
        if (haettava == null) {
            throw new IllegalArgumentException();
        }
        Viesti loydetty = (Viesti) viestiLista.hae(haettava);
        
        return loydetty;
    }

    @Override
    public void lisaaVastaus(Viesti lisattava) throws IllegalArgumentException {
        if (lisattava == null || this.hae(lisattava) != null) {
            throw new IllegalArgumentException();
        }
        
        viestiLista.lisaa(lisattava);
    }

    @Override
    public void tyhjenna() {
        sisalto = POISTETTUTEKSTI;
        tiedosto = null;
    }

}
