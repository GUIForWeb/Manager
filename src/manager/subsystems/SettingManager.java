package manager.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JTextField;

import org.json.JSONObject;

public class SettingManager {
	private static String path;
	public static String storageDir;
	public static String ipAddress;
	private static JSONObject json;
	public static JTextField tFieldSDir;
	private static SettingManager instance;
	public static SettingManager getInstance() {
		if(null == instance) {
			instance = new SettingManager();
		}
		instance.init();
		return instance;
	}
	public SettingManager() {
		this.path = this.getClass().getClassLoader().getResource("").getPath() + "/setting.json";
	}
	public void init(){
		File sFile = new File(this.path);
		if(!sFile.exists()){
			try {
				sFile.createNewFile();
				this.json = new JSONObject();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Scanner scn = new Scanner(new File(this.path));
				String content = "{}";
				if(scn.hasNextLine()) {
					content = scn.next();
				}
				this.json = new JSONObject(content);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	public static void loadStorageDir() {
		if(json.has("storageDir"))
			tFieldSDir.setText(json.getString("storageDir"));
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
	public static void saveStorageDir() {
		if(null == json)
			json = new JSONObject();
		json.put("storageDir", storageDir);
		jsonToFile();
	}
	private static void jsonToFile() {
		try {
			FileWriter fw = new FileWriter(path);
			fw.write(json.toString());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getStorageDir() {
		return storageDir;
	}
	public void setStorageDir(String storageDir) {
		this.storageDir = storageDir;
	}
	public JTextField gettFieldSDir() {
		return tFieldSDir;
	}
	public void settFieldSDir(JTextField tFieldSDir) {
		this.tFieldSDir = tFieldSDir;
	}
}
