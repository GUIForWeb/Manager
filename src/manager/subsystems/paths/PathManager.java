package manager.subsystems.paths;

public class PathManager {
	public PathManager() {
		this.init();
	}
	public void init(){
		Path.root = "/";
		Path.managerDir = this.getClass().getClassLoader().getResource(".").getPath();
		Path.onImgFile = Path.managerDir+"on.png";
		Path.offImgFile = Path.managerDir+"off.png";
		Path.dtdFile = Path.managerDir + "settings.dtd";
		Path.xmlFile = Path.managerDir + "settings.xml";
		String osName = System.getProperty("os.name");
		if(osName.contains("Linux")) {
			Path.serverXMLFile = Path.serverDir + "webapps/WebGUI/WEB-INF/settings.xml";
			Path.serverExeFile = Path.serverDir +"bin/catalina.sh";
		}
		else if(osName.contains("Windows")) {
			Path.serverXMLFile = Path.serverDir + "webapps\\WebGUI\\WEB-INF\\settings.xml";
			Path.serverExeFile = Path.serverDir +"bin\\catalina.bat";
		}
		System.out.println(this.toString());
	}
	public String toString(){
		String tmpStr = "";
		tmpStr += "Manager Dir: " + Path.managerDir + System.getProperty("line.separator");
		tmpStr += "On  Img: " + Path.onImgFile + System.getProperty("line.separator");
		tmpStr += "Off Img: " + Path.offImgFile + System.getProperty("line.separator");
		tmpStr += "Dtd: " + Path.dtdFile + System.getProperty("line.separator");
		tmpStr += "XML: " + Path.xmlFile + System.getProperty("line.separator");
		tmpStr += "Server XML: " + Path.serverXMLFile + System.getProperty("line.separator");
		tmpStr += "Server Exe: " + Path.serverExeFile;
		return tmpStr;
	}
}
