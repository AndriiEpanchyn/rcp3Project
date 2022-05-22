package dataModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import rcp3project.Application;

public class MyViewLabelProvider extends LabelProvider {
	private String status0FileName = "icons/status0.png";
	private String status1FileName = "icons/status1.png";
	private String status2FileName = "icons/status2.png";
	private String status3FileName = "icons/status3.png";
	private String status4FileName = "icons/status4.png";
	private String status5FileName = "icons/status5.png";
	private String folderFileName = ISharedImages.IMG_OBJ_FOLDER;
	private String leafFileName = ISharedImages.IMG_OBJ_FILE;

	@Override
	public String getText(Object obj) {
		return ((Node) obj).getName();
	}

	@Override
	public Image getImage(Object obj) {
		String imageKey = "";
		Node node = (Node) obj;

		if (node.getPicStatus() == 0) {
			if (node.isLeaf()) {
				imageKey = status0FileName; // unchecked file
			} else {
				imageKey = status1FileName; // unchecked folder
			}
		} else if (node.getPicStatus() == 1) {
			if (node.isLeaf()) {
				imageKey = status2FileName; // checked file
			} else {
				imageKey = status3FileName; // checked folder
			}
		} else if (node.getPicStatus() == 2) {
			if (node.isLeaf()) {
				imageKey = status4FileName; // denied file
			} else {
				imageKey = status5FileName; // denied folder
			}
		} else {
			if (node.isLeaf()) {
				imageKey = leafFileName; // checked file
			} else {
				imageKey = folderFileName; // checked folder
			}
		}
		Image answer = convertPhotoForLabel(imageKey);

		return answer;
	}

	private Image convertPhotoForLabel(String fileName) {
		Image photo;
		Display display = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay();

		if (fileName == null || fileName.isEmpty() || fileName.length() == 0) {
			System.out.println("strange situation appeared in MyLabelProvider.convertPhotoForLabel");
			photo = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		}
		if (new File(fileName).isAbsolute()) {
			photo = new Image(display, fileName);
		} else {
			InputStream stream = Application.class.getClassLoader().getResourceAsStream(fileName);
			photo = new Image(display, stream);

			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Image answer = new Image(display, photo.getImageData().scaledTo(16, 16));
		try {
			photo.dispose();
		} catch (Exception e) {
		}
		return answer;
	}
}