/**
 * subclass of class Hand that is used to provide a detailed implementation of the abstract
 * method of type StraightFlush
 * @author jeffsin
 *
 */

public class StraightFlush extends Hand{
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * a method to return the type of hand
	 * @return type of the hand
	 */
	public String getType() {
		return "StraightFlush";
	}
	/**
	 * a method to determine the fivecards hand ranking of type StraightFlush
	 * @return the ranking of this fivecards hand type StraightFlush
	 */

	public int fivecardsrank() {
		return 5;
	}
	/**
	 * check if this hand is of Type StraightFlush
	 * @return whether this hand is of type StraightFlush
	 */

	public boolean isValid() {
		if (this.size() != 5) {
			return false;
		}
		this.sort();
		BigTwoCard card0 = (BigTwoCard) this.getCard(0);
		int rankk = card0.getRank();
		int currank;
		if (card0.rank == 0 || card0.rank == 1) {
			rankk += 13;
		}
		for (int i = 1; i < this.size(); i++) {
			card0 = (BigTwoCard) this.getCard(i);
			currank = card0.getRank();
			if (card0.rank == 0 || card0.rank == 1) {
				currank += 13;
			}
			if (currank != rankk + 1) {
				return false;
			}
			rankk = currank;
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
}
