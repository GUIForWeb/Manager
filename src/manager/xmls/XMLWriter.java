/****************************************************************************************************
* Project: comp3095assignment2
* Assignment: Assignment 2 
* Author(s): Gon Hu, Elis Shukullari, Leba Rubinoff
* Student Number: 100936779, 100823478, 100831385 
* Date: December 5, 2016
* Description: XML files Loader
****************************************************************************************************/
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import manager.xmls.XMLException;
import manager.xmls.XMLWriter;

public class XMLWriter {
	private Document doc;
	private String xmlPath;
	private String treeTagName;
	private HashMap<String,String> info;
	private List<Map<String,String>> regularMap;
	public XMLWriter()
	{
		this.info = new HashMap<String,String>();
		this.regularMap = new ArrayList<Map<String,String>>();
	}
	public XMLWriter(String xmlPath, String treeTagName){
		this.xmlPath = xmlPath;
		this.treeTagName = treeTagName;
		this.info = new HashMap<String,String>();
		this.regularMap = new ArrayList<Map<String,String>>();
		try {
			this.readXML();
		} catch (XMLException e) {
			e.printStackTrace();
		}
	}
	
	public void readXML() throws XMLException
	{
		if(this.xmlPath == null)
			throw new XMLException("XML path is null!");
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
			this.makeRegular(nodeList);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void makeRegular(NodeList nodeList) {
		int tmpLen;
		String tmpNodeName = "";
		String tmpParentNodeName = "";
		String tmpTextContent = "";
		Node tmpNode;
		NodeList tmpNodeList;
		tmpNodeList = nodeList;
		tmpLen = tmpNodeList.getLength();
		for(int li=0; li<tmpLen; li++) {
			System.out.println(nodeList.item(li).getAttributes().getNamedItem("name").getTextContent());
			System.out.println(nodeList.item(li).getTextContent());
			/*
			tmpNode = tmpNodeList.item(li);
			tmpNodeName = tmpNode.getNodeName();
			tmpParentNodeName = tmpNode.getParentNode().getNodeName();
			tmpTextContent = tmpNode.getTextContent();
			if(tmpNode.hasChildNodes()) {
				this.makeRegular(tmpNode.getChildNodes());
			}
			else {
				if(!tmpNode.getTextContent().trim().equals("") || tmpNode.getNodeType() == 1) {
					Map<String,String> tmpRegularMap = new HashMap<String,String>();
					tmpRegularMap.put(tmpNode.getParentNode().getNodeName(),tmpNode.getTextContent());
					AttributeMap tmpAMap = (AttributeMap) tmpNode.getParentNode().getAttributes();
					for(int mi=0; mi<tmpAMap.getLength(); mi++)
						tmpRegularMap.put(tmpAMap.item(mi).getNodeName(),tmpAMap.item(mi).getTextContent());
					this.regularMap.add(tmpRegularMap);
				}
			}
			*/
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
	public HashMap<String, String> getInfo() {
		return info;
	}
	public void setInfo(HashMap<String, String> info) {
		this.info = info;
	}
	public List<Map<String, String>> getRegularMap() {
		return regularMap;
	}
	public void setRegularMap(List<Map<String, String>> regularMap) {
		this.regularMap = regularMap;
	}
}
