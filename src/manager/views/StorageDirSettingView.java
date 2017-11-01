package manager.views;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import manager.subsystems.SettingManager;
import manager.subsystems.WindowManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StorageDirSettingView extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String storageDir;
	private int oWidth = 450;
	private int oHeight = 150;
	private int x;
	private int y;
	public StorageDirSettingView() {
		this.x = (int) WindowManager.webGUIView.getLocation().getX();
		this.y = (int) WindowManager.webGUIView.getLocation().getY() + 50;
		this.initFrame();
		this.initPanel();
		this.initialize();
		SettingManager.loadStorageDir();
	}
	private void initialize() {
		JTextField tFieldSDir = new JTextField();
		tFieldSDir.setColumns(10);
		SettingManager.tFieldSDir = tFieldSDir;
		JButton btnCancle = new JButton("Cancel");
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingManager.storageDir = tFieldSDir.getText();
				SettingManager.saveStorageDir();
			}
		});
		
		JLabel lblStorageDirectory = new JLabel("Storage Directory");
		
		JButton btnOpenBrowser = new JButton("Open Browser");
		btnOpenBrowser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response = fc.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION){
					storageDir = fc.getSelectedFile().toString();
					tFieldSDir.setText(storageDir);
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(this.contentPane);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnCancle)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSave)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(lblStorageDirectory)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(tFieldSDir, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnOpenBrowser))))
					.addGap(37))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(lblStorageDirectory)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOpenBrowser)
						.addComponent(tFieldSDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnCancle))
					.addContainerGap())
		);
		this.contentPane.setLayout(groupLayout);
	}
	private void initFrame() {
		super.setTitle("Storage Setting");
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setBounds(this.x, this.y, this.oWidth, this.oHeight);
	}
	private void initPanel(){
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
	}
}
