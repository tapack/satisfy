package io.tapack.satisfy.spi;

public interface LoadAcceptor {

    boolean accept(Class<? extends LoadAcceptor> loadclass);
}
