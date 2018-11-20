package draughts.connection;

import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import draughts.enums.*;
import draughts.messages.*;
import draughts.windows.MainWindow;


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
			case 1:
				this.address = args.get(0);
			case 2:
				try {
					this.localPort = Integer.parseInt(args.get(1));
				}
				catch (Exception e) {
					System.out.println("Writed port: '" + args.get(1) + "' is not a number, using default port: " + localPort);
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
			e.printStackTrace();
		}
	}
	
	public void connect(MainWindow mainWindow) {
		try {
			this.mainWindow = mainWindow;
			socket = new Socket(address, localPort);	
			
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
			connect(mainWindow);
			e.printStackTrace();
		}
	}

	public Boolean getConnected() {
		return connected;
	}

	public void setConnected(Boolean connected) {
		this.connected = connected;
	}
}
