package io.tapack.satisfy.steps.spi;

public interface RichEditorSteps extends AcceptableByIdentity {

    void fillEditorWithText(String text);

    void verifyEditorContainsText(String text);

    void verifyEditorNotContainsText(String text);

}