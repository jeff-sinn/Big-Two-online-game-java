import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 * The BigTwoClient class implements the NetWorkGame Interface. It is used to model a Big Two
 * game client that is responsible for establishing a connection and communicating with the 
 * BigTwoServer 
 * 
 * @author jeff sin
 *
 */
public class BigTwoClient implements NetworkGame{
	private BigTwo game;
	
	private BigTwoGUI gui;
	
	private Socket sock;
	
	private ObjectOutputStream oos;
	
	private int playerID;
	
	private String playerName;
	
	private String serverIP;
	
	private int serverPort;
	
	/**
	 * a constructor for creating a Big Two client
	 * @param game reference to BigTwo object
	 * @param gui reference to BigTwoGUI object
	 */
	public BigTwoClient(BigTwo game, BigTwoGUI gui) {
		this.game = game;
		this.gui = gui;
		String tempname = JOptionPane.showInputDialog("Enter your name: ");
		if (tempname != null) {
			this.setServerIP("127.0.0.1");
			this.setServerPort(2396);
			this.setPlayerName(tempname);
			this.connect();
			//CardGameMessage msgg = new CardGameMessage(1, -1, this.getPlayerName());
			//sendMessage(msgg);
			
		}
		else {
			gui.printMsg("Enter an available name");
			gui.disable();
			gui.disablechat();
		}
		
		
	}
	/**
	 * a method for getting the playerID of the local player
	 * @return playerID of the local player
	 */
	public int getPlayerID(){
		return playerID;
	}
	/**
	 * a method for setting the playerID of the local player
	 * @param playerID playerID to be set
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	/**
	 * a method for getting the name of the local player
	 * @return the name of the local player
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * a method for setting the name of the local player
	 * @param playerName player name to be set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	/**
	 * a method for getting the ServerIP address of the game server
	 * @return IP address of the server
	 */
	public String getServerIP(){
		return serverIP;
	}
	/**
	 * a method for setting the IP address of the game server
	 * @param serverIP serverIP to be set
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	/**
	 * a method for getting the TCP port of the game server
	 * @return TCP port of the game server
	 */
	public int getServerPort() {
		return serverPort;
	}
	/**
	 * a method for setting the TCP port of the game server
	 * @param serverPort serverPort to be set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	/**
	 * check if the client has connected to the server
	 * @return whether th client has conencted to the server
	 */
	public boolean checkcon() {
		if (!sock.isClosed()) {
			return true;
		}
		return false;
	}
	/**
	 * a method for making a socket connection with the game server
	 */
	public void connect() {
		try {
			sock = new Socket(getServerIP(), getServerPort());
			oos = new ObjectOutputStream(sock.getOutputStream());
			Runnable threadjob = new ServerHandler();
			Thread mythread = new Thread(threadjob);
			mythread.start();
			CardGameMessage msgg = new CardGameMessage(1, -1, this.getPlayerName());
			sendMessage(msgg);
			gui.disable();
			//gui.repaint();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	/**
	 * a method for parsing the messages received from the game server
	 */
	public void parseMessage(GameMessage message) {
		if (message.getType() == 0) {
			this.setPlayerID(message.getPlayerID());
			String[] namelis = (String[]) message.getData();
			for (int i = 0; i < namelis.length; i++) {
				if (namelis[i] != null) {
					game.getPlayerList().get(i).setName(namelis[i]);
				}
			}
			gui.repaint();
			
		}
		else if (message.getType() == 1) {
			int index = message.getPlayerID();
			String name = (String) message.getData();
			game.getPlayerList().get(index).setName(name);
			if (this.getPlayerID() == message.getPlayerID()) {
				CardGameMessage messa = new CardGameMessage(4, -1, null);
				sendMessage(messa);
			}
			gui.repaint();
		}
		
		else if (message.getType() == 2) {
			gui.printMsg("The server is full and cannot join the game");
			gui.disable();
			gui.disablechat();
			try {
				sock.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		else if (message.getType() == 3) {
			int index = message.getPlayerID();
			game.getPlayerList().get(index).setName("");
			//gui.repaint();
			if (!game.endOfGame()) {
				gui.disable();
				CardGameMessage msgg = new CardGameMessage(4, -1, null);
				sendMessage(msgg);
			}
		}
		
		else if (message.getType() == 4) {
			int index = message.getPlayerID();
			gui.printMsg(game.getPlayerList().get(index).getName()+" is ready");
			//gui.repaint();
		}
		
		else if (message.getType() == 5) {
			BigTwoDeck deckk = (BigTwoDeck) message.getData();
			game.start(deckk);
			//gui.repaint();
		}
		
		else if (message.getType() == 6) {
			int index = message.getPlayerID();
			int [] cardse = (int[]) message.getData();
			game.checkMove(index, cardse);
		}
		
		else if (message.getType() == 7) {
			int index = message.getPlayerID();
			String data = (String) message.getData();
			gui.printchat(data);
		}
	}
	/**
	 * a method for sending the specified message to the game server
	 */
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * an inner class that implemetns the Runnable interface. It would implement the run method of 
	 * the interface and create a thread for its job
	 * @author jeffsin
	 *
	 */
	public class ServerHandler implements Runnable{
		ObjectInputStream inputt;
		@Override
		public void run() {
			try {
				inputt = new ObjectInputStream(sock.getInputStream());
				CardGameMessage temp;
				while (sock.isClosed() != true) {
					temp = (CardGameMessage) inputt.readObject();
					if (temp != null) {
						parseMessage(temp);
					}
				}
				inputt.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
}

