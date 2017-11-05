package io.tapack.satisfy.steps;

import io.tapack.satisfy.services.WheatherServiceImpl;
import net.thucydides.core.Thucydides;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.jbehave.core.annotations.Given;
import org.junit.Assert;

import javax.xml.ws.Endpoint;

import static org.junit.Assert.assertTrue;

public class FakeSoapServiceSteps {

    @Given("send SOAP webservice")
    public void givenFakeSOAPWebservice() {
        Endpoint endpoint = Endpoint.publish("http://localhost:9090/WheatherServicePort", new WheatherServiceImpl());
        assertTrue(endpoint.isPublished());
        Assert.assertEquals("http://schemas.xmlsoap.org/wsdl/soap/http", endpoint.getBinding().getBindingID());
    }

    @Given("start SOAP webservice port '$port'")
    public void givenFakeSOAPWebservice(String port) {
        String serviceUrl = "http://localhost:" + port + "/WheatherServicePort";
        Endpoint endpoint = Endpoint.publish(serviceUrl, new WheatherServiceImpl());
        assertTrue(endpoint.isPublished());
        Assert.assertEquals("http://schemas.xmlsoap.org/wsdl/soap/http", endpoint.getBinding().getBindingID());
    }

    @Given("send SOAP web service through https")
    public void givenFakeSOAPWebserviceThroughHttps() {
        SpringBusFactory factory = new SpringBusFactory();
        Bus bus = factory.createBus("src/test/resources/META-INF/spring/jaxws-server-with-ssl.xml");
        BusFactory.setDefaultBus(bus);
        Endpoint endpoint = Endpoint.publish("https://localhost:9091/WheatherServicePort", new WheatherServiceImpl());
        assertTrue(endpoint.isPublished());
        Assert.assertEquals("http://schemas.xmlsoap.org/wsdl/soap/http", endpoint.getBinding().getBindingID());
    }

    @Given("save placeholder with '$key' and '$value' for freemarker template")
    public void givenForSaveFreeMarkerPlaceholder(String key, String value) {
        Thucydides.getCurrentSession().put(key, value);
    }
}
