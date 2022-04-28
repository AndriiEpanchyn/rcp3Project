package rcp3project;

import org.eclipse.ui.IFolderLayout;
//import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

//import rcp3project.NavigationView;


public class Perspective implements IPerspectiveFactory {

	@Override	
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		layout.addStandaloneView(NavigationView.ID,  false,
				IPageLayout.LEFT, 0.35f, editorArea);
		
		IFolderLayout folder = layout.createFolder("students",
				IPageLayout.TOP, 0.65f, editorArea);
		
		folder.addPlaceholder(FormView.ID + ":*");
		folder.addView(FormView.ID);
		//folder.addView(FormView.ID);
		
		layout.getViewLayout(NavigationView.ID).setCloseable(false);
		
//		layout.addView(FormView.ID, IPageLayout.LEFT, 0.65f, editorArea);
//		layout.getViewLayout(FormView.ID).setCloseable(false);
//		layout.addPlaceholder(FormView.ID, IPageLayout.LEFT, 0.65f, editorArea);
	}
}
