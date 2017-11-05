package io.tapack.satisfy.services;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

@WebServiceClient(name = "WheatherServiceService", 
                  wsdlLocation = "src/test/wsdl/WheatherService.wsdl",
                  targetNamespace = "http://services.showcase.acceptancetest.tapack.io/")
public class WheatherServiceService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://services.showcase.acceptancetest.tapack.io/", "WheatherServiceService");
    public final static QName WheatherServicePort = new QName("http://services.showcase.acceptancetest.tapack.io/", "WheatherServicePort");
    static {
        URL url = null;
        try {
            url = new URL("src/test/wsdl/WheatherService.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(WheatherServiceService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "src/test/wsdl/WheatherService.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public WheatherServiceService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public WheatherServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WheatherServiceService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns WheatherService
     */
    @WebEndpoint(name = "WheatherServicePort")
    public WheatherService getWheatherServicePort() {
        return super.getPort(WheatherServicePort, WheatherService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WheatherService
     */
    @WebEndpoint(name = "WheatherServicePort")
    public WheatherService getWheatherServicePort(WebServiceFeature... features) {
        return super.getPort(WheatherServicePort, WheatherService.class, features);
    }

}
