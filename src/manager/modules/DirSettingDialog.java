package manager.modules;

import java.awt.EventQueue;

import manager.views.DirSettingView;

public class DirSettingDialog{
	public void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DirSettingView view = new DirSettingView();
					view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
