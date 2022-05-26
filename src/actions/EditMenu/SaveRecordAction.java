package actions.EditMenu;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.osgi.framework.Bundle;

import rcp3project.Activator;
import rcp3project.FormEditor;

public class SaveRecordAction extends Action {
	IWorkbenchWindow window;
	private final String ACTION_ID = "SaveRecordAction";

	public SaveRecordAction(IWorkbenchWindow win) {
		window = win;
		setId(ACTION_ID);
		setActionDefinitionId(ACTION_ID);
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("/icons/saveRecord16x16.png");
		setImageDescriptor(ImageDescriptor.createFromURL(fileURL));
	}

	@Override
	public void run() {
		IWorkbenchPage page = window.getActivePage();
		FormEditor editor = (FormEditor) page.getActiveEditor();
		if (editor == null) {
			return;
		}
		if (editor.isEnteredCorrectData()) {
			if (editor != null && editor.isDirty()) {
				page.saveEditor(editor, false);
				editor.refreshAll();
			}
		} else {
			MessageBox box = new MessageBox(window.getShell(), SWT.ICON_WARNING);
			box.setText("Warning");
			box.setMessage("Record can't be saved due the input mistake. Please check the entered data.");
			int buttonID = box.open();
		}
	}

}
