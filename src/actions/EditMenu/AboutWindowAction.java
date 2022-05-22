package actions.EditMenu;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;

import rcp3project.Activator;

public class AboutWindowAction extends Action {
	 private final IWorkbenchWindow window;
	 private final String ACTION_ID="AboutWindowAction";
	 
	 public AboutWindowAction(IWorkbenchWindow win){
		 this.window=win;
		 setId(ACTION_ID);
		 setActionDefinitionId(ACTION_ID);
		 Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		 URL fileURL = bundle.getEntry("/icons/eclipse16.png");
		 setImageDescriptor(ImageDescriptor.createFromURL(fileURL));
	 }
	 
	 @Override
	 public void run() {
		 final String aboutMessage = "This program is made on the basis of Eclipse RCP framework, and allows users to store"
		 		+ " information about the persons grouped by some criteria.";
		 MessageDialog.openInformation(window.getShell(), "About program", aboutMessage);	 
	 }
}
