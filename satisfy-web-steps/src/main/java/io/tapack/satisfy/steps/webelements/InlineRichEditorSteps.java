package io.tapack.satisfy.steps.webelements;

import io.tapack.satisfy.steps.spi.RichEditorSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import io.tapack.satisfy.steps.utils.FrameOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.tapack.satisfy.steps.spi.WebStepsFactory.getAssertionSteps;

public class InlineRichEditorSteps implements RichEditorSteps {

    private static final String EDITABLE_ATTRIBUTE = "contenteditable";
    private final WebPage page;
    private By editorBy;

    public InlineRichEditorSteps() {
        page = WebStepsFactory.getPage();
    }

    @Override
    public void fillEditorWithText(String text) {
        WebElement element = page.element(editorBy);
        String elementId = element.getAttribute("id");
        if (element.getAttribute(EDITABLE_ATTRIBUTE) != null) {
            page.evaluateJavascript("document.getElementById('" + elementId +
                    "').innerHTML = '" + text + "'");
        }
    }

    @Override
    public void verifyEditorContainsText(String text) {
        getAssertionSteps(editorBy).identityContainsText(text);
    }

    @Override
    public void verifyEditorNotContainsText(String text) {
        getAssertionSteps(editorBy).identityDoesNotContainText(text);
    }

    @Override
    public boolean accept(By identity) {
        this.editorBy = identity;
        WebElement editor = page.element(identity);
        return !FrameOperations.isFrameElement(editor);
    }
}