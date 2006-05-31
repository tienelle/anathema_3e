package net.sf.anathema.character.generic.framework.magic.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.disy.commons.swing.action.SmartAction;
import net.disy.commons.swing.layout.GridDialogLayoutDataUtilities;
import net.disy.commons.swing.layout.grid.EndOfLineMarkerComponent;
import net.disy.commons.swing.layout.grid.GridDialogLayout;
import net.sf.anathema.lib.control.GenericControl;
import net.sf.anathema.lib.control.IClosure;
import net.sf.anathema.lib.gui.list.ComponentEnablingListSelectionListener;

public class MagicLearnView implements IMagicLearnView {

  private final GenericControl<IMagicViewListener> control = new GenericControl<IMagicViewListener>();
  private final JList learnOptionsList = new JList(new DefaultListModel());
  private final JList learnedList = new JList(new DefaultListModel());
  private final List<JButton> endButtons = new ArrayList<JButton>();
  private JPanel boxPanel;
  private JButton addButton;

  public void init(final IMagicLearnProperties properties) {
    learnOptionsList.setCellRenderer(properties.getAvailableMagicRenderer());
    learnOptionsList.setSelectionMode(properties.getAvailableListSelectionMode());
    learnedList.setCellRenderer(properties.getLearnedMagicRenderer());
    addButton = createAddMagicButton(properties.getAddButtonIcon(), properties.getAddButtonToolTip());
    addOptionListListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        addButton.setEnabled(properties.isMagicSelectionAvailable(learnOptionsList.getSelectedValue()));
      }
    });
    JButton removeButton = createRemoveMagicButton(
        properties.getRemoveButtonIcon(),
        properties.getRemoveButtonToolTip());
    endButtons.add(removeButton);
    addSelectionListListener(createLearnedListListener(removeButton, learnedList));
  }

  protected ListSelectionListener createLearnedListListener(JButton button, JList list) {
    return new ComponentEnablingListSelectionListener(button, list);
  }

  private JButton createAddMagicButton(Icon icon, String tooltip) {
    final SmartAction smartAction = new SmartAction(icon) {
      @Override
      protected void execute(Component parentComponent) {
        fireMagicAdded(learnOptionsList.getSelectedValues());
      }
    };
    return createButton(tooltip, smartAction);
  }

  private JButton createRemoveMagicButton(Icon icon, String tooltip) {
    final SmartAction smartAction = new SmartAction(icon) {
      @Override
      protected void execute(Component parentComponent) {
        fireMagicRemoved(learnedList.getSelectedValues());
      }
    };
    return createButton(tooltip, smartAction);
  }

  public JComboBox addFilterBox(String label, Object[] objects, ListCellRenderer renderer) {
    this.boxPanel = new JPanel(new GridDialogLayout(2, false));
    boxPanel.add(new JLabel(label));
    JComboBox box = new JComboBox(objects);
    box.setRenderer(renderer);
    boxPanel.add(box, GridDialogLayoutDataUtilities.createHorizontalFillNoGrab());
    return box;
  }

  private JButton createButton(String tooltip, final SmartAction smartAction) {
    smartAction.setEnabled(false);
    smartAction.setToolTipText(tooltip);
    return new JButton(smartAction);
  }

  private void fireMagicRemoved(final Object[] removedMagics) {
    control.forAllDo(new IClosure<IMagicViewListener>() {
      public void execute(IMagicViewListener input) {
        input.magicRemoved(removedMagics);
      }
    });
  }

  private void fireMagicAdded(final Object[] addedMagics) {
    control.forAllDo(new IClosure<IMagicViewListener>() {
      public void execute(IMagicViewListener input) {
        input.magicAdded(addedMagics);
      }
    });
  }

  public void setMagicOptions(Object[] magics) {
    exchangeObjects((DefaultListModel) learnOptionsList.getModel(), magics);
  }

  private void exchangeObjects(DefaultListModel listModel, Object[] magic) {
    listModel.clear();
    for (Object spell : magic) {
      listModel.addElement(spell);
    }
  }

  public void setLearnedMagic(Object[] magics) {
    exchangeObjects((DefaultListModel) learnedList.getModel(), magics);
  }

  public void addMagicViewListener(IMagicViewListener listener) {
    control.addListener(listener);
  }

  public JButton addAdditionalAction(Action action) {
    JButton button = new JButton(action);
    endButtons.add(button);
    return button;
  }

  /** Takes up 4 columns in GridDialogLayouted-Panel */
  public void addTo(JPanel panel) {
    if (boxPanel != null) {
      panel.add(boxPanel);
      panel.add(new EndOfLineMarkerComponent());
    }
    panel.add(createScrollPane(learnOptionsList));
    panel.add(addButton);
    panel.add(createScrollPane(learnedList));
    JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
    for (JButton button : endButtons) {
      buttonPanel.add(button);
    }
    panel.add(buttonPanel);
  }

  private JScrollPane createScrollPane(JList list) {
    JScrollPane scrollPane = new JScrollPane(list);
    scrollPane.setPreferredSize(new Dimension(200, 300));
    return scrollPane;
  }

  public ListModel getLearnedListModel() {
    return learnedList.getModel();
  }

  public void clearSelection() {
    learnedList.clearSelection();
  }

  public void addSelectionListListener(ListSelectionListener listener) {
    learnedList.addListSelectionListener(listener);
  }

  public void addOptionListListener(ListSelectionListener listener) {
    learnOptionsList.addListSelectionListener(listener);
  }
}