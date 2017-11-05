package io.tapack.satisfy.steps.webelements;

import io.tapack.satisfy.steps.spi.RadioButtonSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.openqa.selenium.By;

public class BaseRadioButtonSteps implements RadioButtonSteps {

    private final WebPage webPage;

    public BaseRadioButtonSteps() {
        webPage = WebStepsFactory.getPage();
    }

    @Override
    public void whenClickRadioButtonWithLabel(String option) {
        String forAttribute = webPage.element(By.xpath("//label[text()='" +
                option + "']")).getAttribute("for");
        webPage.element(By.xpath("//*[@for='" + forAttribute + "']")).click();
    }

    @Override
    public boolean accept() {
        return false;
    }

}
