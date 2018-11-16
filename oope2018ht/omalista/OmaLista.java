package oope2018ht.omalista;

import fi.uta.csjola.oope.lista.LinkitettyLista;
import oope2018ht.apulaiset.Ooperoiva;

/**
  * Harjoitustyössä käytettävä OmaLista luokka. Peritty valmiina annetusta Linkitetystä
  * listasta.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
  * <p>
  * @author Miika Johansson (Johansson.Miika.A@student.uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  */

public class OmaLista extends LinkitettyLista implements Ooperoiva {

    public OmaLista() {
        super();
    }

    /* Etsii listalta parametrina annetun kohteen jos parametri on kelvollinen
       ja jos listalla on kohteita. Palauttaa null jos kohdetta ei löydy tai 
       parametri on kelvoton. Muuten palauttaa haetun kohteen listalta. */
    @Override
    public Object hae(Object haettava) {
        if (onkoTyhja() || haettava == null) {
            return null;
        }
        
        for (int i = 0; i < koko(); i++) {
            if (alkio(i).equals(haettava)) {
                return alkio(i);
            }
        }
        return null;
    }

    /* Lisää listaan kohteita suuruusjärjestyksessä
       Palauttaa tosi jos lisääminen onnistui, muuten false.
       Saa parametrina kohteen, jonka oletetaan toteuttavan Comparable -rajapinnan.
    */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean lisaa(Object uusi) {
        /* Tarkistaa onko parametri null, ja toteuttaako se Comparable rajapinnan.
           Palautetaan heti false jos parametri ei ole kelvollinen. */
        if (uusi == null || !(uusi instanceof Comparable)) {
            return false;
        }

        // Jos lista on tyhjä lisätään kohde listan alkuun.
        if (onkoTyhja()) {
            lisaaAlkuun(uusi);
            return true;
        }

        /* Käy listan läpi ja lisää uuden lisättävän kohteen oikeaan paikkaan
           listassa. */
        Comparable lisattava = (Comparable) uusi;
        boolean onSuurempi = false;
        for (int i = 0; i < koko(); i++) {
            Comparable haettu = (Comparable) alkio(i);

            if (haettu.compareTo(lisattava) > 0) {
                onSuurempi = true;
            }
            
            if (onSuurempi) {
                super.lisaa(i, uusi);
                return true;
            } 
        }
        
        if (!onSuurempi) {
            lisaaLoppuun(uusi);
            return true;
        }

        return false;
    }

    /* Palauttaa uuden listan joka sisältää parametrinä annetun määrän
       alkioita listan alusta. Palauttaa null jos lista on tyhjä, ei sisällä
       parametrin verran alkioita tai jos parametri on pienempi kuin yksi. */
    @Override
    public OmaLista annaAlku(int n) {
        if (onkoTyhja() || n < 1 || n > koko()) {
            return null;
        }
        
        OmaLista uusi = new OmaLista();
        
        for (int i = 0; i < n; i++) {
            uusi.lisaa(alkio(i));
        }

        return uusi;
    }

    /* Palauttaa uuden listan joka sisältää parametrinä annetun määrän alkioita
       listan lopusta. Palauttaa null jos lista on tyhjä, ei sisällä parametrin
       verran alkioita tai jos parametri on pienempi kuin yksi. */
    @Override
    public OmaLista annaLoppu(int n) {
        if (onkoTyhja() || n < 1 || n > koko()) {
            return null;
        }
        
        OmaLista uusi = new OmaLista();
        
        for (int i = koko() - n; i < koko(); i++) {
            uusi.lisaa(alkio(i));
        }
        
        return uusi;
    }

}
