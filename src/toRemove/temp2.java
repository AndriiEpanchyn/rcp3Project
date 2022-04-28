package toRemove;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class temp2 {
	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display, SWT.SHELL_TRIM | SWT.DOUBLE_BUFFERED);
		shell.setText("Snippet 355");
		final Image image = display.getSystemImage (SWT.ICON_QUESTION);
		shell.addListener (SWT.Paint, e -> {
			Rectangle rect = image.getBounds ();
			int width = rect.width;
			int height = rect.height;
			GC gc = e.gc;
			int x = 10, y = 10;
			gc.drawImage (image, 0, 0, width, height, x, y, width, height);
			gc.drawImage (image, 0, 0, width, height, x+width, y, (int)Math.round(width * 0.5), (int)Math.round(height * 0.5));
			gc.drawImage (image, 0, 0, width, height, x+width+(int)Math.round(width * 0.5), y, width * 2, height * 2);
		});
		shell.setSize (600, 400);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();
	}
}
