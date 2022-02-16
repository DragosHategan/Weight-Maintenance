package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import scraps.User;
import server.Database;

/**
 * The UsersSubMenu class is used for the admin users to manipulate data about
 * other users, like creating, deleting, and offering other users admin rights
 * during their creation.
 * 
 * @author Dragos
 *
 */

public class UsersSubMenu {

	private Database database;
	private Display display;
	private Menu menu;
	private Shell usersSubMenuShell;
	private User user;

	public UsersSubMenu(Database memoryDatabase, Display display, Menu menu, Shell shell, User user) {
		this.database = memoryDatabase;
		this.display = display;
		this.menu = menu;
		this.usersSubMenuShell = shell;
		this.user = user;
		init();
	}

	private void init() {
		MenuItem manageUsersButton = new MenuItem(menu, SWT.CASCADE);
		manageUsersButton.setText("Manage users");

		Menu submenuUsers = new Menu(usersSubMenuShell, SWT.DROP_DOWN);
		manageUsersButton.setMenu(submenuUsers);

		MenuItem addUser = new MenuItem(submenuUsers, SWT.PUSH);
		addUser.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					AddUser();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		addUser.setText("Add user");

		MenuItem deleteUser = new MenuItem(submenuUsers, SWT.PUSH);
		deleteUser.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DeleteUser();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		deleteUser.setText("Delete user");
	}

	private void displayUserListShell() {
		Shell userListShell = new Shell(display);
		userListShell.setText("Existing users");
		userListShell.setLayout(new FormLayout());
		userListShell.setSize(260, 400);
		List<String> userNameList = new ArrayList<>();
		for (User user : database.getUsers()) {
			userNameList.add(user.getUserName());
		}

		Table userNameTable = new Table(userListShell, SWT.MULTI);
		userNameTable.setLinesVisible(true);
		FormData formData = new FormData();
		formData.top = new FormAttachment(0);
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.bottom = new FormAttachment(100);
		userNameTable.setLayoutData(formData);

		TableItem item = new TableItem(userNameTable, SWT.PUSH);

		for (int i = 0; i < userNameList.size(); i++) {
			item = new TableItem(userNameTable, SWT.PUSH);
			item.setText("" + (i + 1) + ". " + userNameList.get(i).toString());
		}

		userListShell.open();
	}

	private void AddUser() throws IOException {

		Shell addUserShell = new Shell(display);
		addUserShell.setLayout(new FormLayout());
		addUserShell.setText("Add an user");
		addUserShell.setSize(300, 200);

		Label nameLabel = new Label(addUserShell, SWT.BOLD);
		nameLabel.setText("Name:");
		FormData formData = new FormData();
		formData.left = new FormAttachment(35);
		formData.top = new FormAttachment(19);
		nameLabel.setLayoutData(formData);

		Text nameText = new Text(addUserShell, SWT.BORDER);
		formData = new FormData(100, SWT.DEFAULT);
		formData.left = new FormAttachment(nameLabel, 5, SWT.RIGHT);
		formData.top = new FormAttachment(nameLabel, 0, SWT.CENTER);
		nameText.setLayoutData(formData);

		Label passwordLabel = new Label(addUserShell, SWT.BOLD);
		passwordLabel.setText("Password:");
		formData = new FormData();
		formData.right = new FormAttachment(nameLabel, 0, SWT.RIGHT);
		formData.top = new FormAttachment(nameLabel, 10, SWT.BOTTOM);
		passwordLabel.setLayoutData(formData);

		Text passwordText = new Text(addUserShell, SWT.BORDER | SWT.PASSWORD);
		formData = new FormData(100, SWT.DEFAULT);
		formData.left = new FormAttachment(passwordLabel, 5, SWT.RIGHT);
		formData.top = new FormAttachment(passwordLabel, 0, SWT.CENTER);
		passwordText.setLayoutData(formData);

		Label confirmPasswordLabel = new Label(addUserShell, SWT.BOLD);
		confirmPasswordLabel.setText("Confirm Password:");
		formData = new FormData();
		formData.right = new FormAttachment(passwordLabel, 0, SWT.RIGHT);
		formData.top = new FormAttachment(passwordLabel, 10, SWT.BOTTOM);
		confirmPasswordLabel.setLayoutData(formData);

		Text confirmPasswordText = new Text(addUserShell, SWT.BORDER | SWT.PASSWORD);
		formData = new FormData(100, SWT.DEFAULT);
		formData.left = new FormAttachment(confirmPasswordLabel, 5, SWT.RIGHT);
		formData.top = new FormAttachment(confirmPasswordLabel, 0, SWT.CENTER);
		confirmPasswordText.setLayoutData(formData);

		Button userList = new Button(addUserShell, SWT.PUSH);
		userList.setText("User list");
		formData = new FormData(60, SWT.DEFAULT);
		formData.left = new FormAttachment(confirmPasswordLabel, 0, SWT.LEFT);
		formData.top = new FormAttachment(confirmPasswordText, 15, SWT.BOTTOM);
		userList.setLayoutData(formData);
		userList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				displayUserListShell();
			}

		});

		Button newUserButton = new Button(addUserShell, SWT.PUSH);
		newUserButton.setText("New user");
		formData = new FormData(60, SWT.DEFAULT);
		formData.right = new FormAttachment(confirmPasswordText, 0, SWT.RIGHT);
		formData.top = new FormAttachment(confirmPasswordText, 15, SWT.BOTTOM);
		newUserButton.setLayoutData(formData);
		newUserButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				newUser(addUserShell, nameText.getText(), passwordText.getText(), confirmPasswordText.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		addUserShell.open();
	}

	private void newUser(Shell addUserShell, String name, String password, String confirm) {
		int ok = 1;

		MessageBox messageBox = new MessageBox(usersSubMenuShell, SWT.OK | SWT.ICON_INFORMATION);

		if (!password.equals(confirm)) {
			messageBox.setMessage("Wrong password confirmation.");
			ok = 0;
			messageBox.open();
		}

		for (User i : database.getUsers()) {
			if (i.getUserName().equals(name)) {
				messageBox.setMessage("Username alrady exists");
				messageBox.open();
				ok = 0;
				break;
			}
		}

		if (name.length() < 3) {
			messageBox.setMessage("Username must be at least 3 characters long.");
			messageBox.open();
			ok = 0;
		}

		if (password.length() < 5) {
			messageBox.setMessage("Password must be at least 5 characters long.");
			messageBox.open();
			ok = 0;
		}
		if (ok == 1) {
			isUserAdmin(name, password);
			addUserShell.close();
		}
	}

	private void isUserAdmin(String name, String password) {
		Shell isUserAdminShell = new Shell(display);
		isUserAdminShell.setLayout(new FormLayout());
		isUserAdminShell.setText("Is/Isn't admin");
		isUserAdminShell.setSize(300, 200);

		MessageBox messageBox = new MessageBox(usersSubMenuShell, SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setMessage("User " + " created  successfully");

		Label adminLabel = new Label(isUserAdminShell, SWT.BOLD);
		adminLabel.setText("Will be " + name + " an admin?");
		FormData formData = new FormData();
		formData.left = new FormAttachment(25);
		formData.top = new FormAttachment(25);
		adminLabel.setLayoutData(formData);

		Button yesButton = new Button(isUserAdminShell, SWT.PUSH);
		yesButton.setText("YES");
		formData = new FormData();
		formData.left = new FormAttachment(adminLabel, 0, SWT.LEFT);
		formData.top = new FormAttachment(adminLabel, 10, SWT.BOTTOM);
		yesButton.setLayoutData(formData);
		yesButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isUserAdminShell.close();
				messageBox.open();
				//database.AddUserToFile(new User(name, password, true));
				database.AddUserToDataBase(new User(name, password, true));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Button noButton = new Button(isUserAdminShell, SWT.PUSH);
		noButton.setText("NO");
		formData = new FormData();
		formData.right = new FormAttachment(adminLabel, 0, SWT.RIGHT);
		formData.top = new FormAttachment(adminLabel, 10, SWT.BOTTOM);
		noButton.setLayoutData(formData);
		noButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isUserAdminShell.close();
				messageBox.open();
				//database.AddUserToFile(new User(name, password, false));
				database.AddUserToDataBase(new User(name, password, false));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		isUserAdminShell.open();
	}

	private void DeleteUser() {

		Shell deleteUserShell = new Shell(display);
		deleteUserShell.setLayout(new FormLayout());
		deleteUserShell.setText("Delete user");
		deleteUserShell.setSize(250, 300);

		FormData data;
		Table table = new Table(deleteUserShell, SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		data = new FormData();
		data.height = 200;
		data.top = new FormAttachment(0);
		data.right = new FormAttachment(100);
		data.left = new FormAttachment(0);
		data.bottom = new FormAttachment(100);
		table.setLayoutData(data);

		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText("NAME");
		column.setWidth(225);

//		column = new TableColumn(table, SWT.NONE);
//		column.setText("Is Admin");
//		column.setWidth(100);

		for (User user : database.getUsers()) {
			if(user.isAdmin() == false) {
				TableItem item = new TableItem(table, SWT.PUSH);
				
				item.setText(0, user.getUserName());
			}
//			if (user.isAdmin()) {
//				item.setText(1, "YES");
//			} else {
//				item.setText(1, "NO");
//			}
		}

		table.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell checkShell = new Shell(display);
				checkShell.setLayout(new FormLayout());
				checkShell.setText("Delete user");
				checkShell.setSize(300, 200);

				String[] string = Arrays.toString(table.getSelection()).split("[{}]");
				checkShell.setText("Delete " + string[1]);

				Label sureLabel = new Label(checkShell, SWT.None);
				sureLabel.setText("Are you sure you want to delete user?");
				FormData formData = new FormData();
				formData.left = new FormAttachment(15);
				formData.top = new FormAttachment(30);
				sureLabel.setLayoutData(formData);

				Button yesButton = new Button(checkShell, SWT.PUSH);
				yesButton.setText("YES");
				formData = new FormData();
				formData.left = new FormAttachment(sureLabel, 0, SWT.LEFT);
				formData.top = new FormAttachment(sureLabel, 5, SWT.BOTTOM);
				yesButton.setLayoutData(formData);
				yesButton.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if (user.getUserName().equals(string[1])) {
							MessageBox messageBox = new MessageBox(usersSubMenuShell, SWT.OK | SWT.ICON_INFORMATION);
							messageBox.setMessage("You cant delete yourself.");
							messageBox.open();
							deleteUserShell.close();
							checkShell.close();
							DeleteUser();
						} else {
							for (User user : database.getUsers()) {
								if (user.getUserName().equals(string[1])) {
									//database.DeleteUserFromFile(user);
									database.DeleteUserFromDataBase(user);
								}
							}
							MessageBox messageBox = new MessageBox(usersSubMenuShell, SWT.OK | SWT.ICON_INFORMATION);
							messageBox.setMessage("User " + string[1] + " deleted successfully.");
							messageBox.open();
							deleteUserShell.close();
							checkShell.close();
							DeleteUser();
						}
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});

				Button noButton = new Button(checkShell, SWT.PUSH);
				noButton.setText("NO");
				formData = new FormData();
				formData.right = new FormAttachment(sureLabel, 0, SWT.RIGHT);
				formData.top = new FormAttachment(sureLabel, 5, SWT.BOTTOM);
				noButton.setLayoutData(formData);
				noButton.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						checkShell.close();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});
				checkShell.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		deleteUserShell.open();
	}
}
