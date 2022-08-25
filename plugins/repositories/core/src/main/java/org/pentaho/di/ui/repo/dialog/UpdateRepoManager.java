package org.pentaho.di.ui.repo.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.json.simple.JSONObject;
import org.pentaho.di.ui.core.PropsUI;
import org.pentaho.di.ui.core.gui.GUIResource;

public class UpdateRepoManager extends Shell {
	private Text text;
	private Text text_1;
	private Text text_2;
	private static final Image LOGO = GUIResource.getInstance().getImageLogoSmall();
	private PropsUI props;
	boolean defaultflag;

	public void updateRepo( JSONObject selectedRepoDetails ) {
		try {
			Display display = Display.getDefault();
			UpdateRepoManager shell = new UpdateRepoManager( display, selectedRepoDetails );
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UpdateRepoManager( Display display, JSONObject selectedRepoDetails ) {
		super(display, SWT.SHELL_TRIM );
		this.props = PropsUI.getInstance();

		Label lblRepoName = new Label( this, SWT.NONE );
		lblRepoName.setBounds( 10, 10, 240, 25 );
		lblRepoName.setText( "Display name" );
		props.setLook( lblRepoName );

		text = new Text( this, SWT.BORDER );
		text.setBounds( 10, 41, 353, 31 );
		props.setLook( text );
		text.setText( selectedRepoDetails.get( "displayName" ).toString() );
		text.setEditable( false );


		Label lblRepoUrl = new Label( this, SWT.NONE );
		lblRepoUrl.setBounds(10, 90, 81, 25 );
		lblRepoUrl.setText( "URL" );
		props.setLook( lblRepoUrl );

		text_1 = new Text( this, SWT.BORDER );
		text_1.setBounds( 10, 121, 353, 31 );
		props.setLook( text_1 );
		text_1.setText( selectedRepoDetails.get( "url" ).toString() );


		Label lblDescription = new Label( this, SWT.NONE );
		lblDescription.setBounds( 10, 169, 174, 25 );
		lblDescription.setText( "Description" );
		props.setLook( lblDescription );

		text_2 = new Text(this, SWT.BORDER | SWT.WRAP  | SWT.MULTI);
		text_2.setBounds(10, 209, 353, 79 );
		props.setLook( text_2 );
		text_2.setText( selectedRepoDetails.get( "description" ).toString() );


		Button btnCreate = new Button(this, SWT.NONE);
		btnCreate.setBounds(10, 387, 105, 35);
		btnCreate.setText(" update ");
		props.setLook( btnCreate );

		Button btnHelp = new Button(this, SWT.NONE);
		btnHelp.setBounds(289, 529, 105, 35);
		btnHelp.setText(" help ");
		props.setLook( btnHelp );

		Button btnCheckoxdefault = new Button(this, SWT.CHECK);
		btnCheckoxdefault.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				if (btnCheckoxdefault.getSelection())
					defaultflag=true;
				else
					defaultflag=false;
			}
		});
		btnCheckoxdefault.setBounds(10, 313, 297, 25);
		btnCheckoxdefault.setText("Launch connection on startup");
		props.setLook( btnCheckoxdefault );

		setText("Update repository");
		setSize(429, 634);
		setImage( LOGO );
		setBackground( new Color( getShell().getDisplay(), 255, 255, 255 ) );

	}


	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
