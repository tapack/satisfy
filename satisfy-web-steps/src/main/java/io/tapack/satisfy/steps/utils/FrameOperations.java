package io.tapack.satisfy.steps.utils;

import io.tapack.satisfy.steps.spi.WebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FrameOperations {
    private static final String IFRAME_TAG = "iframe";
    private static final String FRAME_TAG = "frame";

    private FrameOperations() {
    }

    public static WebElement getFrameElement(WebPage webPage, By
            frameIdentity) {
        final WebElement editorElement = webPage.element(frameIdentity);
        return isNotFrameElement(editorElement) ? findFrameInsideElement
                (editorElement) : editorElement;
    }

    public static boolean isFrameElement(final WebElement element) {
        return !isNotFrameElement(element) || isElementContainsTag(element,
                IFRAME_TAG) || isElementContainsTag(element, FRAME_TAG);
    }

    private static boolean isElementContainsTag(WebElement element, String
            tagName) {
        return !element.findElements(By.tagName(tagName)).isEmpty();
    }

    private static boolean isNotFrameElement(WebElement element) {
        return !hasElementTagName(element, IFRAME_TAG) && !hasElementTagName
                (element, FRAME_TAG);
    }

    private static boolean hasElementTagName(WebElement element, String
            tagName) {
        return element.getTagName().contentEquals(tagName);
    }

    private static WebElement findFrameInsideElement(WebElement element) {
        WebElement frameElement;
        if (isElementContainsTag(element, IFRAME_TAG)) {
            frameElement = findChildElementByTagName(IFRAME_TAG, element);
        } else {
            frameElement = findChildElementByTagName(FRAME_TAG, element);
        }
        return frameElement;
    }

    private static WebElement findChildElementByTagName(String tagName,
                                                        WebElement parent) {
        return parent.findElement(By.tagName(tagName));
    }
}