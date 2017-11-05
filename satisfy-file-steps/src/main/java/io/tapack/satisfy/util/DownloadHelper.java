package io.tapack.satisfy.util;

import net.thucydides.core.Thucydides;

import java.io.File;

public class DownloadHelper {

    private DownloadHelper(){}

    public static File getDownloadedFile(String key){
        File downloadedFile = (File) Thucydides.getCurrentSession().get(key);
        if (null == downloadedFile) {
            throw new AssertionError("COULD NOT FIND FILE BY KEY '" + key + "' in THUCYDIDES CURRENT SESSION ");
        }
        return downloadedFile;
    }
}
