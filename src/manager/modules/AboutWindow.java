package manager.modules;

import manager.views.AboutView;

public class AboutWindow{
	public void main() {
		try {
			AboutView dialog = new AboutView();
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
