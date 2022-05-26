package dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import dataModel.Node;
import dataModel.SessionManager;
import rcp3project.FormEditor;
import rcp3project.NavigationView;

public class TreeDropTargetCreator {
	public static DropTarget create(Tree tree) {
		Display display = tree.getDisplay();
		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
		DropTarget target = new DropTarget(tree, operations);

		target.setTransfer(types);
		target.addDropListener(new DropTargetAdapter() {
			Node node;

			@Override
			public void dragOver(DropTargetEvent event) {

				event.detail = DND.DROP_MOVE;
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
				if (event.item != null) {
					TreeItem item = (TreeItem) event.item;
					Point pt = display.map(null, tree, event.x, event.y);
					Rectangle bounds = item.getBounds();
					if (pt.y < bounds.y + bounds.height / 3) {
						event.feedback |= DND.FEEDBACK_INSERT_BEFORE;
					} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
						event.feedback |= DND.FEEDBACK_INSERT_AFTER;
					} else {
						event.feedback |= DND.FEEDBACK_SELECT;
					}
				}
			}

			@Override
			public void drop(DropTargetEvent event) {
				// Drop not active (Source = null, target = any value)
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				Node currentNode = SessionManager.getCurrentRefrence();
				TreeItem item = (TreeItem) event.item;
				Node target;

				if (event.data != "Folder") {
					if (item == null) {
						// Target = null, source leaf or folder
						target = SessionManager.getSession();
						currentNode.moveTo(target, false);
					} else {
						target = (Node) item.getData();

						if (currentNode != target) {
							Point pt = display.map(null, tree, event.x, event.y);
							Rectangle bounds = item.getBounds();
							boolean insertAfter = false;
							if (pt.y > bounds.y + bounds.height / 3) {
								insertAfter = true;
							}
							currentNode.moveTo(target, insertAfter);
						}
					}

					// Redraw and refresh data
					NavigationView n;
					try {
						n = (NavigationView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.findView(NavigationView.ID);
						n.setExpandStatus(true);
						n.redrawTree();

						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IEditorReference[] eRefs = page.getEditorReferences();
						for (IEditorReference ref : eRefs) {
							FormEditor editor1 = (FormEditor) ref.getEditor(false);
							if (editor1.getEditorsNode().hashCode() == currentNode.hashCode()) {
								editor1.changeGroup();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		return target;
	}

}
// end of class
