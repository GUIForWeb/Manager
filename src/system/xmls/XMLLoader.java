package system.xmls;

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

import system.xmls.XMLException;
import system.xmls.XMLLoader;

public class XMLLoader {
	private Document doc;
	private String xmlPath;
	private String treeTagName;
	private int option;
	private HashMap<String,String> info;
	private List<Map<String,String>> regularMap;
	private HashMap<String,Integer> counter;
	public final static int REGULAR = 1;
	public final static int REVERSE = 2;
	public XMLLoader()
	{
		this.info = new HashMap<String,String>();
		this.counter = new HashMap<String,Integer>();
		this.regularMap = new ArrayList<Map<String,String>>();
		this.option = XMLLoader.REGULAR;
	}
	public XMLLoader(String xmlPath, String treeTagName){
		this.xmlPath = xmlPath;
		this.treeTagName = treeTagName;
		this.info = new HashMap<String,String>();
		this.counter = new HashMap<String,Integer>();
		this.option = XMLLoader.REGULAR;
		this.regularMap = new ArrayList<Map<String,String>>();
		try {
			this.readXML();
		} catch (XMLException e) {
			e.printStackTrace();
		}
	}
	public XMLLoader(String xmlPath, String treeTagName, int option){
		this.xmlPath = xmlPath;
		this.treeTagName = treeTagName;
		this.info = new HashMap<String,String>();
		this.counter = new HashMap<String,Integer>();
		this.option = option;
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
		if(this.option == 0)
			throw new XMLException("option is null!");
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
			if(this.option == XMLLoader.REVERSE) {
				this.getReversedList(nodeList);
			}
			else if(this.option == XMLLoader.REGULAR) {
				this.getRegular(nodeList);
				//this.keyNormalization();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getReversedList(NodeList nodeList)
	{
		this.makeReversedList(nodeList);
	}
	private void getRegular(NodeList nodeList)
	{
		this.makeRegular(nodeList);
	}
	
	private void makeRegular(NodeList nodeList) {
		int tmpLen;
		@SuppressWarnings("unused")
		String tmpNodeName = "";
		@SuppressWarnings("unused")
		String tmpParentNodeName = "";
		@SuppressWarnings("unused")
		String tmpTextContent = "";
		Node tmpNode;
		NodeList tmpNodeList;
		tmpNodeList = nodeList;
		tmpLen = tmpNodeList.getLength();
		for(int li=0; li<tmpLen; li++) {
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
		}
	}
	
	private void makeReversedList(NodeList nodeList) {
		int tmpLen;
		int cnt;
		String tmpParentNodeName;
		String tmpTextContent;
		Node tmpNode;
		NodeList tmpNodeList;
		tmpNodeList = nodeList;
		tmpLen = tmpNodeList.getLength();
		
		for(int li=0; li<tmpLen; li++){
			tmpNode = tmpNodeList.item(li);
			tmpParentNodeName = tmpNode.getParentNode().getNodeName();
			tmpTextContent = tmpNode.getTextContent();
			if(tmpNode.hasChildNodes())
			{
				this.makeReversedList(tmpNode.getChildNodes());
			}
			else
			{
				if(!tmpNodeList.item(li).getTextContent().trim().equals(""))
				{
					if(!this.counter.containsKey(tmpParentNodeName))
					{
						this.info.put(tmpTextContent,tmpParentNodeName);
						this.counter.put(tmpParentNodeName, 0);
					}
					else if(this.counter.containsKey(tmpParentNodeName))
					{
						cnt = this.counter.get(tmpParentNodeName);
						cnt++;
						this.info.put(tmpTextContent,tmpParentNodeName);
						this.counter.put(tmpParentNodeName, cnt);
					}
				}
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
	public HashMap<String, String> getInfo() {
		return info;
	}
	public void setInfo(HashMap<String, String> info) {
		this.info = info;
	}
	public int getOption() {
		return option;
	}
	public void setOption(int option) {
		this.option = option;
	}
	public List<Map<String, String>> getRegularMap() {
		return regularMap;
	}
	public void setRegularMap(List<Map<String, String>> regularMap) {
		this.regularMap = regularMap;
	}
}
