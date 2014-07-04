package net.sf.anathema.character.equipment.creation.presenter;

import net.sf.anathema.library.event.IntValueChangedListener;
import net.sf.anathema.library.number.IntegerRange;
import org.jmock.example.announcer.Announcer;

public class RangedIntValueModel implements IIntValueModel {

  private final IntegerRange range;
  private final Announcer<IntValueChangedListener> valueControl = Announcer.to(IntValueChangedListener.class);
  private int value;

  public RangedIntValueModel(int initialValue) {
    this(new IntegerRange(Integer.MIN_VALUE, Integer.MAX_VALUE), initialValue);
  }

  public RangedIntValueModel(IntegerRange range, int initialValue) {
    this.value = initialValue;
    this.range = range;
  }

  @Override
  public final void addIntValueChangeListener(IntValueChangedListener changeListener) {
    valueControl.addListener(changeListener);
  }

  public IntegerRange getRange() {
    return range;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  public void setValue(int value) {
    value = range.getNearestValue(value);
    if (this.value == value) {
      return;
    }
    this.value = value;
    valueControl.announce().valueChanged(value);
  }

  @Override
  public Integer getMaximum() {
    return range.getUpperBound();
  }

  @Override
  public Integer getMinimum() {
    return range.getLowerBound();
  }
}