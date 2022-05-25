package dataModel;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class MyContentProvider implements IStructuredContentProvider, ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		return ((Node) inputElement).getChildren().toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return getElements(parentElement);
	}

	@Override
	public Object getParent(Object element) {
		if (element == null) {
			return null;
		}
		return ((Node) element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		return ((Node) element).hasChildren();
	}

}
