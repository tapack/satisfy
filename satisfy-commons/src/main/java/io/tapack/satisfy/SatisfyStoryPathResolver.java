package io.tapack.satisfy;

import org.jbehave.core.Embeddable;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class SatisfyStoryPathResolver extends UnderscoredCamelCaseResolver {
    private String s;

    @Override
    protected String resolveDirectory(Class<? extends Embeddable>
                                              embeddableClass) {
        return "stories";
    }

    @Override
    protected String resolveName(Class<? extends Embeddable> embeddableClass) {
        final String name = super.resolveName(embeddableClass);
        s = name;
        final URL storiesFolder = Thread.currentThread()
                .getContextClassLoader().getResource("stories");
        try {
            final Path start = Paths.get(storiesFolder.toURI());
            final Path path = Files.walkFileTree(start, new
                    SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file,
                                                 BasicFileAttributes attrs)
                        throws IOException {
                    if (file.toFile().getName().contentEquals(name + "" +
                            ".story")) {
                        s = start.relativize(file).toString().replaceFirst
                                ("\\.story", "");
                        return FileVisitResult.TERMINATE;
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return s;
    }
}
