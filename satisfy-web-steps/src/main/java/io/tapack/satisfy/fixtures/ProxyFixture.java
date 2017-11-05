package io.tapack.satisfy.fixtures;

import net.thucydides.core.fixtureservices.FixtureException;
import net.thucydides.core.fixtureservices.FixtureService;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ProxyFixture implements FixtureService {

    private final static String PROPERTIES_FILENAME = "zap.proxy.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyFixture
            .class);

    @Override
    public void setup() throws FixtureException {
    }

    @Override
    public void shutdown() throws FixtureException {
    }

    @Override
    public void addCapabilitiesTo(DesiredCapabilities capabilities) {
        Map<String, String> proxyProperties = getProxyProperties();

        if (proxyProperties.size() == 0) {
            return;
        } else if (proxyProperties.containsValue("")) {
            try {
                throw new Exception(
                        "Proxy cannot be set. Please provide all settings " +
                                "needed to setup proxy connection.");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            LOGGER.error("Setting proxy ...");
            setProxyTo(capabilities, proxyProperties);
        }
    }

    private void setProxyTo(DesiredCapabilities capabilities, Map<String,
            String> proxyProperties) {
        Proxy proxy = new Proxy();
        boolean isEnable = false;
        try {
            String enableParam = System.getProperty("zap.enable");
            isEnable = Boolean.parseBoolean(enableParam);
        } catch (Exception ex) {
            LOGGER.error("\"NullPointerException = \" + ex.getMessage()");
        }


        String host = proxyProperties.get(ProxyProperty.ZAP_PROXY_HOST
                .toString());
        String port = proxyProperties.get(ProxyProperty.ZAP_PROXY_PORT
                .toString());

        if (!isEnable) {
            LOGGER.error("Proxy will not be set: " + " = " + isEnable);
            return;
        }
        proxy.setHttpProxy(host + ":" + port);
        proxy.setSocksProxy(host + ":" + port);
        proxy.setFtpProxy(host + ":" + port);
        proxy.setSslProxy(host + ":" + port);
        proxy.setProxyType(ProxyType.MANUAL);

        capabilities.setCapability(CapabilityType.PROXY, proxy);
    }


    private Map<String, String> getProxyProperties() {
        Map<String, String> result = new LinkedHashMap<>();
        Properties propertyFile = new Properties();

        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(PROPERTIES_FILENAME);
            propertyFile.load(inputStream);
        } catch (Exception e) {
            return new LinkedHashMap<>();
        }

        LOGGER.error("Loading proxy properties...");
        ProxyProperty[] availableProperties = ProxyProperty.values();
        for (int i = 0; i < availableProperties.length; i++) {
            ProxyProperty prop = availableProperties[i];
            String propName = prop.getPropertyName();
            String propValue = prop.from(propertyFile);

            result.put(propName, propValue);
            LOGGER.error(propName + "=" + propValue);
        }
        return result;
    }
}
