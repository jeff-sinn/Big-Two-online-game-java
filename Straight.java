/**
 * subclass of class Hand that is used to provide a detailed implementation of the abstract
 * method of type Straight
 * @author jeffsin
 *
 */
public class Straight extends Hand{
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	public String getType() {
		return "Straight";
	}
	/**
	 * check if this hand is of Type Straight
	 * @return whether this hand is of type Straight
	 */
	public boolean isValid() {
		if (this.size() != 5) {
			return false;
		}
		else {
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
			return true;
		}
	}
	/**
	 * a method to determine the fivecards hand ranking of type Straight
	 * @return the ranking of this fivecards hand type Straight
	 */
	public int fivecardsrank() {
		return 1;
	}
}
