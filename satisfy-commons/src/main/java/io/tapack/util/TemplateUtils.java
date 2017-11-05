package io.tapack.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.thucydides.core.Thucydides;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class TemplateUtils {

    public static File processTemplate(File template) throws IOException, TemplateException {
        Configuration config = new Configuration();
        config.setDirectoryForTemplateLoading(template.getParentFile());
        config.setObjectWrapper(new DefaultObjectWrapper());
        config.setDefaultEncoding("UTF-8");
        Template temp = config.getTemplate(template.getName());
        String child = template.getName() + RandomStringUtils.randomAlphanumeric(8);
        File fileOutput = new File(template.getParentFile(), child);
        Writer fileWriter = new FileWriter(fileOutput);
        Map<Object, Object> currentSession = Thucydides.getCurrentSession();
        temp.process(currentSession, fileWriter);
        return fileOutput;
    }
}
