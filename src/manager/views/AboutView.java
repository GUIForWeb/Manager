package manager.views;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import manager.subsystems.windows.WindowManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutView extends JWindow {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private int oWidth = 300;
	private int oHeight = 150;
	private int x;
	private int y;
	public AboutView() {
		this.x = (int) WindowManager.webGUIView.getLocation().getX() + (((int)WindowManager.webGUIView.getSize().getWidth() - this.oWidth)/2);
		this.y = (int) WindowManager.webGUIView.getLocation().getY() + (((int)WindowManager.webGUIView.getSize().getHeight() - this.oHeight)/2);
		this.initWindow();
		this.initPanel();
	}
	private void initWindow() {
		setBounds(this.x, this.y, this.oWidth, this.oHeight);
	}
	private void initPanel() {
		
		JLabel lblWebguiV = new JLabel("WebGUI v0.1");
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, this.oWidth, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, this.oHeight, GroupLayout.PREFERRED_SIZE)
		);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JLabel lblHttphugonga = new JLabel("http://hugon.ga/");
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(225, Short.MAX_VALUE)
					.addComponent(btnOk)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(95)
					.addComponent(lblWebguiV)
					.addContainerGap(107, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addContainerGap(83, Short.MAX_VALUE)
					.addComponent(lblHttphugonga, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
					.addGap(78))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(26)
					.addComponent(lblWebguiV)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblHttphugonga)
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addComponent(btnOk)
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		contentPanel.setBorder(
	            BorderFactory.createTitledBorder("About WebGUI"));
		getContentPane().setLayout(groupLayout);
	}
}
