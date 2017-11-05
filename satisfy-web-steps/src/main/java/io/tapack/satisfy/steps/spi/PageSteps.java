package io.tapack.satisfy.steps.spi;

import org.openqa.selenium.By;

public interface PageSteps extends Acceptable {

    void pageHasTitle(String title);

    void open(String contextPath);

    void switchToNewWindow();

    void returnToBaseWindow();

    void switchToIFrame(By identity);

    void returnToPageFromIFrame();
}