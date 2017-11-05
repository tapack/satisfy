package io.tapack.satisfy.steps.bdd;

import io.tapack.util.SessionVariablesUtils;
import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.model.ExamplesTable;


public class VariablesSteps {

    private static final long serialVersionUID = 1L;

    @AsParameterConverter
    public String getValueIfVariable(String variable) {
        String variableSubstitutedByValue = SessionVariablesUtils
                .substituteVariableByValue(variable);
        return SessionVariablesUtils.localize(variableSubstitutedByValue);
    }

    @AsParameterConverter
    public ExamplesTable getTableValueIfVariable(String variable) {
        String tableWithSubstitutedVariables = SessionVariablesUtils
                .substituteVariableByValue(variable);
        return new ExamplesTable(tableWithSubstitutedVariables);
    }


}
