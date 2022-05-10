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

public class CollapseTreeAction extends Action {
	IWorkbenchWindow window;
	private final String ACTION_ID = "CollapseTreeAction";
//	private final String EXPAND_ALL_ICON = "/icons/expandAction16x16.png";
	private final String COLLAPS_ALL_ICON = "/icons/collapseAction16x16.png";

	String fileName = COLLAPS_ALL_ICON;
	NavigationView navigationView;

	public CollapseTreeAction(IWorkbenchWindow win) {
		window = win;
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_ID);
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry(fileName);
		setImageDescriptor(ImageDescriptor.createFromURL(fileURL));
	}

	@Override
	public void run() {
		IWorkbenchPage page = window.getActivePage();
		@SuppressWarnings("deprecation")
		NavigationView navigationView = (NavigationView) (page.getViews()[0]);
		navigationView.setExpandStatus(false);
		navigationView.redrawTree();
	}
}
