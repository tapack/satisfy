package io.tapack.satisfy.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(targetNamespace = WheatherService.HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, name = "WheatherService")
@XmlSeeAlso({ObjectFactory.class})
public interface WheatherService {

    String HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO = "http://services.showcase.acceptancetest.tapack.io/";

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getWeatherFor", targetNamespace = HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, className = "io.tapack.satisfy.services.GetWeatherFor")
    @WebMethod(action = "getWeatherFor")
    @ResponseWrapper(localName = "getWeatherForResponse", targetNamespace = HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, className = "io.tapack.satisfy.services.GetWeatherForResponse")
    public String getWeatherFor(
            @WebParam(name = "arg0", targetNamespace = "")
            String arg0
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getWeatherInformation", targetNamespace = HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, className = "io.tapack.satisfy.services.GetWeatherInformation")
    @WebMethod(action = "getWeatherInformation")
    @ResponseWrapper(localName = "getWeatherInformationResponse", targetNamespace = HTTP_SERVICES_SHOWCASE_ACCEPTANCETEST_TAPACK_IO, className = "io.tapack.satisfy.services.GetWeatherInformationResponse")
    public String getWeatherInformation();
}
