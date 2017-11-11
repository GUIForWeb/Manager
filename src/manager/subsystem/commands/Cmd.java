package manager.subsystem.commands;

import java.util.List;

public class Cmd {
	public static List<String> start;
	public static List<String> stop;
	private static Cmd instance;
	public static Cmd newInstance() {
		if(null == instance) {
			instance = new Cmd();
		}
		return instance;
	}
}