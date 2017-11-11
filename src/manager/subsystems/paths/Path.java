package manager.subsystems.paths;

import org.json.JSONObject;

public class Path {
	public static String root;
	public static String storageDir;
	public static String sqliteFile;
	public static String managerDir;
	public static String serverDir;
	public static String serverExeFile;
	public static String serverXMLFile;
	public static String xmlFile;
	public static String dtdFile;
	public static String offImgFile;
	public static String onImgFile;
	public static JSONObject json;
	public static String ipAddress;
	private static Path instance;
	public static Path newInstance() {
		if(null == instance) {
			instance = new Path();
		}
		return instance;
	}
}