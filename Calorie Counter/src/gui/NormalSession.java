package gui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import scraps.ExitMenuItem;
import scraps.LogOutMenuItem;
import scraps.User;
import scraps.UserInfo;
import server.Database;

/**
 * The NormalSession class is mainly used to check if the user is new to the
 * application or not. The reason for this is initializing further information
 * about the user.
 * 
 * @author Dragos
 *
 */

public class NormalSession {

	private Database database;
	private User user;
	private Display display;
	private Shell normalSessionShell;
	@SuppressWarnings("unused")
	private String userLine = null;

	public NormalSession(Database memoryDatabase, User user) {

		this.database = memoryDatabase;
		this.user = user;
		init();
	}

	private void init() {
		display = new Display();
		normalSessionShellConfig();
		display.dispose();
	}

	private void normalSessionShellConfig() {
		normalSessionShell = new Shell(display);
		normalSessionShell.setSize(500, 250);
		normalSessionShell.setText("Weight Maintenance");
		normalSessionShell.setLayout(new FormLayout());

		Menu menu = new Menu(normalSessionShell, SWT.BAR);
		setMenu(menu);

		initializeSession();

		normalSessionShell.setMenuBar(menu);
		normalSessionShell.open();
		while (!normalSessionShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private void setMenu(Menu menu) {
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

		MenuItem logoutButton = new MenuItem(menu, SWT.MENU);
		logoutButton.setText("Log out");
		new LogOutMenuItem(logoutButton, display);

		MenuItem exitButton = new MenuItem(menu, SWT.MENU);
		exitButton.setText("Exit");
		new ExitMenuItem(exitButton, display);
	}

	private void initializeSession() {
		// checkUserDataFromFile();
		checkUserDataFromDataBase();
	}

	private void checkUserDataFromDataBase() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String userName = "UserScoala";
			String password = "UserScoala";
			Connection connection = DriverManager.getConnection(url, userName, password);
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement
					.executeQuery("select * from userinfo where username = '" + user.getUserName() + "'");
			resultSet.next();

			if (resultSet.getString(4) == null) {
				firstTime();
			} else {

				int ageValue = resultSet.getInt(4);
				double weightValue = Double.parseDouble(resultSet.getString(5));
				int heightValue = resultSet.getInt(6);
				char sex = resultSet.getString(7).charAt(0);
				new MainApp(database, display, normalSessionShell, user,
						new UserInfo(user, ageValue, weightValue, heightValue, sex));
			}

			connection.close();
			statement.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void checkUserDataFromFile() {
		try {
			FileReader fileReader = new FileReader("UserInfo");
			Scanner sc = new Scanner(fileReader);
			String userLine = null;
			while (sc.hasNext()) {
				String line = sc.nextLine();
				String name = line.substring(0, line.indexOf(":"));
				if (name.equals(user.getUserName())) {
					userLine = line;
					this.userLine = line;
				}
			}
			sc.close();
			if (userLine.equals(user.getUserName() + ":")) {
				firstTime();
			} else {
				int ageValue = Integer.parseInt(userLine.substring(userLine.indexOf(":") + 2, userLine.indexOf(",")));
				double weightValue = Double
						.parseDouble(userLine.substring(userLine.indexOf(",") + 2, userLine.lastIndexOf(",")));
				int heightValue = Integer
						.parseInt(userLine.substring(userLine.lastIndexOf(",") + 2, userLine.indexOf(";")));
				char sex = userLine.charAt(userLine.length() - 1);
				new MainApp(database, display, normalSessionShell, user,
						new UserInfo(user, ageValue, weightValue, heightValue, sex));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void firstTime() {
		Composite firstTimeComposite = new Composite(normalSessionShell, SWT.None);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.top = new FormAttachment(0);
		formData.bottom = new FormAttachment(80);
		firstTimeComposite.setLayout(new FormLayout());
		firstTimeComposite.setLayoutData(formData);

		Label infoLabel = new Label(firstTimeComposite, SWT.NONE);
		infoLabel.setText("As this is your first time logging in, let's set up your data.");
		formData = new FormData();
		formData.left = new FormAttachment(19);
		formData.top = new FormAttachment(15);
		infoLabel.setLayoutData(formData);

		Label ageLabel = new Label(firstTimeComposite, SWT.None);
		ageLabel.setText("Age:");
		formData = new FormData();
		formData.left = new FormAttachment(45);
		formData.top = new FormAttachment(infoLabel, 15, SWT.BOTTOM);
		ageLabel.setLayoutData(formData);

		Text ageText = new Text(firstTimeComposite, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(ageLabel, 3, SWT.RIGHT);
		formData.top = new FormAttachment(ageLabel, 0, SWT.TOP);
		ageText.setLayoutData(formData);

		Label weightLabel = new Label(firstTimeComposite, SWT.None);
		weightLabel.setText("Weight in kilograms:");
		formData = new FormData();
		formData.right = new FormAttachment(ageLabel, 0, SWT.RIGHT);
		formData.top = new FormAttachment(ageLabel, 10, SWT.BOTTOM);
		weightLabel.setLayoutData(formData);

		Text weightText = new Text(firstTimeComposite, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(weightLabel, 3, SWT.RIGHT);
		formData.top = new FormAttachment(weightLabel, 0, SWT.TOP);
		weightText.setLayoutData(formData);

		Label heightLabel = new Label(firstTimeComposite, SWT.None);
		heightLabel.setText("Height in centimeters:");
		formData = new FormData();
		formData.right = new FormAttachment(weightLabel, 0, SWT.RIGHT);
		formData.top = new FormAttachment(weightLabel, 10, SWT.BOTTOM);
		heightLabel.setLayoutData(formData);

		Text heightText = new Text(firstTimeComposite, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(heightLabel, 3, SWT.RIGHT);
		formData.top = new FormAttachment(heightLabel, 0, SWT.TOP);
		heightText.setLayoutData(formData);

		Button femaleButtonRadio = new Button(firstTimeComposite, SWT.RADIO);
		femaleButtonRadio.setText("Female");
		formData = new FormData();
		formData.top = new FormAttachment(heightText, 10, SWT.BOTTOM);
		formData.right = new FormAttachment(heightText, 0, SWT.RIGHT);
		femaleButtonRadio.setLayoutData(formData);

		Button maleButtonRadio = new Button(firstTimeComposite, SWT.RADIO);
		maleButtonRadio.setText("Male");
		formData = new FormData();
		formData.top = new FormAttachment(femaleButtonRadio, 0, SWT.TOP);
		formData.right = new FormAttachment(femaleButtonRadio, -3, SWT.LEFT);
		maleButtonRadio.setLayoutData(formData);

		Label sexLabel = new Label(firstTimeComposite, SWT.None);
		sexLabel.setText("Sex:");
		formData = new FormData();
		formData.top = new FormAttachment(femaleButtonRadio, 0, SWT.TOP);
		formData.right = new FormAttachment(maleButtonRadio, -5, SWT.LEFT);
		sexLabel.setLayoutData(formData);

		Button setButton = new Button(firstTimeComposite, SWT.PUSH);
		setButton.setText("SET");
		formData = new FormData();
		formData.left = new FormAttachment(weightText, 10, SWT.RIGHT);
		formData.top = new FormAttachment(weightText, -2, SWT.TOP);
		setButton.setLayoutData(formData);
		setButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell verificationShell = new Shell(display);
				verificationShell.setLayout(new FormLayout());
				verificationShell.setText("Info");
				verificationShell.setSize(300, 200);

				Label infoLabel = new Label(verificationShell, SWT.None);
				infoLabel.setText("Are you sure this is your corect data?");
				FormData formData = new FormData();
				formData.top = new FormAttachment(10);
				formData.left = new FormAttachment(16);
				infoLabel.setLayoutData(formData);

				Button yesButton = new Button(verificationShell, SWT.PUSH);
				yesButton.setText("YES");
				formData = new FormData();
				formData.top = new FormAttachment(infoLabel, 10, SWT.BOTTOM);
				formData.left = new FormAttachment(infoLabel, 0, SWT.LEFT);
				yesButton.setLayoutData(formData);
				yesButton.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						char sex;
						if (maleButtonRadio.getSelection()) {
							sex = 'M';
						} else {
							sex = 'F';
						}

						setUser(ageText.getText(), weightText.getText(), heightText.getText(), sex);
					}
				});

				Button noButton = new Button(verificationShell, SWT.PUSH);
				noButton.setText("NO");
				formData = new FormData();
				formData.top = new FormAttachment(infoLabel, 10, SWT.BOTTOM);
				formData.right = new FormAttachment(infoLabel, 0, SWT.RIGHT);
				noButton.setLayoutData(formData);
				noButton.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						verificationShell.close();
					}
				});

				Label ageLabel = new Label(verificationShell, SWT.None);
				ageLabel.setText("Age: " + ageText.getText() + " years old.");
				formData = new FormData();
				formData.left = new FormAttachment(yesButton, 16, SWT.RIGHT);
				formData.top = new FormAttachment(infoLabel, 25, SWT.BOTTOM);
				ageLabel.setLayoutData(formData);

				Label weightLabel = new Label(verificationShell, SWT.None);
				weightLabel.setText("Weight: " + weightText.getText() + "kg.");
				formData = new FormData();
				formData.right = new FormAttachment(ageLabel, 0, SWT.RIGHT);
				formData.top = new FormAttachment(ageLabel, 10, SWT.BOTTOM);
				weightLabel.setLayoutData(formData);

				Label heightLabel = new Label(verificationShell, SWT.None);
				heightLabel.setText("Height: " + heightText.getText() + "cm.");
				formData = new FormData();
				formData.right = new FormAttachment(ageLabel, 0, SWT.RIGHT);
				formData.top = new FormAttachment(weightLabel, 10, SWT.BOTTOM);
				heightLabel.setLayoutData(formData);

				Label sexLabel = new Label(verificationShell, SWT.None);
				if (maleButtonRadio.getSelection()) {
					sexLabel.setText("Sex: Male");
				} else {
					sexLabel.setText("Sex: Female");
				}
				formData = new FormData();
				formData.right = new FormAttachment(heightLabel, 0, SWT.RIGHT);
				formData.top = new FormAttachment(heightLabel, 10, SWT.BOTTOM);
				sexLabel.setLayoutData(formData);

				verificationShell.open();
			}
		});
	}

	private void setUser(String age, String weight, String height, char sex) {
		MessageBox messageBox = new MessageBox(normalSessionShell);
		messageBox.setText("Info");
		boolean fieldsAreCompleted = true;
		boolean dataIsCorect = true;
		if (age.equals("")) {
			messageBox.setMessage("Enter your age.");
			messageBox.open();
			fieldsAreCompleted = false;
		} else if (weight.equals("")) {
			messageBox.setMessage("Enter your weight.");
			messageBox.open();
			fieldsAreCompleted = false;
		} else if (height.equals("")) {
			messageBox.setMessage("Enter your height.");
			messageBox.open();
			fieldsAreCompleted = false;
		} else if (weight.contains(".") && weight.substring(weight.indexOf(".") + 1).contains(".")) {
			messageBox.setMessage(
					"Weight must be a pozitive number, only containing digits and '.' character ONCE, for data precision.");
			messageBox.open();
			fieldsAreCompleted = false;
		} else if (!Character.isDigit(weight.charAt(0)) || !Character.isDigit(weight.charAt(weight.length() - 1))) {
			messageBox.setMessage("Invalid weight data.");
			messageBox.open();
			fieldsAreCompleted = false;
		}
		if (fieldsAreCompleted) {
			for (int i = 0; i < age.length(); i++) {
				if (!Character.isDigit(age.charAt(i))) {
					messageBox.setMessage("Age must be a pozitive number, only containing digits.");
					messageBox.open();
					dataIsCorect = false;
					break;
				}
			}
			for (int i = 0; i < weight.length(); i++) {
				if (Character.isDigit(weight.charAt(i)) == false && weight.charAt(i) != '.') {
					messageBox.setMessage(
							"Weight must be a pozitive number, only containing digits and '.' character for data precision.");
					messageBox.open();
					dataIsCorect = false;
					break;
				}
			}
			for (int i = 0; i < height.length(); i++) {
				if (Character.isDigit(height.charAt(i)) == false) {
					messageBox.setMessage("Height must be a pozitive number, only containing digits.");
					messageBox.open();
					dataIsCorect = false;
					break;
				}
			}
		}

		if (dataIsCorect && fieldsAreCompleted) {
			//database.SetUserInfoInFile(user, age, weight, height, sex);
			database.SetUserInfoInDB(user, age, weight, height, sex);
			display.dispose();
			new NormalSession(database, user);
		}
	}
}