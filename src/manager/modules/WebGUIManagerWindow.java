package manager.modules;

import java.awt.EventQueue;

import manager.views.WebGUIManagerView;

public class WebGUIManagerWindow {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebGUIManagerView frame = new WebGUIManagerView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
