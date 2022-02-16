package scraps;

import java.io.Serializable;

/**
 * The User class contains logging information about the using user.
 * 
 * @author Dragos
 *
 */

@SuppressWarnings("serial")
public class User implements Serializable, Comparable<User> {

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", admin=" + admin + "]";
	}

	private String username;
	private String password;
	private boolean admin;

	public User() {
	}

	public User(String username, String password, boolean admin) {
		this.username = username;
		this.password = password;
		this.admin = admin;
	}

	public String getUserName() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public int compareTo(User user) {
		return this.getUserName().compareTo(user.getUserName());
	}
}