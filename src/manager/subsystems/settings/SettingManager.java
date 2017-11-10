package manager.subsystems.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import system.xmls.XMLManager;

import org.apache.commons.io.FileUtils;

public class SettingManager {
	private XMLManager xmlManager;
	private JSONObject json;
	private String dtdPath;
	private String xmlPath;
	private File xmlFile;
	private File dtdFile;
	public SettingManager() {
		SettingProp.newInstance();
		SettingProp.managerDir = this.getClass().getClassLoader().getResource(".").getPath();
		this.dtdPath = SettingProp.managerDir + "settings.dtd";
		this.xmlPath = SettingProp.managerDir + "settings.xml";
		this.dtdFile = new File(this.dtdPath);
		this.xmlFile = new File(this.xmlPath);
		if(!this.dtdFile.exists())
			this.makeDTD();
		if(!this.xmlFile.exists())
			this.makeXML();
		this.xmlManager = new XMLManager(this.xmlPath);
	}
	private void makeDTD() {
		try {
			this.dtdFile.createNewFile();
			this.newDTD();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void makeXML() {
		try {
			this.xmlFile.createNewFile();
			this.newXML();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void newDTD() {
		String dtdStr = "";
		dtdStr += "<!ELEMENT settings ((directories|ip_address)+)>";
		dtdStr += System.getProperty("line.separator");
		dtdStr += "<!ELEMENT directories ((directory)+)>";
		dtdStr += System.getProperty("line.separator");
		dtdStr += "<!ELEMENT directory (#PCDATA)>";
		dtdStr += System.getProperty("line.separator");
		dtdStr += "<!ATTLIST directory name CDATA #REQUIRED>";
		dtdStr += System.getProperty("line.separator");
		dtdStr += "<!ELEMENT ip_address (#PCDATA)>";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(this.dtdPath));
			out.print(dtdStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void newXML() {
		String webGUIFolder = SettingProp.managerDir.replace("opt/WebGUIManager/bin/", "");
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);
			Element setsElm = doc.createElement("settings");
			doc.appendChild(setsElm);
			Element dirsElm = doc.createElement("directories");
			Element dirElm = doc.createElement("directory");
			Attr attr = doc.createAttribute("name");
			attr.setValue("server");
			dirElm.setAttributeNode(attr);
			dirElm.appendChild(doc.createTextNode(webGUIFolder+"opt/tomcat"));
			dirsElm.appendChild(dirElm);
			dirElm = doc.createElement("directory");
			attr = doc.createAttribute("name");
			attr.setValue("sqlite");
			dirElm.setAttributeNode(attr);
			dirElm.appendChild(doc.createTextNode(webGUIFolder+"opt/sqlite"));
			dirsElm.appendChild(dirElm);
			dirElm = doc.createElement("directory");
			attr = doc.createAttribute("name");
			attr.setValue("storage");
			dirElm.setAttributeNode(attr);
			dirElm.appendChild(doc.createTextNode(webGUIFolder+"home"));
			dirsElm.appendChild(dirElm);
			Element ipElm = doc.createElement("ip_address");
			setsElm.appendChild(dirsElm);
			setsElm.appendChild(ipElm);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(this.xmlPath));
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "settings.dtd");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	}
	public void loadXML() {
		JSONObject tmpJSON;
		this.xmlManager = new XMLManager(this.xmlPath);
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
		File f0 = new File(this.xmlPath);
		File f1 = new File(serverXMLPath);
		try {
			if(!FileUtils.contentEquals(f0,f1))
				FileUtils.copyFile(f0, f1);
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
