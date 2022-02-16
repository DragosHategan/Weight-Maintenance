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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import scraps.UserInfo;

/**
 * The BodyFatCalculator class is used to let the user obtain it's personal data
 * about body fat percentage. Further information is displayed in the app about
 * the user's situation and other data about the calculator.
 * 
 * @author Dragos
 *
 */

public class BodyFatCalculator {

	private Shell bodyFatCalculatorShell;
	private Display display;
	private UserInfo userInfo;

	public BodyFatCalculator(Display display, UserInfo userInfo) {
		this.display = display;
		this.userInfo = userInfo;
		init();
	}

	private void init() {
		bodyFatCalculatorShellConfig();
	}

	private void bodyFatCalculatorShellConfig() {
		bodyFatCalculatorShell = new Shell(display);
		bodyFatCalculatorShell.setSize(525, 300);
		bodyFatCalculatorShell.setText("Body Fat Calculator");
		bodyFatCalculatorShell.setLayout(new FormLayout());

		initializeShell();

		bodyFatCalculatorShell.open();
	}

	private void initializeShell() {
		Table americanCouncilOnExerciseTable = new Table(bodyFatCalculatorShell, SWT.MULTI | SWT.BORDER);
		americanCouncilOnExerciseTable.setLinesVisible(true);
		americanCouncilOnExerciseTable.setHeaderVisible(true);
		FormData formData = new FormData();
		formData.top = new FormAttachment(23);
		formData.left = new FormAttachment(50);
		formData.right = new FormAttachment(94);
		formData.bottom = new FormAttachment(78);
		americanCouncilOnExerciseTable.setLayoutData(formData);

		Label tableInfoLabel = new Label(bodyFatCalculatorShell, SWT.None);
		tableInfoLabel.setText("Values for adults:");
		formData = new FormData();
		formData.bottom = new FormAttachment(americanCouncilOnExerciseTable, -3, SWT.TOP);
		formData.left = new FormAttachment(americanCouncilOnExerciseTable, 0, SWT.LEFT);
		tableInfoLabel.setLayoutData(formData);

		Button infoButton = new Button(bodyFatCalculatorShell, SWT.PUSH);
		infoButton.setText("INFO");
		formData = new FormData();
		formData.bottom = new FormAttachment(americanCouncilOnExerciseTable, -3, SWT.TOP);
		formData.right = new FormAttachment(americanCouncilOnExerciseTable, 0, SWT.RIGHT);
		infoButton.setLayoutData(formData);
		infoButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showBodyFatInfo();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		setamericanCouncilOnExerciseTable(americanCouncilOnExerciseTable);

		Composite calculatorComposite = new Composite(bodyFatCalculatorShell, SWT.None);
		formData = new FormData();
		formData.top = new FormAttachment(0);
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(45);
		formData.bottom = new FormAttachment(100);
		calculatorComposite.setLayout(new FormLayout());
		calculatorComposite.setLayoutData(formData);

		if (userInfo.getSex() == 'M') {
			maleVersionCalculator(calculatorComposite);
		} else {
			femaleVersionCalculator(calculatorComposite);
		}
	}

	private void femaleVersionCalculator(Composite calculatorComposite) {
		Label unitLabel = new Label(calculatorComposite, SWT.None);
		unitLabel.setText("Use centimeters for measurement!");
		FormData formData = new FormData();
		formData.top = new FormAttachment(20);
		formData.left = new FormAttachment(15);
		unitLabel.setLayoutData(formData);

		Label neckLabel = new Label(calculatorComposite, SWT.None);
		neckLabel.setText("Neck circumference");
		formData = new FormData();
		formData.top = new FormAttachment(unitLabel, 20, SWT.BOTTOM);
		formData.left = new FormAttachment(unitLabel, 0, SWT.LEFT);
		neckLabel.setLayoutData(formData);

		Text neckText = new Text(calculatorComposite, SWT.BORDER);
		formData = new FormData();
		formData.top = new FormAttachment(neckLabel, 0, SWT.TOP);
		formData.left = new FormAttachment(neckLabel, 3, SWT.RIGHT);
		neckText.setLayoutData(formData);

		Text waistText = new Text(calculatorComposite, SWT.BORDER);
		formData = new FormData();
		formData.top = new FormAttachment(neckText, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(neckText, 0, SWT.LEFT);
		waistText.setLayoutData(formData);

		Label waistLabel = new Label(calculatorComposite, SWT.None);
		waistLabel.setText("Waist circumference");
		formData = new FormData();
		formData.top = new FormAttachment(waistText, 0, SWT.TOP);
		formData.right = new FormAttachment(waistText, -3, SWT.LEFT);
		waistLabel.setLayoutData(formData);

		Text hipText = new Text(calculatorComposite, SWT.BORDER);
		formData = new FormData();
		formData.top = new FormAttachment(waistText, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(waistText, 0, SWT.LEFT);
		hipText.setLayoutData(formData);

		Label hipLabel = new Label(calculatorComposite, SWT.None);
		hipLabel.setText("Hip circumference");
		formData = new FormData();
		formData.top = new FormAttachment(hipText, 0, SWT.TOP);
		formData.right = new FormAttachment(hipText, -3, SWT.LEFT);
		hipLabel.setLayoutData(formData);

		Button calculateButton = new Button(calculatorComposite, SWT.PUSH);
		calculateButton.setText("CALCULATE");
		formData = new FormData();
		formData.top = new FormAttachment(hipLabel, 15, SWT.BOTTOM);
		formData.right = new FormAttachment(hipText, 0, SWT.RIGHT);
		calculateButton.setLayoutData(formData);

		Label resultLabel = new Label(calculatorComposite, SWT.None);
		resultLabel.setText("Body Fat Percentage:               ");
		formData = new FormData();
		formData.top = new FormAttachment(calculateButton, 25, SWT.BOTTOM);
		formData.left = new FormAttachment(hipLabel, 0, SWT.LEFT);
		resultLabel.setLayoutData(formData);

		calculateButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				boolean correctData = true;

				MessageBox messageBox = new MessageBox(bodyFatCalculatorShell);
				messageBox.setText("Info");

				if (waistText.getText().equals("")) {
					messageBox.setMessage("Enter your waist circumference.");
					messageBox.open();
					correctData = false;
				} else if (hipText.getText().equals("")) {
					messageBox.setMessage("Enter your hip circumference.");
					messageBox.open();
					correctData = false;
				} else if (neckText.getText().equals("")) {
					messageBox.setMessage("Enter your neck circumference.");
					messageBox.open();
					correctData = false;
				} else if (hipText.getText().charAt(0) == '.'
						|| hipText.getText().charAt(hipText.getText().length() - 1) == '.') {
					messageBox.setMessage("Invalid hip circumference data.");
					messageBox.open();
					correctData = false;
				} else if (waistText.getText().charAt(0) == '.'
						|| waistText.getText().charAt(waistText.getText().length() - 1) == '.') {
					messageBox.setMessage("Invalid waist circumference data.");
					messageBox.open();
					correctData = false;
				} else if (neckText.getText().charAt(0) == '.'
						|| neckText.getText().charAt(neckText.getText().length() - 1) == '.') {
					messageBox.setMessage("Invalid neck circumference data.");
					messageBox.open();
					correctData = false;
				} else if (hipText.getText().contains(".")
						&& hipText.getText().substring(hipText.getText().indexOf(".") + 1).contains(".")) {
					messageBox.setMessage(
							"Hip circumference must be a pozitive number, only containing digits and '.' character ONCE, for data precision.");
					messageBox.open();
					correctData = false;
				} else if (waistText.getText().contains(".")
						&& waistText.getText().substring(waistText.getText().indexOf(".") + 1).contains(".")) {
					messageBox.setMessage(
							"Waist circumference must be a pozitive number, only containing digits and '.' character ONCE, for data precision.");
					messageBox.open();
					correctData = false;
				} else if (neckText.getText().contains(".")
						&& neckText.getText().substring(neckText.getText().indexOf(".") + 1).contains(".")) {
					messageBox.setMessage(
							"Neck circumference must be a pozitive number, only containing digits and '.' character ONCE, for data precision.");
					messageBox.open();
					correctData = false;
				} else {
					for (int i = 0; i < waistText.getText().length(); i++) {
						if (Character.isDigit(waistText.getText().charAt(i)) == false
								&& waistText.getText().charAt(i) != '.') {
							messageBox.setMessage(
									"Waist circumference must be a pozitive number, only containing digits and '.' character for data precision.");
							messageBox.open();
							correctData = false;
							break;
						}
					}

					if (correctData) {
						for (int i = 0; i < hipText.getText().length(); i++) {
							if (Character.isDigit(hipText.getText().charAt(i)) == false
									&& hipText.getText().charAt(i) != '.') {
								messageBox.setMessage(
										"Hip circumference must be a pozitive number, only containing digits and '.' character for data precision.");
								messageBox.open();
								correctData = false;
								break;
							}
						}
					}

					if (correctData) {
						for (int i = 0; i < neckText.getText().length(); i++) {
							if (Character.isDigit(neckText.getText().charAt(i)) == false
									&& neckText.getText().charAt(i) != '.') {
								messageBox.setMessage(
										"Neck circumference must be a pozitive number, only containing digits and '.' character for data precision.");
								messageBox.open();
								correctData = false;
								break;
							}
						}
					}
				}
				if (correctData) {
					calculateFemaleBodyFat(calculatorComposite, resultLabel, neckText.getText(), waistText.getText(),
							hipText.getText());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	private void calculateFemaleBodyFat(Composite calculatorComposite, Label resultLabel, String neckText,
			String waistText, String hipText) {

		double neckValue = Double.parseDouble(neckText);
		double waistValue = Double.parseDouble(waistText);
		double hipValue = Double.parseDouble(hipText);

		double bodyFatPercentageValue = 495 / (1.29579 - 0.35004 * Math.log10(waistValue + hipValue - neckValue)
				+ 0.22100 * Math.log10(userInfo.getHeigthCM())) - 450;

		DecimalFormat df = new DecimalFormat("#.##");

		resultLabel.setText("Body Fat Percentage: " + df.format(bodyFatPercentageValue) + "%");
	}

	private void maleVersionCalculator(Composite calculatorComposite) {

		Label unitLabel = new Label(calculatorComposite, SWT.None);
		unitLabel.setText("Use centimeters for measurement!");
		FormData formData = new FormData();
		formData.top = new FormAttachment(20);
		formData.left = new FormAttachment(15);
		unitLabel.setLayoutData(formData);

		Label neckLabel = new Label(calculatorComposite, SWT.None);
		neckLabel.setText("Neck circumference");
		formData = new FormData();
		formData.top = new FormAttachment(unitLabel, 20, SWT.BOTTOM);
		formData.left = new FormAttachment(unitLabel, 0, SWT.LEFT);
		neckLabel.setLayoutData(formData);

		Text neckText = new Text(calculatorComposite, SWT.BORDER);
		formData = new FormData();
		formData.top = new FormAttachment(neckLabel, 0, SWT.TOP);
		formData.left = new FormAttachment(neckLabel, 3, SWT.RIGHT);
		neckText.setLayoutData(formData);

		Text waistText = new Text(calculatorComposite, SWT.BORDER);
		formData = new FormData();
		formData.top = new FormAttachment(neckText, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(neckText, 0, SWT.LEFT);
		waistText.setLayoutData(formData);

		Label waistLabel = new Label(calculatorComposite, SWT.None);
		waistLabel.setText("Waist circumference");
		formData = new FormData();
		formData.top = new FormAttachment(waistText, 0, SWT.TOP);
		formData.right = new FormAttachment(waistText, -3, SWT.LEFT);
		waistLabel.setLayoutData(formData);

		Button calculateButton = new Button(calculatorComposite, SWT.PUSH);
		calculateButton.setText("CALCULATE");
		formData = new FormData();
		formData.top = new FormAttachment(waistLabel, 15, SWT.BOTTOM);
		formData.right = new FormAttachment(waistText, 0, SWT.RIGHT);
		calculateButton.setLayoutData(formData);

		Label resultLabel = new Label(calculatorComposite, SWT.None);
		resultLabel.setText("Body Fat Percentage:               ");
		formData = new FormData();
		formData.top = new FormAttachment(calculateButton, 25, SWT.BOTTOM);
		formData.left = new FormAttachment(waistLabel, 0, SWT.LEFT);
		resultLabel.setLayoutData(formData);

		calculateButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				boolean correctData = true;

				MessageBox messageBox = new MessageBox(bodyFatCalculatorShell);
				messageBox.setText("Info");

				if (waistText.getText().equals("")) {
					messageBox.setMessage("Enter your waist circumference.");
					messageBox.open();
					correctData = false;
				} else if (neckText.getText().equals("")) {
					messageBox.setMessage("Enter your neck circumference.");
					messageBox.open();
					correctData = false;
				} else if (waistText.getText().charAt(0) == '.'
						|| waistText.getText().charAt(waistText.getText().length() - 1) == '.') {
					messageBox.setMessage("Invalid waist circumference data.");
					messageBox.open();
					correctData = false;
				} else if (neckText.getText().charAt(0) == '.'
						|| neckText.getText().charAt(neckText.getText().length() - 1) == '.') {
					messageBox.setMessage("Invalid neck circumference data.");
					messageBox.open();
					correctData = false;
				} else if (waistText.getText().contains(".")
						&& waistText.getText().substring(waistText.getText().indexOf(".") + 1).contains(".")) {
					messageBox.setMessage(
							"Waist circumference must be a pozitive number, only containing digits and '.' character ONCE, for data precision.");
					messageBox.open();
					correctData = false;
				} else if (neckText.getText().contains(".")
						&& neckText.getText().substring(neckText.getText().indexOf(".") + 1).contains(".")) {
					messageBox.setMessage(
							"Neck circumference must be a pozitive number, only containing digits and '.' character ONCE, for data precision.");
					messageBox.open();
					correctData = false;
				} else {
					for (int i = 0; i < waistText.getText().length(); i++) {
						if (Character.isDigit(waistText.getText().charAt(i)) == false
								&& waistText.getText().charAt(i) != '.') {
							messageBox.setMessage(
									"Waist circumference must be a pozitive number, only containing digits and '.' character for data precision.");
							messageBox.open();
							correctData = false;
							break;
						}
					}

					if (correctData) {
						for (int i = 0; i < neckText.getText().length(); i++) {
							if (Character.isDigit(neckText.getText().charAt(i)) == false
									&& neckText.getText().charAt(i) != '.') {
								messageBox.setMessage(
										"Neck circumference must be a pozitive number, only containing digits and '.' character for data precision.");
								messageBox.open();
								correctData = false;
								break;
							}
						}
					}
				}

				if (correctData) {
					calculateMaleBodyFat(calculatorComposite, resultLabel, neckText.getText(), waistText.getText());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	private void calculateMaleBodyFat(Composite calculatorComposite, Label resultLabel, String neckText,
			String waistText) {

		double neckValue = Double.parseDouble(neckText);
		double waistValue = Double.parseDouble(waistText);

		double bodyFatPercentageValue = 495
				/ (1.0324 - 0.19077 * Math.log10(waistValue - neckValue) + 0.15456 * Math.log10(userInfo.getHeigthCM()))
				- 450;

		DecimalFormat df = new DecimalFormat("#.##");

		resultLabel.setText("Body Fat Percentage: " + df.format(bodyFatPercentageValue) + "%");
	}

	private void showBodyFatInfo() {
		Shell bodyFatInfo = new Shell(display);
		bodyFatInfo.setSize(400, 150);
		bodyFatInfo.setText("Body Fat Info");
		bodyFatInfo.setLayout(new FormLayout());

		Label infoLabel1 = new Label(bodyFatInfo, SWT.None);
		infoLabel1.setText("There are many specific techniques used for measuring body fat.");
		FormData formData = new FormData();
		formData.top = new FormAttachment(3);
		formData.left = new FormAttachment(3);
		infoLabel1.setLayoutData(formData);

		Label infoLabel2 = new Label(bodyFatInfo, SWT.None);
		infoLabel2.setText("The present calculator uses a method involving equations developed");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabel1, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabel2.setLayoutData(formData);

		Label infoLabel3 = new Label(bodyFatInfo, SWT.None);
		infoLabel3.setText("at the Naval Health Research Center by Hodgdon and Beckett in 1984.");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabel2, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabel3.setLayoutData(formData);

		Label infoLabel4 = new Label(bodyFatInfo, SWT.None);
		infoLabel4.setText("ATENTION! The calculator ESTIMATES the percentage, values may vary");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabel3, 10, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabel4.setLayoutData(formData);

		Label infoLabel5 = new Label(bodyFatInfo, SWT.None);
		infoLabel5.setText("due to genetics.");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabel4, 3, SWT.BOTTOM);
		formData.left = new FormAttachment(3);
		infoLabel5.setLayoutData(formData);

		//
		//
		//

		bodyFatInfo.open();

	}

	private void setamericanCouncilOnExerciseTable(Table americanCouncilOnExerciseTable) {
		TableColumn column = new TableColumn(americanCouncilOnExerciseTable, SWT.NONE);
		column.setText("Description");
		column.setWidth(85);

		column = new TableColumn(americanCouncilOnExerciseTable, SWT.NONE);
		column.setText("Women");
		column.setWidth(70);

		column = new TableColumn(americanCouncilOnExerciseTable, SWT.NONE);
		column.setText("Men");
		column.setWidth(65);

		TableItem item = new TableItem(americanCouncilOnExerciseTable, SWT.NONE);
		item.setText(0, "");
		item.setText(1, "");
		item.setText(2, "");

		item = new TableItem(americanCouncilOnExerciseTable, SWT.NONE);
		item.setText(0, "Essential fat");
		item.setText(1, "10-13%");
		item.setText(2, "2-5%");

		item = new TableItem(americanCouncilOnExerciseTable, SWT.NONE);
		item.setText(0, "Athletes");
		item.setText(1, "14-20%");
		item.setText(2, "6-13%");

		item = new TableItem(americanCouncilOnExerciseTable, SWT.NONE);
		item.setText(0, "Fitness");
		item.setText(1, "21-24%");
		item.setText(2, "14-17%");

		item = new TableItem(americanCouncilOnExerciseTable, SWT.NONE);
		item.setText(0, "Average");
		item.setText(1, "25-31%");
		item.setText(2, "18-24%");

		item = new TableItem(americanCouncilOnExerciseTable, SWT.NONE);
		item.setText(0, "Obese");
		item.setText(1, "32+%");
		item.setText(2, "25+%");
	}
}
