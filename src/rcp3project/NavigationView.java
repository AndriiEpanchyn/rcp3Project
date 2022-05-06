package rcp3project;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import dataModel.MyContentProvider2;
import dataModel.Node;
import dataModel.SessionManager;

public class NavigationView extends ViewPart {
	public static final String ID = "rcp3project.NavigationView";
	private TreeViewer viewer;
//	Node session = SessionManager.getSession();
	Node session = Node.makeDummyTree();
	Node currentNode = SessionManager.getCurrentRefrence();

	@Override
	public void createPartControl(Composite parent) {
		SessionManager.setSession(session);
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(new MyContentProvider2());
		viewer.setLabelProvider(new LabelProvider());
		createColumn(viewer.getTree(), "Students");
		viewer.setInput(session);
		viewer.expandAll();
		viewer.getTree().setHeaderBackground(new Color(120, 120, 120));
		viewer.getTree().setHeaderForeground(new Color(220, 220, 220));
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				currentNode = (Node) selection.getFirstElement();
				SessionManager.setCurrentRefrence(currentNode);
				if (currentNode.isLeaf()) {
					try {
						IEditorPart f = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.openEditor(new EditorInput(currentNode), FormEditor.ID);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}
		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				currentNode = (Node) selection.getFirstElement();
				SessionManager.setCurrentRefrence(currentNode);
			}
		});
	}

	public void createColumn(Tree tr, String text) {
		TreeColumn column = new TreeColumn(tr, SWT.NONE);
		column.setWidth(300);
		column.setText(text);
		tr.setHeaderVisible(true);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void redrawTree() {
		viewer.getTree().deselectAll();
		session = SessionManager.getSession();
		viewer.setInput(session);
		viewer.expandAll();
		viewer.refresh();
	}

	public Node getCurrentRecord() {
		return currentNode;
	}

}