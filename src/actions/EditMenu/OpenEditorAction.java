package actions.EditMenu;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import dataModel.Node;
import dataModel.SessionManager;
import rcp3project.EditorInput;
import rcp3project.FormEditor;
import rcp3project.NavigationView;

public class OpenEditorAction extends Action {
	IWorkbenchWindow window;
	private final String ACTION_ID = "OpenEditorAction";

	public OpenEditorAction(IWorkbenchWindow win) {
		window = win;
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_ID);
	}

	@Override
	public void run() {
		IWorkbenchPage page = window.getActivePage();
		NavigationView navigationView = (NavigationView) (page.getViews()[0]);

		IViewSite iViewSite = (IViewSite) navigationView.getSite();
		if (iViewSite != null) {
			ISelectionProvider selectionProvider = iViewSite.getSelectionProvider();
			if (selectionProvider != null) {
				IStructuredSelection selection = (IStructuredSelection) selectionProvider.getSelection();
				Node currentNode = (Node) selection.getFirstElement();

				SessionManager.setCurrentRefrence(currentNode);
				if (currentNode == null) {
				} else if (currentNode.isLeaf()) {
					try {
						boolean isEditorAlreadyOpened = false;
						IEditorReference[] eRefs = page.getEditorReferences();
						for (IEditorReference ref : eRefs) {
							FormEditor editor1 = (FormEditor) ref.getEditor(false);
							if (editor1.getEditorsNode().hashCode() == currentNode.hashCode()) {
								page.activate(editor1);
								isEditorAlreadyOpened = true;
								break;
							}
						}
						if (!isEditorAlreadyOpened) {
							IEditorPart newEditorWindow = page.openEditor(new EditorInput(currentNode), FormEditor.ID);
						}
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				} else {
					navigationView.setExpandStatus(true);
					navigationView.redrawTree();
				}
			}
		}
	}
}
