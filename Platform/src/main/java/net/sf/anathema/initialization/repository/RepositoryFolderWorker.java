package net.sf.anathema.initialization.repository;

import net.sf.anathema.framework.module.preferences.CanonicalPathResolver;
import net.sf.anathema.library.exception.PersistenceException;

import java.io.File;

import static java.text.MessageFormat.format;

public class RepositoryFolderWorker {

  public File createFolder(File folder) {
    try {
      create(folder);
      return folder;
    } catch (PersistenceException e) {
      String message = format("Could not create {0}:", folder.getAbsolutePath());
      throw new PersistenceException(message, e);
    }
  }

  private void create(File folder) {
    IOFileSystemAbstraction fileSystem = new IOFileSystemAbstraction();
    new RepositoryFolderCreator(fileSystem, new CanonicalPathResolver(folder)).createRepositoryFolder();
  }
}