package manager.views;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.border.EmptyBorder;

import manager.subsystems.SettingManager;
import manager.subsystems.WindowManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;

public class DirSettingView extends JDialog{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int oWidth = 450;
	private int oHeight = 350;
	private int x;
	private int y;
	public DirSettingView() {
		this.x = (int) WindowManager.webGUIView.getLocation().getX();
		this.y = (int) WindowManager.webGUIView.getLocation().getY() + 50;
		SettingManager.loadDir();
		this.initFrame();
		this.initPanel();
		this.initialize();
	}
	private void initialize() {
		contentPane.setLayout(new MigLayout("", "[132px][6px][135px][135px]", "[24px][25px][24px][25px][24px][25px][25px][25px][25px]"));
		
		JLabel lblManagerDirectory = new JLabel("Manager Directory");
		contentPane.add(lblManagerDirectory, "cell 0 0 3 1,grow");
		
		JTextField tFManagerDir = new JTextField();
		tFManagerDir.setText((String) null);
		tFManagerDir.setColumns(10);
		tFManagerDir.setText(SettingManager.managerDir);
		contentPane.add(tFManagerDir, "cell 0 1 3 1,grow");
		
		JLabel lblServerDirectory = new JLabel("Server Directory");
		contentPane.add(lblServerDirectory, "cell 0 2,grow");
		
		JTextField tFServerDir = new JTextField();
		tFServerDir.setText((String) null);
		tFServerDir.setColumns(10);
		tFServerDir.setText(SettingManager.serverDir);
		contentPane.add(tFServerDir, "cell 0 3 3 1,grow");
		
		JButton btnOpenBrowser0 = new JButton("Open Browser");
		btnOpenBrowser0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response = fc.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION){
					tFServerDir.setText(fc.getSelectedFile().toString());
				}
			}
		});
		contentPane.add(btnOpenBrowser0, "cell 3 3,grow");
		
		JLabel lblStorageDirectory = new JLabel("Storage Directory");
		contentPane.add(lblStorageDirectory, "cell 0 4,grow");
		JTextField tFStorageDir = new JTextField();
		tFStorageDir.setColumns(10);
		tFStorageDir.setText(SettingManager.storageDir);
		contentPane.add(tFStorageDir, "cell 0 5 3 1,grow");
		
		JButton btnOpenBrowser1 = new JButton("Open Browser");
		btnOpenBrowser1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response = fc.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION){
					tFStorageDir.setText(fc.getSelectedFile().toString());
				}
			}
		});
		contentPane.add(btnOpenBrowser1, "cell 3 5,grow");
		JLabel lblSqliteDirectory = new JLabel("Sqlite Directory");
		contentPane.add(lblSqliteDirectory, "cell 0 6,grow");
		
		JTextField tFSQLiteDir = new JTextField();
		tFSQLiteDir.setColumns(10);
		tFSQLiteDir.setText(SettingManager.sqliteDir);
		contentPane.add(tFSQLiteDir, "cell 0 7 3 1,grow");
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JButton btnOpenBrowser2 = new JButton("Open Browser");
		btnOpenBrowser2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response = fc.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION){
					tFSQLiteDir.setText(fc.getSelectedFile().toString());
				}
			}
		});
		contentPane.add(btnOpenBrowser2, "cell 3 7,grow");
		contentPane.add(btnClose, "cell 2 8,grow");
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingManager.serverDir = tFServerDir.getText();
				SettingManager.storageDir = tFStorageDir.getText();
				SettingManager.sqliteDir = tFSQLiteDir.getText();
				SettingManager.saveDir();
			}
		});
		contentPane.add(btnSave, "cell 3 8,grow");
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