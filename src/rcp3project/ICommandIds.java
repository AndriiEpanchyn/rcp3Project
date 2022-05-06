package rcp3project;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

// File Menu
	public static final String newFileAction = "rcp3Project.NewFileAction";
	public static final String openFileAction = "rcp3Project.OpenFileAction"; //TODO
//	public static final String saveFileAction = "rcp3Project.OpenFileAction"; //TODO
//	public static final String saveAsFileAction = "rcp3Project.OpenFileAction"; //TODO
// Edit Menu
	// public static final String SaveRecordAction = "SaveRecordAction";
	
	public static final String CloseEditor = "org.eclipse.ui.file.close";
// About Menu
	public static final String AboutWindowAction = "rcp3Project.AboutWindowAction";
	
// Toolbar
	
//	Popup menu
	
}
