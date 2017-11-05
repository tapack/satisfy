package net.thucydides.core.steps;

import net.thucydides.core.pages.Pages;

public class SatisfyStepFactory extends StepFactory {
    public SatisfyStepFactory(Pages pages) {
        super(pages);
    }

    @Override
    public <T> T instantiateNewStepLibraryFor(Class<T> scenarioStepsClass) {
        SatisfyStepInterceptor stepInterceptor = new SatisfyStepInterceptor
                (scenarioStepsClass);
        return instantiateNewStepLibraryFor(scenarioStepsClass,
                stepInterceptor);
    }
}
