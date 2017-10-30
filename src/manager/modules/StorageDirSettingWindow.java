package manager.modules;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import manager.subsystems.SettingManager;
import manager.views.StorageDirSettingView;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class StorageDirSettingWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	public static void main() {
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
