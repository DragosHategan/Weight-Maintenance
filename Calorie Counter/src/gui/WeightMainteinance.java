package gui;

import java.text.DecimalFormat;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import scraps.ExitMenuItem;
import scraps.LogOutMenuItem;
import scraps.User;
import scraps.UserInfo;
import server.Database;

/**
 * The WeightMaintenance class is used to display user's information, like weight
 * or height, accompanied with a chart about it's health situation according to
 * BodyMassIndex (BMI) value.
 * 
 * The class contains paths to CalorieCalculator and BodyFatCalculator.
 * 
 * @author Dragos
 *
 */

public class WeightMainteinance {
	private Database database;
	private Display display;
	private Shell weightMaintenanceShell;
	private User user;
	private UserInfo userInfo;

	public WeightMainteinance(Database memoryDatabase, User user, UserInfo userinfo) {
		this.database = memoryDatabase;
		this.user = user;
		this.userInfo = userinfo;
		init();
	}

	private void init() {
		display = new Display();
		weightMaintenanceShellConfig();
		display.dispose();
	}

	private void weightMaintenanceShellConfig() {
		weightMaintenanceShell = new Shell(display);
		weightMaintenanceShell.setSize(500, 400);
		weightMaintenanceShell.setText("Weight Maintenance");
		weightMaintenanceShell.setLayout(new FormLayout());

		Menu menu = setMenu();

		setComposites();

		weightMaintenanceShell.setMenuBar(menu);
		weightMaintenanceShell.open();
		while (!weightMaintenanceShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private Menu setMenu() {
		Menu menu = new Menu(weightMaintenanceShell, SWT.BAR);

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

		MenuItem calorieCounterItem = new MenuItem(menu, SWT.MENU);
		calorieCounterItem.setText("Calorie Counter");
		calorieCounterItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				display.dispose();
				new CalorieCounter(database, user, userInfo);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		new UpdateData(display, menu, weightMaintenanceShell, userInfo, database);

		MenuItem logoutButton = new MenuItem(menu, SWT.MENU);
		logoutButton.setText("Log out");
		new LogOutMenuItem(logoutButton, display);

		MenuItem exitButton = new MenuItem(menu, SWT.MENU);
		exitButton.setText("Exit");
		new ExitMenuItem(exitButton, display);
		return menu;
	}

	private void setComposites() {
		myData();
		bmi();
		buttons();
	}

	private void buttons() {

		Composite buttonsComposite = new Composite(weightMaintenanceShell, SWT.BORDER);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.top = new FormAttachment(80);
		formData.bottom = new FormAttachment(100);
		buttonsComposite.setLayout(new FormLayout());
		buttonsComposite.setLayoutData(formData);

		Button calorieCalculatorButton = new Button(buttonsComposite, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(27);
		formData.top = new FormAttachment(32);
		calorieCalculatorButton.setText("Calorie Calculator");
		calorieCalculatorButton.setLayoutData(formData);
		calorieCalculatorButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new CalorieCalculator(display, userInfo);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button bodyFatCalculator = new Button(buttonsComposite, SWT.PUSH);
		formData = new FormData();
		formData.left = new FormAttachment(calorieCalculatorButton, 5, SWT.RIGHT);
		formData.top = new FormAttachment(32);
		bodyFatCalculator.setText("Body Fat Calculator");
		bodyFatCalculator.setLayoutData(formData);
		bodyFatCalculator.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new BodyFatCalculator(display, userInfo);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	private void myData() {

		Composite ageComposite = new Composite(weightMaintenanceShell, SWT.BORDER);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(33);
		formData.top = new FormAttachment(0);
		formData.bottom = new FormAttachment(10);
		ageComposite.setLayout(new FormLayout());
		ageComposite.setLayoutData(formData);

		Label ageLabel = new Label(ageComposite, SWT.None);
		ageLabel.setText("Age = " + userInfo.getAge() + " years old");
		formData = new FormData();
		formData.top = new FormAttachment(25);
		formData.left = new FormAttachment(19);
		ageLabel.setLayoutData(formData);

		Composite weightComposite = new Composite(weightMaintenanceShell, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(33);
		formData.right = new FormAttachment(66);
		formData.top = new FormAttachment(0);
		formData.bottom = new FormAttachment(10);
		weightComposite.setLayout(new FormLayout());
		weightComposite.setLayoutData(formData);

		DecimalFormat df = new DecimalFormat("0.0");

		Label weightLabel = new Label(weightComposite, SWT.None);
		weightLabel.setText("Weight = " + df.format(userInfo.getWeightKG()) + "kg");
		formData = new FormData();
		formData.top = new FormAttachment(25);
		formData.left = new FormAttachment(22);
		weightLabel.setLayoutData(formData);

		Composite heightComposite = new Composite(weightMaintenanceShell, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(66);
		formData.right = new FormAttachment(100);
		formData.top = new FormAttachment(0);
		formData.bottom = new FormAttachment(10);
		heightComposite.setLayout(new FormLayout());
		heightComposite.setLayoutData(formData);

		Label heightLabel = new Label(heightComposite, SWT.None);
		heightLabel.setText("Height = " + userInfo.getHeigthCM() + "cm");
		formData = new FormData();
		formData.top = new FormAttachment(25);
		formData.left = new FormAttachment(26);
		heightLabel.setLayoutData(formData);
	}

	private void bmi() {
		Composite bmiIndexComposite = new Composite(weightMaintenanceShell, SWT.BORDER);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.top = new FormAttachment(10);
		formData.bottom = new FormAttachment(80);
		bmiIndexComposite.setLayout(new FormLayout());
		bmiIndexComposite.setLayoutData(formData);

		float height = userInfo.getHeigthCM();
		height /= 100;

		Label bmiValueLabel = new Label(bmiIndexComposite, SWT.None);
		DecimalFormat df = new DecimalFormat("#.##");
		bmiValueLabel.setText("Body Mass Index (BMI) = " + df.format(userInfo.getWeightKG() / (height * height)));
		formData = new FormData();
		formData.left = new FormAttachment(33);
		formData.top = new FormAttachment(3);
		bmiValueLabel.setLayoutData(formData);

		Button infoButton = new Button(bmiIndexComposite, SWT.PUSH);
		infoButton.setText("INFO");
		formData = new FormData();
		formData.left = new FormAttachment(bmiValueLabel, 10, SWT.RIGHT);
		formData.top = new FormAttachment(bmiValueLabel, -6, SWT.TOP);
		infoButton.setLayoutData(formData);
		infoButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				bmiInfo();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Table bmiValuesTable = new Table(bmiIndexComposite, SWT.MULTI | SWT.BORDER);
		bmiValuesTable.setLinesVisible(true);
		bmiValuesTable.setHeaderVisible(true);
		formData = new FormData();
		formData.top = new FormAttachment(12);
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.bottom = new FormAttachment(100);
		bmiValuesTable.setLayoutData(formData);

		setBMITable(bmiValuesTable);
	}

	private void bmiInfo() {
		Shell bmiInfoShell = new Shell(display);
		bmiInfoShell.setText("BMI Info");
		bmiInfoShell.setSize(300, 200);
		bmiInfoShell.setLayout(new FormLayout());

		Label infoLabelNo1 = new Label(bmiInfoShell, SWT.None);
		infoLabelNo1.setText("Body mass index is a value derived from the mass");
		FormData formData = new FormData();
		formData.top = new FormAttachment(8);
		formData.left = new FormAttachment(3);
		infoLabelNo1.setLayoutData(formData);

		Label infoLabelNo2 = new Label(bmiInfoShell, SWT.None);
		infoLabelNo2.setText("and height of a person. The BMI is defined as the");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo1, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo2.setLayoutData(formData);

		Label infoLabelNo3 = new Label(bmiInfoShell, SWT.None);
		infoLabelNo3.setText("body mass divided by the square of the body height");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo2, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo3.setLayoutData(formData);

		Label infoLabelNo4 = new Label(bmiInfoShell, SWT.None);
		infoLabelNo4.setText(", and is expressed in units of kg/m², resulting mass");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo3, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo4.setLayoutData(formData);

		Label infoLabelNo5 = new Label(bmiInfoShell, SWT.None);
		infoLabelNo5.setText("in kilograms and height in metres.");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo4, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo5.setLayoutData(formData);

		Label infoLabelNo6 = new Label(bmiInfoShell, SWT.None);
		infoLabelNo6.setText("ATENTION! The BMI calculator may be inaccurate");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo5, 10, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo6.setLayoutData(formData);

		Label infoLabelNo7 = new Label(bmiInfoShell, SWT.None);
		infoLabelNo7.setText("if you have a relatively higher muscle mass.");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo6, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo7.setLayoutData(formData);

		bmiInfoShell.open();
	}

	private void setBMITable(Table bmiValuesTable) {
		TableColumn column = new TableColumn(bmiValuesTable, SWT.NONE);
		column.setText("Category");
		column.setWidth(310);

		column = new TableColumn(bmiValuesTable, SWT.NONE);
		column.setText("BMI Range");
		column.setWidth(165);

		TableItem item = new TableItem(bmiValuesTable, SWT.NONE);
		item.setText(0, "");
		item.setText(1, "");

		item = new TableItem(bmiValuesTable, SWT.NONE);
		item.setText(0, "Severe Thinness");
		item.setText(1, "< 16");

		item = new TableItem(bmiValuesTable, SWT.NONE);
		item.setText(0, "Moderate Thinness");
		item.setText(1, "16 - 17");

		item = new TableItem(bmiValuesTable, SWT.NONE);
		item.setText(0, "Mild Thinness");
		item.setText(1, "17 - 18.5");

		item = new TableItem(bmiValuesTable, SWT.NONE);
		item.setText(0, "Normal");
		item.setText(1, "18.5 - 25");

		item = new TableItem(bmiValuesTable, SWT.NONE);
		item.setText(0, "Overweight");
		item.setText(1, "25 - 30");

		item = new TableItem(bmiValuesTable, SWT.NONE);
		item.setText(0, "Obese Class I");
		item.setText(1, "30 - 35");

		item = new TableItem(bmiValuesTable, SWT.NONE);
		item.setText(0, "Obese Class II");
		item.setText(1, "35 - 40");

		item = new TableItem(bmiValuesTable, SWT.NONE);
		item.setText(0, "Obese Class III");
		item.setText(1, "40+");
	}

}
