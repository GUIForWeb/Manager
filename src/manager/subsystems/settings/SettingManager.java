package manager.subsystems.settings;

import java.io.File;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import manager.xmls.XMLManager;

import org.apache.commons.io.FileUtils;

public class SettingManager {
	private XMLManager xmlManager;
	private JSONObject json;
	public SettingManager() {
		SettingProp.getInstance();
		SettingProp.managerDir = this.getClass().getClassLoader().getResource(".").getPath();
		SettingProp.xmlPath = SettingProp.managerDir + "settings.xml";
		this.xmlManager = new XMLManager(SettingProp.xmlPath);
		this.init();
		if(!SettingProp.isExsistedXML)
			this.makeXML();
		this.xmlManager.read();
	}
	private void makeXML() {
		
	}
	private void init(){
		File xmlFile = new File(SettingProp.xmlPath);
		if(xmlFile.exists()) 
			SettingProp.isExsistedXML = true;
		else
			SettingProp.isExsistedXML = false;
	}
	public void loadXML() {
		JSONObject tmpJSON;
		this.xmlManager = new XMLManager(SettingProp.xmlPath);
		this.xmlManager.read("directory");
		JSONArray jArr;
		if(xmlManager.getJSON().has("directory")){
			jArr = xmlManager.getJSON().getJSONArray("directory");
			json = new JSONObject();
			for(int ji=0; ji<jArr.length(); ji++) {
				tmpJSON = jArr.getJSONObject(ji);
				json.put(tmpJSON.getString("name"), tmpJSON.getString("textContent"));
			}
		}
		xmlManager.read("ip_address");
		tmpJSON = xmlManager.getJSON();
		if(tmpJSON.has("ip_address")) {
			jArr = tmpJSON.getJSONArray("ip_address");
			json.put("ip_address",jArr.getJSONObject(0).get("textContent"));
		}
	}
	public void loadDir() {
		this.loadXML();
		if(this.json != null) {
			SettingProp.serverDir = json.getString("server");
			SettingProp.sqliteDir = json.getString("sqlite");
			SettingProp.storageDir = json.getString("storage");
		}
	}
	public void loadIPAddress() {
		if(this.json != null && this.json.has("ip_address") && !this.json.getString("ip_address").equals("")){
			SettingProp.ipAddress = this.json.getString("ip_address");
		}
	}
	public void saveServerXML() {
		String serverXMLPath = SettingProp.serverDir + "/webapps/WebGUI/WEB-INF/settings.xml";
		File f0 = new File(SettingProp.xmlPath);
		File f1 = new File(serverXMLPath);
		try {
			System.out.println(FileUtils.contentEquals(f0,f1));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void saveIPAddress() {
		if(null == this.json)
			json = new JSONObject();
		this.json.put("ip_address", SettingProp.ipAddress);
		this.xmlManager.put("ip_address", SettingProp.ipAddress);
		this.xmlManager.save();
	}
	public void saveDir() {
		this.xmlManager.put("directory", SettingProp.serverDir, "name", "server");
		this.xmlManager.put("directory", SettingProp.sqliteDir, "name", "sqlite");
		this.xmlManager.put("directory", SettingProp.storageDir, "name", "storage");
		this.xmlManager.save();
	}
}
