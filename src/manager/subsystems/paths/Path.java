package manager.subsystems.paths;

import org.json.JSONObject;

public class Path {
	public static String root;
	public static String storageDir;
	public static String sqliteDir;
	public static String managerDir;
	public static String serverDir;
	public static String serverExe;
	public static String serverXML;
	public static String xml;
	public static String dtd;
	public static JSONObject json;
	public static String ipAddress;
	public static String imgOff;
	public static String imgOn;
	private static Path instance;
	public static Path newInstance() {
		if(null == instance) {
			instance = new Path();
		}
		return instance;
	}
}