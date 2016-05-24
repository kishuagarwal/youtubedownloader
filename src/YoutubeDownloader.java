import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @Kishu Agarwal
 */
public class YoutubeDownloader implements OnVideoLinkFoundListener {

	private JTable downloadLinksTable;
	private ArrayList<Link> inputLinks;
	private double totalSize;
	private HashMap<String, Integer> qualityCodes;
	private String selectedQuality;
	private ArrayList<HashMap<Integer, DownloadLink>> downloadLinks;
	private JLabel size;
	private DownloadListener listener;
	private JLabel statuslabel;
	private boolean errorsOccured;
	private int count;

	// Stores the links for the videos which have links for the selected
	// quality.
	private ArrayList<String> finalDownloadLinks;

	private Thread thread;

	public YoutubeDownloader(JTable table, JLabel size, DownloadListener listener) {
		this.downloadLinksTable = table;
		this.listener = listener;
		this.size = size;
		qualityCodes = QualityCodes.getQualityCodes();
	}

	private void cleanUpLinks(String[] links) {
		for (int i = 0; i < links.length; i++) {
			links[i] = HttpUtils.linkCleanup(links[i]);
		}
	}

	public void fetchLinks(String[] links) {
		// stop any running thread
		if (thread != null) {
			thread.interrupt();
		}
		cleanUpLinks(links);
		inputLinks = new ArrayList<>();
		for (String s : links) {
			inputLinks.add(new Link(s));
		}
		errorsOccured = false;
		totalSize = 0;
		count = 0;
		clearTable();
		size.setText("0 KB");
		if (finalDownloadLinks == null) {
			finalDownloadLinks = new ArrayList<>();
		} else
			finalDownloadLinks.clear();
		// list of download links for each file
		// one hash map for one file
		// integer value denotes the quality and download link denotes the
		// corresponding download link
		downloadLinks = new ArrayList<HashMap<Integer, DownloadLink>>();

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					processInputLinks();
					size.setText(getTotalSize());
					listener.onDownloadComplete();
				} catch (IOException ex) {
					Logger.log("I/O Exception occurred.");
				}
			}
		});
		thread.start();

	}

	private String getTotalSize() {
		double size = totalSize / 1024;
		if (size < 1024) {
			return String.format("%.2f", size) + " KB";
		} else {
			size = size / 1024;
			if (size < 1024) {
				return String.format("%.2f", size) + " MB";
			} else {
				size = size / 1024;
				return String.format("%.2f", size) + " GB";
			}
		}
	}

	private void displayResults() {
		DefaultTableModel model = (DefaultTableModel) downloadLinksTable.getModel();
		int count = 0;
		clearTable();
		if (finalDownloadLinks == null)
			finalDownloadLinks = new ArrayList<>();
		finalDownloadLinks.clear();
		if (downloadLinks != null) {
			for (HashMap<Integer, DownloadLink> h : downloadLinks) {
				// For each file get the download link corresponding to the user
				// selected quality value
				DownloadLink l = h.get(qualityCodes.get(selectedQuality));
				// First check if the download link for the selected quality
				// exists or not.
				if (l != null) {
					// If it exists than add it the table
					model.addRow(new Object[] { count, true, l.title, l.minutes + "" });
					finalDownloadLinks.add(l.link);
					totalSize += l.size;
					count++;
				}
			}
		}
	}

	/**
	 * Displays the found links for the video in the table. The method is
	 * synchronized so as to avoid any table ambiguity.
	 * 
	 * @param itags
	 *            the links to show
	 */
	private synchronized void displayLink(HashMap<Integer, DownloadLink> itags) {
		// results.setText("");
		DefaultTableModel model = (DefaultTableModel) downloadLinksTable.getModel();
		DownloadLink l = itags.get(qualityCodes.get(selectedQuality));
		// Checks to see if the link is available for the selected quality
		// preference
		if (l != null) {
			model.addRow(new Object[] { count, true, l.title, l.minutes + "" });
			totalSize += l.size;
			finalDownloadLinks.add(l.link);
			count++;
			size.setText(getTotalSize());
		}
	}

	private void processInputLinks() throws IOException {
		for (Link l : inputLinks) {
			if (l.isPlaylistUrl()) {
				YoutubePlaylistDownloader playlistDownloader = new YoutubePlaylistDownloader(l.getId(), this);
				downloadLinks.addAll(playlistDownloader.getItags());
			} else {
				if (l.isChannelUrl()) {
					YoutubeChannelDownloader channelDownloader = new YoutubeChannelDownloader(l.getId(), this);
					downloadLinks.addAll(channelDownloader.getItags());
				} else {
					YoutubeVideoDownloader videoDownloader = new YoutubeVideoDownloader(l.getId(), this);
					try {
						downloadLinks.add(videoDownloader.getItags());
					} catch (VideoLinkNotFound videoLinkNotFound) {
						Logger.DLog("Video link not found for the video " + l.getId());
					}
				}
			}
		}
		Logger.log("All links processed.");
	}

	/**
	 * This changes the quality control of the videos. It also redisplays
	 * results based on the new selected quality.
	 * 
	 * @param quality
	 *            the new quality
	 */
	public void setQuality(String quality) {
		selectedQuality = quality;
		totalSize = 0;
		finalDownloadLinks = null; // new download links would be there for new
									// quality selection
		displayResults();
		size.setText(getTotalSize());
	}

	/**
	 * Returns the download links of the selected video ids as a string. All the
	 * different links are separated by a newline operator '\n'.
	 * 
	 * @return list of download links separated by '\n'
	 */
	public synchronized String getLinks() {
		final DefaultTableModel tableModel = (DefaultTableModel) downloadLinksTable.getModel();
		final StringBuilder finalLinks = new StringBuilder();
		int rows = tableModel.getRowCount();
		for (int i = 0; i < rows; i++) {
			if ((boolean) tableModel.getValueAt(i, 1)) {
				finalLinks.append(finalDownloadLinks.get(i) + "\n");
			}
		}
		return finalLinks.toString();
	}

	/**
	 * Clears the table of any data.
	 */
	private void clearTable() {
		DefaultTableModel tableModel = (DefaultTableModel) downloadLinksTable.getModel();
		int count = tableModel.getRowCount();
		for (int i = 0; i < count; i++) {
			tableModel.removeRow(0);
		}
	}

	//TODO: Implement the reset button
	public void cancelRunningTask() {
		
	}

	@Override
	public void onVideoLinkFound(HashMap<Integer, DownloadLink> link) {
		displayLink(link);
	}

	interface DownloadListener {
		public void onDownloadComplete();

		public void onDownloadError();

	}

}
