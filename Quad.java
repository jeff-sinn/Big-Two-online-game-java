/**
 * subclass of class Hand that is used to provide a detailed implementation of the abstract
 * method of type Quad
 * @author jeffsin
 *
 */

public class Quad extends Hand{
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * return the type of hand
	 * @return type of the hand
	 */
	public String getType() {
		return "Quad";
	}
	/**
	 * a method to determine the fivecards hand ranking of type Quad
	 * @return the ranking of this fivecards hand type Quad
	 */
	public int fivecardsrank() {
		return 4;
	}
	/**
	 * a method to determine the top card of the hand of Type Quad
	 * @return the top card of the hand Type Quad
	 */
	public Card getTopCard() {
		this.sort();
		if (this.getCard(0).rank == this.getCard(1).rank) {
			return this.getCard(3);
		}
		else {
			return this.getCard(4);
		}
	}
	/**
	 * check if this hand is of Type Quad
	 * @return whether this hand is of type Quad
	 */

	public boolean isValid() {
		if (this.size() != 5) {
			return false;
		}
		this.sort();
		int count1 = 0;
		int count2 = 0;
		BigTwoCard card1 = (BigTwoCard) this.getCard(0);
		int rank1 = card1.getRank();
		int rank2;
		if (this.getCard(1).rank == rank1) {
			rank2 = this.getCard(4).rank;
		}
		else {
			rank2 = this.getCard(1).rank;
		}
		for (int i = 0; i < this.size(); i++) {
			if (this.getCard(i).getRank() == rank1) {
				count1 ++;
			}
			else if (this.getCard(i).getRank() == rank2) {
				count2 ++;
			}
		}
		if (count1 == 1 && count2 == 4) {
			return true;
		}
		else if (count1 == 4 && count2 == 1) {
			return true;
		}
		return false;
	}
}
