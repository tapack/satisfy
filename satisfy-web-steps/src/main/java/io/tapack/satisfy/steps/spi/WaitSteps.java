package io.tapack.satisfy.steps.spi;

import org.openqa.selenium.By;

public interface WaitSteps extends Acceptable {

    void waitIdentityIsDisplayed(By identity);

    void waitIdentityIsNotDisplayed(By identity);

}
