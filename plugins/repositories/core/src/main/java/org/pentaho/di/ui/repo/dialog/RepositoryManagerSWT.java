package org.pentaho.di.ui.repo.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.json.simple.JSONObject;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.ui.core.PropsUI;
import org.pentaho.di.ui.core.gui.GUIResource;
import org.pentaho.di.ui.repo.controller.RepositoryConnectController;
import java.util.List;

public class RepositoryManagerSWT extends Dialog {

	private Display display;
	private Shell shell;
	private PropsUI props;
	private static Class<?> PKG = RepositoryConnectionSWT.class;
	private static final String MANAGER_TITLE = BaseMessages.getString( PKG, "RepositoryDialog.Dialog.Manager.Title" );
	private LogChannelInterface log =
			KettleLogStore.getLogChannelInterfaceFactory().create( RepositoryManagerSWT.class );
	private static final Image LOGO = GUIResource.getInstance().getImageLogoSmall();

	public RepositoryManagerSWT( Shell shell) {
		super( shell, SWT.NONE );
		this.props = PropsUI.getInstance();
	}
	/**
	 * Launch the application.
	 *
	 */
	public  void createDialog() {
		Shell parent = getParent();
		display = parent.getDisplay();

		shell = new Shell( parent, SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX );
		props.setLook( shell );
		shell.setLayout( new FormLayout() );
		shell.setText( MANAGER_TITLE );
		shell.setImage( LOGO );

		try {

			Composite composite = new Composite( shell, SWT.COLOR_GRAY );
			composite.setBounds( 10, 10, 940, 582 );

			Label lblExistingRepos = new Label( composite, SWT.BOLD );
			lblExistingRepos.setBounds( 268, 21, 157, 25 );
			lblExistingRepos.setText( "Existing repositories" );


			List<JSONObject> repoLists = RepositoryConnectController.getInstance().getRepositories();


			Table table = new Table( composite, SWT.MULTI | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL );
			table.setHeaderVisible( true );
			table.setLinesVisible( true );
			table.setBounds( 15, 60, 652, 496 );
			int columnCount = 3;
			TableColumn column_repoName= new TableColumn( table, SWT.NONE );
				TableColumn column_repoURL =  new TableColumn( table, SWT.NONE );
				TableColumn column_repoDesc = new TableColumn( table, SWT.NONE );
			column_repoName.setText( " repo name " );
			column_repoURL.setText( " repo url " );
			column_repoDesc.setText( " repo desc " );

			int itemCount = repoLists.size();
			for ( int i = 0; i < itemCount; i++ ) {
				TableItem item = new TableItem( table, SWT.NONE );

				item.setText( new String[] {
					repoLists.get( i ).get("displayName"  ).toString(),
					repoLists.get( i ).get( "url" ).toString(),
					 repoLists.get( i ).get( "description").toString()
					 });
			}

			Listener paintListener = new Listener() {
				public void handleEvent( Event event) {
					switch ( event.type ) {
						case SWT.MeasureItem: {
							TableItem item = ( TableItem ) event.item;
							String text = getText( item, event.index );
							Point size = event.gc.textExtent( text );
							event.width = size.x;
							event.height = Math.max( event.height, size.y );
							break;
						}
						case SWT.PaintItem: {
							TableItem item = ( TableItem ) event.item;
							String text = getText( item, event.index );
							Point size = event.gc.textExtent( text );
							int offset2 = event.index == 0 ? Math.max( 0, ( event.height - size.y ) / 2 ) : 0;
							event.gc.drawText( text, event.x, event.y + offset2, true );
							break;
						}
						case SWT.EraseItem: {
							event.detail &= ~SWT.FOREGROUND;
							break;
						}
					}
				}

				String getText( TableItem item, int column ) {
					String text = item.getText( column );
					if ( column != 0 ) {
						int index = table.indexOf( item );
						if ( ( index + column ) % 3 == 1 ) {
							text += "\n";
						}
						if ( ( index + column ) % 3 == 2 ) {
							text += "\n\n";
						}
					}
					return text;
				}
			};
			table.addListener( SWT.MeasureItem, paintListener );
			table.addListener( SWT.PaintItem, paintListener );
			table.addListener( SWT.EraseItem, paintListener );
			for ( int i = 0; i < columnCount; i++ ) {
				table.getColumn( i ).pack();
			}

			Button btnCreateNew = new Button( composite, SWT.NONE );
			btnCreateNew.setBounds( 720, 126, 100, 35 );
			btnCreateNew.setText("  Create new  ");
			props.setLook( btnCreateNew );

			Button btnUpdate = new Button( composite, SWT.NONE );
			btnUpdate.setBounds( 720, 183, 100, 35 );
			btnUpdate.setText( "   Update   " );
			props.setLook( btnUpdate );

			Button btnDelete = new Button( composite, SWT.NONE );
			btnDelete.setBounds( 720, 251, 100, 35 );
			btnDelete.setText( "  Delete   " );
			props.setLook( btnDelete );

			//********** button implementation for create new repo ***************
			btnCreateNew.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					new CreateRepoManager( shell.getDisplay()).createNewRepo();
				}
			});

			//********** button implementation for update repo ***************
			btnUpdate.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {

					int i = table.getSelectionIndex();
					if( i < 0 ){
						MessageBox messageBox = new MessageBox( getParent().getShell(), SWT.OK |
							SWT.ICON_ERROR | SWT.CANCEL );
						messageBox.setMessage( "select a repository to update" );
						messageBox.open();
					}
					String selectedRepoName = table.getItem(i).getText();
					JSONObject selectedRepoNameJSON = RepositoryConnectController.getInstance().getRepository( selectedRepoName );

					new UpdateRepoManager( shell.getDisplay(), selectedRepoNameJSON ).updateRepo( selectedRepoNameJSON);
				}
			} );

			btnDelete.addSelectionListener( new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {

					int i = table.getSelectionIndex( );
					if ( i < 0 ) {
						MessageBox messageBox = new MessageBox( getParent().getShell(), SWT.OK | SWT.ICON_ERROR | SWT.CANCEL );
						messageBox.setMessage( "select a repository to delete" );
						messageBox.open();
					}
					String selectedRepoName = table.getItem( i ).getText();
					JSONObject selectedRepoDetails = RepositoryConnectController.getInstance( ).getRepository( selectedRepoName );

					new DeleteRepoManager( shell.getDisplay(), selectedRepoDetails ).deleteRepository( selectedRepoDetails );
				}
			} );
			shell.pack();
			shell.setMinimumSize( 900, 700 );
			shell.open();
			while (!shell.isDisposed() ) {
				if ( !display.readAndDispatch() ) {
					display.sleep();
				}
			}
		} catch ( Exception e ) {
			log.logError("Error ", e );
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
