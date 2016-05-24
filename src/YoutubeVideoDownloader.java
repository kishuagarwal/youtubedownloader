import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import com.google.gson.Gson;

import model.YoutubeMap;

public class YoutubeVideoDownloader {

	private String videoId;
	private OnVideoLinkFoundListener listener;

	public YoutubeVideoDownloader(String videoId, OnVideoLinkFoundListener listener) {
		this.videoId = videoId;
		this.listener = listener;
	}

	/*
	 * Get the json embedded in the html page inside ytplayer.config tag
	 */
	public static String getJSONString(String response) {
		String s = "ytplayer.config";
		int startIndex = response.indexOf(s) + s.length() + 2;
		int endIndex = response.indexOf("};", startIndex) + 1;
		// Logger.log(response);
		// Logger.log(response.substring(startIndex, endIndex));
		return response.substring(startIndex, endIndex);
	}

	public HashMap<Integer, DownloadLink> getItags() throws IOException, VideoLinkNotFound {
		HashMap<Integer, DownloadLink> links = DownloadLinkCache.getDownloadLinkCacheInstance()
				.getDownloadLinks(videoId);
		Logger.DLog("Checking if the download links for videoId " + videoId + " are present in the cache.");
		if (links == null) {
			Logger.DLog("Download links not present.");
			links = getYoutubeVideoLinkJson();
			DownloadLinkCache.getDownloadLinkCacheInstance().addVideoLink(videoId, links);
			Logger.DLog("Added download links for videoId " + videoId + " into the cache.");
		} else {
			Logger.DLog("Cache Hit.Download links found for the videoId " + videoId);
		}
		if (listener != null)
			listener.onVideoLinkFound(links);
		return links;
	}

	/*
	 * Gets the video itags using the get_video_info extension to the youtube.
	 */
	public HashMap<Integer, DownloadLink> getYoutubeVideoLink() throws VideoLinkNotFound, IOException {
		String videoUrl = "https://www.youtube.com/get_video_info?video_id=" + videoId;
		Logger.log("Processing video link: " + videoUrl);
		String response = Downloader.getResponse(videoUrl);
		HashMap<String, String> videoInfo = UrlFormEncodedDecode(response);
		// Logger.log(videoInfo.toString());
		String[] formats = videoInfo.get("url_encoded_fmt_stream_map").split(",");
		HashMap<Integer, DownloadLink> itags = new HashMap<>();
		for (String format : formats) {
			HashMap<String, String> formatInfo = UrlFormEncodedDecode(format);
			itags.put(Integer.parseInt(formatInfo.get("itag")),
					new DownloadLink(videoInfo.get("title"),
							formatInfo.get("url") + "&title=" + URLEncoder.encode(videoInfo.get("title"), "utf-8"),
							Long.parseLong(videoInfo.get("length_seconds"))));
		}
		Logger.log("Processed " + videoInfo.get("title"));
		return itags;
	}

	/*
	 * Gets the video links embeded in the html of the video page. Used when
	 * above one fails. For example when downloading copyrighted material.
	 */
	public HashMap<Integer, DownloadLink> getYoutubeVideoLinkJson() throws VideoLinkNotFound, IOException {
		String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
		Logger.log("Processing video link: " + videoUrl);
		String response = Downloader.getResponse(videoUrl);
		if (response.contains("Sign in to confirm your age")) {
			Logger.DLog("Video is age-restricted.Switching to second download method.");
			return getYoutubeVideoLink();
		}
		response = getJSONString(response);
		Gson gson = new Gson();
		YoutubeMap url_map = gson.fromJson(response, YoutubeMap.class);
		System.out.println("Video title found is: " + url_map.args.title + "\nLength is:" + url_map.args.length_seconds
				+ "\nfmt_url_map :" + url_map.args.url_encoded_fmt_stream_map);

		String[] formats = url_map.args.url_encoded_fmt_stream_map.split(",");
		HashMap<Integer, DownloadLink> itags = new HashMap<>();
		for (String format : formats) {
			HashMap<String, String> formatInfo = UrlFormEncodedDecode(format);
			itags.put(Integer.parseInt(formatInfo.get("itag")),
					new DownloadLink(url_map.args.title,
							formatInfo.get("url") + "&title=" + URLEncoder.encode(url_map.args.title, "utf-8"),
							url_map.args.length_seconds));
		}
		Logger.log("Processed " + url_map.args.title);
		return itags;

	}

	public HashMap<String, String> UrlFormEncodedDecode(String response) throws IOException {
		HashMap<String, String> dict = new HashMap<>();
		String[] keyValue = response.split("&");
		for (String k : keyValue) {
			String pairs[] = k.split("=");
			if (pairs.length == 2) {
				dict.put(pairs[0], URLDecoder.decode(pairs[1], "UTF-8"));
			}
		}
		return dict;
	}

}
