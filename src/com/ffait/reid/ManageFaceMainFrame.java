package com.ffait.reid;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import com.ffait.manager.SerialPortManager;
import com.ffait.util.BlackImg;
import com.ffait.util.ByteUtils;
import com.ffait.util.DownloadFromUrl;
import com.ffait.util.NewLineString;
import com.ffait.util.ParameterOperate;
import com.ffait.util.ShowUtils;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;



public class ManageFaceMainFrame {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private static SerialPort mSerialport;
	static FaceService fs=new FaceService();
	private JFrame frame;
	static JLabel cameralable;
	static JLabel photolable;
	static JTextArea name;
	static JTextArea idnum;
	static JTextArea sex;
	static JTextArea nation;
	static JTextArea birthday;
	static JTextArea address;
	static JTextArea org;
	static JTextArea validateTime;
	static JTextArea trainProject;
	static JLabel message;
	static int flag = 0;
	static VideoCapture camera;
	static BufferedImage showImg;
	public ManageFaceMainFrame() {
		initialize();
		
	}
	//完善版本1.0
	private void initialize() {
		frame = new JFrame("AI培训系统");
		try {
			frame.setIconImage(ImageIO.read(new File("C:\\parameter\\nicola.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setBounds(0,0, 1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		
		name = new JTextArea("姓名：");
		name.setFont(new Font("monospaced", Font.PLAIN, 40) );
		name.setEditable(false);//模仿JLabel 禁止编辑文字
		name.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
		name.setBounds(20, 40, 600, 120);
		
		
//		idnum = new JTextArea("用户名(身份证号后六位)：");
//		idnum.setFont(new Font("monospaced", Font.PLAIN, 20) );
//		idnum.setEditable(false);//模仿JLabel 禁止编辑文字
//		idnum.setBackground(new Color(238,238,238));//设置背景色和 窗体的背景色一样
//		idnum.setBounds(20, 50, 600, 40);
		
//		trainProject = new JTextArea("培训项目：");
//		trainProject.setLineWrap(true);
//		trainProject.setFont(new Font("monospaced", Font.PLAIN, 20) );
//		trainProject.setEditable(false);					//模仿JLabel 禁止编辑文字
//		trainProject.setBackground(new Color(238,238,238)); //设置背景色和 窗体的背景色一样
//		trainProject.setLineWrap(true);        				//激活自动换行功能 
//		trainProject.setWrapStyleWord(true);  				// 激活断行不断字功能
//		trainProject.setBounds(20, 90, 630, 50);		
		
		cameralable = new JLabel("");
		cameralable.setBounds(32, 768-540-30, 960, 540);

		
		photolable = new JLabel();
		photolable.setBounds(750+72, 10, 180, 180);
		
		
		//提示信息栏
		message = new JLabel("");
		message.setFont(new Font("黑体",Font.PLAIN,20));
		message.setForeground(Color.RED);
		message.setBounds(20, 160, 630, 50);
		
		frame.getContentPane().add(name);
		//frame.getContentPane().add(idnum);
		//frame.getContentPane().add(trainProject);
		frame.getContentPane().add(cameralable);		
		frame.getContentPane().add(photolable);
		
		frame.getContentPane().add(message);

		
		//1024*960版本
//		frame.setBounds(0,0, 1024, 960);
//		
//		name.setFont(new Font("monospaced", Font.PLAIN, 60) );
//		name.setBounds(20, 100, 600, 120);
//		
//		message.setBounds(20, 250, 630, 50);
//		photolable.setBounds(750, 30, 252, 252);
//		cameralable.setBounds(32, 380, 960, 540);

		
		int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getWidth()) / 2;
		int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getHeight()) / 2;
		frame.setLocation(x, 0);
	
			
	}	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageFaceMainFrame window = new ManageFaceMainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		long pretime=System.currentTimeMillis();
//		VideoCapture camera = new VideoCapture(0);
		camera = new VideoCapture(0);
		
		if (!camera.isOpened()) {
			System.out.println("Camera Error");
		} else {
			Mat frame = new Mat();
			while (flag == 0) {
				camera.read(frame);
                BufferedImage bi=fs.mat2BI(frame);
                long currenttime=System.currentTimeMillis();
                if(currenttime-pretime>10000) {
                	new Thread(new Runnable() {
						@Override
						public void run() {
							//long a=System.currentTimeMillis();
							if(true){
								if(message.getText() == null || message.getText() == "")
									message.setText("正在识别中");
									String s = fs.judgeMember(bi);  
										//返回值 !null：验证通过
									
									if(null==s) {
										message.setText("没有检测到人脸");
									}else if("null".equals(s)) {
										message.setText("没有检测到人脸");
									}else if("noFace".equals(s)) {
										message.setText("没有检测到人脸");
									}else if ("noUser".equals(s)) {
										message.setText("用户未注册！");
									}
									else {//验证成功
										System.out.println(s);
										
										int f1 = s.indexOf('_');
								        int f2 = s.indexOf('_',f1+1);
								        int f3 = s.indexOf('_',f2+1);
								        int f4 = s.indexOf('_',f3+1);
								        int f5 = s.indexOf('_',f4+1);
								        
								        String userId = s.substring(0, f1);
								        String userCode = s.substring(f1+1,f2);
								        String userName = s.substring(f2+1,f3);
								        String roleId = s.substring(f3+1,f4);
								        String photoUrl = s.substring(f4+1,f5);
								        String projects = s.substring(f5+1);
								        BufferedImage photo = DownloadFromUrl.downloadBufferedImageFromUrl(ParameterOperate.extract("mainService")+photoUrl, "jpg");
								        
								        ImageIcon  i = new ImageIcon(photo);
										i.setImage(i.getImage().getScaledInstance(180,180,Image.SCALE_DEFAULT));//设置缩放
										photolable.setIcon(i);
										name.setText("姓名："+ userName);
										//idnum.setText("身份证后六位："+userId);
										//trainProject.setText("培训项目：" + projects);
										if("2".equals(roleId)) {
											 message.setText("设备将启动!");
											
										}else {
											 message.setText("身份验证成功,你没有权限开启设备");
										}
										try {
											//验证成功 五秒后清除信息
											Thread.sleep(5000);
											if("2".equals(roleId)) {
												openSerialPort(ParameterOperate.extract("commName"));
												if(sendData()) {
													 try {
															String appname=  ParameterOperate.extract("appname");
															Runtime.getRuntime().exec(appname);
														} catch (IOException e1) {
															// TODO Auto-generated catch block
															e1.printStackTrace();
														}
												    SerialPortManager.closePort(mSerialport);
													System.exit(0);
												}
												
											}else {
												 message.setText("身份验证成功,你没有权限开启设备");
											}
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}finally{
											name.setText("姓名：");
											//idnum.setText("身份证后六位：");
											//trainProject.setText("培训项目：");
											message.setText("正在识别中");
											photolable.setIcon(null);
										}
									}
									
							}
						}
                		
                	}).start();
                	pretime=currenttime;
                }
                showImg = BlackImg.deepCopy(bi);
                
                BlackImg.drawFace(showImg);
                
                cameralable.setIcon(new ImageIcon(showImg));
                
                BlackImg.drawFace(bi);
                cameralable.setIcon(new ImageIcon(bi));
			}
		}
	}
	private static void openSerialPort(String comname) {
		int baudrate = 115200;
		// 检查串口名称是否获取正确
		if (comname == null || comname.equals("")) {
			System.out.println("没有搜索到有效串口！");
		} else {
			try {
				mSerialport = SerialPortManager.openPort(comname, baudrate);
				if (mSerialport != null) {
					System.out.println(comname + "串口已打开");
					// mSerialPortOperate.setText("关闭串口");
				}
			} catch (PortInUseException e) {
				System.out.println("串口已被占用！");
			}
		}
	}

	private static boolean sendData() {
		// 待发送数据
		if (mSerialport == null) {
			ShowUtils.warningMessage("请先打开串口！");
			return false;
		}
		SerialPortManager.sendToPort(mSerialport, ByteUtils.hexStr2Byte("AA010D0A"));
		return true;
	}

}