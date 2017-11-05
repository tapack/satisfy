package io.tapack.satisfy.steps.spi;

public interface UploadSteps extends AcceptableByIdentity {

    void uploadFileToFileInput(String filePath);

}