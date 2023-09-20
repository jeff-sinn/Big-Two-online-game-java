/**
 * subclass of class Hand that is used to provide a detailed implementation of the abstract
 * method of type FullHouse
 * @author jeffsin
 *
 */
public class FullHouse extends Hand{
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	public String getType() {
		return "FullHouse";
	}
	/**
	 * a method to determine the fivecards hand ranking of type FullHouse
	 * @return the ranking of this fivecards hand type FullHouse
	 */
	public int fivecardsrank() {
		return 3;
	}
	/**
	 * a method to determine the top card of the hand of Type FullHouse
	 * @return the top card of the hand Type FullHouse
	 */
	public Card getTopCard() {
		this.sort();
		int count = 0;
		BigTwoCard card0 = (BigTwoCard) this.getCard(0);
		int rankk = card0.rank;
		for(int i = 0; i < this.size(); i++) {
			if (this.getCard(i).rank == rankk) {
				count ++;
			}
		}
		if (count == 3) {
			return this.getCard(2);
		}
		else if (count == 2){
			return this.getCard(4);
		}
		else {
			return null;
		}
	}
	/**
	 * check if this hand is of Type FullHouse
	 * @return whether this hand is of type FullHouse
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
		if (this.getCard(2).rank != rank1) {
			rank2 = this.getCard(2).rank;
		}
		else {
			rank2 = this.getCard(3).rank;
		}
		for (int i = 0; i < this.size(); i++) {
			if (this.getCard(i).getRank() == rank1) {
				count1 ++;
			}
			else if (this.getCard(i).getRank() == rank2) {
				count2 ++;
			}
		}
		if (count1 == 2 && count2 == 3) {
			return true;
		}
		else if (count1 == 3 && count2 == 2) {
			return true;
		}
		return false;
		
	}
	
}
