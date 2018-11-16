package oope2018ht.kayttoliittyma;

import java.io.File;
import java.util.Scanner;
import oope2018ht.apulaiset.In;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Kuva;
import oope2018ht.tiedostot.Tiedosto;
import oope2018ht.tiedostot.Video;
import oope2018ht.viestit.*;

/**
 * Harjoitustyön käyttöliittymä. Sisältää ohjelman loopin.
 * <p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2018.
 * <p>
 * @author Miika Johansson (Johansson.Miika.A@student.uta.fi), Luonnontieteiden tiedekunta,
 * Tampereen yliopisto.
 */
public class Kayttoliittyma {

    /*Komennot vakioituna*/
    private final String EXIT = "exit";
    private final String ADD = "add";
    private final String CATALOG = "catalog";
    private final String SELECT = "select";
    private final String NEW = "new";
    private final String REPLY = "reply";
    private final String TREE = "tree";
    private final String LIST = "list";
    private final String HEAD = "head";
    private final String TAIL = "tail";
    private final String EMPTY = "empty";
    private final String FIND = "find";

    private final Keskustelualue kAlue;

    /**
     * Luodaan käyttöliittymälle keskustelualue.
     */
    public Kayttoliittyma() {
        kAlue = new Keskustelualue();
    }

    /**
     * Käynnistää käyttöliittymän loopin suorittamisen.
     */
    public void kaynnista() {
        System.out.print("Welcome to S.O.B.\n");
        boolean kaynnissa = true;
        while (kaynnissa) {
            boolean onnistuiko = true;

            System.out.print(">");
            /* Luetaan komento käyttäjältä */
            String komento = In.readString();
            /* Jaetaan komento osiin välilyöntien kohdalta, jotta voidaan erottaa
               komennon tunnussana. */
            String[] osat = komento.split(" ");

            /* Etsitään annettua komentoa vakioista */
            switch (osat[0]) {
                case EXIT:
                    kaynnissa = false;
                    break;
                case ADD:
                    lisaaKetju(osat);
                    break;
                case CATALOG:
                    tulostaKatalogi();
                    break;
                case SELECT:
                    try {
                        if (osat.length > 2) {
                            throw new IllegalArgumentException();
                        }
                        kAlue.valitseKetju(Integer.parseInt(osat[1]));
                    } catch (Exception e) {
                        System.out.println("Error!");
                    }
                    break;
                case NEW:
                    onnistuiko = lisaaOksa(osat);
                    break;
                case REPLY:
                    onnistuiko = vastaa(osat);
                    break;
                case TREE:
                    onnistuiko = tulostaPuuna();
                    break;
                case LIST:
                    onnistuiko = tulostaListana();
                    break;
                case HEAD:
                    onnistuiko = tulostaPaa(osat);
                    break;
                case TAIL:
                    onnistuiko = tulostaHanta(osat);
                    break;
                case EMPTY:
                    onnistuiko = tyhjenna(osat);
                    break;
                case FIND:
                    onnistuiko = etsi(osat);
                    break;
            }

            if (!onnistuiko) {
                System.out.println("Error!");
            }
        }

        System.out.println("Bye! See you soon.");
    }

    /* KOMENTOJEN METODIT */
    /**
     * Etsii aktiivisesta viestiketjusta parametrinä saatua merkkijonoa.
     *
     * @param osat komento muutettuna taulukoksi.
     * @return true jos haku onnistui, muuten false.
     */
    private boolean etsi(String[] osat) {
        Viestiketju aktiivinen = kAlue.getAktiivinenKetju();
        if (aktiivinen == null) {
            return false;
        }
        /* Muutetaan parametrinä saadusta taulukosta osat takaisin
           String muotoon, jotta hakusana voidaan kokonaisuudessaan
           etsiä viestilistasta. */
        StringBuilder hakusana = new StringBuilder();
        for (int i = 1; i < osat.length; i++) {
            hakusana.append(osat[i]);
            if (i < osat.length - 1) {
                hakusana.append(" ");
            }
        }
        OmaLista hakuL = aktiivinen.getViitteet();
        for (int i = 0; i < hakuL.koko(); i++) {
            Viesti apuv = (Viesti) hakuL.alkio(i);
            if (apuv.getSisalto().contains(hakusana.toString())) {
                System.out.println(apuv);
            }
        }

        return true;
    }

    /**
     * Tyhjentää parametrinä saadun viestin sisällön.
     *
     * @param osat komento muutettuna taulukoksi.
     * @return true jos tyhjentäminen onnistui, muuten false.
     */
    private boolean tyhjenna(String[] osat) {
        Viestiketju aktiivinen = kAlue.getAktiivinenKetju();
        if (aktiivinen == null) {
            return false;
        }

        /* Luodaan uusi viesti jonka avulla haetaan samaa viitettä omaavaa viestiä. */
        Viesti haettava = new Viesti(Integer.parseInt(osat[1]), "---");
        Viesti tyhj = (Viesti) aktiivinen.getViitteet().hae(haettava);

        if (tyhj == null) {
            return false;
        }

        tyhj.tyhjenna();

        return true;
    }

    /**
     * Tulostaa aktiivisen viestiketjun listamuodossa.
     *
     * @return true jos on olemassa aktiivinen ketju, muuten false.
     */
    private boolean tulostaListana() {
        Viestiketju akt = kAlue.getAktiivinenKetju();
        if (akt == null) {
            return false;
        }
        System.out.print("=\n== " + akt + "\n===\n");
        OmaLista vt = akt.getViitteet();
        for (int i = 0; i < vt.koko(); i++) {
            System.out.println(vt.alkio(i));
        }

        return true;
    }

    /**
     * Lisää keskustelualueelle uuden viestiketjun.
     *
     * @param osat komento muutettuna taulukoksi.
     * @return true jos ketjun lisääminen onnistui, muuten false.
     */
    private boolean lisaaKetju(String[] osat) {
        if (osat.length < 2 || osat[1].length() < 1 || osat[1].equals(" ")) {
            return false;
        }
        StringBuilder ketjunAihe = new StringBuilder();
        for (int i = 1; i < osat.length; i++) {
            ketjunAihe.append(osat[i]);
            if (i < osat.length - 1) {
                ketjunAihe.append(" ");
            }
        }
        kAlue.lisaaKetju(new Viestiketju(kAlue.haeUusiTunniste(), ketjunAihe.toString()));

        return true;
    }

    /**
     * Lisää ketjuun uuden oksaviestin.
     *
     * @param osat komento muutettuna taulukoksi.
     * @return true jos viestin lisääminen onnistui, muuten false.
     */
    private boolean lisaaOksa(String[] osat) {
        /* Tarkastaa onko parametri kelvollinen. Mikäli se ei ole tulostetaan virheviesti
           ja poistutaan metodista palauttamalla false. */
        if (osat.length < 2 || osat[1].length() < 1 || osat[1].equals(" ")) {
            return false;
        }
        /* Tarkistetaan onko aktiivista ketjua olemassa viestin lisäämistä varten. */
        Viestiketju aktiivinen = kAlue.getAktiivinenKetju();
        if (aktiivinen == null) {
            return false;
        }
        Tiedosto viestiTiedosto = null;
        /* Tarkistaa onko viestin mukaan liitetty tiedosto. */
        if (osat[osat.length - 1].charAt(0) == '&') {
            viestiTiedosto = luoTiedosto(osat);
        }

        /* Tekee osista string tyyppisen viestin sisällön. */
        StringBuilder sisalto = new StringBuilder();
        for (int i = 1; i < osat.length; i++) {
            sisalto.append(osat[i]);
            if (i < osat.length - 1) {
                sisalto.append(" ");
            }
        }

        /* Jos aktiivisessa ketjuissa ei vielä ole viestejä,
           annetaan viitteelle ensimmäisen viestin viite. */
        if (aktiivinen.getUusiViite() != 0) {
            aktiivinen.lisaaOksa(new Viesti(aktiivinen.haeUusiViite(), sisalto.toString(), null, viestiTiedosto));
        } else {
            aktiivinen.lisaaOksa(new Viesti(1, sisalto.toString(), null, viestiTiedosto));
        }
        return true;
    }

    /**
     * Vastaa olemassaolevaan viestiin, parametri sisältää viitteen viestiin, johon vastataan.
     *
     * @param osat komento muutettuna taulukoksi.
     * @return true jos haku onnistui, muuten false.
     */
    private boolean vastaa(String[] osat) {
        try {
            Integer.parseInt(osat[1]);
        } catch (Exception e) {
            return false;
        }
        if (osat.length < 3 || osat[1].length() < 1 || osat[2].equals(" ")) {
            return false;
        }
        // Rakentaa viestin sisällön
        StringBuilder vastaus = new StringBuilder();
        for (int i = 2; i < osat.length; i++) {
            vastaus.append(osat[i]);
            if (i < osat.length - 1) {
                vastaus.append(" ");
            }
        }

        Tiedosto viestiTiedosto = null;
        /* Tarkistaa onko viestin mukaan liitetty tiedosto. */
        if (osat[osat.length - 1].charAt(0) == '&') {
            viestiTiedosto = luoTiedosto(osat);
        }

        Viestiketju aktv = kAlue.getAktiivinenKetju();
        // Hakee vastattavan viestin käyttämällä komennon parametrinä olevaa tunnusta.
        Viesti vastattava = (Viesti) aktv.getViitteet().hae(new Viesti(Integer.parseInt(osat[1]), "derp"));
        // Josa vastattavaa viestiä ei ole tulostetaan virheviesti.
        if (vastattava == null) {
            return false;
        }

        // Lisää ensin viestin ketjuun ja sitten asettaa viestin vastaukseksi,
        // jotta vastauksen tunnus saadaan muutettua isommaksi.
        if (aktv.getViitteet().koko() != 0) {
            int apu = aktv.haeUusiViite();
            Viesti lisattava = new Viesti(apu, vastaus.toString(), vastattava, viestiTiedosto);
            aktv.lisaaViesti(lisattava);
            vastattava.lisaaVastaus(lisattava);
        } else {
            vastattava.setViesti(new Viesti(100, vastaus.toString()));
        }
        return true;
    }

    /**
     * Luo tiedoston komennon parametristä.
     *
     * @param osat komento muutettuna taulukoksi.
     * @return Tiedosto-olio tai null, jos tiedostoa ei voitu avata.
     */
    private Tiedosto luoTiedosto(String[] osat) {
        Tiedosto viestiTiedosto = null;
        /* Otetaan osan loppuosa '&' merkin jälkeen tiedoston nimeksi. */
        String tiedostonNimi = osat[osat.length - 1].substring(1);
        String tiedostoStr = null;

        /* Yritetään lukea tiedoston ensimmäisen rivin. */
        try {
            File tiedosto = new File(tiedostonNimi);

            Scanner lukija = new Scanner(tiedosto);

            while (lukija.hasNextLine()) {
                tiedostoStr = lukija.nextLine();
            }

            lukija.close();

        } catch (Exception e) {
            System.out.println("Failed to load file.");
        }

        String[] tiedostoOsat = tiedostoStr.split(" ");
        if (tiedostoOsat[0].equals("Kuva")) {
            Kuva uusi = new Kuva(tiedostonNimi, Integer.parseInt(tiedostoOsat[1]),
                    Integer.parseInt(tiedostoOsat[2]), Integer.parseInt(tiedostoOsat[3]));
            viestiTiedosto = uusi;
        }
        if (tiedostoOsat[0].equals("Video")) {
            Video uusi = new Video(tiedostonNimi, Integer.parseInt(tiedostoOsat[1]),
                    Double.parseDouble(tiedostoOsat[2]));
            viestiTiedosto = uusi;
        }

        return viestiTiedosto;
    }

    /**
     * Tulostaa n määrän viestejä ketjun alusta, n määritellään komennon parametrinä.
     *
     * @param osat komento muutettuna taulukoksi.
     * @return true jos tulostaminen onnistui, muuten false.
     */
    private boolean tulostaPaa(String[] osat) {
        int maara;
        /* Tarkistetaan onko komennon parametri int -luku. */
        try {
            maara = Integer.parseInt(osat[1]);
        } catch (Exception e) {
            return false;
        }
        Viestiketju aktiivinen = kAlue.getAktiivinenKetju();
        if (aktiivinen == null || maara < 1 || maara > aktiivinen.getViitteet().koko()) {
            return false;
        }

        OmaLista alk = aktiivinen.getViitteet().annaAlku(Integer.parseInt(osat[1]));
        for (int i = 0; i < alk.koko(); i++) {
            System.out.println(alk.alkio(i));
        }
        return true;
    }

    /**
     * Tulostaa n määrän viestejä ketjun lopusta, n määritellään komennon parametrinä.
     *
     * @param osat komento muutettuna taulukoksi.
     * @return true jos tulostaminen onnistui, muuten false.
     */
    private boolean tulostaHanta(String[] osat) {
        int maara;
        try {
            maara = Integer.parseInt(osat[1]);
        } catch (Exception e) {
            return false;
        }
        Viestiketju aktiivinen = kAlue.getAktiivinenKetju();
        if (aktiivinen == null || maara < 1 || maara > aktiivinen.getViitteet().koko()) {
            return false;
        }

        OmaLista lop = aktiivinen.getViitteet().annaLoppu(Integer.parseInt(osat[1]));
        for (int i = 0; i < lop.koko(); i++) {
            System.out.println(lop.alkio(i));
        }

        return true;
    }

    /**
     * Tulostaa keskustelualueen ketjut.
     */
    private void tulostaKatalogi() {
        OmaLista ketjut = kAlue.getViestiketjut();
        for (int i = 0; i < ketjut.koko(); i++) {
            Viestiketju tulostettava = (Viestiketju) ketjut.alkio(i);
            System.out.println(tulostettava.toString());
        }
    }

    /**
     * Tulostaa ketjun viestit rekursiivisesti puumuodossa.
     *
     * @return true jos tulostaminen onnistui, muuten false.
     */
    private boolean tulostaPuuna() {
        Viestiketju aktiivinen = kAlue.getAktiivinenKetju();
        if (aktiivinen == null) {
            return false;
        }
        System.out.print("=\n== " + aktiivinen + "\n===\n");

        OmaLista ketju = aktiivinen.getOksaviestit();
        for (int i = 0; i < ketju.koko(); i++) {
            tulostaPuuna((Viesti) ketju.alkio(i), 0);
        }

        return true;
    }

    private void tulostaPuuna(Viesti viesti, int syvyys) {
        for (int i = 0; i < syvyys; i++) {
            System.out.print("   ");
        }
        System.out.println(viesti);

        OmaLista vastaukset = viesti.getViestiLista();
        for (int j = 0; j < vastaukset.koko(); j++) {
            tulostaPuuna((Viesti) vastaukset.alkio(j), syvyys + 1);
        }
    }
}
