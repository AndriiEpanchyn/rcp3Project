package actions.FileMenu;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;

import dataModel.SessionManager;
import rcp3project.Activator;
import savers.FileReadManager;

public class FileReadAction extends Action {
	private final IWorkbenchWindow window;
	private final String ACTION_ID = "rcp3Project.ReadFileAction";

	public FileReadAction(IWorkbenchWindow win) {
		this.window = win;
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_ID);
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("/icons/newFile16x16.png");
		setImageDescriptor(ImageDescriptor.createFromURL(fileURL));
	}

	@Override
	public void run() {
		FileReadManager.execute(window);
		window.getShell().setText("JFace application:  " + SessionManager.getFileName());
	}
}