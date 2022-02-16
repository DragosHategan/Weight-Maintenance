package scraps;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * The ExitMenuItem class is used to display a menu item in the application that
 * allows the user to exit it.
 * 
 * @author Dragos
 *
 */

public class ExitMenuItem {

	private MenuItem exitButton;
	private Display display;

	public ExitMenuItem(MenuItem exitButton, Display display) {
		this.exitButton = exitButton;
		this.display = display;
		init();
	}

	private void init() {
		exitButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				exitOutEvent();
			}

			private void exitOutEvent() {
				Shell warningShell = new Shell(display);
				warningShell.setText("Warning");
				warningShell.setLayout(new FormLayout());
				warningShell.setSize(300, 200);

				Label label = new Label(warningShell, SWT.None);
				label.setText("Are you sure you want to exit?");
				FormData formData = new FormData();
				formData.left = new FormAttachment(22);
				formData.top = new FormAttachment(25);
				label.setLayoutData(formData);

				Button yesButton = new Button(warningShell, SWT.PUSH);
				yesButton.setText("YES");
				formData = new FormData();
				formData.left = new FormAttachment(label, 0, SWT.LEFT);
				formData.top = new FormAttachment(label, 5, SWT.BOTTOM);
				yesButton.setLayoutData(formData);
				yesButton.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						display.dispose();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});

				Button noButton = new Button(warningShell, SWT.PUSH);
				noButton.setText("NO");
				formData = new FormData();
				formData.right = new FormAttachment(label, 0, SWT.RIGHT);
				formData.top = new FormAttachment(label, 5, SWT.BOTTOM);
				noButton.setLayoutData(formData);
				noButton.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						warningShell.close();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});
				warningShell.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
}
