package io.tapack.satisfy.steps.spi;

public interface DropDownSteps extends AcceptableByIdentity {

    void whenSelectOptionFromDropDown(String option);

    void thenOptionIsSelectedFromDropDown(String option);

}
