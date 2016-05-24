/**
 * @author Kishu Agarwal
 */
public class DownloadLink {
	String link;
	long size;
	String title;
	int minutes;

	public DownloadLink(String title, String link, long sec) {
		this.title = title;
		this.link = link;
		this.minutes = (int) (sec / 60);
		getSize();

	}

	public void getSize() {
		/*
		 * try { URL url = new URL(link); HttpURLConnection conn =
		 * (HttpURLConnection) url.openConnection(); Logger.DLog(
		 * "Trying to get the video size.");
		 * conn.setRequestProperty("user-agent",
		 * "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0"
		 * ); conn.setRequestMethod("HEAD");
		 * conn.setInstanceFollowRedirects(true); size =
		 * conn.getContentLengthLong(); Logger.DLog("Video Size found to be "+
		 * size+ "byres"); } catch (IOException e) { System.out.println(
		 * "Can't find the video size."); }
		 */
		size = 0;
	}

}
