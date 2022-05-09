package rcp3project;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MyTitleAreaDialog extends TitleAreaDialog {
	private String previousName;
	private String newName;
	private Text previousNameText;
	private Text newNameText;

	public MyTitleAreaDialog(Shell parentShell, String prevName) {
		super(parentShell);
		this.previousName = prevName;
		this.newName = prevName;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Do you confirm renaming of the folder?");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.BORDER);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		createPreviousName(container);
		createNewName(container);
		return area;
	}

	private void createPreviousName(Composite container) {
		Label lbPrevName = new Label(container, SWT.NONE);
		lbPrevName.setText("Initial folder name");

		GridData dataPrevName = new GridData();
		dataPrevName.grabExcessHorizontalSpace = true;
		dataPrevName.horizontalAlignment = GridData.FILL;

		previousNameText = new Text(container, SWT.BORDER);
		previousNameText.setLayoutData(dataPrevName);
		previousNameText.setText(previousName);
		previousNameText.setEnabled(false);
	}

	private void createNewName(Composite container) {
		Label lbNewName = new Label(container, SWT.NONE);
		lbNewName.setText("New folder name");

		GridData dataLastName = new GridData();
		dataLastName.grabExcessHorizontalSpace = true;
		dataLastName.horizontalAlignment = GridData.FILL;
		newNameText = new Text(container, SWT.BORDER);
		newNameText.setLayoutData(dataLastName);
		newNameText.setText(newName);
		newNameText.setSelection(0);

	}

	@Override
	protected boolean isResizable() {
		return true; // possible better false
	}

	private void saveNewName() {
		if (newNameText.getText() == null || newNameText.getText().isEmpty()) {
			newName = previousName;
		} else {
			newName = newNameText.getText();
		}
	}

	@Override
	protected void okPressed() {
		saveNewName();
		super.okPressed();
	}

	public String getNewName() {
		return newName;
	}

}
