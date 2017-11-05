package io.tapack.satisfy.steps.spi;

public interface FakeEmailServerRunner extends LoadAcceptor {
    void startAllFakeLocalServers();
}