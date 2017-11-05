package io.tapack.satisfy.services;

import javax.jws.WebService;
import java.util.logging.Logger;

@WebService(
        serviceName = "WheatherServiceService",
        portName = "WheatherServicePort",
        targetNamespace = "http://services.showcase.acceptancetest.tapack.io/",
        wsdlLocation = "src/test/wsdl/WheatherService.wsdl",
        endpointInterface = "io.tapack.satisfy.services.WheatherService")

public class WheatherServiceImpl implements WheatherService {

    private static final Logger LOG = Logger.getLogger(WheatherServiceImpl.class.getName());

    /* (non-Javadoc)
     * @see io.tapack.acceptancetest.showcase.services.WheatherService#getWeatherFor(java.lang.String  arg0 )*
     */
    public String getWeatherFor(String arg0) {
        LOG.info("Executing operation getWeatherFor");
        String s = "";
        if (arg0.equals("Orlando")) {
            s = "Sunny!";
        }
        if (arg0.equals("Boston")) {
            s = "Cloudy!";
        }
        return s;
    }



    /* (non-Javadoc)
     * @see io.tapack.acceptancetest.showcase.services.WheatherService#getWeatherInformation(*
     */
    public String getWeatherInformation() {
        LOG.info("Executing operation getWeatherInformation");
        return "Hi this is fake weather webservice.";
    }

}
