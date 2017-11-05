package net.thucydides.junit.listeners;

import com.google.inject.Key;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.Listeners;
import net.thucydides.core.steps.SatisfyBaseStepListener;
import net.thucydides.core.steps.StepListener;
import net.thucydides.junit.guice.JUnitInjectors;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.junit.runners.model.InitializationError;

public class SatisfyThucydidesRunner extends ThucydidesRunner {

    public SatisfyThucydidesRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    protected JUnitStepListener initListenersUsing(final Pages pageFactory) {
        return new JUnitStepListener(getTestClass().getJavaClass(),
                new SatisfyBaseStepListener(getConfiguration()
                        .getOutputDirectory(), pageFactory),
                Listeners.getLoggingListener(),
                newTestCountListener());
    }

    protected JUnitStepListener initListeners() {
        return new JUnitStepListener(getTestClass().getJavaClass(),
                new SatisfyBaseStepListener(getConfiguration()
                        .getOutputDirectory()),
                Listeners.getLoggingListener(),
                newTestCountListener());
    }


    private StepListener newTestCountListener() {
        return JUnitInjectors.getInjector().getInstance(Key.get(StepListener
                .class, TestCounter.class));
    }
}
