package actions.FileMenu;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;

import rcp3project.Activator;
import savers.FileReadManager;

public class FileOpenAction extends Action {
	 private final IWorkbenchWindow window;
	 private final String ACTION_ID="OpenFileAction";
 
 public FileOpenAction(IWorkbenchWindow win) {
	 this.window=win;
	 setId(ACTION_ID);
	 setActionDefinitionId(ACTION_ID);
	 Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
	 URL fileURL = bundle.getEntry("/icons/eclipse16.png");
	 setImageDescriptor(ImageDescriptor.createFromURL(fileURL));
 }
	public void run() {
		FileReadManager.execute(window);
	}
	
}
