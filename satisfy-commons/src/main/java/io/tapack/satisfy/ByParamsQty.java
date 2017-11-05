package io.tapack.satisfy;

import org.jbehave.core.steps.StepCandidate;
import org.jbehave.core.steps.StepFinder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom prioritizing strategy by quantity of parameters.
 */
public class ByParamsQty implements StepFinder.PrioritisingStrategy {

    public final List<StepCandidate> prioritise(String stepAsText,
                                          List<StepCandidate> candidates) {
        return candidates.stream().sorted((e1, e2) -> Integer.compare(
                e1.getMethod().getParameterTypes().length,
                e2.getMethod().getParameterTypes().length))
                .collect(Collectors.toList());
    }

}
