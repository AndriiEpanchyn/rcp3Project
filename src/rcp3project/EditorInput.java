package rcp3project;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import dataModel.Node;

public class EditorInput implements IEditorInput {
	public final static String ID = "EditorInput";
	Node currentRefrence;

	public EditorInput(Node current) {
		this.currentRefrence = current;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return currentRefrence.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return currentRefrence.getName();
	}

	public Node getRecord() {
		return currentRefrence;
	}
}