package io.tapack.util;

import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils
            .class);

    private FileUtils() {
    }

    public static File getFileFromResourcesByFilePath(String filePath) {
        URL fileUrl = FileUtils.class.getResource(filePath);
        String fileName = fileUrl.getFile();
        if (isFileInJar(fileName)) {
            return createTemporaryFile(fileName, fileUrl);
        }
        return new File(fileName);
    }

    private static boolean isFileInJar(String file) {
        return file.contains(".jar!/");
    }

    private static File createTemporaryFile(String fileName, URL fileUrl) {
        try {
            File tempFile = new File(Files.createTempDir(), FilenameUtils
                    .getName(fileUrl.getFile()));
            IOUtils.copy(fileUrl.openStream(), org.apache.commons.io
                    .FileUtils.openOutputStream(tempFile));
            return tempFile;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return new File(fileName);
        }
    }

    public static InputStream getStreamFromResourcesByFilePath(String filePath) {
        return FileUtils.class.getResourceAsStream(filePath);
    }

}
