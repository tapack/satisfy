package io.tapack.satisfy.steps.spi;

import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public interface WebPage extends Acceptable {

    By convertToSelector(String identity);

    void waitAjaxIsFinished();

    WebElementFacade element(By identity);

    PageObject.FieldEntry enter(String value);

    void shouldBeVisible(By by);

    void shouldNotBeVisible(By by);

    String getTitle();

    void openAt(String url);

    WebDriver getDriver();

    void clickOn(WebElement element);

    PageObject waitFor(ExpectedCondition expectedCondition);

    Object evaluateJavascript(String script);

}
