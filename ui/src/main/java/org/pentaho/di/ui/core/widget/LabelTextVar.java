/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.di.ui.core.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.ui.core.PropsUI;

/**
 * Adds a line of text with a label and a variable to a composite (like a dialog shell)
 *
 * @author Matt
 * @since 17-may-2006
 *
 */
public class LabelTextVar extends Composite {
  private static final PropsUI props = PropsUI.getInstance();

  private Label wLabel;
  private TextVar wText;

  public LabelTextVar( VariableSpace space, Composite composite, String labelText, String toolTipText ) {
    this( space, composite, SWT.NONE, labelText, toolTipText );
  }

  public LabelTextVar( VariableSpace space, Composite composite, String labelText, String toolTipText, boolean passwordField ) {
    this( space, composite, SWT.NONE, labelText, toolTipText, passwordField );
  }

  public LabelTextVar( VariableSpace space, Composite composite, int flags, String labelText, String toolTipText ) {
    this( space, composite, flags, labelText, toolTipText, false );
  }
  public LabelTextVar( VariableSpace space, Composite composite, int flags, String labelText, String toolTipText,
      boolean passwordField ) {
    super( composite, SWT.NONE );
    props.setLook( this );

    int middle = props.getMiddlePct();
    int margin = Const.MARGIN;

    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = 0;
    formLayout.marginHeight = 0;
    formLayout.marginTop = 0;
    formLayout.marginBottom = 0;

    this.setLayout( formLayout );

    int textFlags = SWT.SINGLE | SWT.LEFT | SWT.BORDER;
    if ( flags != SWT.NONE ) {
      textFlags = flags;
    }

    if ( passwordField ) {
      wText = new PasswordTextVar( space, this, textFlags, toolTipText );
    } else {
      wText = new TextVar( space, this, textFlags, toolTipText );
    }
    FormData fdText = new FormData();
    fdText.left = new FormAttachment( middle, margin );
    fdText.right = new FormAttachment( 100, 0 );
    wText.setLayoutData( fdText );
    wText.getTextWidget().setToolTipText( toolTipText );

    wLabel = new Label( this, SWT.RIGHT );
    props.setLook( wLabel );
    wLabel.setText( labelText );
    FormData fdLabel = new FormData();
    fdLabel.left = new FormAttachment( 0, 0 );
    fdLabel.right = new FormAttachment( middle, 0 );
    fdLabel.top = new FormAttachment( wText, 0, SWT.CENTER );
    wLabel.setLayoutData( fdLabel );
    wLabel.setToolTipText( toolTipText );
  }

  public void addModifyListener( ModifyListener lsMod ) {
    wText.addModifyListener( lsMod );
  }

  public void addSelectionListener( SelectionAdapter lsDef ) {
    wText.addSelectionListener( lsDef );
  }

  public void setText( String name ) {
    wText.setText( name );
  }

  public String getText() {
    return wText.getText();
  }

  public void setEchoChar( char c ) {
    wText.setEchoChar( c );
  }

  public void setEnabled( boolean flag ) {
    wText.setEnabled( flag );
    wLabel.setEnabled( flag );
  }

  public boolean setFocus() {
    return wText.setFocus();
  }

  public void addTraverseListener( TraverseListener tl ) {
    wText.addTraverseListener( tl );
  }

  public Text getTextWidget() {
    return wText.getTextWidget();
  }

  public Label getLabelWidget() {
    return wLabel;
  }
}
