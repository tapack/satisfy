package io.tapack.satisfy.steps.spi;

import org.openqa.selenium.By;

public interface AcceptableByIdentity {

    boolean accept(By identity);

}
