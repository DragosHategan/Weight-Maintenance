package server;

import java.sql.*;
import java.text.DecimalFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import foodStuff.Food;
import scraps.User;

/**
 * The class DataBaseImpl is used to implement the Database interface, further
 * information in the interface.
 * 
 * @author Dragos
 *
 */

public class DatabaseImpl implements Database {

	private List<User> userList;
	private List<Food> foodList;

	public DatabaseImpl() {
		this.init();
	}

	public void init() {
		GetUsersAndFoodData();
	}

	@Override
	public List<User> getUsers() {
		return userList;
	}

	@Override
	public List<Food> getFoods() {
		return foodList;
	}

	public void setUsers(List<User> users) {
		this.userList = users;
	}

	private void GetUsersAndFoodData() {
		/*
		 * Session session = SessionFactoryProvider.getSessionFactory().openSession();
		 * foodList = session.createQuery("from Food").list(); users =
		 * session.createQuery("from UserData").list(); session.close();
		 */

		ReadUsersFromDataBase();
		ReadFoodFile();
		// ReadUsersFile();
	}

	@Override
	public void ReadFoodFile() {
		foodList = new ArrayList<Food>();
		try {
			FileReader fileReader = new FileReader("FoodList");
			Scanner sc = new Scanner(fileReader);
			while (sc.hasNextLine()) {
				String data = sc.nextLine();
				if (data.isEmpty()) {
					continue;
				}
				String[] line = data.split(", ");
				foodList.add(new Food(line[0], line[1], Integer.parseInt(line[2])));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ReadUsersFile() {
		userList = new ArrayList<User>();
		File userFile = new File("UserList");
		try {
			Scanner sc = new Scanner(userFile);
			while (sc.hasNext()) {
				String line = sc.nextLine();
				if (line.isEmpty()) {
					continue;
				}
				String[] data = line.split(", ");
				userList.add(new User(data[0], data[1], Boolean.parseBoolean(data[2])));
			}

			sc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ReadUsersFromDataBase() {
		userList = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String userName = "UserScoala";
			String password = "UserScoala";
			Connection connection = DriverManager.getConnection(url, userName, password);
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("select * from userinfo");
			while (resultSet.next()) {
				userList.add(new User(resultSet.getString(1), resultSet.getString(2),
						resultSet.getInt(3) == 1 ? true : false));
			}

			connection.close();
			statement.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void PrintAllProducts() {
		System.out.println("Database contains " + foodList.size() + " products:\n");
		for (Food food : foodList) {
			System.out.println(food.toString());
		}
	}

	public void PrintAllUserNames() {
		System.out.println("Users:");
		int i = 1;
		for (User u : userList) {
			System.out.println("" + i + ". " + u.getUserName());
			i++;
		}
	}

	@Override
	public void AddUserToDataBase(User user) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String userName = "UserScoala";
			String password = "UserScoala";
			Connection connection = DriverManager.getConnection(url, userName, password);
			Statement statement = connection.createStatement();

			String updateString = "insert into userinfo values ('" + //
					user.getUserName() + "','" + user.getPassword() + //
					"'," + (user.isAdmin() == true ? 1 : 0) + ",null,null,null,null)";

			statement.executeUpdate(updateString);

			connection.close();
			statement.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void DeleteUserFromDataBase(User user) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String userName = "UserScoala";
			String password = "UserScoala";
			Connection connection = DriverManager.getConnection(url, userName, password);
			Statement statement = connection.createStatement();

			String updateString = "delete from userinfo where username = '" + user.getUserName() + "'";

			statement.executeUpdate(updateString);

			connection.close();
			statement.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void SetUserInfoInDB(User user, String age, String weight, String heigth, char sex) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String userName = "UserScoala";
			String password = "UserScoala";
			Connection connection = DriverManager.getConnection(url, userName, password);
			Statement statement = connection.createStatement();

			DecimalFormat df = new DecimalFormat("0.0");

			String updateString = "update userinfo set " + //
					"age = " + Integer.parseInt(age) + ", weight = " + df.format(Double.parseDouble(weight))//
					+ ", sex = '" + sex + "', heigth = " + Integer.parseInt(heigth) + //
					" where username = '" + user.getUserName() + "'";

			statement.executeUpdate(updateString);

			connection.close();
			statement.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void DeleteFoodFromFile(Food food) {
		try {
			List<String> file = new ArrayList<>();
			FileReader fileReader = new FileReader("FoodList");
			Scanner sc = new Scanner(fileReader);
			while (sc.hasNext()) {
				String line = sc.nextLine();
				String[] data;
				if (!line.isEmpty()) {
					data = line.split(", ");
				} else {
					file.add(line);
					continue;
				}
				if (!(data[1].equals(food.getName()) && data[0].equals(food.getCategory()))) {
					file.add(line);
				}
			}
			sc.close();
			fileReader.close();
			FileWriter fileWriter = new FileWriter("FoodList");
			for (String line : file) {
				fileWriter.write(line + "\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ReadFoodFile();
	}

	@Override
	public void AddFoodToFile(Food food) {
		List<String> stringList = new ArrayList<>();
		for (int i = 0; i < foodList.size(); i++) {
			stringList.add(foodList.get(i).getCategory() + //
					", " + foodList.get(i).getName() + //
					", " + String.valueOf((int) foodList.get(i).getCalories_100g()));
		}

		stringList.add(//
				Character.toUpperCase(food.getCategory().charAt(0)) + //
						food.getCategory().substring(1, food.getCategory().length()) + //
						", " + Character.toUpperCase(food.getName().charAt(0)) + //
						food.getName().substring(1, food.getName().length()) + //
						", " + String.valueOf((int) food.getCalories_100g()));
		Collections.sort(stringList);

		try {
			FileWriter fileWriter = new FileWriter("FoodList");
			for (String string : stringList) {
				fileWriter.write(string + "\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ReadFoodFile();
	}
	
//	@Override
//	public void DeleteUserFromFile(User user) {
//		try {
//			List<String> file = new ArrayList<>();
//			FileReader fileReader = new FileReader("UserList");
//			Scanner sc = new Scanner(fileReader);
//			while (sc.hasNext()) {
//				String line = sc.nextLine();
//				String[] data = line.split(", ");
//				if (!data[0].equals(user.getUserName())) {
//					file.add(line);
//				}
//			}
//			sc.close();
//			fileReader.close();
//			FileWriter fileWriter = new FileWriter("UserList");
//			for (String line : file) {
//				fileWriter.write(line + "\n");
//			}
//			fileWriter.close();
//
//			file = new ArrayList<>();
//			fileReader = new FileReader("UserInfo");
//			sc = new Scanner(fileReader);
//			while (sc.hasNext()) {
//				String line = sc.nextLine();
//				String[] data = line.split(": ");
//				if (!data[0].equals(user.getUserName())) {
//					file.add(line);
//				}
//			}
//			sc.close();
//
//			fileReader.close();
//			fileWriter = new FileWriter("UserInfo");
//			for (String line : file) {
//				fileWriter.write(line + "\n");
//			}
//			fileWriter.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		ReadUsersFile();
//	}
	
//	@Override
//	public void SetUserInfoInFile(User user, String age, String weight, String heigth, char sex) {
//		try {
//			FileReader fileReader = new FileReader("UserInfo");
//			Scanner sc = new Scanner(fileReader);
//			List<String> fileLines = new ArrayList<>();
//
//			while (sc.hasNext()) {
//				String line = sc.nextLine();
//				if (line.subSequence(0, line.indexOf(":")).equals(user.getUserName())) {
//					fileLines.add(line.subSequence(0, line.indexOf(":")) + ": " + age + ", " + weight + ", " + heigth
//							+ "; " + sex);
//				} else {
//					fileLines.add(line);
//				}
//			}
//
//			FileWriter fileWriter = new FileWriter("UserInfo");
//			for (String line : fileLines) {
//				fileWriter.write(line + "\n");
//			}
//
//			sc.close();
//			fileWriter.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Override
//	public void AddUserToFile(User user) {
//		try {
//			userList.add(user);
//			Collections.sort(userList);
//			FileWriter fileWriter = new FileWriter("UserList");
//			for (User userIter : userList) {
//				String isAdmin;
//				if (userIter.isAdmin()) {
//					isAdmin = "true";
//				} else {
//					isAdmin = "false";
//				}
//				fileWriter.write(userIter.getUserName() + ", " + userIter.getPassword() + ", " + isAdmin + "\n");
//			}
//			fileWriter.close();
//
//			fileWriter = new FileWriter("UserInfo", true);
//			fileWriter.write(user.getUserName() + ":\n");
//			fileWriter.close();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		ReadUsersFile();
//	}

}