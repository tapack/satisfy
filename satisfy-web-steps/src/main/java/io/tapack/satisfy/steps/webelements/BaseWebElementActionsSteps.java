package io.tapack.satisfy.steps.webelements;

import io.tapack.satisfy.steps.spi.WebElementSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.openqa.selenium.By;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class BaseWebElementActionsSteps implements WebElementSteps {

    private final By identity;
    private final WebPage webPage;

    public BaseWebElementActionsSteps(By identity) {
        this.identity = identity;
        webPage = WebStepsFactory.getPage();
    }

    @Override
    public void whenClicksOnElement() {
        webPage.element(identity).click();
    }

    @Override
    public void fillField(String value) {
        webPage.enter(value).intoField(identity);
    }

    @Override
    public void thenElementDoesNotContainAttributeWithValue(String attributeName, String attributeValue) {
        assertThat(webPage.element(identity).getAttribute(attributeName), not
                (containsString(attributeValue)));
    }

    @Override
    public void thenElementContainsAttributeWithValue(String attributeName,
                                                      String attributeValue) {
        assertThat(webPage.element(identity).getAttribute(attributeName), is
                (containsString(attributeValue)));
    }

    @Override
    public boolean accept(By identity) {
        return false;
    }

}
