package draughts.connection;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import draughts.messages.*;
import draughts.windows.MainWindow;
import javafx.application.Platform;


public class Connection {

	private Socket socket = null;
	private String address = "127.0.0.1";
	private InetAddress inetAddr = null;
	private int localPort = 10000;
	private MainWindow mainWindow = null;
	private OutputStreamWriter writer = null;
	
	private Reader reader = null;
	private Thread threadReader = null;
	
	private Boolean connected = false;

	public Connection(List<String> args) {
		switch(args.size()) {
			case 4:
				if (args.get(2).equals("-port") || args.get(2).equals("-p")) {
					try {
						this.localPort = Integer.parseInt(args.get(3));
					}
					catch (Exception e) {
						System.out.println("Writed port: '" + args.get(3) + "' is not a number, using default port: " + localPort);
					}
				}
				else {
					System.out.println("Third parameter is not -port or -p, using default port: " + localPort);
				}
			case 2:
				if (args.get(0).equals("-address") || args.get(0).equals("-a")) {
					try {
						if (checkIPv4(args.get(1))) {
							this.address = args.get(1);
						}
						else {
							System.out.println("Writed address: '" + args.get(1) + "' is not a valid IPv4 address, using default address: " + this.address);
						}
					}
					catch (Exception e) {
						System.out.println("Writed address: '" + args.get(1) + "' is not a valid IPv4 address, using default address: " + this.address);
					}
				}
				else {
					System.out.println("First parameter is not -address or -a, using default address: " + this.address);
				}
			default:
				break;
		}		
	}
	
	public void closeConnection() {
		try {
			reader.stop();
			writer.close();
			socket.close();
		} 
		catch (Exception e) {
			mainWindow.quit();
		}
	}
	
	public void connect(MainWindow mainWindow) {
		try {
			this.mainWindow = mainWindow;
			
			socket = new Socket();
			
			try {
				InetSocketAddress isa = new InetSocketAddress(address, localPort);
				socket.connect(isa, 2 * 1000); 
			}
			catch (Exception e) {
				System.out.println(e);
				connected = false;
				return;
			}
			
			reader = new Reader(socket, mainWindow, this);
			threadReader = new Thread(reader);
			threadReader.start();
			
			writer = new OutputStreamWriter(socket.getOutputStream());
			
			inetAddr = socket.getInetAddress();
			System.out.println("Connection to address: " + inetAddr.getHostAddress() + " with name: " + inetAddr.getHostName() + " and port: " + localPort);
			connected = true;
		}
		catch (Exception e) {			
			if (threadReader != null) threadReader.interrupt();
			connected = false;
		}
	}
	
	public void write(Message message) {
		try {
			writer.write(message.toString());
			writer.flush();
			System.out.println("Writed message: " + message.toString());
		} 
		catch (NullPointerException e) {
			System.out.println("No message send");
		}
		catch (Exception e) {
			System.out.println("Connection lost");
			Platform.runLater(() -> {
				mainWindow.quit();
			});
		}
	}

	public Boolean getConnected() {
		return connected;
	}

	public void setConnected(Boolean connected) {
		this.connected = connected;
	}
	
	public static final boolean checkIPv4(final String ip) {
	    boolean isIPv4;
	    try {
	    final InetAddress inet = InetAddress.getByName(ip);
	    isIPv4 = inet.getHostAddress().equals(ip)
	            && inet instanceof Inet4Address;
	    } catch (final UnknownHostException e) {
	    isIPv4 = false;
	    }
	    return isIPv4;
	}
}
