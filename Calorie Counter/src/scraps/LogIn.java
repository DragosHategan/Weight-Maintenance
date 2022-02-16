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

import gui.AdminSession;
import gui.NormalSession;
import server.Database;

/**
 * The LogIn class it's used to let an user Sign in into the account.
 * 
 * The class also gives the user the possibility to visit the Sign up window.
 * 
 * @author Dragos
 */

public class LogIn {
	private Database database;
	private Display display;
	private Shell logInShell;

	public LogIn(Database memoryDatabase) {

		this.database = memoryDatabase;
		this.init();
	}

	private void init() {
		DisplayLogIn();
	}

	private void DisplayLogIn() {
		display = new Display();
		loginShelConfig();
		display.dispose();
	}

	private void loginShelConfig() {
		logInShell = new Shell(display);
		logInShell.setSize(512, 288);
		logInShell.setText("Weight Maintenance");

		FormLayout layout = new FormLayout();
		layout.marginWidth = layout.marginHeight = 5;
		logInShell.setLayout(layout);

		Label userLabel = new Label(logInShell, SWT.NONE);
		userLabel.setText("Username");
		FormData labelFD = new FormData(55, SWT.DEFAULT);
		labelFD.top = new FormAttachment(25);
		labelFD.right = new FormAttachment(40);
		userLabel.setLayoutData(labelFD);

		Text userText = new Text(logInShell, SWT.BORDER);
		FormData fd = new FormData(100, SWT.DEFAULT);
		fd.left = new FormAttachment(userLabel, 5, SWT.RIGHT);
		fd.top = new FormAttachment(userLabel, 0, SWT.CENTER);
		userText.setLayoutData(fd);

		Label passwordLabel = new Label(logInShell, SWT.NONE);
		passwordLabel.setText("Password");
		labelFD = new FormData(55, SWT.DEFAULT);
		labelFD.top = new FormAttachment(userLabel, 15, SWT.BOTTOM);
		labelFD.left = new FormAttachment(userLabel, 0, SWT.LEFT);
		passwordLabel.setLayoutData(labelFD);

		Text passwordText = new Text(logInShell, SWT.BORDER | SWT.PASSWORD);
		fd = new FormData(100, SWT.DEFAULT);
		fd.left = new FormAttachment(passwordLabel, 5);
		fd.top = new FormAttachment(passwordLabel, 0, SWT.CENTER);
		passwordText.setLayoutData(fd);

		Button loginButton = new Button(logInShell, SWT.PUSH);
		loginButton.setText("Login");
		FormData fdButton = new FormData(55, SWT.DEFAULT);
		fdButton.left = new FormAttachment(passwordText, 0, SWT.LEFT);
		fdButton.top = new FormAttachment(passwordText, 15, SWT.BOTTOM);
		loginButton.setLayoutData(fdButton);
		loginButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LogMeIn(userText.getText(), passwordText.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Button registerButton = new Button(logInShell, SWT.PUSH);
		registerButton.setText("Register");
		fdButton = new FormData(55, SWT.DEFAULT);
		fdButton.right = new FormAttachment(passwordText, 0, SWT.RIGHT);
		fdButton.top = new FormAttachment(passwordText, 15, SWT.BOTTOM);
		registerButton.setLayoutData(fdButton);
		registerButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.dispose();
				new Register(database);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Button exitButton = new Button(logInShell, SWT.PUSH);
		exitButton.setText("EXIT");
		fdButton = new FormData(55, SWT.DEFAULT);
		fdButton.right = new FormAttachment(loginButton, 0, SWT.RIGHT);
		fdButton.top = new FormAttachment(loginButton, 10, SWT.BOTTOM);
		exitButton.setLayoutData(fdButton);
		new ExitButton(exitButton, display);

		logInShell.open();
		while (!logInShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private void LogMeIn(String userText, String passwordText) {
		int ok = 0;

		MessageBox messageBox = new MessageBox(logInShell, SWT.OK | SWT.ICON_INFORMATION);

		for (User u : database.getUsers()) {
			if (u.getUserName().equals(userText) && u.getPassword().equals(passwordText)) {
				display.dispose();
				if (u.isAdmin() == true) {
					new AdminSession(database, u);
				} else {
					new NormalSession(database, u);
				}
				ok = 1;
			}
		}
		if (ok == 0) {
			messageBox.setMessage("Wrong username or password.");
			messageBox.open();
		}
	}
}