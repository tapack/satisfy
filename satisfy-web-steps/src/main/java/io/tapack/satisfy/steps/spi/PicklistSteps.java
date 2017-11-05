package io.tapack.satisfy.steps.spi;

import java.util.List;

public interface PicklistSteps extends AcceptableByIdentity {

    void whenSelectFromPicklist(List<String> items);

    void whenDoubleClickOnItemFromPicklist(String item);

    void whenPressAddButtonInPicklist();

    void whenPressAddAllButtonInPicklist();

    void whenPressRemoveButtonInPicklist();

    void whenPressRemoveAllButtonInPicklist();

    void thenAppearedInPicklistSourceList(List<String> items);

    void thenAppearedInPicklistTargetList(List<String> items);

}