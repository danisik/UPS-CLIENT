package draughts.connection;

import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import draughts.enums.*;
import draughts.messages.*;
import draughts.windows.MainWindow;


public class Connection {

	private Socket socket = null;
	private String address = "127.0.0.1";
	private InetAddress inetAddr = null;
	private int localPort = 10000;
	
	private OutputStreamWriter writer = null;
	
	private Reader reader = null;
	private Thread threadReader = null;
	
	private Boolean connected = false;

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
			socket = new Socket(address, localPort);	
			
			reader = new Reader(socket, mainWindow, this);
			threadReader = new Thread(reader);
			threadReader.start();
			
			writer = new OutputStreamWriter(socket.getOutputStream());
			
			inetAddr = socket.getInetAddress();
			System.out.println("Pripojuju se na : " + inetAddr.getHostAddress() + " se jmenem : " + inetAddr.getHostName());
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
		catch (Exception e) {
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
