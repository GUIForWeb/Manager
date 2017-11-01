package manager.modules;

import java.awt.EventQueue;

import manager.views.StorageDirSettingView;

public class StorageDirSettingDialog{
	public void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StorageDirSettingView view = new StorageDirSettingView();
					view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
