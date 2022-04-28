package dataModel;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class MyViewLabelProvider extends LabelProvider {

	@Override
	public String getText(Object obj) {
		return ((Node)obj).getName();
	}

	@Override
	public Image getImage(Object obj) {
		String imageKey;
		if(((Node)obj).isLeaf()) {
			imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		} else {		
			imageKey = ISharedImages.IMG_OBJ_FOLDER;
		}
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}