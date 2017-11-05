package io.tapack.satisfy.steps.spi;

public interface CalendarSteps extends AcceptableByIdentity {

    void fillDateIntoCalendarField(String date);

    void selectDateFromPicker(String date);

    void verifyDateAppearedInCalendarField(String date);

}
