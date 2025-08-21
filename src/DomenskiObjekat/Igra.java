/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomenskiObjekat;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author stank
 */
public class Igra extends GeneralDObject implements Serializable {

    private static final long serialVersionUID = 6529685098267757691L;//jer je vise puta kreirana ova klasa

    private Long id;
    private Long domacinId;
    private Long gostId;
    private StatusIgre status;
    private String createdAt;
    private String updatedAt;
    private String domacinIme;
    private String gostIme;
   
    
    public String getDomacinIme() {
        return domacinIme;
    }

    public void setDomacinIme(String domacinIme) {
        this.domacinIme = domacinIme;
    }

    public String getGostIme() {
        return gostIme;
    }

    public void setGostIme(String gostIme) {
        this.gostIme = gostIme;
    }

    @Override
    public String getAtrValue() {
        return domacinId + "," + gostId + ",'" + status.toString() + "','" + createdAt + "','" + updatedAt + "'";
    }

    @Override
    public String getAtrValue2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String setAtrValue() {
        StringBuilder sb = new StringBuilder();
        sb.append("gost_id=").append(gostId == null ? "NULL" : gostId);
        if (status != null) {
            sb.append(", status='").append(status.name()).append("'");
        }
        // uvek osveži updated_at
        sb.append(", updated_at=NOW()");
        return sb.toString();
    }

    @Override
    public String getClassName() {
        return "igra";
    }

    @Override
    public String getWhereCondition() {
        return "id=" + id;
    }

    @Override
    public String getNameByColumn(int column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GeneralDObject getNewRecord(ResultSet rs) throws SQLException {
        Igra i = new Igra();
        i.setId(rs.getLong("id"));
        i.setDomacinId(rs.getLong("domacin_id"));
        i.setGostId(rs.getLong("gost_id"));
        i.setStatus(StatusIgre.valueOf(rs.getString("status")));
        i.setCreatedAt(rs.getString("created_at"));
        i.setUpdatedAt(rs.getString("updated_at"));

        i.setDomacinIme(rs.getString("domacin_ime"));
        i.setGostIme(rs.getString("gost_ime"));
        return i;
    }

    @Override
    public String columnsForInsert() {
        return "(domacin_id,gost_id,status,created_at,updated_at)";
    }

    public enum StatusIgre {
        CREATED,
        STARTED,
        FINISHED,
        CANCELLED
    }

    public Igra() {
    }

    public Igra(Long id, Long domacinId, Long gostId,
            Integer poeniDomacin, Integer poeniGost,
            StatusIgre status, String createdAt, String updatedAt) {
        this.id = id;
        this.domacinId = domacinId;
        this.gostId = gostId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDomacinId() {
        return domacinId;
    }

    public void setDomacinId(Long domacinId) {
        this.domacinId = domacinId;
    }

    public Long getGostId() {
        return gostId;
    }

    public void setGostId(Long gostId) {
        this.gostId = gostId;
    }

    public StatusIgre getStatus() {
        return status;
    }

    public void setStatus(StatusIgre status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
