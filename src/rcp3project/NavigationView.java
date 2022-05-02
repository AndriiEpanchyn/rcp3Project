package rcp3project;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import dataModel.MyContentProvider2;
import dataModel.Node;

public class NavigationView extends ViewPart {
	public static final String ID = "rcp3project.NavigationView";
	private TreeViewer viewer;

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(new MyContentProvider2());
		viewer.setLabelProvider(new LabelProvider());
		createColumn(viewer.getTree(), "Students");
		viewer.setInput(Node.makeDummyTree());
		viewer.expandAll();
		viewer.getTree().setHeaderBackground(new Color(120, 120, 120));
		viewer.getTree().setHeaderForeground(new Color(220, 220, 220));
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				// TODO Auto-generated method stub
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Node currentNode = (Node) selection.getFirstElement();
				System.out.println(currentNode);
				System.out.println("result: " + currentNode.isLeaf());
				try {
					 PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(FormView.ID);
					//System.out.println(f.group=currentNode.getParent().getName());
					 String path = new File("C:\\JavaProjects\\_Luxoft\\rcp3Project\\icons\\eclipse128.png").getAbsolutePath();
					 System.out.print(File.separator+" "+path);
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
}