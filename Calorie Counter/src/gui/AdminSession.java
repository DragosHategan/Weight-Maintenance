package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import scraps.ExitMenuItem;
import scraps.LogOutMenuItem;
import scraps.User;
import server.Database;

/**
 * The AdminSession class offers the admin user the ability to manipulate
 * application data: users and foods.
 * 
 * @author Dragos
 *
 */

public class AdminSession {
	private Database database;
	private User user;
	private Display display;
	private Shell adminSessionShell;

	public AdminSession(Database memoryDatabase, User user) {

		this.database = memoryDatabase;
		this.user = user;
		init();
	}

	private void init() {
		display = new Display();
		adminSessionShellConfig();
		display.dispose();
	}

	private void adminSessionShellConfig() {
		adminSessionShell = new Shell(display);
		adminSessionShell.setSize(512, 288);
		adminSessionShell.setText("Weight Maintenance Admin Session");

		FillLayout layout = new FillLayout();
		adminSessionShell.setLayout(layout);

		Menu menu = new Menu(adminSessionShell, SWT.BAR);
		new ProductsSubMenu(database, display, menu, adminSessionShell);
		new UsersSubMenu(database, display, menu, adminSessionShell, user);

		MenuItem logOutButton = new MenuItem(menu, SWT.MENU);
		logOutButton.setText("Log out");
		new LogOutMenuItem(logOutButton, display);

		MenuItem exitButton = new MenuItem(menu, SWT.MENU);
		exitButton.setText("Exit");
		new ExitMenuItem(exitButton, display);

		Button normalSessionButton = new Button(adminSessionShell, SWT.PUSH);
		normalSessionButton.setText("Weight Maintenance Session");
		normalSessionButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.dispose();
				new NormalSession(database, user);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		adminSessionShell.setMenuBar(menu);
		adminSessionShell.open();
		while (!adminSessionShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
}