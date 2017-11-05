package io.tapack.satisfy.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the io.tapack.acceptancetest.showcase.services package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    public static final String HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO = "http://services.showcase.acceptancetest.tapack.io/";
    private final static QName _GetWeatherInformation_QNAME = new QName(HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, "getWeatherInformation");
    private final static QName _GetWeatherFor_QNAME = new QName(HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, "getWeatherFor");
    private final static QName _GetWeatherInformationResponse_QNAME = new QName(HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, "getWeatherInformationResponse");
    private final static QName _GetWeatherForResponse_QNAME = new QName(HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, "getWeatherForResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: io.tapack.acceptancetest.showcase.services
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetWeatherInformation }
     */
    public GetWeatherInformation createGetWeatherInformation() {
        return new GetWeatherInformation();
    }

    /**
     * Create an instance of {@link GetWeatherFor }
     */
    public GetWeatherFor createGetWeatherFor() {
        return new GetWeatherFor();
    }

    /**
     * Create an instance of {@link GetWeatherInformationResponse }
     */
    public GetWeatherInformationResponse createGetWeatherInformationResponse() {
        return new GetWeatherInformationResponse();
    }

    /**
     * Create an instance of {@link GetWeatherForResponse }
     */
    public GetWeatherForResponse createGetWeatherForResponse() {
        return new GetWeatherForResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeatherInformation }{@code >}}
     */
    @XmlElementDecl(namespace = HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, name = "getWeatherInformation")
    public JAXBElement<GetWeatherInformation> createGetWeatherInformation(GetWeatherInformation value) {
        return new JAXBElement<GetWeatherInformation>(_GetWeatherInformation_QNAME, GetWeatherInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeatherFor }{@code >}}
     */
    @XmlElementDecl(namespace = HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, name = "getWeatherFor")
    public JAXBElement<GetWeatherFor> createGetWeatherFor(GetWeatherFor value) {
        return new JAXBElement<GetWeatherFor>(_GetWeatherFor_QNAME, GetWeatherFor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeatherInformationResponse }{@code >}}
     */
    @XmlElementDecl(namespace = HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, name = "getWeatherInformationResponse")
    public JAXBElement<GetWeatherInformationResponse> createGetWeatherInformationResponse(GetWeatherInformationResponse value) {
        return new JAXBElement<GetWeatherInformationResponse>(_GetWeatherInformationResponse_QNAME, GetWeatherInformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeatherForResponse }{@code >}}
     */
    @XmlElementDecl(namespace = HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, name = "getWeatherForResponse")
    public JAXBElement<GetWeatherForResponse> createGetWeatherForResponse(GetWeatherForResponse value) {
        return new JAXBElement<GetWeatherForResponse>(_GetWeatherForResponse_QNAME, GetWeatherForResponse.class, null, value);
    }

}
