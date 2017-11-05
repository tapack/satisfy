package io.tapack.satisfy.steps.spi;

public interface DownloadSteps extends AcceptableByIdentity {

    void downloadFile(String fileName);

}