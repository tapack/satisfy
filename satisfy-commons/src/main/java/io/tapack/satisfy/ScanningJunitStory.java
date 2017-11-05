package io.tapack.satisfy;

import com.google.common.util.concurrent.MoreExecutors;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.junit.listeners.SatisfyThucydidesRunner;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.NullEmbedderMonitor;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.reporters.ConsoleOutput;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.StepFinder;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.util.Collections;

import static io.tapack.util.SatisfyProperties.getSatisfyProperties;

@RunWith(SatisfyThucydidesRunner.class)
public class ScanningJunitStory extends ConfigurableEmbedder {

    @Managed
    public WebDriver webdriver;

    @ManagedPages
    public Pages pages;

    private MarkUnmatchedStepsAsPending stepCollector = new
            MarkUnmatchedStepsAsPending(new StepFinder(new ByParamsQty()));

    @Override
    public Embedder configuredEmbedder() {
        StepEventBus.getEventBus().setUniqueSession(true);
        Embedder embedder = super.configuredEmbedder();
        embedder.useExecutorService(MoreExecutors.sameThreadExecutor());
        embedder.configuration().useStepCollector(stepCollector);
        embedder.useStepsFactory(new ScanningStepsFactory(embedder
                .configuration(), this.getClass(), pages));
        embedder.configuration()
                .useParameterControls(new ParameterControls()
                        .useDelimiterNamedParameters(true));
        embedder.configuration()
                .storyReporterBuilder()
                .withReporters(new SatisfyStoryReporter()
                        , new ConsoleOutput());
        embedder.useEmbedderMonitor(new NullEmbedderMonitor());
        embedder.useMetaFilters(getSatisfyProperties().getMetaFilters());
        return embedder;
    }

    public void run() throws Throwable {
        Embedder embedder = configuredEmbedder();
        embedder.configuration().useStoryPathResolver(new
                SatisfyStoryPathResolver());
        StoryPathResolver pathResolver = embedder.configuration()
                .storyPathResolver();
        String storyPath = pathResolver.resolve(this.getClass());
        try {
            embedder.runStoriesAsPaths(Collections.singletonList(storyPath));
        } finally {
            embedder.generateCrossReference();
        }
    }

}
