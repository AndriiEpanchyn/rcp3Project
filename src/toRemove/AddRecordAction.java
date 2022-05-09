package toRemove;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.osgi.framework.Bundle;

import dataModel.Node;
import dataModel.SessionManager;
import rcp3project.Activator;
import rcp3project.EditorInput;
import rcp3project.FormEditor;
import rcp3project.NavigationView;

public class AddRecordAction extends Action {
	IWorkbenchWindow window;
	private final String ACTION_ID = "AddRecordAction";

	public AddRecordAction(IWorkbenchWindow win) {
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
		System.out.println("Navi current = " + currentNode);

		if (currentNode == null) {
			currentNode = SessionManager.getCurrentRefrence();
			System.out.println("Session current = " + currentNode);
		}

		Node newNode = null;
		if (currentNode.isLeaf()) {// Add record next to current leaf
			Node parent = currentNode.getParent();
			newNode = parent.createLeaf("New Record");
		} else {// Add record in this folder
			System.out.println("Add leaf under folder");
			System.out.println("Session current = " + currentNode);
			newNode = currentNode.createLeaf("NewRecord underFolder");
		}
		SessionManager.setCurrentRefrence(newNode);
		try {
			System.out.println(currentNode);
			@SuppressWarnings("unused")
			IEditorPart newEditorWindow = page.openEditor(new EditorInput(newNode), FormEditor.ID);
			page.activate(newEditorWindow);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		navigationView.redrawTree();
	}
}
