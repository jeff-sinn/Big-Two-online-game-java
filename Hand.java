/**
 * subclass of CardList class and used to model a hand of cards.
 * @author jeffsin
 *
 */
public abstract class Hand extends CardList{
	private CardGamePlayer player;
	/**
	 * A constructor for building a hand with specified player and list of cards
	 * @param player specified player who play this hand
	 * @param cards  cards combination of hand
	 */
	public Hand(CardGamePlayer player, CardList cards){
		this.player = player;
		for (int i = 0; i < cards.size(); i++) {
			this.addCard(cards.getCard(i));
		}
		this.sort();
	}
	/**
	 * a method for retrieving the player of this hand 
	 * @return player of this hand
	 */
	public CardGamePlayer getPlayer() {
		return player;
	}
	/**
	 * a method for retrieving the top card of this hand
	 * @return top card of this hand
	 */
	public Card getTopCard() {
		if(this.isEmpty() == false) {
			this.sort();
			return (this.getCard(this.size()-1));
		}
		else {
			return null;
		}
	}
	/**
	 * determine the ranking of different type of the five cards hand
	 * @return the ranking of the type of the five cards hand
	 */
	public int fivecardsrank() {
		return 0;
	}
	/**
	 * a method for checking if this hand beats a specified hand
	 * @param hand specified hand that is checked for being beaten by current hand
	 * @return whether this hand beats the specifed hand
	 */
	public boolean beats(Hand hand) {
		if (hand == null || !hand.isValid() || !this.isValid() || this.size() != hand.size()) {
			return false;
		}
		
		if(this.getType() != hand.getType()) {
			if (this.fivecardsrank() > hand.fivecardsrank()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return (this.getTopCard().compareTo(hand.getTopCard()) > 0);
		}
		
	}
	/**
	 * a method for checking if this is a valid hand
	 * @return whether this is a valid hand
	 */
	public abstract boolean isValid();
	/**
	 * a method for returning the type of this hand
	 * @return a string of the type of hand
	 */
	public abstract String getType();
}
