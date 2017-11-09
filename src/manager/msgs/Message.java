package manager.msgs;

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
		
		if(!msgs.equals("")) this.display(msgs);
		return flag;
	}
	private void display(String msgs){
		JOptionPane.showMessageDialog(null, msgs);
	}
}
