package io.tapack.satisfy.steps.file;

import com.google.common.io.Files;
import io.tapack.satisfy.props.SatisfyWebProperties;
import io.tapack.satisfy.props.WebProperty;
import io.tapack.satisfy.steps.spi.UploadSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import net.thucydides.core.pages.components.FileToUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BaseFileUploadSteps implements UploadSteps {
    private static final Logger LOG = LoggerFactory.getLogger
            (BaseFileUploadSteps.class);

    private final WebPage page;
    private By fileInput;

    public BaseFileUploadSteps() {
        page = WebStepsFactory.getPage();
    }

    public BaseFileUploadSteps(By identity) {
        this();
        fileInput = identity;
    }

    @Override
    public void uploadFileToFileInput(String filePath) {
        String localFilePath = getFileFromResourcesByFilePath(filePath)
                .getAbsolutePath();// find file in own class loader
        LOG.info("UPLOAD FILE_PATH = " + localFilePath);
        new FileToUpload(localFilePath).useRemoteDriver(isRemoteDriver()).to
                (page.getDriver().findElement(fileInput));
    }

    @Override
    public boolean accept(By identity) {
        fileInput = identity;
        String type = page.element(fileInput).getAttribute("type");
        return type != null && type.equalsIgnoreCase("file");
    }

    private boolean isRemoteDriver() {
        return SatisfyWebProperties.getSatisfyWebProperties().isDefined
                (WebProperty.WEBDRIVER_REMOTE_URL);
    }

    private File getFileFromResourcesByFilePath(String filePath) {
        URL url = BaseFileUploadSteps.class.getResource(filePath);
        String file = url.getFile();
        if (file.contains(".jar!/")) {
            try {
                File tempFile = new File(Files.createTempDir(), FilenameUtils
                        .getName(url.getFile()));
                IOUtils.copy(url.openStream(), org.apache.commons.io
                        .FileUtils.openOutputStream(tempFile));
                return tempFile;
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return new File(file);
    }
}