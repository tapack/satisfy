package io.tapack.satisfy;

import io.tapack.util.SessionVariablesUtils;
import org.junit.Assert;
import org.junit.Test;

public class SessionVariableUtilsTest {

    @Test
    public void saveVariableAndGetItFromSession() {
        Object value = new Object();
        String key = "new variable";
        SessionVariablesUtils.save(key, value);
        Assert.assertEquals(value, SessionVariablesUtils.getVariable(key));
    }
}
