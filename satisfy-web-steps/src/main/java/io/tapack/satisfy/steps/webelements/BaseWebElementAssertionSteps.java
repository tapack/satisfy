package io.tapack.satisfy.steps.webelements;

import io.tapack.satisfy.steps.spi.AssertionSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.openqa.selenium.By;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class BaseWebElementAssertionSteps implements AssertionSteps {

    private final By identity;
    private final WebPage webPage;

    public BaseWebElementAssertionSteps(By identity) {
        this.identity = identity;
        webPage = WebStepsFactory.getPage();
    }

    @Override
    public void identityContainsText(String text) {
        assertThat(webPage.element(identity).getText(), is(containsString
                (text)));
    }

    @Override
    public void identityDoesNotContainText(String text) {
        assertThat(webPage.element(identity).getText(), not(containsString
                (text)));
    }

    @Override
    public boolean accept(By identity) {
        return false;
    }

}