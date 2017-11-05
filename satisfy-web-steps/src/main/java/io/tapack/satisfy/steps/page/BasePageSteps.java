package io.tapack.satisfy.steps.page;

import io.tapack.satisfy.props.WebProperty;
import io.tapack.satisfy.steps.spi.PageSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import io.tapack.satisfy.steps.utils.FrameOperations;
import net.thucydides.core.Thucydides;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.Set;

import static io.tapack.satisfy.props.SatisfyWebProperties.getSatisfyWebProperties;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BasePageSteps implements PageSteps {

    private final WebPage webPage;
    private String baseWindowHandle;

    public BasePageSteps() {
        webPage = WebStepsFactory.getPage();
    }

    @Override
    public void open(String path) {
        if (path.startsWith("http:")
                || path.startsWith("https:")
                || path.startsWith("file:")) {
            webPage.openAt(path);
        } else {
            String baseUrl = getSatisfyWebProperties()
                    .getProperty(WebProperty.WEBDRIVER_BASE_URL);
            webPage.openAt(baseUrl + path);
        }
    }

    @Override
    public void pageHasTitle(String title) {
        assertThat(webPage.getTitle(), is(equalTo(title)));
    }

    @Override
    public void switchToNewWindow() {
        WebDriver driver = webPage.getDriver();
        baseWindowHandle = driver.getWindowHandle();
        Thucydides.getCurrentSession().put("baseWindowHandle",
                baseWindowHandle);
        Set<String> openedWindows = driver.getWindowHandles();
        String newWindow = null;
        if (openedWindows.size() > 1 && openedWindows.remove
                (baseWindowHandle)) {
            Iterator<String> openedWindowsIterator = openedWindows.iterator();
            newWindow = openedWindowsIterator.next();
        } else {
            int timeout = getSatisfyWebProperties().getSatisfyWait();
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            newWindow = wait.until(anyWindowOtherThan(openedWindows));
        }
        driver.switchTo().window(newWindow);
    }

    @Override
    public void returnToBaseWindow() {
        baseWindowHandle = (String) Thucydides.getCurrentSession().get
                ("baseWindowHandle");
        WebDriver driver = webPage.getDriver();
        if (!baseWindowHandle.isEmpty() && !driver.getWindowHandle().equals
                (baseWindowHandle)) {
            driver.switchTo().window(baseWindowHandle);
        }
    }

    @Override
    public void switchToIFrame(By identity) {
        WebElement frameElement = FrameOperations.getFrameElement(webPage,
                identity);
        webPage.getDriver().switchTo().frame(frameElement);
    }

    @Override
    public void returnToPageFromIFrame() {
        webPage.getDriver().switchTo().defaultContent();
    }

    @Override
    public boolean accept() {
        return false;
    }

    private ExpectedCondition<String> anyWindowOtherThan(
            final Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                if (driver == null)
                    throw new WebDriverException();
                Set<String> allWindows = driver.getWindowHandles();
                allWindows.removeAll(oldWindows);
                return allWindows.size() > 0 ? allWindows.iterator().next()
                        : null;
            }
        };
    }
}