package dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import dataModel.Node;
import dataModel.SessionManager;
import rcp3project.EditorInput;
import rcp3project.EmptyView;
import rcp3project.FormEditor;

public class ViewerDropTargetCreator {
	public static DropTarget create(Composite dropTarget) {
		Display display = dropTarget.getDisplay();
		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
		DropTarget target = new DropTarget(dropTarget, operations);

		target.setTransfer(types);
		target.addDropListener(new DropTargetAdapter() {
			@Override
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;

				if ((Composite) event.item == dropTarget) {

					Point pt = display.map(null, dropTarget, event.x, event.y);
					Rectangle bounds = ((Composite) event.item).getBounds();

					if ((pt.y > bounds.y && pt.y < bounds.y + bounds.height)
							&& (pt.x > bounds.x && pt.x < bounds.x + bounds.width)) {
						event.feedback |= DND.FEEDBACK_INSERT_BEFORE;
					} else {
						event.feedback |= DND.FEEDBACK_NONE;
					}
				}
			}

			@Override
			public void drop(DropTargetEvent event) {
				// protects from unpredictable crushes
				if (event.data == null) {
					event.detail = DND.DROP_COPY;
					return;
				} else {
					event.detail = DND.DROP_COPY;
					Node currentNode = SessionManager.getCurrentRefrence();
					if (currentNode.isLeaf()) {
						// Hide emptyView
						IWorkbenchPage pageV = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						pageV.hideView(pageV.findView(EmptyView.ID));
						// OpenEditor
						try {
							boolean isEditorAlreadyOpened = false;
							IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
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
								IEditorPart newEditorWindow = page.openEditor(new EditorInput(currentNode),
										FormEditor.ID);
							}
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		return target;
	}
}
