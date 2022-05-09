package actions.EditMenu;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;

import dataModel.Node;
import dataModel.SessionManager;
import rcp3project.Activator;
import rcp3project.NavigationView;

public class AddFolderAction extends Action {
	IWorkbenchWindow window;
	private final String ACTION_ID = "AddFolderAction";

	public AddFolderAction(IWorkbenchWindow win) {
		window = win;
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_ID);
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("/icons/addFolder16x16.png");
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

		Node newFolder = null;
		if (currentNode.isLeaf()) {// Add Folder next to current leaf
			Node parent = currentNode.getParent();
			newFolder = parent.createSubFolder("New Folder");
		} else {// Add record in this folder
			System.out.println("Add folder under folder");
			System.out.println("Session current = " + currentNode);
			newFolder = currentNode.createSubFolder("New  subFolder");
		}
		SessionManager.setCurrentRefrence(newFolder);
		System.out.println("Created new folder: " + newFolder.getParent() + " \\ " + newFolder);
		navigationView.redrawTree();
	}
}
