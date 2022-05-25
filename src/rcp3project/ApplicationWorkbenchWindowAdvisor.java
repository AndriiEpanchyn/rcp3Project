package rcp3project;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import dataModel.SessionManager;
import dnd.ViewerDropTargetCreator;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
		super.postWindowClose();
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(900, 350));
		configurer.setShowMenuBar(true);
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(false);
		configurer.setShowPerspectiveBar(true);
		configurer.setTitle("Alien RCP app"); //$NON-NLS-1$
		ViewerDropTargetCreator.create(configurer.getWindow().getShell());

	}

	@Override
	public void postWindowCreate() {
		Shell shell = getWindowConfigurer().getWindow().getShell();
		shell.setLocation(1000, 150);
		IEditorInput input = new EditorInput(SessionManager.getCurrentRefrence());

	}

	public boolean preWindowClose() {

		return true;

	}

}
