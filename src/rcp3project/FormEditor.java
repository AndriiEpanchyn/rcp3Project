package rcp3project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import dataModel.Node;
import dataModel.SessionManager;
import dnd.ViewerDropTargetCreator;

public class FormEditor extends EditorPart implements ISaveablePart2 {
	public static String ID = "rcp3project.FormEditor";
	private boolean dirty;

	Composite mainComposite;
	Node currentReference = SessionManager.getCurrentRefrence();
	String name = currentReference.getName();
	String group = currentReference.getParent().getName();
	String address = currentReference.getAddress();
	String city = currentReference.getCity();
	String result = String.valueOf(currentReference.getResult());
	String photoFileName = currentReference.getPhoto();

	Label labelName, labelGroup, labelAddress, labelCity, labelResult, photoLabel;
	Text textName, textGroup, textAddress, textCity, textResult;

	Image photo;
	String emptyFileName = "src/photos/_noPhoto256x256.png";

	@Override
	public void createPartControl(Composite parent) {
		GridLayout mainCompositeLayout = new GridLayout(7, true);
		mainCompositeLayout.horizontalSpacing = 5;
		mainCompositeLayout.verticalSpacing = 5;

		mainComposite = new Composite(parent, SWT.BORDER);
		mainComposite.setLayout(mainCompositeLayout);
		ViewerDropTargetCreator.create(mainComposite);

		labelName = createLabel(mainComposite, "Name");
		textName = createText(mainComposite, name);
		textName.addVerifyListener(FormEditor::ensureTextContainsOnlyTwoWordsWithSpaceAsDelimeter);
		textName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!textName.getText().equals(name)) {
					setDirty(true);
					// setPartName("*" + name);
				}
			}
		});
		setPartName(name);

		photoLabel = new Label(mainComposite, SWT.BORDER);
		photoLabel.setLayoutData(createPhotoGrid());
		photo = convertPhotoForLabel(photoFileName);
		photoLabel.setImage(photo);
		photoLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				String openedFile = chooseFile();
				File checkFile;
				if (!(openedFile == null) && !openedFile.equals("")) {
					checkFile = new File(openedFile);
					if (checkFile.exists()) {
						photoFileName = openedFile;
					}
				}
				photo = convertPhotoForLabel(photoFileName);
				photoLabel.setImage(photo);
				setDirty(true);
				// setPartName("*" + name);
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});

		labelGroup = createLabel(mainComposite, "Group");
		textGroup = createText(mainComposite, group);
		textGroup.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!textGroup.getText().equals(group)) {
					setDirty(true);
					// setPartName("*" + name);
				}
			}
		});
		textGroup.setEnabled(false);

		labelAddress = createLabel(mainComposite, "Address");
		textAddress = createText(mainComposite, address);
		textAddress.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!textAddress.getText().equals(address)) {
					setDirty(true);
					// setPartName("*" + name);
				}
			}
		});

		labelCity = createLabel(mainComposite, "City");
		textCity = createText(mainComposite, city);
		textCity.addVerifyListener(FormEditor::ensureTextContainsOnlyTwoWordsWithSpaceAsDelimeter);
		textCity.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!textCity.getText().equals(city)) {
					setDirty(true);
					// setPartName("*" + name);
				}
			}
		});

		labelResult = createLabel(mainComposite, "Result");
		textResult = createText(mainComposite, result);
		textResult.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				String string = e.text;
				e.doit = (string.matches("\\d*"));
				return;
			}

		});
		textResult.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!textResult.getText().equals(result)) {
					setDirty(true);
					// setPartName("*" + name);
				}
			}
		});

		// MenuManager help to create context menu
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(this.getEditorSite().getShell());
		mainComposite.setMenu(menu);
		ISelectionProvider ISelectionProvider = null;
		getSite().registerContextMenu(menuManager, ISelectionProvider);
		getSite().setSelectionProvider(ISelectionProvider);

		this.setDirty(false);
		this.firePropertyChange(999999);

	}

	public void refreshAll() {
		textName.redraw();
		textGroup.redraw();
		textAddress.redraw();
		textCity.redraw();
		textResult.redraw();
		photoLabel.redraw();
		NavigationView n;
		try {
			n = (NavigationView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(NavigationView.ID);
			n.redrawTree();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
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
		labelGrid.heightHint = 22;
		labelGrid.widthHint = 60;

		Label label = new Label(mainComposite, SWT.NONE);
		label.setText(strLabel);
		label.setLayoutData(labelGrid);

		return label;
	}

	private Text createText(Composite parent, String label) {
		GridData textGrid = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_END);
		textGrid.grabExcessHorizontalSpace = true;
		textGrid.grabExcessVerticalSpace = false;
		textGrid.widthHint = 120;
		textGrid.heightHint = 18;
		textGrid.verticalAlignment = SWT.TOP;
		textGrid.horizontalSpan = 2;
		textGrid.minimumWidth = 60;

		if (label == null)
			label = "";
		Text text = new Text(parent, SWT.BORDER);
		text.setText(label);
		text.setLayoutData(textGrid);
		return text;
	}

	private GridData createPhotoGrid() {
		GridData photoGrid = new GridData(GridData.FILL_BOTH);
		photoGrid.grabExcessHorizontalSpace = true;
		photoGrid.grabExcessVerticalSpace = true;
		photoGrid.widthHint = 180;
		photoGrid.heightHint = 180;
		photoGrid.horizontalSpan = 4;
		photoGrid.verticalSpan = 5;
		photoGrid.minimumWidth = 100;
		return photoGrid;
	}

	private Image convertPhotoForLabel(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			fileName = emptyFileName;
		}
		if (new File(fileName).isAbsolute()) {
			photo = new Image(photoLabel.getShell().getDisplay(), fileName);
		} else {
			InputStream stream = Application.class.getClassLoader().getResourceAsStream(fileName);
			try {
				photo = new Image(photoLabel.getShell().getDisplay(), stream);
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		final Image answer = new Image(photoLabel.getShell().getDisplay(), photo.getImageData().scaledTo(256, 256));
		photo.dispose();
		return answer;
	}

	public Node getEditorsNode() {
		return currentReference;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (currentReference != null) {
			currentReference.setName(textName.getText());
			currentReference.setParent(currentReference.getParent());
			currentReference.setAddress(textAddress.getText());
			currentReference.setCity(textCity.getText());
			currentReference.setResult(Integer.parseInt(textResult.getText()));
			currentReference.setPhoto(photoFileName);
			SessionManager.setCurrentRefrence(currentReference);
			setPartName(currentReference.getName());
		}
		this.firePropertyChange(999999);
		setDirty(false);
		refreshAll();
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof EditorInput)) {
			throw new PartInitException("Invalid Input: Must be " + EditorInput.class.getName());
		}
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		return this.dirty;
	}

	public void setDirty(boolean dirty) {
		if (this.dirty != dirty) {
			this.dirty = dirty;
			this.firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	private static void ensureTextContainsOnlyTwoWordsWithSpaceAsDelimeter(VerifyEvent e) {
		String currentChar = e.text;
		String text = ((Text) e.widget).getText() + currentChar;
		e.doit = (text.matches("[a-zA-Zà-ÿÀ-ß³²¿¯ºª']+[ ]{0,1}[a-zA-Zà-ÿÀ-ß³²¿¯ºª' ]*") && text.length() > 0);
	}

	private String chooseFile() {
		final String[] FILTER_NAMES = { "PNG (*.png)", "JPG (*.JPG)", "All Files (*.*)" };
		// These filter extensions are used to filter which files are displayed.
		final String[] FILTER_EXTS = { "*.png", "*.JPG", "*.*" };
		String fileName = "";
		FileDialog dlg = new FileDialog(mainComposite.getShell(), SWT.OPEN);
		dlg.setFilterNames(FILTER_NAMES);
		dlg.setFilterExtensions(FILTER_EXTS);
		fileName = dlg.open();
		dlg.getFileName();
		if (fileName != null && !fileName.equals("")) {
			File inputFile = new File(fileName);
			if (!inputFile.exists() || !inputFile.canRead()) {
				fileName = "";
			}
		}
		return fileName;
	}

	@Override
	public int promptToSaveOnClose() {
		int answer = -1;
		if (this.isDirty()) {
			MessageBox messageBox = new MessageBox(this.getEditorSite().getShell(),
					SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL);
			messageBox.setText("Warning");
			messageBox.setMessage("Save the changes before exiting?");
			int buttonID = messageBox.open();
			switch (buttonID) {
			case SWT.YES: {
				answer = ISaveablePart2.YES;
				break;
			}
			case SWT.NO: {
				answer = ISaveablePart2.NO;
				break;
			}
			case SWT.CANCEL: {
				answer = ISaveablePart2.CANCEL;
				break;
			}
			}
		}
		return answer;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public void changeGroup() {
		currentReference = SessionManager.getCurrentRefrence();
		group = currentReference.getParent().getName();
		textGroup.setText(group);
		textGroup.redraw();
	}

}
