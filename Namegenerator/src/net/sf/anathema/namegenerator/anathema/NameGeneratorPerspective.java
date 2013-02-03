package net.sf.anathema.namegenerator.anathema;

import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.framework.view.perspective.Container;
import net.sf.anathema.framework.view.perspective.Perspective;
import net.sf.anathema.framework.view.perspective.PerspectiveAutoCollector;
import net.sf.anathema.framework.view.perspective.PerspectiveToggle;
import net.sf.anathema.initialization.reflections.ReflectionObjectFactory;
import net.sf.anathema.initialization.reflections.Weight;
import net.sf.anathema.lib.resources.IResources;
import net.sf.anathema.namegenerator.exalted.ExaltedNameGeneratorModel;
import net.sf.anathema.namegenerator.presenter.NameGeneratorPresenter;
import net.sf.anathema.namegenerator.presenter.model.INameGeneratorModel;
import net.sf.anathema.namegenerator.presenter.view.INameGeneratorView;
import net.sf.anathema.namegenerator.view.NameGeneratorView;

import javax.swing.Icon;

@PerspectiveAutoCollector
@Weight(weight = 7000)
public class NameGeneratorPerspective implements Perspective {

  @Override
  public void configureToggle(PerspectiveToggle toggle) {
    toggle.setTooltip("ItemType.NameGenerator.PrintName");
    toggle.setIcon("TabNamegenerator116.png");
  }

  @Override
  public void initContent(Container container, IAnathemaModel model, IResources resources, ReflectionObjectFactory objectFactory) {
    NamegeneratorUI ui = new NamegeneratorUI(resources);
    Icon icon = ui.getNameGeneratorTabIcon();
    INameGeneratorView view = new NameGeneratorView();
    INameGeneratorModel generatorModel = new ExaltedNameGeneratorModel();
    new NameGeneratorPresenter(resources, view, generatorModel).initPresentation();
    DelegatingItemView itemView = new DelegatingItemView("PrintName", icon, view);
    container.setSwingContent(itemView.getComponent());
  }
}
