package server;

import java.util.List;
import foodStuff.Food;
import scraps.User;

/**
 * The Database interface is used to read, store, and manipulate information
 * about the foods and the users.
 * 
 * The information is obtained from a database or a file.
 * 
 * @author Dragos
 *
 */

public interface Database {

	public void ReadFoodFile();

	public void ReadUsersFile();

	public void ReadUsersFromDataBase();

	public List<User> getUsers();

	public List<Food> getFoods();

	public void DeleteFoodFromFile(Food food);

	public void AddFoodToFile(Food food);

	public void DeleteUserFromDataBase(User user);

	public void AddUserToDataBase(User user);

	void SetUserInfoInDB(User user, String age, String weight, String heigth, char sex);
	
	//public void AddUserToFile(User user);
	//public void DeleteUserFromFile(User user);
	//public void SetUserInfoInFile(User user, String age, String weight, String heigth, char sex);
}