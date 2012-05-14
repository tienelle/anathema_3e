/**
 * Copyright (C) 2005, 2011 disy Informationssysteme GmbH and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package net.disy.commons.swing.dialog.foldout;

import net.disy.commons.swing.dialog.userdialog.UserDialog;
import net.sf.anathema.lib.gui.action.IActionConfiguration;
import net.sf.anathema.lib.gui.action.MnemonicLabel;
import net.sf.anathema.lib.gui.action.MnemonicLabelParser;
import net.sf.anathema.lib.lang.StringUtilities;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FoldOutDialog extends UserDialog {
  private JButton foldOutButton;
  private boolean isFoldedOut = false;
  private JPanel foldOutPanel;

  public FoldOutDialog(final Component parent, final IFoldOutDialogConfiguration userDialog) {
    super(parent, userDialog);
    isFoldedOut = userDialog.isInitiallyFoldedOut();
    updateResizeable();
  }

  @Override
  protected boolean isMainContentGrabVerticalSpace() {
    // for fold out dialogs the folded out content shall be resized instead of the main content 
    return false;
  }

  private IFoldOutDialogConfiguration getFoldOutUserDialog() {
    return (IFoldOutDialogConfiguration) getConfiguration();
  }

  @Override
  protected JComponent[] createAdditionalButtons() {
    foldOutButton = new JButton();
    foldOutButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        toggleFoldOut();
      }
    });
    updateButtonText();
    return new JComponent[]{ foldOutButton };
  }

  private void toggleFoldOut() {
    isFoldedOut = !isFoldedOut;
    foldOutPanel.setVisible(isFoldedOut);
    updateButtonText();
    updateResizeable();
    if (isFoldedOut) {
      getFoldOutUserDialog().getFoldOutPage().requestFocus();
    }
    else {
      getConfiguration().getDialogPage().requestFocus();
    }
    getDialog().pack();
  }

  private void updateResizeable() {
    getDialog().setResizable(isFoldedOut);
  }

  private void updateButtonText() {
    if (isFoldedOut) {
      configure(foldOutButton, getFoldOutUserDialog().getFoldInButtonConfiguration());
    }
    else {
      configure(foldOutButton, getFoldOutUserDialog().getFoldOutButtonConfiguration());
    }
  }

  private static void configure(final JButton button, final IActionConfiguration actionConfiguration) {
    final MnemonicLabel label = MnemonicLabelParser.parse(actionConfiguration.getName());
    button.setText(label.getPlainText());
    if (label.getMnemonicCharacter() != null) {
      button.setMnemonic(label.getMnemonicCharacter().charValue());
    }
    final String toolTipText = actionConfiguration.getToolTipText();
    button.setToolTipText(StringUtilities.isNullOrEmpty(toolTipText) ? null : toolTipText);
    button.setIcon(actionConfiguration.getIcon());
  }

  @Override
  protected JComponent createOptionalBelowButtonsPanel() {
    final JComponent foldOutContent = getFoldOutUserDialog().getFoldOutPage().getContent();
    foldOutPanel = new JPanel(new BorderLayout());
    foldOutPanel.setVisible(false);
    foldOutPanel.setBorder(BorderFactory.createEmptyBorder(10, 8, 0, 8));
    foldOutPanel.add(foldOutContent, BorderLayout.CENTER);
    return foldOutPanel;
  }
}