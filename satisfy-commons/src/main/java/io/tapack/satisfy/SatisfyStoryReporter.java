package io.tapack.satisfy;

import net.thucydides.core.model.TestTag;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.steps.StepFailure;
import org.jbehave.core.model.*;
import org.jbehave.core.reporters.StoryReporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.thucydides.core.model.TestTag.withName;

public class SatisfyStoryReporter implements StoryReporter {

    private static final String OPEN_PARAM_CHAR = "\uff5f";
    private static final String CLOSE_PARAM_CHAR = "\uff60";
    private boolean isScenarioWithFailure = false;
    private boolean isStoryWithFailure = false;
    private boolean isStoryWithPending = false;

    @Override
    public void storyNotAllowed(Story story, String filter) {

    }

    @Override
    public void storyCancelled(Story story, StoryDuration storyDuration) {

    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {

    }

    @Override
    public void afterStory(boolean givenStory) {

    }

    @Override
    public void narrative(Narrative narrative) {

    }

    @Override
    public void lifecyle(Lifecycle lifecycle) {

    }

    @Override
    public void scenarioNotAllowed(Scenario scenario, String filter) {
        StepEventBus.getEventBus().updateCurrentStepTitle(normalized(scenario.getTitle()));
        StepEventBus.getEventBus().stepIgnored();
        List<TestTag> tags = new ArrayList<>();
        tags.add(withName(filter).andType("Stories with skipped scenario"));
        StepEventBus.getEventBus().addTagsToCurrentTest(tags);
    }

    @Override
    public void beforeScenario(String scenarioTitle) {
        if (!scenarioTitle.isEmpty()) {
            StepEventBus.getEventBus().stepStarted(ExecutedStepDescription
                    .withTitle("Scenario: " + scenarioTitle));
        }
    }

    @Override
    public void scenarioMeta(Meta meta) {

    }

    @Override
    public void afterScenario() {
        if (aStepInScenarioFailed()) {
            StepEventBus.getEventBus().stepIgnored();
        } else if (aPreviousStepHasSuspended()) {
            StepEventBus.getEventBus().stepIgnored();
        } else {
            StepEventBus.getEventBus().stepFinished();
        }
    }

    @Override
    public void givenStories(GivenStories givenStories) {

    }

    @Override
    public void givenStories(List<String> storyPaths) {

    }

    @Override
    public void beforeExamples(List<String> steps, ExamplesTable table) {

    }

    @Override
    public void example(Map<String, String> tableRow) {

    }

    @Override
    public void afterExamples() {

    }

    public void beforeStep(String stepTitle) {
        if (aPreviousStepHasFailed() || aPreviousStepHasSuspended()) {
            StepEventBus.getEventBus().stepStarted(ExecutedStepDescription
                    .withTitle(stepTitle));
        }
    }

    public void successful(String title) {
        if (aPreviousStepHasFailed() || aPreviousStepHasSuspended()) {
            StepEventBus.getEventBus().updateCurrentStepTitle(normalized
                    (title));
            StepEventBus.getEventBus().stepIgnored();
        }
    }

    public void ignorable(String title) {
        StepEventBus.getEventBus().updateCurrentStepTitle(normalized(title));
        StepEventBus.getEventBus().stepIgnored();
    }

    public void pending(String stepTitle) {
        if (StepEventBus.getEventBus().aStepInTheCurrentTestHasFailed()) {
            StepEventBus.getEventBus().stepStarted(ExecutedStepDescription
                    .withTitle(normalized(stepTitle)));
            StepEventBus.getEventBus().stepIgnored();
        } else {
            StepEventBus.getEventBus().stepStarted(ExecutedStepDescription
                    .withTitle(normalized(stepTitle)));
            StepEventBus.getEventBus().stepPending();
        }
    }

    public void notPerformed(String stepTitle) {
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription
                .withTitle(normalized(stepTitle)));
        StepEventBus.getEventBus().stepIgnored();
    }

    public void failed(String stepTitle, Throwable cause) {
        Throwable rootCause = cause.getCause() != null ? cause.getCause() :
                cause;
        StepEventBus.getEventBus().stepStarted(ExecutedStepDescription
                .withTitle(normalized(stepTitle)));
        StepEventBus.getEventBus().stepFailed(new StepFailure
                (ExecutedStepDescription.withTitle(normalized(stepTitle))
                        , rootCause));
        isStoryWithFailure = true;
    }

    @Override
    public void failedOutcomes(String step, OutcomesTable table) {

    }

    @Override
    public void restarted(String step, Throwable cause) {

    }

    @Override
    public void dryRun() {

    }

    @Override
    public void pendingMethods(List<String> methods) {

    }

    private String normalized(String value) {
        return value.replaceAll(OPEN_PARAM_CHAR, "{").replaceAll
                (CLOSE_PARAM_CHAR, "}");
    }

    private boolean aPreviousStepHasFailed() {
        boolean aPreviousStepHasFailed = false;
        if (StepEventBus.getEventBus().aStepInTheCurrentTestHasFailed()) {
            if (isStoryWithFailure) {
                aPreviousStepHasFailed = true;
            } else {
                isStoryWithFailure = true;
            }
        }
        return aPreviousStepHasFailed;
    }

    private boolean aPreviousStepHasSuspended() {
        boolean aPreviousStepHasSuspended = false;
        if (StepEventBus.getEventBus().currentTestIsSuspended()) {
            if (isStoryWithPending) {
                aPreviousStepHasSuspended = true;
            } else {
                isStoryWithPending = true;
            }
        }
        return aPreviousStepHasSuspended;
    }


    private boolean aStepInScenarioFailed() {
        boolean aPreviousStepHasFailed = false;
        if (StepEventBus.getEventBus().aStepInTheCurrentTestHasFailed()) {
            if (isScenarioWithFailure) {
                aPreviousStepHasFailed = true;
            } else {
                isScenarioWithFailure = true;
            }
        }
        return aPreviousStepHasFailed;
    }

}
