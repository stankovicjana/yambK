/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DomenskiObjekat;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public abstract class GeneralDObject implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;//jer je vise puta kreirana ova klasa

    abstract public String getAtrValue();

    abstract public String getAtrValue2();

    abstract public String setAtrValue();

    abstract public String getClassName();

    abstract public String getWhereCondition();

    abstract public String getNameByColumn(int column);

    abstract public DomenskiObjekat.GeneralDObject getNewRecord(ResultSet rs) throws SQLException;

    public abstract String columnsForInsert();
}
