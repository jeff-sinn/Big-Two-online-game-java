/**
 * subclass of class Hand that is used to provide a detailed implementation of the abstract
 * method of type Pair
 * @author jeffsin
 *
 */
public class Pair extends Hand{
	
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * a method the get the type of the hand
	 * @return the type of the hand
	 */
	public String getType() {
		return "Pair";
	}
	/**
	 * check if this hand is of Type pair
	 * @return whether this hand is of type pair
	 */
	public boolean isValid() {
		if (this.size() != 2) {
			return false;
		}
		else {
			BigTwoCard card0 = (BigTwoCard) this.getCard(0);
			BigTwoCard card1 = (BigTwoCard) this.getCard(1);
			if (card0.rank == card1.rank) {
				return true;
			}
			else {
				return false;
			}
		}
		
	}
}
