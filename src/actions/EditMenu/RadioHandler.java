package actions.EditMenu;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

import dataModel.Node;
import dataModel.SessionManager;
import rcp3project.NavigationView;

public class RadioHandler extends AbstractHandler implements IElementUpdater {

	private static final String PARM_INFO = "org.eclipse.ui.commands.radioStateParameter";

	private String fCurrentValue;
	IWorkbenchWindow window;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String parm = event.getParameter(PARM_INFO);
		if (parm.equals(fCurrentValue)) {
			return null; // in theory, we're already in the correct state
		}

		// do whatever having "parm" active implies

		// работаем с выделением
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IStructuredSelection selection = (IStructuredSelection) page.getSelection();
		Node currentNode = (Node) selection.getFirstElement();

		System.out.println("selected = " + currentNode + "; status = " + currentNode.getPicStatus());
		// fCurrentValue = String.valueOf(currentNode.getPicStatus());
		System.out.println("fCurrentValue = " + fCurrentValue);

		System.out.println("parm = " + parm);

		currentNode.setPicStatus(Integer.valueOf(parm));
		SessionManager.setCurrentRefrence(currentNode);

		try {
			NavigationView n = (NavigationView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(NavigationView.ID);
			// n.setExpandStatus(false);
			n.redrawTree();
		} catch (PartInitException e) {
		}

		// update our radio button states ... get the service from
		// a place that's most appropriate
		ICommandService service = HandlerUtil.getActiveWorkbenchWindowChecked(event).getService(ICommandService.class);
		service.refreshElements(event.getCommand().getId(), null);

//		if (HandlerUtil.matchesRadioState(event)) {
//			return null; // we are already in the updated state - do nothing
//		}
//
//		String currentState = event.getParameter(PARM_INFO);
////	String currentState = event.getParameter(RadioState.PARAMETER_ID);
//		// perform task for current state
//		if (currentState.equals("0")) {	} 
//		else if (currentState.equals("1")) {}
//		// perform left alignment
//		else if (currentState.equals("2"))
//		// perform center alignment and so on ... and finally update the current state
		// HandlerUtil.updateRadioState(event.getCommand(), parm);
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
