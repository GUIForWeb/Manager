package manager.subsystems.settings;

import org.json.JSONObject;

public class SettingProp {
	public static String storageDir;
	public static String sqliteDir;
	public static String managerDir;
	public static String serverDir;
	public static String xmlPath;
	public static JSONObject json;
	public static String ipAddress;
	private static SettingProp instance;
	public static boolean isExsistedXML;
	public static SettingProp getInstance() {
		if(null == instance) {
			instance = new SettingProp();
		}
		return instance;
	}
}