package manager.xmls;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import manager.xmls.XMLWriter;

public class XMLWriter {
	private Document document;
	private String xmlPath;
	public XMLWriter(){
	}
	public XMLWriter(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	public void put(String tagName, String newText, String attr, String attrValue){
		NodeList nodeList;
		nodeList = this.document.getElementsByTagName(tagName);
		if(nodeList.getLength() == 0)
		try {
			throw new XMLException("the tag of the name deos not exist!");
		} catch (XMLException e) {
			e.printStackTrace();
		}
		this.findAndPut(nodeList, newText, attr, attrValue, 0);
	}
	public void put(String tagName, String newText){
		NodeList nodeList;
		nodeList = this.document.getElementsByTagName(tagName);
		if(nodeList.getLength() == 0)
		try {
			throw new XMLException("the tag of the name deos not exist!");
		} catch (XMLException e) {
			e.printStackTrace();
		}
		Node tmpNode = nodeList.item(0);
		tmpNode.setTextContent(newText);
	}
	public void save(){
		try {
			this.document.setXmlStandalone(true);
			Source input = new DOMSource(this.document);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			StreamResult output = new StreamResult(new File(this.xmlPath));
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "settings.dtd");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(input, output);
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void findAndPut(NodeList nodeList, String newText, String attr, String attrValue, int cnt){
		Node tmpNode = nodeList.item(cnt);
		if(tmpNode != null) {
			if(this.read(tmpNode.getAttributes(), attr, attrValue, 0)){
				tmpNode.setTextContent(newText);
			}
			this.findAndPut(nodeList, newText, attr, attrValue, ++cnt);
		}
	}
	private boolean read(NamedNodeMap namedNodeMap, String attr, String attrValue, int cnt){
		boolean flag = false;
		Node tmpNode = namedNodeMap.item(cnt);
		if(tmpNode != null) {
			if(tmpNode.getNodeName().equals(attr) && tmpNode.getTextContent().equals(attrValue))
				flag = true;
			this.read(namedNodeMap, attr, attrValue, ++cnt);
		}
		return flag;
	}
	public String getXmlPath() {
		return xmlPath;
	}
	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
}
