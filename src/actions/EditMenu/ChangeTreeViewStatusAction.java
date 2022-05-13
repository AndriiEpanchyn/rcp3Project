package actions.EditMenu;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;

import rcp3project.Activator;
import rcp3project.NavigationView;

public class ChangeTreeViewStatusAction extends Action {
	IWorkbenchWindow window;
	private final String ACTION_ID = "ChangeTreeViewStatusAction";

	NavigationView navigationView;

	public ChangeTreeViewStatusAction(IWorkbenchWindow win) {
		window = win;
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_ID);
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("/icons/eclipse16.png");
		setImageDescriptor(ImageDescriptor.createFromURL(fileURL));
	}

	@Override
	public void run() {
		IWorkbenchPage page = window.getActivePage();
		@SuppressWarnings("deprecation")
		NavigationView navigationView = (NavigationView) (page.getViews()[0]);
		navigationView.setExpandStatus(true);
		navigationView.redrawTree();
	}
}
