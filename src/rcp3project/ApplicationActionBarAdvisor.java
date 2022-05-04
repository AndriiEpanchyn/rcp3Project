package rcp3project;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction saveRecordAction;
	private IWorkbenchAction openFileAction;
	private IWorkbenchAction saveFileAction;
	private IWorkbenchAction newFileAction;
	//private OpenViewAction helpAction;
	
	
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}
	
	@Override
	protected void makeActions(IWorkbenchWindow window) {
		super.makeActions(window);
		saveRecordAction = ActionFactory.QUIT.create(window);
		register(saveRecordAction);
		
		openFileAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
        register(openFileAction);
	}
	
	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		super.fillMenuBar(menuBar);
	}
	
	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		super.fillCoolBar(coolBar);
		 IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
	        coolBar.add(new ToolBarContributionItem(toolbar, "main"));   
	       
	        toolbar.add(new Separator());
	        toolbar.add(saveRecordAction);
	        toolbar.add(new Separator());
	        toolbar.add(openFileAction);
	        toolbar.add(new Separator());
	}

}

