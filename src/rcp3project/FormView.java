package rcp3project;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class FormView extends ViewPart {
	public static String ID = "rcp3project.FormView";

	Composite mainComposite;
	String name = "Some name";
	String group = "Some group";
	String address = "Some address";
	String city = "Some city";
	int result = 5;
	Image img;

	Label labelName, labelGroup, label, labelCity, labelResult, photoLabel;
	Text textName, textGroup, textAddress, textCity, textResult;
    Canvas canvas;
	Image photo;
	String photoFileName = "./icons/eclipse128.png";
	
	
	public void createPartControl(Composite parent) {
		GridLayout mainCompositeLayout = new GridLayout(7, true);
		mainCompositeLayout.horizontalSpacing = 5;
		mainCompositeLayout.verticalSpacing = 5;
		
		mainComposite = new Composite(parent, SWT.BORDER);
		mainComposite.setLayout(mainCompositeLayout);

		labelName = createLabel(mainComposite, "Name");
		textName = createText(mainComposite,name);
		
		photoLabel = new Label(mainComposite, SWT.BORDER);
		photo = new Image(parent.getShell().getDisplay(), photoFileName);
		photoLabel.setLayoutData(createPhotoGrid());
		photoLabel.setImage(photo);
		//TODO ADD resize see the saved link
		
			
		labelGroup = createLabel(mainComposite, "Group");
		textGroup = createText(mainComposite, group);
		
		label = createLabel(mainComposite, "Address");
		textAddress = createText(mainComposite, address);
		
		labelCity = createLabel(mainComposite, "City");
		textCity = createText(mainComposite, city);

		labelResult = createLabel(mainComposite, "Result");
		textResult = createText(mainComposite,String.valueOf(result));
		

	}
	
	public void setFields() {
		textName.setText(name);
		textGroup.setText(group);
		textAddress.setText(address);
		textCity.setText(city);
		textResult.setText(String.valueOf(result));
		photo = img;
	}
	
	public void refreshAll() {
		textName.redraw();
		textGroup.redraw();
		textAddress.redraw();
		textCity.redraw();
		textResult.redraw();
		photoLabel.redraw();
		
	}

	@Override
	public void setFocus() {
		textName.setFocus();
	}
	
	private Label createLabel(Composite parent, String strLabel) {
		GridData labelGrid = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING);
		labelGrid.grabExcessHorizontalSpace = true;
		labelGrid.grabExcessVerticalSpace = false;
		labelGrid.horizontalSpan = 1;
		labelGrid.minimumWidth = 30;
		labelGrid.heightHint=22;
		labelGrid.widthHint=60;
		
		Label label = new Label(mainComposite, SWT.BORDER);
		label.setText(strLabel);
		label.setLayoutData(labelGrid);
		return label;
	}
	
	private Text createText(Composite parent, String label) {
		GridData textGrid = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_END);
		textGrid.grabExcessHorizontalSpace = true;
		textGrid.grabExcessVerticalSpace = false;
		textGrid.widthHint=120;
		textGrid.heightHint=18;
		textGrid.verticalAlignment = SWT.TOP;
		textGrid.horizontalSpan = 2;
		textGrid.minimumWidth = 60;
		
		Text text = new Text(parent, SWT.BORDER);
		text.setText(label);
		text.setLayoutData(textGrid);
		return text;
	}
	
	private GridData createPhotoGrid() {
		GridData photoGrid = new GridData(GridData.FILL_BOTH);
		photoGrid.grabExcessHorizontalSpace = true;
		photoGrid.grabExcessVerticalSpace = true;
		photoGrid.widthHint=180;
		photoGrid.heightHint=180;
		photoGrid.horizontalSpan = 4;
		photoGrid.verticalSpan = 5;
		photoGrid.minimumWidth = 100;
		return photoGrid;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	

	public void setGroup(String group) {
		this.group = group;
	}

	
	public void setAddress(String address) {
		this.address = address;
	}

	
	public void setCity(String city) {
		this.city = city;
	}

	
	public void setResult(int result) {
		this.result = result;
	}

	
	public void setImg(Image img) {
		this.img = img;
	}

//	public FormView( String name, String group, String address, String city, int result,
//			Image img) {
//		this.name = name;
//		this.group = group;
//		this.address = address;
//		this.city = city;
//		this.result = result;
//		this.img = img;
//		setFields();
//	}
}