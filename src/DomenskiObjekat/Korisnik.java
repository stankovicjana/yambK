/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomenskiObjekat;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author user
 */
public class Korisnik extends GeneralDObject implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;//jer je vise puta kreirana ova klasa

    public Long idKorisnik;
    String korisnickoIme;
    String sifra;
    String ime;
    String prezime;
    public int brojPoena;
    private java.util.Date datum;  // DB: DATE
    private int statusIndex;

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getStatusIndex() {
        return statusIndex;
    }

    public void setStatusIndex(int statusIndex) {
        this.statusIndex = statusIndex;
    }

    public Korisnik() {
        this.idKorisnik = 0L;
        this.korisnickoIme = "";
        this.sifra = "";
        this.ime = "";
        this.prezime = "";
        this.brojPoena = 0;
//    
    }

    public Korisnik(Long idKorisnik, String korisnickoIme, String sifra, String ime, String prezime, int brojPoena) {
        this.idKorisnik = idKorisnik;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
        this.ime = ime;
        this.prezime = prezime;
        this.brojPoena = brojPoena;

    }

    // alternativni primarni kljuc     
    public Korisnik(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    // primarni kljuc
    public Korisnik(Long idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public Long vratiIDKorisnik() {
        return this.idKorisnik;
    }

    public String getKorisnickoIme() {
        return this.korisnickoIme;
    }

    public String getSifra() {
        return this.sifra;
    }

    public String getIme() {
        return this.ime;
    }

    public String getPrezime() {
        return this.prezime;
    }

    public void setIDKorisnika(Long idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @Override
    public String getAtrValue() {
        return idKorisnik + ", '" + korisnickoIme + "', '" + sifra + "', '" + ime + "', '" + prezime + "', " + brojPoena;
    }

    @Override
    public String getAtrValue2() {
        return "'" + korisnickoIme + "', '" + sifra + "', '" + ime + "', '" + prezime + "', " + brojPoena;
    }

    @Override
    public String setAtrValue() {
        return "idKorisnik=" + idKorisnik + ", " + "korisnickoIme='" + korisnickoIme + "', " + "sifra='" + sifra + "', ime='" + ime + "', prezime='" + prezime + "', " + "brojPoena=" + brojPoena;
    }

    @Override
    public String getClassName() {
        return "Korisnik";
    }

    @Override
    public String getWhereCondition() {
        return "idKorisnik=" + idKorisnik;
    }

    @Override
    public String getNameByColumn(int column) {
        String names[] = {"idKorisnik", "korisnickoIme", "sifra", "ime", "prezime", "brojPoena"};
        return names[column];
    }

    @Override
    public DomenskiObjekat.GeneralDObject getNewRecord(ResultSet rs) throws SQLException {
        return new DomenskiObjekat.Korisnik(rs.getLong("idKorisnik"), rs.getString("korisnickoIme"), rs.getString("sifra"), rs.getString("ime"), rs.getString("prezime"), rs.getInt("brojPoena"));
    }

    @Override
    public String columnsForInsert() {

        return " (korisnickoIme, sifra, ime, prezime, brojPoena) ";
    }

    @Override
    public String toString() {
        return "Korisnik{" + "idKorisnik=" + idKorisnik + ", korisnickoIme=" + korisnickoIme + ", sifra=" + sifra + ", ime=" + ime + ", prezime=" + prezime + ", brojPoena=" + brojPoena + '}';
    }

    public Long vratiIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Long idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public int getBrojPoena() {
        return brojPoena;
    }

    public void setBrojPoena(int brojPoena) {
        this.brojPoena = brojPoena;
    }

}
