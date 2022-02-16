package foodStuff;

/**
 * The MeasurementUnit enum is used for further food caloric calculations.
 * 
 * The base measurement unit value is 1, and it refers to calories per 100g or
 * 100ml.
 * 
 * @author Dragos
 *
 */

public enum MeasurementUnit {

	gram_militer(0.01), kilogram_liter(10), cup(1.28), hectogram(1);

	//mililiter(1), liter(1000), teaspoon(5), tablespoon(15)
	
	private double unit;

	MeasurementUnit(double unit) {
		this.unit = unit;
	}
	
	public double getValueOfMeasurementUnit() {
		return this.unit;
	}
}