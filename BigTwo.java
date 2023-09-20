import java.util.ArrayList;

import javax.swing.JOptionPane;
/**
 * The BigTwo class implements the CardGame interface and is used to model a Big Two card game. 
 * It has private instance variables for
 *  storing the number of players, a deck of cards, a list of players, a list of hands played on the table, 
 *  an index of the current player, and a user interface.
 * @author jeffsin
 *
 */
public class BigTwo implements CardGame{
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int currentPlayerIdx;
	private BigTwoGUI ui;
	private BigTwoClient client;
	/**
	 * a constructor for creating a Big Two Card game
	 */
	public BigTwo() {
		numOfPlayers = 4;
		this.playerList = new ArrayList<CardGamePlayer>(); //create playerList of the game
		this.handsOnTable = new ArrayList<Hand>(); // create hands played on table list
		CardGamePlayer player0 = new CardGamePlayer();
		CardGamePlayer player1 = new CardGamePlayer();
		CardGamePlayer player2 = new CardGamePlayer();
		CardGamePlayer player3 = new CardGamePlayer();
		
		playerList.add(player0);
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		
		ui = new BigTwoGUI(this);
		client = new BigTwoClient(this, ui);
	}
	/**
	 * a method for getting the number of players
	 * @return number of players
	 */
	public int getNumOfPlayers() {
		return playerList.size();
	}
	/**
	 * a method for retrieving the deck of cards being used
	 * @return deck of cards used
	 */
	public Deck getDeck() {
		return this.deck;
	}
	/**
	 * a method for retrieving the list of players
	 * @return list of players
	 */
	public ArrayList<CardGamePlayer> getPlayerList(){
		return playerList;
	}
	/**
	 * a method for retrieving the list of hands played on the table
	 * @return list of hands played on table
	 */
	public ArrayList<Hand> getHandsOnTable(){
		return this.handsOnTable;
	}
	/**
	 * a method for retrieving the index of the current player
	 * @return index of the current player
	 */
	public int getCurrentPlayerIdx() {
		return currentPlayerIdx;
	}
	/**
	 * a method to send message from client to server
	 * @param msg message sent from the client
	 */
	public void sendmsg(CardGameMessage msg) {
		client.sendMessage(msg);
	}
	/**
	 * a method to check whether the client connect to the server
	 * @return whether the client connect to the server
	 */
	public boolean checkconnect() {
		if (client.checkcon()) {
			return true;
		}
		return false;
	}
	/**
	 * a method for the client conenct to the server
	 */
	public void connectt() {
		client.connect();
	}
	/**
	 * a method for starting/restarting the game with a shuffled deck of cards
	 * (i) remove all the cards from the players as well as from the table
	 * (ii) distribute the cards to the players
	 * (iii) identify the player who holds the Three of Diamond
	 * (iv) set both the currentPlayerIdx of the BigTwo object 
	 * and the activePlayer of the BigTwoUI object to the index of the player who holds the Three of Diamonds
	 * (v) call the repaint() method of the BigTwoUI object to show the cards on the table
	 * (vi) call the promptActivePlayer() method of the BigTwoUI object to prompt user to select cards and make his/her move.
	 * @param deck shuffled deck used for the game
	 */
	public void start(Deck deck) {
		for (int i = 0; i < this.getNumOfPlayers(); i++) {
			playerList.get(i).removeAllCards();
		}
		handsOnTable.clear();
		
		
		for (int i = 0; i < this.getNumOfPlayers(); i++) {
			for (int j = 0; j < 13; j++) {
				playerList.get(i).addCard(deck.getCard(i*13+j));
			}
			playerList.get(i).sortCardsInHand();
		}
		ui.setActivePlayer(client.getPlayerID());
		BigTwoCard diamond3= new BigTwoCard(0, 2);
		for (int i = 0; i < this.getNumOfPlayers(); i++) {
			if (playerList.get(i).getCardsInHand().contains(diamond3)){
				currentPlayerIdx = i;
				//ui.setActivePlayer(i);
				break;
			}
		}
		
		ui.reset();
		ui.repaint();
		ui.promptActivePlayer();
		
	}
	/**
	 * a method for making a move by a player with the specified index
	 * using the the cards specified by the list of indices
	 * This method will be called from BIgUI after active player have selected
	 * cards for his move
	 * @param playerIdx index of current player
	 * @param cardIdx cards specified by list of indices
	 */
	public void makeMove(int playerIdx, int[] cardIdx) {
		//checkMove(playerIdx, cardIdx);
		CardGameMessage msgg = new CardGameMessage(6, -1, cardIdx);
		client.sendMessage(msgg);
	}
	/**
	 * a method for checking a move made by a player
	 * @param playerIdx index of current player
	 * @param cardIdx cards specified by list of indices
	 */
	public void checkMove(int playerIdx, int[] cardIdx) {
		boolean legal = false;
		boolean pass = false;
		CardGamePlayer playerr = playerList.get(playerIdx);
		CardList cardplay = playerr.play(cardIdx);
			//Hand lasthand = handsOnTable.get(handsOnTable.size()-1);
		Hand cardout = composeHand(playerr, cardplay);
		if (handsOnTable.isEmpty()) {
			BigTwoCard diamond3 = new BigTwoCard(0,2);
			if (cardout != null && cardout.contains(diamond3)) {
				handsOnTable.add(cardout);
				legal = true;
			}
			else {
				legal = false;
			}
		}
		else if (cardIdx == null) {
			pass = true;
			Hand lasthandd = handsOnTable.get(handsOnTable.size()-1);
			if (lasthandd.getPlayer() == playerr) {
				legal = false;
			}
			else {
				legal = true;
			}
		}
		else if (cardout == null) {
			legal = false;
		}
		else if (handsOnTable.get(handsOnTable.size()-1).getPlayer() == playerr) {
			legal = true;
		}
		else {
			Hand lasthand = handsOnTable.get(handsOnTable.size()-1);
			if (cardout.beats(lasthand)) {
				legal = true;
			}
			else {
				legal = false;
			}
			
		}
		if (legal == true) {
			if (pass == true) {
				ui.printMsg("{Pass}");
				//System.out.println("{Pass}");
			}
			else {
				playerr.removeCards(cardout);
				handsOnTable.add(cardout);
				ui.printMsg("{"+cardout.getType()+"} " + cardout.toString());
				//System.out.print("{"+cardout.getType()+"} ");
				//System.out.println(cardout.toString());
			}
			//System.out.println();
			if (endOfGame()) {
				String endmsg = "";
				ui.repaint();
				//ui.printMsg("");
				//System.out.println();
				//ui.printMsg("Game ends");
				endmsg += "Game ends\n";
				//System.out.println("Game ends");
				for (int i = 0; i < playerList.size(); i++) {
					if (playerList.get(i) == playerr) {
						//ui.printMsg(playerr.getName()+" wins the game.");
						endmsg += (playerr.getName()+" wins the game.\n");
					}
					else {
						//ui.printMsg(playerList.get(i).getName()+" has "+ playerList.get(i).getNumOfCards()+ " cards in hand.");
						//System.out.println(playerList.get(i).getName()+" has "+ playerList.get(i).getNumOfCards()+ " cards in hand.");
						endmsg += playerList.get(i).getName()+" has "+ playerList.get(i).getNumOfCards()+ " cards in hand.\n";
					}
				}
				int y;
				y = JOptionPane.showConfirmDialog(null, endmsg, null, JOptionPane.OK_CANCEL_OPTION);
				if (y == JOptionPane.OK_OPTION) {
					client.sendMessage(new CardGameMessage(4, -1, null));
				}
				
				ui.disable();
				return;
			}
			currentPlayerIdx = (playerIdx+1)%4;
			//ui.setActivePlayer(currentPlayerIdx);
			ui.repaint();
			ui.promptActivePlayer();
		}
		else {
			ui.printMsg("Not a legal move");
			//System.out.println("Not a legal move!!!");
			ui.repaint();
			ui.promptActivePlayer();
		}
		ui.resetSelected();
	}
	/**
	 * a method for checking if the game ends
	 * @return whether the game ends
	 */
	public boolean endOfGame() {
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getNumOfCards() == 0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * a method for returning a valid hand from the specified list of cards of the player
	 * return null if no valid hand can be composed
	 * @param player player who play this hand
	 * @param cards  specified list of cards played by the player
	 * @return a valid hand of its own type or a null if the hand is invalid
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		if (cards == null) {
			return null;
		}
		Hand temp;
		
		temp = new Single(player, cards);
		if (temp.isValid()) {
			return temp;
		}
		temp = new Pair(player, cards);
		if (temp.isValid()) {
			return temp;
		}
		temp = new Triple(player, cards);
		if (temp.isValid()) {
			return temp;
		}
		
		temp = new StraightFlush(player, cards);
		if (temp.isValid()) {
			return temp;
		}
		temp = new Straight(player, cards);
		if (temp.isValid()) {
			return temp;
		}
		temp = new Flush(player, cards);
		if (temp.isValid()) {
			return temp;
		}
		temp = new FullHouse(player, cards);
		if (temp.isValid()) {
			return temp;
		}
		temp = new Quad(player, cards);
		if (temp.isValid()) {
			return temp;
		}
		return null;

		
	}
	
	public static void main(String[] args) {
		BigTwo game = new BigTwo();
		
		BigTwoDeck deckk = new BigTwoDeck();
		
		deckk.shuffle();
		
		//game.start(deckk);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
