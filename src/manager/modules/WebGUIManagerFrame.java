package manager.modules;

import java.awt.EventQueue;

import manager.views.WebGUIManagerView;

public class WebGUIManagerFrame {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebGUIManagerView view = new WebGUIManagerView();
					view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
