package actions.EditMenu;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

public class RadioHandler extends AbstractHandler implements IElementUpdater {

	private static final String PARM_INFO = "radioStateParameter";
	private String fCurrentValue;
	IWorkbenchWindow window;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		String parm = event.getParameter(PARM_INFO);
//		if (parm.equals(fCurrentValue)) {
//			return null; // in theory, we're already in the correct state
//		}
//
//		// do whatever having "parm" active implies
//		fCurrentValue = parm;
//
//		System.out.println("parm = " + parm);
//
//		// update our radio button states ... get the service from
//		// a place that's most appropriate
//		ICommandService service = HandlerUtil.getActiveWorkbenchWindowChecked(event).getService(ICommandService.class);
//		service.refreshElements(event.getCommand().getId(), null);

		if (HandlerUtil.matchesRadioState(event)) {
			return null; // we are already in the updated state - do nothing
		}

		String currentState = event.getParameter("org.eclipse.ui.commands.radioStateParameter");
		System.out.println("currentState: " + currentState);
//		String currentState = event.getParameter(RadioState.PARAMETER_ID);
		// perform task for current state
		if (currentState.equals("0")) {
			System.out.println("0 is chosen");
		} else if (currentState.equals("1")) {
			System.out.println("1 is chosen");
		}
		// perform left alignment
		else if (currentState.equals("2"))
			System.out.println("2 is chosen");
		// perform center alignment
		// and so on ...

		// and finally update the current state
		HandlerUtil.updateRadioState(event.getCommand(), currentState);

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateElement(UIElement element, Map parameters) {
		String parm = (String) parameters.get(PARM_INFO);
		if (parm != null) {
			if (fCurrentValue != null && fCurrentValue.equals(parm)) {
				element.setChecked(true);
			} else {
				element.setChecked(false);
			}
		}
	}
}
