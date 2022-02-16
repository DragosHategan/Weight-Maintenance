package foodStuff;

/**
 * The Food class contains specific information about every food in the
 * application.
 * 
 * @author Dragos
 *
 */

public class Food {

	private String category;
	private String name;
	private int calories_100g;

	public Food() {
	}

	public Food(String category, String productName, int calories_100g) {
		this.category = category;
		this.name = productName;
		this.calories_100g = calories_100g;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Food [category=" + category + ", name=" + name + ", calories_100g=" + calories_100g + "]";
	}

	public int getCalories_100g() {
		return calories_100g;
	}

	public void setCalories_100g(int calories_100g) {
		this.calories_100g = calories_100g;
	}
}
