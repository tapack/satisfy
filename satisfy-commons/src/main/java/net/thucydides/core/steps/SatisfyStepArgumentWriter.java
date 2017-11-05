package net.thucydides.core.steps;

import org.apache.commons.lang3.ArrayUtils;
import org.jbehave.core.model.ExamplesTable;

public class SatisfyStepArgumentWriter {

    public static String readableFormOf(Object arg) {
        if (arg == null) {
            return "<null>";
        } else if (arg.getClass().isArray()) {
            return ArrayUtils.toString(arg);
        } else if (arg instanceof ExamplesTable) {
            return "\n" + ((ExamplesTable) arg).asString();
        } else {
            return arg.toString();
        }
    }
}
