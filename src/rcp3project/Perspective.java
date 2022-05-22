package rcp3project;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {
	IFolderLayout folder;

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);

		layout.addStandaloneView(NavigationView.ID, false, IPageLayout.LEFT, 0.25f, editorArea);
		// layout.addView(EmptyView.ID, IPageLayout.RIGHT, 0.75f, editorArea);

//		 folder = layout.createFolder("students", IPageLayout.TOP, 0.65f, editorArea);
//		 folder.addPlaceholder( .+":*");
		layout.getViewLayout(NavigationView.ID).setCloseable(false);
		// layout.getViewLayout(EmptyView.ID).setCloseable(true);
	}
}
