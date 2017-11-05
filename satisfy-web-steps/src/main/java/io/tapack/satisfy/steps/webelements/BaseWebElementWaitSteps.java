package io.tapack.satisfy.steps.webelements;

import io.tapack.satisfy.steps.spi.WaitSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.openqa.selenium.By;

public class BaseWebElementWaitSteps implements WaitSteps {

    private final WebPage webPage;

    public BaseWebElementWaitSteps() {
        webPage = WebStepsFactory.getPage();
    }

    @Override
    public void waitIdentityIsDisplayed(By identity) {
        webPage.shouldBeVisible(identity);
    }

    @Override
    public void waitIdentityIsNotDisplayed(By identity) {
        webPage.shouldNotBeVisible(identity);
    }

    @Override
    public boolean accept() {
        return false;
    }

}
