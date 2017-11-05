package io.tapack.satisfy.util;

import com.google.common.io.Files;
import org.jbehave.core.model.ExamplesTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TableToCsv {

    private static final String CSV_SUFFIX = ".csv";
    private static final String NEW_LINE = "\n";
    private static final String COMMA = ",";

    public File create(String filename, ExamplesTable table) throws IOException {
        File dir = Files.createTempDir();
        File file = new File(dir, filename + CSV_SUFFIX);
        FileWriter writer = new FileWriter(file);
        writer.append(getCsvHeaders(table.getHeaders()))
                .append(getCsvBody(table.getHeaders(), table.getRows()));
        writer.flush();
        writer.close();
        return file;
    }

    private String getCsvHeaders(List<String> headers) {
        StringBuilder builder = new StringBuilder();
        for (String header : headers) {
            builder.append(header);
            if (isLastInRow(headers, header)) {
                builder.append(NEW_LINE);
            } else {
                builder.append(COMMA);
            }
        }
        return builder.toString();
    }

    private String getCsvBody(List<String> headers, List<Map<String, String>> rows) {
        StringBuilder builder = new StringBuilder();
        for (Map<String, String> row : rows) {
            for (String header : headers) {
                builder.append(row.get(header));
                if (isLastInRow(headers, header)) {
                    builder.append(NEW_LINE);
                } else {
                    builder.append(COMMA);
                }
            }
        }
        return builder.toString();
    }

    private boolean isLastInRow(List<String> row, String value) {
        return row.get(row.size() - 1).equals(value);
    }
}
