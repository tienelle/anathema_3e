package net.sf.anathema.character.main.view;

import net.sf.anathema.framework.environment.ConfigurableDummyObjectFactory;
import net.sf.anathema.hero.application.SubViewMap;
import net.sf.anathema.hero.individual.view.SubViewFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SubViewMapTest {

  private ConfigurableDummyObjectFactory factory = new ConfigurableDummyObjectFactory();

  @Test
  public void containsFactoryForRegisteredViewClass() throws Exception {
    DummyView expectation = new DummyView();
    factory.add(SubViewFactory.class, new ConfigurableViewFactory(expectation));
    DummyView result = new SubViewMap(factory).get(DummyView.class);
    assertThat(result, is(expectation));
  }
}