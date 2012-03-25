package net.sf.anathema.character.generic.impl.bootjob;

import net.sf.anathema.ProxySplashscreen;
import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.framework.view.IAnathemaView;
import net.sf.anathema.initialization.BootJob;
import net.sf.anathema.initialization.IAnathemaBootJob;
import net.sf.anathema.lib.resources.IResources;

import java.io.File;

@BootJob
public class RepositoryCleanup implements IAnathemaBootJob {
  @Override
  public void run(IResources resources, IAnathemaModel model, IAnathemaView view) {
    if (!new File(model.getRepository().getRepositoryPath()).exists()) {
      return;
    }
    ProxySplashscreen.getInstance().displayStatusMessage(
            resources.getString("Character.Bootjob.Cleanup.Splashmessage")); //$NON-NLS-1$
    new RepositoryBackup().backupRepository(resources, model);
    new FirstEditionDeleter(model).actOnAllCharacters();
    //new CharacterTransformer(model, new TemplateTransformer()).actOnAllCharacters();
  }
}