package manager.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import manager.xmls.XMLManager;
import manager.xmls.XMLReader;

public class SettingManager {
	public static String storageDir;
	public static String sqliteDir;
	public static String managerDir;
	public static String serverDir;
	public static String xmlPath;
	public static JSONObject json;
	public static String ipAddress;
	public static XMLManager xmlManager;
	private static SettingManager instance;
	public static SettingManager getInstance() {
		if(null == instance) {
			instance = new SettingManager();
		}
		instance.init();
		return instance;
	}
	public SettingManager() {
		managerDir = this.getClass().getClassLoader().getResource(".").getPath();
		xmlPath = managerDir + "settings.xml";
	}
	public void init(){
		JSONObject tmpJSON;
		xmlManager = new XMLManager();
		xmlManager.read(xmlPath,"directory");
		JSONArray jArr;
		if(xmlManager.getJSON().has("directory")){
			jArr = xmlManager.getJSON().getJSONArray("directory");
			json = new JSONObject();
			for(int ji=0; ji<jArr.length(); ji++) {
				tmpJSON = jArr.getJSONObject(ji);
				json.put(tmpJSON.getString("name"), tmpJSON.getString("textContent"));
			}
		}
		xmlManager.read(xmlPath,"ip_address");
		tmpJSON = xmlManager.getJSON();
		if(tmpJSON.has("ip_address")) {
			jArr = tmpJSON.getJSONArray("ip_address");
			json.put("ip_address",jArr.getJSONObject(0).get("textContent"));
		}
	}
	public static void loadDir() {
		serverDir = json.getString("server");
		sqliteDir = json.getString("sqlite");
		storageDir = json.getString("storage");
	}
	public static void loadIPAddress() {
		if(json.has("ip_address"))
			ipAddress = json.getString("ip_address");
	}
	public static void saveIPAddress() {
		if(null == json)
			json = new JSONObject();
		json.put("ip_address", ipAddress);
		xmlManager.put("ip_address", ipAddress);
		xmlManager.save();
	}
	public static void saveDir() {
		xmlManager.put("directory", serverDir, "name", "server");
		xmlManager.put("directory", sqliteDir, "name", "sqlite");
		xmlManager.put("directory", storageDir, "name", "storage");
		xmlManager.save();
	}
}
