package gui;

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

import foodStuff.Food;
import server.Database;

/**
 * The ProductsSubMenu class is used for the admin users to manipulate data
 * about foods, like creating or deleting them.
 * @author Dragos
 *
 */

public class ProductsSubMenu {

	private Database database;
	private Display display;
	private Menu menu;
	private Shell shell;

	public ProductsSubMenu(Database memoryDatabase, Display display, Menu menu, Shell shell) {
		this.database = memoryDatabase;
		this.display = display;
		this.menu = menu;
		this.shell = shell;
		init();
	}

	private void init() {
		MenuItem manageProductsButton = new MenuItem(menu, SWT.CASCADE);
		manageProductsButton.setText("Manage products");

		Menu submenuProducts = new Menu(shell, SWT.DROP_DOWN);
		manageProductsButton.setMenu(submenuProducts);

		MenuItem addProduct = new MenuItem(submenuProducts, SWT.PUSH);
		addProduct.setText("Add product");
		addProduct.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddProduct();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		MenuItem deleteProduct = new MenuItem(submenuProducts, SWT.PUSH);
		deleteProduct.setText("Delete product");
		deleteProduct.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DeleteProduct();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	private void AddProduct() {

		Shell addProductShell = new Shell(display);
		addProductShell.setSize(300, 250);
		addProductShell.setText("Add product");
		addProductShell.setLayout(new FormLayout());

		shellMakerAddProduct(addProductShell);

		addProductShell.open();
	}

	private void shellMakerAddProduct(Shell addProductShell) {
		Label infoLabel = new Label(addProductShell, SWT.None);
		infoLabel.setText(//
				"You can select the category, name and calories/100g\n"//
						+ "of the product you want do add, or add it in the\n'FoodList' file,"//
						+ " in the same format as other products.");
		FormData formData = new FormData();
		formData.top = new FormAttachment(3);
		formData.left = new FormAttachment(2);
		infoLabel.setLayoutData(formData);

		Label categoryLabel = new Label(addProductShell, SWT.None);
		categoryLabel.setText("Category:");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabel, 10, SWT.BOTTOM);
		formData.left = new FormAttachment(35);
		categoryLabel.setLayoutData(formData);

		Text categoryText = new Text(addProductShell, SWT.BORDER);
		formData = new FormData(100, SWT.DEFAULT);
		formData.top = new FormAttachment(categoryLabel, 0, SWT.TOP);
		formData.left = new FormAttachment(categoryLabel, 5, SWT.RIGHT);
		categoryText.setLayoutData(formData);

		Label nameLabel = new Label(addProductShell, SWT.None);
		nameLabel.setText("Name:");
		formData = new FormData();
		formData.top = new FormAttachment(categoryLabel, 10, SWT.BOTTOM);
		formData.right = new FormAttachment(categoryLabel, 0, SWT.RIGHT);
		nameLabel.setLayoutData(formData);

		Text nameText = new Text(addProductShell, SWT.BORDER);
		formData = new FormData(100, SWT.DEFAULT);
		formData.top = new FormAttachment(nameLabel, 0, SWT.TOP);
		formData.left = new FormAttachment(nameLabel, 5, SWT.RIGHT);
		nameText.setLayoutData(formData);

		Label caloriesLabel = new Label(addProductShell, SWT.None);
		caloriesLabel.setText("Calories per 100g/100ml:");
		formData = new FormData();
		formData.top = new FormAttachment(nameLabel, 10, SWT.BOTTOM);
		formData.right = new FormAttachment(nameLabel, 0, SWT.RIGHT);
		caloriesLabel.setLayoutData(formData);

		Text caloriesText = new Text(addProductShell, SWT.BORDER);
		formData = new FormData(100, SWT.DEFAULT);
		formData.top = new FormAttachment(caloriesLabel, 0, SWT.TOP);
		formData.left = new FormAttachment(caloriesLabel, 5, SWT.RIGHT);
		caloriesText.setLayoutData(formData);

		Button addButton = new Button(addProductShell, SWT.PUSH);
		formData = new FormData(50, SWT.DEFAULT);
		addButton.setText("Add");
		formData.top = new FormAttachment(caloriesText, 5, SWT.BOTTOM);
		formData.left = new FormAttachment(caloriesText, 0, SWT.LEFT);
		addButton.setLayoutData(formData);
		addButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addProduct(categoryText.getText(), nameText.getText(), caloriesText.getText());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		Button categoriesButton = new Button(addProductShell, SWT.PUSH);
		categoriesButton.setText("Categories");
		formData = new FormData();
		formData.top = new FormAttachment(addButton, 0, SWT.TOP);
		formData.right = new FormAttachment(addButton, 0, SWT.LEFT);
		categoriesButton.setLayoutData(formData);
		categoriesButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell categoryShell = new Shell(display);
				categoryShell.setText("Categories");
				categoryShell.setLayout(new FormLayout());
				categoryShell.setSize(260, 400);
				List<String> categoryList = new ArrayList<>();
				for (Food food : database.getFoods()) {
					if (!categoryList.contains(food.getCategory())) {
						categoryList.add(food.getCategory());
					}
				}

				Table categoryTable = new Table(categoryShell, SWT.MULTI | SWT.FULL_SELECTION);
				categoryTable.setLinesVisible(true);
				categoryTable.setHeaderVisible(true);
				FormData formData = new FormData();
				formData.top = new FormAttachment(0);
				formData.left = new FormAttachment(0);
				formData.right = new FormAttachment(100);
				formData.bottom = new FormAttachment(100);
				categoryTable.setLayoutData(formData);

				TableColumn column = new TableColumn(categoryTable, SWT.NONE);
				column.setText("Category");
				column.setWidth(225);

				TableItem item = new TableItem(categoryTable, SWT.PUSH);
				item.setText("");

				for (int i = 0; i < categoryList.size(); i++) {
					item = new TableItem(categoryTable, SWT.PUSH);
					item.setText("" + (i + 1) + ". " + categoryList.get(i).toString());
				}

				categoryTable.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						String[] stringArray = Arrays.toString(categoryTable.getSelection()).split("[{,}]");
						String category = stringArray[1].substring(stringArray[1].indexOf(" ") + 1);
						categoryText.setText(category);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});

				categoryShell.open();

				// TODO close categoryShell if addProductShell is closed.
				// System.out.println(addProductShell.getChildren());
			}
		});

		Label infoCategoryLabel = new Label(addProductShell, SWT.None);
		infoCategoryLabel.setText(
				"INFO: You can add a new category, while adding a\nnew product that has no existing category.");
		formData = new FormData();
		formData.top = new FormAttachment(categoriesButton, 5, SWT.BOTTOM);
		formData.left = new FormAttachment(2);
		infoCategoryLabel.setLayoutData(formData);
	}

	private void addProduct(String category, String name, String calories) {
		Boolean isOriginal = true;
		Boolean isDigitOnly = true;
		MessageBox messageBox;
		for (int i = 0; i < calories.length(); i++) {
			if (!Character.isDigit(calories.charAt(i))) {
				isDigitOnly = false;
				break;
			}
		}
		if (!(category.length() > 0)) {
			messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setMessage("Category field is empty.");
			messageBox.open();
		} else if (!(name.length() > 0)) {
			messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setMessage("Name field is empty.");
			messageBox.open();
		} else if (!(calories.length() > 0)) {
			messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setMessage("Calories field is empty.");
			messageBox.open();
		} else if (!isDigitOnly) {
			messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setMessage("Calories field must contain only digits.");
			messageBox.open();
		} else {
			for (Food food : database.getFoods()) {
				if (food.getName().toLowerCase().equals(name.toLowerCase())
						&& food.getCategory().toLowerCase().equals(category.toLowerCase())) {
					messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
					messageBox.setMessage("Food already exists.");
					messageBox.open();
					isOriginal = false;
					break;
				}
			}
			if (isOriginal) {
				database.AddFoodToFile(new Food(category, name, Integer.parseInt(calories)));
				messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
				messageBox.setMessage("Food added successfully.");
				messageBox.open();
			}
		}
	}

	private void DeleteProduct() {

		Shell deleteProductShell = new Shell(display);
		int length = 400;
		int hight = 500;
		deleteProductShell.setSize(length, hight);
		deleteProductShell.setText("Delete product");
		deleteProductShell.setLayout(new FormLayout());

		shellMakerDeleteProduct(deleteProductShell, length, hight);

		deleteProductShell.open();
	}

	private void shellMakerDeleteProduct(Shell deleteProductShell, int length, int hight) {
		Label infoLabel = new Label(deleteProductShell, SWT.None);
		infoLabel.setText(//
				"You can select the category and name of the product"//
						+ " you want do\ndelete or delete it from the 'FoodList' file.");
		FormData formData = new FormData();
		formData.top = new FormAttachment(1);
		formData.left = new FormAttachment(5);
		infoLabel.setLayoutData(formData);

		Label categoryLabel = new Label(deleteProductShell, SWT.None);
		categoryLabel.setText("Category:");
		formData = new FormData();
		formData.top = new FormAttachment(infoLabel, 20, SWT.BOTTOM);
		formData.left = new FormAttachment(27);
		categoryLabel.setLayoutData(formData);

		Text categoryText = new Text(deleteProductShell, SWT.BORDER);
		formData = new FormData(100, SWT.DEFAULT);
		formData.top = new FormAttachment(categoryLabel, 0, SWT.TOP);
		formData.left = new FormAttachment(categoryLabel, 5, SWT.RIGHT);
		categoryText.setLayoutData(formData);

		Label nameLabel = new Label(deleteProductShell, SWT.None);
		nameLabel.setText("(Optional) Name:");
		formData = new FormData();
		formData.top = new FormAttachment(categoryLabel, 10, SWT.BOTTOM);
		formData.right = new FormAttachment(categoryLabel, 0, SWT.RIGHT);
		nameLabel.setLayoutData(formData);

		Text nameText = new Text(deleteProductShell, SWT.BORDER);
		formData = new FormData(100, SWT.DEFAULT);
		formData.top = new FormAttachment(nameLabel, 0, SWT.TOP);
		formData.left = new FormAttachment(nameLabel, 5, SWT.RIGHT);
		nameText.setLayoutData(formData);

		Table productTable = new Table(deleteProductShell, SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER);
		productTable.setLinesVisible(true);
		productTable.setHeaderVisible(true);
		formData = new FormData();
		formData.top = new FormAttachment(nameText, 20, SWT.BOTTOM);
		formData.left = new FormAttachment(0);
		formData.right = new FormAttachment(100);
		formData.bottom = new FormAttachment(100);
		productTable.setLayoutData(formData);

		TableColumn column = new TableColumn(productTable, SWT.NONE);
		column.setText("Name");
		column.setWidth(deleteProductShell.getSize().x / 3);
		column = new TableColumn(productTable, SWT.NONE);
		column.setText("Category");
		column.setWidth(deleteProductShell.getSize().x / 3 + 6);
		column = new TableColumn(productTable, SWT.NONE);
		column.setText("Calories/100g");
		column.setWidth(deleteProductShell.getSize().x / 3 - 25);

		Button searchButton = new Button(deleteProductShell, SWT.PUSH);
		searchButton.setText("Search");
		formData = new FormData();
		formData.top = new FormAttachment(categoryText, 0, SWT.TOP);
		formData.left = new FormAttachment(nameText, 5, SWT.RIGHT);
		searchButton.setLayoutData(formData);
		searchButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setTable(categoryText.getText(), nameText.getText(), productTable);
			}
		});

		Button categoriesButton = new Button(deleteProductShell, SWT.PUSH);
		categoriesButton.setText("Categories");
		formData = new FormData();
		formData.top = new FormAttachment(nameText, 0, SWT.TOP);
		formData.left = new FormAttachment(searchButton, 0, SWT.LEFT);
		categoriesButton.setLayoutData(formData);
		categoriesButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell categoryShell = new Shell(display);
				categoryShell.setText("Categories");
				categoryShell.setLayout(new FormLayout());
				categoryShell.setSize(260, 400);
				List<String> categoryList = new ArrayList<>();
				for (Food food : database.getFoods()) {
					if (!categoryList.contains(food.getCategory())) {
						categoryList.add(food.getCategory());
					}
				}

				Table categoryTable = new Table(categoryShell, SWT.MULTI | SWT.FULL_SELECTION);
				categoryTable.setLinesVisible(true);
				categoryTable.setHeaderVisible(true);
				FormData formData = new FormData();
				formData.top = new FormAttachment(0);
				formData.left = new FormAttachment(0);
				formData.right = new FormAttachment(100);
				formData.bottom = new FormAttachment(100);
				categoryTable.setLayoutData(formData);

				TableColumn column = new TableColumn(categoryTable, SWT.NONE);
				column.setText("Category");
				column.setWidth(225);

				TableItem item = new TableItem(categoryTable, SWT.PUSH);
				item.setText("");

				for (int i = 0; i < categoryList.size(); i++) {
					item = new TableItem(categoryTable, SWT.PUSH);
					item.setText("" + (i + 1) + ". " + categoryList.get(i).toString());
				}

				categoryTable.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						String[] stringArray = Arrays.toString(categoryTable.getSelection()).split("[{,}]");
						String category = stringArray[1].substring(stringArray[1].indexOf(" ") + 1);
						categoryText.setText(category);
						setTable(category, "", productTable);
					}
				});
				categoryShell.open();
			}
		});

		productTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell2 = new Shell(display);
				shell2.setLayout(new FormLayout());
				shell2.setText("Delete food");
				shell2.setSize(300, 200);

				String[] string = Arrays.toString(productTable.getSelection()).split("[{,}]");

				shell2.setText("Delete " + string[1]);

				Label sureLabel = new Label(shell2, SWT.None);
				sureLabel.setText("Are you sure you want to delete food?");
				FormData formData = new FormData();
				formData.left = new FormAttachment(15);
				formData.top = new FormAttachment(30);
				sureLabel.setLayoutData(formData);

				Button yesButton = new Button(shell2, SWT.PUSH);
				yesButton.setText("YES");
				formData = new FormData();
				formData.left = new FormAttachment(sureLabel, 0, SWT.LEFT);
				formData.top = new FormAttachment(sureLabel, 5, SWT.BOTTOM);
				yesButton.setLayoutData(formData);
				yesButton.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						for (Food food : database.getFoods()) {
							if (food.getName().equals(string[1])) {
								database.DeleteFoodFromFile(food);
							}
						}
						MessageBox messageBox = new MessageBox(deleteProductShell, SWT.OK | SWT.ICON_INFORMATION);
						messageBox.setMessage("Product " + string[1] + " deleted successfully");
						messageBox.open();
						deleteProductShell.close();
						shell2.close();
						DeleteProduct();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});

				Button noButton = new Button(shell2, SWT.PUSH);
				noButton.setText("NO");
				formData = new FormData();
				formData.right = new FormAttachment(sureLabel, 0, SWT.RIGHT);
				formData.top = new FormAttachment(sureLabel, 5, SWT.BOTTOM);
				noButton.setLayoutData(formData);
				noButton.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						shell2.close();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});
				shell2.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

	}

	private void setTable(String category, String name, Table productTable) {
		int ok = 0;
		productTable.removeAll();
		if (category.length() > 0) {
			for (Food food : database.getFoods()) {
				if (!(name.length() > 0)) {
					if (food.getCategory().toLowerCase().contains(category.toLowerCase())) {
						TableItem item = new TableItem(productTable, SWT.PUSH);
						item.setText(1, food.getCategory());
						item.setText(0, food.getName());
						item.setText(2, Integer.toString(food.getCalories_100g()));
						ok = 1;
					}
				} else {
					if (food.getCategory().toLowerCase().contains(category.toLowerCase())
							&& food.getName().toLowerCase().contains(name.toLowerCase())) {
						TableItem item = new TableItem(productTable, SWT.PUSH);
						item.setText(1, food.getCategory());
						item.setText(0, food.getName());
						item.setText(2, Double.toString(food.getCalories_100g()));
						ok = 1;
					}
				}
			}
		}
		if (ok == 0) {
			TableItem item = new TableItem(productTable, SWT.PUSH);
			item.setText(0, "No data found");
			item.setText(1, "No data found");
			item.setText(2, "No data found");
		}
	}
}
