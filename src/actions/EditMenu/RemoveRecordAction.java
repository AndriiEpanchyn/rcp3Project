package actions.EditMenu;

import java.net.URL;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;

import dataModel.Node;
import dataModel.SessionManager;
import rcp3project.Activator;
import rcp3project.FormEditor;
import rcp3project.NavigationView;

public class RemoveRecordAction extends Action {
	IWorkbenchWindow window;
	private final String ACTION_ID = "RemoveRecordAction";

	public RemoveRecordAction(IWorkbenchWindow win) {
		window = win;
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_ID);
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("/icons/removeRecord16x16.png");
		setImageDescriptor(ImageDescriptor.createFromURL(fileURL));
	}

	@Override
	public void run() {
		IWorkbenchPage page = window.getActivePage();
		Node leafToRemove;
		leafToRemove = SessionManager.getCurrentRefrence();

		if (leafToRemove.getName().equals("root")) {// Blocks removing root
			Status status = new Status(IStatus.ERROR, "actions.EditMenu", "ROOT is not removable mandatory folder");
			ErrorDialog.openError(window.getShell(), "Error", "Root folder cann't be removed", status);
		} else if (leafToRemove.isLeaf() == true) {// removing leaf
			MessageDialog dialog = new MessageDialog(window.getShell(), "Confirm removing", null,
					"Do you confirm removing of the " + leafToRemove.getName() + "?", MessageDialog.CONFIRM,
					new String[] { "Yes", "NO" }, 1);
			int result = dialog.open();
			if (result == 0) {
				IEditorReference[] eRefs = page.getEditorReferences();
				for (IEditorReference ref : eRefs) {
					FormEditor editor1 = (FormEditor) ref.getEditor(false);
					if (editor1.getEditorsNode().hashCode() == leafToRemove.hashCode()) {
						page.closeEditor(editor1, false);
					}
				}
				leafToRemove.removeLeaf();
			}
		} else if (leafToRemove.hasChildren()) {// remove full folder
			MessageDialog dialog = new MessageDialog(window.getShell(), "Confirm removing NOT EMPTY folder", null,
					"Do you confirm removing of the NOT EMPTY FOLDER: \n" + leafToRemove.getName() + "?",
					MessageDialog.CONFIRM, new String[] { "Yes", "NO" }, 1);
			int result = dialog.open();
			if (result == 0) {
				leafToRemove.removeFullSubfolder();
			}
		} else {// remove empty folder
			MessageDialog dialog = new MessageDialog(window.getShell(), "Confirm removing empty folder", null,
					"Do you confirm removing of the empty folder: \n" + leafToRemove.getName() + "?",
					MessageDialog.CONFIRM, new String[] { "Yes", "NO" }, 1);
			int result = dialog.open();
			if (result == 0) {
				leafToRemove.removeEmptySubFolder();
			}
		}
		NavigationView navigationView = (NavigationView) (page.getViews()[0]);
		navigationView.redrawTree();
	}

}
