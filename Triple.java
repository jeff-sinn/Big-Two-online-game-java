/**
 * subclass of class Hand that is used to provide a detailed implementation of the abstract
 * method of type Triple
 * @author jeffsin
 *
 */
public class Triple extends Hand{
	
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * a method the get the type of the hand
	 * @return the type of the hand
	 */
	public String getType() {
		return "Triple";
	}
	/**
	 * check if this hand is of Type triple
	 * @return whether this hand is of type triple
	 */
	public boolean isValid() {
		if (this.size() != 3) {
			return false;
		}
		BigTwoCard card0 = (BigTwoCard) this.getCard(0);
		BigTwoCard card1 = (BigTwoCard) this.getCard(1);
		BigTwoCard card2 = (BigTwoCard) this.getCard(2);
		if (card0.rank == card1.rank && card1.rank == card2.rank) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
