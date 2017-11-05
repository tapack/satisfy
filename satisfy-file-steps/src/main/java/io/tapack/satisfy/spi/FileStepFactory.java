package io.tapack.satisfy.spi;

import io.tapack.satisfy.csv.CsvProvider;
import io.tapack.satisfy.pdf.PdfProvider;
import io.tapack.satisfy.xml.XmlProvider;

import java.util.ServiceLoader;

public class FileStepFactory {

    private FileStepFactory() {}

    public static FileHandler getXmlSteps(Class<? extends FileHandler> aClass) {
        ServiceLoader<? extends FileHandler> loader = ServiceLoader.load(aClass);
        for (FileHandler provider : loader) {
            if (provider.accept(aClass)) {
                return provider;
            }
        }
        return new XmlProvider();
    }

    public static FileHandler getPdfSteps(Class<? extends FileHandler> aClass) {
        ServiceLoader<? extends FileHandler> loader = ServiceLoader.load(aClass);
        for (FileHandler provider : loader) {
            if (provider.accept(aClass)) {
                return provider;
            }
        }
        return new PdfProvider();
    }

    public static FileHandler getCsvSteps(Class<? extends FileHandler> aClass) {
        ServiceLoader<? extends FileHandler> loader = ServiceLoader.load(aClass);
        for (FileHandler provider : loader) {
            if (provider.accept(aClass)) {
                return provider;
            }
        }
        return new CsvProvider();
    }
}
