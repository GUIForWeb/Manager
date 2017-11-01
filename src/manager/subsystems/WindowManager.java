package manager.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.json.JSONObject;

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
