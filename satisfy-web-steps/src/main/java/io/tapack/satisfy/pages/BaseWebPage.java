package io.tapack.satisfy.pages;

import io.tapack.satisfy.props.WebProperty;
import io.tapack.satisfy.steps.spi.WebPage;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.tapack.satisfy.props.SatisfyWebProperties
        .getSatisfyWebProperties;

public class BaseWebPage extends PageObject implements WebPage {

    public static final boolean AJAX_LOADER_ID_IS_DEFINED =
            getSatisfyWebProperties().isDefined(WebProperty.AJAX_LOADER_ID);
    public static final String AJAX_LOADER_ID = getSatisfyWebProperties()
            .getProperty(WebProperty.AJAX_LOADER_ID);
    public static final int TIME_OUT_IN_SECONDS = getSatisfyWebProperties()
            .getSatisfyWait();
    private static final long WAIT_FOR_ELEMENT_PAUSE_LENGTH = 50;

    public BaseWebPage() {
        super(ThucydidesWebDriverSupport.getDriver());
    }

    @Override
    public void waitAjaxIsFinished() {
        if (AJAX_LOADER_ID_IS_DEFINED) {
            doWait().until(ExpectedConditions.invisibilityOfElementLocated(By
                    .id(AJAX_LOADER_ID)));
        }
    }

    @Override
    public boolean accept() {
        return false;
    }

    /**
     * ID tokens must begin with a letter ([A-Za-z])
     * and may be followed by any number of letters,
     * digits ([0-9]), hyphens ("-"), underscores ("_"),
     * colons (":"), and periods (".").
     * As a purely practical matter, you may want to avoid certain characters.
     * Periods, colons and '#' have special meaning in CSS selectors.
     */
    @Override
    public By convertToSelector(String identity) {
        waitAjaxIsFinished();
        By selector = new By.ByCssSelector(identity);
        if (identity.matches("^[a-zA-Z][\\w_-]*$")) {
            selector = new By.ById(identity);
        } else if (identity.startsWith("/") || identity.startsWith("./")) {
            selector = new By.ByXPath(identity);
        }
        return selector;
    }

    @Override
    public Object evaluateJavascript(String script) {
        return ((JavascriptExecutor) getDriver()).executeScript(script);
    }

    @Override
    public void shouldBeVisible(By by) {
        doWait().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Override
    public void shouldNotBeVisible(By by) {
        doWait().until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    @Override
    public PageObject waitFor(ExpectedCondition expectedCondition) {
        doWait().until(expectedCondition);
        return this;
    }

    private WebDriverWait doWait() {
        return new WebDriverWait(getDriver(), TIME_OUT_IN_SECONDS,
                WAIT_FOR_ELEMENT_PAUSE_LENGTH);
    }

}
