package manager.msgs;

import java.io.File;

import javax.swing.JOptionPane;

import manager.subsystems.paths.Path;

public class Message {
	public Message(){
	}
	public boolean start() {
		boolean flag = false;
		String msgs = "";
		if(Path.serverDir == null || Path.serverDir.equals("")) {
			msgs += "You need to set a server directory."; 
			flag = true;
		}
		if(Path.sqliteDir == null || Path.sqliteDir.equals("")) {
			if(!msgs.equals("")) msgs += System.getProperty("line.separator");
			msgs += "You need to set a sqlite directory.";
			flag = true;
		}
		if(Path.storageDir == null || Path.storageDir.equals("")) {
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
		if(path.equals(Path.root)) { 
			msgs += "Root is not accepted for a directory path!"; 
			flag = true;
		}
		if(flag) this.display(msgs);
		return flag;
	}
	public boolean checkFile() {
		boolean flag = false;
		String msgs = "";
		if(!new File(Path.serverExe).exists()) {
			msgs += "The excution file does not exist"; 
			flag = true;
		}
		if(flag) this.display(msgs);
		return flag;
	}
	public boolean checkDirs() {
		boolean flag = false;
		String msgs = "";
		if(!new File(Path.serverDir).exists()) {
			msgs += "The server directory does not exist"; 
			flag = true;
		}
		if(!new File(Path.sqliteDir).exists()) {
			if(!msgs.equals("")) msgs += System.getProperty("line.separator");
			msgs += "The sqlite directory does not exist";
			flag = true;
		}
		if(!new File(Path.storageDir).exists()) {
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
