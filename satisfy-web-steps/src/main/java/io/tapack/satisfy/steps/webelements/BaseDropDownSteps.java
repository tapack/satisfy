package io.tapack.satisfy.steps.webelements;

import io.tapack.satisfy.steps.spi.DropDownSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.openqa.selenium.By;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BaseDropDownSteps implements DropDownSteps {

    private final By identity;
    private final WebPage webPage;

    public BaseDropDownSteps(By identity) {
        this.identity = identity;
        webPage = WebStepsFactory.getPage();
    }

    @Override
    public boolean accept(By identity) {
        return false;
    }

    @Override
    public void whenSelectOptionFromDropDown(String option) {
        webPage.element(identity).selectByVisibleText(option);
        thenOptionIsSelectedFromDropDown(option);
    }

    @Override
    public void thenOptionIsSelectedFromDropDown(String option) {
        assertThat(webPage.element(identity).getSelectedVisibleTextValue(),
                is(option));
    }

}
