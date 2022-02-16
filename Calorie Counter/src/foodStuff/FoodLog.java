package foodStuff;

import java.time.LocalDateTime;
import java.util.List;

import scraps.User;

/**
 * The FoodLog class is used to store data about specific food logs used in the
 * CalorieCounter application.
 * 
 * @author Dragos
 *
 */

//O clasa FoodLog cu atributele: Userul, Produsul,
//Cantitatea consumata si momentul de timp.

public class FoodLog {

	private User user;
	private List<FoodEntry> foodEntry;
	private LocalDateTime date;

	public FoodLog() {
	}

	public FoodLog(User user, List<FoodEntry> foodEntry, LocalDateTime date) {
		this.user = user;
		this.foodEntry = foodEntry;
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<FoodEntry> getFoodEntry() {
		return foodEntry;
	}
	
	public void setFoodEntry(List<FoodEntry> foodEntry) {
		this.foodEntry = foodEntry;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}