package io.tapack.satisfy.plugin;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.writer.FileCodeWriter;
import io.tapack.satisfy.ScanningJunitStory;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Goal which generates junit runner classes for each *.story file.
 */
@Mojo(name = "generate-test-runners", defaultPhase = LifecyclePhase
        .GENERATE_TEST_SOURCES)
public class StoryRunnersMojo
        extends AbstractMojo {
    /**
     * Directory with story files.
     */
    @Parameter(property = "project.stories.directory",
            defaultValue = "${project.basedir}/src/test/resources/stories",
            required = true)
    private File storiesDirectory;

    /**
     * Output directory for runners.
     */
    @Parameter(property = "project.junit.stories.directory",
            defaultValue = "${project.build.directory}"
                    + "/generated-test-sources/jbehave",
            required = true)
    private File outputDirectory;

    /**
     * Maven project instance.
     */
    @Component
    private MavenProject project;


    /**
     * Execute maven plugin goal.
     *
     * @throws MojoExecutionException maven plugin exception
     */
    public void execute() throws MojoExecutionException {
        Collection<File> files = FileUtils.listFiles(getStoriesDirectory(),
                new String[]{"story"}, true);

        JCodeModel codeModel = new JCodeModel();
        for (File storyFile : files) {
            try {
                String name = getClassNameFrom(storyFile.getName());
                JDefinedClass runnerClass = codeModel._class(
                        "io.tapack.satisfy." + name);
                runnerClass._extends(ScanningJunitStory.class);
                final JMethod runMethod = runnerClass
                        .method(JMod.PUBLIC, void.class, name);
                runMethod._throws(Throwable.class);
                runMethod.annotate(Test.class);
                runMethod.body().invoke("run");
            } catch (JClassAlreadyExistsException e) {
                getLog().error(e);
            }
        }
        File outputDir = getOutputDirectory();
        if (!outputDir.exists()
                && outputDir.mkdirs()) {
            outputDir = getOutputDirectory();
        }
        try {
            codeModel.build(new FileCodeWriter(outputDir));
        } catch (IOException e) {
            getLog().error(e);
        }
        addOutputToSourceRoot();
    }

    /**
     * Convert file name to class name.
     *
     * @param name of file
     * @return class name
     */
    public String getClassNameFrom(final String name) {
        int extensionIndex = name.lastIndexOf('.');
        String nameWithOutExtension = name.substring(0, extensionIndex);
        String[] words = nameWithOutExtension.split("_");
        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(Character.toTitleCase(word.charAt(0)))
                    .append(word.substring(1));
        }
        return builder.toString();
    }

    /**
     * Getter for stories directory.
     *
     * @return stories directory
     */
    public File getStoriesDirectory() {
        return storiesDirectory;
    }

    /**
     * Setter for stories directory.
     *
     * @param storiesDir directory
     */
    public void setStoriesDirectory(final File storiesDir) {
        this.storiesDirectory = storiesDir;
    }

    /**
     * Getter for output directory.
     *
     * @return runners directory
     */
    public File getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * Setter for output directory.
     *
     * @param outputDir directory
     */
    public void setOutputDirectory(final File outputDir) {
        this.outputDirectory = outputDir;
    }

    /**
     * Getter for maven project.
     *
     * @return maven project
     */
    public MavenProject getProject() {
        return project;
    }

    /**
     * Setter for maven project.
     *
     * @param mavenProject maven
     */
    public void setProject(final MavenProject mavenProject) {
        this.project = mavenProject;
    }

    /**
     * Add generated runners to test compile source root.
     */
    public void addOutputToSourceRoot() {
        File source = getOutputDirectory();
        getProject().addTestCompileSourceRoot(source.getAbsolutePath());
        if (getLog().isInfoEnabled()) {
            getLog().info("Test Source directory: " + source + " added.");
        }
    }
}
