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


package org.pentaho.di.ui.core.database.wizard;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.ui.core.PropsUI;

/**
 *
 * On page one we select the database connection Oracle specific settings 1) The data tablespace 2) The index tablespace
 *
 * @author Matt
 * @since 04-apr-2005
 */
public class CreateDatabaseWizardPageOracle extends WizardPage {
  private static Class<?> PKG = CreateDatabaseWizard.class; // for i18n purposes, needed by Translator2!!

  private Label wlDataTS;
  private Text wDataTS;
  private FormData fdlDataTS, fdDataTS;

  private Label wlIndexTS;
  private Text wIndexTS;
  private FormData fdlIndexTS, fdIndexTS;

  private PropsUI props;
  private DatabaseMeta info;

  public CreateDatabaseWizardPageOracle( String arg, PropsUI props, DatabaseMeta info ) {
    super( arg );
    this.props = props;
    this.info = info;

    setTitle( BaseMessages.getString( PKG, "CreateDatabaseWizardPageOracle.DialogTitle" ) );
    setDescription( BaseMessages.getString( PKG, "CreateDatabaseWizardPageOracle.DialogMessage" ) );

    setPageComplete( false );
  }

  public void createControl( Composite parent ) {
    int margin = Const.MARGIN;
    int middle = props.getMiddlePct();

    // create the composite to hold the widgets
    Composite composite = new Composite( parent, SWT.NONE );
    props.setLook( composite );

    FormLayout compLayout = new FormLayout();
    compLayout.marginHeight = Const.FORM_MARGIN;
    compLayout.marginWidth = Const.FORM_MARGIN;
    composite.setLayout( compLayout );

    wlDataTS = new Label( composite, SWT.RIGHT );
    wlDataTS.setText( BaseMessages.getString( PKG, "CreateDatabaseWizardPageOracle.DataTablespace.Label" ) );
    props.setLook( wlDataTS );
    fdlDataTS = new FormData();
    fdlDataTS.top = new FormAttachment( 0, 0 );
    fdlDataTS.left = new FormAttachment( 0, 0 );
    fdlDataTS.right = new FormAttachment( middle, 0 );
    wlDataTS.setLayoutData( fdlDataTS );
    wDataTS = new Text( composite, SWT.SINGLE | SWT.BORDER );
    props.setLook( wDataTS );
    fdDataTS = new FormData();
    fdDataTS.top = new FormAttachment( 0, 0 );
    fdDataTS.left = new FormAttachment( middle, margin );
    fdDataTS.right = new FormAttachment( 100, 0 );
    wDataTS.setLayoutData( fdDataTS );
    wDataTS.addModifyListener( new ModifyListener() {
      public void modifyText( ModifyEvent arg0 ) {
        setPageComplete( false );
      }
    } );

    wlIndexTS = new Label( composite, SWT.RIGHT );
    wlIndexTS.setText( BaseMessages.getString( PKG, "CreateDatabaseWizardPageOracle.IndexTableSpace.Label" ) );
    props.setLook( wlIndexTS );
    fdlIndexTS = new FormData();
    fdlIndexTS.top = new FormAttachment( wDataTS, margin );
    fdlIndexTS.left = new FormAttachment( 0, 0 );
    fdlIndexTS.right = new FormAttachment( middle, 0 );
    wlIndexTS.setLayoutData( fdlIndexTS );
    wIndexTS = new Text( composite, SWT.SINGLE | SWT.BORDER );
    props.setLook( wIndexTS );
    fdIndexTS = new FormData();
    fdIndexTS.top = new FormAttachment( wDataTS, margin );
    fdIndexTS.left = new FormAttachment( middle, margin );
    fdIndexTS.right = new FormAttachment( 100, 0 );
    wIndexTS.setLayoutData( fdIndexTS );
    wIndexTS.addModifyListener( new ModifyListener() {
      public void modifyText( ModifyEvent arg0 ) {
        setPageComplete( false );
      }
    } );

    // set the composite as the control for this page
    setControl( composite );
  }

  public boolean canFlipToNextPage() {
    getDatabaseInfo();
    setErrorMessage( null );
    setMessage( BaseMessages.getString( PKG, "CreateDatabaseWizardPageOracle.Message.Next" ) );
    return true;
  }

  public DatabaseMeta getDatabaseInfo() {
    if ( wDataTS.getText() != null && wDataTS.getText().length() > 0 ) {
      info.setDataTablespace( wDataTS.getText() );
    }

    if ( wIndexTS.getText() != null && wIndexTS.getText().length() > 0 ) {
      info.setIndexTablespace( wIndexTS.getText() );
    }

    return info;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
   */
  public IWizardPage getNextPage() {
    IWizard wiz = getWizard();
    return wiz.getPage( "2" );
  }
}
