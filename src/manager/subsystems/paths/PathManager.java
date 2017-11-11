package manager.subsystems.paths;

public class PathManager {
	public PathManager() {
		this.init();
	}
	public void init(){
		Path.root = "/";
		Path.managerDir = this.getClass().getClassLoader().getResource(".").getPath();
		Path.imgOn = Path.managerDir+"on.png";
		Path.imgOff = Path.managerDir+"off.png";
		Path.dtd = Path.managerDir + "settings.dtd";
		Path.xml = Path.managerDir + "settings.xml";
		Path.serverXML = Path.serverDir + "webapps/WebGUI/WEB-INF/settings.xml";
		Path.serverExe = Path.serverDir +"bin/catalina.sh";
		System.out.println(this.toString());
	}
	public String toString(){
		String tmpStr = "";
		tmpStr += "Manager Dir: " + Path.managerDir + System.getProperty("line.separator");
		tmpStr += "On  Img: " + Path.imgOn + System.getProperty("line.separator");
		tmpStr += "Off Img: " + Path.imgOff + System.getProperty("line.separator");
		tmpStr += "Dtd: " + Path.dtd + System.getProperty("line.separator");
		tmpStr += "XML: " + Path.xml + System.getProperty("line.separator");
		tmpStr += "Server XML: " + Path.serverXML + System.getProperty("line.separator");
		tmpStr += "Server Exe: " + Path.serverExe;
		return tmpStr;
	}
}
