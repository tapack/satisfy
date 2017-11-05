package io.tapack.satisfy.steps.bdd;

import io.tapack.satisfy.email.props.PropertyAccessor;
import org.jbehave.core.annotations.Given;

public class EmailStepsUtilsBDD {

    @Given("send protocol '$testSendProtocol' and receive protocol '$testReceiveProtocol'")
    public void configureSendAndReceiveProtocols(String sendProtocol, String receiveProtocol){
        PropertyAccessor.setSendingProtocol(sendProtocol);
        PropertyAccessor.setReceiveProtocol(receiveProtocol);
    }

}