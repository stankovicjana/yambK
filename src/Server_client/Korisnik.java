package Server_client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "korisnik",
        propOrder = {
            "idKorisnik",
            "brojPoena",
            "ime",
            "korisnickoIme",
            "prezime",
            "sifra",
            "statusIndex"
        }
)
public class Korisnik extends GeneralDObject {

    protected Long idKorisnik;
    protected int brojPoena;
    protected String ime;
    protected String korisnickoIme;
    protected String prezime;
    protected String sifra;

    @XmlElement(nillable = true)
    protected Integer statusIndex;

    public Korisnik() {
        // JAXB zahteva no-arg konstruktor
    }

    public Long getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Long value) {
        this.idKorisnik = value;
    }

    public int getBrojPoena() {
        return brojPoena;
    }

    public void setBrojPoena(int value) {
        this.brojPoena = value;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String value) {
        this.ime = value;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String value) {
        this.korisnickoIme = value;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String value) {
        this.prezime = value;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String value) {
        this.sifra = value;
    }

    public Integer getStatusIndex() {
        return statusIndex;
    }

    public void setStatusIndex(Integer value) {
        this.statusIndex = value;
    }
}
