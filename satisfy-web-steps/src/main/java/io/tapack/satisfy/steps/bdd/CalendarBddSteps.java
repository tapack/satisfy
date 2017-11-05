package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;

public class CalendarBddSteps {

    @When("choose date '$date' in '$identity' calendar")
    public void whenChooseDate(String date, By identity) {
        WebStepsFactory.getCalendarSteps(identity).fillDateIntoCalendarField
                (date);
    }

    @When("choose date '$date' in '$identity' date picker")
    public void whenChooseDateFromDatePicker(String date, By identity) {
        WebStepsFactory.getCalendarSteps(identity).selectDateFromPicker(date);
    }

    @Then("selected date '$date' appears in the '$identity' calendar")
    public void thenSelectedDateAppearsInTheCalendar(String date, By identity) {
        WebStepsFactory.getCalendarSteps(identity)
                .verifyDateAppearedInCalendarField(date);
    }

}