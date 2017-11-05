package io.tapack.satisfy.steps.file;

import io.tapack.satisfy.steps.spi.DownloadSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import net.thucydides.core.Thucydides;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BaseFileDownloadSteps implements DownloadSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger
            (BaseFileDownloadSteps.class);
    private final WebPage page;
    private By linkIdentity;

    public BaseFileDownloadSteps() {
        page = WebStepsFactory.getPage();
    }

    public BaseFileDownloadSteps(By identity) {
        this();
        linkIdentity = identity;
    }

    @Override
    public void downloadFile(String fileName) {
        String href = page.element(linkIdentity).getAttribute("href");
        if (isNotAnchorLink(href)) {
            downloadFileFromURL(fileName, href);
        }
    }

    @Override
    public boolean accept(By identity) {
        linkIdentity = identity;
        return isNotAnchorLink(page.element(identity).getAttribute("href"));
    }

    private void downloadFileFromURL(String fileKey, String href) {
        try {
            File downloadedFile = File.createTempFile("download", ".tmp");
            FileUtils.copyURLToFile(new URL(href), downloadedFile);
            Thucydides.getCurrentSession().put(fileKey, downloadedFile);
            LOGGER.info("Downloaded = " + downloadedFile.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private boolean isNotAnchorLink(String href) {
        return href != null && !href.equals(page.getDriver().getCurrentUrl()
                + "#");
    }

}