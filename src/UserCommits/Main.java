package UserCommits;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {

		HttpURLConnection connection;
		String userName;
		String passwordKey;

		//Accepting user input
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter username: ");
		userName = scanner.nextLine();
		System.out.println("Please enter password: ");
		passwordKey = scanner.nextLine();
		scanner.close();

		try {
			URL url = new URL("https://api.github.com/search/commits?q=author:"+userName+"&sort=author-date&order=desc");
			connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("ACCEPT", "application/vnd.github.cloak-preview");
			connection.setRequestMethod("GET");

			int status = connection.getResponseCode();
			System.out.println(status);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}