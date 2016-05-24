import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public final class YoutubePlaylistDownloader {

	private String playlistId;
	private ArrayList<String> videoIds;
	private OnVideoLinkFoundListener listener;

	public YoutubePlaylistDownloader(String playlistId, OnVideoLinkFoundListener listener) {
		this.playlistId = playlistId;
		this.videoIds = new ArrayList<>();
		this.listener = listener;
	}

	/**
	 * @deprecated
	 * Uses the youtube gdata api for finding the video ids.
	 * 
	 * @throws IOException
	 */
	private void getVideoIdsGdata() throws IOException {
		boolean hasNextPage = true;
		int pageCount = 0;
		int pageOffset;
		do {
			Logger.log("Processing playlist: " + playlistId + " page=" + pageCount);
			pageOffset = pageCount * 50 + 1;
			String pageUrl = Constants.PLAYLIST_PREFIX + playlistId + "?start-index=" + pageOffset + "&"
					+ Constants.PLAYLIST_SUFFIX;
			hasNextPage = parseXml(pageUrl);
			pageCount++;
		} while (hasNextPage);

		Logger.log("Playlist Processed." + videoIds.size() + " videos found.");
	}

	/**
	 * Parse the pageUrl for video ids.
	 * 
	 * @param pageUrl
	 *            the page url to search for video ids
	 * @return boolean indicating are there any next pages to look into
	 * @throws IOException
	 */
	private boolean parseXml(String pageUrl) throws IOException {
		if (pageUrl == null)
			throw new NullPointerException("Page Url can't be empty.");
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
		return response.contains(searchString);
	}

	private void getVideoIds() throws IOException {
		Logger.log("Processing playlist: " + playlistId);
		String response = Downloader.getResponse(Constants.PLAYLIST_PREFIX_SIMPLE + playlistId);
		int startIndex = response.indexOf("data-video-id=\"");
		int endIndex;
		while (startIndex != -1) {
			startIndex += 15;
			endIndex = response.indexOf("\"", startIndex);
			videoIds.add(response.substring(startIndex, endIndex));
			startIndex = response.indexOf("data-video-id=\"", startIndex);
		}
		Logger.log("Playlist Processed." + videoIds.size() + " videos found.");
	}

	/**
	 * Returns the download links for the videos in the current playlist.
	 * 
	 * @return ArrayList of HashMap for mappings from quality code to the
	 *         download link.
	 * @throws IOException
	 */
	public ArrayList<HashMap<Integer, DownloadLink>> getItags() throws IOException {
		getVideoIds();
		// getVideoIdsGdata();
		ArrayList<HashMap<Integer, DownloadLink>> downloadLinks = new ArrayList<>();
		// TODO:add thread support
		YoutubeVideoDownloader videoDownloader;
		for (String videoId : videoIds) {
			videoDownloader = new YoutubeVideoDownloader(videoId, listener);
			try {
				downloadLinks.add(videoDownloader.getItags());
			} catch (VideoLinkNotFound e) {
				Logger.DLog("Video link not found for " + videoId);
			}
		}
		return downloadLinks;
	}

	// For testing only
	public static void main(String args[]) throws IOException {
		String playlistUrl = "https://www.youtube.com/playlist?list=ELYR5txmTpa_c";
		YoutubePlaylistDownloader downloader = new YoutubePlaylistDownloader(playlistUrl, null);
		ArrayList<HashMap<Integer, DownloadLink>> links = downloader.getItags();

	}

}
