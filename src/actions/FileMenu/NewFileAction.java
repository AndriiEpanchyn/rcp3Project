package actions.FileMenu;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import dataModel.SessionManager;
import rcp3project.Activator;
import rcp3project.NavigationView;

public class NewFileAction extends Action {
	private final IWorkbenchWindow window;
	private final String ACTION_ID = "rcp3Project.NewFileAction";

	public NewFileAction(IWorkbenchWindow win) {
		this.window = win;
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_ID);
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("/icons/newFile16x16.png");
		setImageDescriptor(ImageDescriptor.createFromURL(fileURL));
	}

	@Override
	public void run() {
		NavigationView n = null;
//		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
		window.getActivePage().closeAllEditors(false);
		SessionManager.clearSession();
		SessionManager.setSession(SessionManager.getSession());
		try {
			n = (NavigationView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(NavigationView.ID);
			n.redrawTree();

		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
}
