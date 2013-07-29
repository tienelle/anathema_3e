package net.sf.anathema.platform.tree.display;

import net.sf.anathema.framework.ui.Coordinate;
import net.sf.anathema.framework.ui.RGBColor;
import net.sf.anathema.platform.tree.display.transform.AgnosticTransform;
import net.sf.anathema.platform.tree.display.transform.CenterOn;
import net.sf.anathema.platform.tree.display.transform.Scale;
import net.sf.anathema.platform.tree.display.transform.ScaleVisitor;
import net.sf.anathema.platform.tree.display.transform.TransformOperation;
import net.sf.anathema.platform.tree.display.transform.Translation;
import net.sf.anathema.platform.tree.view.draw.GraphicsElement;
import net.sf.anathema.platform.tree.view.draw.InteractiveGraphicsElement;
import net.sf.anathema.platform.tree.view.interaction.Executor;
import net.sf.anathema.platform.tree.view.interaction.MouseClickClosure;
import net.sf.anathema.platform.tree.view.interaction.MouseMotionClosure;
import net.sf.anathema.platform.tree.view.interaction.MousePressClosure;
import net.sf.anathema.platform.tree.view.interaction.MouseWheelClosure;
import net.sf.anathema.platform.tree.view.interaction.PolygonPanel;
import net.sf.anathema.platform.tree.view.interaction.SpecialControlTrigger;

public class AgnosticPolygonPanel implements PolygonPanel {
  private static final double MAX_ZOOM_OUT_SCALE = 0.3d; //30%
  private static final double MAX_ZOOM_IN_SCALE = 1.5d; //150%

  private final DisplayPolygonPanel panel;
  private final AgnosticTransform transform = new AgnosticTransform();

  public AgnosticPolygonPanel(DisplayPolygonPanel polygonPanel) {
    this.panel = polygonPanel;
  }

  @Override
  public void refresh() {
    panel.refresh();
  }

  @Override
  public SpecialControlTrigger addSpecialControl() {
    return panel.addSpecialControl();
  }

  @Override
  public void add(InteractiveGraphicsElement element) {
    panel.add(element);
  }

  @Override
  public void add(GraphicsElement element) {
    panel.add(element);
  }

  @Override
  public void scale(double scale) {
    executeScaleIfBoundsAreNotBroken(new Scale(scale));
    updateDisplayTransformation();
  }

  private void updateDisplayTransformation() {
    panel.setTransformation(transform);
  }

  @Override
  public void scaleToPoint(double scale, Coordinate coordinate) {
    Translation operation = new Translation(coordinate.x, coordinate.y);
    Scale operation1 = new Scale(scale);
    Translation operation2 = new Translation(-coordinate.x, -coordinate.y);
    executeScaleIfBoundsAreNotBroken(operation, operation1, operation2);
    updateDisplayTransformation();
  }

  @Override
  public void translate(int x, int y) {
    transform.add(new Translation(x, y));
    updateDisplayTransformation();
  }

  @Override
  public void translateRelativeToScale(int x, int y) {
    Translation translation = createTranslationRelativeToScale(x, y);
    transform.add(translation);
    updateDisplayTransformation();
  }

  private Translation createTranslationRelativeToScale(int x, int y) {
    double scale = getScale(transform);
    return new Translation(x / scale, y / scale);
  }


  @Override
  public void resetTransformation() {
    transform.setToIdentity();
    updateDisplayTransformation();
  }

  @Override
  public void changeCursor(Coordinate screenCoordinates) {
    panel.changeCursor(screenCoordinates);
  }

  @Override
  public void clear() {
    panel.clear();
  }

  @Override
  public Executor onElementAtPoint(Coordinate screenCoordinates) {
    return panel.onElementAtPoint(screenCoordinates);
  }

  @Override
  public void centerOn(Coordinate coordinate) {
    int xCenter = panel.getWidth() / 2;
    int yCenter = panel.getHeight() / 2;
    int newCenterX = xCenter - coordinate.x;
    int newCenterY = yCenter - coordinate.y;
    transform.add(new CenterOn(newCenterX, newCenterY));
    updateDisplayTransformation();
  }

  @Override
  public void addMousePressListener(final MousePressClosure listener) {
    panel.addMousePressListener(listener);
  }

  @Override
  public void addMouseClickListener(final MouseClickClosure listener) {
    panel.addMouseClickListener(listener);
  }

  @Override
  public void addMouseWheelListener(final MouseWheelClosure listener) {
    panel.addMouseWheelListener(listener);
  }

  @Override
  public void setToolTipText(String toolTip) {
    panel.setToolTipText(toolTip);
  }

  @Override
  public void addMouseMotionListener(final MouseMotionClosure listener) {
    panel.addMouseMotionListener(listener);
  }

  @Override
  public void setBackground(RGBColor color) {
    panel.setBackground(color);
  }

  @Override
  public void showMoveCursor() {
    panel.showMoveCursor();
  }

  private void executeScaleIfBoundsAreNotBroken(TransformOperation... operations) {
    AgnosticTransform copy = transform.createCopy();
    copy.preconcatenate(operations);
    double newScale = getScale(copy);
    boolean isScaleAllowed = MAX_ZOOM_OUT_SCALE <= newScale && newScale <= MAX_ZOOM_IN_SCALE;
    if (isScaleAllowed) {
      transform.preconcatenate(operations);
    }
  }

  private double getScale(AgnosticTransform transform) {
    ScaleVisitor visitor = new ScaleVisitor();
    transform.visitOperations(visitor);
    return visitor.getScale();
  }
}