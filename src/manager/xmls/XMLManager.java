package manager.xmls;

import org.json.JSONObject;
import org.w3c.dom.Document;

public class XMLManager {
	private Document document;
	private XMLReader reader = new XMLReader();
	private XMLWriter writer = new XMLWriter();
	private String xmlPath;
	private String treeTagName;
	public XMLManager() {
	}
	public XMLManager(String xmlPath) {
		this.xmlPath = xmlPath;
		this.initXML();
	}
	public XMLManager(String xmlPath, String treeTagName) {
		this.xmlPath = xmlPath;
		this.treeTagName = treeTagName;
		this.initSpecificXML();
	}
	public void read(String xmlPath) {
		this.xmlPath = xmlPath;
		this.initXML();
	}
	public void read(String xmlPath, String treeTagName) {
		this.xmlPath = xmlPath;
		this.treeTagName = treeTagName;
		this.initSpecificXML();
	}
	private void initXML() {
		this.reader.setXmlPath(this.xmlPath);
		this.writer.setXmlPath(this.xmlPath);
		this.reader.readXML();
		this.document = this.reader.getDocument();
		this.writer.setDocument(this.document);
	}
	private void initSpecificXML() {
		this.reader.setXmlPath(this.xmlPath);
		this.reader.setTreeTagName(this.treeTagName);
		this.writer.setXmlPath(this.xmlPath);
		this.reader.readSpecificXML();
		this.writer.setDocument(this.document);
	}
	public JSONObject getJSON(){
		return this.reader.getJson();
	}
	public void put(String tagName, String newText, String attr, String attrValue){
		this.writer.put(tagName, newText, attr, attrValue);
	}
	public void put(String tagName, String newText){
		this.writer.put(tagName, newText);
	}
	public void save() {
		this.writer.save();
	}
}

