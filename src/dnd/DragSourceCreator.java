package dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class DragSourceCreator {
	public static DragSource create(Tree tree) {
		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
		final DragSource source = new DragSource(tree, operations);
		source.setTransfer(types);
		final TreeItem[] dragSourceItem = new TreeItem[1];

		source.addDragListener(new DragSourceListener() {
			@Override
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = tree.getSelection();
				event.doit = true;
				dragSourceItem[0] = selection[0];
			}

			@Override
			public void dragSetData(DragSourceEvent event) {
				event.data = dragSourceItem[0].getText();
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE)
					dragSourceItem[0].dispose();
				dragSourceItem[0] = null;
			}
		});
		return source;
	}
}
