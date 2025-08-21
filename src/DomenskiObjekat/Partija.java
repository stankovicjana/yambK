/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomenskiObjekat;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author stank
 */
public class Partija extends GeneralDObject implements Serializable {

    int idPartija;
    int rezultat;

    public int getIdPartija() {
        return idPartija;
    }

    public void setIdPartija(int idPartija) {
        this.idPartija = idPartija;
    }

    public int getRezultat() {
        return rezultat;
    }

    public void setRezultat(int rezultat) {
        this.rezultat = rezultat;
    }

    public Partija() {
    }

    public Partija(int rezultat) {
        this.rezultat = rezultat;
    }

    public Partija(int idPartija, int rezultat) {
        this.idPartija = idPartija;
        this.rezultat = rezultat;
    }

    @Override
    public String getAtrValue() {
        return idPartija + "," + rezultat;
    }

    @Override
    public String getAtrValue2() {
        return "";
    }

    @Override
    public String setAtrValue() {
        return "";
    }

    @Override
    public String getClassName() {
        return "partija";
    }

    @Override
    public String getWhereCondition() {
        return "";
    }

    @Override
    public String getNameByColumn(int column) {
        switch (column) {
            case 1:
                return "rezultat";
            default:
                return "n/a";
        }
    }

    @Override
    public GeneralDObject getNewRecord(ResultSet rs) throws SQLException {
        return new Partija(
                rs.getInt("idPartija"),
                rs.getInt("rezultat")
        );
    }

    @Override
    public String columnsForInsert() {
        return "(rezultat)";
    }
}
