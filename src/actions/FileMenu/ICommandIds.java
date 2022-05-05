package actions.FileMenu;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

//    public static final String CMD_OPEN = "mailRcp.open";
//    public static final String CMD_OPEN_MESSAGE = "mailRcp.openMessage";
    public static final String AboutWindowAction = "rcp3Project.AboutWindowAction";
    public static final String CloseEditor = "org.eclipse.ui.file.close";
    public static final String OpenFileAction = "rcp3Project.OpenFileAction";
    public static final String deleteFileAction = "org.eclipse.ui.edit.delete";
    public static final String NewFileAction = "NewFileAction";
}
