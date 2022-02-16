package gui;

import java.text.DecimalFormat;

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
import org.eclipse.swt.widgets.Text;

import scraps.UserInfo;
import server.Database;

/**
 * The UpdateData class is used in order to let the user update it's data about
 * the: age, weight and height.
 * 
 * It is used in multiple locations.
 * 
 * @author Dragos
 *
 */

public class UpdateData {

	private Display display;
	private Menu menu;
	private Shell updateDataShell;
	private UserInfo userInfo;
	private Database database;

	public UpdateData(Display display, Menu menu, Shell shell, UserInfo userInfo, Database memoryDatabase) {
		this.menu = menu;
		this.display = display;
		this.updateDataShell = shell;
		this.userInfo = userInfo;
		this.database = memoryDatabase;
		init();
	}

	private void init() {
		MenuItem udpateData = new MenuItem(menu, SWT.CASCADE);
		udpateData.setText("Update Data");

		Menu submenuUpdateData = new Menu(updateDataShell, SWT.DROP_DOWN);
		udpateData.setMenu(submenuUpdateData);

		MenuItem changeAgeItem = new MenuItem(submenuUpdateData, SWT.PUSH);
		changeAgeItem.setText("Age");
		changeAgeItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeAge();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		MenuItem changeWeightItem = new MenuItem(submenuUpdateData, SWT.PUSH);
		changeWeightItem.setText("Weight");
		changeWeightItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeWeight();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		MenuItem changeHeigthItem = new MenuItem(submenuUpdateData, SWT.PUSH);
		changeHeigthItem.setText("Heigth");
		changeHeigthItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				changeHeigth();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	private void changeAge() {
		Shell changeAgeShell = new Shell(display);
		changeAgeShell.setText("Change Age");
		changeAgeShell.setSize(300, 150);
		changeAgeShell.setLayout(new FormLayout());

		Label currentAgeLabel = new Label(changeAgeShell, SWT.None);
		currentAgeLabel.setText("Current Age = " + userInfo.getAge());
		FormData formData = new FormData();
		formData.left = new FormAttachment(27);
		formData.top = new FormAttachment(17);
		currentAgeLabel.setLayoutData(formData);

		Label newAgeLabel = new Label(changeAgeShell, SWT.None);
		newAgeLabel.setText("New Age = ");
		formData = new FormData();
		formData.left = new FormAttachment(currentAgeLabel, 0, SWT.LEFT);
		formData.top = new FormAttachment(currentAgeLabel, 5, SWT.BOTTOM);
		newAgeLabel.setLayoutData(formData);

		Text newAgeText = new Text(changeAgeShell, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(newAgeLabel, 3, SWT.RIGHT);
		formData.top = new FormAttachment(newAgeLabel, 0, SWT.TOP);
		newAgeText.setLayoutData(formData);

		Button newAgeButton = new Button(changeAgeShell, SWT.PUSH);
		newAgeButton.setText("CHANGE");
		formData = new FormData();
		formData.right = new FormAttachment(newAgeText, 3, SWT.RIGHT);
		formData.top = new FormAttachment(newAgeText, 5, SWT.BOTTOM);
		newAgeButton.setLayoutData(formData);
		newAgeButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				changeAgeAction(newAgeText.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		changeAgeShell.open();
	}

	private void changeWeight() {
		Shell changeWeightShell = new Shell(display);
		changeWeightShell.setText("Change Weight");
		changeWeightShell.setSize(300, 150);
		changeWeightShell.setLayout(new FormLayout());

		DecimalFormat df = new DecimalFormat("0.0");

		Label currentWeightLabel = new Label(changeWeightShell, SWT.None);
		currentWeightLabel.setText("Current Weight = " + df.format(userInfo.getWeightKG()));
		FormData formData = new FormData();
		formData.left = new FormAttachment(22);
		formData.top = new FormAttachment(17);
		currentWeightLabel.setLayoutData(formData);

		Label newWeightLabel = new Label(changeWeightShell, SWT.None);
		newWeightLabel.setText("New Weight = ");
		formData = new FormData();
		formData.left = new FormAttachment(currentWeightLabel, 0, SWT.LEFT);
		formData.top = new FormAttachment(currentWeightLabel, 5, SWT.BOTTOM);
		newWeightLabel.setLayoutData(formData);

		Text newWeightText = new Text(changeWeightShell, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(newWeightLabel, 3, SWT.RIGHT);
		formData.top = new FormAttachment(newWeightLabel, 0, SWT.TOP);
		newWeightText.setLayoutData(formData);

		Button newWeightButton = new Button(changeWeightShell, SWT.PUSH);
		newWeightButton.setText("CHANGE");
		formData = new FormData();
		formData.right = new FormAttachment(newWeightText, 3, SWT.RIGHT);
		formData.top = new FormAttachment(newWeightText, 5, SWT.BOTTOM);
		newWeightButton.setLayoutData(formData);
		newWeightButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				changeWeightAction(newWeightText.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		changeWeightShell.open();
	}

	private void changeHeigth() {
		Shell changeHeigthShell = new Shell(display);
		changeHeigthShell.setText("Change Heigth");
		changeHeigthShell.setSize(300, 150);
		changeHeigthShell.setLayout(new FormLayout());

		Label currentHeigthLabel = new Label(changeHeigthShell, SWT.None);
		currentHeigthLabel.setText("Current Heigth = " + userInfo.getHeigthCM());
		FormData formData = new FormData();
		formData.left = new FormAttachment(22);
		formData.top = new FormAttachment(17);
		currentHeigthLabel.setLayoutData(formData);

		Label newHeigthLabel = new Label(changeHeigthShell, SWT.None);
		newHeigthLabel.setText("New Heigth = ");
		formData = new FormData();
		formData.left = new FormAttachment(currentHeigthLabel, 0, SWT.LEFT);
		formData.top = new FormAttachment(currentHeigthLabel, 5, SWT.BOTTOM);
		newHeigthLabel.setLayoutData(formData);

		Text newHeigthText = new Text(changeHeigthShell, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(newHeigthLabel, 3, SWT.RIGHT);
		formData.top = new FormAttachment(newHeigthLabel, 0, SWT.TOP);
		newHeigthText.setLayoutData(formData);

		Button newHeigthButton = new Button(changeHeigthShell, SWT.PUSH);
		newHeigthButton.setText("CHANGE");
		formData = new FormData();
		formData.right = new FormAttachment(newHeigthText, 3, SWT.RIGHT);
		formData.top = new FormAttachment(newHeigthText, 5, SWT.BOTTOM);
		newHeigthButton.setLayoutData(formData);
		newHeigthButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				changeHeigthAction(newHeigthText.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		changeHeigthShell.open();
	}

	private void changeAgeAction(String ageString) {
		MessageBox messageBox = new MessageBox(updateDataShell);
		messageBox.setText("Info");

		boolean dataIsCorect = true;

		if (ageString.equals("")) {
			messageBox.setMessage("Enter your age.");
			messageBox.open();
			dataIsCorect = false;
		}

		for (int i = 0; i < ageString.length(); i++) {
			if (!Character.isDigit(ageString.charAt(i))) {
				messageBox.setMessage("Age must be a pozitive number, only containing digits.");
				messageBox.open();
				dataIsCorect = false;
				break;
			}
		}

		if (dataIsCorect == true) {
			//database.SetUserInfoInFile(userInfo.getUser(), ageString, String.valueOf(userInfo.getWeightKG()),
			//		String.valueOf(userInfo.getHeigthCM()), userInfo.getSex());
			database.SetUserInfoInDB(userInfo.getUser(), ageString, String.valueOf(userInfo.getWeightKG()),
					String.valueOf(userInfo.getHeigthCM()), userInfo.getSex());
			display.dispose();
			userInfo.setAge(Integer.parseInt(ageString));
			new WeightMainteinance(database, userInfo.getUser(), userInfo);
		}
	}

	private void changeWeightAction(String weightString) {
		MessageBox messageBox = new MessageBox(updateDataShell);
		messageBox.setText("Info");

		boolean dataIsCorect = true;

		if (weightString.equals("")) {
			messageBox.setMessage("Enter your weight.");
			messageBox.open();
			dataIsCorect = false;
		} else if (weightString.contains(".") && weightString.substring(weightString.indexOf(".") + 1).contains(".")) {
			messageBox.setMessage(
					"Weight must be a pozitive number, only containing digits and '.' character ONCE, for data precision.");
			messageBox.open();
			dataIsCorect = false;
		} else if (!Character.isDigit(weightString.charAt(0))
				|| !Character.isDigit(weightString.charAt(weightString.length() - 1))) {
			messageBox.setMessage("Invalid weight data.");
			messageBox.open();
			dataIsCorect = false;
		}

		for (int i = 0; i < weightString.length(); i++) {
			if (Character.isDigit(weightString.charAt(i)) == false && weightString.charAt(i) != '.') {
				messageBox.setMessage(
						"Weight must be a pozitive number, only containing digits and '.' character for data precision.");
				messageBox.open();
				dataIsCorect = false;
				break;
			}
		}

		if (dataIsCorect == true) {
			//database.SetUserInfoInFile(userInfo.getUser(), String.valueOf(userInfo.getAge()), weightString,
			//		String.valueOf(userInfo.getHeigthCM()), userInfo.getSex());
			database.SetUserInfoInDB(userInfo.getUser(), String.valueOf(userInfo.getAge()), weightString,
					String.valueOf(userInfo.getHeigthCM()), userInfo.getSex());
			display.dispose();
			userInfo.setWeightKG(Float.parseFloat(weightString));
			new WeightMainteinance(database, userInfo.getUser(), userInfo);
		}
	}

	private void changeHeigthAction(String heigthString) {
		MessageBox messageBox = new MessageBox(updateDataShell);
		messageBox.setText("Info");

		boolean dataIsCorect = true;

		if (heigthString.equals("")) {
			messageBox.setMessage("Enter your height.");
			messageBox.open();
			dataIsCorect = false;
		}

		for (int i = 0; i < heigthString.length(); i++) {
			if (Character.isDigit(heigthString.charAt(i)) == false) {
				messageBox.setMessage("Height must be a pozitive number, only containing digits.");
				messageBox.open();
				dataIsCorect = false;
				break;
			}
		}

		if (dataIsCorect == true) {
		//	database.SetUserInfoInFile(userInfo.getUser(), String.valueOf(userInfo.getAge()),
		//			String.valueOf(userInfo.getWeightKG()), heigthString, userInfo.getSex());
			database.SetUserInfoInDB(userInfo.getUser(), String.valueOf(userInfo.getAge()),
					String.valueOf(userInfo.getWeightKG()), heigthString, userInfo.getSex());
			display.dispose();
			userInfo.setHeigthCM(Integer.parseInt(heigthString));
			new WeightMainteinance(database, userInfo.getUser(), userInfo);
		}
	}
}
