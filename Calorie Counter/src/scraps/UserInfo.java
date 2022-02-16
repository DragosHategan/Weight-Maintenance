package scraps;

/**
 * The UserInfo class contains body related information, accompanied with the
 * log in information about the using user.
 * 
 * @author Dragos
 *
 */

public class UserInfo {

	private User user;
	private int age;
	private double weightKG;
	private int heightCM;
	private char sex;

	public UserInfo(User user, int age, double weightKG, int heightCM, char sex) {
		this.user = user;
		this.age = age;
		this.weightKG = weightKG;
		this.heightCM = heightCM;
		this.sex = sex;
	}

	public int getHeightCM() {
		return heightCM;
	}

	public void setHeightCM(int heightCM) {
		this.heightCM = heightCM;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public User getUser() {
		return user;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getWeightKG() {
		return weightKG;
	}

	public void setWeightKG(double weightKG) {
		this.weightKG = weightKG;
	}

	public int getHeigthCM() {
		return heightCM;
	}

	public void setHeigthCM(int heightCM) {
		this.heightCM = heightCM;
	}
}
