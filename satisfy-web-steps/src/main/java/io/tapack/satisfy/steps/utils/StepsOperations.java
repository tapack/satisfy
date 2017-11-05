package io.tapack.satisfy.steps.utils;

import org.openqa.selenium.By;

public class StepsOperations {

    public static By parseBy(String identity) {
        if (isXPath(identity)) {
            return By.xpath(identity);
        } else if (isCss(identity)) {
            return By.cssSelector(identity);
        } else {
            return By.id(identity);
        }
    }

    private static boolean isCss(String identity) {
        return identity.startsWith(".") || identity.startsWith("#");
    }

    private static boolean isXPath(String identity) {
        return identity.startsWith("./") || identity.startsWith("/");
    }
}
