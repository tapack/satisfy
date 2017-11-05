package io.tapack.satisfy.steps.webelements;

import ch.lambdaj.function.convert.Converter;
import io.tapack.satisfy.steps.spi.PicklistSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static ch.lambdaj.Lambda.convert;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class BasePicklistSteps implements PicklistSteps {

    private final WebPage webPage;
    private final By rootElement;

    public BasePicklistSteps(By identity) {
        rootElement = identity;
        webPage = WebStepsFactory.getPage();
    }

    @Override
    public boolean accept(By identity) {
        return false;
    }

    @Override
    public void whenSelectFromPicklist(List<String> items) {
        if (items.size() > 1) {
            selectMultipleItems(items);
        } else {
            getPicklistItem(items.get(0)).click();
        }
    }

    private void selectMultipleItems(List<String> items) {
        //set focus on element
        getPicklistItem(items.get(0)).click();
        for (String item : items) {
            selectItemByJScript(item);
        }
    }

    private void selectItemByJScript(String text) {
        ((JavascriptExecutor) WebStepsFactory.getPage().getDriver()).
                executeScript("$('li:contains(\"" + text + "\")').addClass" +
                        "('ui-selected ui-state-highlight " +
                        "pickList_selectedListItem')");
    }

    @Override
    public void whenDoubleClickOnItemFromPicklist(String itemValue) {
        WebElement picklistItem = getPicklistItem(itemValue);
        Actions action = new Actions(WebStepsFactory.getPage().getDriver());
        action.doubleClick(picklistItem).perform();
    }

    private WebElement getPicklistItem(String itemValue) {
        By picklistItem = By.xpath("//li[text()='" + itemValue + "']");
        return getRootElement().findElement(picklistItem);
    }

    @Override
    public void whenPressAddButtonInPicklist() {
        clickOnPicklistButtonByClassName("pickList_add");
    }

    @Override
    public void whenPressAddAllButtonInPicklist() {
        clickOnPicklistButtonByClassName("pickList_addAll");
    }

    @Override
    public void whenPressRemoveButtonInPicklist() {
        clickOnPicklistButtonByClassName("pickList_remove");
    }

    @Override
    public void whenPressRemoveAllButtonInPicklist() {
        clickOnPicklistButtonByClassName("pickList_removeAll");
    }

    private void clickOnPicklistButtonByClassName(String className) {
        By button = By.className(className);
        getRootElement().findElement(button).click();
    }

    @Override
    public void thenAppearedInPicklistSourceList(List<String> items) {
        verifyItemsInPicklistContainer(items, "pickList_sourceListContainer");
    }

    @Override
    public void thenAppearedInPicklistTargetList(List<String> items) {
        verifyItemsInPicklistContainer(items, "pickList_targetListContainer");
    }

    private void verifyItemsInPicklistContainer(List<String> expectedItems,
                                                String containerClass) {
        List<String> actualItems = getItemsFromPicklistContainer
                (containerClass);
        assertThat(actualItems, equalTo(expectedItems));
    }

    private List<String> getItemsFromPicklistContainer(String listContainer) {
        By list = By.className(listContainer);
        WebElement listElement = getRootElement().findElement(list);
        By items = By.className("pickList_listItem");
        List<WebElement> itemElements = listElement.findElements(items);
        return convert(itemElements, toTextValues());
    }

    private Converter<WebElement, String> toTextValues() {
        return new Converter<WebElement, String>() {
            public String convert(WebElement from) {
                return from.getText();
            }
        };
    }

    private WebElement getRootElement() {
        return webPage.element(rootElement);
    }

}
