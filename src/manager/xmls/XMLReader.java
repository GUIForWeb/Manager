package manager.xmls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;

import org.apache.xerces.dom.AttributeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ibm.icu.util.BytesTrie.Iterator;

import manager.xmls.XMLException;
import manager.xmls.XMLReader;

public class XMLReader {
	private Document doc;
	private String xmlPath;
	private String treeTagName;
	private String ppName = "";
	private JSONObject json = new JSONObject();
	private JSONArray jsonArray = new JSONArray();
	private List<Integer> hashCodeList;
	public XMLReader(String xmlPath) {
	}
	public XMLReader(String xmlPath, String treeTagName){
		this.xmlPath = xmlPath;
		this.treeTagName = treeTagName;
		try {
			this.readSpecificXML();
		} catch (XMLException e) {
			e.printStackTrace();
		}
	}
	
	public void readSpecificXML() throws XMLException {
		if(this.xmlPath == null)
			throw new XMLException("XML does not exist!");
		if(this.treeTagName == null)
			throw new XMLException("treeTagName is null!");
		File xmlFile = new File(this.xmlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setIgnoringComments(true);
		DocumentBuilder dBuilder;
		NodeList nodeList;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			dbFactory.setValidating(true);
			this.doc = dBuilder.parse(xmlFile);
			this.doc.getDocumentElement().normalize();
			nodeList = this.doc.getElementsByTagName(this.treeTagName);
			if(nodeList.getLength() == 0) 
				throw new XMLException("the tag of the name deos not exist!");
			else {
				this.json = new JSONObject();
				this.hashCodeList = new ArrayList<Integer>();
				this.read(nodeList, new JSONObject(), 0);
				System.out.println("============================");
				System.out.println(this.json);
			}	
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		if(tmpNode != null) {
			String name = tmpNode.getNodeName();
			String pName = tmpNode.getParentNode().getNodeName();
			if(tmpNode.hasChildNodes()) {
				if(this.json.length() == 0)
					this.json.put(pName,new JSONObject());
				this.findAndPut(this.json, pName, name);
				tmpNode = this.read(tmpNode.getChildNodes(), json, 0);
				if(tmpNode != null) {
					JSONArray tmpJArr;
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
			else if(!tmpNode.getTextContent().trim().equals("")) {
				return tmpNode;
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
	private void read(NamedNodeMap namedNodeMap,JSONObject jsonObject, int cnt){
		Node tmpNode = namedNodeMap.item(cnt);
		if(tmpNode != null) {
			jsonObject.put(tmpNode.getNodeName(), tmpNode.getTextContent());
			this.read(namedNodeMap,jsonObject, ++cnt);
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
}
