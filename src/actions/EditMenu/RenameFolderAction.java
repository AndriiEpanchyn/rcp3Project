package actions.EditMenu;

import java.net.URL;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;

import dataModel.Node;
import dataModel.SessionManager;
import rcp3project.Activator;
import rcp3project.FormEditor;
import rcp3project.MyTitleAreaDialog;
import rcp3project.NavigationView;

public class RenameFolderAction extends Action {
	IWorkbenchWindow window;
	private final String ACTION_ID = "RenameFolderAction";
	private URL fileURL;

	public RenameFolderAction(IWorkbenchWindow win) {
		window = win;
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_ID);
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		fileURL = bundle.getEntry("/icons/renameFolderAction16x16.png");
		setImageDescriptor(ImageDescriptor.createFromURL(fileURL));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Node leafToRemove = SessionManager.getCurrentRefrence();
		IWorkbenchPage page = window.getActivePage();

		if (leafToRemove == null || leafToRemove.getName().equals("Folder")) {
			Status status = new Status(IStatus.ERROR, "actions.EditMenu", "Root folder cann't be renamed");
			ErrorDialog.openError(window.getShell(), "Error", "Operation cann't be complete!", status);
		} else if (!leafToRemove.isLeaf()) {
			MyTitleAreaDialog dialog = new MyTitleAreaDialog(window.getShell(), leafToRemove.getName());
			dialog.create();
			if (dialog.open() == Window.OK) {
				SessionManager.getCurrentRefrence().setName(dialog.getNewName());
				NavigationView navigationView = (NavigationView) (page.getViews()[0]);

				IEditorReference[] eRefs = page.getEditorReferences();
				for (IEditorReference ref : eRefs) {
					FormEditor editor1 = (FormEditor) ref.getEditor(false);
					editor1.refreshAll();
				}

				navigationView.redrawTree();
			}
		}
	}
}
