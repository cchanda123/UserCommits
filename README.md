# UserCommits1

Introduction

The given code performs the following operations:

Part # 1 - Accepting user input and generating a CSV file with the time stamps of the first 30 commits of a user.
The requirements mention that we have to retrieve the last 60 commits of a user. However, as per the GitHub web site - https://docs.github.com/en/rest/reference/search#rate-limit, there is a limit of 30 results per minute for authenticated requests. Hence, this limit has been updated to 30.

Part # 2 - Calculating the mean of the time difference in hours between two adjacent commits.

Setup: 

A reference to JRE system library is required

The current code requires the following Java packages to be added manually to the project.
1. java.net.http
2. java.json (Please download from http://www.java2s.com/Code/JarDownload/java/java-json.jar.zip)

The java.net.http is required to add the authentication headers and accept format to the request. The GitHub API is specified in such a way that it sorts the commits in a descending order.
The java.json helps in JSON parsing.

Required Input:

The program accepts the following three inputs via scanner:
1. The GitHub User Name of user.
2. The GitHub Password of user in the form of Authentication token.
3. The path on which the CSV file must be generated and stored on the user's machine. Please note that that '\' is written as '\\'. For example, "C:\Users\chetan" must be written as "C:\\Users\\chetan".

Expected Output:

1. CSV file of the last 30 commit dates of the user, while is stores in the location specified by user.
2. Average of the differences in hours. 

Limitations:
1. The mean difference between any two consecutive commits' time stamps in hours is rounded down to the nearest integer. For example, if the mean difference is 1.87 hours, the program output will display it as 1 hour.
2. The code only displays the first 30 commits of a user.

Future Improvements:
1. Include 'maven' or 'gradle' for a simpler setup.
2. Include the ability to increase the number of commits that can be retrieved. For example, multiple threads can run in parallel to retrieve greater than 30 commits. However, this would allow some sort of mechanism to segregate batches of requests. For example, we can specify page numbers for retrieving a particular set of commits.
3. The entire logic is currently within one Main class. This could be modularized in the future.