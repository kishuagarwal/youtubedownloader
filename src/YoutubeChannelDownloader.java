import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kishu Agarwal on 22-12-2014.
 */
public class YoutubeChannelDownloader {
	private String channelId;
	private ArrayList<String> videoIds;
	private OnVideoLinkFoundListener listener;
	private int pageCount;
	private String channelUrl;

	public YoutubeChannelDownloader(String channelId, OnVideoLinkFoundListener listener) {
		this.channelId = channelId;
		this.videoIds = new ArrayList<>();
		this.listener = listener;
		videoIds = new ArrayList<>();
		pageCount = 1;
		channelUrl = Constants.CHANNEL_PREFIX + channelId + Constants.CHANNEL_SUFFIX;
	}

	private void getVideoIds() throws IOException {
		String pageUrl = channelUrl;
		do {
			Logger.log("Processing channel: " + channelId + " page=" + pageCount);
			pageUrl = getVideoIdsFromPage(pageUrl);
			pageCount++;
			if (pageUrl != null && !pageUrl.contains("prettyprint"))
				pageUrl = pageUrl + "&prettyprint=true";
		} while (pageUrl != null);

		Logger.log("Channel Processed." + videoIds.size() + " videos found.");
	}

	private String getVideoIdsFromPage(String pageUrl) throws IOException {
		String response = Downloader.getResponse(pageUrl);
		String searchQueary = "href='http://www.youtube.com/watch?v=";
		int startIndex = response.indexOf(searchQueary);
		int endIndex;
		while (startIndex != -1) {
			startIndex += searchQueary.length();
			endIndex = response.indexOf("&", startIndex);
			videoIds.add(response.substring(startIndex, endIndex));
			startIndex = response.indexOf(searchQueary, startIndex);
		}
		String searchString = "<link rel='next'";
		if (response.contains(searchString)) {
			int start = response.indexOf(searchString);
			int nextPageStartIndex = response.indexOf("href='", start) + 6;
			int nextPageEndIndex = response.indexOf("'", nextPageStartIndex);
			return response.substring(nextPageStartIndex, nextPageEndIndex);
		}
		return null;

	}

	public ArrayList<HashMap<Integer, DownloadLink>> getItags() throws IOException {
		getVideoIds();
		ArrayList<HashMap<Integer, DownloadLink>> downloadLinks = new ArrayList<>();
		for (String videoId : videoIds) {
			YoutubeVideoDownloader videoDownloader = new YoutubeVideoDownloader(videoId, listener);
			try {
				downloadLinks.add(videoDownloader.getItags());
			} catch (VideoLinkNotFound videoLinkNotFound) {
				videoLinkNotFound.printStackTrace();
			}
		}
		return downloadLinks;
	}

}
