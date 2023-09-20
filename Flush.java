/**
 * subclass of class Hand that is used to provide a detailed implementation of the abstract
 * method of type Flush
 * @author jeffsin
 *
 */
public class Flush extends Hand{
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	public String getType() {
		return "Flush";
	}
	/**
	 * check if this hand is of Type Flush
	 * @return whether this hand is of type Flush
	 */
	public boolean isValid() {
		if (this.size() != 5) {
			return false;
		}
		BigTwoCard cardd = (BigTwoCard) this.getCard(0);
		int suitt = cardd.getSuit();
		for (int i = 1; i < this.size(); i++) {
			cardd = (BigTwoCard) this.getCard(i);
			if (cardd.getSuit() != suitt) {
				return false;
			}
		}
		return true;
	}
	/**
	 * a method to determine the fivecards hand ranking of type Flush
	 * @return the ranking of this fivecards hand type Flush
	 */
	public int fivecardsrank() {
		return 2;
	}
	/**
	 * a method to comapare current hand of Type Flush with a specified hand
	 * @param hand handd to be compared
	 * @return whether the current hand of Type Flush beat the specified hand
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
			if (this.getCard(0).suit > hand.getCard(0).suit) {
				return true;
			}
			else if (this.getCard(0).suit < hand.getCard(0).suit) {
				return false;
			}
			else {
				return (this.getTopCard().compareTo(hand.getTopCard()) > 0);
			}
		}
		
	}
	
}
