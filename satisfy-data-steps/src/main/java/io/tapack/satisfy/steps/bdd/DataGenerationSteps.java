package io.tapack.satisfy.steps.bdd;


import io.tapack.util.RandomDataUtils;
import io.tapack.util.SessionVariablesUtils;
import io.tapack.util.State;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.annotations.Given;

import java.util.UUID;

public class DataGenerationSteps {

    @Given("generated random alphabetic string with length '$length' and saved to '$name' variable")
    public void givenGeneratedRandomAlphabeticStringSavedToVariable(int length, String name) {
        SessionVariablesUtils.save(name, RandomStringUtils.randomAlphabetic
                (length));
    }

    @Given("generated random numerical string with length '$length' and saved to '$name' variable")
    public void givenGeneratedRandomNumericalStringSavedToVariable(int length, String name) {
        SessionVariablesUtils.save(name, RandomStringUtils.randomNumeric
                (length));
    }

    @Given("generated random alphanumerical string with length '$length' and saved to '$name' variable")
    public void givenGeneratedRandomAlphanumericStringSavedToVariable(int length, String name) {
        SessionVariablesUtils.save(name, RandomStringUtils.randomAlphanumeric
                (length));
    }

    @Given("random USA state name saved to '$name' variable")
    public void givenRandomUSAStateSavedToVariable(String name) {
        SessionVariablesUtils.save(name, State.getRandomState().getName());
    }

    @Given("random USA state abbreviation saved to '$name' variable")
    public void givenRandomUSAStateAbbreviationSavedToVariable(String name) {
        SessionVariablesUtils.save(name, State.getRandomState()
                .getAbbreviation());
    }

    @Given("random email saved to '$name' variable")
    public void givenRandomEmailSavedToVariable(String name) {
        SessionVariablesUtils.save(name, RandomDataUtils.getRandomEmail());
    }

    @Given("random SSN saved to '$name' variable")
    public void givenRandomSSNSavedToVariable(String name) {
        SessionVariablesUtils.save(name, RandomDataUtils.getRandomSsn());
    }

    @Given("random phone number saved to '$name' variable")
    public void givenRandomPhoneNumberSavedToVariable(String name) {
        SessionVariablesUtils.save(name, RandomDataUtils.getRandomPhone());
    }

    @Given("unique string with prefix '$prefix' and '$length' length saved to '$name' variable")
    public void givenUniqueStringWithPrefixAndLengthSavedToVariable(String prefix, int length, String name) {
        String uniqueString = String.valueOf(UUID.randomUUID()).replace("-",
                "");
        String textElement = StringUtils.rightPad(prefix, length, uniqueString);
        SessionVariablesUtils.save(name, textElement);
    }

}

