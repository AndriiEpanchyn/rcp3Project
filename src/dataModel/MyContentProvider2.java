package dataModel;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class MyContentProvider2 implements IStructuredContentProvider, ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		
		//System.out.println(((Node) inputElement).getChildren().toArray());
			return  ((Node) inputElement).getChildren().toArray();
		//return ArrayContentProvider.getInstance().getElements(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return getElements( parentElement);
	}

	@Override
	public Object getParent(Object element) {
		if(element==null) {
			return null;
		}
		return ((Node) element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
			return ((Node) element).hasChildren();
	}

}
