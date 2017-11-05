package io.tapack.satisfy.steps.spi;

interface LoadAcceptor {
    boolean accept(String acceptanceParam);
}