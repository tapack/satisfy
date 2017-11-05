package io.tapack.satisfy.steps.webelements;

import io.tapack.satisfy.steps.spi.CalendarSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseCalendarSteps implements CalendarSteps {

    public static final String INPUT_TAG_NAME = "input";
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final DateFormat MONTH_DAY_YEAR_FORMAT = new
            SimpleDateFormat(DATE_FORMAT);
    public static final DateFormat MONTH_DAY_YEAR_EXTENDED_FORMAT = new
            SimpleDateFormat("MMMM/dd/yyyy");
    private static final By DATE_PICKER = By.cssSelector(":not(" +
            ".ui-datepicker-inline).ui-datepicker.ui-widget");
    private static final By MONTH = By.cssSelector(".ui-datepicker-month");
    private static final By YEAR = By.cssSelector(".ui-datepicker-year");
    private static final By NEXT_BUTTON = By.cssSelector(".ui-datepicker-next");
    private static final By PREV_BUTTON = By.cssSelector(".ui-datepicker-prev");

    private final By identity;
    private final WebPage webPage;

    private WebElement datePicker;

    public BaseCalendarSteps(By identity) {
        this.identity = identity;
        webPage = WebStepsFactory.getPage();
    }

    @Override
    public void fillDateIntoCalendarField(String date) {
        WebElement calendarInputField = findInputField(identity);
        calendarInputField.clear();
        calendarInputField.sendKeys(date);
    }

    @Override
    public void selectDateFromPicker(String date) {
        openPopupCalendar(identity);
        Calendar expectedCalendar = getExpectedCalendar(date);
        navigateToExpectedMonth(expectedCalendar);
        getDayToSelect(expectedCalendar).click();
    }

    @Override
    public void verifyDateAppearedInCalendarField(String date) {
        Assert.assertEquals(date, findInputField(identity).getAttribute
                ("value"));
    }

    @Override
    public boolean accept(By identity) {
        String cssClasses = webPage.element(identity).getAttribute("class");
        return cssClasses.contains("hasDatepicker") && !cssClasses.contains
                ("ui-widget");
    }

    private WebElement findInputField(By identity) {
        WebElement calendarElement = webPage.element(identity);
        return calendarElement.getTagName().equals(INPUT_TAG_NAME) ?
                calendarElement : calendarElement.findElement(By.tagName
                (INPUT_TAG_NAME));
    }

    private void openPopupCalendar(By identity) {
        webPage.element(identity).click();
        webPage.shouldBeVisible(DATE_PICKER);
        datePicker = webPage.element(DATE_PICKER);
    }

    private Calendar getExpectedCalendar(String dateString) {
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(parseDateString(dateString,
                MONTH_DAY_YEAR_FORMAT));
        return expectedCalendar;
    }

    private void navigateToExpectedMonth(Calendar expectedCalendar) {
        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTime(getActualTime());
        do {
            switchMonth(expectedCalendar, actualCalendar);
        } while (isNotMonthAndYearAreEquals(expectedCalendar, actualCalendar));
    }

    private void switchMonth(Calendar expectedCalendar, Calendar
            actualCalendar) {
        if (actualCalendar.after(expectedCalendar)) {
            clickPrevArrow();
        } else if (actualCalendar.before(expectedCalendar)) {
            clickNextArrow();
        }
        actualCalendar.setTime(getActualTime());
    }

    private void clickPrevArrow() {
        datePicker.findElement(PREV_BUTTON).click();
    }

    private void clickNextArrow() {
        datePicker.findElement(NEXT_BUTTON).click();
    }

    private WebElement getDayToSelect(Calendar calendar) {
        return datePicker.findElement(By.xpath("" +
                ".//*[@data-handler='selectDay']/a[text()='" + calendar.get
                (Calendar.DAY_OF_MONTH) + "']"));
    }

    private boolean isNotMonthAndYearAreEquals(Calendar expected, Calendar
            actual) {
        return isNotEqualsFields(expected, actual, Calendar.YEAR) ||
                isNotEqualsFields(expected, actual, Calendar.MONTH);
    }

    private boolean isNotEqualsFields(Calendar expectedCalendar, Calendar
            actualCalendar, int fieldId) {
        return actualCalendar.get(fieldId) != expectedCalendar.get(fieldId);
    }

    private Date getActualTime() {
        return parseDateString(getCurrentDateAsString(),
                MONTH_DAY_YEAR_EXTENDED_FORMAT);
    }

    private Date parseDateString(String dateAsString, DateFormat format) {
        try {
            return format.parse(dateAsString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date parsing failed "
                    + dateAsString);
        }
    }

    private String getCurrentDateAsString() {
        return getTextInDatePicker(MONTH) + "/01/" + getTextInDatePicker(YEAR);
    }

    private String getTextInDatePicker(By identity) {
        return datePicker.findElement(identity).getText();
    }

}