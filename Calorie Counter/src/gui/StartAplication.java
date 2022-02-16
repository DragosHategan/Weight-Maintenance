package gui;

import scraps.LogIn;
import server.Database;
import server.DatabaseImpl;

/**
 * The StartAplication class it's used to open an application session.
 * 
 * Main actions:
 * 1) Retrieving user related information from a database through @param database 
 * 2) Retrieving food related information from a text file through @param database
 * 3) Open the GUI, starting with the Sign in window.
 * 
 * @author Dragos
 */

public class StartAplication {
	private Database database;

	public StartAplication() {
		database = new DatabaseImpl();
		run();
	}

	private void run() {
		new LogIn(database);
	}

	public static void main(String[] args) {
		new StartAplication();
	}
}
