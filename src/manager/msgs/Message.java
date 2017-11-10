package manager.msgs;

import java.io.File;

import javax.swing.JOptionPane;

import manager.subsystems.settings.SettingProp;

public class Message {
	public Message(){
	}
	public boolean start() {
		boolean flag = false;
		String msgs = "";
		if(SettingProp.serverDir == null || SettingProp.serverDir.equals("")) {
			msgs += "You need to set a server directory."; 
			flag = true;
		}
		if(SettingProp.sqliteDir == null || SettingProp.sqliteDir.equals("")) {
			if(!msgs.equals("")) msgs += System.getProperty("line.separator");
			msgs += "You need to set a sqlite directory.";
			flag = true;
		}
		if(SettingProp.storageDir == null || SettingProp.storageDir.equals("")) {
			if(!msgs.equals("")) msgs += System.getProperty("line.separator");
			msgs += "You need to set a storage directory.";
			flag = true;
		}
		
		if(flag) this.display(msgs);
		return flag;
	}
	public boolean checkRoot(String path) {
		boolean flag = false;
		String msgs = "";
		if(path.equals("/")) { 
			msgs += "Root is not accepted for a directory path!"; 
			flag = true;
		}
		if(flag) this.display(msgs);
		return flag;
	}
	public boolean checkFile() {
		boolean flag = false;
		String filePath = "";
		if(System.getProperty("os.name").equals("Linux"))
			filePath = SettingProp.serverDir +"/bin/catalina.sh";
		else if(System.getProperty("os.name").equals("Windows"))
			filePath = SettingProp.serverDir +"/bin/catalina.bat";
		String msgs = "";
		if(!new File(filePath).exists()) {
			msgs += "The excution file does not exist"; 
			flag = true;
		}
		if(flag) this.display(msgs);
		return flag;
	}
	public boolean checkDirs() {
		boolean flag = false;
		String msgs = "";
		if(!new File(SettingProp.serverDir).exists()) {
			msgs += "The server directory does not exist"; 
			flag = true;
		}
		if(!new File(SettingProp.sqliteDir).exists()) {
			if(!msgs.equals("")) msgs += System.getProperty("line.separator");
			msgs += "The sqlite directory does not exist";
			flag = true;
		}
		if(!new File(SettingProp.storageDir).exists()) {
			if(!msgs.equals("")) msgs += System.getProperty("line.separator");
			msgs += "The storage directory does not exist";
			flag = true;
		}
		
		if(flag) this.display(msgs);
		return flag;
	}
	private void display(String msgs){
		JOptionPane.showMessageDialog(null, msgs);
	}
}
