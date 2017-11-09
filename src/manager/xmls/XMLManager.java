package manager.xmls;

import org.json.JSONObject;
import org.w3c.dom.Document;

public class XMLManager {
	private Document document;
	private XMLReader reader;
	private XMLWriter writer;
	private String xmlPath;
	private String treeTagName;
	public XMLManager(String xmlPath) {
		this.xmlPath = xmlPath;
		this.reader = new XMLReader(this.xmlPath);
		this.writer = new XMLWriter(this.xmlPath);
		this.initXML();
	}
	public XMLManager(String xmlPath, String treeTagName) {
		this.xmlPath = xmlPath;
		this.treeTagName = treeTagName;
		this.reader = new XMLReader(this.xmlPath);
		this.writer = new XMLWriter(this.xmlPath);
		this.initXML();
	}
	public void read() {
		this.reader.readXML();
	}
	public void read(String treeTagName) {
		this.treeTagName = treeTagName;
		this.initXML();
		this.reader.readXML();
	}
	private void initXML() {
		this.document = this.reader.getDocument();
		this.reader.setTreeTagName(this.treeTagName);
		this.writer.setXmlPath(this.xmlPath);
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

