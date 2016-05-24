import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * @author Kishu Agarwal
 */
public class Downloader {

	/*
	 * Get the html from a specified URL.
	 */
	public static String getResponse(String u) throws IOException {
		URL url = new URL(u);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("User-agent",
				"user-agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
		Logger.DLog("Connecting to " + u);
		InputStream stream = conn.getInputStream();
		System.out.println("Response Code:" + conn.getResponseCode());
		System.out.println("Response message:" + conn.getResponseMessage());
		if (conn.getContentEncoding() != null && conn.getContentEncoding().equals("gzip"))
			stream = new GZIPInputStream(stream);
		StringBuilder response = new StringBuilder();
		String temp;
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		while ((temp = reader.readLine()) != null) {
			response.append(temp);
		}
		stream.close();
		// Logger.DLog("Response from "+ u + ":" + response);
		return response.toString();
	}

}
