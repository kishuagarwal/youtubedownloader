import java.util.HashMap;

/**
 * Created by Kishu Agarwal on 23-12-2014.
 */
public class DownloadLinkCache {

	private HashMap<String, HashMap<Integer, DownloadLink>> cacheMap;
	private static DownloadLinkCache singletonInstance;

	public static DownloadLinkCache getDownloadLinkCacheInstance() {
		if (singletonInstance == null) {
			singletonInstance = new DownloadLinkCache();
		}
		return singletonInstance;
	}

	private DownloadLinkCache() {
		cacheMap = new HashMap<>();
	}

	public void addVideoLink(String videoId, HashMap<Integer, DownloadLink> downloadLInks) {
		if (!downloadLinkExists(videoId)) {
			cacheMap.put(videoId, downloadLInks);
		}
	}

	public boolean downloadLinkExists(String videoId) {
		return cacheMap.containsKey(videoId);
	}

	public HashMap<Integer, DownloadLink> getDownloadLinks(String videoId) {
		return cacheMap.get(videoId);
	}

}
