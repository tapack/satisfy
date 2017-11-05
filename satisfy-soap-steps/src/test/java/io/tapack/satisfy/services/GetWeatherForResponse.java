package io.tapack.satisfy.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getWeatherForResponse complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="getWeatherForResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getWeatherForResponse", propOrder = {
        "returnValue"
})
public class GetWeatherForResponse {

    @XmlElement(name = "return")
    private String returnValue;

    /**
     * Gets the value of the return property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReturn() {
        return returnValue;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReturn(String value) {
        this.returnValue = value;
    }

}
