package io.tapack.satisfy.steps.spi;

import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.By;

public interface TableSteps extends AcceptableByIdentity {

    void inTableClickOnIdentityInRowsWithParameters(By elementIdentity,
                                                    ExamplesTable params);

    void tableContainsRowWithParameters(ExamplesTable params);

    void tableDoesNotContainRowWithParameters(ExamplesTable params);

}
