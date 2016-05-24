/**
 * Created by Kishu Agarwal on 14-12-2014.
 */
public class HttpUtils {

	public static String linkCleanup(String link) {
		if (link.indexOf(" ") != -1) // link contains spaces
		{
			link = link.substring(0, link.indexOf(" "));
		}
		return link;
	}

	public static boolean isValidUrl(String url) {
		String pattern = "(https?://)?www\\.[w-]*\\.com";
		return url.matches(pattern);
	}

}
