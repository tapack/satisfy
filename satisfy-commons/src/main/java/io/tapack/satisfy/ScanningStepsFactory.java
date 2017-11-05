package io.tapack.satisfy;

import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.SatisfyStepFactory;
import net.thucydides.core.steps.StepFactory;
import org.jbehave.core.annotations.*;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.AbstractStepsFactory;
import org.junit.After;
import org.junit.Before;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScanningStepsFactory extends AbstractStepsFactory {

    private final Set<Class<?>> types = new HashSet<>();
    private final StepFactory stepFactory;
    private String matchingRegex = ".*";
    private String notMatchingRegex = "";

    public ScanningStepsFactory(Configuration configuration,
                                Class<?> root, Pages pages) {
        super(configuration);
        types.addAll(scanTypes(root.getPackage().getName()));
        stepFactory = new SatisfyStepFactory(pages);
    }


    public ScanningStepsFactory matchingNames(String matchingRegex) {
        this.matchingRegex = matchingRegex;
        return this;
    }

    public ScanningStepsFactory notMatchingNames(String notMatchingRegex) {
        this.notMatchingRegex = notMatchingRegex;
        return this;
    }

    private Set<Class<?>> scanTypes(String packageName) {
        Reflections reflections = new Reflections(packageName,
                new MethodAnnotationsScanner());
        Set<Class<?>> types = new HashSet<>();
        types.addAll(typesAnnotatedWith(reflections, Given.class));
        types.addAll(typesAnnotatedWith(reflections, When.class));
        types.addAll(typesAnnotatedWith(reflections, Then.class));
        types.addAll(typesAnnotatedWith(reflections, Before.class));
        types.addAll(typesAnnotatedWith(reflections, After.class));
        types.addAll(typesAnnotatedWith(reflections, BeforeScenario.class));
        types.addAll(typesAnnotatedWith(reflections, AfterScenario.class));
        types.addAll(typesAnnotatedWith(reflections, BeforeStory.class));
        types.addAll(typesAnnotatedWith(reflections, AfterStory.class));
        types.addAll(typesAnnotatedWith(reflections, BeforeStories.class));
        types.addAll(typesAnnotatedWith(reflections, AfterStories.class));
        types.addAll(typesAnnotatedWith(reflections, AsParameterConverter
                .class));
        return types;
    }

    private Set<Class<?>> typesAnnotatedWith(Reflections reflections,
                                             Class<? extends Annotation>
                                                     annotation) {
        Set<Class<?>> types = new HashSet<>();
        Set<Method> methodsAnnotatedWith;
        synchronized (ReflectionUtils.class) {
            methodsAnnotatedWith = reflections
                    .getMethodsAnnotatedWith(annotation);
        }
        for (Method method : methodsAnnotatedWith) {
            types.add(method.getDeclaringClass());
        }
        return types;
    }

    @Override
    protected List<Class<?>> stepsTypes() {
        List<Class<?>> matchingTypes = new ArrayList<>();
        for (Class<?> type : types) {
            String name = type.getName();
            if (name.matches(matchingRegex) && !name.matches
                    (notMatchingRegex)) {
                matchingTypes.add(type);
            }
        }
        return matchingTypes;
    }

    public Object createInstanceOfType(Class<?> type) {
        return stepFactory.instantiateNewStepLibraryFor(type);
    }
}
