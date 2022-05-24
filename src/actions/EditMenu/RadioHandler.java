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
	IWorkbenchWindow window;

	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	IStructuredSelection selection = (IStructuredSelection) page.getSelection();
	Node currentNode = (Node) (selection.getFirstElement());
	private String fCurrentValue = String.valueOf(currentNode.getPicStatus());

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String parm = event.getParameter(PARM_INFO);
		if (parm.equals(fCurrentValue)) {
			return null; // in theory, we're already in the correct state
		}
		// work with selection
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		selection = (IStructuredSelection) page.getSelection();
		Node currentNode = (Node) (selection.getFirstElement());
		fCurrentValue = String.valueOf(currentNode.getPicStatus());
		currentNode.setPicStatus(Integer.valueOf(parm));
		SessionManager.setCurrentRefrence(currentNode);
		try {
			NavigationView n = (NavigationView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(NavigationView.ID);
			n.setExpandStatus(true);
			n.redrawTree();
		} catch (PartInitException e) {
			System.out.print(e.getStackTrace());
		}

		// update our radio button states ... get the service from a place that's most
		// appropriate
		ICommandService service = HandlerUtil.getActiveWorkbenchWindowChecked(event).getService(ICommandService.class);
		service.refreshElements(event.getCommand().getId(), null);
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateElement(UIElement element, Map parameters) {
		String parm = (String) parameters.get(PARM_INFO);
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		selection = (IStructuredSelection) page.getSelection();
		Node currentNode = (Node) (selection.getFirstElement());

		if (currentNode != null) {
			fCurrentValue = String.valueOf(currentNode.getPicStatus());
		}

		if (parm != null) {
			if (fCurrentValue != null && fCurrentValue.equals(parm)) {
				element.setChecked(true);
			} else {
				element.setChecked(false);
			}
		}
	}
}
