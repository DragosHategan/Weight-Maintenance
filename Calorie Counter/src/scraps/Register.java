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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import gui.NormalSession;
import server.Database;

/**
 * The Register class gives the user the possibility to create a new account.
 * 
 * @author Dragos
 *
 */

public class Register {

	private Database database;
	private Display display;
	private Shell registerShell;

	public Register(Database memoryDatabase) {

		this.database = memoryDatabase;
		this.init();
	}

	public void init() {
		DisplayRegister();
	}

	private void DisplayRegister() {
		display = new Display();
		registerShellConfig();
		display.dispose();
	}

	private void registerShellConfig() {
		registerShell = new Shell(display);
		registerShell.setText("Weight Maintenance");

		FormLayout layout = new FormLayout();
		layout.marginWidth = layout.marginHeight = 5;
		registerShell.setLayout(layout);

		Label userLabel = new Label(registerShell, SWT.NONE);
		userLabel.setText("Username");
		FormData labelFD = new FormData(55, SWT.DEFAULT);
		labelFD.top = new FormAttachment(20);
		labelFD.left = new FormAttachment(30);
		userLabel.setLayoutData(labelFD);

		Label passwordLabel = new Label(registerShell, SWT.NONE);
		passwordLabel.setText("Password");
		labelFD = new FormData(55, SWT.DEFAULT);
		labelFD.right = new FormAttachment(userLabel, 0, SWT.RIGHT);
		labelFD.top = new FormAttachment(userLabel, 15, SWT.BOTTOM);
		passwordLabel.setLayoutData(labelFD);

		Label password2Label = new Label(registerShell, SWT.NONE);
		password2Label.setText("Confirm password");
		labelFD = new FormData(100, SWT.DEFAULT);
		labelFD.right = new FormAttachment(userLabel, 0, SWT.RIGHT);
		labelFD.top = new FormAttachment(passwordLabel, 15, SWT.BOTTOM);
		password2Label.setLayoutData(labelFD);

		Text userText = new Text(registerShell, SWT.BORDER);
		FormData fd = new FormData(100, SWT.DEFAULT);
		fd.left = new FormAttachment(userLabel, 5);
		fd.top = new FormAttachment(userLabel, 0, SWT.CENTER);
		userText.setLayoutData(fd);

		Text passwordText = new Text(registerShell, SWT.BORDER | SWT.PASSWORD);
		fd = new FormData(100, SWT.DEFAULT);
		fd.left = new FormAttachment(passwordLabel, 5);
		fd.top = new FormAttachment(passwordLabel, 0, SWT.CENTER);
		passwordText.setLayoutData(fd);

		Text password2Text = new Text(registerShell, SWT.BORDER | SWT.PASSWORD);
		fd = new FormData(100, SWT.DEFAULT);
		fd.left = new FormAttachment(password2Label, 5);
		fd.top = new FormAttachment(password2Label, 0, SWT.CENTER);
		password2Text.setLayoutData(fd);

		Button registerButton = new Button(registerShell, SWT.PUSH);
		registerButton.setText("Register");
		FormData fdButton = new FormData(55, SWT.DEFAULT);
		fdButton.left = new FormAttachment(passwordText, 0, SWT.LEFT);
		fdButton.top = new FormAttachment(password2Text, 15, SWT.BOTTOM);
		registerButton.setLayoutData(fdButton);
		registerButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RegisterMe(userText.getText(), passwordText.getText(), password2Text.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Button backButton = new Button(registerShell, SWT.PUSH);
		backButton.setText("Back");
		fdButton = new FormData(55, SWT.DEFAULT);
		fdButton.right = new FormAttachment(password2Text, 0, SWT.RIGHT);
		fdButton.top = new FormAttachment(password2Text, 15, SWT.BOTTOM);
		backButton.setLayoutData(fdButton);
		backButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.dispose();
				new LogIn(database);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Button exitButton = new Button(registerShell, SWT.PUSH);
		exitButton.setText("EXIT");
		fdButton = new FormData(55, SWT.DEFAULT);
		fdButton.left = new FormAttachment(passwordText, 0, SWT.LEFT);
		fdButton.top = new FormAttachment(registerButton, 10, SWT.BOTTOM);
		exitButton.setLayoutData(fdButton);
		new ExitButton(exitButton, display);

		registerShell.setSize(512, 288);
		registerShell.open();
		while (!registerShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private void RegisterMe(String userName, String password, String passwordConfirmation) {

		int ok = 1;
		MessageBox messageBox = new MessageBox(registerShell, SWT.OK | SWT.ICON_INFORMATION);

		if (!password.equals(passwordConfirmation)) {
			messageBox.setMessage("Wrong password confirmation.");
			messageBox.open();
			ok = 0;
		} else if (userName.length() < 3) {
			messageBox.setMessage("Username must be at least 3 characters long.");
			messageBox.open();
			ok = 0;
		} else if (password.length() < 5) {
			messageBox.setMessage("Password must be at least 5 characters long.");
			messageBox.open();
			ok = 0;
		} else {
			for (User i : this.database.getUsers()) {
				if (i.getUserName().equals(userName)) {
					messageBox.setMessage("Username alrady exists");
					messageBox.open();
					ok = 0;
					break;
				}
			}
		}

		if (ok == 1) {
			//database.AddUserToFile(new User(userName, password, false));
			database.AddUserToDataBase(new User(userName, password, false));
			messageBox.setMessage("User " + userName + " created.");
			messageBox.open();
			display.dispose();
			new NormalSession(database, new User(userName, password, false));
		}
	}
}
