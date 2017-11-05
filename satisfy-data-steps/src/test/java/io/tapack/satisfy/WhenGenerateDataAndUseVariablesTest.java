package io.tapack.satisfy;

import io.tapack.satisfy.steps.bdd.DataGenerationSteps;
import io.tapack.satisfy.steps.bdd.VariablesSteps;
import io.tapack.util.SessionVariablesUtils;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

import static org.hamcrest.Matchers.*;

@RunWith(ThucydidesRunner.class)
public class WhenGenerateDataAndUseVariablesTest {

    @Managed
    public WebDriver driver;

    @ManagedPages
    public Pages pages;

    @Steps
    public VariablesSteps variablesSteps;

    @Steps
    public DataGenerationSteps dataGenerationSteps;

    @Test
    public void whenValueIsNotVariable() {
        Assert.assertEquals("John Doe", variablesSteps.getValueIfVariable
                ("John Doe"));
    }

    @Test
    public void whenValueIsVariable() {
        SessionVariablesUtils.save("new John", "John Doe");
        Assert.assertEquals("John Doe", variablesSteps.getValueIfVariable
                ("${new John}"));
    }

    @Test
    public void whenTableValueIfNotVariable() {
        String table = "|Table_Head2|Table_Head3|\r\n|Value2.2|Value3.3|";
        ExamplesTable expectedTable = new ExamplesTable(table);
        ExamplesTable actualTable = variablesSteps.getTableValueIfVariable
                (table);
        Assert.assertEquals(expectedTable.getRows(), actualTable.getRows());
    }

    @Test
    public void whenValueIsLocaleVariable() {
        Locale.setDefault(new Locale("es"));
        Assert.assertEquals("Cuál es tu nombre", variablesSteps
                .getValueIfVariable("@name"));
        Locale.setDefault(Locale.ENGLISH);
        Assert.assertEquals("What is your name", variablesSteps
                .getValueIfVariable("@name"));
    }

    @Test
    public void whenValueIsNotLocaleVariable() {
        Locale.setDefault(new Locale("es"));
        Assert.assertEquals("name", variablesSteps.getValueIfVariable("name"));
        Locale.setDefault(Locale.ENGLISH);
        Assert.assertEquals("name", variablesSteps.getValueIfVariable("name"));
    }

    @Test
    public void whenTableValueIfVariable() {
        SessionVariablesUtils.save("test", "Value3.3");
        String table = "|Table_Head2|Table_Head3|\r\n|Value2.2|Value3.3|";
        ExamplesTable examplesTable = new ExamplesTable(table);
        ExamplesTable actualTable = variablesSteps.getTableValueIfVariable
                ("|Table_Head2|Table_Head3|\r\n|Value2.2|${test}|");
        Assert.assertEquals(examplesTable.getRows(), actualTable.getRows());
    }

    @Test
    public void generateUniqueNameAndCreateMessageWithIt() {
        dataGenerationSteps
                .givenUniqueStringWithPrefixAndLengthSavedToVariable("John ",
                        10, "John Doe");
        Assert.assertThat(variablesSteps.getValueIfVariable("Hello ${John " +
                        "Doe}!"),
                both(startsWith("Hello John ")).and(endsWith("!")));
    }

}
