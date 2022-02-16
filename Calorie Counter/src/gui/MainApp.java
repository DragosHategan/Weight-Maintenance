package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import scraps.User;
import scraps.UserInfo;
import server.Database;

/**
 * The MainApp class is used to let the user choose between the two
 * ramifications of the application, the WeightMaintenance app and the
 * CalorieCounter app.
 * 
 * @version 02/05/2022 only contains the food data for the CalorieCounter app,
 * conjunctively with the full WeightMaintenance app.
 * 
 * @author Dragos
 *
 */

public class MainApp {

	private Database database;
	private Display display;
	private Shell mainAppShell;
	private User user;
	private UserInfo userInfo;

	public MainApp(Database memoryDatabase, Display display, Shell shell, User user, UserInfo userinfo) {
		this.display = display;
		this.database = memoryDatabase;
		this.mainAppShell = shell;
		this.user = user;
		this.userInfo = userinfo;
		init();
	}

	private void init() {
		
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		mainAppShell.setLayout(fillLayout);
		mainAppShell.setSize(300, 250);
		
		Button weightMaintenanceButton = new Button(mainAppShell, SWT.PUSH);
		weightMaintenanceButton.setText("Weight Maintenance");
		weightMaintenanceButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.dispose();
				new WeightMainteinance(database, user, userInfo);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		Button calorieCounterButton = new Button(mainAppShell, SWT.PUSH);
		calorieCounterButton.setText("Calorie Counter");
		calorieCounterButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.dispose();
				new CalorieCounter(database, user, userInfo);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
	}
}