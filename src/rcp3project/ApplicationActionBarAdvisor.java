package rcp3project;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import actions.EditMenu.AddAction;
import actions.EditMenu.RemoveRecordAction;
import actions.EditMenu.RenameFolderAction;
import actions.EditMenu.SaveRecordAction;
// import actions.EditMenu.SaveRecordAction;
import actions.FileMenu.AboutWindowAction;
import actions.FileMenu.NewFileAction;
import toRemove.AddFolderAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private AboutWindowAction aboutAction;
	private IWorkbenchAction closeEditorAction;
	private NewFileAction newFileAction;
	private SaveRecordAction saveRecordAction;
	private RemoveRecordAction removeRecordAction;
	private AddAction addAction;
	private AddFolderAction addFolderAction;
	private RenameFolderAction renameFolderAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void makeActions(IWorkbenchWindow window) {
		super.makeActions(window);

		aboutAction = new AboutWindowAction(window);
		register(aboutAction);

		closeEditorAction = ActionFactory.CLOSE.create(window);
		register(closeEditorAction);

		newFileAction = new NewFileAction(window);
		register(newFileAction);

		saveRecordAction = new SaveRecordAction(window);
		register(saveRecordAction);

		removeRecordAction = new RemoveRecordAction(window);
		register(removeRecordAction);

		addAction = new AddAction(window);
		register(addAction);

//		addFolderAction = new AddFolderAction(window);
//		register(addFolderAction);

		renameFolderAction = new RenameFolderAction(window);
		register(renameFolderAction);

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
//		toolbar.add(newFileAction);
//		toolbar.add(new Separator());
//		toolbar.add(addAction);
//		toolbar.add(saveRecordAction);
//		toolbar.add(removeRecordAction);
//		toolbar.add(new Separator());
//		// toolbar.add(addFolderAction);
//		toolbar.add(renameFolderAction);

	}

	public void getFillCoolBar() {
	}

}
