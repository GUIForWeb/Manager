package manager.xmls;

public class XMLTester {

	public static void main(String[] args) {
		String xmlPath = "/opt/WebGUIManager/settings.xml";
		XMLManager xmlManager = new XMLManager(xmlPath);
		xmlManager.read("directory");
		System.out.println(xmlManager.getJSON());
		xmlManager.put("directory", "yo2", "name", "storage");
		xmlManager.save();
	}
}
