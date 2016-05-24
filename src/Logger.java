import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JLabel;

/**
 * @author Kishu Agarwal
 */
public class Logger {

	private static JLabel statusLabel;
	private static boolean isDebugLoggingEnabled;
	private static PrintWriter mPrintWriter;

	public static void init(JLabel statusLabel) {
		Logger.statusLabel = statusLabel;
		Logger.isDebugLoggingEnabled = false;
		try {
			mPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter("Log.txt")));
		} catch (IOException e) {
			Logger.log("Error Creating the log file. Skipping the log file creation.");
			mPrintWriter = null;
		}
	}

	public static void setIsDebugLoggingEnabled(boolean isDebugLoggingEnabled) {
		Logger.isDebugLoggingEnabled = isDebugLoggingEnabled;
	}

	public static void log(String message) {
		setStatus(message);
	}

	public static void DLog(String message) {
		if (isDebugLoggingEnabled) {
			setStatus(message);
		}
	}

	private static void setStatus(final String message) {
		try {
			// Delay of 1 second between two messages
			Thread.sleep(500);
		} catch (InterruptedException e) {

		}
		// Display it to the user
		if (statusLabel != null)
			statusLabel.setText(message);

		// Log it on the console
		System.out.println(message);

		// Print it the file
		if (mPrintWriter != null) {
			mPrintWriter.println(message);
		}
	}

	public static void close() {
		mPrintWriter.flush();
		mPrintWriter.close();
	}

}
