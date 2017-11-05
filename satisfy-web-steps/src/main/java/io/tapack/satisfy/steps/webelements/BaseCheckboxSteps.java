package io.tapack.satisfy.steps.webelements;

import io.tapack.satisfy.steps.spi.CheckboxSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import net.thucydides.core.pages.WebElementFacade;
import org.openqa.selenium.By;

public class BaseCheckboxSteps implements CheckboxSteps {

    private final By identity;
    private final WebPage webPage;

    public BaseCheckboxSteps(By identity) {
        this.identity = identity;
        webPage = WebStepsFactory.getPage();
    }

    @Override
    public void whenMakeCheckboxUnchecked() {
        WebElementFacade element = webPage.element(identity);
        if (element.isSelected()) {
            element.click();
        }
    }

    @Override
    public void whenMakeCheckboxChecked() {
        WebElementFacade element = webPage.element(identity);
        if (!element.isSelected()) {
            element.click();
        }
    }

    @Override
    public boolean accept(By identity) {
        return false;
    }

}
