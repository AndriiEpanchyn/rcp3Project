package rcp3project;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GetFolderNameTitleAreaDialog extends TitleAreaDialog {
	private String newName;
	private Text nameText;

	public GetFolderNameTitleAreaDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Enter new object name");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.BORDER);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		createName(container);
		return area;
	}

	private void createName(Composite container) {
		Label lbNewName = new Label(container, SWT.NONE);
		lbNewName.setText("Enter new object name");

		GridData dataLastName = new GridData();
		dataLastName.grabExcessHorizontalSpace = true;
		dataLastName.horizontalAlignment = GridData.FILL;
		nameText = new Text(container, SWT.BORDER);
		nameText.setLayoutData(dataLastName);
		nameText.setText("New object");
		nameText.setSelection(0, 10);
		nameText.addVerifyListener(GetFolderNameTitleAreaDialog::ensureTextContainsOnlyTwoWordsWithSpaceAsDelimeter);
	}

	@Override
	protected boolean isResizable() {
		return false; // possible better false
	}

	private void saveNewName() {
		if (nameText.getText() == null || nameText.getText().isEmpty()) {
			newName = "";
		} else {
			newName = nameText.getText();
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

	private static void ensureTextContainsOnlyTwoWordsWithSpaceAsDelimeter(VerifyEvent e) {
		String currentChar = e.text;
		String text = ((Text) e.widget).getText() + currentChar;
		e.doit = (text.matches("[a-zA-Zà-ÿÀ-ß³²¿¯ºª']+[ ]{0,1}[a-zA-Zà-ÿÀ-ß³²¿¯ºª' ]*") && text.length() > 0);
	}

}
