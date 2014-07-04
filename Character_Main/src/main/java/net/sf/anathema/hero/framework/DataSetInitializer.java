package net.sf.anathema.hero.framework;

import net.sf.anathema.ProxySplashscreen;
import net.sf.anathema.hero.framework.data.IExtensibleDataSetCompiler;
import net.sf.anathema.hero.framework.data.IExtensibleDataSetProvider;
import net.sf.anathema.initialization.ExtensibleDataSetCompiler;
import net.sf.anathema.library.initialization.InitializationException;
import net.sf.anathema.library.initialization.ObjectFactory;
import net.sf.anathema.library.logging.Logger;
import net.sf.anathema.library.resources.ResourceFile;
import net.sf.anathema.library.resources.ResourceLoader;

import java.util.Collection;
import java.util.Set;

public class DataSetInitializer {

  private static final Logger logger = Logger.getLogger(DataSetInitializer.class);
  private final ResourceLoader resourceLoader;
  private final ObjectFactory objectFactory;

  public DataSetInitializer(ResourceLoader resourceLoader, ObjectFactory objectFactory) {
    this.resourceLoader = resourceLoader;
    this.objectFactory = objectFactory;
  }

  public IExtensibleDataSetProvider initializeExtensibleResources() throws InitializationException {
    ExtensibleDataManager manager = new ExtensibleDataManager();
    Collection<IExtensibleDataSetCompiler> compilers = objectFactory.instantiateOrdered(ExtensibleDataSetCompiler.class, objectFactory);
    for (IExtensibleDataSetCompiler compiler : compilers) {
      try {
        ProxySplashscreen.getInstance().displayStatusMessage("Compiling " + compiler.getName() + "...");
        getDataFilesFromReflection(compiler);
        manager.addDataSet(compiler.build());
      } catch (Exception e) {
        String message = handleCompilationException(compiler, e);
        throw new InitializationException(message, e);
      }
    }
    return manager;
  }

  private void getDataFilesFromReflection(IExtensibleDataSetCompiler compiler) throws Exception {
    Set<ResourceFile> files = resourceLoader.getResourcesMatching(compiler.getRecognitionPattern());
    logger.info(compiler.getName() + ": Found " + files.size() + " data files.");
    for (ResourceFile file : files) {
      compiler.registerFile(file);
    }
  }

  private String handleCompilationException(IExtensibleDataSetCompiler compiler, Exception e) {
    StringBuilder message = new StringBuilder("Could not compile ");
    message.append(compiler.getName());
    Throwable cause = e.getCause();
    while (cause != null) {
      message.append(":");
      message.append("\n");
      String messagePart = cause.getMessage();
      if (messagePart.contains("Nested")) {
        int nested = messagePart.indexOf("Nested");
        messagePart = messagePart.substring(0, nested);
      }
      message.append(messagePart);
      cause = cause.getCause();
    }
    return message.toString();
  }
}