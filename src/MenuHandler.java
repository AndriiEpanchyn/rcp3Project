import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

public class MenuHandler extends ExtensionContributionFactory {

	public MenuHandler() {
		super();
	}

	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {
		System.err.println();
		ICommandService service = PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = service.getCommand("radioStateParameter");
		State state = command.getState("org.eclipse.ui.commands.radioState");
//		State state1 = command.getState("org.eclipse.ui.commands.radioStateParameter");
		if (state != null) {
			state.setValue(true);
		}
//				state.setValue(!(Boolean) state.getValue());
	}

}
