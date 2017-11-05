package io.tapack.satisfy.steps.spi;


public interface DragNDropSteps extends AcceptableByIdentity {

    void whenSelectElementAndMoveTo(int x, int y);

    void thenCheckElementHasCorrectCoordinate(int x, int y);
}
