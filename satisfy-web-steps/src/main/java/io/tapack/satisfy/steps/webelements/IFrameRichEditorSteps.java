package io.tapack.satisfy.steps.webelements;

import io.tapack.satisfy.steps.spi.PageSteps;
import io.tapack.satisfy.steps.spi.RichEditorSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import io.tapack.satisfy.steps.utils.FrameOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.tapack.satisfy.steps.spi.WebStepsFactory.getAssertionSteps;

public class IFrameRichEditorSteps implements RichEditorSteps {

    private static final By BODY = By.xpath(".//body");
    private final WebPage page;
    private final PageSteps pageSteps;
    private By editor;

    public IFrameRichEditorSteps() {
        page = WebStepsFactory.getPage();
        pageSteps = WebStepsFactory.getPageSteps();
    }

    @Override
    public void fillEditorWithText(String text) {
        pageSteps.switchToIFrame(editor);
        page.evaluateJavascript("document.body.innerHTML = '<p>" + text +
                "</p>'");
        pageSteps.returnToPageFromIFrame();
    }

    @Override
    public void verifyEditorContainsText(String text) {
        pageSteps.switchToIFrame(editor);
        getAssertionSteps(BODY).identityContainsText(text);
        pageSteps.returnToPageFromIFrame();
    }

    @Override
    public void verifyEditorNotContainsText(String text) {
        pageSteps.switchToIFrame(editor);
        getAssertionSteps(BODY).identityDoesNotContainText(text);
        pageSteps.returnToPageFromIFrame();
    }

    @Override
    public boolean accept(By identity) {
        editor = identity;
        WebElement editor = page.element(identity);
        return FrameOperations.isFrameElement(editor);
    }
}