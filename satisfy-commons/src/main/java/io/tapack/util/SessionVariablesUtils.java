package io.tapack.util;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.thucydides.core.Thucydides.getCurrentSession;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.substringsBetween;

public final class SessionVariablesUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger
            (SessionVariablesUtils.class);
    private static final String PROPERTY_FILE_BASE_NAME = "base";
    private static final EnvironmentVariables ENVIRONMENT_VARIABLES = Injectors.getInjector().getInstance(EnvironmentVariables.class);

    private SessionVariablesUtils() {
    }

    public static void save(String key, Object value) {
        getCurrentSession().put(key, value);
        LOGGER.info("Session variable " + key + " = " + value + " was saved.");
    }

    public static Object getVariable(String name) {
        return getCurrentSession().get(name);
    }

    public static <T> T getVariableValue(String key) {
        return (T) getCurrentSession().get(key);
    }

    public static void removeVariable(String key) {
        getCurrentSession().remove(key);
    }

    public static String substituteVariableByValue(String templateString) {
        Map<String, String> vars = findPropsThatMatchVars(templateString);
        for (Object key : getCurrentSession().keySet()) {
            if (key instanceof String) {
                Object val = getCurrentSession().get(key);
                if (val instanceof String) {
                    vars.put((String) key, (String) val);
                }
            }
        }
        StrSubstitutor sub = new StrSubstitutor(vars);
        return sub.replace(templateString);
    }

    public static Map<String, String> findPropsThatMatchVars(String variable) {
        Map<String, String> props = new HashMap<>();
        String[] keys = substringsBetween(variable, "${", "}");
        if (keys == null) {
            return props;
        }
        for (String key : keys) {
            String property = ENVIRONMENT_VARIABLES.getProperty(key);
            if (!isEmpty(property)) {
                props.put(key, property);
            }
        }
        return props;
    }

    public static String localize(String text) {
        Pattern pattern = Pattern.compile("^@.*");
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            return getValueFromLocalePropertyFile(text);
        } else {
            return text;
        }
    }

    private static String getValueFromLocalePropertyFile(String localeVariableString) {
        Locale currentLocale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.
                getBundle(PROPERTY_FILE_BASE_NAME, currentLocale);
        String propertyName = localeVariableString.substring(1);
        return resourceBundle.getString(propertyName);
    }

}
