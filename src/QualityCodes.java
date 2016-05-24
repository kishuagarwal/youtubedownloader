import java.util.HashMap;

/**
 * @author Kishu Agarwal
 */

public class QualityCodes {

	private static HashMap<String, Integer> qualityCodes = new HashMap<String, Integer>();

	public static void init() {
		qualityCodes.put("FLV 240p", 5);
		qualityCodes.put("3GP 144p", 17);
		qualityCodes.put("MP4 360P", 18);
		qualityCodes.put("MP4 720P", 22);
		qualityCodes.put("3GP 240P", 36);
		qualityCodes.put("WebM 360P", 43);
		qualityCodes.put("MP4 360P 3D", 82);
		qualityCodes.put("MP4 240P 3D", 83);
		qualityCodes.put("MP4 720P 3D", 84);
		qualityCodes.put("MP4 1080P 3D", 85);
		qualityCodes.put("WebM 360P VP8", 100);
	}

	public static HashMap<String, Integer> getQualityCodes() {
		return qualityCodes;
	}

}
