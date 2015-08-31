package de.matrixweb.maven.plugins.renamefile;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "rename-file", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public final class RenameFileMojo extends AbstractMojo {

  @Parameter(property = "skip", defaultValue = "false")
  private Boolean skip;

  @Parameter(property = "source", defaultValue = "false")
  private File source;

  @Parameter(property = "target", defaultValue = "false")
  private File target;

  @Parameter(property = "workingDirectory", defaultValue = "${basedir}")
  private String workingDirectory;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (!skip) {
      source.renameTo(target);
    }
  }

}
