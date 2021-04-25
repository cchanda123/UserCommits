package UserCommits;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class Main {

	public static void main(String[] args) throws IOException {

		// Part 1: Retrieving commits
		HttpURLConnection connection;
		String userName;
		String passwordKey;
		String csvFilePath;

		// Accepting user input
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter username: ");
		userName = scanner.nextLine();
		System.out.println("Please enter password: ");
		passwordKey = scanner.nextLine();
		System.out.println("Please enter csv file path: ");
		csvFilePath = scanner.nextLine();
		scanner.close();

		try {
			URL url = new URL(
					"https://api.github.com/search/commits?q=author:" + userName + "&sort=author-date&order=desc");
			connection = (HttpURLConnection) url.openConnection();
			connection.addRequestProperty("ACCEPT", "application/vnd.github.cloak-preview");
			connection.addRequestProperty("Authorization", passwordKey);
			connection.setRequestMethod("GET");

			// Obtaining and parsing the JSON after receiving a successful response code.
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String currentLine;
			StringBuffer responseContent = new StringBuffer();
			while ((currentLine = bufferedReader.readLine()) != null) {
				responseContent.append(currentLine);
			}
			bufferedReader.close();

			String jsonContents = responseContent.toString();
			JSONObject jsonObject = new JSONObject(jsonContents);

			String total_count = jsonObject.get("total_count").toString();

			int totalCommits = Integer.parseInt(total_count);

			// Although the requirement says last 60 commits, the API has a limitation of
			// only generating last 30 records per minute for authenticated requests.
			// https://docs.github.com/en/rest/reference/search#rate-limit
			if (totalCommits > 30) {
				totalCommits = 30;
			}

			String[] commitURLs = new String[totalCommits];
			String[] commitDateStrings = new String[totalCommits];
			Date[] commitDateTimes = new Date[totalCommits];

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");

			JSONArray jsonArray = jsonObject.getJSONArray("items");
			PrintWriter printWriter = new PrintWriter(new File(csvFilePath));

			for (int i = 0; i < totalCommits; i++) {
				JSONObject commit = jsonArray.getJSONObject(i).getJSONObject("commit");
				commitURLs[i] = commit.getString("url");
				commitDateStrings[i] = commit.getJSONObject("author").getString("date").substring(0, 23);
				commitDateTimes[i] = simpleDateFormat.parse(commitDateStrings[i]);
				printWriter.write(commitDateStrings[i] + ",\n");
			}

			printWriter.close();

			// Part 2: Finding the mean of consecutive requests.
			long totalDifferenceInHours = 0;
			for (int i = 1; i < totalCommits; i++) {
				long differenceInHours = (commitDateTimes[i - 1].getTime() - commitDateTimes[i].getTime())
						/ (1000 * 60 * 60);
				totalDifferenceInHours += differenceInHours;
			}

			double meanDifferenceInHours = totalDifferenceInHours / (totalCommits - 1);
			System.out.println(meanDifferenceInHours);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
