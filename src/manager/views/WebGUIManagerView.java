package manager.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import manager.modules.AboutWindow;
import manager.modules.StorageDirSettingDialog;
import manager.subsystems.SettingManager;
import manager.subsystems.WindowManager;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class WebGUIManagerView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<String> ipList;
	public WebGUIManagerView() {
		SettingManager.getInstance();
		WindowManager.getInstance();
		WindowManager.webGUIView = this;
		this.initIPs();
		this.initFrame();
		this.initPanel();
		this.initMenu();
		this.initMain();
	}
	private void initIPs(){
		SettingManager.loadIPAddress();
		try {
			Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
			this.ipList = new ArrayList<String>();
			List<String> tmpIpList = new ArrayList<String>();
		    for (; networkInterface.hasMoreElements();)
		    {
		        NetworkInterface e = networkInterface.nextElement();
		        Enumeration<InetAddress> a = e.getInetAddresses();
		        for (; a.hasMoreElements();) {
		            InetAddress addr = a.nextElement();
		            if (addr instanceof Inet6Address) continue;
		            else if (addr.getHostAddress().equals("127.0.0.1")) continue;
		            tmpIpList.add(addr.getHostAddress());
		        }
		    }
		    if(null != SettingManager.ipAddress && !tmpIpList.contains(SettingManager.ipAddress))
		    	this.ipList.add(SettingManager.ipAddress);
		    this.ipList.addAll(tmpIpList);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}  
	}
	private void initFrame() {
		super.setTitle("WebGUI Manager");
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setBounds(100, 100, 450, 300);
	}
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmStorageDir = new JMenuItem("Storage Dir");
		mnSettings.add(mntmStorageDir);
		mntmStorageDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StorageDirSettingDialog sds = new StorageDirSettingDialog();
				sds.main();
			}
		});
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	AboutWindow aw = new AboutWindow();
		    	aw.main();
		    }
		});
		mnHelp.add(mntmAbout);
	}
	private void initPanel(){
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
	}
	private void initMain() {
		JLabel lblIpAddress = new JLabel("IP Address");
		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setModel(new DefaultComboBoxModel<Object>(this.ipList.toArray(new String[this.ipList.size()])));
		comboBox.setMaximumRowCount(this.ipList.size());
		comboBox.setEditable(true);
		
		comboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	SettingManager.ipAddress = (String) comboBox.getSelectedItem();
		    	SettingManager.saveIPAddress();
		    }
		});
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingManager.ipAddress = (String) comboBox.getSelectedItem();
		    	SettingManager.saveIPAddress();
		    	/*
				try {
					Runtime.getRuntime().exec("/tomcat/bin/catalina.bat start");
					
					Process p = 
					BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			        String line;
			        while (true) {
			            line = r.readLine();
			            if (line == null) { break; }
			            System.out.println(line);
			        }
			      
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
			}
		});
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Runtime.getRuntime().exec("/tomcat/bin/catalina.bat stop");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(50)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblIpAddress)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
							.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
							.addGap(50))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addComponent(lblIpAddress)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStart)
						.addComponent(btnStop))
					.addGap(53))
		);
		this.contentPane.setLayout(gl_contentPane);
	}
}
