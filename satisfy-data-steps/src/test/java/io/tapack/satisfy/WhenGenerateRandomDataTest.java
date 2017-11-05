package io.tapack.satisfy;

import io.tapack.satisfy.steps.bdd.DataGenerationSteps;
import io.tapack.satisfy.steps.bdd.VariablesSteps;
import io.tapack.util.State;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.*;

@RunWith(ThucydidesRunner.class)
public class WhenGenerateRandomDataTest {

    @Steps
    public VariablesSteps variablesSteps;

    @Steps
    public DataGenerationSteps dataGenerationSteps;

    @Test
    public void useGeneratedRandomAlphabeticString() {
        String alphabetic = "Alphabetic";
        dataGenerationSteps
                .givenGeneratedRandomAlphabeticStringSavedToVariable(10,
                        alphabetic);
        String alphabeticValue = variablesSteps.getValueIfVariable("${" +
                alphabetic + "}");
        Assert.assertThat(alphabeticValue, instanceOf(String.class));
        Assert.assertThat(alphabeticValue.length(), equalTo(10));
    }

    @Test
    public void useGeneratedRandomAlphanumericString() {
        String alphanumeric = "Alphanumeric";
        dataGenerationSteps
                .givenGeneratedRandomAlphanumericStringSavedToVariable(9,
                        alphanumeric);
        String alphanumericValue = variablesSteps.getValueIfVariable("${" +
                alphanumeric + "}");
        Assert.assertThat(alphanumericValue, instanceOf(String.class));
        Assert.assertThat(alphanumericValue.length(), equalTo(9));
    }

    @Test
    public void useGeneratedRandomNumericalString() {
        String numerical = "Numerical";
        dataGenerationSteps
                .givenGeneratedRandomNumericalStringSavedToVariable(8,
                        numerical);
        String numericalValue = variablesSteps.getValueIfVariable("${" +
                numerical + "}");
        Assert.assertThat(numericalValue, instanceOf(String.class));
        Assert.assertThat(numericalValue.length(), equalTo(8));
    }

    @Test
    public void useGeneratedRandomEmail() {
        String email = "new email";
        dataGenerationSteps.givenRandomEmailSavedToVariable(email);
        String emailValue = variablesSteps.getValueIfVariable("${" + email +
                "}");
        Assert.assertThat(emailValue, instanceOf(String.class));
        Assert.assertThat(emailValue.length(), equalTo(19));
    }

    @Test
    public void useGeneratedRandomSSN() {
        String ssn = "new ssn";
        dataGenerationSteps.givenRandomSSNSavedToVariable(ssn);
        String ssnValue = variablesSteps.getValueIfVariable("${" + ssn + "}");
        Assert.assertThat(ssnValue, instanceOf(String.class));
        Assert.assertThat(ssnValue.length(), equalTo(11));
    }

    @Test
    public void useGeneratedRandomPhoneNumber() {
        String phoneNumber = "new phone";
        dataGenerationSteps.givenRandomPhoneNumberSavedToVariable(phoneNumber);
        String phoneNumberValue = variablesSteps.getValueIfVariable("${" +
                phoneNumber + "}");
        Assert.assertThat(phoneNumberValue, instanceOf(String.class));
        Assert.assertThat(phoneNumberValue.length(), equalTo(13));
    }

    @Test
    public void useGeneratedRandomUSAState() {
        String state = "new state";
        dataGenerationSteps.givenRandomUSAStateSavedToVariable(state);
        String stateValue = variablesSteps.getValueIfVariable("${" + state +
                "}");
        Assert.assertThat(State.valueOfName(stateValue), isOneOf(State.values
                ()));
    }

    @Test
    public void useGeneratedRandomUSAStateAbbreviation() {
        String stateAbbreviation = "new stateAbbreviation abbreviation";
        dataGenerationSteps.givenRandomUSAStateAbbreviationSavedToVariable
                (stateAbbreviation);
        String stateAbbreviationValue = variablesSteps.getValueIfVariable
                ("${" + stateAbbreviation + "}");
        Assert.assertThat(State.valueOfAbbreviation(stateAbbreviationValue),
                isOneOf(State.values()));
    }
}
