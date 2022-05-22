package savers;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;

import dataModel.Node;
import dataModel.SessionManager;

public class FileReadManager {
	static Node node;
	StringBuilder inputString = new StringBuilder();
	private static final String[] FILTER_NAMES = { "JSON Files (*.json)", "Plain text (*.txt)", "All Files (*.*)" };
	// These filter extensions are used to filter which files are displayed.
	private static final String[] FILTER_EXTS = { "*.json", "*.txt", "*.*" };

	private static IWorkbenchWindow window;

	public static void execute(IWorkbenchWindow inWindow) {
		window = inWindow;
		String tempFileName = chooseFile();

		if (tempFileName != null && !tempFileName.equals("")) {
			Savable fileData = selectSaver(tempFileName);
			node = fileData.readFromFile(tempFileName);
			SessionManager.setSession(node);
			SessionManager.setFileName(tempFileName);
			SessionManager.isNewFile = false;
		}
	}

	private static String chooseFile() {
		String fileName = "";
		FileDialog dlg = new FileDialog(window.getShell(), SWT.OPEN);
		dlg.setFilterNames(FILTER_NAMES);
		dlg.setFilterExtensions(FILTER_EXTS);
		fileName = dlg.open();
		if (fileName != null && !fileName.equals("")) {
			File inputFile = new File(fileName);
			if (!inputFile.exists() || !inputFile.canRead()) {
				fileName = "";
			}
		}
		return fileName;
	}

	private static Savable selectSaver(String filename) {
		int start = filename.lastIndexOf(".");
		String extenstion = filename.substring(start + 1).toUpperCase();
		switch (extenstion) {
		case "JSON":
			return new JsonSaver();
//		case "TXT":
//			return new TxtSaver();
		default:
			System.out.println("Incorrect file type");
			return null;
		}
	}

}
