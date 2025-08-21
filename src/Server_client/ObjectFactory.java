
package Server_client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the Server_client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RegistrujKorisnika_QNAME = new QName("http://Server/", "registrujKorisnika");
    private final static QName _RegistrujKorisnikaResponse_QNAME = new QName("http://Server/", "registrujKorisnikaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: Server_client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RegistrujKorisnika }
     * 
     */
    public RegistrujKorisnika createRegistrujKorisnika() {
        return new RegistrujKorisnika();
    }

    /**
     * Create an instance of {@link RegistrujKorisnikaResponse }
     * 
     */
    public RegistrujKorisnikaResponse createRegistrujKorisnikaResponse() {
        return new RegistrujKorisnikaResponse();
    }

    /**
     * Create an instance of {@link GenerickiTransferObjekat }
     * 
     */
    public GenerickiTransferObjekat createGenerickiTransferObjekat() {
        return new GenerickiTransferObjekat();
    }

    /**
     * Create an instance of {@link Korisnik }
     * 
     */
    public Korisnik createKorisnik() {
        return new Korisnik();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrujKorisnika }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "registrujKorisnika")
    public JAXBElement<RegistrujKorisnika> createRegistrujKorisnika(RegistrujKorisnika value) {
        return new JAXBElement<RegistrujKorisnika>(_RegistrujKorisnika_QNAME, RegistrujKorisnika.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrujKorisnikaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Server/", name = "registrujKorisnikaResponse")
    public JAXBElement<RegistrujKorisnikaResponse> createRegistrujKorisnikaResponse(RegistrujKorisnikaResponse value) {
        return new JAXBElement<RegistrujKorisnikaResponse>(_RegistrujKorisnikaResponse_QNAME, RegistrujKorisnikaResponse.class, null, value);
    }

}
