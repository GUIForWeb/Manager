package manager.subsystem.commands;

import java.util.ArrayList;

import manager.subsystems.paths.Path;

public class CmdManager {
	private String osName;
	public CmdManager() {
		this.osName = System.getProperty("os.name");
		Cmd.start = new ArrayList<String>();
		Cmd.stop = new ArrayList<String>();
	}
	public void init() {
		if(this.osName.contains("Windows")) 
			this.windowCmds();
		else if(this.osName.contains("Linux")) 
			this.linuxCmds();
	}
	private void windowCmds() {
		Cmd.start.add("cmd");
		Cmd.start.add("/C");
		Cmd.start.add(Path.serverExeFile+" start");
		Cmd.stop.add("cmd");
		Cmd.stop.add("/C");
		Cmd.stop.add(Path.serverExeFile+" stop 1");
	}
	private void linuxCmds() {
		Cmd.start.add("sh");
		Cmd.start.add("-c");
		Cmd.start.add(Path.serverExeFile+" start");
		Cmd.stop.add("sh");
		Cmd.stop.add("-c");
		Cmd.stop.add(Path.serverExeFile+" stop 1");
	}
}

