package rcp3project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import dnd.ViewerDropTargetCreator;

public class EmptyView extends ViewPart {
	public static final String ID = "rcp3project.EmptyView";
	Composite composite;

	@Override
	public void createPartControl(Composite parent) {
		composite = parent;
		FillLayout fill = new FillLayout(SWT.ALL);
		composite.setLayout(fill);
		ViewerDropTargetCreator.create(composite);
	}

	@Override
	public void setFocus() {

	}

	public Composite getComposite() {
		return this.composite;
	}

}