import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Link {

	private String link;
	private boolean isPlaylistUrl;
	private boolean isVideoUrl;
	private boolean isChannelUrl;

	public String getId() {
		return id;
	}

	private String id;

	public Link(String link) {
		this.link = link.trim(); // remove whitespaces from both the sides

		if (!link.startsWith("https")) {
			this.link = new StringBuilder(link).insert(4, 's').toString();
		}
		// if (!HttpUtils.isValidUrl(this.link))
		// throw new InvalidLinkException();
		parseLink();
		// parseLinkRegEx();
	}

	public String getLink() {
		return link;
	}

	// TODO: add support for regular expression
	private void parseLink() {
		if (link.contains("watch?v")) {
			isVideoUrl = true;
			if (link.contains("&"))
				id = link.substring(link.indexOf("v=") + 2, link.indexOf("&"));
			else
				id = link.substring(link.indexOf("v=") + 2);
		} else {
			if (link.contains("playlist?")) {
				isPlaylistUrl = true;
				if (link.contains("&"))
					id = link.substring(link.indexOf("list=") + 5, link.indexOf("&"));
				else
					id = link.substring(link.indexOf("list=") + 5);
			} else {
				if (link.contains("channel")) {
					isChannelUrl = true;
					int channelStartIndex = link.indexOf("channel/") + 8;
					int channelEndIndex;
					if (link.indexOf("/", channelStartIndex) != -1) {
						channelEndIndex = link.indexOf("/", channelStartIndex);
					} else {
						channelEndIndex = link.length() - 1;
					}
					id = link.substring(channelStartIndex, channelEndIndex);
				}
			}
		}
	}

	private void parseLinkRegEx() {
		Pattern videoRegEx = Pattern.compile("https?://www\\.youtube\\.com/watch\\?v=([\\w-]*)(&(\\.)*)*");
		Matcher videoMatcher = videoRegEx.matcher(link);
		if (videoMatcher.matches()) {
			isVideoUrl = true;
			id = videoMatcher.group(2);
		} else {
			Pattern playlistRegEx = Pattern.compile("https?://www\\.youtube\\.com/playlist\\?list=([\\w-]*)");
			Matcher playlistMatcher = playlistRegEx.matcher(link);
			if (playlistMatcher.matches()) {
				isPlaylistUrl = true;
				id = playlistMatcher.group(2);
			}
		}
	}

	public boolean isVideoUrl() {
		return isVideoUrl;
	}

	public boolean isPlaylistUrl() {
		return isPlaylistUrl;
	}

	public boolean isChannelUrl() {
		return isChannelUrl;
	}

	public static class InvalidLinkException extends Exception {
		
		private static final long serialVersionUID = 1L;

		@Override
		public String getMessage() {
			return "The link is not a valid url.";
		}
	}

}