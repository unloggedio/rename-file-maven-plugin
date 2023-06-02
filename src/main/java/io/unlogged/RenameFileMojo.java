package io.unlogged;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mojo(name = "rename-file", defaultPhase = LifecyclePhase.COMPILE)
public final class RenameFileMojo extends AbstractMojo {

    @Parameter(property = "skip", defaultValue = "false")
    private Boolean skip;

    @Parameter(property = "source", required = true)
    private String source;

    @Parameter(property = "target", required = true)
    private String target;

    @Parameter(property = "workingDirectory", defaultValue = "${basedir}")
    private String workingDirectory;

    @Override
    public void execute() throws MojoFailureException {
        if (skip) {
            return;
        }
        boolean recursive = false;
        if (workingDirectory.startsWith("**/")) {
            recursive = true;
            workingDirectory = workingDirectory.substring(3);
        }
        File directory = new File(workingDirectory);
        if (!directory.exists() || !directory.isDirectory()) {
            getLog().error("Working directory does not exist or is not a directory");
            throw new MojoFailureException(workingDirectory + " is not a directory");
        }
        getLog().info(
                "Rename files in [" + workingDirectory + "] recursive: [" + recursive +
                        "] from [" + source + "] to [" + target + "]");
        renameFiles(directory, Pattern.compile(source), target, recursive);
    }

    public void renameFiles(File workingDirectory, Pattern sourcePattern, String targetPattern, boolean recursive) {
        File[] fileList = workingDirectory.listFiles();
        if (fileList == null) {
            return;
        }
        for (File file : fileList) {
            if (file.isDirectory()) {
                if (recursive) {
                    renameFiles(file, sourcePattern, targetPattern, recursive);
                }
            } else {
                Matcher matcher = sourcePattern.matcher(file.getName());
                if (matcher.matches()) {
                    String newName = matcher.replaceAll(targetPattern);
                    String fileNewPath = file.getParentFile().getAbsolutePath() + File.separator + newName;
                    boolean renamed = file.renameTo(new File(fileNewPath));
                    if (!renamed) {
                        getLog().warn("Failed to rename [" + file.getAbsolutePath() + "] to [" + fileNewPath + "]");
                    }
                }
            }
        }
    }

}
