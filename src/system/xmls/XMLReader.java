package system.xmls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import system.xmls.XMLException;
import system.xmls.XMLReader;

public class XMLReader {
	private Document document;
	private String xmlPath;
	private String treeTagName;
	private String ppName = "";
	private JSONObject json = new JSONObject();
	private List<Integer> hashCodeList;
	public XMLReader(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	public XMLReader(String xmlPath, String treeTagName){
		this.xmlPath = xmlPath;
		this.treeTagName = treeTagName;
	}
	public void readXML(String xmlPath, String treeTagName) {
		this.xmlPath = xmlPath;
		this.treeTagName = treeTagName;
		this.readXML();
	}
	public void readXML(String treeTagName) {
		this.treeTagName = treeTagName;
		this.readXML();
	}
	private void read(NamedNodeMap namedNodeMap,JSONObject json, int cnt){
		Node tmpNode = namedNodeMap.item(cnt);
		if(tmpNode != null) {
			json.put(tmpNode.getNodeName(), tmpNode.getTextContent());
			this.read(namedNodeMap,json, ++cnt);
		}
	}
	public void initDocument() {
		try{	
			if(this.xmlPath == null)
				throw new XMLException("XML does not exist!");
			File xmlFile = new File(this.xmlPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setIgnoringComments(true);
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			dbFactory.setValidating(true);
			this.document = dBuilder.parse(xmlFile);
			this.document.getDocumentElement().normalize();
		} catch (XMLException | ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	private NodeList initNodeList() {
		NodeList nodeList = null;
		if(this.treeTagName == null)
			nodeList = this.document.getChildNodes();
		else if(this.treeTagName != null) 
			nodeList = this.document.getElementsByTagName(this.treeTagName);
		return nodeList;
	}
	public void readXML() {
		try {
			NodeList nodeList = this.initNodeList();
			if(nodeList.getLength() == 0) 
				throw new XMLException("the tag of the name deos not exist!");
			else {
				this.json = new JSONObject();
				this.hashCodeList = new ArrayList<Integer>();
				this.read(nodeList, new JSONObject(), 0);
				this.json = this.json.getJSONObject(this.json.keys().next());
			}
		} catch (XMLException e) {
			e.printStackTrace();
		} 
	}
	private void findAndPut(JSONObject json, String pName, String name){
		if(json.has(pName)){
			JSONObject tmpJSON = json.getJSONObject(pName);
			tmpJSON.put(name, new JSONObject());
		}
		else {
			this.findPName(json, pName, name);
		}
	}
	private void findPName(JSONObject json, String pName, String name){
		JSONObject tmpJSON = null;
		if(json.length() != 0) {
			java.util.Iterator<String> keys = json.keys();
			while(keys.hasNext()) {
				Object obj = json.get(keys.next());
				if(obj instanceof JSONObject){
					tmpJSON = (JSONObject) obj;
					this.findAndPut(tmpJSON, pName, name);
				}
			}
		}
	}
	private Node read(NodeList nodeList, JSONObject json, int cnt) throws XMLException {
		Node tmpNode = nodeList.item(cnt);
		JSONArray tmpJArr;
		if(tmpNode != null) {
			String name = tmpNode.getNodeName();
			String pName = tmpNode.getParentNode().getNodeName();
			if(tmpNode.hasChildNodes()) {
				if(this.json.length() == 0)
					this.json.put(pName,new JSONObject());
				this.findAndPut(this.json, pName, name);
				tmpNode = this.read(tmpNode.getChildNodes(), json, 0);
				if(tmpNode != null) {
					String tmpPPName = tmpNode.getParentNode().getParentNode().getNodeName();
					if(!this.ppName.equals(tmpPPName)){
						this.ppName = tmpPPName;
						json = new JSONObject();
						json.put(name, new JSONArray());
						json.put("pName",pName);
					}
					tmpJArr = json.getJSONArray(name);
					JSONObject tmpJSON = new JSONObject();
					this.read(tmpNode.getParentNode().getAttributes(),tmpJSON, 0);
					tmpJSON.put("textContent", tmpNode.getTextContent());
					tmpJArr.put(tmpJSON);
				}
			}
			else if(tmpNode.getTextContent() != null && !tmpNode.getTextContent().trim().equals("")) {
				return tmpNode;
			}
			else if(tmpNode.getNodeType() != 3 && tmpNode.getTextContent() != null) {
				pName = tmpNode.getParentNode().getNodeName();
				if(this.json.length() == 0)
					this.json.put(pName,new JSONObject());
				this.findAndPut(this.json, pName, name);
				if(!json.has(name)) {
					json = new JSONObject();
					json.put(name, new JSONArray());
					json.put("pName",pName);
				}
				if(!json.getString("pName").equals(pName)){
					json = new JSONObject();
					json.put(name, new JSONArray());
					json.put("pName",pName); 
				}
				tmpJArr = json.getJSONArray(name);
				JSONObject tmpJSON = new JSONObject();
				this.read(tmpNode.getAttributes(),tmpJSON, 0);
				tmpJSON.put("textContent", tmpNode.getTextContent());
				tmpJArr.put(tmpJSON);
			}
			tmpNode = this.read(nodeList, json, ++cnt);
			int hashCode = json.hashCode();
			if(json.length() != 0 && !this.hashCodeList.contains(hashCode)) {
				this.hashCodeList.add(hashCode);
				pName = json.getString("pName");
				json.remove("pName");
				name = (String) json.keySet().toArray()[0];
				this.findAndPut(name, pName, this.json, json);
			}
		}
		return tmpNode;
	}
	private void findAndPut(String name, String pName, JSONObject json, JSONObject valJSON) {
		JSONObject tmpJSON = null;
		if(!json.has(pName)) {
			java.util.Iterator<String> keys = json.keys();
			while(keys.hasNext()) {
				Object obj = json.get(keys.next());
				if(obj instanceof JSONObject){
					tmpJSON = (JSONObject) obj;
					this.findAndPut(name, pName, tmpJSON, valJSON);
				}
			}
		}
		else {
			tmpJSON = json.getJSONObject(pName);
			if(tmpJSON.has(name)){
				tmpJSON.put(name, valJSON.get(name));	
			}
		}
	}
	public String getXmlPath() {
		return xmlPath;
	}
	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	public String getTreeTagName() {
		return treeTagName;
	}
	public void setTreeTagName(String treeTagName) {
		this.treeTagName = treeTagName;
	}
	public JSONObject getJson() {
		return json;
	}
	public void setJson(JSONObject json) {
		this.json = json;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
}
