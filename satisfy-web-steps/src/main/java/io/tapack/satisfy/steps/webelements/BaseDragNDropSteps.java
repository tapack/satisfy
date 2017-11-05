package io.tapack.satisfy.steps.webelements;

import io.tapack.satisfy.steps.spi.DragNDropSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import net.thucydides.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

public class BaseDragNDropSteps implements DragNDropSteps {

    private final By identity;
    private final WebPage webPage;

    public BaseDragNDropSteps(By identity) {
        this.identity = identity;
        this.webPage = WebStepsFactory.getPage();
    }

    @Override
    public boolean accept(By identity) {
        return false;
    }

    @Override
    public void whenSelectElementAndMoveTo(int x, int y) {
        WebElementFacade element = webPage.element(identity);
        Actions builder = new Actions(webPage.getDriver());
        builder.dragAndDropBy(element, x, y).build().perform();
    }

    @Override
    public void thenCheckElementHasCorrectCoordinate(int x, int y) {
        WebElementFacade element = webPage.element(identity);
        int top = 0;
        int left = 0;
        String style = element.getAttribute("style");
        String[] css = style.split(";");
        for (String value : css) {
            if (value.contains("top"))
                top = Integer.parseInt(value.substring(6, value.indexOf("px")));
            if (value.contains("left"))
                left = Integer.parseInt(value.substring(7, value.indexOf
                        ("px")));
        }
        Assert.assertEquals("Top must be " + x + " but was " + top, x, top);
        Assert.assertEquals("Left must be " + y + " but was " + left, y, left);
    }
}
