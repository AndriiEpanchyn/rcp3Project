package actions.EditMenu;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import dataModel.Node;
import dataModel.SessionManager;
import rcp3project.Activator;
import rcp3project.EditorInput;
import rcp3project.EmptyView;
import rcp3project.FormEditor;
import rcp3project.GetFolderNameTitleAreaDialog;
import rcp3project.NavigationView;

public class AddAction extends Action {
	IWorkbenchWindow window;
	private final String ACTION_ID = "AddAction";
	String newObjectName = "";

	public AddAction(IWorkbenchWindow win) {
		window = win;
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_ID);
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("/icons/addRecord16x16.png");
		setImageDescriptor(ImageDescriptor.createFromURL(fileURL));
	}

	@Override
	public void run() {
		IWorkbenchPage page = window.getActivePage();
		NavigationView navigationView = (NavigationView) (page.getViews()[0]);
		Node currentNode = navigationView.getCurrentRecord();

		if (currentNode == null || currentNode == SessionManager.getSession()) {
			currentNode = SessionManager.getSession().getChildren().get(0);

		}

		MessageDialog dialog = new MessageDialog(window.getShell(), "Create new object.", null,
				"Which object should be created? ", MessageDialog.CONFIRM, new String[] { "Folder", "Record", "None" },
				2);
		int result = dialog.open();
		if (result == 0) {// 0- Folder
			addFolder(page, currentNode);
		} else if (result == 1) {// 1- record
			addRecord(page, currentNode);
			navigationView.setExpandStatus(true);
		}
		navigationView.redrawTree();
	}

	private void addRecord(IWorkbenchPage page, Node currentNode) {
		Node newNode = null;
		GetFolderNameTitleAreaDialog dialog = new GetFolderNameTitleAreaDialog(window.getShell());
		dialog.create();
		if (dialog.open() == 0 && !dialog.getNewName().equals("")) {
			newObjectName = dialog.getNewName();

			if (currentNode.isLeaf()) {
				// Add record next to current leaf
				Node parent = currentNode.getParent();
				newNode = parent.createLeaf(newObjectName);
			} else {
				// Add record in this folder
				newNode = currentNode.createLeaf(newObjectName);
			}
			SessionManager.setCurrentRefrence(newNode);
			// Hide emptyView
			IWorkbenchPage pageV = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			pageV.hideView(pageV.findView(EmptyView.ID));
			try {
				IEditorPart newEditorWindow = page.openEditor(new EditorInput(newNode), FormEditor.ID);
				page.activate(newEditorWindow);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		} else {
			MessageBox promptBox = new MessageBox(window.getShell(), SWT.ICON_ERROR | SWT.OK);
			promptBox.setText("Warning! Unable create empty record");
			promptBox.setMessage("Impossible create record with empty name");
			int button = promptBox.open();
		}
	}

	private void addFolder(IWorkbenchPage page, Node currentNode) {
		Node newFolder = null;
		// Get new folder name
		GetFolderNameTitleAreaDialog dialog = new GetFolderNameTitleAreaDialog(window.getShell());
		dialog.create();
		if (dialog.open() == 0 && !dialog.getNewName().equals("")) {
			newObjectName = dialog.getNewName();
			if (currentNode.isLeaf()) {
				// Add Folder next to current leaf
				Node parent = currentNode.getParent();
				newFolder = parent.createSubFolder(newObjectName);
			} else {
				// Add record in this folder
				newFolder = currentNode.createSubFolder(newObjectName);
			}
			SessionManager.setCurrentRefrence(newFolder);
		} else {
			MessageDialog.openError(window.getShell(), "Error", "There wasn't correct folder name entered");
		}
	}
}
