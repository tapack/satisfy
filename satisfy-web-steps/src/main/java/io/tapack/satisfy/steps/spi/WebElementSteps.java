package io.tapack.satisfy.steps.spi;

public interface WebElementSteps extends AcceptableByIdentity {

    void whenClicksOnElement();

    void fillField(String value);

    void thenElementContainsAttributeWithValue(String attributeName, String
            attributeValue);

    void thenElementDoesNotContainAttributeWithValue(String attributeName,
                                                     String attributeValue);

}
