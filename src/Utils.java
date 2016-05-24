import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Kishu Agarwal on 13-03-2015.
 */
public class Utils {

	public static void save(String contents, String fileName) throws IOException {
		if (contents == null)
			throw new NullPointerException("Content to write can't be empty.");
		if (fileName == null)
			throw new NullPointerException("Filename is empty.");
		PrintWriter printWriter = new PrintWriter(new File(fileName));
		printWriter.println(contents);
		printWriter.close();
	}
}
