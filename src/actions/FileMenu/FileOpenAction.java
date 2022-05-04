package actions.FileMenu;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;

import savers.FileReadManager;

public class FileOpenAction extends Action {
 ApplicationWindow _window;
 
 public FileOpenAction(ApplicationWindow window) {
	 this._window=window;
	 setText("Open file \tCtrl+O");
	 setToolTipText("Open file");
 }
	public void run() {
		FileReadManager.execute(_window);
	}
	
}
