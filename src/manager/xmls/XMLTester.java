package manager.xmls;

public class XMLTester {

	public static void main(String[] args) {
		String xmlPath = "/opt/WebGUIManager/setting.xml";
		XMLReader xmlLoader = new XMLReader(xmlPath,"settings");
	}
}
