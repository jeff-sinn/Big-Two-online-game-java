import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class that is used to build a HUI for the big Two card game and handle all user actions.
 * @author jeffsin
 *
 */
public class BigTwoGUI implements CardGameUI{
	private BigTwo game;
	
	private boolean[] selected;
	
	private int activePlayer;
	
	private JFrame frame;
	
	private JPanel bigTwoPanel;
	
	private JButton playButton;
	
	private JButton passButton;
	
	private JTextArea msgArea;
	
	private JTextArea chatArea;
	
	private JTextField chatInput;
	
	private Image[] imagearr;
	
	private Image[][] cardarr;
	
	private BigTwoClient client;
	
	/**
	 * a constructor for creating a BigTwoGUI
	 * @param game reference to a BigTwo card game associates with this GUI
	 */
	
	BigTwoGUI(BigTwo game){
		this.game = game;
		this.selected = new boolean[13];
		Image image1 = new ImageIcon("batman.jpg").getImage();
		Image image2 = new ImageIcon("captain.jpg").getImage();
		Image image3 = new ImageIcon("ironman.jpg").getImage();
		Image image4 = new ImageIcon("spiderman.jpg").getImage();
		
		imagearr = new Image[4];
		imagearr[0] = image1;
		imagearr[1] = image2;
		imagearr[2] = image3;
		imagearr[3] = image4;
		
		cardarr = new Image[4][13];
		
		String filename = "cards/";
		Image temp;
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				if(j == 0) {
					filename += "a";
				}
				else if (j == 9) {
					filename += "t";
				}
				else if (j == 10) {
					filename += "j";
				}
				else if (j == 11) {
					filename += "q";
				}
				else if (j == 12) {
					filename += "k";
				}
				else {
					filename += (j+1);
				}
				
				if (i == 0) {
					filename += "d";
				}
				else if(i == 1) {
					filename += "c";
				}
				else if(i == 2) {
					filename += "h";
				}
				else if (i == 3){
					filename += "s";
				}
				filename += ".gif";
				temp = new ImageIcon(filename).getImage();
				
				cardarr[i][j] = temp;
				filename = "cards/";
			}
		}
		
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 700);
		frame.setLayout(new BorderLayout());
		
		
		JMenuBar menubar = new JMenuBar();
		JMenu gameopt = new JMenu("Game");
		JMenuItem connect = new JMenuItem("Connect");
		connect.addActionListener(new ConnectMenuItemListener());
		JMenuItem quitt = new JMenuItem("Quit");
		quitt.addActionListener(new QuitMenuItemListener());
		gameopt.add(connect);
		gameopt.add(quitt);
		menubar.add(gameopt);
		frame.setJMenuBar(menubar);
		//frame.add(menubar, BorderLayout.SOUTH);
		JPanel bottombutton = new JPanel();
		bottombutton.setLayout(new FlowLayout());
		
		playButton = new JButton("Play");
		passButton = new JButton("Pass");
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());
		
		bottombutton.add(playButton);
		bottombutton.add(passButton);
		//frame.add(bottombutton, BorderLayout.SOUTH);
		
		JPanel msgpan = new JPanel();
		msgpan.setLayout(new BoxLayout(msgpan, BoxLayout.Y_AXIS));
		msgArea = new JTextArea(19, 25);
		msgArea.setForeground(Color.black);
		msgArea.setEditable(false);
		msgArea.setBackground(Color.white);
		JScrollPane rolling = new JScrollPane(msgArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		msgpan.add(rolling);
		
		chatArea = new JTextArea(19,25);
		chatArea.setBackground(Color.white);
		chatArea.setEditable(false);
		JScrollPane roll = new JScrollPane(chatArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		msgpan.add(roll);
		
		chatInput = new JTextField();
		chatInput.addActionListener(new EnterListener());
		chatInput.setPreferredSize(new Dimension(200, 27));
		
		msgpan.add(chatInput);
		msgpan.add(bottombutton);
		
		frame.add(msgpan, BorderLayout.EAST);
		bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setPreferredSize(new Dimension(600, 500));
		bigTwoPanel.setBackground(Color.yellow);
		frame.add(bigTwoPanel, BorderLayout.WEST);
		frame.setVisible(true);
		
		
		
		
		
	}
	
	/**
	 * a method for setting the index of the active player
	 * @param activePlayer the active player of the current situation
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	/**
	 * a method for repainting the GUI
	 */
	public void repaint() {
		frame.repaint();
		
	}
	/**
	 * a method for printing the specified string to the message area of the GUI
	 * @param msg message to be sent
	 */
	public void printMsg(String msg) {
		msgArea.append(msg + "\n");
	}
	/**
	 * a method for clearing the message area of GUI
	 */
	public void clearMsgArea() {
		msgArea.setText("");
	}
	/**
	 * reset the selected list
	 */
	public void resetSelected() {
		for (int j = 0; j < selected.length; j++) {
			selected[j] = false;
		}
		this.repaint();
	}
	/**
	 * a method for resetting the GUI
	 */
	public void reset() {
		resetSelected();
		clearMsgArea();
		enable();
	}
	
	/**
	 * a method for enabling user interaction with the GUI
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		chatInput.setEnabled(true);
		bigTwoPanel.setEnabled(true);
	}
	/**
	 * a method for disabling user interactions with the GUI
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		//chatInput.setEnabled(false);
		bigTwoPanel.setEnabled(false);
	}
	/**
	 * a method for prompting the active player to select cards
	 */
	public void promptActivePlayer() {
		if (activePlayer == game.getCurrentPlayerIdx()) {
			this.enable();
		}
		else {
			this.disable();
		}
		printMsg(game.getPlayerList().get(game.getCurrentPlayerIdx()).getName() + "'s turn: ");
		//int[] cardIdx = getSelected();
		//resetSelected();
		//game.makeMove(activePlayer, cardIdx);
	}
	/**
	 * a method to print the message on the chat
	 * @param msg message to be printed on the chat
	 */
	public void printchat(String msg) {
		chatArea.append(msg+"\n");
	}
	/**
	 * a method to disable the chatinput field
	 */
	public void disablechat() {
		chatInput.setEnabled(false);
	}
	private int[] getSelected() {
		int[] cardIdx = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count++;
			}
		}

		if (count != 0) {
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					cardIdx[count] = j;
					count++;
				}
			}
		}
		return cardIdx;
	}
	/**
	 * an inner class that implements the ActionListener interface for the PlayButton
	 * @author jeffsin
	 *
	 */
	class PlayButtonListener implements ActionListener{
		/**
		 * method to react to the event when PlayButton is pressed
		 * @param e event of mouse clicking of Playbutton
		 */
		public void actionPerformed(ActionEvent e) {
			if (getSelected() != null) {
				game.makeMove(activePlayer, getSelected());
			}
		}
	}
	/**
	 * an inner class that implements the ActionListener for the pass button
	 * @author jeffsin
	 *
	 */
	class PassButtonListener implements ActionListener{
		/**
		 * method to react to the event when PassButton is pressed
		 * @param e event of mouse clicking of PassButton
		 */
		public void actionPerformed(ActionEvent e) {
			game.makeMove(activePlayer, null);
		}
	}
	/**
	 * an inner class that extends the JPanel class and implements the MouseListener interface
	 * @author jeffsin
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		/**
		 * constructor for creating BigTwoPanel for the card table design
		 */
		public BigTwoPanel() {
			this.addMouseListener(this);
		}
		/**
		 * a method to draw the card game table
		 * @param g the graphics to draw the gametable
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//bigTwoPanel.revalidate(); 
			Image back = new ImageIcon("cards/b.gif").getImage();
			this.setBackground(Color.GREEN);
			String textt;
			
			BigTwoCard cardtem;
			int suitem;
			int ranktem;
			int cardsep = this.getWidth()/7/3;
			int up = 10;
			String handplayer;
			Hand lasthandd;
			for (int i = 0; i < game.getNumOfPlayers(); i++) {
				textt = game.getPlayerList().get(i).getName();
				g.drawString(textt, this.getWidth()/80 , this.getHeight()/60 + i * this.getHeight()/5);
				g.drawImage(imagearr[i], this.getWidth()/40, this.getHeight()/23 + i * this.getHeight()/5, this.getWidth()/9, this.getHeight()/11, this);
				//g.drawImage(imagearr[i], this.getWidth()/40, this.getHeight()/23 + i * this.getHeight()/5, this.getWidth()/20, this.getHeight()/20, this);
				if (activePlayer == i) {
					for (int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); j++) {
						cardtem = (BigTwoCard) game.getPlayerList().get(i).getCardsInHand().getCard(j);
						suitem = cardtem.getSuit();
						ranktem = cardtem.getRank();
						if (selected[j]) {
							g.drawImage(cardarr[suitem][ranktem], this.getWidth()/6 + cardsep * j , this.getHeight()/31 + i *this.getHeight()/5 -up, this.getWidth()/7, this.getHeight()/6, this);
						}
						else {
							g.drawImage(cardarr[suitem][ranktem], this.getWidth()/6 + cardsep * j , this.getHeight()/31 + i *this.getHeight()/5 , this.getWidth()/7, this.getHeight()/6, this);
						}
					}
				}
				else {
					for (int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); j++) {
						g.drawImage(back, this.getWidth()/6 + cardsep * j , this.getHeight()/31 + i *this.getHeight()/5, this.getWidth()/7, this.getHeight()/6, this);
					}
				}
				//textt = "Player ";
				
			
			}
			if (!game.getHandsOnTable().isEmpty()) {
				lasthandd = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
				handplayer = lasthandd.getPlayer().getName();
				textt = "Played by " + handplayer;
				g.drawString(textt, this.getWidth()/80, this.getHeight()/60 + 4 * this.getHeight()/5);
				for (int i = 0; i < lasthandd.size(); i++) {
					cardtem = (BigTwoCard) lasthandd.getCard(i);
					suitem = cardtem.getSuit();
					ranktem = cardtem.getRank();
					g.drawImage(cardarr[suitem][ranktem], this.getWidth()/40 + cardsep * i , this.getHeight()/31 + 4 *this.getHeight()/5, this.getWidth()/7, this.getHeight()/6, this);
				}
			}
			
			
		}
		/**
		 * handle the event when the card is clicked and selected
		 * @param e event of mouse clicking
		 */
		public void mouseReleased(MouseEvent e) {
			int minx = this.getWidth()/6;
			int miny = this.getHeight() / 31 + activePlayer * this.getHeight()/5;
			int maxy = this.getHeight()/31 + this.getHeight()/6 + activePlayer * this.getHeight()/5;
			int cardsep = this.getWidth()/7/3;
			int up = 10;
			
			int cardnum = game.getPlayerList().get(activePlayer).getNumOfCards();
			for (int j = 0; j < game.getPlayerList().get(activePlayer).getNumOfCards(); j++){
				if (j == cardnum - 1) {
					if (selected[j]) {
						if (e.getX()>= minx + cardsep * j && e.getX() <= minx + cardsep * j + this.getWidth()/7) {
							if (e.getY() >= miny - up&& e.getY() <= maxy - up) {
								selected[j] = false;
								break;
							}
						}
					}
					else {
						if (e.getX()>= minx + cardsep * j && e.getX() <= minx + cardsep * j + this.getWidth()/7) {
							if (e.getY() >= miny && e.getY() <= maxy) {
								selected[j] = true;
								break;
							}
						}
					}
				}
				else {
					if (selected[j]) {
						if (e.getX()>= minx + cardsep * j && e.getX() <= minx + cardsep * j + cardsep) {
							if (e.getY() >= miny - up&& e.getY() <= maxy - up) {
								selected[j] = false;
								break;
							}
						}
					}
					else {
						if (e.getX()>= minx + cardsep * j && e.getX() <= minx + cardsep * j + cardsep) {
							if (e.getY() >= miny && e.getY() <= maxy) {
								selected[j] = true;
								break;
							}
						}
					}
				}
			}
			
			this.repaint();
			
			/*
			if (e.getY() >= miny && e.getY() <= maxy) {
				for (int i = 0; i < cardnum; i++) {
					if (i == cardnum -1) {
						if (e.getX() >= this.getWidth()/6 + cardsep * i && e.getX() <= this.getWidth()/6 + cardsep * i + this.getWidth()/7) {
							selected[i] = !selected[i];
							this.repaint();
						}
					}
					
					else {
						if (e.getX() > this.getWidth()/6 + cardsep * i && e.getX() < this.getWidth()/6 + cardsep * i + cardsep) {
							selected[i] = !selected[i];
							this.repaint();
							
						}
						
					}
				}
			}
			*/
	
		}
		
		/**
		 * Override the method
		 */
		public void mousePressed(MouseEvent e) {
			
		}
		/**
		 * Override the method
		 */
		public void mouseClicked(MouseEvent e) {
			
		}
		/**
		 * Override the method
		 */
		public void mouseEntered(MouseEvent e) {
			
		}
		/**
		 * Override the method
		 */
		public void mouseExited(MouseEvent e) {
			
		}
		
	}
	
	/**
	 * inner class that is used to handle the chat text input from user
	 * @author jeffsin
	 *
	 */
	class EnterListener implements ActionListener{
		/**
		 * handle the event of pressing enter on the keyboard to send message
		 * @param e event of pressing Enter button
		 */
		public void actionPerformed(ActionEvent e) {
			String words = chatInput.getText();
			if (words != null) {
				CardGameMessage msgg = new CardGameMessage(7, -1, words);
				game.sendmsg(msgg);
			}
			//chatArea.append("Player "+activePlayer+ ": " + words +"\n");
			chatInput.setText("");
		}
	}
		
	/**
	 * inner class that is used to handle the connect menu item from menu
	 * @author jeffsin
	 *
	 */
	class ConnectMenuItemListener implements ActionListener{
		/**
		 * handle the event of clicking the connect menu item
		 * @param event of clicking the connect menu item
		 */
		public void actionPerformed(ActionEvent e) {
			//BigTwoDeck deckk = new BigTwoDeck();
			
			//deckk.shuffle();
			//game.start(deckk);
			if (!game.checkconnect()) {
				game.connectt();
			}
		}
	}
	/**
	 * inner class that is used to help user to quit the game when clicking quit menu item
	 * @author jeffsin
	 *
	 */
	class QuitMenuItemListener implements ActionListener{
		/**
		 * handle event of clicking the quit menu item
		 * @param event of clicking the quit menu item
		 */
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
		
		
}
	
	
	
	
	
	
	

