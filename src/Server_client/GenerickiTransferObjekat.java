package Server_client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for generickiTransferObjekat complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="generickiTransferObjekat">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gdo" type="{http://Server/}korisnik" minOccurs="0"/>
 *         &lt;element name="poruka" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signal" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="nazivOperacije" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentRecord" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generickiTransferObjekat", propOrder = {
    "gdo",
    "poruka",
    "signal",
    "nazivOperacije",
    "currentRecord"
})
public class GenerickiTransferObjekat {

    public Korisnik gdo;
    protected String poruka;
    protected boolean signal;
    protected String nazivOperacije;
    protected int currentRecord;

    /**
     * Gets the value of the gdo property.
     *
     * @return possible object is {@link Korisnik }
     *
     */
    public Korisnik getGdo() {
        return gdo;
    }

    /**
     * Sets the value of the gdo property.
     *
     * @param value allowed object is {@link Korisnik }
     *
     */
    public void setGdo(Korisnik value) {
        this.gdo = value;
    }

    /**
     * Gets the value of the poruka property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPoruka() {
        return poruka;
    }

    /**
     * Sets the value of the poruka property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPoruka(String value) {
        this.poruka = value;
    }

    /**
     * Gets the value of the signal property.
     *
     */
    public boolean isSignal() {
        return signal;
    }

    /**
     * Sets the value of the signal property.
     *
     */
    public void setSignal(boolean value) {
        this.signal = value;
    }

    /**
     * Gets the value of the nazivOperacije property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNazivOperacije() {
        return nazivOperacije;
    }

    /**
     * Sets the value of the nazivOperacije property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNazivOperacije(String value) {
        this.nazivOperacije = value;
    }

    /**
     * Gets the value of the currentRecord property.
     *
     */
    public int getCurrentRecord() {
        return currentRecord;
    }

    /**
     * Sets the value of the currentRecord property.
     *
     */
    public void setCurrentRecord(int value) {
        this.currentRecord = value;
    }

}
