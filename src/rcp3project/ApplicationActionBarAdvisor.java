package rcp3project;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import actions.EditMenu.AboutWindowAction;
import actions.EditMenu.AddAction;
import actions.EditMenu.CollapseTreeAction;
import actions.EditMenu.ExpandTreeAction;
import actions.EditMenu.OpenEditorAction;
import actions.EditMenu.RemoveRecordAction;
import actions.EditMenu.RenameFolderAction;
import actions.EditMenu.SaveRecordAction;
import actions.FileMenu.FileReadAction;
import actions.FileMenu.FileSaveAction;
import actions.FileMenu.FileSaveAsAction;
import actions.FileMenu.NewFileAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private AboutWindowAction aboutAction;
	private IWorkbenchAction closeEditorAction;
	private NewFileAction newFileAction;
	private SaveRecordAction saveRecordAction;
	private RemoveRecordAction removeRecordAction;
	private AddAction addAction;
	private RenameFolderAction renameFolderAction;
	private ExpandTreeAction expand;
	private CollapseTreeAction collapse;
	private OpenEditorAction open;
	private FileSaveAsAction fileSaveAsAction;
	private FileReadAction fileReadAction;
	private FileSaveAction fileSaveAction;

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

		renameFolderAction = new RenameFolderAction(window);
		register(renameFolderAction);

		expand = new ExpandTreeAction(window);
		register(expand);

		collapse = new CollapseTreeAction(window);
		register(collapse);

		open = new OpenEditorAction(window);
		register(open);

		fileSaveAsAction = new FileSaveAsAction(window);
		register(fileSaveAsAction);

		fileReadAction = new FileReadAction(window);
		register(fileReadAction);

		fileSaveAction = new FileSaveAction(window);
		register(fileSaveAction);
	}

	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		super.fillMenuBar(menuBar);
	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		super.fillCoolBar(coolBar);

	}

	public void getFillCoolBar() {
	}

}
