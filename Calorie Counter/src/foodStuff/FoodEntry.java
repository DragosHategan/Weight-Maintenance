package foodStuff;

public class FoodEntry {
	private Food food;
	private double quantity;
	private double caloriesPerQuantity;
	
	public FoodEntry() {
	}
	
	public FoodEntry(Food food, double quantity, MeasurementUnit measurementUnit) {
		super();
		this.food = food;
		this.quantity = quantity;
		this.caloriesPerQuantity = food.getCalories_100g() * measurementUnit.getValueOfMeasurementUnit() * quantity;
	}
	
	public Food getFood() {
		return food;
	}
	public double getCaloriesPerQuantity() {
		return caloriesPerQuantity;
	}

	public void setCaloriesPerQuantity(int caloriesPerQuantity) {
		this.caloriesPerQuantity = caloriesPerQuantity;
	}

	public void setFood(Food food) {
		this.food = food;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
}
