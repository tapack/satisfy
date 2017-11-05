package io.tapack.satisfy.steps.spi;

public interface AssertionSteps extends AcceptableByIdentity {

    void identityContainsText(String text);

    void identityDoesNotContainText(String text);

}
