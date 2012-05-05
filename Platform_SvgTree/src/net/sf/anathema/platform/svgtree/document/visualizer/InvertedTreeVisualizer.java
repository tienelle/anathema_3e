package net.sf.anathema.platform.svgtree.document.visualizer;

import net.sf.anathema.graph.graph.LayeredGraph;
import net.sf.anathema.platform.svgtree.document.components.ILayer;
import net.sf.anathema.platform.svgtree.document.components.IVisualizableNode;

public class InvertedTreeVisualizer extends AbstractTreeVisualizer {

  public InvertedTreeVisualizer(LayeredGraph graph, ITreePresentationProperties properties,
                                VisualizedGraphFactory factory) {
    super(properties, graph, factory);
  }

  @Override
  protected IVisualizableNode[] getRelatives(IVisualizableNode node) {
    return node.getParents();
  }

  @Override
  protected ILayer getInitialLayer(ILayer[] layers) {
    return layers[layers.length - 1];
  }
}
