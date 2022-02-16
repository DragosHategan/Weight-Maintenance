package gui;

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import scraps.UserInfo;

/**
 * The CalorieCalculator class is used to let the user obtain it's personal data
 * about the amount of calories needed in order to lose, maintain, or gain
 * weight. Further information is displayed in the app about the user's
 * situation and other data about the calculator.
 * 
 * @author Dragos
 *
 */

public class CalorieCalculator {

	private Shell calorieCalculatorShell;
	private Display display;
	private UserInfo userInfo;
	private double bmrValue;
	private Table weightLossTable;
	private Table weightGainTable;
	private List<TableItem> weightLossTableItemList = new ArrayList<>();
	private List<TableItem> weightGainTableItemList = new ArrayList<>();

	public CalorieCalculator(Display display, UserInfo userInfo) {
		this.display = display;
		this.userInfo = userInfo;
		init();
	}

	private void init() {
		calorieCalculatorShellConfig();
	}

	private void calorieCalculatorShellConfig() {
		calorieCalculatorShell = new Shell(display);
		calorieCalculatorShell.setSize(550, 575);
		calorieCalculatorShell.setText("Calorie Calculator");
		calorieCalculatorShell.setLayout(new FormLayout());

		initializingShell();

		calorieCalculatorShell.open();
	}

	private void initializingShell() {
		bmrCompositeInitialization();

		setTables();

		setActivityButtons();

		Button cautionLabel = new Button(calorieCalculatorShell, SWT.PUSH);
		cautionLabel.setText("! CAUTION !");
		FormData formData = new FormData();
		formData.bottom = new FormAttachment(weightGainTable, -5, SWT.TOP);
		formData.right = new FormAttachment(weightGainTable, 35, SWT.LEFT);
		cautionLabel.setLayoutData(formData);
		cautionLabel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				cautionShell();
			}
		});
	}

	private void cautionShell() {
		Shell cautionShell = new Shell(display);
		cautionShell.setText("CAUTION");
		cautionShell.setSize(305, 80);
		cautionShell.setLayout(new FormLayout());

		Label infoLabelNo1 = new Label(cautionShell, SWT.None);
		FormData formData = new FormData();
		formData.top = new FormAttachment(8);
		formData.left = new FormAttachment(3);
		infoLabelNo1.setLayoutData(formData);

		Label infoLabelNo2 = new Label(cautionShell, SWT.None);
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo1, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo2.setLayoutData(formData);

		if (userInfo.getSex() == 'F') {
			infoLabelNo1.setText("Please consult a doctor if you consume less than");
			infoLabelNo2.setText("the minimum recomandation of 1200 calories a day.");
		} else {
			infoLabelNo1.setText("Please consult a doctor if you consume less than");
			infoLabelNo2.setText("the minimum recomandation of 1500 calories a day.");
		}

		cautionShell.open();
	}

	private void bmrCompositeInitialization() {
		Composite bmrComposite = new Composite(calorieCalculatorShell, SWT.BORDER);
		FormData formData = new FormData();
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.top = new FormAttachment(0);
		formData.bottom = new FormAttachment(7);
		bmrComposite.setLayout(new FormLayout());
		bmrComposite.setLayoutData(formData);

		if (userInfo.getSex() == 'M') {
			bmrValue = 10 * userInfo.getWeightKG() + 6.25 * userInfo.getHeightCM() - 5 * userInfo.getAge() + 5;
		} else {
			bmrValue = 10 * userInfo.getWeightKG() + 6.25 * userInfo.getHeightCM() - 5 * userInfo.getAge() - 161;
		}

		Label bmrLabel = new Label(bmrComposite, SWT.None);
		bmrLabel.setText("Basal Metabolic Rate (BMR) = " + Math.round(bmrValue));
		formData = new FormData();
		formData.left = new FormAttachment(28);
		formData.top = new FormAttachment(30);
		bmrLabel.setLayoutData(formData);

		Button bmrInfoButton = new Button(bmrComposite, SWT.PUSH);
		bmrInfoButton.setText("INFO");
		formData = new FormData();
		formData.top = new FormAttachment(bmrLabel, -5, SWT.TOP);
		formData.left = new FormAttachment(bmrLabel, 5, SWT.RIGHT);
		bmrInfoButton.setLayoutData(formData);
		bmrInfoButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				bmiInfoShell();
			}
		});
	}

	private void setActivityButtons() {
		FormData formData;
		Label infoAboutResult = new Label(calorieCalculatorShell, SWT.None);
		infoAboutResult.setText(
				"The following results are based on your current body data, combined with your activity level.");
		formData = new FormData();
		formData.top = new FormAttachment(10);
		formData.left = new FormAttachment(4);
		infoAboutResult.setLayoutData(formData);

		Label infoAboutActivity = new Label(calorieCalculatorShell, SWT.None);
		infoAboutActivity.setText("Choose your current activity level:");
		formData = new FormData();
		formData.top = new FormAttachment(infoAboutResult, 15, SWT.BOTTOM);
		formData.left = new FormAttachment(25);
		infoAboutActivity.setLayoutData(formData);

		Label maintananceValueLabel = new Label(calorieCalculatorShell, SWT.None);
		maintananceValueLabel.setText("Calories intake for weight maintenance:           ");
		formData = new FormData();
		formData.left = new FormAttachment(28);
		formData.top = new FormAttachment(57);
		maintananceValueLabel.setLayoutData(formData);

		Button bmrRadioButton = new Button(calorieCalculatorShell, SWT.RADIO);
		bmrRadioButton.setText("BMR");
		formData = new FormData();
		formData.top = new FormAttachment(infoAboutActivity, 10, SWT.BOTTOM);
		formData.left = new FormAttachment(infoAboutActivity, 0, SWT.LEFT);
		bmrRadioButton.setLayoutData(formData);
		bmrRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				calculateMaintenanceAndFillTables(1, maintananceValueLabel);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button sedentaryRadioButton = new Button(calorieCalculatorShell, SWT.RADIO);
		sedentaryRadioButton.setText("Sedentary: Little or no exercise.");
		formData = new FormData();
		formData.top = new FormAttachment(bmrRadioButton, 5, SWT.BOTTOM);
		formData.left = new FormAttachment(infoAboutActivity, 0, SWT.LEFT);
		sedentaryRadioButton.setLayoutData(formData);
		sedentaryRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				calculateMaintenanceAndFillTables(1.2, maintananceValueLabel);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button lightRadioButton = new Button(calorieCalculatorShell, SWT.RADIO);
		lightRadioButton.setText("Light: Exercise 1-3 times/week");
		formData = new FormData();
		formData.top = new FormAttachment(sedentaryRadioButton, 5, SWT.BOTTOM);
		formData.left = new FormAttachment(infoAboutActivity, 0, SWT.LEFT);
		lightRadioButton.setLayoutData(formData);
		lightRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				calculateMaintenanceAndFillTables(1.375, maintananceValueLabel);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		Button moderateRadioButton = new Button(calorieCalculatorShell, SWT.RADIO);
		moderateRadioButton.setText("Moderate: Exercise 4-5 times/week");
		formData = new FormData();
		formData.top = new FormAttachment(lightRadioButton, 5, SWT.BOTTOM);
		formData.left = new FormAttachment(infoAboutActivity, 0, SWT.LEFT);
		moderateRadioButton.setLayoutData(formData);
		moderateRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				calculateMaintenanceAndFillTables(1.465, maintananceValueLabel);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button activeRadioButton = new Button(calorieCalculatorShell, SWT.RADIO);
		activeRadioButton.setText("Active: Daily exercise or intense exercise 3-4 times/week");
		formData = new FormData();
		formData.top = new FormAttachment(moderateRadioButton, 5, SWT.BOTTOM);
		formData.left = new FormAttachment(infoAboutActivity, 0, SWT.LEFT);
		activeRadioButton.setLayoutData(formData);
		activeRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				calculateMaintenanceAndFillTables(1.55, maintananceValueLabel);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button veryActiveRadioButton = new Button(calorieCalculatorShell, SWT.RADIO);
		veryActiveRadioButton.setText("Very Active: Intense exercise 6-7 times/week");
		formData = new FormData();
		formData.top = new FormAttachment(activeRadioButton, 5, SWT.BOTTOM);
		formData.left = new FormAttachment(infoAboutActivity, 0, SWT.LEFT);
		veryActiveRadioButton.setLayoutData(formData);
		veryActiveRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				calculateMaintenanceAndFillTables(1.725, maintananceValueLabel);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button extraActiveRadioButton = new Button(calorieCalculatorShell, SWT.RADIO);
		extraActiveRadioButton.setText("Extra Active: Very intense exercise daily, or physical job");
		formData = new FormData();
		formData.top = new FormAttachment(veryActiveRadioButton, 5, SWT.BOTTOM);
		formData.left = new FormAttachment(infoAboutActivity, 0, SWT.LEFT);
		extraActiveRadioButton.setLayoutData(formData);
		extraActiveRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				calculateMaintenanceAndFillTables(1.9, maintananceValueLabel);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Button activityInfoButton = new Button(calorieCalculatorShell, SWT.PUSH);
		activityInfoButton.setText("INFO");
		formData = new FormData();
		formData.top = new FormAttachment(moderateRadioButton, -5, SWT.TOP);
		formData.right = new FormAttachment(moderateRadioButton, -10, SWT.LEFT);
		activityInfoButton.setLayoutData(formData);
		activityInfoButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				openActivityInfoShell();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	private void setTables() {
		FormData formData;
		weightLossTable = new Table(calorieCalculatorShell, SWT.MULTI | SWT.BORDER);
		weightLossTable.setLinesVisible(true);
		weightLossTable.setHeaderVisible(true);
		formData = new FormData();
		formData.top = new FormAttachment(73);
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(50);
		formData.bottom = new FormAttachment(100);
		weightLossTable.setLayoutData(formData);

		TableColumn column = new TableColumn(weightLossTable, SWT.NONE);
		column.setText("Loss Level");
		column.setWidth(87);

		column = new TableColumn(weightLossTable, SWT.NONE);
		column.setText("Daily Intake");
		column.setWidth(87);

		column = new TableColumn(weightLossTable, SWT.NONE);
		column.setText("Weekly Intake");
		column.setWidth(87);

		TableItem item = new TableItem(weightLossTable, SWT.NONE);
		item.setText(0, "");
		item.setText(1, "");
		item.setText(2, "");

		item = new TableItem(weightLossTable, SWT.NONE);
		item.setText(0, "-0.25kg/week");
		weightLossTableItemList.add(item);

		item = new TableItem(weightLossTable, SWT.NONE);
		item.setText(0, "-0.5kg/week");
		weightLossTableItemList.add(item);

		item = new TableItem(weightLossTable, SWT.NONE);
		item.setText(0, "-1kg/week");
		weightLossTableItemList.add(item);

		item = new TableItem(weightLossTable, SWT.NONE);
		item.setText(0, "-1.5kg/week");
		weightLossTableItemList.add(item);

		item = new TableItem(weightLossTable, SWT.NONE);
		item.setText(0, "-2kg/week");
		weightLossTableItemList.add(item);

		weightGainTable = new Table(calorieCalculatorShell, SWT.MULTI | SWT.BORDER);
		weightGainTable.setLinesVisible(true);
		weightGainTable.setHeaderVisible(true);
		formData = new FormData();
		formData.top = new FormAttachment(73);
		formData.left = new FormAttachment(50);
		formData.right = new FormAttachment(100);
		formData.bottom = new FormAttachment(100);
		weightGainTable.setLayoutData(formData);

		column = new TableColumn(weightGainTable, SWT.NONE);
		column.setText("Gain Level");
		column.setWidth(87);

		column = new TableColumn(weightGainTable, SWT.NONE);
		column.setText("Daily Intake");
		column.setWidth(87);

		column = new TableColumn(weightGainTable, SWT.NONE);
		column.setText("Weekly Intake");
		column.setWidth(87);

		item = new TableItem(weightGainTable, SWT.NONE);
		item.setText(0, "");
		item.setText(1, "");
		item.setText(2, "");

		item = new TableItem(weightGainTable, SWT.NONE);
		item.setText(0, "+0.25kg/week");
		weightGainTableItemList.add(item);

		item = new TableItem(weightGainTable, SWT.NONE);
		item.setText(0, "+0.5kg/week");
		weightGainTableItemList.add(item);

		item = new TableItem(weightGainTable, SWT.NONE);
		item.setText(0, "+1kg/week");
		weightGainTableItemList.add(item);

		item = new TableItem(weightGainTable, SWT.NONE);
		item.setText(0, "+1.5kg/week");
		weightGainTableItemList.add(item);

		item = new TableItem(weightGainTable, SWT.NONE);
		item.setText(0, "+2kg/week");
		weightGainTableItemList.add(item);

		Label weightLossTableLabel = new Label(calorieCalculatorShell, SWT.None);
		weightLossTableLabel.setText("Weight loss");
		formData = new FormData();
		formData.left = new FormAttachment(20);
		formData.bottom = new FormAttachment(weightGainTable, -3, SWT.TOP);
		weightLossTableLabel.setLayoutData(formData);

		Label weightGainTableLabel = new Label(calorieCalculatorShell, SWT.None);
		weightGainTableLabel.setText("Weight gain");
		formData = new FormData();
		formData.right = new FormAttachment(80);
		formData.bottom = new FormAttachment(weightGainTable, -3, SWT.TOP);
		weightGainTableLabel.setLayoutData(formData);
	}

	private void openActivityInfoShell() {

		Shell activityInfoShell = new Shell(display);
		activityInfoShell.setText("Activity Level Info");
		activityInfoShell.setSize(365, 98);
		activityInfoShell.setLayout(new FormLayout());

		Label infoLabelNo1 = new Label(activityInfoShell, SWT.None);
		infoLabelNo1.setText("Exercise: 15-30 minutes of elevated heart rate activity.");
		FormData formData = new FormData();
		formData.top = new FormAttachment(8);
		formData.left = new FormAttachment(3);
		infoLabelNo1.setLayoutData(formData);

		Label infoLabelNo2 = new Label(activityInfoShell, SWT.None);
		infoLabelNo2.setText("Intense exercise: 45-120 minutes of elevated heart rate activity.");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo1, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo2.setLayoutData(formData);

		Label infoLabelNo3 = new Label(activityInfoShell, SWT.None);
		infoLabelNo3.setText("Very intense exercise: 2+ hours of elevated heart rate activity.");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo2, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo3.setLayoutData(formData);

		activityInfoShell.open();
	}

	private void calculateMaintenanceAndFillTables(double activityMultiplier, Label maintananceValueLabel) {
		maintananceValueLabel
				.setText("Calories intake for weight maintenance: " + Math.round(bmrValue * activityMultiplier));

		int i = 0;
		double caloriesPerGramOfBodyFat = 7.7;
		int intBmrValue = (int) bmrValue;

		for (TableItem tableItem : weightLossTableItemList) {
			if (i == 0) {
				int nr = (int) (intBmrValue * activityMultiplier - (250 * caloriesPerGramOfBodyFat) / 7);
				if (nr > 0) {
					tableItem.setText(1, Integer.toString(nr));
					tableItem.setText(2, Integer.toString(nr * 7));
				} else {
					tableItem.setText(1, "Unachievable");
					tableItem.setText(2, "Unachievable");
				}
			} else if (i == 1) {
				int nr = (int) (intBmrValue * activityMultiplier - (500 * caloriesPerGramOfBodyFat) / 7);
				if (nr > 0) {
					tableItem.setText(1, Integer.toString(nr));
					tableItem.setText(2, Integer.toString(nr * 7));
				} else {
					tableItem.setText(1, "Unachievable");
					tableItem.setText(2, "Unachievable");
				}
			} else if (i == 2) {
				int nr = (int) (intBmrValue * activityMultiplier - (1000 * caloriesPerGramOfBodyFat) / 7);
				if (nr > 0) {
					tableItem.setText(1, Integer.toString(nr));
					tableItem.setText(2, Integer.toString(nr * 7));
				} else {
					tableItem.setText(1, "Unachievable");
					tableItem.setText(2, "Unachievable");
				}
			} else if (i == 3) {
				int nr = (int) (intBmrValue * activityMultiplier - (1500 * caloriesPerGramOfBodyFat) / 7);
				if (nr > 0) {
					tableItem.setText(1, Integer.toString(nr));
					tableItem.setText(2, Integer.toString(nr * 7));
				} else {
					tableItem.setText(1, "Unachievable");
					tableItem.setText(2, "Unachievable");
				}
			} else if (i == 4) {
				int nr = (int) (intBmrValue * activityMultiplier - (2000 * caloriesPerGramOfBodyFat) / 7);
				if (nr > 0) {
					tableItem.setText(1, Integer.toString(nr));
					tableItem.setText(2, Integer.toString(nr * 7));
				} else {
					tableItem.setText(1, "Unachievable");
					tableItem.setText(2, "Unachievable");
				}
			}
			i++;
		}

		i = 0;
		for (TableItem tableItem : weightGainTableItemList) {
			if (i == 0) {
				int nr = (int) (intBmrValue * activityMultiplier + (250 * caloriesPerGramOfBodyFat) / 7);
				tableItem.setText(1, Integer.toString(nr));
				tableItem.setText(2, Integer.toString(nr * 7));
			} else if (i == 1) {
				int nr = (int) (intBmrValue * activityMultiplier + (500 * caloriesPerGramOfBodyFat) / 7);
				tableItem.setText(1, Integer.toString(nr));
				tableItem.setText(2, Integer.toString(nr * 7));
			} else if (i == 2) {
				int nr = (int) (intBmrValue * activityMultiplier + (1000 * caloriesPerGramOfBodyFat) / 7);
				tableItem.setText(1, Integer.toString(nr));
				tableItem.setText(2, Integer.toString(nr * 7));
			} else if (i == 3) {
				int nr = (int) (intBmrValue * activityMultiplier + (1500 * caloriesPerGramOfBodyFat) / 7);
				tableItem.setText(1, Integer.toString(nr));
				tableItem.setText(2, Integer.toString(nr * 7));
			} else if (i == 4) {
				int nr = (int) (intBmrValue * activityMultiplier + (2000 * caloriesPerGramOfBodyFat) / 7);
				tableItem.setText(1, Integer.toString(nr));
				tableItem.setText(2, Integer.toString(nr * 7));
			}
			i++;
		}
	}

	private void bmiInfoShell() {
		Shell infoShell = new Shell(display);
		infoShell.setText("BMR Info");
		infoShell.setSize(445, 125);
		infoShell.setLayout(new FormLayout());

		Label infoLabelNo1 = new Label(infoShell, SWT.None);
		infoLabelNo1.setText("Basal Metabolic Rate (BMR) is the number of calories you burn as your body");
		FormData formData = new FormData();
		formData.top = new FormAttachment(8);
		formData.left = new FormAttachment(3);
		infoLabelNo1.setLayoutData(formData);

		Label infoLabelNo2 = new Label(infoShell, SWT.None);
		infoLabelNo2.setText("performs basic (basal) life-sustaining function. Commonly also termed as");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo1, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo2.setLayoutData(formData);

		Label infoLabelNo3 = new Label(infoShell, SWT.None);
		infoLabelNo3.setText("Resting Metabolic Rate (RMR), which is the calories burned if you stayed in ");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo2, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo3.setLayoutData(formData);

		Label infoLabelNo4 = new Label(infoShell, SWT.None);
		infoLabelNo4.setText("bed all day.");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabelNo3, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabelNo4.setLayoutData(formData);

		infoShell.open();
	}
}
