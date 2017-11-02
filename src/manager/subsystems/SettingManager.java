package manager.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

public class SettingManager {
	private static String jsonPath;
	public static String storageDir;
	public static String sqliteDir;
	public static String managerDir;
	public static String serverDir;
	public static String ipAddress;
	private static JSONObject json;
	private static SettingManager instance;
	public static SettingManager getInstance() {
		if(null == instance) {
			instance = new SettingManager();
		}
		instance.init();
		return instance;
	}
	public SettingManager() {
		managerDir = this.getClass().getClassLoader().getResource("").getPath();
		jsonPath = managerDir + "/setting.json";
	}
	public void init(){
		File sFile = new File(jsonPath);
		if(!sFile.exists()){
			try {
				sFile.createNewFile();
				json = new JSONObject();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Scanner scn = new Scanner(new File(jsonPath));
				String content = "";
				while(scn.hasNextLine()) {
					content += scn.next();
				}
				if(content.equals(""))
					content = "{}";
				json = new JSONObject(content);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	public static void loadDir() {
		if(json.has("storageDir"))
			storageDir = json.getString("storageDir");
		if(json.has("sqliteDir"))
			sqliteDir = json.getString("sqliteDir");
		if(json.has("managerDir"))
			managerDir = json.getString("managerDir");
		if(json.has("serverDir"))
			serverDir = json.getString("serverDir");
	}
	public static void loadIPAddress() {
		if(json.has("ipAddress"))
			ipAddress = json.getString("ipAddress");
	}
	public static void saveIPAddress() {
		if(null == json)
			json = new JSONObject();
		json.put("ipAddress", ipAddress);
		jsonToFile();
	}
	public static void saveDir() {
		if(null == json)
			json = new JSONObject();
		json.put("storageDir", storageDir);
		json.put("sqliteDir", sqliteDir);
		json.put("managerDir", managerDir);
		json.put("serverDir", serverDir);
		jsonToFile();
	}
	private static void jsonToFile() {
		try {
			FileWriter fw = new FileWriter(jsonPath);
			fw.write(json.toString());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
