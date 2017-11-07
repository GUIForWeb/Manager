package manager.xmls;

public class XMLTester {

	public static void main(String[] args) {
		String xmlPath = "/opt/WebGUIManager/settings.xml";
		/*
		XMLManager xmlManager = new XMLManager(xmlPath);
		xmlManager.put("directory", "yo2", "name", "storage");
		xmlManager.save();
		*/
		XMLReader xmlLoader = new XMLReader(xmlPath);
		System.out.println(xmlLoader.getJson());
		/*
		xmlLoader = new XMLReader(xmlPath,"settings");
		System.out.println(xmlLoader.getJson());
		XMLWriter xmlWriter = new XMLWriter(xmlPath);
		xmlWriter.put("directory", "yo", "name", "storage");
		*/
	}
}
