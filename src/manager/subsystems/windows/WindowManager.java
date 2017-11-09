package manager.subsystems.windows;

import javax.swing.JFrame;

public class WindowManager {
	public static JFrame webGUIView;
	private static WindowManager instance;
	public static WindowManager getInstance() {
		if(null == instance) {
			instance = new WindowManager();
		}
		instance.init();
		return instance;
	}
	public WindowManager() {
	}
	public void init(){

	}
}
