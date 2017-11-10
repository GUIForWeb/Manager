package manager.subsystems.windows;

import javax.swing.JFrame;

public class WindowProp {
	public static JFrame webGUIView;
	private static WindowProp instance;
	public static WindowProp newInstance() {
		if(null == instance) {
			instance = new WindowProp();
		}
		return instance;
	}
	public WindowProp() {
	}
}
