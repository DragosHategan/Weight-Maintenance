package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import scraps.ExitMenuItem;
import scraps.LogOutMenuItem;
import scraps.User;
import scraps.UserInfo;
import server.Database;

/**
 * The CalorieCounter class is used to help the user track it's daily calorie
 * consumption using a food log. The app contains all the foods and their
 * caloric values a user needs for tracking. The user also has the opportunity
 * to add random sources of calories to the food log.
 * 
 * @author Dragos
 *
 */

public class CalorieCounter {

	private Database database;
	private Display display;
	private Shell calorieCounterShell;
	private User user;
	private UserInfo userinfo;

	public CalorieCounter(Database memoryDatabase, User user, UserInfo userinfo) {
		this.user = user;
		this.database = memoryDatabase;
		this.userinfo = userinfo;
		init();
	}

	private void init() {
		display = new Display();
		calorieCounterShellConfig();
		display.dispose();
	}

	private void calorieCounterShellConfig() {
		calorieCounterShell = new Shell(display);
		calorieCounterShell.setSize(500, 700);
		calorieCounterShell.setText("Calorie Counter");
		calorieCounterShell.setLayout(new GridLayout(1, true));

		Menu menu = new Menu(calorieCounterShell, SWT.BAR);

		if (user.isAdmin()) {
			MenuItem adminButton = new MenuItem(menu, SWT.MENU);
			adminButton.setText("Admin Session");
			adminButton.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					display.dispose();
					new AdminSession(database, user);
				}
			});
		}

		MenuItem maintenanceItem = new MenuItem(menu, SWT.MENU);
		maintenanceItem.setText("Weight Maintenance");
		maintenanceItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				display.dispose();
				new WeightMainteinance(database, user, userinfo);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		new UpdateData(display, menu, calorieCounterShell, userinfo, database);

		MenuItem logoutButton = new MenuItem(menu, SWT.MENU);
		logoutButton.setText("Log out");
		new LogOutMenuItem(logoutButton, display);

		MenuItem exitButton = new MenuItem(menu, SWT.MENU);
		exitButton.setText("Exit");
		new ExitMenuItem(exitButton, display);
		
		compositeInitialization();

		calorieCounterShell.setMenuBar(menu);
		calorieCounterShell.open();
		while (!calorieCounterShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private void compositeInitialization() {
		
		//FormData formData = new FormData();
		
		Composite composite1 = new Composite(calorieCounterShell, SWT.BORDER);
		composite1.setLayout(new GridLayout(2, false));
		composite1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label weightLabel = new Label(composite1, SWT.None);
		weightLabel.setText("Weight in kilograms:");
		weightLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER));
		
		Composite composite2 = new Composite(calorieCounterShell, SWT.BORDER);
		composite2.setLayout(new GridLayout(3, false));
		composite2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}
}
