package io.tapack.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class SatisfyProperties {
    public static final String METAFILTER = "metafilter";
    private static SatisfyProperties customProperties = new
            SatisfyProperties();
    private final EnvironmentVariables environmentVariables;

    public SatisfyProperties() {
        this(Injectors.getInjector().getInstance(EnvironmentVariables.class));
    }

    @Inject
    public SatisfyProperties(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public static SatisfyProperties getSatisfyProperties() {
        return customProperties;
    }

    public boolean isDefined(String name) {
        return !isEmpty(environmentVariables.getProperty(name));
    }

    public String getProperty(String name) {
        return environmentVariables.getProperty(name);
    }

    public List<String> getMetaFilters() {
        String metaFilters = EMPTY;
        if(isDefined(METAFILTER)) {
            metaFilters = getProperty(METAFILTER);
        }
        return Lists.newArrayList(Splitter.on(Pattern.compile(","))
                .trimResults().omitEmptyStrings().split(metaFilters));
    }

}
